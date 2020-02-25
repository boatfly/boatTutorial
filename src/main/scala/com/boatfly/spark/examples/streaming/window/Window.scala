package com.boatfly.spark.examples.streaming.window

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 窗口操作
 */
object Window {
  def main(args: Array[String]): Unit = {
    //scala 中的窗口
    val ints = List(1, 2, 3, 4, 5, 6, 7)
    //滑动窗口函数[窗口尺寸，步长]
    val lines: Iterator[List[Int]] = ints.sliding(3, 3)
    for (line <- lines) {
      println(line.mkString(","))
    }

    //sparkstreaming中的窗口
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
    //窗口大小为采集周期的整数倍，窗口滑动的步长也为采集周期的整数倍
    val windowDStream: DStream[(String, String)] = kafkaDStream.window(Seconds(9), Seconds(3)) //数据从少到多，再从多到少的一个过程。

    //将采集的数据进行分解（扁平化）
    val wordDStream: DStream[String] = windowDStream.flatMap(t => t._2.split(" "))

    //将数据进行结构的转换方便统计分析
    val wordMapDStream: DStream[(String, Int)] = wordDStream.map((_, 1))

    //将转换结构后的数据进行聚合处理
    val word2sumDStream: DStream[(String, Int)] = wordMapDStream.reduceByKey(_ + _)

    //将结构打印
    word2sumDStream.print()

    //启动采集器
    streamingContext.start()

    //Driver等待采集器的执行
    streamingContext.awaitTermination()
  }
}
