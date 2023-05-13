# プログラミング応用　プログラミング技術　レポート

## グループ 16 　ふたまたニョキニョキ㌠

### メンバー

- 33114070 田内 智哉
- 33114098 野上 恵吾
- 33114113 藤岡 拓夢

---

## はじめに

本レポートでは、主にデータベースを用いつつ、JUnit を用いたテスト、BoF のデザインパターン、Java 17 にて実装された新機能を取り込んだシステムを作成した。

私たちが作成したのは、名工大で現在使われている打刻システム「ピロリン」や履修登録システムを再現した「ニョキピロリン」である。<br>
「ニョキピロリン」は以下に示すような機能を持っている。

- 履修登録
- 打刻
- 出欠判定

機能ごとに分け、それぞれのおおまかな実装方法を説明するとともに、適宜条件となっていたテスト、デザインパターン、Java 17 の新機能について触れる。

## ディレクトリ構成

以下に今回私たちが作成・利用した各種ファイルの場所を示す。

java-nyokinyoki/<br>
&emsp;├lib/<br>
&emsp;│&ensp;├hamcrest-core-1.3.jar<br>
&emsp;│&ensp;├jstl-api-1.2.jar<br>
&emsp;│&ensp;├jstl-impl-1.2.jar<br>
&emsp;│&ensp;├junit-4.13.2.jar<br>
&emsp;│&ensp;└sqlite-jdbc-3.41.2.1.jar<br>
&emsp;├src/<br>
&emsp;│&ensp;├main/java/com/nyokinyoki/<br>
&emsp;│&ensp;│&emsp;├TimeTable/<br>
&emsp;│&ensp;│&emsp;│&emsp;├Course/<br>
&emsp;│&ensp;│&emsp;│&emsp;│&emsp;├TimeSlot/<br>
&emsp;│&ensp;│&emsp;│&emsp;│&emsp;│&emsp;├TimeSlot.java<br>
&emsp;│&ensp;│&emsp;│&emsp;│&emsp;│&emsp;└TimeSlotDAO.java<br>
&emsp;│&ensp;│&emsp;│&emsp;│&emsp;├Course.java<br>
&emsp;│&ensp;│&emsp;│&emsp;│&emsp;└CourseDAO.java<br>
&emsp;│&ensp;│&emsp;│&emsp;├TimeTable.java<br>
&emsp;│&ensp;│&emsp;│&emsp;├TimeTableDAO.java<br>
&emsp;│&ensp;│&emsp;│&emsp;└TimeTableIntegrationTest.java<br>
&emsp;│&ensp;│&emsp;├AbstractDAO.java<br>
&emsp;│&ensp;│&emsp;├App.java<br>
&emsp;│&ensp;│&emsp;├Attendance.java<br>
&emsp;│&ensp;│&emsp;├AttendanceManager.java<br>
&emsp;│&ensp;│&emsp;├Matcher.java<br>
&emsp;│&ensp;│&emsp;├StampStatus.java<br>
&emsp;│&ensp;│&emsp;├TimeCard.java<br>
&emsp;│&ensp;│&emsp;└TimeStampDAO.java<br>
&emsp;│&ensp;└test/java/com/nyokinyoki/<br>
&emsp;│&ensp;&emsp;&emsp;└AppTest.java<br>
&emsp;├target/<br>
&emsp;├.gitignore<br>
&emsp;├index.sh<br>
&emsp;├Makefile<br>
&emsp;├NyokinyokiPirorin.db<br>
&emsp;├pom.xml<br>
&emsp;├README.md<br>
&emsp;└report.md<br>

##
