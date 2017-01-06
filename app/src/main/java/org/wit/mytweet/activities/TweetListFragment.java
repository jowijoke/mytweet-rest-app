package org.wit.mytweet.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.wit.android.helpers.IntentHelper;
import org.wit.mytweet.R;
import org.wit.mytweet.activities.settings.SettingsActivity;
import org.wit.mytweet.main.MyTweetApp;
import org.wit.mytweet.models.Portfolio;
import org.wit.mytweet.models.Tweet;
import org.wit.mytweet.sqlite.DbHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.wit.android.helpers.LogHelpers.info;

/**
 * Created by User on 17/10/2016.
 */

public class TweetListFragment extends ListFragment implements Callback<List<Tweet>>, AdapterView.OnItemClickListener, AbsListView.MultiChoiceModeListener{

    private ListView listView;
    private ArrayList<Tweet> tweets;
    private Portfolio portfolio;
    private TweetAdapter adapter;
    MyTweetApp app;

    private DbHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getActivity().setTitle(R.string.app_name);

        app = MyTweetApp.getApp();
        portfolio = app.portfolio;
        tweets = portfolio.tweets;

        adapter = new TweetAdapter(getActivity(), tweets);
        setListAdapter(adapter);

        retrieveTweets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        View v = super.onCreateView(inflater, parent, savedInstanceState);
        listView = (ListView) v.findViewById(android.R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(this);


        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tweet msg = ((TweetAdapter) getListAdapter()).getItem(position);
        Intent i = new Intent(getActivity(), TweetPagerActivity.class);
        i.putExtra(TweetFragment.EXTRA_TWEET_ID, msg._id);
        startActivityForResult(i, 0);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TweetAdapter) getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.tweetlist, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_tweet:
                Tweet tweet = new Tweet();

                createTweet(tweet);
                return true;

            case R.id.action_refresh:
                retrieveTweets();
                return true;

            case R.id.action_settings:
                startActivity(new Intent(getActivity(), SettingsActivity.class));
                return true;

            case R.id.action_clear:
                portfolio.deleteAllTweets(tweets);
                adapter.notifyDataSetChanged();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createTweet(Tweet tweet) {
        Call<Tweet> call = app.tweetService.makeTweet(MyTweetApp.currentUser._id, tweet);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void onResponse(Call<Tweet> call, Response<Tweet> response) {
                Tweet tweet = response.body();
                if (tweet != null) {
                    Toast.makeText(getActivity(), "Tweet created successfully", Toast.LENGTH_SHORT).show();

                    portfolio.addTweet(tweet);
                    Intent i = new Intent(getActivity(), TweetPagerActivity.class);
                    i.putExtra(TweetFragment.EXTRA_TWEET_ID, tweet._id);
                    startActivityForResult(i, 0);

                }
                else {
                    Toast.makeText(getActivity(), "Tweet null returned due to incorrectly configured client", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Tweet> call, Throwable t) {
                Toast.makeText(getActivity(), "Tweet null returned due to incorrectly configured client", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Tweet tweet = adapter.getItem(position);
        IntentHelper.startActivityWithData(getActivity(), TweetPagerActivity.class, "TWEET_ID", tweet._id);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode actionMode, int position, long id, boolean checked) {

    }

    public void retrieveTweets() {
        Toast.makeText(getActivity(), "Retrieving Tweet list", Toast.LENGTH_SHORT).show();
        Call<List<Tweet>> call = (Call<List<Tweet>>) app.tweetService.getAllTweets();
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<List<Tweet>> call, Response<List<Tweet>> response) {
        adapter.tweets = response.body();
        adapter.notifyDataSetChanged();
        portfolio.refreshTweets(adapter.tweets);
    }

    @Override
    public void onFailure(Call<List<Tweet>> call, Throwable t) {
        Toast toast = Toast.makeText(getActivity(), "Error retrieving tweets", Toast.LENGTH_SHORT);
        toast.show();
        info(this, "Failed: " + t);
    }


    /* ************ MultiChoiceModeListener methods (begin) *********** */
    @Override
    public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
        MenuInflater inflater = actionMode.getMenuInflater();
        inflater.inflate(R.menu.tweet_list_context, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode actonMode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_item_delete_tweet:
                deleteTweet(actionMode);
                return true;
            default:
                return false;
        }

    }

    private void deleteTweet(ActionMode actionMode) {
        for (int i = adapter.getCount() - 1; i >= 0; i--) {
            if (listView.isItemChecked(i)) {
                portfolio.deleteTweet(adapter.getItem(i));
            }
        }
        actionMode.finish();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyActionMode(ActionMode actionMode) {

    }


    /* ************ MultiChoiceModeListener methods (end) *********** */

    class TweetAdapter extends ArrayAdapter<Tweet> {
        private Context context;
        public List<Tweet> tweets;

        public TweetAdapter(Context context, ArrayList<Tweet> tweets) {
            super(context, 0, tweets);
            this.context = context;
            this.tweets = tweets; //declare and intialise field

        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_tweet, null);
            }
            Tweet tweet = tweets.get(position);

            TextView messageTextView = (TextView) convertView.findViewById(R.id.tweet_list_item_message);
            messageTextView.setText(tweet.message);

            TextView dateTextView = (TextView) convertView.findViewById(R.id.tweet_list_item_dateTextView);
            dateTextView.setText(tweet.getDateString());

            return convertView;
        }
        @Override
        public int getCount() {
            return tweets.size();
        }
    }
}
