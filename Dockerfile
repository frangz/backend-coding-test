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

# Install Tomcat
run apt-get -y install tomcat7 
run echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7

# Build project
run cd backend-coding-test/backend && mvn package

# Deploy
run rm -rf /var/lib/tomcat7/webapps/ROOT*
run cp backend-coding-test/backend/target/backend-coding-test-0.0-SNAPSHOT.war /var/lib/tomcat7/webapps/ROOT.war

expose 8080

cmd service tomcat7 start && tail -F /var/lib/tomcat7/logs/catalina.out