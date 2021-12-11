package driverFactoryPattern;

public class DriverManagerFactory {

	public static DriverManager getBrowserDriver(String browserName) {

		DriverManager driverManager;

		switch (browserName) {
		case "chrome_ui":
			driverManager = new ChromeDriverManager();
			break;
		case "chrome_headless":
			driverManager = new ChromeHeadlessDriverManager();
			break;
		case "firefox_ui":
			driverManager = new FirefoxDriverManager();
			break;
		case "firefox_headless":
			driverManager = new FirefoxHeadlessDriverManager();
			break;
		default:
			driverManager = new ChromeDriverManager();
			break;
		}

		return driverManager;

	}

}
