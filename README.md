# Book Schelf application

Application is launched by default on 8080 port. Please don't change it due to Frontend connects to this port.

There is used openapi library and as consequence auto generated code should be generated before to launch. For example you can do this with help of:
    mvn clean compile
    
Application uses H2 database for storing of data. By default it stores into file but you can to switch mode to "in memory" by changing of application.properties file (see first two lines).

To monitor database:  http://localhost:8080/h2-console
To work with RestAPI: http://localhost:8080/swagger-ui/