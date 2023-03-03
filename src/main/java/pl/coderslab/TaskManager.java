package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    private static final List<String[]> tasks = new ArrayList<>();

    public static void main(String[] args) {

        readTasksFromFile("tasks.csv");
        displayOptions();


        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print(ConsoleColors.BLUE + "Please select an option: " + ConsoleColors.RESET);
            input = scanner.nextLine().toLowerCase();
            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    saveTasksToFile("tasks.csv");
                    System.out.println(ConsoleColors.RED + "Bye, bye." + ConsoleColors.RESET);
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    break;
            }
        } while (!input.equals("exit"));
    }

    private static void displayOptions() {
        String[] options = {"add", "remove", "list", "exit"};
        System.out.println(ConsoleColors.BLUE + "Available options:" + ConsoleColors.RESET);
        for (String option : options) {
            System.out.println(option);
        }
    }

    private static void readTasksFromFile(String filename) {
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] task = line.split(",");
                tasks.add(task);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    private static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Task description: ");
        String description = scanner.nextLine();
        System.out.print("Task due date (YYYY-MM-DD): ");
        String dueDate = scanner.nextLine();
        System.out.print("Is the task important? (true/false): ");
        String isImportant = scanner.nextLine();
        tasks.add(new String[]{description, dueDate, isImportant});
        System.out.println("Task added successfully.");
    }

    private static void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println(ConsoleColors.BLUE + "Tasks:" + ConsoleColors.RESET);
            for (int i = 0; i < tasks.size(); i++) {
                String[] task = tasks.get(i);
                System.out.printf("%d. %s (due %s, %s)\n", i, task[0], task[1], task[2]);
            }
        }
    }

    private static void removeTask() {
        Scanner scanner = new Scanner(System.in);
        int index;
        do {
            System.out.print("Task number to remove: ");
            try {
                index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                index = -1;
            }
            if (index < 0 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
            }
        } while (index < 0 || index >= tasks.size());
        tasks.remove(index);
        System.out.println("Task removed successfully.");
    }

    private static void saveTasksToFile(String filename) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (String[] task : tasks) {
                writer.write(task[0] + "," + task[1] + "," + task[2] + "\n");
            }
            writer.close();
            System.out.println("Tasks saved successfully.");
        } catch (IOException e) {
            System.out.println("Cannot save tasks");
        }
    }
}