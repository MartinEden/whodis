# whodis
Monitor local network for devices and announce them creepily

## Get it running
```
sudo apt install openjdk-8-jdk nmap espeak
./gradlew :run
```

## Enabling sound on Ubuntu server
Instructions from [here](http://howto.blbosti.com/2010/03/ubuntu-server-install-alsa-sound-and-moc-music-on-console/):

```
sudo apt install alsa alsa-tools
sudo adduser $USER audio
```

Then reboot and run `alsamixer` to interactively set the volume.
