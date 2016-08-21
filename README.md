# Shared tables in a SparkSQL Context using Spark Job Server (spark 1.6)

```
Goal is to share a SqlContext between 2 applications. 
Application A creates some data that is stored in-memory.
Application B retrieves the data using the shared table.
Application extends SparkSqlJob, overriden methods takes a SQLContext as argument instead of a SparkContext.
```

## Send jars
```
curl --data-binary @JobA-1.0-SNAPSHOT.jar localhost:8090/jars/JobA
curl --data-binary @JobB-1.0-SNAPSHOT.jar localhost:8090/jars/JobB
```

## Creates a SQLContext
```
curl -d "" "localhost:8090/contexts/CTXSQL?context-factory=spark.jobserver.context.SQLContextFactory"
```

## Checks if SQLContext has been created, SQLContext name should appear in the list
```
curl -X GET "localhost:8090/contexts"
```

## Run Jobs
```
curl -d "" POST "localhost:8090/jobs?appName=JobA&classPath=job.sjszeppelin.JobA&context=CTXSQL&sync=true"
curl -d "" POST "localhost:8090/jobs?appName=JobB&classPath=job.sjszeppelin.JobB&context=CTXSQL&sync=true"
```

