package config;

/**
 * 
 * This class is used to wrap constants which are used within the project.
 * 
 * @author Dora
 */
public class SparkConfigConstants {

	/**
	 * Constant application name for Spark streaming.
	 */
	public static final String APP_NAME = "UserBookRating";
	
	/**
	 * The master url to connect to, "local" means run on local with one thread.
	 *  
	 * More detail {@link SparkConf#setMaster(master: String)}
	 */
	public static final String LOCAL_ONE_THREAD = "local";
	
	/**
	 * The master url to connect to, "local[4]" mean run on local with 4 cores.
	 * 
	 * More detail {@link SparkConf#setMaster(master: String)}
	 */
	public static final String LOCAL_4_CORE = "local[4]";
	
}
