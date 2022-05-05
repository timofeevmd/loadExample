package Healper;

import Healper.RPSCalc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ParameterBuilder {
    static FileInputStream fis;
    static Properties properties = new Properties();

    public String testName;
    public int maxThreads;
    public double rps;
    public int rumpUp;
    public int step;
    public int durationStep;
    public int timeToTest;

    public int currentThroughputPer5min;
    public int targetLoadProfilePercentage;
    public double loadTimePerTransaction;

    public ParameterBuilder(String configProperty){
        try{
            fis = new FileInputStream(configProperty);
            properties.load(fis);

            this.testName = properties.getProperty("testName");
            this.rumpUp = Integer.parseInt(properties.getProperty("rumpUp"));
            this.step = Integer.parseInt(properties.getProperty("step"));
            this.durationStep = Integer.parseInt(properties.getProperty("durationStep"));
            this.currentThroughputPer5min = Integer.parseInt(properties.getProperty("currentThroughputPer5min"));
            this.targetLoadProfilePercentage = Integer.parseInt(properties.getProperty("targetLoadProfilePercentage"));
            this.loadTimePerTransaction = Double.parseDouble(properties.getProperty("loadTimePerTransaction"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RPSCalc rpsCalc = new RPSCalc(testName, currentThroughputPer5min, targetLoadProfilePercentage, loadTimePerTransaction, timeToTest);
        this.maxThreads = rpsCalc.vUser;
        this.rps = rpsCalc.targetThroughputPerVUser;
    }


}
