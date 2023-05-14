test:
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/*.java
	java -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java com.nyokinyoki.IntegrationTest