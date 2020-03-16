package ui.task_tests.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

public class AddTaskPage {
    @FindBy(xpath = "//p[@class='add-task-header__warning']")
    private WebElement taskCreationLabel;
    @FindBy(xpath = "//input[@placeholder='What are you planning?']")
    private WebElement taskTitle;
    @FindBy(xpath = "//button[@class='submit-task-adding-button']")
    private WebElement addTaskButton;
    @FindBy(xpath = "//button[contains(@class,'add-coins__button')]")
    private WebElement taskCostIcon;
    @FindBy(xpath = "//input[contains(@class,'coins-insert-popup__input')]")
    private WebElement taskCostField;
    @FindBy(xpath = "//button[contains(@class,'coins-insert-popup__button')]")
    private WebElement addCoinsButton;
    @FindBy(xpath = "")
    private WebElement taskDescription;
    @FindBy(xpath = "")
    private WebElement subTaskField;
    @FindBy(xpath = "")
    private WebElement addSubTaskButton;
    @FindBy(xpath = "//button[@class='add-task-footer__add-timer-button add-timer-button']")
    private WebElement taskTimerIcon;
    @FindBy(xpath = "//input[@class='timer-input']")
    private WebElement inputTimer;
    @FindBy(xpath = "//button[@class='set-timer-popup__button']")
    private WebElement saveTamerButton;

    @FindBy(xpath = "//button[contains(@class,'set-timer__hrs time-up')]")
    private WebElement hourArrowUp;
    @FindBy(xpath = "//button[contains(@class,'set-timer__hrs time-down')]")
    private WebElement hourArrowDown;
    @FindBy(xpath = "//button[contains(@class,'set-timer__min time-up')]")
    private WebElement minuteArrowUp;
    @FindBy(xpath = "//button[contains(@class,'set-timer__min time-down')]")
    private WebElement minuteArrowDown;

    @FindBy(xpath = "")
    private WebElement taskDateIcon;
    @FindBy(className = "")
    private WebElement taskRepetitionIcon;
    @FindBy(className = "")
    private WebElement taskNecessityIcon;

    /**
     *
     */
    public AddTaskPage() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * Method for adding new task in appropriate sphere
     *
     * @param createdTaskTitle created task title
     * @param taskCost         created task cost
     */
    public void newTask(final String createdTaskTitle, final String taskCost) {
        taskTitle.clear();
        taskTitle.sendKeys(createdTaskTitle);
        taskCostIcon.click();
        taskCostField.clear();
        taskCostField.sendKeys(taskCost);
        addCoinsButton.click();
    }

    /**
     * click on timer icon
     */
    public void timerIconClick() {
        taskTimerIcon.click();
    }

    /**
     * method that input the timer value
     *
     * @param tensHour    tens Hour
     * @param unitsHour   units Hour
     * @param tensMinute  tens Minute
     * @param unitsMinute units Minute
     */
    public void setTimer(final String tensHour, final String unitsHour, final String tensMinute, final String unitsMinute) {
        inputTimer.sendKeys(tensHour, unitsHour, tensMinute, unitsMinute);

    }

    /**
     * method that returns timer value
     *
     * @return timer value
     */
    public WebElement getTimer() {
        return inputTimer;
    }

    /**
     * method for resetting the timer value
     */
    public void clearTimer() {
        inputTimer.sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);
    }

    /**
     * click on saveTimer button
     */
    public void saveTimerButtonClick() {
        saveTamerButton.click();
    }

    /**
     * click on addTask button
     */
    public void addTaskButtonClick() {
        addTaskButton.click();
    }

    /**
     * click on the hour arrow up
     */
    public void hourArrowUpClick() {
        hourArrowUp.click();
    }

    /**
     * click on the hour arrow down
     */
    public void hourArrowDownClick() {
        hourArrowDown.click();
    }

    /**
     * click on the minute arrow up
     */
    public void minuteArrowUpClick() {
        minuteArrowUp.click();
    }

    /**
     * click on the minute arrow down
     */
    public void minuteArrowDownClick() {
        minuteArrowDown.click();
    }

    /**
     * method that clicks on timer arrows
     *
     * @param arrowUpHourClick     click on the up arrow for an hour
     * @param arrowUpMinuteClick   click on the up arrow for a minute
     * @param arrowDownHourClick   click on the down arrow for an hour
     * @param arrowDownMinuteClick click on the up arrow for an minute
     */
    public void setTimerByArrow(final int arrowUpHourClick, final int arrowUpMinuteClick, final int arrowDownHourClick, final int arrowDownMinuteClick) {
        for (int clickCounterArrowUpHour = 0; clickCounterArrowUpHour < arrowUpHourClick; clickCounterArrowUpHour++) {
            hourArrowUpClick();
        }
        for (int clickCounterArrowUpMinute = 0; clickCounterArrowUpMinute < arrowUpMinuteClick; clickCounterArrowUpMinute++) {
            minuteArrowUpClick();
        }
        for (int clickCounterArrowDownHour = 0; clickCounterArrowDownHour < arrowDownHourClick; clickCounterArrowDownHour++) {
            hourArrowDownClick();
        }
        for (int clickCounterArrowDownMinute = 0; clickCounterArrowDownMinute < arrowDownMinuteClick; clickCounterArrowDownMinute++) {
            minuteArrowDownClick();
        }
    }
}
