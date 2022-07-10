/**
 * Creates Listings class and specifies parameters for use
 */
public class Listings {

    private String listingTitle, listingURL, listingCurrentPrice;

    /**
     * Creates Listing object to be used in storing of listing data in a list
     * @param listingTitle Title of listing on webpage
     * @param listingURL URL of the same listing on webpage
     * @param listingCurrentPrice Current price of the same listing on webpage
     */
    public Listings(String listingTitle, String listingURL, String listingCurrentPrice) {
        this.listingTitle = listingTitle;
        this.listingURL = listingURL;
        this.listingCurrentPrice = listingCurrentPrice;
    }

    /**
     * Returns Title: + listing title
     * @return Title of the listing
     */
    public String getListingTitle() {
        return "Title: " + this.listingTitle;
    }

    /**
     * Returns URL: + listing URL
     * @return URL of the listing
     */
    public String getListingURL() {
        return "URL: " + this.listingURL;
    }

    /**
     * Returns Current price: + listing current price
     * @return Current price of the listing
     */
    public String getListingCurrentPrice() {
        return "Current price: " + this.listingCurrentPrice;
    }
}

