from pyspark.sql import SparkSession


spark = SparkSession.builder.appName("SparkDemo").getOrCreate()

data = [1, 2, 3, 4, 5]
distData = spark.sparkContext.parallelize(data)
print 'dist data count: '+str(distData.count())

trans_data=distData.map(lambda num: num*num)

output=''
result_data = trans_data.collect()
for num in result_data:
    output+=str(num)+','
print output[:-1]


spark.stop()