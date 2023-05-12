# 結合テストの実行
test:
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:lib/junit-4.13.2.jar src/main/java/com/nyokinyoki/nyokinyoki_pirorin/*.java
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar:lib/junit-4.13.2.jar src/test/java/com/nyokinyoki/nyokinyoki_pirorin/*.java
	java -cp .:lib/sqlite-jdbc-3.41.2.1.jar src/test/java/com/nyokinyoki/nyokinyoki_pirorin/TimeTableIntegrationTest