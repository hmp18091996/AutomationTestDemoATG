package pageObject;

import org.openqa.selenium.WebDriver;

import pageUIs.HomePageUI;

public class PageGeneratorManager {

	public static HomePO getHomePage(WebDriver driver) {
		return new HomePO(driver);
	}

	public static listProductPO getSearchPage(WebDriver driver) {
		return new listProductPO(driver);
	}
	
	

}
