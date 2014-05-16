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

Database preparation
--------------------

Before you run the backend application, you need to have a local MySQL database available. To create it with the right parameters, you can use the provided createdb.sql script.

```bash
> mysql -u root < createdb.sql 
```

The DDL will be run by Hibernate, so you don't need to create any tables.

Building the backend
--------------------

The backend consists of one WAR file. To build it you will need Java 8 and Maven 3 installed. Earlier versions of Java should work as well, but haven't been tested. 

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

If you don't have Java 8 or Maven in your machine, you can use Docker to set up a small virtual machine that runs the compiled code. You will see there is a file called Dockerfile. This is used to build the server image. In this case, the frontend is also included into the server, so you can do a complete test.

The script does the following, starting with a plain Ubuntu as a base image:

1. Update the OS packages.
2. Install Java 8.
3. Copy the Java code to the container and compile it.
4. Install Tomcat 7.
5. Install MySQL and initialize the required database.
8. Deploy the WAR file.
5. Install Nginx and copy the frontend files to the web root folder.
7. Configure Nginx to proxy the /expenses path to Tomcat.
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

Much of the steps that we described in the Dockerfile, like installing Tomcat and Nginx can be replaced by more complex configuration management tools like Puppet and Chef. This would allow a finer control on the server configuration, such us package versions or more complex configurations.


Deploy to AWS Elastic Beanstalk
-------------------------------

Docker makes it easy to build and run applications in different plataforms, such us Google Compute Engine or Amazon Web Services. AWS announced support for Docker in Elastic Beanstalk just a few weeks ago. Here we will deploy our application to Elastic Beanstalk.

1. Clone this project.
2. Download the Elastick Beanstalk Command Line Tool (eb) from http://aws.amazon.com/code/6752709412171743. Make sure you have Python 2.7+ and Boto installed. You can install boto with pip.
3. Run the set up script from your project root: AWS-ElasticBeanstalk-CLI-2.6.2/AWSDevTools/Linux/AWSDevTools-RepositorySetup.sh
4. Prepare a IAM User account to use with eb. Elastic Beanstalk requires many permissions since it manages all your AWS services for you. You can use the following as an example, but be careful on where you store this credentials.
```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "elasticbeanstalk:*",
        "ec2:*",
        "elasticloadbalancing:*",
        "autoscaling:*",
        "cloudwatch:*",
        "s3:*",
        "sns:*",
        "cloudformation:*",
        "rds:*",
        "sqs:*",
        "iam:*"
      ],
      "Resource": "*"
    }
  ]
}
```
Remember to download the user's API keys.
5. Set up eb: from the project root run:
```bash
eb init
```
You will need to answer questions about the Elastic Beanstalk application you are creating. Use these answers as a reference:
```plain
 1. Enter the API keys of the user you created before
 2. Region: EU West (Ireland) 
 3. Enter names for the application and environment
 4. Tier: WebServer::Standard::1.0 
 5. Stack: 64bit Amazon Linux 2014.03 v1.0.4 running Docker 0.9.0
 6. Environment type: SingleInstance
 7. Create an RDS DB: No
 8. Attach an instance profile: default
```
6. Start your environment running:
```bash
eb start
```
You will be asked if you want to upload your latest git commit. Choose No. Wait until the environment finishes launching. You can follow the progress at the Web Console or by the command line messages. You will get your envinronment's URL once it's done. It will look like this: http://backend-coding-test-env-mwiym8c2zt.elasticbeanstalk.com/.

7. Since the Dockerfile performs many heavy tasks we will need a bit more than a t1.micro instance. From the Web Console, modify your environment to use m1.small instances instead. This can also be done with configuration files. Once you confirm, it will take around 5 minutes to go back to Green (ready).

8. Now you can upload the application. From the project root run:
```bash
eb push
```
This will upload your latest local git commit to the environment you launched. Since there is a lot of downloading in our Dockerfile, it will take some time. My test took **15-20 minutes** until the application became available at the environment's URL. Both the command line tool and the web console will timeout before that, but the instance will keep working after that.

Now you have the application deployed in a m1.small instance in Ireland, inside a docker container. If we would have chosen LoadBalanced instead of SingleInstance we would have 2 instances and a load balancer, but for that we need to take the database outside Docker first. 

If you make changes to the application you can upload them to the environment running *eb push* again. I haven't tested it, but I assume it should be faster than the first time.

For a complete guide on deploying to AWS Elastic Beanstalk with docker, check: http://docs.aws.amazon.com/elasticbeanstalk/latest/dg/create_deploy_docker_eb.html

**Remember to delete your Elastic Beanstalk application once you are done to avoid extra charges!**