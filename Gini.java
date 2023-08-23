import java.util.Scanner;
import java.util.PriorityQueue;

/**
 * This program reads multiple incomes entered by the user and then creates
 * a small report including how many incomes were entered, what the average 
 * of the incomes is, and what the Gini coefficient of the incomes is.
 * 
 * Included in this program are methods to get info on the entered incomes 
 * such as the sum, average, sum of the average, difference of sums, sum 
 * of differences, and a method to calculate the Gini Coefficient, which are
 * all described in further detail in the code below.
 * 
 * @author Riley OKeefe
 */
public class Gini {

    public static final Scanner KBD = new Scanner(System.in);

    private static PriorityQueue<Double> incomes = new PriorityQueue<>();
    private static PriorityQueue<Double> sumOfIncomes = new PriorityQueue<>();
    private static PriorityQueue<Double> sumOfAvgIn = new PriorityQueue<>();
    private static double sumOfDif;
    private static double average;

    public static void main(String[] args) {
        printIntroduction();
        pause();
        prompt();
        pause();
        incomeReport();
        pause();
    }

    /**
     * This Method prompts the user to enter incomes and stops once the user
     * enters a negative income. The incomes are added to a priority queue and
     * negative incomes are removed.
     */
    public static void prompt() {
        Double income = 0.0;
        System.out.println("Enter the incomes below. Enter a negative "
                + "income to end input.\n");
        while (income >= 0.0) {
            income = KBD.nextDouble();
            KBD.nextLine();
            incomes.add(income);
            if (income < 0) {
                incomes.remove(income);
            }
        }
    }

    /**
     * This method reports the results of the calculation.
     */
    public static void incomeReport() {
        System.out.println("Income Report");
        System.out.println("-------------");
        System.out.printf("Number of Incomes:%8d\n"
                + "Average Income:   $%10.2f\n"
                + "Gini Coefficient:%12.2f\n",
                incomes.size(), getAverage(), giniCoef());
    }

    /**
     * This method gets the average income from the incomes entered by the 
     * user.
     *
     * @return the average income
     */
    public static double getAverage() {
        PriorityQueue<Double> temp = new PriorityQueue<>();
        temp.addAll(incomes);

        double sum = 0;
        double num;
        double size = temp.size();

        while (!temp.isEmpty()) {
            num = temp.remove();
            sum += num;

        }
        average = (sum / size);

        return average;
    }

    /**
     * This method gets the cumulative sum of the incomes and adds them to a
     * queue of the sums as each one is added, then stops adding once there is
     * no more incomes left.
     */
    public static void getSum() {
        double sum = 0;
        double num;
        PriorityQueue<Double> temp = new PriorityQueue<>();
        temp.addAll(incomes);

        while (!temp.isEmpty()) {
            num = temp.remove();
            sum += num;
            sumOfIncomes.add(sum);
        }
    }

    /**
     * This method gets the cumulative sum of the average incomes and adds 
     * them to a queue then stops adding income once there is no more left.
     */
    public static void sumOfAverage() {
        PriorityQueue<Double> temp = new PriorityQueue<>();
        temp.addAll(incomes);

        double sumOfAvg = 0;
        double totalSumOf = 0;

        while (!temp.isEmpty()) {
            if (sumOfAvgIn.isEmpty()) {
                sumOfAvg = average;
            } else if (!sumOfAvgIn.isEmpty()) {
                sumOfAvg = average * 2 - average;
            }
            totalSumOf += sumOfAvg;
            sumOfAvgIn.add(totalSumOf);
            temp.remove();
        }

    }

    /**
     * This method gets the difference between each cumulative income and
     * cumulative average income, then the differences are added to get a 
     * total sum of the differences.
     */
    public static void getDifInSums() {
        double dif1;
        double dif2;
        double dif;

        getSum();
        sumOfAverage();

        while (!sumOfAvgIn.isEmpty() && !sumOfIncomes.isEmpty()) {
            dif1 = sumOfAvgIn.remove();
            dif2 = sumOfIncomes.remove();
            dif = dif1 - dif2;
            sumOfDif += dif;
        }
    }

    /**
     * This method calculates the Gini Coefficient which is a number that
     * represents inequality in incomes. Ranging 0-1, 0 represents perfect
     * equality, 1 represents perfect inequality.
     *
     * To calculate the Gini Coefficient the following formula is used: 
     * Final Sum of Differences * 2 / Average Income * (N * (N - 1)), where 
     * N is the number of incomes.
     *
     * @return the Gini Coefficient
     */
    public static double giniCoef() {
        int numOf = incomes.size();

        getDifInSums();

        double gini
                = (sumOfDif * 2) / (average * (numOf * (numOf - 1)));

        return gini;

    }

    /**
     * This method prints an introduction, which states what the program does
     * along with the author information.
     */
    public static void printIntroduction() {
        System.out.println("Gini Coefficient Calculator");
        System.out.println("---------------------------\n");
        System.out.println("Reads incomes and reports the Gini coefficient.");
        System.out.println("\nby Riley OKeefe");
    }

    /**
     * This method requires the user to press enter throughout the program.
     */
    public static void pause() {
        System.out.print("\n...press enter...");
        KBD.nextLine();
        System.out.println("");
    }

}

