package ui.task_tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

import java.util.List;

public class TodayTabPage {
    @FindBy(xpath = "//button[@class='add-sphere-button']")
    private WebElement createSphereButton;
    @FindAll({
            @FindBy(xpath = "//h1[contains(@class,'sphere__header-title')]")
    })
    private List<WebElement> sphereTitle;
    @FindBy(xpath = "//input[contains(@class,'add-task-button')]")
    private WebElement addTaskButton;
    @FindAll({
            @FindBy(xpath = "//button[contains(@class,'sphere__header-settings-button')]")
    })
    private List<WebElement> sphereMenu;
    @FindBy(xpath = "//div[@class='dropdown-menu show']//button[@class='dropdown-item'][contains(text(),'Delete')]")

    private WebElement deleteSphereButtonFromMenu;
    @FindBy(xpath = "//button[contains(@class,'sphere__delete-popup__button')]")
    private WebElement deleteSpherePopupButton;
    /**
     * Today page constructor
     */
    public TodayTabPage() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * click on 'create Sphere' button
     */
    public void createSphereButtonClick() {
        createSphereButton.click();
    }

    /**
     * method that returns List of sphere title
     *
     * @return sphere title list
     */
    public List<WebElement> getSphereTitle() {
        return sphereTitle;
    }

    public void addTaskButtonClick() {
        addTaskButton.click();
    }

    /**
     * Method for delete new sphere on tab
     */
    public void deleteSphere(int index) {
        sphereMenu.get(index).click();
        deleteSphereButtonFromMenu.click();
        deleteSpherePopupButton.click();
    }
}
