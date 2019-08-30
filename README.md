# learning-Spark

A repository containing simple examples on Spark. It contains various packages.
1) rdd
2) dataframe
3) streaming (Structured Streaming)

# rdd
This package contains various examples of Spark.
 - Factorial // How sparks help in computing factorial of very large number
 - Wordcount

# dataframe
This package contains different operations on dataframe. 
 - Basic operations like, creating dataframe, selection, renaming columns, adding new column (DataFrameExamples)
 - Filtering DataFrame (BooleanOperations)
 - Numeric Operation on DataFrame (NumberOperations)
 - String Operation on DataFrame (StringOperation)
 
# streaming
This package contains example Spark Structured Streaming.Currently. Example shows integration of kafka, Spark and Cassandra. Words are streamed into Kafka Topic, read as a stream from Kafka using Spark. Spark applies aggregation on words and count occurrence of each word. Eventually Spark writes data on cassandra in update mode. To receover from crash, checkpointing is used.  
 
 - DataStreamer // It streams words to kafka topic continously.
 - StructuredStreamingWordCount // It reads data from kafka, applies aggregation on words and write to cassandra.
 - CassandraForeachWriter // Writes data to cassandra.
 
