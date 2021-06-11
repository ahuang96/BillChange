package test;

import main.BillChange;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BillChangeTest {

    private static int[] denomination = {20, 10, 5, 2, 1};


    /**
     * Testing put and change with various combinations of $13.
     */
    @Test
    public void testPutBillsAndChange(){
        // int[] bills = new int[5];
        BillChange billChange =  new BillChange(denomination);
        int[] expected = new int[]{0, 0, 2, 1, 1};
        String[] changeInput = {"change", "8"};

        billChange.put(new String[]{"put", "0", "0", "2", "1", "1"});

        helper(expected, billChange.getBills());
        assertEquals(13, billChange.getSum());

        billChange.change(changeInput);
        expected = new int[]{0, 0, 1, 0, 0};
        helper(expected, billChange.getBills());


        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "0", "2", "0", "3"});
        billChange.change(changeInput);
        helper(expected, billChange.getBills());

        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "0", "1", "4", "0"});
        billChange.change(changeInput);
        helper(expected, billChange.getBills());

        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "0", "1", "4", "0"});
        billChange.change(changeInput);
        helper(expected, billChange.getBills());

        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "0", "1", "1", "6"});
        billChange.change(changeInput);
        expected = new int[]{0, 0, 0, 0, 5};
        helper(expected, billChange.getBills());

        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "1", "0", "1", "1"});
        billChange.change(changeInput);
        expected = new int[]{0, 1, 0, 1, 1};
        helper(expected, billChange.getBills());
    }

    @Test
    public void testTake(){

        int[] expected = new int[]{0, 0, 1, 0, 0};
        String[] takeInput = {"take", "0", "0", "1", "1", "1"};

        BillChange billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "0", "0", "2", "1", "1"});
        billChange.take(takeInput);
        helper(expected, billChange.getBills());

        billChange =  new BillChange(denomination);
        billChange.put(new String[]{"put", "1", "1", "0", "1", "1"});
        billChange.take(takeInput);
        expected = new int[]{1, 1, 0, 1, 1};
        helper(expected, billChange.getBills());

    }

    public void helper(int[] expected, int[] actual){
        for(int i = 0; i < expected.length; i++){
            assertEquals(expected[i], actual[i]);
        }
    }
}
