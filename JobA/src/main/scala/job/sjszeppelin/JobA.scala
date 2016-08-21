package job.sjszeppelin

import com.typesafe.config.Config
import org.apache.spark.sql.SQLContext
import spark.jobserver.{SparkJobValid, SparkJobValidation, SparkSqlJob}

/**
  * A job that accepts a SQLContext, as opposed to the regular SparkContext.
  * Just initializes some data into tables.
  */
object JobA extends SparkSqlJob {

  /**
    * Properties for jdbc request
    * jdbc:oracle:thin:RR_PM/a@fgr-lgrrtdb209:1521/LONGA
    */
  val conenctionURL = s"jdbc:oracle:thin:@$rfoDb"
  val rfoDb = ""
  val prop = new java.util.Properties
  prop.setProperty("user","")
  prop.setProperty("password","")
  prop.setProperty("driver","oracle.jdbc.driver.OracleDriver")


  /**
    * Validates a job
    * @param sc is the Spark Context
    * @param config is the Configuration in the existing SQL Context
    * @return job Validation
    */
  override def validate(sc: SQLContext, config: Config): SparkJobValidation = {
    SparkJobValid
  }

  /**
    * runJob contains the implementation of the Job
    * @param sc is the Spark Context
    * @param config is the Configuration in the existing SQL Context
    * @return two temporary tables from HDFS cluster and Jdbc Connection
    */
  override def runJob(sc: SQLContext, config: Config): Any = {

    //Creates a temporary table from Jdbc connection
    val dfJdbc = sc.read.jdbc(conenctionURL,"rep_framework",prop)
    dfJdbc.registerTempTable("DFJdbc")

    //Creates a temporary table from cluster HDFS
    val dfHdfs = sc.read
      .format("com.databricks.spark.csv") //Parsing CSV file
      .option("header", "true") // Use first line of all files as header
      .option("inferSchema", "true") // Automatically infer data types
      .option("delimiter", ",")
      .load("")
      .registerTempTable("DFHdfs")
  }

  def main(args: Array[String]): Unit = {
  }
}
