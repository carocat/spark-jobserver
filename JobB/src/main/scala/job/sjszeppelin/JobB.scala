package job.sjszeppelin

import com.typesafe.config.Config
import org.apache.spark.sql.SQLContext
import spark.jobserver._

/**
  * Run a second job
  */
object JobB extends SparkSqlJob {

  /**
    * Validates a job
    *
    * @param sc is the Spark Context
    * @param config is the Configuration in the existing SQL Context
    * @return job Validation
    */
  override def validate(sc: SQLContext, config: Config): SparkJobValidation = {
    SparkJobValid
  }

  /**
    * runJob contains the implementation of the Job
    *
    * @param sc is the Spark Context
    * @param config is the Configuration in the existing SQL Context
    * @return the first 1000 lines of a RDD in a Json format if sync is equal to true
    */
  override def runJob(sc: SQLContext, config: Config): Any = {
    val DFCheck = sc.sql("select * from DFHdfs as t1 INNER JOIN DFJdbc as t2 on" +
      " t1.<ColumnName> = t2.<ColumnName>")
    DFCheck.toJSON.take(1000)
  }
}