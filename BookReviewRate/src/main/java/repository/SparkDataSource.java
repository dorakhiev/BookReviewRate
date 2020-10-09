package repository;

import org.apache.spark.api.java.JavaRDD;

/**
 * 
 * An interface which defines all necessary methods which are used to perform static data
 * streaming from data source directory. 
 * 
 * @author Dora
 *
 */
public interface SparkDataSource {
	
	/**
	 * This method is used to load data source byte steam into Java Resilience Distributed DataSet(JavaRDD). 
	 * @param inputDirectoryPath
	 * 
	 * @return data stream as {@link JavaRDD<String>}
	 */
	JavaRDD<String> loadJavaDataStream(String inputDirectoryPath);
	
	/**
	 * This method is used to write data stream from JavaRDD into file base on the param outputFileDirectory. 
	 * 
	 * @param data - in input data stream as {@link JavaRDD<String>}
	 * @param outputFileDirectory - output file path which is used to write data on. 
	 */
	void saveTextFile(JavaRDD<String> data, String outputFileDirectory);
	
}
