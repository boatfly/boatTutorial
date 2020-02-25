package com.boatfly.spark.streaminginaction.realtime.app

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat
import java.util

import com.boatfly.spark.streaminginaction.data.bean.AdsInfo
import com.boatfly.spark.streaminginaction.util.RedisUtil
import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{Dataset, ForeachWriter, Row, SparkSession}
import redis.clients.jedis.Jedis

/**
 *
 */
object Blacklist {

  def statBlacklist(spark: SparkSession, adsinfoDS: Dataset[AdsInfo]): Dataset[AdsInfo] = {
    //先过滤掉黑名单用户的点击
    import spark.implicits._ //非常重要，隐式转换，mapPartitions就不需要再传入encoder参数了
    val filterAdsInfoDS: Dataset[AdsInfo] = adsinfoDS.mapPartitions(it => {
      val adInfolist: List[AdsInfo] = it.toList
      val client: Jedis = RedisUtil.getJedisClient
      val blacklist: util.Set[String] = client.smembers(s"blacklist${adInfolist(0).dayString}")
      adInfolist.filter(adinfo=>{
        !blacklist.contains(adinfo.userId)
      }).iterator
    })
    //重新统计黑名单，已经加入黑名单的就不会从新计算
    filterAdsInfoDS.createOrReplaceTempView("adsinfo")
    spark.sql(
      """
        |select
        |dayString,userId
        |from adsinfo
        |group by dayString,userId,adsId
        |having count(*)>10000
        |""".stripMargin)
      .writeStream
      //      .format("console")
      .outputMode("update")
      .foreach(new ForeachWriter[Row] {
        var client: Jedis = _

        //建立连接
        override def open(partitionId: Long, epochId: Long): Boolean = {
          client = RedisUtil.getJedisClient
          client != null && client.isConnected
        }

        //写出数据
        override def process(value: Row): Unit = {
          val daystr: String = value.getString(0)
          val userid: String = value.getString(1)
          client.sadd(s"blacklist:$daystr", userid)
        }

        override def close(errorOrNull: Throwable): Unit = {
          if (client != null && client.isConnected) {
            client.close()
          }
        }
      })
      .trigger(Trigger.ProcessingTime(10000))
      .start()
      //.awaitTermination()

    filterAdsInfoDS
  }
}
