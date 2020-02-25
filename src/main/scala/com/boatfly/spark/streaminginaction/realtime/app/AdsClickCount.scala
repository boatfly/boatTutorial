package com.boatfly.spark.streaminginaction.realtime.app

import com.boatfly.spark.streaminginaction.data.bean.AdsInfo
import com.boatfly.spark.streaminginaction.util.RedisUtil
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}
import redis.clients.jedis.Jedis

/**
 * 每天按地区显示广告点击量
 */
object AdsClickCount {
  def statAdsClickCount(spark: SparkSession, filterAdsInfoDS: Dataset[AdsInfo]): Unit = {
    import spark.implicits._
//    val statDF: DataFrame = filterAdsInfoDS.groupBy("dayString", "area", "city", "adsId").count()
//    statDF.writeStream
//      //.format("console")
//      .outputMode("complete")
//      .foreachBatch((df, batchid) => {
//        df.persist()
//        if (df.count() > 0) {
//          df.foreachPartition(rowIt => {
//            val client: Jedis = RedisUtil.getJedisClient
//            var daystring: String = ""
//            val rmap: Map[String, String] = rowIt.map(row => {
//              daystring = row.getString(0)
//              val area: String = row.getString(1)
//              val city: String = row.getString(2)
//              val adsId: String = row.getString(3)
//              val count: String = row.getString(4)
//              (s"$area:$city:$adsId", count.toString)
//            }).toMap
//            import scala.collection.JavaConversions._
//            client.hmset(s"date:area:city:adsid:$daystring", rmap)
//            client.close()
//          })
//        }
//        df.unpersist()
//      })
//      .start()
//      .awaitTermination()

  }
}
