package utils;

public class IOUtils {
	public static void closeQuietly(AutoCloseable resource) {
		try {
			if(resource != null)
				resource.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
