package com.boatfly.spark.examples.structuredstreaming.wcount

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * StructuredStreaming word count
 */
object Wcount {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[*]").appName("ss-wordcount").getOrCreate()
    import spark.implicits._

    //1.从数据源加载数据
    val lines: DataFrame = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", "9999")
      .load

    //val wordcount: DataFrame = lines.as[String].flatMap(_.split(" ")).groupBy("value").count()
    lines.as[String].flatMap(_.split(" ")).createOrReplaceTempView("word")
    val wordcount: DataFrame = spark.sql(
      """
        |select
        |*,
        |count(*) count
        |from word
        |group by value
        |""".stripMargin
    )

    val result: StreamingQuery = wordcount.writeStream
      .format("console")
      .outputMode("update") //complete append update
      .start

    result.awaitTermination()

    spark.close()
  }
}
