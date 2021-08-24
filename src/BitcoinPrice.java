import com.google.gson.JsonObject;

/**
 * Class containing information on the current price of Bitcoin
 * 
 * @author Aron Saengchan
 */
public class BitcoinPrice {
    
    /**
     * Time the API was accessed
     */
    private JsonObject time;

    /**
     * Disclaimer from the API
     */
    private String disclaimer;

    /**
     * Chart name of the API
     */
    private String chartName;

    /**
     * Exchange rate of Bitcoin (Bitcoin Price Index)
     */
    private JsonObject bpi;

    /**
     * Constructor that initializes the BitcoinPrice object
     * @param time: time the API was accessed
     * @param disclaimer: Disclaimer from the API
     * @param chartName: Chart name of the API
     * @param bpi: exchange rate of Bitcoin
     */
    public BitcoinPrice(JsonObject time, String disclaimer, String chartName, JsonObject bpi) {
        this.time = time;
        this.disclaimer = disclaimer;
        this.chartName = chartName;
        this.bpi = bpi;
    }

    /**
     * Getter that retrieves the time the API was accessed
     * @return time the API was accessed
     */
    public JsonObject getTime() {
        return this.time;
    }

    /**
     * Getter that retrieves the API's disclaimer 
     * @return the API's disclaimer
     */
    public String getDisclaimer() {
        return this.disclaimer;
    }

    /**
     * Getter that retrieves the API's chart name
     * @return the API's chart name
     */
    public String getChartName() {
        return this.chartName;
    }

    /**
     * Getter that retrieves the API's Bitcoin Price Index
     * @return the API's Bitcoin Price Index
     */
    public JsonObject getBPI() {
        return this.bpi;
    }

    /**
     * Getter that retrieves the exchange rate of Bitcoin from it's BPI
     * @param currency: a monetary currency 
     * @return the exchange rate of Bitcoin with the specified currency
     */
    public double getRate(String currency) {
        return Double.parseDouble(this.getBPI().get(currency).getAsJsonObject().get("rate").toString().replaceAll("\"|,", ""));
    }
}