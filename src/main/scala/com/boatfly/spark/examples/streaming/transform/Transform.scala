package com.boatfly.spark.examples.streaming.transform

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * 转换操作
 */
object Transform {
  def main(args: Array[String]): Unit = {

    //sparkstreaming中的窗口
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount-streaming")

    //实时数据分析的环境对象，采集周期，以指定的时间为周期采集实时数据
    val streamingContext = new StreamingContext(conf, Seconds(3))



    //启动采集器
    streamingContext.start()

    //Driver等待采集器的执行
    streamingContext.awaitTermination()
  }
}
