package ui.task_tests;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import ui.Configurator;
import ui.task_tests.pages.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

public class TodayTests {
    private static LoginPage loginPage;
    private static TodayTabPage todayTab;
    private static AddSpherePage addSpherePage;
    private static InboxTab inboxTab;
    private static LaterTab laterTab;
    private static TomorrowTab tomorrowTab;
    private static NavMenu navMenu;
    private static RegistrationPage registrationPage;
    private static UtilDB database;
    private Properties property = new Properties();

    /**
     * initialize method
     *
     * @throws IOException throwable in/output exception
     */
    @BeforeSuite
    public void createUser() throws IOException, SQLException {
        database = new UtilDB();
        Configurator.setup();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
            registrationPage = new RegistrationPage();
            registrationPage.fillingFields(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"), true);
            registrationPage.signUpButtonClick();
            todayTab = new TodayTabPage();
            laterTab = new LaterTab();
            tomorrowTab = new TomorrowTab();
            navMenu = new NavMenu();
            inboxTab = new InboxTab();
            addSpherePage = new AddSpherePage();
            loginPage = new LoginPage();
            loginPage.fillingFields(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"));
            loginPage.signInButtonClick();
        }
    }

    /**
     * test to check for add sphere
     */
    @Test
    public void addSphereTest() {
        todayTab.createSphereButtonClick();
        addSpherePage.newSphere(property.getProperty("db.sphereTitle"));
        addSpherePage.addSphereButtonClick();
        Configurator.getDriver().navigate().refresh();
        assertTrue(todayTab.getSphereTitle().get(0).getText().contains(property.getProperty("db.sphereTitle")));
        Configurator.getDriver().navigate().refresh();
        todayTab.deleteSphere(0);
    }

    /**
     * test to check for delete sphere
     */
    @Test
    public void deleteSphereTest() {
        todayTab.createSphereButtonClick();
        addSpherePage.newSphere(property.getProperty("db.sphereTitle"));
        addSpherePage.addSphereButtonClick();
        Configurator.getDriver().navigate().refresh();
        todayTab.deleteSphere(0);
        Configurator.getDriver().navigate().refresh();
        Assert.assertFalse(todayTab.getSphereTitle().get(0).getText().contains(property.getProperty("db.sphereTitle")));
    }

    /**
     * test to check for switch between "today" page and "later" page
     */
    @Test
    public void switchBetweenTodayAndLaterTest() {
        navMenu.goToLaterTab();
        assertTrue(navMenu.getActiveTab().getText().contains(property.getProperty("db.laterLink")));
        navMenu.goToTodayTab();
    }

    /**
     * test to check for switch between "today" page and "tomorrow" page
     */
    @Test
    public void switchBetweenTodayAndTomorrowTest() {
        navMenu.goToTomorrowTab();
        assertTrue(navMenu.getActiveTab().getText().contains(property.getProperty("db.tomorrowLink")));
        navMenu.goToTodayTab();
    }

    /**
     * test to check for switch between "today" page and "later" page
     */
    @Test
    public void switchBetweenTodayAndInboxTest() {
        navMenu.goToInboxTab();
        assertTrue(navMenu.getActiveTab().getText().contains(property.getProperty("db.inboxLink")));
        navMenu.goToTodayTab();
    }

    /**
     * test to check save spheres after switching between tabs
     */
    @Test
    public void saveSphereBetweenTabsTest() {
        List<WebElement> titleList = new ArrayList<WebElement>(todayTab.getSphereTitle());
        navMenu.goToTomorrowTab();
        boolean spheresEquals = titleList.equals(tomorrowTab.getSphereTitle());
        navMenu.goToLaterTab();
        boolean laterTabSpheresEquals = titleList.equals(laterTab.getSphereTitle());
        navMenu.goToInboxTab();
        assertEquals(titleList.equals(inboxTab.getSphereTitle()), spheresEquals);
        assertEquals(titleList.equals(inboxTab.getSphereTitle()), laterTabSpheresEquals);
        navMenu.goToTodayTab();
    }

    /**
     * After Class to close the browser
     */
    @AfterTest

    public void tearDown() throws Exception {
        database.deleteUserByEmail(property.getProperty("db.testLogin"));
        database.close();
        Configurator.tearDown();
    }
}
