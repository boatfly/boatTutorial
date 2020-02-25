package com.boatfly.spark.rdd.operator.v

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Map {
  def main(args: Array[String]): Unit = {
    //local模式
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val listRDD: RDD[Int] = sc.makeRDD(1 to 10) // sc.makeRDD(List(1, 2, 3, 4))

    //val mapRDD: RDD[Int] = listRDD.map(x => x * 2) // map(_*2)
    //mapRDD.collect().foreach(println)

    //mapPartitions，可以对RDD中所有的分区进行遍历
    val value = listRDD.mapPartitions(datas => {
      datas.map(data => data * 2)
    })

    val value1 = listRDD.mapPartitionsWithIndex {
      case (num, datas) => {
        datas.map((_, "分区号：" + num))
      }
    }

    value1.collect().foreach(println)
  }
}
