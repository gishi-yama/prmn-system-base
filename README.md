# prmn-system-base

プロジェクトメンバーが何かプロトタイプを作る時の参考用に作成したアプリです。

フロントエンドをVaddin(based on SpringBoot), バックエンドをSpring Boot (RestController、山川研版の4層モデル）で作成しています。


**注意:**
フロントエンドやバックエンドの連携や、その中の層構造のイメージをもってもらうために作成したものなので、本来必要となるセキュリティ面は考慮していません。
これをベースに、各プロジェクトで作品を作成してもらっても構いませんが、試験的な場合を含め、内外に作品を動作させて利用してもらう場合には、セキュリティ面の対応をよく行なった上で進めてください。

## How to run

1. backend-sb, front-vaadin, front-wicket をIntelliJ IDEAで開き、起動してください。
2. Vaadinの場合は http://localhost:8080 に、Wicketの場合は http://localhost:8081 にアクセスしてください。
3. ボタンを押すと、backend-sb から取得したあいさつメッセージが表示されます。時間帯によって変わります。
