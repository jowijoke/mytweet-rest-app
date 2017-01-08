# MyTweet Android App 


## Synopsis

MyTweet_app is android designed for users to send/read tweet submitted from the MyTweet API. Through this simple design, all users can view, write, read tweets to their hearts content. Also comprised int this app are two customized features where the app can cross communicate to the API via Heroku or AWS.  


## Installation

By Default this app is designed to communicate with an API connected via localhost. However, this project can be easily modified to connect to a AWS instance or heroku website to connect to the API by following a few simple instructions.

1) Type git clone followed by the https/ssh link for this project.

2) Type into the command line "git checkout heroku", which refers to a branch where the project has become modified to support the API connect to Heroku.

3) Alternatively, you can "git checkout aws", which refers to another branch modified to connect to an AWS instance. Whenever you wish to reconnect to localhost please type "git checkout master".

### Branch Information:

1) develop: consisted of creating new user and sending it to localhost API.

2) mobile:  consisted of logging into an existing user from localhost API.

3) JWT: Sending callbacks to authenticate a user while retrieving a JWT token.

4) tweet_fresh: create callabcks to retrieve the user's tweets

5) social: Attempted to show users followers and recommendations to follow new users 

5) heroku: Configured website to be used in heroku by connecting to mLab.

6) aws: Configured website to be used in AWS by connecting to mLab.



###Users already on the database
## homer@simpson.com 
## marge@simpson.com 
## bart@simpson.com 
## lisa@simpson.com