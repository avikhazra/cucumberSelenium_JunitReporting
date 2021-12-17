package memberWeb;

import io.cucumber.java.an.E;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class CommonLIB {

    private WebDriver driver;
    public CommonLIB(WebDriver driver){
        this.driver=driver;
    }
    public void  Click(By ele) {
        try {
            String previuosDomproperty = driver.getPageSource().toString();

            WebElement e = waiting(5).until(ExpectedConditions.elementToBeClickable(ele));
            String AfterDomproperty = driver.getPageSource().toString();
            int i = 0;
            while (previuosDomproperty.equals(AfterDomproperty)) {
                getJavascriptExecuter().executeScript("arguments[0].click();", e);

                AfterDomproperty = driver.getPageSource().toString();
                if (i == 3) {
                    getJavascriptExecuter().executeScript("arguments[0].click();", ele);

                    break;
                }
                i++;

            }
        }catch (Exception ex){
            Assert.assertTrue("click on Object is failed due to"+ex.getMessage(), false);
        }
    }
    public JavascriptExecutor getJavascriptExecuter() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;

    }
    public void  Write(By ele,String strtestdata) {
        try {
            WebElement e = waiting(5).until(ExpectedConditions.elementToBeClickable(ele));
                e.click();
                e.clear();
                e.sendKeys(strtestdata);
        }catch(Exception ex) {

            Assert.assertTrue("Exception is "+ ex.toString() + " for causing :" + ex.getMessage(),false);
        }
    }
    public void  Click(WebElement e){

        try{
            e.click();
        }catch (Exception ex){
            try{
                getJavascriptExecuter().executeScript("arguments[0].click();", e);
            }catch (Exception exe){
                Assert.assertTrue("click on Object is failed",false);
            }
        }
    }

    public void  Write(WebElement e,String strtestdata) {
        try {

            e.click();
            e.clear();
            e.sendKeys(strtestdata);
        }catch(Exception ex) {

            Assert.assertTrue("Exception is "+ ex.toString() + " for causing :" + ex.getMessage(),false);
        }
    }

    public  String  ReplacedXpath( By Xpath, String ReplacedValue,String Value) {
        return	Xpath.toString().replace(ReplacedValue, Value.toString()).replace("By.xpath:", "");
    }
    public  String  GetElementText( WebElement e) {
        String Text="";
        try {
            Text= e.getText().toString();
            if(Text.equals("")){
                Text= e.getAttribute("innerText");
            }

        }catch(Exception ex) {
            Assert.assertTrue(e.toString(),false);
        }
        return Text;
    }
    public  String  GetElementText( By ele) {
        String Text="";
        WebElement e= driver.findElement(ele);
        try {
            Text= e.getText().toString();
            if(Text.equals("")){
                Text= e.getAttribute("innerText");
            }

        }catch(Exception ex) {
            Assert.assertTrue(e.toString(),false);
        }
        return Text;
    }
    public List<WebElement> GetElements (By e) {
        List<WebElement>eles=driver.findElements(e);
        return eles;
    }
    public void Waitfor(By ele, int time) {

        while(time>0) {
            try {
                WebElement foo = waiting(5).until(new Function<WebDriver, WebElement>() {
                    public WebElement apply(WebDriver driver) {
                        return driver.findElement(ele);
                    }
                });
                if (foo.isDisplayed()) {
                    return;
                }
            } catch (Exception e) {
                     time--;
            }
        }
    }
public Wait<WebDriver>  waiting(int time){
    Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
            .withTimeout(Duration.ofSeconds(time))
            .pollingEvery(Duration.ofSeconds(1))
            .ignoring(NoSuchElementException.class);
return  wait;

}
public void CheckBox(By ele){

        WebElement e= driver.findElement(ele);
         e.click();
         try {
             if (!e.isSelected() || e.getAttribute("checked").equalsIgnoreCase("")) {
                 e.sendKeys(Keys.SPACE);
             }
         }catch (Exception ex){

         }
}


}
