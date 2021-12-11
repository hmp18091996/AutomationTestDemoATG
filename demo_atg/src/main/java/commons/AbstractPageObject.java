package commons;

import java.nio.channels.InterruptedByTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Function;

public class AbstractPageObject {

	By by;
	Select select;
	Actions action;
	WebElement element;
	long shortTimeout = 5;
	long longTimeout = 30;
	WebDriver driverGlobal;
	List<WebElement> elements;
	WebDriverWait waitExplicit;
	JavascriptExecutor jsExecutor;

	public AbstractPageObject(WebDriver driverLocal) {
		this.driverGlobal = driverLocal;
		jsExecutor = (JavascriptExecutor) driverLocal;
		waitExplicit = new WebDriverWait(driverGlobal, 30);
		action = new Actions(driverGlobal);
	}

	public void openUrl(String urlValue) {
		driverGlobal.get(urlValue);
	}

	public String getPageTitle() {
		return driverGlobal.getTitle();
	}

	public String getPageCurrentUrl() {
		return driverGlobal.getCurrentUrl();
	}

	public String getPageSourceCode() {
		return driverGlobal.getPageSource();
	}

	public void backToPage() {
		driverGlobal.navigate().back();
	}

	public void refreshToPage() {
		driverGlobal.navigate().refresh();
	}

	public void forwardToPage() {
		driverGlobal.navigate().forward();
	}

	public void waitAlertPresence() {
		waitExplicit.until(ExpectedConditions.alertIsPresent());
	}

	public void acceptAlert() {
		driverGlobal.switchTo().alert().accept();
	}

	public void cancelAlert() {
		driverGlobal.switchTo().alert().dismiss();
	}

	public void sendkeyToAlert(String value) {
		driverGlobal.switchTo().alert().sendKeys(value);
	}

	public String getTextAlert() {
		return driverGlobal.switchTo().alert().getText();
	}

	public void clickToElement(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		element.click();
	}

	public void clickToElement(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		element.click();
	}

	public void sendKeyToElement(String locator, String value) {
		element = driverGlobal.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(value);
	}

	public void sendKeyToElement(String locator, String valueToSendKey, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		element.clear();
		element.sendKeys(valueToSendKey);
	}

	public void selectItemInDropdown(String locator, String valueItem) {
		element = driverGlobal.findElement(By.xpath(locator));
		select = new Select(element);
		select.selectByVisibleText(valueItem);
	}

	public void selectItemInDropdown(String locator, String valueItem, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		select = new Select(element);
		select.selectByVisibleText(valueItem);
	}

	public String getValueItemInDropdown(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void selectItemInCustomDropdown(String parentLocator, String allItemsLocator, String expectedItem)
			throws InterruptedByTimeoutException, InterruptedException {
		element = driverGlobal.findElement(By.xpath(parentLocator));
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		sleepInSecond(1);
		jsExecutor.executeScript("arguments[0].click();", element);
		sleepInSecond(1);

		waitExplicit.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(expectedItem)));
		elements = driverGlobal.findElements(By.xpath(allItemsLocator));

