package ui.task_tests.pages;

import ui.Configurator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * class described login page
 */
public class LoginPage {

    @FindBy(xpath = "//input[@name='Email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@name='Password']")
    private WebElement passField;
    @FindBy(xpath = "//button[contains(@class,'show-password')]")
    private WebElement visibilityEye;
    @FindBy(xpath = "//a[@class='auth-nav-bar__link']")
    private WebElement singUpLink;
    @FindBy(xpath = "//a[contains(@class,'auth-nav-bar__link_active')]")
    private WebElement singInLink;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signInButton;
    @FindBy(xpath = "//span[contains(text(),'Email/Password is invalid')]")
    private WebElement error;
    @FindBy(xpath = "//a[contains(@class,'forgot-password')]")
    private WebElement forgotPassLink;
    private Properties property = new Properties();

    /**
     * Login page constructor
     */
    public LoginPage() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
            Configurator.getDriver().get(property.getProperty("baseUrl"));
        }
        PageFactory.initElements(Configurator.getDriver(), this);
    }



    /**
     * Sign In button click method
     */
    public void signInButtonClick() {
        signInButton.click();
    }

    /**
     * method for filling fields
     * @param email email string
     * @param pass  password string
     */
    public void fillingFields(final String email, final String pass) {
        emailField.clear();
        emailField.sendKeys(email);
        passField.clear();
        passField.sendKeys(pass);
    }

    /**
     * method for checking the error display
     * @return return statement
     */
    public boolean errorIsDisplayed() {
        return error.isDisplayed();
    }

    /**
     * method to go to the registration page
     */
    public void redirectToReg() {
        singUpLink.click();
    }

    /**
     * method to go to login page
     */
    public void redirectToLogin() {
        singInLink.click();
    }

    /**
     * method to check for all elements
     * @return return statement
     */
    public boolean elementsAreDisplayed() {
        return emailField.isDisplayed() && passField.isDisplayed() && visibilityEye.isDisplayed() &&
                singUpLink.isDisplayed() && singInLink.isDisplayed() && signInButton.isDisplayed() &&
                forgotPassLink.isDisplayed();
    }

    /**
     * method for checking forgot link display
     * @return return statement
     */
    public boolean forgotLinkAreDisplayed() {
        return forgotPassLink.isDisplayed();
    }

    /**
     * method that returns a WebElement passField
     * @return passField
     */
    public WebElement getPassField() {
        return passField;
    }

    /**
     * method that click on visibilityEye
     */
    public void revealPassword() {
        visibilityEye.click();
    }
}
