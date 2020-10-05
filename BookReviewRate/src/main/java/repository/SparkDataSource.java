package repository;

import org.apache.spark.api.java.JavaRDD;

public interface SparkDataSource {
	
	JavaRDD<String> loadJavaDataStream(String inputDirectoryPath);
	
	void saveTextFile(JavaRDD<String> data, String outputFileDirectory);
	
}
