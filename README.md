# About
This is my simple school project that use client to send a number to the server and let it convert that number to word in 3 or more languages. This repo contain the server application.

For client application, please take a look at this repo: [PBL4_Clients](https://github.com/VuXuanHoang2003/PBL4_Clients)

# Compile & Build
Builds artifact are generated automatically for every commit. You can take a look at Actions that contain JAR artifact file.

If you rather compile on your own, make sure to use JDK 17, other JDK versions were untested, then use `mvn -B package --file pom.xml`, its will create an JAR application in `target/` folder

Use `java -jar PBL4_Server-1.0-SNAPSHOT.jar` to run


