package pageObject;

import org.openqa.selenium.WebDriver;

import commons.AbstractPageObject;
import pageUIs.HomePageUI;

public class HomePO extends AbstractPageObject {

	WebDriver driver;

	public HomePO(WebDriver driverLocal) {
		super(driverLocal);
		driver = driverLocal;
	}

	public void selectBooksItemInDepartmentDropdown(String textValue) {
		selectItemInDropdown(HomePageUI.DEPARTMENT_DROPDOWN, textValue);
	}

	public void inputToSearchTextbox(String textValue) {
		waitToElementVisible(HomePageUI.SEARCH_TEXTBOX);
		sendKeyToElement(HomePageUI.SEARCH_TEXTBOX, textValue);
	}

	public listProductPO clickToSearchButton() {
		waitToElementVisible(HomePageUI.SEARCH_BUTTON);
		clickToElement(HomePageUI.SEARCH_BUTTON);
		return PageGeneratorManager.getSearchPage(driver);
	}

}
