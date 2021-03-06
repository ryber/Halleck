# Halleck Micro-LMS

<img src="http://upload.wikimedia.org/wikipedia/en/0/04/Gurney-1984.jpg" align="right" hspace="10" vspace="10" alt="Moods are for cattle and loveplay. Not fighting!" />
Halleck is free and open source "micro- learning management system" (or LMS) aimed at small teams and organizations that want to track learning activities. It aims at being fast and minimal, without the bloat of traditional LMS's.

Halleck is of course named after Gurney Halleck; Paul Atreides weapons trainer from Frank Herbert's 'Dune'

<br />
## Quick Start
* Clone it
* Compile it with Maven (mvn package)
* Run the jar (java --jar halleck.jar)
* Goto http://localhost:4567

### Starting in demo mode
* Simply add "demo" as a jar param (e.g. '''java --jar halleck.jar demo''')

## Requirements
* Java 1.8
* MongoDB if you want long term persistence 

## Configuration File
You can pass halleck the path to a properties file that contains config information. Available properties are:

* *site.name*
 * Use: The name of the site
 * Default: "Halleck LMS"
* *site.port*
 * Use: The port to run the app on
 * Default: 4567
* *site.admins* 
 * Use: The admin user
 * Default: 'admin'
* *site.externalmedia*
 * Use: A location, external to the jar. That contains static resources. Perfect for courses or videos. The root of this directory will be available from the root of Halleck.  
 * Default: none configured.
* *persistence.type*
 * Use: The type of persistence to use. Options are *memory* and *mongo* 
 * Default: *memory*
* *mongo.host*
 * Use: If using MongoDB, the location 
 * Default: 127.0.0.1 
* *mongo.port*
 * Use: If using MongoDB,  the port it's on
 * Default: 27017 
* *authentication.type*
 * Use: The kind of authentication to use. Options are *fake* and *LDAP*. Fake will pass any username and password as OK. LDAP uses a LDAP server.
 * Default: fake
* *ldap.url* 
 * Use: The URI to the ldap server. Needs to include the port and any CN's or other needed params.
 * Default: ldap://127.0.0.1:389
* *ldap.domain*
 * Use: A optional domain to prepend to all usernames. e.g. a value of "FOO" for a user named "Leto" will result in "FOO/Leto"
 * Default:
* *course.load*
 * Use: The location to a json file of courses to load at start up. (see next section for JSON description.
 * Default:

## Course Load Format

    [
        {
            "id":"1",
            "name":"How To Fold Space",
            "description":"The beginning is a very delicate time.",
            "maxCapacity":42,
            "registeredUsers":["Leto", "Jessica"],
            "url":"https://www.youtube.com/watch?v=6H5jeLxUy-0"
        }
    ]

*note on course URLs:* any course URL ending with .mp4 will be embeded into the launch lage. All other urls will launch into a new window.

## Road Map

### Soon
* Courses are "owned" by their creators.
* Users can "publish" their content to the catalog for others to take.
* Dynamic searching of the catalog.
* the ability to group courses into 'katas'
* Some kind of lighter persistence option. More than the in-memory option but still does not require a external service like Mongo to be set up. Suggestions?
