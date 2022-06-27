import UtilityPack.Utility;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrbitzDemo {
    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\omina\\Downloads\\chromedriver_win32\\chromedriver.exe");
       ChromeOptions options = new ChromeOptions();
       options.addArguments("--disable-blink-features=AutomationControlled");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://www.orbitz.com/");
        driver.findElement(By.xpath("//button[@aria-label='Going to']")).click();
       Thread.sleep(1000);
      driver.findElement(By.id("location-field-destination")).sendKeys("Orlando",Keys.ENTER);
       Thread.sleep(2000);
      driver.findElement(By.id("d1-btn")).click();
      driver.findElement(By.xpath("(//table[@class='uitk-date-picker-weeks'])[1]//tr//td//button[@data-day='20']")).sendKeys(Keys.ENTER);
      driver.findElement(By.xpath("(//table[@class='uitk-date-picker-weeks'])[1]//tr//td//button[@data-day='24']")).sendKeys(Keys.ENTER);
      driver.findElement(By.xpath("//button[@data-stid='apply-date-picker']")).click();
      driver.findElement(By.xpath("(//div[@class='uitk-field has-floatedLabel-label has-icon has-placeholder'])[4]")).click();
      driver.findElement(By.xpath("(//button[@class='uitk-layout-flex-item uitk-step-input-touch-target'])[1]")).click();
      driver.findElement(By.xpath("(//button[@class='uitk-layout-flex-item uitk-step-input-touch-target'])[4]")).click();
      driver.findElement(By.xpath("(//button[@class='uitk-layout-flex-item uitk-step-input-touch-target'])[4]")).click();
        Select selectAge1 = new Select(driver.findElement(By.xpath("//select[@id='child-age-input-0-0']")));
        selectAge1.selectByVisibleText("4");
        Select selectAge2 = new Select(driver.findElement(By.xpath("//select[@id='child-age-input-0-1']")));
        selectAge2.selectByVisibleText("8");
       driver.findElement(By.xpath("//button[@data-testid='guests-done-button']")).click();
      driver.findElement(By.xpath("//button[@data-testid='submit-button']")).click();
       driver.findElement(By.xpath("//input[@id='popularFilter-0-FREE_BREAKFAST']")).click();
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='uitk-pill-text']")));
      String breakfastExpected = driver.findElement(By.xpath("//span[@class='uitk-pill-text']")).getText();
        Assert.assertTrue(breakfastExpected.contains("Breakfast included"));
        WebElement elementClick = driver.findElement(By.xpath("//input[@id='popularFilter-0-FREE_BREAKFAST']"));
       Thread.sleep(3000);
        elementClick.click();
        Assert.assertFalse(elementClick.isSelected());
        Thread.sleep(4000);
       driver.findElement(By.xpath("(//input[@aria-valuemax='300'])[2]")).sendKeys(Keys.ARROW_LEFT);
        WebDriverWait wait1 = new WebDriverWait(driver,Duration.ofSeconds(7));
        wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='playback-filter-pill-price-0.0-270.0']")));
       WebElement lessThan = driver.findElement(By.xpath("//button[@id='playback-filter-pill-price-0.0-270.0']"));
        System.out.println(lessThan.getText());
        Assert.assertEquals(lessThan.getText(),"Less than $270");
      List<WebElement> setPrice =driver.findElements(By.xpath("//div[contains(text(), \"The price is\")]"));
      int totalsize = setPrice.size();
        System.out.println(totalsize);
      Assert.assertEquals(setPrice.size(),totalsize);
        for (WebElement onePrice : setPrice) {
            System.out.println(onePrice.getText().replaceAll("[Theprics,$ ]",""));
            int price = Integer.parseInt(onePrice.getText().replaceAll("[Theprics,$ ]",""));
            Assert.assertTrue(price<=270);
        }
          Utility.scroll(driver,0,1000);
        driver.findElement(By.xpath("//input[@id='radio-guestRating-45']")).click();
        Thread.sleep(3000);
        List<WebElement> ratingTotals = driver.findElements(By.xpath("//span[@class='uitk-text uitk-type-300 uitk-type-bold uitk-text-default-theme']"));
        int totalretingsize = ratingTotals.size();
        for (WebElement ratingEach : ratingTotals) {
            System.out.println(ratingEach.getText().substring(0,3));
           double rating =Double.parseDouble(ratingEach.getText().substring(0,3));
            Assert.assertTrue(rating>=4.5);
        }
        Double lastRating = Double.parseDouble(ratingTotals.get(totalretingsize-1).getText().substring(0,3));

        List<WebElement> nameAll = driver.findElements(By.xpath("//div[@class='uitk-spacing uitk-spacing-padding-blockend-three uitk-layout-flex-item']//h2[@class='uitk-heading-5']"));
        String nameLast = nameAll.get(totalretingsize-1).getText();
        Utility.scroll(driver,0,9500);
        System.out.println(nameLast);
        Actions act = new Actions(driver);
        act.moveToElement(ratingTotals.get(totalretingsize-1)).click().build().perform();
