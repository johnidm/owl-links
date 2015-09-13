#!/usr/bin/env bash

#variables
activatorVersion="1.3.5"
sbtVersion="0.13.9"

echo "=========================================="
echo "Provision VM START"
echo "=========================================="

sudo apt-get update

###############################################
# install prerequisits
###############################################
sudo apt-get -y -q upgrade
sudo add-apt-repository ppa:webupd8team/java -y
sudo add-apt-repository ppa:git-core/ppa -y
sudo apt-get -y -q install build-essential
sudo apt-get -y -q install software-properties-common htop

sudo apt-key adv --keyserver keyserver.ubuntu.com --recv 7F0CEB10
echo "deb http://repo.mongodb.org/apt/debian wheezy/mongodb-org/3.0 main" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.0.list

sudo apt-get -y -q update

sudo apt-get -y -q install oracle-java8-installer
sudo apt-get -y -q install oracle-java8-set-default
sudo update-java-alternatives -s java-8-oracle

sudo apt-get install -y -q mongodb-org

sudo apt-get -y -q install postgresql postgresql-contrib postgresql-client-common postgresql-common

sudo apt-get -y -q install git

echo "Download SBT..."
wget http://dl.bintray.com/sbt/debian/sbt-$sbtVersion.deb
sudo dpkg -i sbt-$sbtVersion.deb
sudo apt-get update
sudo apt-get install sbt
rm sbt-$sbtVersion.deb
echo "SBT done."

###############################################
# Install typesafe activator
###############################################
cd /home/vagrant
echo "Download Typesafe Activator..."
wget http://downloads.typesafe.com/typesafe-activator/$activatorVersion/typesafe-activator-$activatorVersion-minimal.zip
unzip -d /home/vagrant typesafe-activator-$activatorVersion-minimal.zip
rm typesafe-activator-$activatorVersion-minimal.zip
echo "Typesafe Activator done."

###############################################
# Add activator to environment variables
###############################################
echo "export PATH=/home/vagrant/activator-$activatorVersion-minimal:\$PATH" >> ~/.bashrc

###############################################
# Use node as default JavaScript Engine
###############################################
echo "export SBT_OPTS=\"\$SBT_OPTS -Dsbt.jse.engineType=Node\"" >> ~/.bashrc

###############################################
# Reset bash
###############################################
source ~/.bashrc

###############################################
# Download dependencies and show activator help
# So we don't need to wait later
###############################################
/home/vagrant/activator-$activatorVersion-minimal/activator help

###############################################
# Show installation summary
###############################################
echo "=========================================="
echo "Provision VM summary"
echo "=========================================="
echo "Dependencies installed:"
echo " "
echo "Java version"
javac -version
java -version
echo " "
echo "PostgreSQL version"
psql -V
echo " "
echo "Git version"
git --version
echo " "
echo "Typesafe Activator version"
activator --version
echo " "
echo "MongoDB version"
mongo --version
echo " "

echo "Starting services:"
echo " "
echo "MongoDB..."
sudo service mongod start
echo " "

echo "=========================================="
echo "Provision VM finished"
echo "=========================================="
