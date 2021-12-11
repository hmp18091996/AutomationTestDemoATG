package driverFactoryPattern;

import org.openqa.selenium.chrome.ChromeDriver;

public class FirefoxDriverManager extends DriverManager {

	@Override
	protected void createDriver() {
		String rootFolder = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", rootFolder + "\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
	}

}
