package ui.task_tests.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ui.Configurator;

import java.util.List;

public class TomorrowTab {
    @FindAll({
            @FindBy(xpath = "//h1[contains(@class,'sphere__header')]")
    })
    private List<WebElement> sphereTitle;

    /**
     * Tomorrow page constructor
     */
    public TomorrowTab() {
        PageFactory.initElements(Configurator.getDriver(), this);
    }

    /**
     * method that returns sphere title
     *
     * @return sphere title
     */
    public List<WebElement> getSphereTitle() {
        return sphereTitle;
    }

}

