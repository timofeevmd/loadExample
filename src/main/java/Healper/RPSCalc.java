package Healper;

import static java.lang.Math.floor;

public class RPSCalc {
    private double loadProfile;
    private double targetLoadProfileTransactions;
    public double transactionTest;
    public double TimeToTest;
    public double overTime;
    public double maxTransactionPerVUser;
    public double targetThroughputPerVUser;
    public double targetThroughputPerVUserMinets;
    public int vUser;

    public RPSCalc( String testName, double currentThroughputPer5min, double targetLoadProfilePercentage, double loadTimePerTransaction, int timeToTest) {
        this.TimeToTest = timeToTest ;

        getLoadProfile100Percents(currentThroughputPer5min);
        getTargetLoadProfile(targetLoadProfilePercentage);
        overTime(loadTimePerTransaction);
        oneVUMaxTransactionsPerTest(timeToTest, overTime);
        vUser(targetLoadProfileTransactions, maxTransactionPerVUser);
        targetThroughputPerVUser(targetLoadProfileTransactions, vUser);
        targetThroughputPerVUserMinets(targetThroughputPerVUser, timeToTest);
        getTestParam(testName);
    }

    public void getTestParam(String testName){
        System.out.println(testName + ".timeToTest: " + TimeToTest);
        System.out.println(testName + ".targetLoadProfileTransactions: " + targetLoadProfileTransactions);
        System.out.println(testName + ".scenarioTime: " + overTime / 2);
        System.out.println(testName + ".overTime: " + overTime);
        System.out.println(testName + ".maxTransactionPerVUser: " + maxTransactionPerVUser);
        System.out.println(testName + ".vUser: " + vUser);
        System.out.println(testName + ".targetThroughputPerVUser: " + targetThroughputPerVUser);
        System.out.println(testName + ".targetThroughputPerVUserMinets: " + targetThroughputPerVUserMinets);
    }

    private double getLoadProfile100Percents(double currentThroghputPer5min){
        loadProfile = (currentThroghputPer5min / 300 /*seconds*/) * TimeToTest;
        return loadProfile;
    }

    private double getTargetLoadProfile(double targetLoadProfilePercentage){
        targetLoadProfileTransactions = loadProfile * targetLoadProfilePercentage / 100;
        return targetLoadProfileTransactions;
    }

    private double overTime(double time){
        overTime = time * 2;
        return overTime;
    }

    private double oneVUMaxTransactionsPerTest(int timeToTest, double overTime){
        maxTransactionPerVUser = timeToTest / overTime;
        return maxTransactionPerVUser;
    }

    private int vUser(double targetLoadProfileTransactions, double maxTransactionPerVUser){
        vUser = (int)floor(targetLoadProfileTransactions/maxTransactionPerVUser);
        return vUser;
    }

    private double targetThroughputPerVUser(double targetLoadProfileTransactions, double vUser){
        targetThroughputPerVUser = targetLoadProfileTransactions / vUser;
        return targetThroughputPerVUser;
    }

    private double targetThroughputPerVUserMinets(double targetThroughputPerVUser, double timeToTest){
        targetThroughputPerVUserMinets = targetThroughputPerVUser / (timeToTest/60);
        return targetThroughputPerVUserMinets;
    }
}
