package Runner;

import PageObjects.CommonPage;
import io.cucumber.java.*;
import memberWeb.CommonLIB;
import memberWeb.DriverInit;
import org.openqa.selenium.*;

import java.time.Duration;

public class Hook {
    private DriverInit DriverInit;
    private WebDriver driver;
    private String Username;
    private CommonPage CommonPage= new CommonPage();
    private CommonLIB CommonLIB;
    @Before(order = 0)
    public void launchBrowser() {
        DriverInit = new DriverInit();
        String browserName = DriverInit.init_prop().getProperty("browser");
        driver = DriverInit.init_driver(browserName);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(DriverInit.init_prop().getProperty("url"));

    }

    @Before(order = 1)
    public void GetProjectProtery() {
        String path=System.getProperty("user.dir")+DriverInit.init_prop().getProperty("excelpath");
        Username = DriverInit.ExtractDataWithFillo(path, "Test").get("UserName");
        String password = DriverInit.ExtractDataWithFillo(path, "Test").get("Password");
        CommonLIB= new CommonLIB(DriverInit.getDriver());
        CommonLIB.Waitfor(CommonPage.Login,4);
        CommonLIB.Click(CommonPage.Login);
        CommonLIB.Waitfor(CommonPage.Username,4);
        CommonLIB.Write(CommonPage.Username,Username);
        CommonLIB.Write(CommonPage.Password,password);
        CommonLIB.Click(CommonPage.SignIN);
    }

    @After(order = 0)
    public void quitBrowser() {
        DriverInit.UpdateExcel(Username);
        driver.close();
        driver.quit();
    }

    @After(order = 1)
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            // take screenshot:
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(sourcePath, "image/png", screenshotName);

        }
    }
}