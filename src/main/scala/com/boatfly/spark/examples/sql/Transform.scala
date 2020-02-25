package com.boatfly.spark.examples.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{DataFrame, SparkSession}

object Transform {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql-demo")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val userDF: DataFrame = spark.read.json("in/user.json")

    //1.采用sql的方式访问数据 - session内view
//    userDF.createOrReplaceTempView("user")
//    spark.sql("select * from user").show

    //2.采用sql的方式访问数据 - session内global_view
    userDF.createGlobalTempView("user1")
    spark.newSession().sql("select * from global_temp.user1").show

    //释放资源
    spark.stop()
  }
}
