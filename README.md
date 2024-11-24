# practice1
The class, depending on the selected system parameter “-Dformat=”json/xml“” (The default is json),
sends the message “Hello `name`!” to the console in XML or JSON format.
The program receives the username from the file 'message.properties',
which should be located near the Jar file or you need to pass the file path to the program arguments.
If the format parameter is not passed, json will be the default.
{ “message”: “Hello <text from external properties file, username=your name> !"}

To create a jar file, run the following commands:
1.Build the project with a maven
mvn clean package -Passembly / -Pshade

2.Run the jar file with libraries
java -Dformat="json" -jar target/json-1.0.jar "path to properties file"