		for (WebElement item : elements) {
			if (item.getText().equals(expectedItem)) {
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInSecond(1);
				item.click();
				sleepInSecond(1);
				break;
			}
		}

	}

	public void sleepInSecond(long numberInSecond) {
		try {
			Thread.sleep(numberInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getAttributeValue(String locator, String attributeName) {
		element = driverGlobal.findElement(By.xpath(locator));
		return element.getAttribute(attributeName);
	}

	public String getTextElement(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		return element.getText();
	}

	public String getTextElement(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		return element.getText();
	}

	public int countElementNumber(String locator) {
		elements = driverGlobal.findElements(By.xpath(locator));
		return elements.size();
	}

	public void checkTheCheckbox(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		if (element.isSelected() == false) {
			element.click();
		}
	}

	public void checkTheCheckbox(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		if (element.isSelected() == false) {
			element.click();
		}
	}

	public void unCheckTheCheckbox(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		if (element.isSelected()) {
			element.click();
		}
	}

	public boolean isElementDisplayed(String locator) {
		overGlobalTimeout(shortTimeout);
		try {
			overGlobalTimeout(longTimeout);
			element = driverGlobal.findElement(By.xpath(locator));
			return element.isDisplayed();
		} catch (Exception e) {
			overGlobalTimeout(longTimeout);
			return false;
		}
	}

	public void overGlobalTimeout(long timeout) {
		driverGlobal.manage().timeouts().implicitlyWait(timeout, TimeUnit.SECONDS);
	}

	public boolean isElementDisplayed(String locator, String... values) {
		overGlobalTimeout(shortTimeout);
		try {
			overGlobalTimeout(longTimeout);
			locator = String.format(locator, (Object[]) values);
			element = driverGlobal.findElement(By.xpath(locator));
			return element.isDisplayed();
		} catch (Exception e) {
			overGlobalTimeout(longTimeout);
			return false;
		}
	}

	public boolean isElementSelected(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		return element.isSelected();
	}

	public boolean isElementEnabled(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		return element.isEnabled();
	}

	public void switchToChidWindowByID(String parent) {
		Set<String> allWindows = driverGlobal.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parent)) {
				driverGlobal.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToChidWindowByTitle(String title) {
		Set<String> allWindows = driverGlobal.getWindowHandles();
		for (String runWindow : allWindows) {
			driverGlobal.switchTo().window(runWindow);
			String currentWin = driverGlobal.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public boolean closeAllWindowsWithoutParent(String parentWindow) {
		Set<String> allWindows = driverGlobal.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentWindow)) {
				driverGlobal.switchTo().window(runWindow);
				break;
			}
		}
		driverGlobal.switchTo().window(parentWindow);
		if (driverGlobal.getWindowHandles().size() == 1) {
			return true;
		} else
			return false;

	}

	public void switchToFrameOfIframe(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		driverGlobal.switchTo().frame(element);
	}

	public void switchToParentPage() {
		driverGlobal.switchTo().defaultContent();
	}

	public void hoverToElement(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		action.moveToElement(element).perform();
	}

	public void doubleClickToElement(String locator) {
		element = driverGlobal.findElement(By.xpath(locator));
		action.doubleClick(element).perform();
	}

	public void sendKeyboardToElement(String locator, Keys key) {
		element = driverGlobal.findElement(By.xpath(locator));
		action.sendKeys(element, key).perform();
	}

	public boolean checkAnyImageLoaded(String locator) {
		boolean status;
		element = driverGlobal.findElement(By.xpath(locator));
		status = (boolean) jsExecutor.executeScript(
				"return arguments[0].complete && typeof arguments[0].naturalWidth != \\\"undefined\\\" && arguments[0].naturalWidth > 0",
				element);
		if (status) {
			return true;
		} else {
			return false;
		}
	}

	public void waitToElementVisible(String locator) {
		by = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitToElementVisible(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		by = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void waitToElementPresence(String locator) {
		by = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	public void waitToElementInvisible(String locator) {
		by = By.xpath(locator);
		overGlobalTimeout(shortTimeout);
		waitExplicit.until(ExpectedConditions.invisibilityOfElementLocated(by));
		overGlobalTimeout(longTimeout);
	}

	public void waitToElementClickable(String locator) {
		by = By.xpath(locator);
		waitExplicit.until(ExpectedConditions.elementToBeClickable(by));
	}

	public void removeElementAttribute(String locator, String attributeRemove) {
		element = driverGlobal.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	public void removeElementAttribute(String locator, String attributeRemove, String... values) {
		locator = String.format(locator, (Object[]) values);
		element = driverGlobal.findElement(By.xpath(locator));
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	public boolean isDataStringSortedAscending(String locatior) {
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driverGlobal.findElements(By.xpath(locatior));

		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}

		System.out.println("---------- Data in UI: ----------");
		for (String name : arrayList) {
			System.out.println(name);
		}

		ArrayList<String> sortedList = new ArrayList<>();
		for (String child : arrayList) {
			sortedList.add(child);
		}

		Collections.sort(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}

	public boolean isDataStringSortedDescending(String locatior) {
		ArrayList<String> arrayList = new ArrayList<>();

		List<WebElement> elementList = driverGlobal.findElements(By.xpath(locatior));

		for (WebElement element : elementList) {
			arrayList.add(element.getText());
		}

		System.out.println("---------- Data in UI: ----------");
		for (String name : arrayList) {
			System.out.println(name);
		}

		ArrayList<String> sortedList = new ArrayList<>();
		for (String child : arrayList) {
			sortedList.add(child);
		}

		// Sort ASC
		Collections.sort(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		// Sort DESC
		Collections.reverse(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (String name : sortedList) {
			System.out.println(name);
		}

		return sortedList.equals(arrayList);

	}

	public boolean isDataFloatSortedAscending(String locatior) {

		ArrayList<Float> arrayList = new ArrayList<>();

		List<WebElement> elementList = driverGlobal.findElements(By.xpath(locatior));

		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("---------- Data in UI: ----------");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		ArrayList<Float> sortedList = new ArrayList<>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Sort ASC
		Collections.sort(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		return sortedList.equals(arrayList);
	}

	public boolean isDataFloatSortedDascending(String locatior) {

		ArrayList<Float> arrayList = new ArrayList<>();

		List<WebElement> elementList = driverGlobal.findElements(By.xpath(locatior));

		for (WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").trim()));
		}

		System.out.println("---------- Data in UI: ----------");
		for (Float name : arrayList) {
			System.out.println(name);
		}

		ArrayList<Float> sortedList = new ArrayList<>();
		for (Float child : arrayList) {
			sortedList.add(child);
		}

		// Sort ASC
		Collections.sort(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		// Sort DESC
		Collections.reverse(sortedList);

		System.out.println("---------- Data was SORT ASC: ----------");
		for (Float name : sortedList) {
			System.out.println(name);
		}

		return sortedList.equals(arrayList);
	}

	public boolean isDataDateSortedDascending(String locatior) {
		ArrayList<Date> arrayList = new ArrayList<Date>();

		List<WebElement> elementList = driverGlobal.findElements(By.xpath(locatior));

		for (WebElement element : elementList) {
			arrayList.add(convertStringToDate(element.getText()));
		}

		System.out.println("---------- Data Date in UI: ----------");
		for (Date name : arrayList) {
			System.out.println(name);
		}

		ArrayList<Date> sortedList = new ArrayList<Date>();

		for (Date chid : arrayList) {
			sortedList.add(chid);
		}

		// Sort ASC
		Collections.sort(sortedList);

		System.out.println("---------- Data Date SORT ASC: ----------");
		for (Date sort : sortedList) {
			System.out.println(sort);
		}

		// Sort DESC
		Collections.reverse(sortedList);

		System.out.println("---------- Data Date SORT DASC: ----------");
		for (Date sort : sortedList) {
			System.out.println(sort);
		}

		return sortedList.equals(arrayList);

	}

	public Date convertStringToDate(String dataInString) {
		dataInString = dataInString.replace(",", "");
		SimpleDateFormat fomatter = new SimpleDateFormat("MMM dd yyy");
		Date date = null;
		try {
			date = fomatter.parse(dataInString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public int countListItems(String location) {
		List<WebElement> listItems = new ArrayList<WebElement>();
		listItems = driverGlobal.findElements(By.xpath(location));
		return listItems.size();
	}

	public boolean isListItemsHavePaging(String location) {
		List<WebElement> listItems = new ArrayList<WebElement>();
		listItems = driverGlobal.findElements(By.xpath(location));
		if (listItems.size() == 1) {
			return true;
		}
		return false;
	}

}
