package ui.task_tests;


import org.testng.annotations.*;
import ui.Configurator;
import ui.task_tests.pages.LoginPage;
import ui.task_tests.pages.NavMenu;
import ui.task_tests.pages.TodayTabPage;
import ui.task_tests.pages.RegistrationPage;
import org.testng.Assert;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * tests for login cases
 */
public class LoginTests extends Assert {

    private static LoginPage loginPage;
    private static RegistrationPage regPage;
    private static RegistrationPage redirectedRegPage;
    private Properties property = new Properties();
    private static NavMenu navMenu;

    /**
     * data set constructor
     * @return return matrix data set
     */
    @DataProvider
    public Object[][] fieldValidation() {
        return new Object[][]{
                {"", "123456", false},
                {"exampletest@mail.ru", "", false},
                {"etoneya@mail.ru", "123456", false},
                {"exampletest@mail.ru", "654321", false},
                {"exampletest@mail.ru", "123456", true}
        };
    }

    /**
     * initialize method
     * @throws IOException throwable in/output exception
     */
    @BeforeSuite
    public void createUser() throws IOException {
        Configurator.setup();
        regPage = new RegistrationPage();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
            regPage.fillingFields(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"), true);
            regPage.signUpButtonClick();
        }

        Configurator.tearDown();

    }

    /**
     * method to perform before each test set
     * @throws IOException throwable in/output exception
     */
    @BeforeTest
    public void initialize() throws IOException {
        Configurator.setup();
        redirectedRegPage = new RegistrationPage();
        loginPage = new LoginPage();
        navMenu = new NavMenu();
    }

    @AfterTest
    public void tearDown() {
        Configurator.tearDown();
    }

    /**
     * login with different email/pass
     *
     * @param email           email
     * @param password        password
     * @param expectedSuccess success with such data
     */
    @Test(dataProvider = "fieldValidation", dependsOnMethods = {"errorAppearanceTest", "redirectToRegistration",
            "pageElementsAreDisplayed", "checkEyeWorking"})
    public void checkLoginSuccessTest(final String email, final String password, final boolean expectedSuccess) {
        loginPage.fillingFields(email, password);
        loginPage.signInButtonClick();
        assertEquals(navMenu.getActiveTab().isDisplayed(), expectedSuccess);
        Configurator.getDriver().navigate().refresh();
    }

    /**
     * error test for incorrect data
     */
    @Test
    public void errorAppearanceTest() {
        loginPage.fillingFields("cheburek@mail.ru", "admin");
        loginPage.signInButtonClick();
        Assert.assertTrue(loginPage.errorIsDisplayed());
    }

    /**
     * redirect to registration page test
     */
    @Test
    public void redirectToRegistration() {
        loginPage.redirectToReg();
        assertTrue(redirectedRegPage.termsAreDisplayed());
        redirectedRegPage.redirectToLogin();
    }

    /**
     * test to check the presence of all elements on the page
     */
    @Test
    public void pageElementsAreDisplayed() {
        assertTrue(loginPage.elementsAreDisplayed());

    }

    /**
     * visibilityEye test
     */
    @Test
    public void checkEyeWorking() {
        loginPage.fillingFields("cheburek@mail.ru", "admin");
        String passTextType = loginPage.getPassField().getAttribute("type");
        loginPage.revealPassword();
        assertNotEquals(passTextType, loginPage.getPassField().getAttribute("type"));

    }

}
