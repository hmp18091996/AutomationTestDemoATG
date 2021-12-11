package pageObject;

import org.openqa.selenium.WebDriver;

import commons.AbstractPageObject;
import pageUIs.listProductPageUI;

public class listProductPO extends AbstractPageObject {

	WebDriver driver;

	public listProductPO(WebDriver driverLocal) {
		super(driverLocal);
		driver = driverLocal;
	}

	public void selectDynamicOptionInLangugeCheckbox(String optionValue) {
		waitToElementVisible(listProductPageUI.DYNAMIC_BOOK_LANGUAGE_CHECKBOX, optionValue);
		checkTheCheckbox(listProductPageUI.DYNAMIC_BOOK_LANGUAGE_CHECKBOX, optionValue);
	}

	public void selectBooksItemInDropdown(String textValue) {
		selectItemInDropdown(listProductPageUI.SORT_SELECTBOX, textValue);
	}

	public HomePO clickToLogo() {
		waitToElementVisible(listProductPageUI.LOGO_LINK);
		clickToElement(listProductPageUI.LOGO_LINK);
		return PageGeneratorManager.getHomePage(driver);
	}

	public boolean isPublicationDateSortedAscending() {
		waitToElementVisible(listProductPageUI.PUBLICATION_DATE_OF_ITEM);
		return isDataDateSortedDascending(listProductPageUI.PUBLICATION_DATE_OF_ITEM);
	}

	public void clickToNextButton() {
		waitToElementVisible(listProductPageUI.NEXT_BUTTON);
		checkTheCheckbox(listProductPageUI.NEXT_BUTTON);
	}

	public boolean isNextButtonEnalbe() {
		waitToElementVisible(listProductPageUI.NEXT_BUTTON);
		if (getAttributeValue(listProductPageUI.NEXT_BUTTON, "class").equals("a-last")) {
			return true;
		}
		return false;
	}

	public boolean isListItemsHavePaging() {
		return isListItemsHavePaging(listProductPageUI.PAGINATION_FORM);
	}

	public boolean isPaginatedIfThereAreThan16Items() {
		waitToElementInvisible(listProductPageUI.LOADING_ICON);
		if (countListItems(listProductPageUI.ITEMS) == 16) {
			return true;
		}
		return false;
	}
}
