# prerequisite

reqirements are

* java > 1.4.2

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

then you can use `rails`, `rake`, `rspec` in the context of the application, i.e. gems are managed by bundler and jars are managed by ruby-maven. when you do not set the *PATH* then you need to prefix `rmvn` in front of those commands:

`rmvn rails`, `rmvn rake`, `rmvn rspec`, etc

*NOTE* some older versions of jruby has a bug which does not obey relative path-entries in *PATH*.

# setup a fresh system

the first run of maven takes a while and for the rubygems part that is true too.

`rake db:migrate db:seed`

will setup the database and a root user. the root user has not password yet, but with the *reset password by email* you can get a password - in development mode the email gets printed out in the console log.


# starting the server

for GWT development you need to use the development shell from GWT. you also can start the application with webrick (or with any other server gem) but here you need to compile first the GWT part into javascript. finally you can use MRI to run the application.

## run gwt development shell

no need for compilation just start the server and developement shell with

`gwt run`

now you can launch a browser directly from that shell. the first time the browser ask to install a gwt-plugin for your browser, after that the application will start up.

## run webrick

first you need to compile the GWT application by

`gwt compile`

then you can start the server

`rails server`

now use the url to start (html view but login page is missing):

`http://localhost:3000/users`

or for the GWT part

`http://localhost:3000/Users.html#users`


### using webrick with MRI

here `ruby` means the MRI. first you need to get all your gems in place by

`ruby -S bundle install`

and then start the server

`ruby -S rails server`

# scaffold a resource

as example an account with a name attribute:

`rails generate scaffold account name:string --optimistic`

note: the optimistic option will add optimistic transactions to the controller using the **updated_at** timestamp of the model.

after added a new resource (model) you need to update the database:

`rake db:migrate`

and most likely you need to restart the server (at least for GWT part).
 
`gwt run`

the GWT url for that new model you get by adding `#accounts:new` to the url which gets started by GWT development shell (with default port):

`http://localhost:8888/Users.html?gwt.codesvr=127.0.0.1:9997#accounts:new`

after successful login you can go to html view as well (there is no html login - yet):

`http://localhost:8888/accounts/new`

all controllers will also generate xml and json views:

`http://localhost:8888/accounts.xml`

`http://localhost:8888/accounts.json`

## using the new resource with webrick

first you need to compile the java part to javascript

`gwt compile`

then you start the server

`rails server`

with the urls the html view (here the login page is missing)

`http://localhost:3000/accounts/new`

or the GWT views

`http://localhost:3000/Users.html#accounts/new`

# authorization

each user belongs to none, one or more groups. for each action on the controller you can declare the __allowed__ groups. for the _acount_ from the example above the guard declaration is in:

`app/guards/accounts_guard.rb`

## groups

each group belongs to __one__ applications. there are two special applications __THIS__ and __ALL__.

* root@THIS: can do everything, i.e. it is equivalent to have database access

* user_admin@THIS: can manage users and their groups (create,modify) but all actions are restricted to the groups the user itself has.

* app\_admin@THIS: can manage the some application specific configuration: applications table, remote\_permissions, groups

* at@ALL: can see all users which are marked as ATs. the special application __ALL__ means it belongs to ALL applications

any combination of groups are possible. for example:

* user-admin + at: can create and manage AT users

* app\_admin + user\_admin: can manage all resources for an application

# authentication

there is the login to __THIS__ application as well an login from a remote application. further it is possible to sync certain tables (Users + AT + Regions) with a remote application.

## get a password (for development)

for each user you can reset the password via the login screen and see it inside the email which gets dump into the rails output.

## remote access

the remote permissions has a password which needs to be set as 'X-Service-Token' which grants access to the these remote resources. the same token is needed to use the authentication service. if the __app\_admin__ configured an IP within the remote\_permission for the application then access is only granted if the IP matches (when coming from a cluster then the IP might vary and can not be checked - keep it nil in that case).

