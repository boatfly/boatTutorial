package com.boatfly.spark.examples.streaming.state

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 有状态的数据转换
 */
object WithState {
  def main(args: Array[String]): Unit = {
    //使用spark streaming实现word count

    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount-streaming")

    //实时数据分析的环境对象，采集周期，以指定的时间为周期采集实时数据
    val streamingContext = new StreamingContext(conf, Seconds(3))

    //从kafka中采集数据
//    val kafkaDStream: ReceiverInputDStream[(String, String)] = KafkaUtils.createStream(
//      streamingContext,
//      "localhost:2181",
//      "groupname",
//      Map("mytopic" -> 3)
//    )
val kafkaDStream: ReceiverInputDStream[(String, String)] = null

    //将采集的数据进行分解（扁平化）
    val wordDStream: DStream[String] = kafkaDStream.flatMap(t => t._2.split(" "))

    //将数据进行结构的转换方便统计分析
    val wordMapDStream: DStream[(String, Int)] = wordDStream.map((_, 1))

    //所谓状态的更新，就是保存状态到检查点
    streamingContext.sparkContext.setCheckpointDir("checkpoint")

    //将转换结构后的数据进行聚合处理
    val word2sumDStream: DStream[(String, Int)] = wordMapDStream.updateStateByKey {
      case (seq, buffer) => {
        val sum = buffer.getOrElse(0) + seq.sum
        Option(sum)
      }
    }

    //将结构打印
    word2sumDStream.print()

    //启动采集器
    streamingContext.start()

    //Driver等待采集器的执行
    streamingContext.awaitTermination()
  }
}
