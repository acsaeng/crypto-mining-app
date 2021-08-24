import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class that represents a mining calculator to compute results pertaining to the Bitcoin miners
 * 
 * @author Aron Saengchan
 */
public class MiningCalculator {
    
    /**
     * Input filename of the miner's data 
     */
    private String filename;

    /**
     * Bitcoin amount to be mined
     */
    private double bitcoinAmt;

    /**
     * List of Bitcoin miners
     */
    private ArrayList<BitcoinMiner> miners;

    /**
     * Energy rate of each hour
     */
    private double[] energyRates;

    /**
     * Constructor that initializes the MiningCalculator object
     * @param filename: input filename of the miner's data
     * @param bitcoinAmt: Bitcoin amount to be mined
     */
    public MiningCalculator(String filename, double bitcoinAmt) {
        this.filename = filename;
        this.bitcoinAmt = bitcoinAmt;
        this.miners = new ArrayList<>();
        this.extractMinerData();
        this.energyRates = new double[24];
    }

    /**
     * Getter that retrieves the Bitcoin miners' list
     * @return a list of the Bitcoin miners 
     */
    public ArrayList<BitcoinMiner> getMiners() {
        return this.miners;
    }

    /**
     * Extract the Bitcoin miners' data from the input file
     */
    public void extractMinerData() {
        try {
            Scanner scanner = new Scanner(new File(this.filename));

            // Read the input CSV file and parse the data
            while(scanner.hasNextLine()) {
                String[] line = scanner.nextLine().split(",");
                this.miners.add(new BitcoinMiner(line[0], Double.parseDouble(line[1]), Integer.parseInt(line[2])));
            }

            scanner.close();

        } catch(FileNotFoundException e) {
            // Print stack trace if error occurs
            e.printStackTrace();
        }
    }

    /**
     * Extract energy rates data from the input file
     * @param energyRatesFilename: filename of input energy rates files
     */
    private void extractEnergyRatesData(String energyRatesFilename) {
        try {
            Scanner scanner = new Scanner(new File(energyRatesFilename));

            // Read the input CSV file and parse the data
            for(int i = 0; scanner.hasNextLine(); i++) {
                String[] line = scanner.nextLine().split(",");
                this.energyRates[i] = Double.parseDouble(line[1]);
            }

            scanner.close();

        } catch(FileNotFoundException e) {
            // Print stack trace if error occurs
            e.printStackTrace();
        }
    }

    /**
     * Calculate the mining time of each individual miner
     * @return the mining times of each miner
     */
    public double[] calculateIndividualMiningTime() {
        double[] individualMiningTime = new double[miners.size()];
        
        // Iterate through the list of miners and compute their mining times
        for(int i = 0; i < miners.size(); i++) {
            individualMiningTime[i] = this.bitcoinAmt / miners.get(i).getDailyBitcoinOutput();
        }

        return individualMiningTime;
    }

    /**
     * Calculate the collective mining time when using all miners
     * @return the mining time when using all miners at the same time
     */
    public double calculateCollectiveMiningTime() {
        double totalBitcoinOutput = 0;

        // Iterate through the list of miners and add Bitcoin outputs together
        for(int i = 0; i < miners.size(); i++) {
            totalBitcoinOutput += miners.get(i).getDailyBitcoinOutput();
        }

        // Compute their collective mining time
        return this.bitcoinAmt / totalBitcoinOutput;
    }

    /**
     * Calculate the hourly Bitcoin efficiency of each miner
     * @param energyRatesFilename: filename of input energy rates files
     */
    public void calculateHourlyEfficiency(String energyRatesFilename) {
        // Extract energy rates data 
        this.extractEnergyRatesData(energyRatesFilename);

        // Iterate through the list of miners and calculate their hourly Bitcoin efficiency
        for(int i = 0; i < this.miners.size(); i++) {
            for(int j = 0; j < this.miners.get(i).getHourlyEfficiency().length; j++) {
                this.miners.get(i).setHourlyEfficiency(j, energyRates[j] * this.miners.get(i).getWattage() / 1000);
            }
        }
    }
}