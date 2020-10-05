import manager.SparkManager;

public class UserBookRatingMain {

	public static void main(String[] args) {
		SparkManager sparkManager = new SparkManager();
		sparkManager.loadDataFromLocalFiles();
	}

}
