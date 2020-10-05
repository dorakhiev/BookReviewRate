package manager;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import repository.SparkRepository;
import util.FileUtil;
import config.SparkConfigConstants;

public class SparkManager {

	private SparkConf sparkConfig;
	private JavaSparkContext javaSparkContext;
	private SparkRepository sparkRepository;

	public SparkManager() {
		sparkConfig = new SparkConf().setAppName(SparkConfigConstants.APP_NAME)
				.setMaster(SparkConfigConstants.MASTER);
		javaSparkContext = new JavaSparkContext(sparkConfig);
		sparkRepository = new SparkRepository(javaSparkContext);
	}

	public void loadDataFromLocalFiles() {

		System.out.println("Sparkoutput directory is deleted " + FileUtil.removeDirectory("sparkoutput"));

		sparkRepository
				.obsRequestData("datasource/users")
				.flatMap(
						data -> sparkRepository.obsSaveData(data,
								"sparkoutput/users")).subscribe(status -> {
					System.out.println("User Data is saved " + status);
				}, throwable -> {
					System.out.println(throwable.getMessage());
				});

		sparkRepository
				.obsRequestData("datasource/books")
				.flatMap(
						data -> sparkRepository.obsSaveData(data,
								"sparkoutput/books")).subscribe(status -> {
					System.out.println("Book Data is saved " + status);
				}, throwable -> {
					System.out.println(throwable.getMessage());
				});

		sparkRepository
				.obsRequestData("datasource/bookratings")
				.flatMap(
						data -> sparkRepository.obsSaveData(data,
								"sparkoutput/bookratings"))
				.subscribe(status -> {
					System.out.println("User Rating Data is saved " + status);
				}, throwable -> {
					System.out.println(throwable.getMessage());
				});
	}

}
