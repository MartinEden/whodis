#!/usr/bin/env bash
set -ex

VERSION=1.0.0
URL=https://github.com/MartinEden/whodis/releases/download/v${VERSION}/whodis-${VERSION}.zip

apt-get install -y openjdk-8-jre nmap espeak unzip
wget -O whodis.zip ${URL}
unzip whodis.zip -d /opt/whodis
ln -s /opt/whodis/whodis /usr/bin/whodis

mkdir -p /etc/whodis

cp whodis.service /etc/systemd/system/whodis.service
systemctl enable whodis
systemctl start whodis

set +x
echo "Installed and started whodis as systemd service"
echo "Control with systemctl [start|stop|status] whodis"
