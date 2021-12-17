package PageObjects;

import org.openqa.selenium.By;

public class CommonPage {
public By Login = By.xpath("//a[text()=' Login']");

    public By Username= By.id("user_email");
    public By Password= By.id("user_password");
    public By SignIN= By.cssSelector("input[name='commit']");

}