## syncing remote data

syncing goes via the following urls:

* http://<THIS_DOMAIN>/users/last\_changes.json?updated_at=<DATE>
* http://<THIS_DOMAIN>/ats/last\_changes.json?updated_at=<DATE>
* http://<THIS_DOMAIN>/regions/last\_changes.json?updated_at=<DATE>

or the xml variant

* http://<THIS_DOMAIN>/users/last\_changes.xml?updated_at=<DATE>
* http://<THIS_DOMAIN>/ats/last\_changes.xml?updated_at=<DATE>
* http://<THIS_DOMAIN>/regions/last\_changes.xml?updated_at=<DATE>

which will deliver all records which changed after the given date. all date formats are in __UTC__ with pattern __%Y-%m-%d %H:%M:%S.%6N__ (ruby-1.9.3 syntax).

example:

wget --header=X-Service-Token:be\ happy "http://localhost:3000/ats/last_changes.json?updated_at=2000-01-01 01:01:01.000000"
yaml response:
--- 
- at: 
    name: AT
    at_token: asd
    updated_at: 2012-01-22T18:26:05.532000+0000
    id: 4
- at: 
    name: AT Admin
    at_token: asd
    updated_at: 2012-01-23T07:35:06.974000+0000
    id: 5

wget --header=X-Service-Token:be\ happy "http://localhost:3000/regions/last_changes.json?updated_at=2000-01-01 01:01:01.000000"
yaml response:
--- 
- region: 
    name: Europe
    updated_at: 2012-01-22T18:25:52.097000+0000
    id: 1
- region: 
    name: Asia
    updated_at: 2012-01-22T18:25:52.344000+0000
    id: 2

wget --header=X-Service-Token:be\ happy "http://localhost:3000/users/last_changes.json?updated_at=2000-01-01 01:01:01.000000"
yaml response:
--- 
- user: 
    name: Root
    updated_at: 2012-01-26T09:12:53.875000+0000
    id: 1
    login: root
- user: 
    name: App Admin
    updated_at: 2012-01-22T18:32:56.082000+0000
    id: 2
    login: app_admin
- user: 
    name: User Admin
    updated_at: 2012-01-22T18:32:41.781000+0000
    id: 3
    login: user_admin
- user: 
    name: AT
    updated_at: 2012-01-22T18:26:05.532000+0000
    id: 4
    login: at

## remote authentication

the request is

authentication:
  login: <login>
  password: <password>

the response is either http 401 or http 200 (content-type: json). by using authentication.xml in the url you will get xml data as response. as input you can either use query parameter in the post body or send json or xml payload with proper content type declaration.

example:
wget --header="X-Service-Token:be happy" --post-data='authentication[login]=root&authentication[password]=LuggklyepNIR' "http://localhost:3000/authentications.json"

response as yaml:
user: 
  name: Root
  groups: 
  - regions: 
    name: root
    id: 5
  applications: 
  - application: 
      name: THIS
      url: http://localhost:3000/Users.html
      id: 1
  - application: 
      name: dev-application
      url: http://localhost/Dev.html
      id: 3
  id: 1
  login: root

## reset password

the request is

authentication:
  login: <login>

the response is either http 404 or http 200 (content-type: text). as input you can either use query parameter in the post body or send json or xml payload with proper content type declaration. the response will be __always__ text.

example:
wget --header="X-Service-Token:be happy" --post-data="authentication[login]=root" "http://localhost:3000/authentications/reset_password.json"

response:
password sent

# flavor of remote user-management

## central username/password

just use it as central username/password service. on login you get a list of application urls for the logged in user has access rights.

## posix users and groups

use it to manage users and their groups like posix user and groups on unix system, via ldap, etc. on successful login a list of groups are delivered along some user info.

## user, groups and regions/locations

each group can be associated with one or more regions (pending: and/or course locations). these associations of each group will be delivered on login.
