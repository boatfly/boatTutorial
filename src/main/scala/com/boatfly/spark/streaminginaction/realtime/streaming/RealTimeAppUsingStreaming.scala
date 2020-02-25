package com.boatfly.spark.streaminginaction.realtime.streaming

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import com.boatfly.spark.streaminginaction.data.bean.AdsInfo
import com.boatfly.spark.streaminginaction.util.KafkaUtil
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 *
 */
object RealTimeAppUsingStreaming {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount-streaming")

    //实时数据分析的环境对象，采集周期，以指定的时间为周期采集实时数据
    val streamingContext = new StreamingContext(conf, Seconds(3))

    val recordDStream: InputDStream[ConsumerRecord[String, String]] = KafkaUtil.getKafkaStream(streamingContext, "ads_log")

    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val hmsdf = new SimpleDateFormat("HH:mm")
    val adsinfoDStream: DStream[AdsInfo] = recordDStream.map(record => {
      val arr: Array[String] = record.value().split(",")
      val date = new Date(arr(0).toLong)
      AdsInfo(
        arr(0).toLong,
        new Timestamp(arr(0).toLong),
        sdf.format(date),
        hmsdf.format(date),
        arr(1),
        arr(2),
        arr(3),
        arr(4)
      )
    })

    //需求1.每天每地区热门广告top3


    streamingContext.start()
    streamingContext.awaitTermination()
  }

}
