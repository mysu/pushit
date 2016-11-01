# PushIt!
PushIt is a web/mobile application created for learning purposes but also to put in production.

The aim of the application is to let the users push messages from the mobile to the server to show them in a browser in a computer.

* __Use case__: The user is navigating in internet on his phone. A web page is interesting, so he wants to open it in a browser in his desktop computer, but the URL is to long to be copied manually, so he uses PushIt to send the URL  to the desktop.

## Main flow

1. The user opens the PushIt web application in the desktop browser.
2. The browser shows a QRCode identifying the client.
3. The browser is connected to a message broker through WebSockets
4. The user reads the code using the PushIt mobile application
5. The mobile application gets the client identifier to push messages to the server using a Rest service
6. When a message arrives to the server, it is send to the message queue with the identifier
7. The message broker receives the messages and redirects it to the desktop client
8. The message is rendered in the desktop browser

## Technologies

Since the project is for learning purposes, I'm using technologies I have little or not experience at all.
I'm gonna try to make the best use of the technologies, if not, feel free in write me, pull request or open an issue to solve the problem.

The technologies are:

* Spring boot
* Websockets
* Rest
* React.js
* Bootstrap

## Notice
First version is a proof-of-concept. I'm gonna refactor and improve after testing the idea.

## Versions

* [*v0.0.0*](https://github.com/mysu/pushit/tree/v0.0.0): Proof of Concept version. Working version, not unit nor integration testing. 
