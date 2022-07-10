import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.util.ArrayList;
/**
 * Creates WebScraper class, contains execution starting point, creates Listings list and specifies parameters for use
 */
public class WebScraper {
    ArrayList<Listings> listings = new ArrayList<>();

    WebDriver driver;
    FileWriter fileWriter;
    Desktop desktop;
    File file;
    String listingTitle, listingURL, listingCurrentPrice, filePath, xPathTitleVariable, xPathURLVariable, xPathCurrentPriceVariable;

    /**
     * Creates new web scraper and starts the program execution
     * @param args command line arguments
     */
    public static void main(String[] args) {
        WebScraper scraper = new WebScraper();
        scraper.invokeWebpage();
        scraper.getListingDataFromPage();
        scraper.quitScraping();
        scraper.writeListingDataToFile();
        scraper.openListingDataFile();
    }

    /**
     * Opens chrome browser, deletes all cookies, maximizes the browser window, goes onto the specified webpage and accepts cookies.
     */
    public void invokeWebpage() {
        try {
            driver = new ChromeDriver();
            driver.manage().deleteAllCookies();
            driver.manage().window().maximize();
            waitUpTo30Seconds();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            driver.get("https://www.osta.ee/index.php?fuseaction=search.search&id=1000&orderby=createdd&q%5Bq%5D=tolkien&q%5Bshow_items%5D=1&q%5Bshow_shop%5D=1&q%5Bcat%5D=1000&q%5Bfuseaction%5D=search.search");
            waitUpTo30Seconds();
            driver.findElement(By.id("onetrust-accept-btn-handler")).click();

        } catch (Exception e) {
            driver.quit();
            System.out.println("Error message: " + e.getMessage());
        }
    }

    /**
     * Waits up to 30 seconds for the next action to become available on webpage. Timeouts after 30 seconds.
     */
    public void waitUpTo30Seconds() {
        try {
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        } catch (Exception e) {
            driver.quit();
            System.out.println("Error message: " + e.getMessage());
        }
    }

    /**
     * Specifies file path where data is written. Goes through specified arraylist and writes each entry to text file, after which the method clears the list.
     */
    public void writeListingDataToFile() {
        try {
            filePath = "C:\\Results.txt";
            fileWriter = new FileWriter(filePath, false);

            for (Listings listing : listings) {
                fileWriter.write("-".repeat(100) + System.lineSeparator());
                fileWriter.write(listing.getListingTitle() + System.lineSeparator());
                fileWriter.write(listing.getListingURL() + System.lineSeparator());
                fileWriter.write(listing.getListingCurrentPrice() + System.lineSeparator());
            }

            fileWriter.close();
            listings.clear();

        } catch (Exception e) {
            driver.quit();
            System.out.println("Error message: " + e.getMessage());
        }
    }

    /**
     * Uses xpath to get title, URL and current price from up to 10 listings with matching conditions. Adds that data to specified arraylist.
     */
    public void getListingDataFromPage() {
        for (int index = 1; index <= 10; index++) {
            try {
                xPathTitleVariable = "(//h3[contains(translate(., 'TOLKIEN', 'tolkien'),'tolkien')])" + "[" + index + "]";
                xPathURLVariable = "(//a[contains(translate(., 'TOLKIEN', 'tolkien'),'tolkien')])" + "[" + index + "]";
                xPathCurrentPriceVariable = "(//div[@class = 'offer-thumb__price--current'])" + "[" + index + "]";

                listingTitle = driver.findElement(By.xpath(xPathTitleVariable)).getText();
                listingURL = driver.findElement(By.xpath(xPathURLVariable)).getAttribute("href");
                listingCurrentPrice = driver.findElement(By.xpath(xPathCurrentPriceVariable)).getText();

                listings.add(new Listings(listingTitle, listingURL, listingCurrentPrice));

            } catch (Exception e) {
                driver.quit();
                System.out.println("Error message: " + e.getMessage());
            }
        }
    }

    /**
     * Ends all operations with chromedriver
     */
    public void quitScraping() {
        driver.quit();
    }

    /**
     * Opens the file where the scraped data from listings was written.
     */
    public void openListingDataFile() {
        try {
            desktop = Desktop.getDesktop();
            file = new File("C:\\Results.txt");
            desktop.open(file);

        } catch (Exception e) {
            driver.quit();
            System.out.println("Error message: " + e.getMessage());
        }
    }
}