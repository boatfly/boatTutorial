package com.boatfly.spark.examples.sql.udf

import com.boatfly.spark.examples.sql.Book
import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession, TypedColumn}

/**
 * 自定义聚合函数(强类型)
 */
object MyAggregateUDFClass {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("sparksql_demo_rdd_df_ds")
    val spark: SparkSession = SparkSession.builder().config(conf).getOrCreate()

    //创建rdd
    val rdd1: RDD[(Int, String, Int)] = spark.sparkContext.makeRDD(Array((1, "spark", 75), (2, "scala", 60), (3, "ethereum", 100)))

    //在进行rdd df ds之间进行转换之前，需要导入隐式转换规则：

    import spark.implicits._
    //创建自定义聚合函数
    val uadf = new MyAgeAvgClassFunction

    //将聚合函数转换为查询列
    val avgcol: TypedColumn[BookBean, Double] = uadf.toColumn.name("avgPrice")

    val bookDS: Dataset[BookBean] = rdd1.map {
      case (id, name, price) => {
        BookBean(id, name, price)
      }
    }.toDS()
    //应用自定义聚合函数
    bookDS.select(avgcol).show() //DSL风格

    spark.stop()
  }
}