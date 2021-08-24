/**
 * Class that represents a Bitcoin Miner
 * 
 * @author Aron Saengchan
 */
public class BitcoinMiner {

    /**
     * Name of the miner
     */
    private String name;

    /**
     * Bitcoin output per day
     */
    private double dailyBitcoinOutput;

    /**
     * Power required to run the miner
     */
    private int wattage;

    /**
     * Amount of Bitcoin mined each hour based on energy rate
     */
    private double[] hourlyEfficiency;

    /**
     * Constructor used to initialize a Bitcoin Miner object
     * @param name: name of the miner
     * @param hourlyBitcoinOutput: Bitcoin output per day
     * @param wattage: Power required to run the miner
     */
    public BitcoinMiner(String name, double hourlyBitcoinOutput, int wattage) {
        this.name = name;
        this.dailyBitcoinOutput = hourlyBitcoinOutput * 24;
        this.wattage = wattage;
        this.hourlyEfficiency = new double[24];
    }
    
    /**
     * Getter that retrieve the miner's name
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter that retrieves the daily Bitcoin output
     * @return
     */
    public double getDailyBitcoinOutput() {
        return this.dailyBitcoinOutput;
    }

    /**
     * Getter that retrieves the wattage
     * @return
     */
    public int getWattage() {
        return this.wattage;
    }

    /**
     * Getter that retrieves the hourly efficiency
     * @return
     */
    public double[] getHourlyEfficiency() {
        return this.hourlyEfficiency;
    }

    /**
     * Setter that stores the hourly efficiency at a specified time
     * @param hour: an hour on a 24-hour clock
     * @param hourlyEfficiency: efficiency at the specified hour
     */
    public void setHourlyEfficiency(int hour, double hourlyEfficiency) {
        this.hourlyEfficiency[hour] = hourlyEfficiency;
    }
}