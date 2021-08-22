import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextFile {

    public static List<Task> readFromTextFile() throws FileNotFoundException {
        File file = new File("./data/task_list.txt");
        Scanner reader = new Scanner(file);
        List<Task> taskList = new ArrayList<>();

        while (reader.hasNextLine()) {
            String taskString = reader.nextLine();
            String[] splitDescription = taskString.split(" \\| ");
            Task task;

            if (splitDescription[0].equals("T")) {
                task = new Todo(splitDescription[2]);
            } else if (splitDescription[0].equals("D")) {
                task = new Deadline(splitDescription[2], splitDescription[3]);
            } else {
                task = new Event(splitDescription[2], splitDescription[3]);
            }

            if (splitDescription[1].equals("1")) {
                task.markAsDone();
            }
            taskList.add(task);
        }
        return taskList;
    }

    public static void writeToTextFile(List<Task> taskList) throws FileNotFoundException {
        PrintWriter out = new PrintWriter("./data/task_list.txt");
        for (Task task : taskList) {
            if (task instanceof Todo) {
                out.printf("T | %s | %s%n", task.getStatusIcon() == "X" ? 1 : 0, task.getDescription());
            } else if (task instanceof Deadline) {
                out.printf("D | %s | %s | %s%n", task.getStatusIcon() == "X" ? 1 : 0, task.getDescription(),
                        ((Deadline) task).getDeadline());
            } else {
                out.printf("E | %s | %s | %s%n", task.getStatusIcon() == "X" ? 1 : 0, task.getDescription(),
                        ((Event) task).getTime());
            }
        }
        out.close();
    }
}
