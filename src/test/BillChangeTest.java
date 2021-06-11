package test;

import main.BillChange;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillChangeTest {

    private BillChange billChange = new BillChange();

    /**
     * Testing put and change with various combinations of $13.
     */
    @Test
    public void testPutBillsAndChange(){
        int[] bills = new int[5];
        int[] expected = new int[]{0, 0, 2, 1, 1};
        String[] changeInput = {"change", "8"};

        billChange.put(new String[]{"put", "0", "0", "2", "1", "1"}, bills);

        helper(expected, bills);
        assertEquals(13, billChange.addAll(bills));

        billChange.change(changeInput, bills);
        expected = new int[]{0, 0, 1, 0, 0};
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "0", "0", "2", "0", "3"}, bills);
        billChange.change(changeInput, bills);
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "0", "0", "1", "4", "0"}, bills);
        billChange.change(changeInput, bills);
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "0", "0", "1", "4", "0"}, bills);
        billChange.change(changeInput, bills);
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "0", "0", "1", "1", "6"}, bills);
        billChange.change(changeInput, bills);
        expected = new int[]{0, 0, 0, 0, 5};
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "0", "1", "0", "1", "1"}, bills);
        billChange.change(changeInput, bills);
        expected = new int[]{0, 1, 0, 1, 1};
        helper(expected, bills);
    }

    @Test
    public void testTake(){
        int[] bills = new int[5];
        int[] expected = new int[]{0, 0, 1, 0, 0};
        String[] takeInput = {"take", "0", "0", "1", "1", "1"};

        billChange.put(new String[]{"put", "0", "0", "2", "1", "1"}, bills);
        billChange.take(takeInput, bills);
        helper(expected, bills);

        bills = new int[5];
        billChange.put(new String[]{"put", "1", "1", "0", "1", "1"}, bills);
        billChange.take(takeInput, bills);
        expected = new int[]{1, 1, 0, 1, 1};
        helper(expected, bills);

    }

    public void helper(int[] expected, int[] actual){
        for(int i = 0; i < expected.length; i++){
            assertEquals(expected[i], actual[i]);
        }
    }
}
