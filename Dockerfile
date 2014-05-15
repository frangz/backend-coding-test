from ubuntu

# Install prerequisites
run apt-get update
run apt-get install -y software-properties-common

# Install java8
run add-apt-repository -y ppa:webupd8team/java
run apt-get update
run echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
run apt-get install -y oracle-java8-installer

# BACK END
# Copy and build the backend application
add backend /backend
run apt-get install -y maven
run cd /backend && mvn package
# Install Tomcat
run apt-get -y install tomcat7 
run echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7
# Deploy
run rm -rf /var/lib/tomcat7/webapps/ROOT*
run cp /backend/target/backend-coding-test-0.0-SNAPSHOT.war /var/lib/tomcat7/webapps/ROOT.war

# FRONT END
# Install Nginx
run apt-get -y install nginx
run rm -rf /usr/share/nginx/html/*
add frontend /usr/share/nginx/html
add nginx-default.conf /etc/nginx/sites-enabled/default

expose 80

cmd service nginx start && service tomcat7 start && tail -F /var/lib/tomcat7/logs/catalina.out