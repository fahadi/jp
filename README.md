# jp
This program includes a StockClient, StockServer, StockCommon

The StockServer has a server.properties file in the Resouces folder, 
this value should be changed so that it points to a location where it can save the data file "save.file.location"=c:\\temp\\
the "server.port.number"=8799 should be a valid port number

This port number should also match the value in config.properties in the StockClient Resources folder

The StockServer and the StockClient have a dist folder if each of these folders are downloaded, then the following command from within
the dist folder:

java -jar "StockServer.jar"
java -jar "StockClient.jar"
