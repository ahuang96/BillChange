package main;

import java.util.*;

public class BillChange {

    private int[] denomination = {20, 10, 5, 2, 1};

    private static Map<Integer, Integer> denominationToIndex = new HashMap<>();

    static {
        denominationToIndex.put(20, 0);
        denominationToIndex.put(10, 1);
        denominationToIndex.put(5, 2);
        denominationToIndex.put(2, 3);
        denominationToIndex.put(1, 4);
    }


    public static void main(String[] args) {

        int[] billCount = new int[5];

        Scanner scanner = new Scanner(System.in);
        System.out.println("ready");
        System.out.print("> ");

        String str = scanner.nextLine();
        BillChange billChange = new BillChange();

        while(!"quit".equals(str.toLowerCase())){
            String[] splitStr = parse(str);

            switch (splitStr[0].toLowerCase()) {
                case "show":
                    billChange.show(billCount);
                    break;
                case "put":
                    billChange.put(splitStr, billCount);
                    break;
                case "take":
                    billChange.take(splitStr, billCount);
                    break;
                case "change":
                    billChange.change(splitStr, billCount);
                    break;
                default:
                    System.out.println("try again");
            }

            System.out.print("> ");
            str = scanner.nextLine();
        }

        System.out.println("Bye");

        scanner.close();

    }

    /**
     * Display the current amount in bill array.
     * @param billCount
     */
    public void show(int[] billCount){
        System.out.println(String.format("$%d %d %d %d %d %d", addAll(billCount), billCount[0], billCount[1], billCount[2], billCount[3], billCount[4]));
    }

    /**
     * Put the bills from the input into bills.
     * @param arr
     * @param bill
     */
    public void put(String[] arr, int[] bill){
        if(!validate(arr, 6)) {
            return;
        }

        for(int i = 1; i < arr.length; i++){
            bill[i-1] += Integer.valueOf(arr[i]);
        }

        show(bill);
    }

    /**
     * Take bills from the input from bills, if it cannot be fullfilled, will return "sorry".
     * @param arr
     * @param bill
     */
    public void take(String[] arr, int[] bill){
        if(!validate(arr, 6)) {
            return;
        }

        boolean canFullfill = true;
        for(int i = 1; i < arr.length; i++){
            if(bill[i-1] < Integer.valueOf(arr[i])){
                canFullfill = false;
            }
        }

        if(canFullfill){
            for(int i = 1; i < arr.length; i++){
                bill[i-1] -= Integer.valueOf(arr[i]);
            }
            show(bill);
        } else {
            System.out.println("sorry");
        }
    }

    /**
     * Returns the change from the input based on what is available in bills. If it cannot be done, returns sorry.
     * @param arr
     * @param bill
     */
    public void change(String[] arr, int[] bill){
        if(!validate(arr, 2)) {
            return;
        }

        List<Integer> bills = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < bill.length; i++){
            for(int j = 0; j < bill[i]; j++){
                bills.add(denomination[i]);
            }
        }

        boolean isChange = changeHelper(bills, 0, Integer.valueOf(arr[1]), result);

        if(isChange){
            int[] changeCount = bill.clone();

            for(int i : result){
                int index = denominationToIndex.get(i);
                bill[index]--;
            }

            for(int i = 0; i < changeCount.length; i++){
                changeCount[i] = changeCount[i] - bill[i];
            }

            System.out.println(String.format("%d %d %d %d %d", changeCount[0], changeCount[1], changeCount[2], changeCount[3], changeCount[4]));

        } else {
            System.out.println("sorry");
        }
    }

    /**
     * Iterates through to find the correct change. Returns true if the target change is met, false if not.
     * @param bills
     * @param index
     * @param target
     * @param result
     * @return
     */
    public boolean changeHelper(List<Integer> bills, int index, int target, List<Integer> result){
        if(target == 0){
            return true;
        }

        if(index == bills.size()){
            return false;
        }

        boolean res = false;

        if(bills.get(index) <= target){
            result.add(bills.get(index));
            res = changeHelper(bills, index+1, target-bills.get(index), result);

            if(!res){
                result.remove(result.size()-1);
                res = changeHelper(bills, index+1, target, result);
            }
        } else {
            res = changeHelper(bills, index+1, target, result);
        }

        return res;
    }

    /**
     * Returns the sum of all the bills.
     * @param bill
     * @return
     */
    public int addAll(int[] bill){
        int sum = 0;

        for(int i = 0; i < denomination.length; i++){
            sum += denomination[i] * bill[i];
        }

        return sum;
    }

    /**
     * Helper to parse the console input.
     * @param str
     * @return
     */
    private static String[] parse(String str){
        String[] result = str.split("\\s+");

        return result;
    }

    /**
     * Validates the input by expected size, and checking if every item in the input array after 0 is an integer.
     * @param arr
     * @param expectedSize
     * @return
     */
    private boolean validate(String[] arr, int expectedSize){
        if(arr.length != expectedSize) {
            System.out.println("wrong parameters");
            return false;
        }

        for(int i = 1; i < arr.length; i++){
            try {
                int verify = Integer.parseInt(arr[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("wrong parameter type");
                return false;
            }
        }

        return true;
    }
}
