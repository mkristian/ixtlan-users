# prerequisite

reqirements are

* java > 1.4.2 (depends on the jruby version, newer verions need java1.6)

* sqlite3

* optional: jruby from [http://jruby.org](jruby.org)

the whole readme will use MRI but you can use jruby anytime. 

## install ruby-maven gem

`gem install ruby-maven`

now there is a `rmvn` command available. 

from rails point of view you just take the rails/rake commands and prefix them with `rmvn` and a `gwt` command.

any rmvn command will place the gems for the application in the directory __target/rubygems__ which is set as **GEM\_HOME** and **GEM\_PATH** by the `rmvn` command.

# setup PATH variable

when the *PATH* set is set to

`export PATH=./target/bin:$PATH`

then you can use `rails`, `rake`, `rspec` in the context of the application, i.e. gems are managed by bundler and jars are managed by ruby-maven. when you do not set the *PATH* then you need to prefix `rmvn` in front of those commands `rmvn rails`, `rmvn rake`, `rmvn rspec`, etc

*NOTE* some older versions of jruby has a bug which does not obey relative path-entries in *PATH*.

# setup a fresh system

now we need to run

`rmvn bundle install`

**NOTE** running maven the first times takes some time. installing rubygems via maven takes a long time on the first run. so just be patient !

the above installs all gem with platform java - this is needed to run gwt shell. to intall the MRI gems just run

`bundle install`

again. now you can setup the database

`rake db:setup`

the root user has no password yet, but with the *reset password by email* on the login screen you can get a password - in development mode the email gets printed out in the console log.

# starting the server

for GWT development you need to use the development shell from GWT. you also can start the application with webrick (or with any other server gem) but for this you need to compile first the GWT part into javascript. finally you can use MRI to run the application.

## get a password (for development)

for each user you can reset the password via the login screen and see it inside the email which gets dump into the rails output.

## run gwt development shell

no need for compilation just start the server and developement shell with

`gwt run`

now you can launch a browser directly from that shell. the first time the browser ask to install a gwt-plugin for your browser, after that the application will start up.

## run webrick

first you need to compile the GWT application by

`gwt compile`

then you can start the server

`rails server`

to start the GUI:

`http://localhost:3000/Users.html#users`

# scaffold a resource

as example an account with a name attribute:

`rails generate scaffold account name:string --optimistic --modified_by`

**note:** the optimistic option will add optimistic transactions to the controller using the **updated_at** timestamp of the model. the modified_by option will add a reference to each record to the last user modified the record and the respective controller code.

after added a new resource (model) you need to update the database:

`rake db:migrate`

and most likely you need to restart the server (at least for GWT part).
 
`gwt run`

the GWT url for that new model you get by adding `#accounts:new` to the url which gets started by GWT development shell (with default port):

`http://localhost:8888/Users.html?gwt.codesvr=127.0.0.1:9997#accounts/new`

all controllers will also generate xml and json views (when there a valid session cookie, i.e. first login in the GWT GUI):

`http://localhost:8888/accounts.xml`

`http://localhost:8888/accounts.json`

**note:** to use the new resource with webrick you need to compile it first with `gwt compile`

# authorization

each user belongs to none, one or more groups. for each action on the controller you can declare the __allowed__ groups. for the _acount_ from the example above the guard declaration is in:

`app/guards/accounts_guard.rb`
