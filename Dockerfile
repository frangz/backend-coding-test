from ubuntu

# Install prerequisites
run apt-get update
run apt-get install -y software-properties-common

# Install java8
run add-apt-repository -y ppa:webupd8team/java
run apt-get update
run echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
run apt-get install -y oracle-java8-installer

# Install tools
run apt-get install -y git maven

# Clone project
run git clone https://github.com/frangz/backend-coding-test.git

# Download Tomcat
run wget http://apache.mirrors.timporter.net/tomcat/tomcat-7/v7.0.53/bin/apache-tomcat-7.0.53.tar.gz
run tar zxf apache-tomcat-7.0.53.tar.gz

# Build project
run cd backend-coding-test/backend && mvn package

# Deploy
run cp backend-coding-test/backend/target/backend-coding-test-0.0-SNAPSHOT.war apache-tomcat-7.0.53/webapps/

expose 8080

cmd [ "/apache-tomcat-7.0.53/bin/startup.sh" ]
