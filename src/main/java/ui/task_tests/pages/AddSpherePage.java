package ui.task_tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

public class AddSpherePage {

    @FindBy(xpath = "//input[contains(@class,'add-sphere__popup__input')]")
    private WebElement sphereTitle;
    @FindBy(xpath = "//button[@class='add-sphere__popup__button']")
    private WebElement addSphereButton;

    /**
     * Add sphere page constructor
     */
    public AddSpherePage() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * Method for adding new sphere on tab
     *
     * @param createdSphereTitle created task title
     */
    public void newSphere(final String createdSphereTitle) {
        sphereTitle.clear();
        sphereTitle.sendKeys(createdSphereTitle);
    }

    /**
     * click on 'add Sphere' button
     */
    public void addSphereButtonClick() {
        addSphereButton.click();
    }
}
