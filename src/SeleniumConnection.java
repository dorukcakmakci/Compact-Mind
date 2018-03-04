import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Created by kaan on 3/4/2018.
 */
public class SeleniumConnection {

    private WebDriver webDriver;
    public SeleniumConnection(){
        //open driver connection
        System.setProperty("webdriver.chrome.driver", "./chromedriver.exe");
        System.setProperty("webdriver.gecko.driver", "./geckodriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-position=-32000,-32000");
        webDriver = new ChromeDriver(options);

        //Open puzzle page
        webDriver.get("https://www.nytimes.com/crosswords/game/mini");
        webDriver.findElements(By.className("buttons-modalButton--1REsR")).get(0).click();
        List<WebElement> li = webDriver.findElements(By.cssSelector("li.Tool-button--39W4J:nth-child(2) > button:nth-child(1)"));
        li.get(0).click();
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div/main/div[2]/div/div/ul/div[1]/li[2]/ul/li[3]/a")).click();
        webDriver.findElements(By.xpath("/html/body/div[1]/div/div[2]/div[2]/article/div[2]/button[2]")).get(0).click();
        webDriver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[2]/div/a")).click();

        String pageSource = webDriver.getPageSource();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get( Calendar.DAY_OF_WEEK);
        int year = cal.get( Calendar.YEAR);

        String puzzlePath = "./ph/reveal-" + month + "-" + day + "-" + year + ".txt";

        File file = new File( puzzlePath);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(puzzlePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scan = new Scanner( pageSource);
        String inputLine;
        try {
            while ((inputLine = scan.nextLine()) != null){
                try{
                    writer.write(inputLine);
                }
                catch(IOException e){

                    e.printStackTrace();
                    return;
                }
            }
        } catch (Exception e) {

            e.printStackTrace();
            return;
        }



        webDriver.getPageSource();
    }
}
