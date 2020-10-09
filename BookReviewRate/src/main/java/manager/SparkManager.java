package manager;

import io.reactivex.schedulers.Schedulers;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import repository.SparkRepository;
import util.FileUtil;
import config.SparkConfigConstants;

/**
 * 
 * Class {@link SparkManager} is a manager class which is used to perform data streaming loading. 
 * It will perform data streaming from three different data source directories ("/datasource/users", 
 * "/datasource/books", and "/datasource/bookratings") and generates and output files into another 
 * three different outpu directories ("/sparkoutoput/users", "/sparkoutput/books", and "/sparkoutput/bookratings")
 * 
 * @author Dora
 */
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

	/**
	 * This method is used to perform data streaming and output files into "sparkoutput" directory as 
	 * mentioned in class document above. 
	 */
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
