test:
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java `find src/main/java/com/nyokinyoki -name "*.java" | grep -v App | grep -v Matcher`
	java -cp .:lib/sqlite-jdbc-3.41.2.1.jar:src/main/java com.nyokinyoki.IntegrationTest