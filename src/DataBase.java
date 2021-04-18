import java.sql.*;
import java.util.ArrayList;

public final class DataBase {
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:Tasks.db");
            statement = connection.createStatement();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addTask(Task task){
        try {
            statement.execute(
                    "INSERT INTO 'tasks' " +
                            "('text', 'Answer', 'Was solved', 'Was solved correctly') " +
                            "VALUES ('" + task.getText() + "', '" + task.getRight_answer() + "', '" + task.WasSolved() + "', '" + task.WasSolvedCorrectly() + "')"
            );
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static ArrayList<Task> getTasksByText (String query){
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT * FROM 'tasks' WHERE text LIKE ('%" + query + "%')");
            while (resultSet.next()) {
                tasks.add(new Task(resultSet));
            }
            resultSet.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return tasks;
    }
    public static ArrayList<Task> getTasks(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM 'tasks'");
            ArrayList<Task> tasks = new ArrayList<>();
            while (resultSet.next()) {
                tasks.add(new Task(resultSet));
            }
            resultSet.close();
            return tasks;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    public static void deleteTask(Task task){
        try {
            statement.executeUpdate("DELETE FROM tasks WHERE Id = "+task.getId());
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateTask(Task task, String new_text, String new_answer){
        try {
            statement.executeUpdate("UPDATE tasks SET 'text' = '"+new_text+"', 'Answer' = '"+new_answer+"' WHERE Id = '"+task.getId()+"'");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void updateTaskFlags(Task task){
        try {
            statement.executeUpdate("UPDATE tasks SET 'Was solved' = '"+(task.WasSolved()?1:0)+"', 'Was solved correctly' = '"+(task.WasSolvedCorrectly()?1:0)+"'");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    private DataBase(){}
}

