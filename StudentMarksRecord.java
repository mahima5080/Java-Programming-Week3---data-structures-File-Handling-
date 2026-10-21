import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class StudentMarksRecord {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> marksList = new ArrayList<>();

        System.out.println("====================================");
        System.out.println("     STUDENT MARKS RECORD PROGRAM   ");
        System.out.println("====================================");

        System.out.print("Enter total number of students: ");
        int count = scanner.nextInt();

        for (int i = 1; i <= count; i++) {
            System.out.print("Enter marks for student " + i + ": ");
            double mark = scanner.nextDouble();
            marksList.add(mark);
        }

        if (!marksList.isEmpty()) {
            double highest = Collections.max(marksList);
            double lowest = Collections.min(marksList);

            double sum = 0;
            for (double m : marksList) {
                sum += m;
            }
            double average = sum / marksList.size();

            System.out.println("\n------------------------------------");
            System.out.println("            MARKS SUMMARY           ");
            System.out.println("------------------------------------");
            System.out.println("Total Students Recorded : " + marksList.size());
            System.out.println("All Marks Entered       : " + marksList);
            System.out.printf("Highest Marks           : %.2f\n", highest);
            System.out.printf("Lowest Marks            : %.2f\n", lowest);
            System.out.printf("Average Class Marks     : %.2f\n", average);
            System.out.println("====================================");
        } else {
            System.out.println("No marks recorded.");
        }

        scanner.close();
    }
}