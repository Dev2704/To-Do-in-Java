import java.util.*;
import java.io.*;

class Task{
    String title;
    boolean isDone;

    public Task(String title){
        this.title = title;
        this.isDone = false;
    }
    public void markDone(){
        isDone = true;
    }
    public String toString(){
        return (isDone ? "[x]" : "[]" ) + title;
    }
}



public class app{
    static final String FILE_NAME = "tasks.txt";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        loadTasks(tasks);

        while (true) {
            System.out.println("\n--- To-Do List ---");
            System.out.println("1. Add Task.");
            System.out.println("2. View Tasks.");
            System.out.println("3. Mark As Done.");
            System.out.println("4. Delete Task.");
            System.out.println("5. Exit.");
            System.out.print("Enter your Choice: ");
            int Choice = sc.nextInt();
            sc.nextLine();

            switch (Choice) {
                case 1:
                    System.out.print("Enter Task name: ");
                    String taskTitle = sc.nextLine();
                    tasks.add(new Task(taskTitle));
                    System.out.println("Task added.");
                    break;

                case 2:
                    if(tasks.isEmpty()){
                        System.out.println("NO Tasks yet.");
                    }else{
                        System.out.println("\nYour Tasks:");
                        for(int i = 0; i<tasks.size(); i++){
                            System.out.println((i+1) + ". " + tasks.get(i));
                        }
                    }
                    break;
                
                case 3:
                    if(tasks.isEmpty()){
                        System.out.println("No tasks to mark!");
                        break;
                    }
                    System.out.print("Enter task number to mark as done: ");
                    int doneIndex = sc.nextInt();
                    if (doneIndex >=1 && doneIndex<= tasks.size()){
                        tasks.get(doneIndex - 1).markDone();
                        System.out.println("Task marked as Done!");
                    }else{
                        System.out.println("Invalid Taks Number.");
                    }
                    break;
                
                case 4:
                    if(tasks.isEmpty()){
                        System.out.println("No tasks to Delete!");
                        break;
                    }
                    System.out.print("Enter task number to mark as done: ");
                    int delIndex = sc.nextInt();
                    if (delIndex >=1 && delIndex<= tasks.size()){
                        Task removed = tasks.remove(delIndex - 1);
                        System.out.println("Task Deleted: " + removed.title);
                    }else{
                        System.out.println("Invalid Taks Number.");
                    }
                    break;

                case 5:
                    saveTasks(tasks);
                    System.out.println("Exiting. Stay Productive!!");
                    sc.close();
                    return;

                default:
                    System.out.println("Wrong Choice Selected.");
            }
        }
    }
    public static void loadTasks(ArrayList<Task> tasks) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) return;

            Scanner fileReader = new Scanner(file);
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine();
                String[] parts = line.split("\\|", 2);
                if (parts.length < 2) continue;

                boolean isDone = parts[0].equals("1");
                String title = parts[1];
                Task task = new Task(title);
                if (isDone) task.markDone();
                tasks.add(task);
            }
            fileReader.close();
        } catch (Exception e) {
            System.out.println("❌ Error loading tasks: " + e.getMessage());
        }
    }

    public static void saveTasks(ArrayList<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(FILE_NAME);
            for (Task task : tasks) {
                writer.write((task.isDone ? "1" : "0") + "|" + task.title + "\n");
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("❌ Error saving tasks: " + e.getMessage());
        }
    }

    public static int getIndex(Scanner sc, int size) {
        try {
            int input = Integer.parseInt(sc.nextLine());
            if (input < 1 || input > size) {
                System.out.println("❌ Invalid task number.");
                return -1;
            }
            return input - 1;
        } catch (NumberFormatException e) {
            System.out.println("⚠️ Please enter a valid number.");
            return -1;
        }
    }
}