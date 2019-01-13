from pyspark.sql import SparkSession


if __name__ == "__main__":
    spark = SparkSession\
        .builder\
        .appName("PythonSort")\
        .getOrCreate()
    
    lines = spark.read.text('./test_data/nums.txt').rdd.map(lambda r: r[0])
    sortedCount = lines.flatMap(lambda x: x.split(' ')) \
        .map(lambda x: (int(x), 1)) \
        .sortByKey()
    # This is just a demo on how to bring all the sorted data back to a single node.
    # In reality, we wouldn't want to collect all the data to the driver node.
    sort_data=''
    output = sortedCount.collect()
    for (num, unitcount) in output:
        sort_data+=str(num)+','
    print sort_data[:-1]

    spark.stop()
