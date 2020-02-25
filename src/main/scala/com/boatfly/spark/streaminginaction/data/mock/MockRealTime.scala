package com.boatfly.spark.streaminginaction.data.mock

import java.util.Properties

import com.boatfly.spark.streaminginaction.data.bean.CityInfo
import com.boatfly.spark.streaminginaction.data.mock.util.{RandomNumUtil, RandomOptions}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import scala.collection.mutable.ArrayBuffer

/**
 * 模拟数据：
 * 1.随机生成一些数据
 * 2.写入到kafka中
 */
/**
 * 生成实时的模拟数据
 */
object MockRealTime {
  /*
  数据格式:
  timestamp area city userid adid
  某个时间点 某个地区 某个城市 某个用户 某个广告

  */
  def mockRealTimeData(): ArrayBuffer[String] = {
    // 存储模拟的实时数据
    val array = ArrayBuffer[String]()
    // 城市信息
    val randomOpts = RandomOptions(
      (CityInfo(1, "北京", "华北"), 30),
      (CityInfo(2, "上海", "华东"), 30),
      (CityInfo(3, "广州", "华南"), 10),
      (CityInfo(4, "深圳", "华南"), 20),
      (CityInfo(5, "杭州", "华中"), 10))
    (1 to 50).foreach {
      i => {
        val timestamp = System.currentTimeMillis()
        val cityInfo = randomOpts.getRandomOption()
        val area = cityInfo.area
        val city = cityInfo.city_name
        val userid = RandomNumUtil.randomInt(100, 105)
        val adid = RandomNumUtil.randomInt(1, 5)
        array += s"$timestamp,$area,$city,$userid,$adid"
      }
    }
    array
  }

  def createKafkaProducer: KafkaProducer[String, String] = {
    val props: Properties = new Properties
    // Kafka服务端的主机名和端口号
    props.put("bootstrap.servers", "192.168.1.5:9092")
    // key序列化
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    // value序列化
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    new KafkaProducer[String, String](props)
  }

  def main(args: Array[String]): Unit = {
    val topic = "ads_log" //kafka-topics.sh --create --topic ads_log --zookeeper zoo1:2181 --replication-factor 1 --partitions 1 //创建topic
                          //kafka-topics.sh --describe --zookeeper zoo1 --topic ads_log //查看topic信息
                          //kafka-console-consumer.sh --bootstrap-server kafka:9092 --from-beginning --topic ads_log //消费数据
                          //进入到容器内：docker exec -it kafka bash
    val producer: KafkaProducer[String, String] = createKafkaProducer
    while (true) {
      mockRealTimeData().foreach {
        msg => {
          producer.send(new ProducerRecord(topic, msg))
          Thread.sleep(100)
        }
      }
      Thread.sleep(1000)
    }
  }
}
