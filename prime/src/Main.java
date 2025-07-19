import java.sql.SQLOutput;
import java.text.BreakIterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int num = scan.nextInt();
        if (num < 2) {
            System.out.println("Not prime");
        } else {

            for (int i = 2; i < num; i++) {
                if (num % i == 0) {
                    System.out.println(num + " is prime.");
                    break;

                }
                else {System.out.println("Not prime");
                    break;
                }
            }
        }
    }}

