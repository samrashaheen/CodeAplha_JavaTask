import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> studentNames = new ArrayList<>();
        ArrayList<Double> studentScores = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int numStudents = sc.nextInt();
        sc.nextLine(); // clear buffer

        for (int i = 0; i < numStudents; i++) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();
            System.out.print("Enter " + name + "'s score: ");
            double score = sc.nextDouble();
            sc.nextLine();

            studentNames.add(name);
            studentScores.add(score);
        }

        // Calculate stats
        double total = 0, highest = Double.MIN_VALUE, lowest = Double.MAX_VALUE;
        String highestName = "", lowestName = "";

        for (int i = 0; i < studentScores.size(); i++) {
            double score = studentScores.get(i);
            total += score;

            if (score > highest) {
                highest = score;
                highestName = studentNames.get(i);
            }
            if (score < lowest) {
                lowest = score;
                lowestName = studentNames.get(i);
            }
        }

        double average = total / studentScores.size();

        // Summary Report
        System.out.println("\n===== Student Grade Summary =====");
        for (int i = 0; i < studentNames.size(); i++) {
            System.out.println(studentNames.get(i) + " - " + studentScores.get(i));
        }
        System.out.println("\nAverage Score: " + average);
        System.out.println("Highest Score: " + highest + " (" + highestName + ")");
        System.out.println("Lowest Score: " + lowest + " (" + lowestName + ")");

        sc.close();
    }
}
