package com.boatfly.spark.examples.sql

import org.apache.spark.{SparkConf, sql}
import org.apache.spark.sql.{DataFrame, SparkSession}

object SparkSQLFromJson {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql-demo")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    val userDF: DataFrame = spark.read.json("in/user.json")
    userDF.show

    //释放资源
    spark.stop()
  }
}
