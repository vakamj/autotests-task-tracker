package ui.task_tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ui.Configurator;
import ui.task_tests.pages.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AddTaskTest {
    private static LoginPage loginPage;
    private Properties property = new Properties();
    private static TodayTabPage todayTab;
    private static AddSpherePage addSpherePage;
    private static AddTaskPage addTaskPage;

    /**
     * initialize method
     *
     * @throws IOException throwable in/output exception
     */
    @BeforeSuite
    public void createUser() throws IOException {
        Configurator.setup();
        loginPage = new LoginPage();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            property.load(fileInputStream);
            loginPage.fillingFields(property.getProperty("db.testLogin"), property.getProperty("db.testPassword"));
            loginPage.signInButtonClick();
            todayTab = new TodayTabPage();
        }
    }

    /**
     * method to perform before each test set
     *
     * @throws IOException throwable in/output exception
     */
    @BeforeTest
    public void initialize() {
        addTaskPage = new AddTaskPage();
        addSpherePage = new AddSpherePage();
        todayTab.addTaskButtonClick();
    }


    /**
     * data set constructor
     *
     * @return timer data set
     */
    @DataProvider
    public static Object[][] setTimer() {
        return new Object[][]{
                {"1", "1", "1", "1", "11:11"},
                {"0", "0", "1", "1", "00:11"},
                {"2", "3", "5", "9", "23:59"},
                {"3", "1", "1", "1", "11:10"},
                {"2", "3", "6", "1", "23:10"},
                {"2", "5", "2", "3", "22:30"},
                {"2", "5", "5", "7", "20:00"},
                {"a", "5", "5", "7", "00:00"},
                {",", "o", "2", "7", "20:00"},
                {"", "o", "1", "7", "17:00"},
        };
    }

    /**
     * data set constructor
     *
     * @return set of clicks on timer arrows
     */
    @DataProvider
    public static Object[][] setTimerByArrowClick() {
        return new Object[][]{
                {5, 8, 0, 0, "05:08"},
                {0, 59, 0, 0, "00:59"},
                {0, 0, 0, 0, "00:00"},
                {0, 0, 1, 1, "23:59"},
                {0, 0, 5, 59, "19:01"},
                {2, 2, 2, 2, "00:00"},
                {5, 27, 2, 5, "03:22"},

        };
    }

    /**
     * test for setting timer values ​​for data entry
     *
     * @param tensHour    tens Hour
     * @param unitsHour   units Hour
     * @param tensMinute  tens Minute
     * @param unitsMinute units Minute
     * @param result      timer value
     */

    @Test(dataProvider = "setTimer")
    public void setTimerTest(final String tensHour, final String unitsHour, final String tensMinute, final String unitsMinute, final String result) throws InterruptedException {
        addTaskPage.timerIconClick();
        addTaskPage.clearTimer();
        addTaskPage.setTimer(tensHour, unitsHour, tensMinute, unitsMinute);
        addTaskPage.saveTimerButtonClick();
        addTaskPage.timerIconClick();
        Assert.assertEquals(addTaskPage.getTimer().getAttribute("value"), result);
        addTaskPage.saveTimerButtonClick();
    }

    /**
     * test for timer setting by clicking on the arrow
     *
     * @param arrowUpHourClick     click on the up arrow for an hour
     * @param arrowUpMinuteClick   click on the up arrow for a minute
     * @param arrowDownHourClick   click on the down arrow for an hour
     * @param arrowDownMinuteClick click on the up arrow for an minute
     * @param result               timer value
     */
    @Test(dataProvider = "setTimerByArrowClick")
    public void setTimerByArrowUpTest(final int arrowUpHourClick, final int arrowUpMinuteClick, final int arrowDownHourClick, final int arrowDownMinuteClick, final String result) {
        addTaskPage.timerIconClick();
        addTaskPage.clearTimer();
        addTaskPage.setTimerByArrow(arrowUpHourClick, arrowUpMinuteClick, arrowDownHourClick, arrowDownMinuteClick);
        addTaskPage.saveTimerButtonClick();
        addTaskPage.timerIconClick();
        Assert.assertEquals(addTaskPage.getTimer().getAttribute("value"), result);
        addTaskPage.saveTimerButtonClick();
    }

    /**
     * After Class to close the browser
     */
    @AfterTest
    public static void tearDown() {
        Configurator.tearDown();
    }
}
