package ui.task_tests;

import ui.Configurator;
import ui.task_tests.pages.LoginPage;
import ui.task_tests.pages.RegistrationPage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * tests for registration cases
 */
public class RegistrationTest extends Assert {

    private static RegistrationPage regPage;
    private static LoginPage loginPage;
    private StringBuilder regEmail = new StringBuilder("exampletest@mail.ru");

    /**
     * data set constructor
     *
     * @return return matrix data set
     */
    @DataProvider
    public static Object[][] fieldValidation() {
        return new Object[][]{
                {"datye", "123456", true, false},
                {"datye@", "123456", true, false},
                {"datye@mail", "123456", true, false},
                {"datye@mail.", "123456", true, false},
                {"datye@mail.com", "passwordissolongsoitcantfitinform", true, false},
                {"datye@mail.com", "123456", false, false},
                {"", "", true, false},
                {"datye3@mail.com", "123456", true, true}
        };
    }

    /**
     * method to perform before each test
     */
    @BeforeTest
    public void initialize() throws IOException {
        Configurator.setup();
        loginPage = new LoginPage();
        regPage = new RegistrationPage();
        regPage.signUpRedirect();
    }

    /**
     * method to perform after each test
     */
    @AfterMethod
    public void refreshPage() {
        regPage.signUpRedirect();
    }

    /**
     * method to perform after all tests
     */
    @AfterTest
    public void tearDown() {
        Configurator.tearDown();
    }

    /**
     * Button enable with different email/pass
     * @param email           email
     * @param password        password
     * @param checkbox        checkbox filling
     * @param expectedSuccess success with such data
     */
    @Test(dataProvider = "fieldValidation")
    public void registrationTest(final String email, final String password,
                                 final boolean checkbox, final boolean expectedSuccess) {
        regPage.fillingFields(email, password, checkbox);
        Assert.assertEquals(expectedSuccess, regPage.signUpButtonEnabled());
    }

    /**
     * test to check for email error
     */
    @Test
    public void emailErrorAppearanceTest() {
        regPage.fillingFields("cheburek", "admin123", true);
        Assert.assertTrue(regPage.emailErrorIsDisplayed());
    }

    /**
     * test to check for password error
     */
    @Test
    public void passErrorAppearanceTest() {
        regPage.fillingFields("cheburek@mail.ru", "admin", true);
        Assert.assertTrue(regPage.passErrorIsDisplayed());
    }

    /**
     * test to check the success of registration
     */
    @Test
    public void registrationSuccessTest() {
        regEmail.append("uuu");
        regPage.fillingFields(regEmail.toString(), "123456", true);
        regPage.signUpButtonClick();
        assertTrue(loginPage.forgotLinkAreDisplayed());
        loginPage.redirectToReg();
    }

    /**
     * test to check the presence of all elements on the page
     */
    @Test
    public void pageElementsAreDisplayed() {
        assertTrue(regPage.elementsAreDisplayed());

    }

    /**
     * redirect to login page test
     */
    @Test
    public void redirectToLogin() {
        regPage.redirectToLogin();
        assertTrue(loginPage.forgotLinkAreDisplayed());
        loginPage.redirectToReg();
    }


}
