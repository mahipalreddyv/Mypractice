import org.apache.spark.sql._;
import org.apache.spark.sql.types._;
import org.apache.spark.{SparkConf, SparkContext}
object Readjson {
  
  def main(args : Array[String]): Unit = {
   var conf = new SparkConf().setAppName("Read json File").setMaster("local[*]")
   val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)
    import sqlContext.implicits._
    var schemaPath=args(0);
    var dataInputPath=args(1)
    var outPutPath=args(2)
    var fileformat=args(3)
    var readSchemaDF=sqlContext.read.json(schemaPath).schema    
    var readJsonDF=sqlContext.read.schema(readSchemaDF).json(dataInputPath)
    .where("events.beaconType=\"pageAdRequested\"")
       
    val adperfExpression= """events.client AS clients
  events.beaconType AS Beacontype
  events.beaconVersion AS beaconVersion
  events.data.milestones.indexExchangeRequested AS IndexExchangeRequested
  events.data.milestones.adRequested AS adRequested
  events.data.milestones.amazonA9Received AS amazonReceived
  events.data.milestones.indexExchangeReceived AS indexExchangeReceived
""".split("\n")
      
    var BeaconSelectExprDF=readJsonDF.selectExpr(adperfExpression:_*).coalesce(1)
    
  BeaconSelectExprDF.write.mode("overWrite").format(fileformat).save(outPutPath)
  
  }

}