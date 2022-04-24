package budget;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.*;

public class Fields {
    private static int count = 0;
    private double sum = 0;
    private double balance = 0;
    static protected List<String> categories = new ArrayList<>(Arrays.asList("Food", "Clothes", "Entertainment",
            "Other", "All"));
    static Map<String, Double> purchaseListFood = new LinkedHashMap<>();
    static Map<String, Double> purchaseListClothes = new LinkedHashMap<>();
    static Map<String, Double> purchaseListEntertainments = new LinkedHashMap<>();
    static Map<String, Double> purchaseListOther = new LinkedHashMap<>();
    static Map<String, Double> purchaseListAll = new LinkedHashMap<>();
    private final File file = new File("purchases.txt");
    private String line;
    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReaderBalance = null;
    BufferedReader bufferedReaderFood = null;
    BufferedReader bufferedReaderClothes = null;
    BufferedReader bufferedReaderEntertainment = null;
    BufferedReader bufferedReaderOther = null;
    Scanner scanner = new Scanner(System.in);
}
