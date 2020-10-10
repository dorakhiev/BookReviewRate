package repository;

import io.reactivex.Observable;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import repository.file.FileSparkDataSource;

/**
 * 
 * Class {@link SparkRepository} is used to handle all the business logic within
 * this project. It is used to perform loading and saving data from data source
 * into output data source directories.
 * 
 * @author Dora
 *
 */
public class SparkRepository {

	private FileSparkDataSource fileSparkDataSource;

	public SparkRepository(JavaSparkContext javaSparkContext,
			JavaStreamingContext javaStreamingContext) {
		this.fileSparkDataSource = new FileSparkDataSource(javaSparkContext,
				javaStreamingContext);
	}

	/**
	 * This method is used to load or request data from directory path as
	 * specified in parameter inputDirectoryPath.
	 * 
	 * @param inputDirectoryPath
	 *            - input data source directory.
	 * 
	 * @return {@link Observable<JavaRDD<String>>}
	 */
	public Observable<JavaRDD<String>> obsRequestData(String inputDirectoryPath) {
		return Observable.create(emitter -> {
			try {
				JavaRDD<String> data = requestData(inputDirectoryPath).map(
						line -> line.replace("\"", ""));
				emitter.onNext(data);
			} catch (RuntimeException e) {
				e.printStackTrace();
				emitter.onError(e);
			}
		});
	}

	/**
	 * This method is used to load or request data from directory path as
	 * specified in parameter inputDirectoryPath.
	 * 
	 * @param inputDirectoryPath
	 *            - input data source directory.
	 * 
	 * @return {@link Observable<JavaDStream<String>>}
	 */
	public Observable<JavaDStream<String>> obsRequestDStreamData(
			String inputDirectoryPath) {
		return Observable
				.create(emitter -> {
					try {
						JavaDStream<String> data = fileSparkDataSource
								.loadJavaDStream(inputDirectoryPath).map(
										line -> line.replace("\"", ""));
						emitter.onNext(data);
					} catch (RuntimeException e) {
						e.printStackTrace();
						emitter.onError(e);
					}
				});
	}

	/**
	 * This method is used to save or request saving from input data stream into
	 * output directory as specified via parameter outputFileDirectory.
	 * 
	 * @param data
	 *            - input data stream as {@link JavaRDD<String>} which is used
	 *            to write into output directory file.
	 * @param outputFileDirectory
	 *            - output data source directory path.
	 * 
	 * @return {@link Observable<Boolean>}
	 */
	public Observable<Boolean> obsSaveData(JavaRDD<String> data,
			String outputFileDirectory) {
		return Observable.create(emitter -> {
			try {
				saveData(data, outputFileDirectory);
				emitter.onNext(true);
			} catch (RuntimeException e) {
				e.printStackTrace();
				emitter.onError(e);
			}
		});
	}

	/**
	 * This method is used to save or request saving from input data stream into
	 * output directory as specified via parameter outputFileDirectory.
	 * 
	 * @param data
	 *            - input data stream as {@link JavaDStream<String>} which is
	 *            used to write into output directory file.
	 * @param outputFileDirectory
	 *            - output data source directory path.
	 * 
	 * @return {@link Observable<Boolean>}
	 */
	public Observable<Boolean> obsSaveDStreamData(JavaDStream<String> data,
			String outputFileDirectory) {
		return Observable.create(emitter -> {
			try {
				fileSparkDataSource.saveDStream(data, outputFileDirectory);
				emitter.onNext(true);
			} catch (Exception e) {
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
