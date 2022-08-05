
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import org.w3c.dom.html.HTMLFieldSetElement;
import resource.Base;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PandaPage extends Base {

    private WebDriver driver;
    String fname = "bat";
    String lname = "man";
    public static String screenshotPath;
    public static String screenshotName;
    public static Logger log = LogManager.getLogger(Base.class.getName());



    @BeforeMethod
    void initialize() {
        driver = initDriver();
        log.info("Driver initialised");
    }

    @Test//(enabled = false)
    public void sortProduct() {
        driver.get("http://live.techpanda.org/");
        log.info("Sort product, page loaded");
        if (driver.getTitle().equals("Home page")) {
            System.out.println("Home page title verified");
            log.info("Title verified");
        } else {
            System.out.println("Title mismatch. Expected title = " + driver.getTitle());
        }
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();

        if (driver.getTitle().equals("Mobile")) {
            System.out.println("Mobile page title verified");
            log.info("Mobile page title verified");
        } else {
            System.out.println("Title mismatch. Expected title = " + driver.getTitle());
        }
        Select select = new Select(driver.findElement(By.xpath("//select[@title=\"Sort By\"]")));
        select.selectByVisibleText("Name");
        List<WebElement> products = driver.findElements(By.xpath("//h2[@class=\"product-name\"]//a"));
        String[] alttext = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            String str = products.get(i).getAttribute("title");
            alttext[i] = str;
        }
        String[] b = Arrays.copyOf(alttext, alttext.length);
        Arrays.sort(alttext);

        if (Arrays.equals(b, alttext)){
            System.out.println("Same");
        log.info("Sorting verified");}
        else
            System.out.println("Not same");

    }

    @Test//(enabled = false)
    public void costVerify() {
        driver.get("http://live.techpanda.org/");
        log.info("Verifying cost method");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        String price = driver.findElement(By.xpath("//*[@id=\"product-price-1\"]/span")).getText();
        driver.findElement(By.xpath("//a[@title=\"Sony Xperia\"]")).click();
        String cost = driver.findElement(By.xpath("//*[@id=\"product-price-1\"]/span")).getText();
        Assert.assertEquals(price, cost);
        log.info("Sony xperia product verified");
    }

    @Test//(enabled = false)
    public void addCart() {
        driver.get("http://live.techpanda.org/");
        log.info("Addding to cart method");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/button/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/input")).sendKeys("000");
        driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[4]/button")).click();
        String str = driver.findElement(By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr/td[2]/p")).getText();
        Assert.assertEquals("* The maximum quantity allowed for purchase is 500.", str);
        driver.findElement(By.xpath("//*[@id=\"empty_cart_button\"]/span/span")).click();
        String emp = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/div[2]/p[1]")).getText();
        Assert.assertEquals("You have no items in your shopping cart.", emp);
        log.info("Shopping cart empty and quantity verified");
    }

    @Test//(enabled = false)
    public void comparePhone() throws InterruptedException {
        driver.get("http://live.techpanda.org/");
        log.info("Compare phones method");
        driver.findElement(By.xpath("//*[@id=\"nav\"]/ol/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[1]/div/div[3]/ul/li[2]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[3]/ul/li[2]/div/div[3]/ul/li[2]/a")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//button[contains(@onclick,\"popWin\")]")).click();
        Iterator<String> windowIterator = driver.getWindowHandles().iterator();
        while (windowIterator.hasNext()) {
            String windowHandle = windowIterator.next();
            driver.switchTo().window(windowHandle);
        }
        log.info("Window pop up appears");

        String xp = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[1]/h2/a")).getText();
        Assert.assertEquals("SONY XPERIA", xp);
        String ip = driver.findElement(By.xpath("//*[@id=\"product_comparison\"]/tbody[1]/tr[1]/td[2]/h2/a")).getText();
        Assert.assertEquals("IPHONE", ip);
        Thread.sleep(3000);
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div[2]/button/span/span")).click();
        log.info("Popup closed after verifying selected products");
    }

    @Test(enabled = false)
    public void createAcc() {

        driver.get("http://live.techpanda.org/");
        log.info("Homepage loaded for creating account");
        driver.findElement(By.xpath("//*[@id=\"header\"]/div/div[2]/div/a")).click();
        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"login-form\"]/div/div[1]/div[2]/a/span/span")).click();
        log.info("Firstname: "+fname+"  "+"Lastname"+lname+"  passed");
        driver.findElement(By.xpath("//*[@id=\"firstname\"]")).sendKeys(fname);
        driver.findElement(By.xpath("//*[@id=\"lastname\"]")).sendKeys(lname);
        driver.findElement(By.xpath("//*[@id=\"email_address\"]")).sendKeys(fname + lname + "@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"confirmation\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//*[@id=\"form-validate\"]/div[2]/button")).click();
        String reg = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div/ul/li/ul/li/span")).getText();
        Assert.assertEquals("Thank you for registering with Main Website Store.", reg);
log.info("Registration complete");
        driver.findElement(By.linkText("TV")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div[1]/div[2]/ul/li[1]/div/div[3]/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"wishlist-view-form\"]/div/div/button[1]/span/span")).click();
        driver.findElement(By.xpath("//*[@id=\"email_address\"]")).sendKeys("thisemail@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"message\"]")).sendKeys("CHeck this out");
        driver.findElement(By.xpath("//button[@title=\"Share Wishlist\"]")).click();
        log.info("Wishlist shared");
        String wish = driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/ul/li/ul/li/span")).getText();
        Assert.assertEquals("Your Wishlist has been shared.", wish);

    }

    @Test(enabled = false)//(priority = 2)
    public void checkOut() throws IOException {

        driver.get("http://live.techpanda.org/");
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys(fname+lname + "@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@title=\"Login\"]")).click();
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.xpath("//a[contains(@title,\"My Wishlist\")]")).click();
        driver.findElement(By.xpath("//button[contains(@title,\"Add to Cart\")]")).click();
        driver.findElement(By.xpath("//button[contains(@title,\"Proceed to Checkout\")]")).click();

        driver.findElement(By.xpath("//*[@id=\"billing:firstname\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:firstname\"]")).sendKeys(fname);
        driver.findElement(By.xpath("//*[@id=\"billing:lastname\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:lastname\"]")).sendKeys(lname);
        driver.findElement(By.xpath("//*[@id=\"billing:street1\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:street1\"]")).sendKeys("ABC");
        driver.findElement(By.xpath("//*[@id=\"billing:city\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:city\"]")).sendKeys("New York");
        Select select = new Select(driver.findElement(By.xpath("//*[@id=\"billing:region_id\"]")));
        select.selectByVisibleText("New York");
        driver.findElement(By.xpath("//*[@id=\"billing:postcode\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:postcode\"]")).sendKeys("542879");
        driver.findElement(By.xpath("//*[@id=\"billing:telephone\"]")).clear();
        driver.findElement(By.xpath("//*[@id=\"billing:telephone\"]")).sendKeys("12345687");

        driver.findElement(By.xpath("//button[contains(@title,\"Continue\")]")).click();
        String flat = driver.findElement(By.xpath("//label//span[@class=\"price\"]")).getText();

        Assert.assertEquals("$5.00", flat);
        driver.findElement(By.xpath("//*[@id=\"shipping-method-buttons-container\"]/button")).click();
        driver.findElement(By.xpath("//*[@id=\"dt_method_checkmo\"]/label")).click();

        driver.findElement(By.xpath("//*[@id=\"payment-buttons-container\"]/button")).click();
        String rate=driver.findElement(By.xpath("//*[@id=\"checkout-review-table\"]/tfoot/tr[2]/td[1]")).getText();
        Assert.assertEquals("Shipping & Handling (Flat Rate - Fixed)",rate);

        driver.findElement(By.xpath("//*[@id=\"review-buttons-container\"]/button")).click();
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/h2")));
        String confirm=driver.findElement(By.xpath("//h1")).getText();
        Assert.assertEquals("YOUR ORDER HAS BEEN RECEIVED.",confirm);
        String order=driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div/p[1]/a")).getText();
        System.out.printf("Your order number is"+ order);
        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        screenshotPath = System.getProperty("user.dir") + "\\report\\" + screenshotName;
        File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        File destination = new File(screenshotPath);
        FileHandler.copy(source, destination);

    }

    @Test
    public void printOrder() throws InterruptedException, AWTException {
        driver.get("http://live.techpanda.org/");
        driver.findElement(By.linkText("ACCOUNT")).click();
        driver.findElement(By.linkText("My Account")).click();
        driver.findElement(By.xpath("//*[@id=\"email\"]")).sendKeys("lom@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"pass\"]")).sendKeys("123456");
        driver.findElement(By.xpath("//button[@title=\"Login\"]")).click();
       String str=driver.findElement(By.xpath("//*[@id=\"my-orders-table\"]/tbody/tr/td[5]/em")).getText();

        Assert.assertEquals(str,"Pending");
//        driver.findElement(By.linkText("ACCOUNT")).click();
//        driver.findElement(By.xpath("//*[@id=\"header-account\"]/div/ul/li[1]/a")).click();
        driver.findElement(By.xpath("//*[@id=\"my-orders-table\"]/tbody/tr/td[6]/span/a[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"top\"]/body/div/div/div[2]/div/div[2]/div/div[1]/a[2]")).click();
       //Thread.sleep(5000);
        Robot robot= new Robot();
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }

    //@AfterMethod
    public void teardown(){
        driver.quit();
    }
}

