package com.amazon.demo;

import static org.testng.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.AbstractTest;
import pageObject.HomePO;
import pageObject.PageGeneratorManager;
import pageObject.listProductPO;

public class demo_01 extends AbstractTest {

	private WebDriver driver;
	private HomePO homePage;
	private listProductPO listProductPage;

	boolean ActualResult;

	String Department = "Books";
	String Keyword = "apple";
	String language = "English";
	String sortOption = "Publication Date";

	@Parameters({ "browser", "url" })
	@BeforeClass
	public void beforeClass(String browserName, String urlValue) {

		driver = getBrowserDriver(browserName, urlValue);
		homePage = PageGeneratorManager.getHomePage(driver);
	}

	@Test
	public void demo_01_VerifyResultListIsPaginatedIfThereAreMoreThan16Items() {

		log.info("Step 01 - Select department dropdown");
		homePage.selectBooksItemInDepartmentDropdown(Department);

		log.info("Step 02 - Input to search textbox");
		homePage.inputToSearchTextbox(Keyword);

		log.info("Step 03 - Click to search button -> move to listPage");
		listProductPage = homePage.clickToSearchButton();

		log.info("Step 04 - Select language");
		listProductPage.selectDynamicOptionInLangugeCheckbox(language);

		log.info("Step 05 - Verify page have paging");
		assertTrue(listProductPage.isListItemsHavePaging());

		log.info("Step 06 -Verify the result displays exactly 16 items on each page");
		while (listProductPage.isNextButtonEnalbe()) {
			listProductPage.clickToNextButton();
			verifyTrue(listProductPage.isPaginatedIfThereAreThan16Items());
		}
	}

	@Test
	public void demo_02_VerifyResultListCanBeSortedOnDemand() {

		log.info("Step 01 - click to logo amazon -> move home page");
		homePage = listProductPage.clickToLogo();

		log.info("Step 02 - Select department dropdown");
		homePage.selectBooksItemInDepartmentDropdown(Department);

		log.info("Step 03 - input to search textbox");
		homePage.inputToSearchTextbox(Keyword);

		log.info("Step 04 - click to search button -> move to listPage");
		listProductPage = homePage.clickToSearchButton();

		log.info("Step 05 - Select language");
		listProductPage.selectDynamicOptionInLangugeCheckbox(language);

		log.info("Step 06 - select 'Publication Date' in Sort dropdown");
		listProductPage.selectBooksItemInDropdown(sortOption);

		log.info("Step 07 - verify the result is sorted by Publication date");
		verifyTrue(listProductPage.isPublicationDateSortedAscending());

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		closeBrowserAndDriver();
	}

}
