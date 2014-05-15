Alchemy Code Test
=================

API Definition
--------------

This REST service is documented and mocked with Apiary.io. You can find the API documentation here: http://docs.alchemy.apiary.io/. The current mock URL is http://alchemy.apiary-mock.com/v1. You can make the front-end consume this mock by editing expenses.js, as following:

```javascript
var expensesApp = angular.module("expensesApp", ["restangular", "ui.date"]).config(function (RestangularProvider) {
	RestangularProvider.setBaseUrl('http://alchemy.apiary-mock.com/v1');
});
```

Building the backend
--------------------

The backend consist of one WAR file. To build it you will need Java 8 and Maven 3 installed. Earlier versions of Java should work as well, but haven't been tested. 

To build the WAR file, run from inside the _backend_ folder.

```bash
> mvn clean package 
```

The resulting WAR file will be *target/backend-coding-test-0.0-SNAPSHOT.war*. You can deploy this into your favorite server. I tested Tomcat 7.


Consuming the REST Services
---------------------------

Once your WAR is deployed and running, go to http://localhost:8080/backend-coding-test-0.0-SNAPSHOT/expenses to test it. You should see JSON output.

This implementation writes CORS headers, allowing you to consume it from different hosts when you are using a modern browser. That means that you can use your local static copy of the frontend to consume this service straight away. You only need to change Restangular's base URL in expenses.js as follows:

```javascript
var expensesApp = angular.module("expensesApp", ["restangular", "ui.date"]).config(function (RestangularProvider) {
	RestangularProvider.setBaseUrl('http://localhost:8080/backend-coding-test-0.0-SNAPSHOT');
});
```

Of course this is only for happy development. We wouldn't do this in more restricted environments.


Run in Docker
-------------

If you don't have Java 8 or Maven in your machine, you can use Docker to set up a small virtual machine that runs the compiled code. You will see there is a file call Dockerfile. This is used to build the server image. In this case, the frontend is also included into the server, so you can do a complete test.

The script does the following, starting with a plain Ubuntu as a base image:

1. Update the OS packages.
2. Install Java 8.
3. Copy the Java code to the container and compile it.
4. Install Tomcat 7 and deploy the WAR file in it.
5. Install Nginx and copy the frontend files to the web root folder.
7. Configure Nginx to proxy the /expense path to Tomcat.
8. Expose the container's port 80.

To build it, after you have installed Docker, run from the folder that contains Dockerfile:

```bash
docker build -t frangz/alchemy .
```

The build process will take a few minutes. This is because we are using Ubuntu bare as the base image. To speed things up we could be using a base image that has Java and Tomcat installed. Also, instead of compiling the code, the final WAR file could be downloaded from an artifacty repository.

Once the build process is done, you will have a new image called frangz/alchemy. To run it, run the following:

```bash
docker run -d -p 8080:80 frangz/alchemy
```

This will start a new server as a daemon (-d) and it will bind your local port 8080 to the server's port 80. After a minute or so, you should see the website in htto://localhost:8080.

To stop the docker container, run *docker ps* to find out the container ID, and then run:
```bash
docker stop <id>
```

Docker makes it easy to build and run applications in different plataforms, such us Google Compute Engine or Amazon Web Services. AWS announced Docker support less than a month ago. Unfortunately, it seems the platform still needs some work. This simple example failed to deploy in it.

Much of the steps that we described in the Dockerfile, like installing Tomcat and Nginx can be replaced by more complex configuration management tools like Puppet and Chef. This would allow a finer control on the server configuration, such us package versions or more complex configurations.