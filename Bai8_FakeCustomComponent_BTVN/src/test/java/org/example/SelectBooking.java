package org.example;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SelectBooking {
    WebDriver driver;

    @Before
    public void startChrome() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        this.driver.get("https://www.booking.com/");
        this.driver.manage().window().maximize();

    }


    @Test
    public void searchTextbox() {
        String Searchvaule = "HA NOI";
        String ChuyendoiSearchvaule = Searchvaule.toUpperCase().replaceAll("\\s+", " ");


        WebElement searchTxt = driver.findElement(By.cssSelector("input[type=search]"));
        searchTxt.sendKeys("HA NOI");
        WebDriverWait Wait = new WebDriverWait(this.driver, 15);
        Wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("ul.-visible"))));
        List<WebElement> list = driver.findElements(By.cssSelector("ul.-visible"));
        for (int i = 0; i < list.size(); i++) {
            String Chuanchuoielemt = list.get(i).getText().replaceAll("\\s+", " ") /*xóa đi khoảng trắng thừa */;
            String InHoaKyTu = Chuanchuoielemt.toUpperCase();
            Assert.assertTrue(InHoaKyTu.contains(ChuyendoiSearchvaule));

        }


        }
    @Test
    public void Bookingday(){
        WebElement checkInCheckOut = driver.findElement(By.cssSelector("span[class=\"sb-date-field__icon sb-date-field__icon-btn bk-svg-wrapper calendar-restructure-sb\"]"));
        checkInCheckOut.click();
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 15);
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("div[class=\"bui-calendar__month\"]"))));
        DateTimeFormatter Format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime checkIn = LocalDateTime.now().plusDays(1);
        LocalDateTime checkOut = LocalDateTime.now().plusDays(3);
        String elementCheckIn = "td[data-date=\"" + Format.format(checkIn) + "\"]";
        String elementCheckOut = "td[data-date=\"" + Format.format(checkOut) + "\"]";
        WebElement checkInItem = driver.findElement(By.cssSelector(elementCheckIn));
        checkInItem.click();
        WebElement checkOutItem = driver.findElement(By.cssSelector(elementCheckOut));
        checkOutItem.click();


    }

    @Test
    public void  persionChoose(){
        WebElement componentAdultChildRoom = driver.findElement(By.cssSelector("span.xp__guests__count"));
        componentAdultChildRoom.click();
        //Doi den khi mo Popup
        WebDriverWait webDriverWait = new WebDriverWait(this.driver, 15);
        webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("button[aria-label=\"Decrease number of Adults\"]"))));
        WebElement buttonDecreaseAdults = driver.findElement(By.cssSelector("button[aria-label=\"Decrease number of Adults\"]"));
        WebElement buttonIncreaseAdults = driver.findElement(By.cssSelector("button[aria-label=\"Increase number of Adults\"]"));
        WebElement buttonDecreaseChildren = driver.findElement(By.cssSelector("button[aria-label=\"Decrease number of Children\"]"));
        WebElement buttonIncreaseChildren = driver.findElement(By.cssSelector("button[aria-label=\"Increase number of Children\"]"));
        WebElement buttonDecreaseRoom = driver.findElement(By.cssSelector("button[aria-label=\"Decrease number of Rooms\"]"));
        WebElement buttonIncreaseRoom = driver.findElement(By.cssSelector("button[aria-label=\"Increase number of Rooms\"]"));
        int currentValueAdults = Integer.parseInt(driver.findElement(By.cssSelector("div.sb-group__field-adults span.bui-stepper__display")).getText());
        int currentValueChildren = Integer.parseInt(driver.findElement(By.cssSelector("div.sb-group-children span.bui-stepper__display")).getText());
        int currentValueRoom = Integer.parseInt(driver.findElement(By.cssSelector("div.sb-group__field-rooms span.bui-stepper__display")).getText());
        while (currentValueAdults < 5) {
            buttonIncreaseAdults.click();
            currentValueAdults++;
        }
        while (currentValueChildren < 1) {
            currentValueChildren++;
            buttonIncreaseChildren.click();
        }
        while (currentValueRoom < 3) {
            currentValueRoom++;
            buttonIncreaseRoom.click();
        }

    }



    @After
    public void afterTest() {
        driver.quit();
    }
}
