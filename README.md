# whodis
Monitor local network for devices and announce them creepily

## Install
Download and run `./install.sh`.

## Running from source
Clone the repo and then run `./gradlew :run`. You will need a JDK already installed.

## Troubleshooting
* Make sure sound is enabled (see below)
* Make sure that the host is connected to the WiFi if you want to announce WiFi devices

### Enabling sound on Ubuntu server
Instructions from [here](http://howto.blbosti.com/2010/03/ubuntu-server-install-alsa-sound-and-moc-music-on-console/):

```
sudo apt install alsa alsa-tools
sudo adduser $USER audio
```

Then reboot and run `alsamixer` to interactively set the volume.