import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public String toFileString() {
        return id + "," + name + "," + department + "," + salary;
    }

    public static Employee fromFileString(String fileLine) {
        String[] parts = fileLine.split(",");
        if (parts.length == 4) {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String dept = parts[2].trim();
            double salary = Double.parseDouble(parts[3].trim());
            return new Employee(id, name, dept, salary);
        }
        return null;
    }

    public void display() {
        System.out.printf("ID: %-5d | Name: %-20s | Dept: %-15s | Salary: $%.2f\n",
                id, name, department, salary);
    }
}

public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.txt";
    private static List<Employee> employeeList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadEmployeesFromFile();

        int choice = -1;
        do {
            System.out.println("\n====================================");
            System.out.println("   EMPLOYEE MANAGEMENT SYSTEM (3.0) ");
            System.out.println("====================================");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Save & Exit");
            System.out.println("====================================");
            System.out.print("Enter choice (1-4): ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        addEmployee(scanner);
                        break;
                    case 2:
                        displayEmployees();
                        break;
                    case 3:
                        searchEmployee(scanner);
                        break;
                    case 4:
                        saveEmployeesToFile();
                        System.out.println("Data saved successfully. Goodbye! 👋");
                        break;
                    default:
                        System.out.println("❌ Invalid choice! Select between 1 and 4.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid integer input. Please try again.");
            }
        } while (choice != 4);

        scanner.close();
    }

    private static void addEmployee(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID: ");
            int id = Integer.parseInt(scanner.nextLine());

            // Check if ID already exists
            for (Employee emp : employeeList) {
                if (emp.getId() == id) {
                    System.out.println("❌ Error: Employee ID already exists!");
                    return;
                }
            }

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter Department: ");
            String dept = scanner.nextLine().trim();

            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            if (salary < 0) {
                System.out.println("❌ Error: Salary cannot be negative!");
                return;
            }

            Employee newEmp = new Employee(id, name, dept, salary);
            employeeList.add(newEmp);
            saveEmployeesToFile();
            System.out.println("✅ Employee added and saved to file successfully!");

        } catch (NumberFormatException e) {
            System.out.println("❌ Input Error: ID and Salary must be numeric values!");
        }
    }

    private static void displayEmployees() {
        if (employeeList.isEmpty()) {
            System.out.println("No employee records available.");
            return;
        }
        System.out.println("\n-------------------------------------------------------------------------");
        System.out.println("                         EMPLOYEE DIRECTORY                              ");
        System.out.println("-------------------------------------------------------------------------");
        for (Employee emp : employeeList) {
            emp.display();
        }
        System.out.println("-------------------------------------------------------------------------");
    }

    private static void searchEmployee(Scanner scanner) {
        try {
            System.out.print("Enter Employee ID to Search: ");
            int id = Integer.parseInt(scanner.nextLine());

            for (Employee emp : employeeList) {
                if (emp.getId() == id) {
                    System.out.println("\n✅ Record Found:");
                    emp.display();
                    return;
                }
            }
            System.out.println("❌ Employee with ID " + id + " not found.");
        } catch (NumberFormatException e) {
            System.out.println("❌ Input Error: Please enter a valid numeric Employee ID.");
        }
    }

    private static void saveEmployeesToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Employee emp : employeeList) {
                writer.write(emp.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ Error saving to file: " + e.getMessage());
        }
    }

    private static void loadEmployeesFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        employeeList.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Employee emp = Employee.fromFileString(line);
                if (emp != null) {
                    employeeList.add(emp);
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading from file: " + e.getMessage());
        }
    }
}