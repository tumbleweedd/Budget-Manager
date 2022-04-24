package budget;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Print print = new Print();
        print.print();
    }
}

class Print {
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
    static Map<String, Double> sortedMap = new LinkedHashMap<>();
    private final File file = new File("purchases.txt");
    private String line;
    BufferedWriter bufferedWriter = null;
    BufferedReader bufferedReaderBalance = null;
    BufferedReader bufferedReaderFood = null;
    BufferedReader bufferedReaderClothes = null;
    BufferedReader bufferedReaderEntertainment = null;
    BufferedReader bufferedReaderOther = null;
    Scanner scanner = new Scanner(System.in);

    public static int getCount() {
        return count;
    }

    public static void setCount() {
        count++;
    }

    public void print() {
        boolean flagAdd = true;
        boolean flagShow = true;
        boolean flagSorted = true;
        boolean flagSortedCertain = true;

        mainMenu();
        do {
            int sw = scanner.nextInt();
            switch (sw) {
                case 1:
                    System.out.println("\n" + "Enter income:");
                    balance += scanner.nextDouble();
                    System.out.println("Income was added!" + "\n");
                    mainMenu();
                    break;
                case 2:
                    System.out.println();
                    purchaseMenu();
                    do {
                        int sw1 = scanner.nextInt();
                        switch (sw1) {
                            case 1:
                                addPurchase(purchaseListFood, purchaseListAll);
                                purchaseMenu();
                                break;
                            case 2:
                                addPurchase(purchaseListClothes, purchaseListAll);
                                purchaseMenu();
                                break;
                            case 3:
                                addPurchase(purchaseListEntertainments, purchaseListAll);
                                purchaseMenu();
                                break;
                            case 4:
                                addPurchase(purchaseListOther, purchaseListAll);
                                purchaseMenu();
                                break;
                            case 5:
                                System.out.println();
                                flagAdd = false;
                                break;
                            default:
                                System.out.println("Unknown command");
                                break;
                        }
                    } while (flagAdd);
                    flagAdd = true;
                    mainMenu();
                    break;
                case 3:
                    if (purchaseListAll.size() == 0) {
                        System.out.println("\n" + "The purchase list is empty\n");
                        flagShow = false;
                        mainMenu();
                        break;
                    }
                    System.out.println();
                    showListPurchaseMenu();
                    do {
                        int sw2 = scanner.nextInt();
                        switch (sw2) {
                            case 1:
                                showPurchase(purchaseListFood);
                                showListPurchaseMenu();
                                break;
                            case 2:
                                showPurchase(purchaseListClothes);
                                showListPurchaseMenu();
                                break;
                            case 3:
                                showPurchase(purchaseListEntertainments);
                                showListPurchaseMenu();
                                break;
                            case 4:
                                showPurchase(purchaseListOther);
                                showListPurchaseMenu();
                                break;
                            case 5:
                                showPurchase(purchaseListAll);
                                showListPurchaseMenu();
                                break;
                            case 6:
                                System.out.println();
                                flagShow = false;
                                break;
                            default:
                                System.out.println("Unknown command");
                                break;
                        }
                    } while (flagShow);
                    flagShow = true;
                    mainMenu();
                    break;
                case 4:
                    String balanceOutPut = new DecimalFormat("#0.00").format(balance);
                    System.out.println("\n" + "Balance: $" + balanceOutPut + "\n");
                    mainMenu();
                    break;
                case 5:
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter(file));
                        bufferedWriter.write(String.valueOf(balance));
                        bufferedWriter.newLine();

                        writeToFile(purchaseListFood, 0);
                        writeToFile(purchaseListClothes, 1);
                        writeToFile(purchaseListEntertainments, 2);
                        writeToFile(purchaseListOther, 3);
                        bufferedWriter.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedWriter.close();
                        } catch (Exception ignored) {
                        }
                    }
                    System.out.println("\n" + "Purchases were saved!\n");
                    mainMenu();
                    break;
                case 6:
                    try {
                        readBalanceFromFile("Food");
                        readFromFile(0, 1, purchaseListFood, purchaseListAll,
                                "Clothes");
                        readFromFile(1, 2, purchaseListClothes, purchaseListAll,
                                "Entertainment");
                        readFromFile(2, 3, purchaseListEntertainments,
                                purchaseListAll, "Other");
                        readFromFile(3, 4, purchaseListOther, purchaseListAll,
                                null);

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (bufferedReaderFood != null || bufferedReaderClothes != null ||
                            bufferedReaderEntertainment != null
                            || bufferedReaderOther != null || bufferedReaderBalance != null) {
                            try {
                                bufferedReaderBalance.close();
                                bufferedReaderFood.close();
                                bufferedReaderClothes.close();
                                bufferedReaderEntertainment.close();
                                bufferedReaderOther.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    System.out.println("\n" + "Purchases were loaded!\n");
                    mainMenu();
                    break;
                case 7:
                    System.out.println();
                    sortedMenu();
                    do {
                        int sw3 = scanner.nextInt();
                        switch (sw3) {
                            case 1:
                                if (purchaseListAll.size() == 0) {
                                    System.out.println("\n" + "The purchase list is empty\n");
                                    sortedMenu();
                                    break;
                                }
                                sortedAllPurchase(purchaseListAll);
                                sortedMenu();
                                break;
                            case 2:
                                createSortedMapByType("Food", purchaseListFood);
                                createSortedMapByType("Clothes", purchaseListClothes);
                                createSortedMapByType("Entertainment", purchaseListEntertainments);
                                createSortedMapByType("Other", purchaseListOther);
                                sortedByType();
                                sortedMenu();
                                break;
                            case 3:
                                System.out.println();
                                sortedCertainTypeMenu();
                                do {
                                    int sw4 = scanner.nextInt();
                                    switch (sw4) {
                                        case 1:
                                            if (purchaseListFood.size() == 0) {
                                                System.out.println("\n" + "The purchase list is empty\n");
                                                flagSortedCertain = false;
                                                sortedMenu();
                                                break;
                                            }
                                            sortedAllPurchase(purchaseListFood);
                                            flagSortedCertain = false;
                                            sortedMenu();
                                            break;
                                        case 2:
                                            if (purchaseListClothes.size() == 0) {
                                                System.out.println("\n" + "The purchase list is empty\n");
                                                flagSortedCertain = false;
                                                sortedMenu();
                                                break;
                                            }
                                            sortedAllPurchase(purchaseListClothes);
                                            flagSortedCertain = false;
                                            sortedMenu();
                                            break;
                                        case 3:
                                            if (purchaseListEntertainments.size() == 0) {
                                                System.out.println("\n" + "The purchase list is empty\n");
                                                flagSortedCertain = false;
                                                sortedMenu();
                                                break;
                                            }
                                            sortedAllPurchase(purchaseListEntertainments);
                                            flagSortedCertain = false;
                                            sortedMenu();
                                            break;
                                        case 4:
                                            if (purchaseListOther.size() == 0) {
                                                System.out.println("\n" + "The purchase list is empty\n");
                                                flagSortedCertain = false;
                                                sortedMenu();
                                                break;
                                            }
                                            sortedAllPurchase(purchaseListOther);
                                            flagSortedCertain = false;
                                            sortedMenu();
                                            break;
                                    }
                                } while (flagSortedCertain);
                                flagSortedCertain = true;
                                break;

                            case 4:
                                System.out.println();
                                flagSorted = false;
                                mainMenu();
                                break;
                            default:
                                System.out.println("Unknown command");
                                break;
                        }
                    } while (flagSorted);
                    flagSorted = true;
                    mainMenu();
                    break;
                case 0:
                    System.out.println("\n" + "Bye!");
                    System.exit(0);
                default:
                    System.out.println("Unknown command");
                    break;
            }
        } while (true);
    }

