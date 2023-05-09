# 結合テストの実行
test:
	javac -cp .:lib/sqlite-jdbc-3.41.2.1.jar *.java
	java -cp .:lib/sqlite-jdbc-3.41.2.1.jar TimeTableIntegrationTest