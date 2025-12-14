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

    private static boolean isInvalidMonth(int month) {
        return month < 0 || month >= MONTHS;
    }

    public static void loadData() {
        try {
            // for 12month
            for (int i = 0; i < MONTHS; i++) {
                String filename = "Data_Files/" + months[i] + ".txt";

                Scanner sc = new Scanner(new File(filename));//???

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

                    int commIndex = -1;
                    for (int c = 0; c < COMMS; c++) {
                        if (commodities[c].equals(commodityName)) {
                            commIndex = c;
                            break;
                        }
                    }

                    if (commIndex != -1 && day >= 1 && day <= DAYS) {
                        int dayIndex = day - 1;
                        profitData[i][dayIndex][commIndex] = profit;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found or I/O error: " + e.getMessage());
        }
    }
    // 10 REQUIRED METHODS

    public static String mostProfitableCommodityInMonth(int month) {
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

   
    public static int totalProfitOnDay(int month, int day) {
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

   
    public static int commodityProfitInRange(String commodity, int from, int to) {
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
   
    
    public static int bestDayOfMonth(int month) {
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

   
    
    public static String bestMonthForCommodity(String comm) {
        return "DUMMY";
    }

    
    
    public static int consecutiveLossDays(String comm) {
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


public static int daysAboveThreshold(String comm, int threshold) {
        return 1234;
}


public static int biggestDailySwing(int month) {
        return 1234;
}


public static String compareTwoCommodities(String c1, String c2) {
        return "DUMMY is better by 1234";
}


public static String bestWeekOfMonth(int month) {
        return "DUMMY";
}