    public void mainMenu() {
        System.out.print("1) Add income\n" +
                         "2) Add purchase\n" +
                         "3) Show list of purchases\n" +
                         "4) Balance\n" +
                         "5) Save\n" +
                         "7) Analyze (Sort)\n" +
                         "6) Load\n" +
                         "0) Exit\n");
    }

    public void purchaseMenu() {
        System.out.print("1) Food\n" +
                         "2) Clothes\n" +
                         "3) Entertainment\n" +
                         "4) Other\n" +
                         "5) Back\n");
    }

    public void showListPurchaseMenu() {
        System.out.print("1) Food\n" +
                         "2) Clothes\n" +
                         "3) Entertainment\n" +
                         "4) Other\n" +
                         "5) All\n" +
                         "6) Back\n");
    }

    public void sortedMenu() {
        System.out.print("1) Sort all purchases\n" +
                         "2) Sort by type\n" +
                         "3) Sort certain type\n" +
                         "4) Back\n");
    }

    public void sortedCertainTypeMenu() {
        System.out.print("1) Food\n" +
                         "2) Clothes\n" +
                         "3) Entertainment\n" +
                         "4) Other\n");
    }

    public void addPurchase(Map<String, Double> purchaseList, Map<String, Double> purchaseListAll) {
        System.out.println("\n" + "Enter purchase name:");
        scanner.nextLine();
        String purchaseNameString = scanner.nextLine();
        System.out.println("Enter its price:");
        double purchasePriceDouble = scanner.nextDouble();
        purchaseList.put(purchaseNameString, purchasePriceDouble);
        purchaseListAll.putAll(purchaseList);
        balance -= purchaseList.get(purchaseNameString);
        System.out.println("Purchase was added!" + "\n");
    }

