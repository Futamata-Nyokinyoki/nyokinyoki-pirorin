# はじめに

打刻システム兼出欠管理システムのパチモンを Java で作成します。

# 機能

- 履修登録
- 時間割表の表示
- 打刻
- 出欠確認

# データベース

- 時間割表(TimeTable)
- シラバス(Syllabus)
- 打刻履歴(TimeStamp)

# 作成するクラス

- TimeTable
    - フィールド
        - Course の 2 次元配列
    - メソッド
        - CRUD
        - 時間割表の表示
- TimeTableDAO
- Course
    - フィールド
        - 時間割番号
        - 日時
        - 科目名
- CourseDAO
- TimeStamp
    - フィールド
        - 現在時刻
- TimeStampDAO
- TimeCard
    - フィールド
        - TimeStamp のリスト
    - メソッド
        - 打刻
- User
    - フィールド
        - TimeCard
        - TimeTable
    - メソッド
        - 履修登録
        - 打刻
        - 出欠確認
- WholeTester
- NyokiNyokiPirorin
    - メソッド
        - main メソッド

# 分担

あとでかく

分担するとしたら
- TimeCard, TimeStamp
- TimeTable, Course

- User, NyokiNyokiPirorin

# 実行フロー

## 履修登録

NyokiNyokiPirorin mainメソッドを実行する;
**履修登録をする**を選ぶ;
講義の id (時間割番号）を指定して履修登録（このあとに時間割表を確認したい）;
if 時間割がかぶった場合 {
  throw new Error("");
}
if 履修登録消去の場合 {
    履修登録;
} 

## 打刻

NyokiNyokiPirorin mainメソッドを実行する
**打刻をする**を選ぶ（システムが打刻時間をTimestamp DBに登録する）
if(success){
  **授業打刻：〇〇の授業に打刻しました**とメッセージを表示する
} else if(failure) {
  **通常打刻：打刻しました**とメッセージを表示する
}