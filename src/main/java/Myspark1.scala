import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types.{DoubleType, StringType, StructField, StructType}
object Myspark1 {  
  case class Employee(empno:String, ename:String, designation:String)
def main(args: Array[String]) = {      
    val conf = new SparkConf().setAppName("Mysparkwordcount").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val spark =new SQLContext(sc)
    println("test")
    var ReadRdd=sc.textFile("D:\\csv_sample\\cpbss_stat.csv")
    
   //val reduceRDD=ReadRdd.flatMap(x => x.split(",")).map(x => x).filter(f => f.startsWith("h",1)).map(a => (a,1)).coalesce(1).reduceByKey(_+_)
   var patternMatchRDD=ReadRdd.map(line=> line.split(",")).map{x =>Employee(x(0),x(1),x(2))}
  // patternMatchRDD.foreach(println) ;
}
}