//



        Set<String> windowHandles = driver.getWindowHandles();  // returns all open window handles
        String nameWindow = "";
        for(String windowHandle:windowHandles){
            driver.switchTo().window(windowHandle);
            if (driver.getTitle().equals(nameLast)){
                break;
            }
            nameWindow = windowHandle;
        }
        System.out.println(driver.getTitle());
        Assert.assertEquals(driver.getTitle(),nameLast);
        Assert.assertEquals(driver.findElement(By.xpath("//h1[@class='is-visually-hidden']")).getText(),nameLast);
       double newRating = Double.parseDouble( driver.findElement(By.xpath("(//h3[@class='uitk-heading-5 uitk-spacing uitk-spacing-padding-blockend-three'])[1]")).getText().substring(0,3));
       Assert.assertEquals(newRating,lastRating);
        driver.close();
        Thread.sleep(2000);
        System.out.println(nameWindow);
        driver.switchTo().window(nameWindow);
        Utility.scroll(driver,0,-10000);
        driver.findElement(By.xpath("//img[@src='https://www.orbitz.com/_dms/header/logo.svg?locale=en_US&siteid=70201&2']")).click();
        WebElement newIframe = driver.findElement(By.xpath("//iframe[@class='vac_iframe']"));
        driver.switchTo().frame(newIframe);
        driver.findElement(By.xpath("//button[@aria-label='Get help from your Virtual Agent.']")).sendKeys(Keys.ENTER);
       Assert.assertTrue(driver.findElement(By.xpath("(//div[@class='uitk-text uitk-type-300 uitk-type-regular uitk-layout-flex-item uitk-text-default-theme'])[1]")).getText().contains("Hi, I'm your Virtual Agent "));
      driver.findElement(By.xpath("//button[@id='vac-close-button']")).click();
      driver.switchTo().defaultContent();
      driver.findElement(By.xpath("//div[@class='uitk-text uitk-type-300 uitk-text-default-theme']")).click();
        String stayVar = driver.findElement(By.xpath("//a[.='Stays']")).getText();
       String flightsVar = driver.findElement(By.xpath("//a[.='Flights'][1]")).getText();
        System.out.println(flightsVar);
        String packageVar = driver.findElement(By.xpath("(//a[.='Packages'])[1]")).getText();
        System.out.println(packageVar);
      String carVar = driver.findElement(By.xpath("(//a[.='Cars'])[1]")).getText();
        System.out.println(carVar);
        String cruisesVar = driver.findElement(By.xpath("(//a[.='Cruises'])[1]")).getText();
        System.out.println(cruisesVar);
       String thingsToDoVar = driver.findElement(By.xpath("(//a[.='Things to do'])[1]")).getText();
        System.out.println(thingsToDoVar);
       String dealsVar = driver.findElement(By.xpath("//div[.='Deals']")).getText();
        System.out.println(dealsVar);
       String groupsVar = driver.findElement(By.xpath("//div[.='Groups & meetings']")).getText();
        System.out.println(groupsVar);
        String blogVar = driver.findElement(By.xpath("(//div[.='Travel Blog'])[1]")).getText();
        System.out.println(blogVar);
       Assert.assertEquals(stayVar,"Stays");
       Assert.assertEquals(flightsVar,"Flights");
       Assert.assertEquals(packageVar,"Packages");
       Assert.assertEquals(carVar,"Cars");
       Assert.assertEquals(cruisesVar,"Cruises");
       Assert.assertEquals(thingsToDoVar,"Things to do");
       Assert.assertEquals(dealsVar,"Deals");
       Assert.assertEquals(groupsVar,"Groups & meetings");
       Assert.assertEquals(blogVar,"Travel Blog");
       driver.quit();

    }
}
