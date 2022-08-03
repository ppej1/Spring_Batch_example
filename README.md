# Spring_Batch_example

#Spring Batch document 
https://spring.io/projects/spring-batch

#Spring Batch 構築
https://docs.spring.io/spring-batch/docs/current/reference/html/job.html

#まずは実践、Spring Boot Batchの動かし方
https://qiita.com/kawakawaryuryu/items/4b3f5cc7574b7bd6b625

#Spring Batch サンプルコード(DB)
https://www.qoosky.io/techs/3f60bee741


#Spring Batch を使用して指定したジョブを実行する際の考慮点
https://qiita.com/kakasak/items/81a2a91ac22a8a05ee2a


#Schema
https://docs.spring.io/spring-boot/docs/current/reference/html/howto.html#howto.data-initialization


#KO 特定JOB利用
https://oingdaddy.tistory.com/283

# tasklet
https://qiita.com/yuuman/items/5c9862f6ba75feb99f13

Spring Batchの全体アーキテクチャです。これだけでも頭に入れておくと良いです。
ここでは以下のワードについて解説します。

#Job
Spring Batchで一番大本にある要素でバッチ処理全体を表します。1Job = 1バッチ処理と考えて問題ないと思います。Jobは複数のStepから構成され、1つのJobを実行するとそれを構成する複数のStepが実行されます。

#JobParameter
Job実行時に渡すパラメータ。ここで渡したパラメータをJobやStepの変数として利用できます。

#Step
バッチ処理を構成する最小単位の要素。Stepは大きく2種類のモデルに分類されます。

#Chunkモデル
読み込み(ItemReader)、加工(ItemProcessor)、書き込み(ItemWriter)によって構成されるステップモデル。読み込み、加工、書き込みの順でStepが構成され、必ずそれぞれの処理が実行されます（例えば読み込み、加工だけを行うようなStepにはできません）。実装者はそれぞれの処理内容を記述する必要があります。予めSpring Batch側でインタフェースが提供されているので、実装者はそれを実装してそれぞれの処理内容を書いていきます。

#Taskletモデル
Chunkモデルとは違い、特に処理の流れが決まっておらず、自由に処理を記述できるステップモデル。何か単一処理を行う場合はこちらを利用します。こちらもSpring Batch側でインタフェースが提供されているので、実装者はインタフェースを実装して処理内容を書いていきます。
＃JobRepository
JobやStepの実行状況や実行結果を保存しておくところ。一般的にRDBなどのストレージで永続化させます。

#JobLauncher
JobLauncherは記載していなかったけどどこで呼んでいるのか？
概念の説明のところでJobはJobLauncher#runメソッドの呼び出しによって実行されると記載しましたが、サンプルアプリケーションではJobLauncherの記載は特にしませんでした。ではどこで呼び出しているのでしょうか。

その答えはSpring BootのJobLauncherCommandLineRunnerクラスにあります。このクラスはCommandLineRunnerインタフェースを実装しているため、Spring Boot起動時にrunメソッドが呼ばれます。そのrunメソッドをたどってみると、executeメソッドでJobLauncher#runメソッドを呼び出していることが分かります。


# spring.batch.job.names=${job.name:NONE}
全体実行を行わない

java -jar Spring_Batch_example-0.0.1-SNAPSHOT.jar --job.name=importUserJob