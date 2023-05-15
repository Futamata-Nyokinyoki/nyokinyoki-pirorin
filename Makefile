test:
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/Attend/*.java
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/DAO/*.java
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/Timestamp/*.java
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/Timetable/*.java
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java src/main/java/com/nyokinyoki/IntegrationTest.java
	java -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java com.nyokinyoki.IntegrationTest