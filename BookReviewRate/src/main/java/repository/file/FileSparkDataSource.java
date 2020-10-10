package repository.file;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import repository.SparkDataSource;

/**
 * 
 * Class {@link FileSparkDataSource} is used to handle file input data source. 
 * 
 * @author Dora
 *
 */
public class FileSparkDataSource implements SparkDataSource {
	
	private JavaSparkContext javaSparkContext;
	private JavaStreamingContext javaStreamingContext;
	
	public FileSparkDataSource(JavaSparkContext javaSparkContext, JavaStreamingContext javaStreamingContext) {
		this.javaSparkContext = javaSparkContext;
		this.javaStreamingContext = javaStreamingContext;
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

	@Override
	public JavaDStream<String> loadJavaDStream(String inputDirectoryPath) {
		return javaStreamingContext.textFileStream(inputDirectoryPath);
	}

	@Override
	public void saveDStream(JavaDStream<String> data, String outputFileDirectory) {
		data.foreachRDD(rdd -> {
			saveTextFile(rdd, outputFileDirectory);
		});
	}
	
}
