# tune2
Project is at development stage now.

Fully redesigned architecture and UI
- using array with specified initial capacity to hold tracks in playlist to improve speed while creating and modifying playlist
- using response from last.fm and vk.com APIs in JSON format instead XML to improve speed while parsing
- added image and duration of track
- searching URL of track at vk.com according to its duration (received from last.fm API) to exclude remixes and false tracks
- added volume control

Application uses third-party libraries and code:
- json-simple from Google https://code.google.com/p/json-simple/
- JLayer 1.0.1 MP3 decoder/player/converter from Javazoom
- volume control utility founded on https://community.oracle.com/thread/1272842
