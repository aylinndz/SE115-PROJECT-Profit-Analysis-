import java.io.*;
import java.util.*;
import java.util.Scanner;

public class Main {
    static final int MONTHS = 12;
    static final int DAYS = 28;
    static final int COMMS = 5;
    static String[] commodities = {"Gold", "Oil", "Silver", "Wheat", "Copper"};
    static String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};

    static int[][][] profitData = new int[MONTHS][DAYS][COMMS];

    private static int getCommodityIndex(String comm) {
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                return i;
            }
        }
        return -1;
    }
    

    public static void loadData() {
        try {
            // for 12month
            for (int i = 0; i < MONTHS; i++) {
                String filename = "Data_Files/" + months[i] + ".txt";

                File file = new File(filename);
                if (!file.exists()) continue;

                Scanner sc = new Scanner(file);
                if (sc.hasNextLine()) {
                    sc.nextLine();
                }
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] parts = line.split(",");

                    if (parts.length != 3) continue;

                    //?
                    int day = Integer.parseInt(parts[0].trim());
                    String commodityName = parts[1].trim();
                    int profit = Integer.parseInt(parts[2].trim());

                    int commIndex = getCommodityIndex(commodityName);
                    if (commIndex != -1 && day >= 1 && day <= DAYS) {
                        profitData[i][day - 1][commIndex] = profit;
                    }
                }
                sc.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    // 10 REQUIRED METHODS

    public static String mostProfitableCommodityInMonth(int month) {//1
        if (month < 0 || month >= MONTHS) {
            return "INVALID_MONTH";
        }
        long maxProfit = Long.MIN_VALUE;
            String mostProfitableComm = " ";

        for (int i = 0; i < COMMS; i++) {// commodity index
            long currentCommProfit = 0;

            for (int j = 0; j < DAYS; j++) {// day index
                currentCommProfit += profitData[month][j][i];
            }
            // most profitable
            if (currentCommProfit > maxProfit) {
                maxProfit = currentCommProfit;
                mostProfitableComm = commodities[i];
            }
        }
        return mostProfitableComm + " " + maxProfit;
    }

   
    public static int totalProfitOnDay(int month, int day) {//2
        if (month < 0 || month >= MONTHS || day < 1 || day > DAYS) {
            return -99999;
        }
        int totalProfit = 0;
        for (int i = 0; i < COMMS; i++){
            totalProfit += profitData[month][day - 1][i];
        }
        return totalProfit;
    }
   
    public static int commodityProfitInRange(String commodity, int from, int to) {//3
        int commIndex = getCommodityIndex(commodity);
        if (commIndex == -1 || from < 1 || to > DAYS || from > to) {
            return -99999;
        }
        long totalProfit = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = from - 1; d < to; d++) {
                totalProfit += profitData[m][d][commIndex];
            }
        }
        return (int) totalProfit;
    }
   
    
    public static int bestDayOfMonth(int month) {//4
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        long maxProfit = Long.MIN_VALUE;
        int bestDayNumber = -1;

        for (int i = 0; i < DAYS; i++) {
            int dailyTotalProfit = 0;

            for (int j = 0; j < COMMS; j++) {
                dailyTotalProfit += profitData[month][i][j];
            }
            if (dailyTotalProfit > maxProfit) {
                maxProfit = dailyTotalProfit;
                bestDayNumber = i + 1;
            }
        }
        return bestDayNumber;
    }

   
    
   public static String bestMonthForCommodity(String comm) {//5
        int commIndex = getCommodityIndex(comm);
        if (commIndex == -1) {
            return "INVALID_COMMODITY";
        }
        long maxProfit = Long.MIN_VALUE;
        String bestMonthName = "";

        for (int i = 0; i < MONTHS; i++) {
            long currentMonthProfit = 0;
            for (int j = 0; j < DAYS; j++){
                currentMonthProfit += profitData[i][j][commIndex];
            }
            if (currentMonthProfit > maxProfit) {
                maxProfit = currentMonthProfit;
                bestMonthName = months[i];
            }
        }
        return bestMonthName;
    }

    
    
    public static int consecutiveLossDays(String comm) {//6
        int commIndex = getCommodityIndex(comm);
        if (commIndex == -1){
            return -1;
        }
        int maxStreak = 0, currentStreak = 0;

        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                if (profitData[m][d][commIndex] < 0) {
                    currentStreak++;
                    if (currentStreak > maxStreak) maxStreak = currentStreak;
                } else {
                    currentStreak = 0;
                }
            }
        }
        return maxStreak;
    }


public static int daysAboveThreshold(String comm, int threshold) {//7
        int commIndex = getCommodityIndex(comm);
        if (commIndex == -1) {
            return -1;
        }
        int count = 0;
        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                int profit = profitData[i][j][commIndex];
                
                if (profit > threshold) {
                    count++;
                }
            }
        }
        return count;
    }


 public static int biggestDailySwing(int month) {//8
        if (month < 0 || month >= MONTHS){
            return -99999;
        }
        int maxSwing = 0;
        for (int i = 0; i < DAYS - 1; i++) {
            int day1 = 0;
            int day2 = 0;
            for (int j = 0; j < COMMS; j++) {
                day1 += profitData[month][i][j];
                day2 += profitData[month][i + 1][j];
            }
            int swing = Math.abs(day2 - day1);
            if (swing > maxSwing){
                maxSwing = swing;
            }
        }
        return maxSwing;
    }


public static String compareTwoCommodities(String c1, String c2) {//9
        int idx1 = getCommodityIndex(c1);
        int idx2 = getCommodityIndex(c2);
        if (idx1 == -1 || idx2 == -1) {
            return "INVALID_COMMODITY";
        }
        long p1 = 0;
        long p2 = 0;
        for (int m = 0; m < MONTHS; m++) {
            for (int d = 0; d < DAYS; d++) {
                p1 += profitData[m][d][idx1];
                p2 += profitData[m][d][idx2];
            }
        }
        long diff = Math.abs(p1 - p2);

        if (p1 > p2){
            return c1 + " is better by " + diff;
        }
        else if (p2 > p1){
            return c2 + " is better by " + diff;
        }
        else return "Equal";
    }


public static int bestWeekOfMonth(int month) {//10
        if (month < 0 || month >= MONTHS){
            return "INVALID_MONTH";
        }
        long maxWeekProfit = Long.MIN_VALUE;
        int bestWeekNumber = 1;
        for (int i = 0; i < 4; i++) {
            long currentWeekProfit = 0;
            for (int j = i * 7; j < (i + 1) * 7; j++) {
                for (int k = 0; k < COMMS; k++) {
                    currentWeekProfit += profitData[month][j][k];
                }
            }
            if (currentWeekProfit > maxWeekProfit) {
                maxWeekProfit = currentWeekProfit;
                bestWeekNumber = i + 1;
            }
        }
        return "Week " + bestWeekNumber;
    }

    
public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
