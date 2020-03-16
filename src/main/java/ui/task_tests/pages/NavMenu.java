package ui.task_tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

public class NavMenu {
    @FindBy(xpath = "//button[contains(@class,'base-nav-bar__today-button')]")
    private WebElement todayTabLink;
    @FindBy(xpath = "//button[contains(@class,'base-nav-bar__tomorrow-button')]")
    private WebElement tomorrowTabLink;
    @FindBy(xpath = "//button[contains(@class,'base-nav-bar__later-button')]")
    private WebElement laterTabLink;
    @FindBy(xpath = "//button[contains(@class,'base-nav-bar__inbox-button')]")
    private WebElement inboxTabLink;
    @FindBy(xpath = "//a[contains(@class,'base-nav-bar__link_active')]")
    private WebElement activeTab;

    /**
     * Today page constructor
     */
    public NavMenu() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }
    /**
     * click on 'Later' link
     */
    public void goToLaterTab() {
        laterTabLink.click();
    }

    /**
     * click on 'Inbox' link
     */
    public void goToInboxTab() {
        inboxTabLink.click();
    }
    /**
     * click on 'Tomorrow' link
     */
    public void goToTomorrowTab() {
        tomorrowTabLink.click();
    }

    /**
     * click on 'Today' link
     */
    public void goToTodayTab() {
        todayTabLink.click();
    }


    public WebElement getActiveTab() {
       return activeTab;
    }
}

