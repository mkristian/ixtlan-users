# prerequisite

reqirements are

* java > 1.4.2

* sqlite3

* jruby from [http://jruby.org](jruby.org)

almost the whole readme will use jruby. that is not strictly needed since after java-to-javascript compilation everything runs with MRI as well. but to keep things simple . . .

## install ruby-maven gem

`jruby -S gem install ruby-maven`

now there is a `rmvn` command available. 

from rails point of view you just take the rails/rake commands and prefix them with 'rmvn'.

any rmvn command will place the gems for the application in the directory __target/rubygems__ which is set as **GEM\_HOME** and **GEM\_PATH** by the `rmvn` command.


# setup a fresh system

the first run of maven takes a while and for the rubygems part that is true too.

`rmvn rake db:migrate db:seed`

will setup the database and a root user. the root user has not password yet, but with the *reset password by email* you can get a password - in development mode the email gets printed out in the console log.


# starting the server

for GWT development you need to use the development shell from GWT. you also can start the application with webrick (or with any other server gem) but here you need to compile first the GWT part into javascript. finally you can use MRI to run the application.

## run gwt development shell

no need for compilation just start the server and developement shell with

`rmvn gwt:run`

now you can launch a browser directly from that shell. the first time the browser ask to install a gwt-plugin for your browser, after that the application will start up.

## run webrick

first you need to compile the GWT application by

`rmvn compile gwt:compile`

then you can start the server

`rmvn rails server`

now use the url to start (html view but login page is missing):

`http://localhost:3000/users`

or for the GWT part

`http://localhost:3000/Users.html#users`


### using webrick and MRI

first get all your gems in place by

`bundle install`

and then start the server

`rails server`

# scaffold a resource

as example an account with a name attribute:

`rmvn rails generate scaffold account name:string --optimistic`

note: the optimistic option will add optimistic transactions to the controller using the **updated_at** timestamp of the model.

after added a new resource (model) you need to update the database:

`rmvn rake db:migrate`

and most likely you need to restart the server (at least for GWT part).
 
`rmvn gwt:run`

the GWT url for that new model you get by adding `#accounts:new` to the url which gets started by GWT development shell (with default port):

`http://localhost:8888/Users.html?gwt.codesvr=127.0.0.1:9997#accounts:new`

after successful login you can go to html view as well (there is no html login - yet):

`http://localhost:8888/accounts/new`

all controllers will also generate xml and json views:

`http://localhost:8888/accounts.xml`

`http://localhost:8888/accounts.json`

## using the new resource with webrick

first you need to compile the java part to javascript

`rmvn compile gwt:compile`

then you start the server

`rmvn rails server`

with the urls the html view (here the login page is missing)

`http://localhost:3000/accounts/new`

or the GWT views

`http://localhost:3000/Users.html#accounts/new`

# authorization

per default **root** group can do everything, i.e. username **root** will get you in :)

each user belong to none, one or more groups. for each action on the controller you can declare the __allowed__ groups. for the _acount_ from the example above the guard declaration is in (changes in that file needs a restart of the server):

`app/guards/accounts_guard.rb`

## configured groups

only root
