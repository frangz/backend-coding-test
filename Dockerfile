FROM ubuntu

# Install prerequisites
RUN apt-get update
RUN apt-get install -y software-properties-common

# Install java8
RUN add-apt-repository -y ppa:webupd8team/java
RUN apt-get update
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
RUN apt-get install -y oracle-java8-installer

# BACK END

# Copy and build the backend application
RUN apt-get install -y maven tomcat7
RUN echo "JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> /etc/default/tomcat7

ADD backend /backend
RUN cd /backend && mvn package

# Install and init database
ADD createdb.sql /
RUN DEBIAN_FRONTEND=noninteractive apt-get -y install mysql-server
RUN service mysql start && mysql -u root < createdb.sql

# Deploy
RUN rm -rf /var/lib/tomcat7/webapps/ROOT*
RUN cp /backend/target/backend-coding-test-0.0-SNAPSHOT.war /var/lib/tomcat7/webapps/ROOT.war


# FRONT END

# Install Nginx
RUN apt-get -y install nginx
RUN rm -rf /usr/share/nginx/html/*
ADD frontend /usr/share/nginx/html
ADD nginx-default.conf /etc/nginx/sites-enabled/default

EXPOSE 80

CMD service mysql start && service nginx start && cd /var/lib/tomcat7 && export CATALINA_BASE=/var/lib/tomcat7 && export CATALINA_HOME=/usr/share/tomcat7 && /usr/share/tomcat7/bin/catalina.sh start && tail -F /var/log/tomcat7/catalina.out