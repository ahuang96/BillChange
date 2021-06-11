package main;
import java.util.Scanner;

public class BillChangeApp {

    public static void main(String[] args) {

        int[] denomination = {20, 10, 5, 2, 1};

        Scanner scanner = new Scanner(System.in);
        System.out.println("ready");
        System.out.print("> ");

        String str = scanner.nextLine();
        BillChange billChange = new BillChange(denomination);


        while(!"quit".equals(str.toLowerCase())){
            String[] splitStr = parse(str);

            switch (splitStr[0].toLowerCase()) {
                case "show":
                    billChange.show();
                    break;
                case "put":
                    billChange.put(splitStr);
                    break;
                case "take":
                    billChange.take(splitStr);
                    break;
                case "change":
                    billChange.change(splitStr);
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
     * Helper to parse the console input.
     * @param str
     * @return
     */
    private static String[] parse(String str){
        String[] result = str.split("\\s+");

        return result;
    }


}
