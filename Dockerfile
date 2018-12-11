from java:8

workdir /usr/local
run wget http://ftp.cuhk.edu.hk/pub/packages/apache.org/maven/maven-3/3.6.0/binaries/apache-maven-3.6.0-bin.tar.gz 
run tar -xvf apache-maven-3.6.0-bin.tar.gz
run ln -s apache-maven-3.6.0 maven
run pwd
run ls -l /usr/local
env MAVEN_HOME /usr/local/maven
env PATH $PATH:$MAVEN_HOME/bin

run mkdir /code
add . /code
workdir /code
run mvn clean install -Dmaven.test.skip=true


expose 8888 9999