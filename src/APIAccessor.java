import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.Gson; 

/**
 * Class that accesses the current price of Bitcoin from the CoinDesk API
 * 
 * @author Aron Saengchan
 */
public class APIAccessor {

    /**
     * Information on the current price of Bitcoin
     */
    private BitcoinPrice price;
    
    /**
     * Constructor to initialize the APIAccessor object
     */
    public APIAccessor() {}

    /**
     * Retrieve data from the API
     * @param inputUrl: URL link to the API
     * @return the current price of Bitcoin
     */
    public BitcoinPrice retrieveData(String inputUrl) {
        try {
            // Access CoinDesk API from its URL
            URL url = new URL(inputUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                // If response code 200 is received, throw error
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                // Otherwise, read JSON file  
                String json = "";
                Scanner scanner = new Scanner(url.openStream());

                // Write all the JSON data into a String using the Scanner
                while (scanner.hasNext()) {
                    json += scanner.nextLine();
                }

                scanner.close();

                // Deserialize JSON to BitcoinPrice object
                Gson gson = new Gson();
                price = gson.fromJson(json, BitcoinPrice.class);
            }
        } catch (Exception e) {
            // Print stack trace if error occurs
            e.printStackTrace();
        }

        return price;
    }
}