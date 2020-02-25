package com.boatfly.spark.streaminginaction.realtime.structured

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import com.boatfly.spark.streaminginaction.data.bean.AdsInfo
import com.boatfly.spark.streaminginaction.realtime.app.{AdsClickCount, Blacklist}
import org.apache.spark.sql.{Dataset, SparkSession}

/**
 *
 */
object RealTimeApp {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[*]").appName("ad-mock-ss").getOrCreate()
    import spark.implicits._

    val sdf = new SimpleDateFormat("yyyy-MM-dd")
    val hmsdf = new SimpleDateFormat("HH:mm")

    val adsinfoDS: Dataset[AdsInfo] = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "192.168.1.5:9092")
      .option("subscribe", "ads_log")
      .option("truncate","true")
      .load
      .select("value")
      .as[String]
      .map(line => {
        //timestamp,area,city,userid,adid
        val arr: Array[String] = line.split(",")
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

    //需求1.黑名单
    val filterAdsInfoDS: Dataset[AdsInfo] = Blacklist.statBlacklist(spark, adsinfoDS)

    //需求2.每天按地区广告点击量统计
    AdsClickCount.statAdsClickCount(spark,filterAdsInfoDS)

//    adsinfoDS.writeStream
//      .format("console")
//      .outputMode("update")
//      .start()
//      .awaitTermination()

  }
}
