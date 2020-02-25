package com.boatfly.spark.streaminginaction.data.bean

import java.sql.Timestamp

case class AdsInfo(ts: Long, //数字型的时间戳
                   timestamp: Timestamp, //时间戳类型的时间戳
                   dayString: String, //2019-09-12
                   hmString: String, //10:25 时分
                   area: String, // 区域
                   city: String, // 城市
                   userId: String, // 用户id
                   adsId: String) // 广告id
