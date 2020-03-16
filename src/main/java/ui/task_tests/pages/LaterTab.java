package ui.task_tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

import java.util.List;

public class LaterTab {

    @FindAll({
            @FindBy(xpath = "//h1[contains(@class,'sphere__header-title')]")
    })
    private List<WebElement> sphereTitle;

    /**
     * Later page constructor
     */
    public LaterTab() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * method that returns List of sphere title
     *
     * @return List of sphere title
     */
    public List<WebElement> getSphereTitle() {
        return sphereTitle;
    }

}

