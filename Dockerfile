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
run apt-get install -y maven tomcat7
run echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7

add backend /backend
run cd /backend && mvn package

# Install and init database
add createdb.sql /
run DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-server
run service mysql start && mysql -u root < createdb.sql

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

cmd service mysql start && service nginx start && cd /var/lib/tomcat7 && export CATALINA_BASE=/var/lib/tomcat7 && export CATALINA_HOME=/usr/share/tomcat7 && /usr/share/tomcat7/bin/catalina.sh start && tail -F /var/log/tomcat7/catalina.out