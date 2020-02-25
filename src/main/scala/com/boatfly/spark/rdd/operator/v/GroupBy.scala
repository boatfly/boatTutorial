package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object GroupBy {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

    val gbRDD: RDD[(Int, Iterable[Int])] = listRDD.groupBy(_ % 2) //以（_%2）的结果进行分组

    gbRDD.collect().foreach(println)

  }
}
