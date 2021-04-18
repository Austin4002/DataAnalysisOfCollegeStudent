package com.ngx.boot.algorithm.SparkSqlTest

import java.util.Properties

import org.apache.ibatis.reflection.ExceptionUtil
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

/**
 *
 * @author : 朱坤
 * @date : ${DATA}  
*/object scalatest {


  /**
   * 从MySql数据库中获取DateFrame
   *
   * @param
   * @param
   * @return DateFrame
   */


//  def getDFFromMysql(sql: String): DataFrame = {
//    //println(s"url:${mySqlConfig.url} user:${mySqlConfig.user} sql: ${sql}")
//    val spark: SparkSession = SparkSession
//      .builder()
//      .master("spark://spark1:7077")
//      .appName("hive")
//      .enableHiveSupport()
//      .getOrCreate()
//    spark.read.format("jdbc").option("url", "jdbc:mysql://localhost:3306")
//      .option("user", "root")
//      .option("password", "123456")
//      .option("driver", "com.mysql.jdbc.Driver")
//      .option("dbtable", sql).load()
//  }

def main(args: Array[String]): Unit = {


  val spark: SparkSession = SparkSession
    .builder()
    .master("spark://spark1:7077")
    .appName("hive")
    .enableHiveSupport()
    .getOrCreate()

  //执行sql
  val result = spark.sql("select * from analysisdata.stu_info")

  //将结果保存到mysql中
  val props = new Properties()
  props.setProperty("user", "root")
  props.setProperty("password", "1234")

  result.write.mode("append").jdbc("jdbc:mysql://spark1:3306/analysisdata", "emp", props)

  //停止Spark
  spark.stop()


}




  //2. 将Spark DataFrame 写入MySQL数据库表

//  /**
//   * 将结果写入Mysql
//   * @param df        DataFrame
//   * @param mode      SaveMode
//   * @param tableName SaveMode
//   */
//  def writeIntoMySql(df: DataFrame, mode: SaveMode, tableName: String): Unit = {
//    mode match {
//      case SaveMode.Append => appendDataIntoMysql(df, tableName);
//      case SaveMode.Overwrite => overwriteMysqlData(df, tableName);
//      case _ => throw new Exception("目前只支持Append及Overwrite！")
//    }
//  }


//  /**
//   * 将数据集插入Mysql表
//   *
//   * @param df             DataFrame
//   * @param mysqlTableName 表名：database_name.table_name
//   * @return
//   */
//  def appendDataIntoMysql(df: DataFrame, mysqlTableName: String) = {
//    df.write.mode(SaveMode.Append).jdbc(mySqlConfig.url, mysqlTableName, getMysqlProp)
//  }
//
//
//  /**
//   * 将数据集插入Mysql表
//   *
//   * @param df             DataFrame
//   * @param mysqlTableName 表名：database_name.table_name
//   * @return
//   */
//  def overwriteMysqlData(df: DataFrame, mysqlTableName: String) = {
//    //先清除Mysql表中数据
//    truncateMysqlTable(mysqlTableName)
//    //再往表中追加数据
//    df.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306", mysqlTableName, getMysqlProp)
//  }
//
//
//  /**
//   * 删除数据表
//   *
//   * @param mysqlTableName
//   * @return
//   */
//  def truncateMysqlTable(mysqlTableName: String): Boolean = {
//    val conn = MySQLPoolManager.getMysqlManager.getConnection //从连接池中获取一个连接
//    val preparedStatement = conn.createStatement()
//    try {
//      preparedStatement.execute(s"truncate table $mysqlTableName")
//    } catch {
//      case e: Exception =>
//        //println(s"mysql truncateMysqlTable error:${ExceptionUtil.getExceptionStack(e)}")
//        false
//    } finally {
//      preparedStatement.close()
//      conn.close()
//    }
//
//  }
//
//  /**
//   *  删除表中的数据
//   * @param mysqlTableName
//   * @param condition
//   * @return
//   */
//  def deleteMysqlTableData(mysqlTableName: String, condition: String): Boolean = {
//    val conn = MySQLPoolManager.getMysqlManager.getConnection //从连接池中获取一个连接
//    val preparedStatement = conn.createStatement()
//    try {
//      preparedStatement.execute(s"delete from $mysqlTableName where $condition")
//    } catch {
//      case e: Exception =>
//        println(s"mysql deleteMysqlTable error:${ExceptionUtil.getExceptionStack(e)}")
//        false
//    } finally {
//      preparedStatement.close()
//      conn.close()
//    }
//  }






}
