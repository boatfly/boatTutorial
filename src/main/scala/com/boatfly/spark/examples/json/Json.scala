package com.boatfly.spark.examples.json

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD
import org.mortbay.util.ajax.JSON

//import scala.util.parsing.json.JSON



/**
 * 每一行为一个json
 */
object Json {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)
    val jsonRDD: RDD[String] = sc.textFile("in/user.json")

    //jsonRDD.map(JSON.parseFull)
  }
}