    public void showPurchase(Map<String, Double> purchaseList) {
        if (purchaseList.size() == 0) {
            System.out.println("\n" + "Food:");
            System.out.println("The purchase list is empty" + "\n");

        } else {
            System.out.println("\n" + "Food:");

            for (Map.Entry<String, Double> entry : purchaseList.entrySet()) {
                String price = new DecimalFormat("#0.00").format(entry.getValue());
                System.out.println(entry.getKey() + " $" + price);
            }
            for (Double value : purchaseList.values()) {
                sum += value;
            }
            String sumOutPut = new DecimalFormat("#0.00").format(sum);
            System.out.println("Total sum: $" + sumOutPut + "\n");
            sum = 0;
        }
    }

    public void writeToFile(Map<String, Double> purchaseList, int category) throws IOException {
        bufferedWriter.write(categories.get(category) + "\n");
        for (Map.Entry<String, Double> entry : purchaseList.entrySet()) {
            bufferedWriter.write(entry.getKey() +
                                 ":" + entry.getValue());
            bufferedWriter.newLine();
        }
    }

    public void readFromFile(int category, int k, Map<String, Double> purchaseList,
                             Map<String, Double> purchaseListAll, String endRide) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        for (int i = 0; i < getCount() + k; i++) {
            bufferedReader.readLine();
        }
        if (Objects.equals(bufferedReader.readLine(), categories.get(category))) {
            while (!Objects.equals(line = bufferedReader.readLine(), endRide)) {
                String[] parts = line.split(":");
                String name = parts[0].trim();
                Double price = Double.parseDouble(parts[1].trim());

                if (!name.equals("")) {
                    purchaseList.put(name, price);
                    purchaseListAll.putAll(purchaseList);
                }
                setCount();
            }

        }

    }

    public void readBalanceFromFile(String endRead) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        while (!Objects.equals(line = bufferedReader.readLine(), endRead)) {
            if (!line.equals("")) {
                balance = Double.parseDouble(line);
            }
        }
    }

    public void sortedAllPurchase(Map<String, Double> purchaseList) {
        System.out.println("\n" + "All:");
        Map<String, Double> sortedMap = purchaseList.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String price = new DecimalFormat("#0.00").format(entry.getValue());
            System.out.println(entry.getKey() + " $" + price);
        }
        for (Double value : purchaseList.values()) {
            sum += value;
        }
        String sumOutPut = new DecimalFormat("#0.00").format(sum);
        System.out.println("Total sum: $" + sumOutPut + "\n");
        sum = 0;
    }

    public void createSortedMapByType(String category, Map<String, Double> purchaseList) {
        double price = 0.00;
        for (Double value : purchaseList.values()) {
            price += value;
        }
        sortedMap.put(category, price);
    }

    public void sortedByType() {
        System.out.println("\n" + "Types:");
        sortedMap = sortedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            String price = new DecimalFormat("#0.00").format(entry.getValue());
            System.out.println(entry.getKey() + " - $" + price);
        }
        for (Double value : purchaseListAll.values()) {
            sum += value;
        }
        String sumOutPut = new DecimalFormat("#0.00").format(sum);
        System.out.println("Total sum: $" + sumOutPut + "\n");
        sum = 0;
    }
}


