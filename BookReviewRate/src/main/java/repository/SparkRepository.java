package repository;

import io.reactivex.Observable;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import repository.file.FileSparkDataSource;

/**
 * 
 * Class {@link SparkRepository} is used to handle all the business logic within this project. 
 * It is used to perform loading and saving data from data source into output data source directories. 
 * 
 * @author Dora
 *
 */
public class SparkRepository {

	private FileSparkDataSource fileSparkDataSource;

	public SparkRepository(JavaSparkContext javaSparkContext) {
		this.fileSparkDataSource = new FileSparkDataSource(javaSparkContext);
	}

	/**
	 * This method is used to load or request data from directory path as specified in parameter
	 * inputDirectoryPath. 
	 * 
	 * @param inputDirectoryPath - input data source directory.
	 *  
	 * @return {@link Observable<JavaRDD<String>>}
	 */
	public Observable<JavaRDD<String>> obsRequestData(String inputDirectoryPath) {
		return Observable.create(emitter -> {			
			try {
				JavaRDD<String> data = requestData(inputDirectoryPath).map(line -> line.replace("\"", ""));
				emitter.onNext(data);
			} catch(RuntimeException e) {
				e.printStackTrace();
				emitter.onError(e);
			}
		});
	}
	
	/**
	 * This method is used to save or request saving from input data stream into output directory 
	 * as specified via parameter outputFileDirectory. 
	 * 
	 * @param data - input data stream which is used to write into output directory file. 
	 * @param outputFileDirectory - output data source directory path. 
	 * 
	 * @return {@link Observable<Boolean>}
	 */
	public Observable<Boolean> obsSaveData(JavaRDD<String> data, String outputFileDirectory) {
		return Observable.create(emitter -> {
			try {
				saveData(data, outputFileDirectory);
				emitter.onNext(true);
			} catch(RuntimeException e) {
				e.printStackTrace();
				emitter.onError(e);
			}
		});
	}
	
	private JavaRDD<String> requestData(String inputDirectoryPath) {
		return fileSparkDataSource.loadJavaDataStream(inputDirectoryPath);
	}	
	
	private void saveData(JavaRDD<String> data, String outputFileDirectory) {
		fileSparkDataSource.saveTextFile(data, outputFileDirectory);
	}
}
