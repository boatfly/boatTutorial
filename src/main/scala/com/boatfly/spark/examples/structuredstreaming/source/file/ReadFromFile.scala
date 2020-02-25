package com.boatfly.spark.examples.structuredstreaming.source.file

import org.apache.spark.sql.streaming.Trigger
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.types.{IntegerType, StringType, StructField, StructType}

/**
 *
 */
object ReadFromFile {
  def main(args: Array[String]): Unit = {
    val spark: SparkSession = SparkSession.builder().master("local[*]").appName("ss-wordcount").getOrCreate()
    import spark.implicits._

    val structType: StructType = StructType(StructField("name", StringType) :: StructField("age", IntegerType) :: StructField("sex", StringType) :: Nil)
    val userDF: DataFrame = spark.readStream
      .format("csv")
      .schema(structType)
      .load("csv")
      .groupBy("sex")
      .sum("age")

    userDF.writeStream
      .format("console")
      .outputMode("update")
      .trigger(Trigger.ProcessingTime(2000))
      .start()
      .awaitTermination()

    //    userDF.createOrReplaceTempView("usercsv")
    //    spark.sql(
    //      """
    //        |select
    //        |*
    //        |from usercsv
    //        |""".stripMargin).stat.show()
  }
}
