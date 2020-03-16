package ui.task_tests.pages;

import ui.Configurator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * class described registration page
 */
public class RegistrationPage {

    @FindBy(xpath = "//input[@name='Email']")
    private WebElement emailField;
    @FindBy(xpath = "//input[@name='Password']")
    private WebElement passField;
    @FindBy(xpath = "//span[@class='terms-of-use__check-mark']")
    private WebElement agreementCheckbox;
    @FindBy(xpath = "//button[@type='button']")
    private WebElement visibilityEye;
    @FindBy(xpath = "//button[@type='submit']")
    private WebElement signUpButton;
    @FindBy(xpath = "//span[contains(text(),'Email is incorrect')]")
    private WebElement emailError;
    @FindBy(xpath = "//span[contains(text(),'Password must contain from 6 to 32 characters')]")
    private WebElement passwordError;
    @FindBy(xpath = "//a[contains(@class,'auth-nav-bar__link_active')]")
    private WebElement singUpLink;
    @FindBy(xpath = "//a[@class='auth-nav-bar__link']")
    private WebElement singInLink;
    @FindBy(xpath = "//a[contains(@class,'link-to-terms')]")
    private WebElement termsOfUse;

    @FindBy(xpath = "//a[contains(@class,'link-to-terms')]")
    private List<WebElement> termsOfUselist;
    private Properties property = new Properties();

    /**
     * Registration page constructor
     */
    public RegistrationPage() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
            Configurator.getDriver().get(property.getProperty("baseUrl") + property.getProperty("db.signUpEndpont"));
        }
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * Sign Up button enable check
     */
    public boolean signUpButtonEnabled() {
        return signUpButton.isEnabled();
    }

    /**
     * Sign Up button click method
     */
    public void signUpButtonClick() {
        signUpButton.click();
    }

    /**
     * method to go to the registration page
     */
    public void signUpRedirect() {
        singUpLink.click();
    }

    /**
     * method for filling fields
     *
     * @param email    email string
     * @param pass     password string
     * @param checkbox checkbox filling
     */
    public void fillingFields(final String email, final String pass, final boolean checkbox) {
        emailField.clear();
        emailField.sendKeys(email);
        passField.clear();
        passField.sendKeys(pass);
        if (checkbox) {
            agreementCheckbox.click();
        }
    }

    /**
     * method for checking the email error display
     *
     * @return return statement
     */
    public boolean emailErrorIsDisplayed() {
        return emailError.isDisplayed();
    }

    /**
     * method for checking the terms link display
     *
     * @return return statement
     */
    public boolean termsAreDisplayed() {
        return termsOfUse.isDisplayed();
    }

    /**
     * method for checking the password error display
     *
     * @return return statement
     */
    public boolean passErrorIsDisplayed() {
        return passwordError.isDisplayed();
    }

    /**
     * method to go to the agreement page
     */
    public void redirectToAgreement() {
        termsOfUse.click();
    }

    /**
     * method to hide/reveal the password
     */
    public void hidePassword() {
        visibilityEye.click();
    }

    /**
     * method to go to the login page
     */
    public void redirectToLogin() {
        singInLink.click();
    }

    public void redirectToReg() {
        singUpLink.click();
    }

    /**
     * method to check for "forgot" link
     *
     * @return return statement
     */

    /**
     * method to check for all elements
     *
     * @return return statement
     */
    public boolean elementsAreDisplayed() {
        return emailField.isDisplayed() && passField.isDisplayed() && visibilityEye.isDisplayed() &&
                singUpLink.isDisplayed() && singInLink.isDisplayed() && signUpButton.isDisplayed() &&
                termsOfUse.isDisplayed() && agreementCheckbox.isDisplayed();
    }

    /**
     * method that returns a WebElement passField
     *
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
