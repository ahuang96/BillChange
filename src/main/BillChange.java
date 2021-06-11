package main;

import java.util.*;

public class BillChange {

    private int[] denomination;

    private Map<Integer, Integer> denominationToIndex = new HashMap<>();

    private int[] billCount;

    public BillChange(int[] denomination) {
        this.denomination = denomination;
        for (int idx = 0; idx < denomination.length; idx++) {
            denominationToIndex.put(denomination[idx], idx);
        }
        billCount = new int[denomination.length];
    }

    /**
     * Display the current amount in bill array.
     */
    public void show(){
        System.out.println(String.format("$%d %d %d %d %d %d", getSum(), billCount[0], billCount[1], billCount[2], billCount[3], billCount[4]));
    }

    /**
     * Put the bills from the input into bills.
     * @param arr
     */
    public void put(String[] arr){
        if(!validate(arr, denomination.length+1)) {
            return;
        }

        for(int i = 1; i < arr.length; i++){
            billCount[i-1] += Integer.valueOf(arr[i]);
        }

        show();
    }

    /**
     * Take bills from the input from bills, if it cannot be fullfilled, will return "sorry".
     * @param arr
     */
    public void take(String[] arr){
        if(!validate(arr, 6)) {
            return;
        }

        boolean canFullfill = true;
        for(int i = 1; i < arr.length; i++){
            if(billCount[i-1] < Integer.valueOf(arr[i])){
                canFullfill = false;
            }
        }

        if(canFullfill){
            for(int i = 1; i < arr.length; i++){
                billCount[i-1] -= Integer.valueOf(arr[i]);
            }
            show();
        } else {
            System.out.println("sorry");
        }
    }

    /**
     * Returns the change from the input based on what is available in bills. If it cannot be done, returns sorry.
     * @param arr
     */
    public void change(String[] arr){
        if(!validate(arr, 2)) {
            return;
        }

        List<Integer> bills = new ArrayList<>();
        List<Integer> result = new ArrayList<>();

        for(int i = 0; i < billCount.length; i++){
            for(int j = 0; j < billCount[i]; j++){
                bills.add(denomination[i]);
            }
        }

        boolean isChange = changeHelper(bills, 0, Integer.valueOf(arr[1]), result);

        if(isChange){
            int[] changeCount = billCount.clone();

            for(int i : result){
                int index = denominationToIndex.get(i);
                billCount[index]--;
            }

            for(int i = 0; i < changeCount.length; i++){
                changeCount[i] = changeCount[i] - billCount[i];
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
    public int getSum(){
        int sum = 0;

        for(int i = 0; i < denomination.length; i++){
            sum += denomination[i] * billCount[i];
        }

        return sum;
    }

    /**
     * Return the bills
     * @return bill
     */
    public int[] getBills() {
        return billCount;
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
                Integer.parseInt(arr[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("wrong parameter type");
                return false;
            }
        }

        return true;
    }
}
