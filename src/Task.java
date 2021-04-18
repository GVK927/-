import java.sql.ResultSet;
import java.sql.SQLException;

public class Task {
    private int id;
    private String task_text;
    private String right_answer;
    private boolean was_solved;
    private boolean was_solved_correctly;
    private String answer;

    public Task(String task_text, String right_answer){
        this.was_solved = false;
        this.was_solved_correctly = false;
        this.right_answer = right_answer;
        this.task_text = task_text;
    }
    public Task(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("Id");
        this.task_text = resultSet.getString("text");
        this.right_answer = resultSet.getString("Answer");
        this.was_solved = resultSet.getBoolean("Was solved");
        this.was_solved_correctly = resultSet.getBoolean("Was solved correctly");
    }

    public String getText () {
        return task_text;
    }
    public void setText (String task_text) {
        this.task_text = task_text;
    }
    public String getRight_answer () {
        return right_answer;
    }
    public void setRight_answer (String right_answer) {
        this.right_answer = right_answer;
    }
    public boolean WasSolvedCorrectly () {
        return was_solved_correctly;
    }
    public void setSolvedCorrectly (boolean was_solved_correctly) {
        this.was_solved_correctly = was_solved_correctly;
    }
    public int getId () {
        return id;
    }
    public void setAnswer(String answer){
        this.answer = answer;
        this.was_solved = true;
        if(answer.equals(this.right_answer)){
            was_solved_correctly = true;
        }else{
            was_solved_correctly = false;
        }
    }
    public boolean WasSolved () {
        return was_solved;
    }

    public Object[] toArray(){
        return new Object[]{
                this.id,
                this.task_text,
                this.right_answer,
                this.was_solved?"Да":"Нет",
                this.was_solved_correctly?"Да":"Нет"
        };
    }

    @Override
    public String toString () {
        return this.task_text;
    }
}
