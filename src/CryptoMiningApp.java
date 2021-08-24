import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Class that represents a command-line interface to access the Crypto Mining App
 * 
 * @author Aron Saengchan
 */
public class CryptoMiningApp {
    
    /**
     * Run the Crypto Mining App program
     */
    public void run() {
        // Initialize API accessor and retrieve Bitcoin data
        APIAccessor accessor = new APIAccessor();
        BitcoinPrice allPrices = accessor.retrieveData("https://api.coindesk.com/v1/bpi/currentprice.json");
        BitcoinPrice cadPrice = accessor.retrieveData("https://api.coindesk.com/v1/bpi/currentprice/CAD.json");

        // Print the current price of Bitcoin in various currencies
        System.out.println("=====| Current price of Bitcoin |=====\n");
        System.out.printf(" $1 BTC = $%.2f USD%n", allPrices.getRate("USD"));
        System.out.printf(" $1 BTC = $%.2f GBP%n", allPrices.getRate("GBP"));
        System.out.printf(" $1 BTC = $%.2f EUR%n", allPrices.getRate("EUR"));
        System.out.printf(" $1 BTC = $%.2f CAD%n", cadPrice.getRate("CAD"));

        Scanner scanner = new Scanner(System.in);
        double bitcoinAmt;

        while(true) {
            try {
                // Prompt user to enter a Bitcoin amount of mine
                System.out.print("\nHow much Bitcoin would you like to mine? ");
                bitcoinAmt = scanner.nextDouble();
                break;
            } catch (InputMismatchException e) {
                // Print message and try again if error in invalid
                System.out.println("Input must be a number");
                scanner.next();
            }
        }

        scanner.close();
        System.out.println("\nCalculating...\n");

        // Print Bitcoin conversion of USD and CAD
        System.out.printf("$%.2f BTC converts to $%.2f USD%n", bitcoinAmt, bitcoinAmt * allPrices.getRate("USD"));
        System.out.printf("$%.2f BTC converts to $%.2f CAD%n%n", bitcoinAmt, bitcoinAmt * cadPrice.getRate("CAD"));

        System.out.println("=====| Mining Duration |=====\n");

        // Initialize MiningCalculator object to read the input data and calculate the mining times
        MiningCalculator calculator = new MiningCalculator("MiningSetup.csv", bitcoinAmt);
        double[] miningTimes = calculator.calculateIndividualMiningTime();

        // Output both individual and collective mining times
        for(int i = 0; i < calculator.getMiners().size(); i++) {
            System.out.printf(" %-20s %.2f days%n", calculator.getMiners().get(i).getName(), miningTimes[i]);
        }

        System.out.printf("\n All miners at the same time: %.2f days%n%n", calculator.calculateCollectiveMiningTime());

        System.out.println("=====| Mining Efficiency |=====\n");

        // Calculate hourly Bitcoin efficiency of the miners based on the energy rates data
        calculator.calculateHourlyEfficiency("EnergyRates.csv");
        System.out.printf("%-10s", "Hour");

        // Output hourly profit in a readable table format
        for(int i = 0; i < calculator.getMiners().size(); i++) {
            System.out.printf("%-20s", calculator.getMiners().get(i).getName());
        }

        System.out.println();

        for(int i = 0; i < calculator.getMiners().get(0).getHourlyEfficiency().length; i++) {
            System.out.printf("%-10d", i);

            for(int j = 0; j < calculator.getMiners().size(); j++) {
                double profit = calculator.getMiners().get(j).getDailyBitcoinOutput() / 24 * allPrices.getRate("USD") - calculator.getMiners().get(j).getHourlyEfficiency()[i] / 100;
                System.out.printf("$%-5.2fUSD%-11s", profit, "");
            }

            System.out.println();
        }
    }

    /**
     * Start the Crypto Mining App program
     * @param args: none
     */
    public static void main(String[] args) {
        CryptoMiningApp app = new CryptoMiningApp();
        app.run();
    }
}