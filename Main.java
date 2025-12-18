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
        int dayIndex = day - 1;

        for (int i = 0; i < COMMS; i++) { // commodity index
            totalProfit += profitData[month][dayIndex][i];
        }
        return totalProfit;
    }

   
    public static int commodityProfitInRange(String commodity, int from, int to) {//3
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(commodity)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1 || from < 1 || from > DAYS || to < 1 || to > DAYS || from > to) {
            return -99999;
        }
        long totalProfit = 0;

        int fromIndex = from - 1;
        int toIndex = to - 1;

        //12 month
        for (int m = 0; m < MONTHS; m++) {
            //day
            for (int d = fromIndex; d <= toIndex; d++) {
                totalProfit += profitData[m][d][commIndex];
            }
        }
        return (int) totalProfit;
    }
   
    
    public static int bestDayOfMonth(int month) {//4
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        int maxProfit = Integer.MIN_VALUE;
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
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return "INVALID_COMMODITY";
        }
        //1st month:
        long firstMonthProfit = 0;
        for (int j = 0; j < DAYS; j++) {
            firstMonthProfit += profitData[0][j][commIndex];
        }

        long maxProfit = firstMonthProfit;
        String bestMonthName = months[0]; 

        for (int i = 1; i < MONTHS; i++) {
            long currentMonthProfit = 0;
            for (int j = 0; j < DAYS; j++) {
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
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
        if (commIndex == -1) {
            return -1;
        }
        int maxStreak = 0;
        int currentStreak = 0;

        //for 12 month:
        for (int m = 0; m < MONTHS; m++) {
            //for 28 day
            for (int d = 0; d < DAYS; d++) {
                int profit = profitData[m][d][commIndex]; 

                if (profit < 0) {
                    currentStreak++;
                } else {
                    if (currentStreak > maxStreak) {
                        maxStreak = currentStreak;
                    }
                    currentStreak = 0;
                }
            }
        }
        if (currentStreak > maxStreak) {
            maxStreak = currentStreak;
        }

        return maxStreak;
    }
}


public static int daysAboveThreshold(String comm, int threshold) {//7
        int commIndex = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(comm)) {
                commIndex = i;
                break;
            }
        }
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
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        int maxSwing = 0;
        int previousDayProfit = 0;

        for (int j = 0; j < COMMS; j++) {
            previousDayProfit += profitData[month][0][j];
        }

        for (int i = 1; i < DAYS; i++) {
            int currentDayProfit = 0;
            for (int j = 0; j < COMMS; j++) {
                currentDayProfit += profitData[month][i][j];
            }
            int difference = currentDayProfit - previousDayProfit;
            int swing;

            if (difference < 0) {//finding the absolute value
                swing = difference * -1;
            } else {
                swing = difference;
            }
            if (swing > maxSwing) {
                maxSwing = swing;
            }
            previousDayProfit = currentDayProfit;
        }
        return maxSwing;
    }


public static String compareTwoCommodities(String c1, String c2) {//9
        int commIndex1 = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c1)) {
                commIndex1 = i;
                break;
            }
        }
        // Index for commodity 2
        int commIndex2 = -1;
        for (int i = 0; i < COMMS; i++) {
            if (commodities[i].equals(c2)) {
                commIndex2 = i;
                break;
            }
        }
        if (commIndex1 == -1 || commIndex2 == -1) {
            return "INVALID_COMMODITY";
        }

        long comm1TotalProfit = 0;
        long comm2TotalProfit = 0;

        for (int i = 0; i < MONTHS; i++) {
            for (int j = 0; j < DAYS; j++) {
                comm1TotalProfit += profitData[i][j][commIndex1];
                comm2TotalProfit += profitData[i][j][commIndex2];
            }
        }
        //compare
        if (comm1TotalProfit > comm2TotalProfit) {
            return c1 + " vs. " + c2;
        } else if (comm2TotalProfit > comm1TotalProfit) {
            return c2 + " vs. " + c1;
        } else {
            return "EQUAL";
        }
}


public static int bestWeekOfMonth(int month) {//10
        if (month < 0 || month >= MONTHS) {
            return -1;
        }
        long maxWeekProfit = Long.MIN_VALUE;
        int bestWeekNumber = -1;

        final int DAYS_PER_WEEK = 7;
        final int NUM_WEEKS = 4;

        for (int i = 0; i < NUM_WEEKS; i++) {
            long currentWeekProfit = 0;

            int startDayIndex = i * DAYS_PER_WEEK;
            int endDayIndex = startDayIndex + DAYS_PER_WEEK;

            for (int j = startDayIndex; j < endDayIndex; j++) {
                for (int k = 0; k < COMMS; k++) {
                    currentWeekProfit += profitData[month][j][k];
                }
            }

            if (currentWeekProfit > maxWeekProfit) {
                maxWeekProfit= currentWeekProfit;
                bestWeekNumber = i + 1;
            }
        }
        return bestWeekNumber;
    }
public static void main(String[] args) {
        loadData();
        System.out.println("Data loaded – ready for queries");
    }
}
