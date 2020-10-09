import manager.SparkManager;

/**
 * {@link UserBookRatingMain} is a main entry class for the execution of Spark data streaming. 
 * It will perform data streaming from folder "datasource" and generate separates three different 
 * folders in "bookratins", "books", and "users" and all these folders are staying inside directory
 * "sparkoutput" which is generated automatically by the code.
 * 
 * @author Dora
 */
public class UserBookRatingMain {

	/**
	 * Execution method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SparkManager sparkManager = new SparkManager();
		sparkManager.loadDataFromLocalFiles();
	}

}
