package com.boatfly.spark.rdd.operator.v

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd.RDD

/**
 * 重新分区，并可以重新指定分区数，也有shuffle的过程（相比较于Coalesce，调用coalesce，设置shuflle=true），效率不高
 */
object RePartitions {
  def main(args: Array[String]): Unit = {
    //创建sparkconf对象，设定spark运行环境
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("wcount")

    //创建sparkcontext上下文对象
    val sc = new SparkContext(conf)

    // map 算子
    val listRDD: RDD[Int] = sc.makeRDD(1 to 16, 4)

    val repartitionRDD: RDD[Int] = listRDD.repartition(2)

    //repartitionRDD.collect().foreach(println)
    repartitionRDD.glom().collect().foreach(array=>{
      println(array.mkString(","))
    })
  }
}
