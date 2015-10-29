# tune2 (tune! v. 2)
Project is at development stage now.

tune! is simple music player, which allows you to search and listen best tracks by tag or artist, according to last.fm charts

Fully redesigned architecture and UI
- using array with specified initial capacity to hold tracks in playlist to improve speed while creating and modifying playlist
- using response from last.fm and vk.com APIs in JSON format instead XML to improve speed while parsing
- added images and duration of tracks
- searching URL of tracks at vk.com according to its duration (received from last.fm API) to exclude remixes and false tracks
- added volume control

Application uses third-party libraries and code:
- json-simple from Google https://code.google.com/p/json-simple/
- JLayer 1.0.1 MP3 decoder/player/converter from Javazoom http://www.javazoom.net/javalayer/javalayer.html
- volume control utility founded on https://community.oracle.com/thread/1272842


<center>![screenshot](https://cloud.githubusercontent.com/assets/11961551/10827638/c03fc1b0-7e78-11e5-9b2e-a069188ac79c.png)</center>
