package com.boatfly.spark.examples.streaming.wcount

import org.apache.spark.streaming.dstream.{DStream, ReceiverInputDStream}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object Wcount {
  def main(args: Array[String]): Unit = {
    //使用spark streaming实现word count

    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount-streaming")

    //实时数据分析的环境对象，采集周期，以指定的时间为周期采集实时数据
    val streamingContext = new StreamingContext(conf, Seconds(3))

    //从指定端口采集数据 (nc -lk 9999)
    val socketLineDStream: ReceiverInputDStream[String] = streamingContext.socketTextStream("localhost", 9999)

    //将采集的数据进行分解（扁平化）
    val wordDStream: DStream[String] = socketLineDStream.flatMap(line => line.split(" "))

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
