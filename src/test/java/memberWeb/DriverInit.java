package memberWeb;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class DriverInit {

    private WebDriver driver;
    private String Excelpath;
    private String Sheet;
    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    private Properties prop;
    public WebDriver init_driver(String browser) {

        System.out.println("browser value is: " + browser);

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            tlDriver.set(new ChromeDriver());
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            tlDriver.set(new FirefoxDriver());
        } else if (browser.equals("safari")) {
            tlDriver.set(new SafariDriver());
        } else {
            System.out.println("Please pass the correct browser value: " + browser);
        }

        getDriver().manage().deleteAllCookies();
        getDriver().manage().window().maximize();
        return getDriver();

    }
    public static synchronized WebDriver getDriver() {
        return tlDriver.get();
    }
    public Properties init_prop() {

        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream("./src/test/resources/project.properties");
            prop.load(ip);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return prop;

    }


    public  Map<String,String> ExtractDataWithFillo(String Excelpath,String sheetName) {
        this.Excelpath=Excelpath;
        this.Sheet=sheetName;
            Map<String,String> HashMap= new HashMap<>();
        try {

            Fillo fillo = new Fillo();

            Connection connection = fillo.getConnection(Excelpath);
            Recordset recordset = connection.executeQuery("SELECT * FROM "+sheetName);
            int numberOfRows = recordset.getCount();
            System.out.println("Size: " + numberOfRows);
            int i = 0;

            while (recordset.next()) {
                if(recordset.getField("Used").contains("")) {
                    HashMap.put("UserName",recordset.getField("UserName"));
                    HashMap.put("Password",recordset.getField("Password"));
                    break;
                }
            }
            recordset.close();
            connection.close();
        }catch (Exception e) {
        }
            return HashMap;
    }
    public void UpdateExcel(String value){
        String query = "UPDATE "+Sheet+" SET Used='Y' WHERE Username='"+value+"'";
        try {
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(Excelpath);
            connection.executeUpdate(query);
            connection.close();
        } catch (FilloException e) {
            //e.printStackTrace();
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


}
