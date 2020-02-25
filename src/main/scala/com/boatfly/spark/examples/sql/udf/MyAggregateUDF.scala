package com.boatfly.spark.examples.sql.udf

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 自定义聚合函数（弱类型）
 */
object MyAggregateUDF {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql_demo_rdd_df_ds")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //创建rdd
    val rdd1: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(Array((1, "spark", 75), (2, "scala", 60), (3, "ethereum", 100)))

    //在进行rdd df ds之间进行转换之前，需要导入隐式转换规则：

    import spark.implicits._
    //创建自定义聚合函数
    val uadf = new MyAgeAvgFunction
    //注册聚合函数
    spark.udf.register("priceAvg", uadf)

    val bookDF: DataFrame = rdd1.toDF("id", "name", "price")

    bookDF.createTempView("book")

    spark.sql("select priceAvg(price) as avg from book").show


    spark.stop()
  }
}