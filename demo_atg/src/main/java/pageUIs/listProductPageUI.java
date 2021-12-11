package pageUIs;

public class listProductPageUI {

	public static final String DYNAMIC_BOOK_LANGUAGE_CHECKBOX = "//span[text()='Language']/parent::div/following-sibling::ul//span[text()='%s']/preceding-sibling::div//i";

	public static final String LOADING_ICON = "//div[@class='s-result-list-placeholder aok-hidden sg-row']";
	public static final String SORT_SELECTBOX = "//select[@name='s']";
	public static final String ITEMS = "//div[@data-index and @data-uuid]";
	public static final String PUBLICATION_DATE_OF_ITEM  = "//div[@data-index and @data-uuid]//div[@class='a-row a-size-base a-color-secondary']//div[@class='a-row']/span[last()]";
	public static final String LOGO_LINK = "//a[@id='nav-logo-sprites']";
	public static final String PAGINATION_FORM = "//ul[@class='a-pagination']";
	public static final String NEXT_BUTTON = "//ul[@class='a-pagination']/li[last()]";
}
