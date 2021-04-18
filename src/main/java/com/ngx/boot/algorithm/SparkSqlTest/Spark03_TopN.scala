package com.ngx.boot.algorithm.SparkSqlTest

/**
 *
 * @author : 朱坤
 * @date : ${DATA}  
*/
import java.text.DecimalFormat
import org.apache.spark.SparkConf
import org.apache.spark.sql.expressions.{MutableAggregationBuffer, UserDefinedAggregateFunction}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DataType, LongType, MapType, StringType, StructField, StructType}

object Spark03_TopN {
  def main(args: Array[String]): Unit = {
    //创建配置文件对象
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("SparkSQL01_MySQL")
    //创建SparkSession对象
    val spark: SparkSession = SparkSession.builder().enableHiveSupport().config(conf).getOrCreate()

    //选择Hive库
    spark.sql("use test")
    //注册自定义函数
    spark.udf.register("city_remark",new CityClickUDAF)
    //--1.1从用户行为表中，查询所有点击记录，并和city_info,product_info进行连接
    //    spark.sql("select *from product_info").show()
    spark.sql(
      """
        |select
        |    c.*,
        |    p.product_name
        |from
        |    user_visit_action a
        |join
        |    city_info c
        |on
        |    a.city_id = c.city_id
        |join
        |    product_info p
        |on
        |    a.click_product_id = p.product_id
        |where
        |    a.click_product_id != -1
      """.stripMargin).createOrReplaceTempView("t1")
    //--1.2按照地区和商品的名称进行分组，统计出每个地区每个商品的总点击数
    spark.sql(
      """
        |select
        |    t1.area,
        |    t1.product_name,
        |    count(*) as product_click_count,
        |    city_remark(t1.city_name)
        |from
        |    t1
        |group by t1.area,t1.product_name
      """.stripMargin).createOrReplaceTempView("t2")
    //--1.3针对每个地区，对商品点击数进行降序排序
    spark.sql(
      """
        |select
        |   t2.*,
        |   row_number() over(partition by t2.area order by t2.product_click_count desc) cn
        |from
        |    t2
      """.stripMargin).createOrReplaceTempView("t3")

    //1.4取当前地区的前3名
    spark.sql(
      """
        |select
        |    *
        |from
        |    t3
        |where t3.cn <= 3
      """.stripMargin).show(false)
    //释放资源
    spark.stop()
  }

}
//自定义一个UDAF聚合函数，完成城市点击量统计
class CityClickUDAF extends UserDefinedAggregateFunction{

  //输入数据类型
  override def inputSchema: StructType = {
    StructType(Array(StructField("city_name",StringType)))
  }

  //缓存的数据类型  用Map缓存城市以及该城市点击数 ：北京->2,天津->3           总的点击量Long：  北京2 + 天津3 = 5
  override def bufferSchema: StructType = {
    StructType(Array(StructField("city_count",MapType(StringType,LongType)),StructField("total_count",LongType)))
  }

  //输出的数据类型   北京21.2%，天津13.2%，其他65.6%
  override def dataType: DataType = StringType

  //稳定性
  override def deterministic: Boolean = false

  //为缓存数据进行初始化
  override def initialize(buffer: MutableAggregationBuffer): Unit = {
    buffer(0) = Map[String,Long]()
    buffer(1) = 0L
  }

  //对缓存数据进行更新
  override def update(buffer: MutableAggregationBuffer, input: Row): Unit = {
    //从输入的数据中，获取城市的名称
    val cityName: String = input.getString(0)
    //从缓存中获取存放城市点击量的Map集合
    val map: Map[String, Long] = buffer.getAs[Map[String,Long]](0)
    //城市点击量 + 1
    buffer(0) = map + (cityName -> (map.getOrElse(cityName,0L) + 1L))
    //总点击量 + 1
    buffer(1) = buffer.getAs[Long](1) + 1L
  }

  //分区间的缓存合并
  override def merge(buffer1: MutableAggregationBuffer, buffer2: Row): Unit = {
    //获取每一个节点城市点击缓存Map
    val map1: Map[String, Long] = buffer1.getAs[Map[String,Long]](0)
    val map2: Map[String, Long] = buffer2.getAs[Map[String,Long]](0)

    //合并两个节点上的城市点击
    buffer1(0) = map1.foldLeft(map2){
      case (mm,(k,v))=>{
        mm + (k->(mm.getOrElse(k,0L) + v))
      }
    }

    //合并两个节点上的总点击数
    buffer1(1) = buffer1.getLong(1) + buffer2.getLong(1)
  }

  //得到最终的输出效果  北京21.2%，天津13.2%，其他65.6%
  override def evaluate(buffer: Row): Any = {
    //从缓存中获取数据
    val cityCountMap: Map[String, Long] = buffer.getAs[Map[String,Long]](0)
    val totalCount: Long = buffer.getAs[Long](1)

    //对Map集合中城市点击记录进行降序排序，取前2个
    val sortList: List[(String, Long)] = cityCountMap.toList.sortBy(-_._2).take(2)
    //计算排名前2的点击率
    var citysRatio: List[CityRemark] = sortList.map {
      case (cityName, count) => {
        CityRemark(cityName, count.toDouble / totalCount)
      }
    }
    //如果城市的个数超过2个，那么其它情况的处理
    if(cityCountMap.size > 2){
      citysRatio = citysRatio :+ CityRemark("其它",citysRatio.foldLeft(1D)(_ - _.cityRatio))
    }

    //    Array(1,2,3).foldRight()
    citysRatio.mkString(",")
  }
}

case class CityRemark(cityName: String, cityRatio: Double) {
  val formatter = new DecimalFormat("0.00%")
  override def toString: String = s"$cityName:${formatter.format(cityRatio)}"
}

