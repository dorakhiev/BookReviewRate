package repository;

import io.reactivex.Observable;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import repository.file.FileSparkDataSource;

public class SparkRepository {

	private FileSparkDataSource fileSparkDataSource;

	public SparkRepository(JavaSparkContext javaSparkContext) {
		this.fileSparkDataSource = new FileSparkDataSource(javaSparkContext);
	}
	
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
