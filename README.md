# Halleck Micro-LMS

<img src="http://upload.wikimedia.org/wikipedia/en/0/04/Gurney-1984.jpg" align="right" hspace="10" vspace="10" alt="Moods are for cattle and loveplay. Not fighting!" />
Halleck is a "micro-learning management system" (or LMS) aimed at small teams and organizations that want to track learning activities. It aims at being fast and minimal, without the bloat of traditional LMS's.

Halleck is of course named after Gurney Halleck; Paul Atreides weapons trainer from Frank Herbert's 'Dune'

<br />
## Quick Start
* Clone it
* Compile it with Maven (mvn compile)
* Run the jar (java --jar halleck.jar)
* Goto http://localhost:4567

## Configuration File
You can pass halleck the path to a properties file that contains config information. Available properties are:

| Property | Use | Default |
| === | === | === |
| site.name | The name of the site | Halleck LMS |
| site.port | The port to run the app on | 4567 |
| site.admins | The admin user | ryber |
| persistence.type | The type of persistence to use. Options are '''memory''' and '''mongo''' | memory |
| mongo.host | If using MongoDB, the location  | 192.168.56.101 |
| mongo.port | If using MongoDB,  the port it's on | 27017 |


## Road Map

### Soon
* Navigation
* The ability to log out
* A 'My Courses' page

### Long Term Goals
* anyone can create and publish content
* the ability to group content into 'boot camps'

### Things Halleck will never do
* SCORM / AICC
