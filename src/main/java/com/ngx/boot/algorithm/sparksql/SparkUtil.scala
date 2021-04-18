package com.ngx.boot.algorithm.sparksql

/**
 *
 * @author : 朱坤
 * @date : ${DATA}  
*/

import java.text.SimpleDateFormat
import java.util.{Calendar, Date, Properties}

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}


object SparkUtil {
  /**
   * 获取sparkSession
   * @return
   */
  def getSparkSession: SparkSession = {
    val sparkSession = SparkSession.builder().master("spark://spark1:7077").getOrCreate()
    sparkSession
  }

  /**
   * 从Mysql中读表并创建临时表
   * @param sparkSession
   * @param table
   */
  def readFromMysql(sparkSession: SparkSession, table: String): DataFrame = {
    val username = getProperty("/jdbc.properties", "jdbc.username")
    val password = getProperty("/jdbc.properties", "jdbc.password")
    val url = getProperty("/jdbc.properties", "jdbc.url")
    val properties = new Properties()

    properties.setProperty("user", username)
    properties.setProperty("password", password)
    sparkSession.read.jdbc(url, table, properties)
  }

  /**
   * @param sparkSession sparkSession
   * @param table 可以直接传表名或者sql语句(sql语句需要加 as )
   * @return
   */
  def readFromMysql2(sparkSession: SparkSession, table: String): DataFrame = {
    val Array(username, password, url) = getProperties("/jdbc.properties", "jdbc.username", "jdbc.password", "jdbc.url")
    val jdbcDF = sparkSession.read
      .format("jdbc")
      .option("url", url)
      .option("dbtable", table)
      .option("user", username)
      .option("password", password)
      .load()
    jdbcDF
  }

  /**
   * 把数据以追加的方式存入Mysql
   *
   * @param df
   * @param table
   */
  def writeToMysql(df: DataFrame, table: String): Unit = {
    val username = getProperty("/jdbc.properties", "jdbc.username")
    val password = getProperty("/jdbc.properties", "jdbc.password")
    val url = getProperty("/jdbc.properties", "jdbc.url")
    val properties = new Properties()
    properties.setProperty("user", username)
    properties.setProperty("password", password)
    df.write.mode(SaveMode.Append).jdbc(url, table, properties)
  }

  /**
   * 获取配置文件
   *
   * @param fileName 配置文件路径
   * @param keyName  参数名
   * @return
   */
  def getProperty(fileName: String, keyName: String): String = {
    val properties = new Properties()
    val in = getClass.getResourceAsStream(fileName)
    properties.load(in)
    in.close()
    properties.getProperty(keyName)
  }

  /** 获取配置文件
   *
   * @param fileName 配置文件路径
   * @param keyNames 参数名
   * @return
   */
  def getProperties(fileName: String, keyNames: String*): Array[String] = {
    val properties = new Properties()
    val in = getClass.getResourceAsStream(fileName)
    properties.load(in)
    in.close()
    val result = new Array[String](keyNames.length)
    for (i <- 0 until keyNames.length) {
      result.update(i, properties.getProperty(keyNames(i)))
    }
    result
  }
}
