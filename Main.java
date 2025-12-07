// Main.java
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
}
