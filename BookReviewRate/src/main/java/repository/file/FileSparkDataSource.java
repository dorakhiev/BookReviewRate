package repository.file;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import repository.SparkDataSource;

public class FileSparkDataSource implements SparkDataSource {
	
	private JavaSparkContext javaSparkContext;
	
	public FileSparkDataSource(JavaSparkContext javaSparkContext) {
		this.javaSparkContext = javaSparkContext;
	}

	@Override
	public JavaRDD<String> loadJavaDataStream(String inputDirectoryPath) {
		JavaRDD<String> lines = javaSparkContext.textFile(inputDirectoryPath);
		return lines;
	}

	@Override
	public void saveTextFile(JavaRDD<String> data, String outputFileDirectory) {
		data.saveAsTextFile(outputFileDirectory);			
	}
	
}
