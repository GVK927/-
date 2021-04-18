import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TestPanel extends JPanel {
    private JTextField ansField;
    private JButton nextBtn;
    private JTextArea taskText;
    private JPanel panel;
    private int task_num;
    private Task[] tasks;
    private JFrame frame;
    private MainPanel mainPanel;

    public TestPanel(JFrame frame, MainPanel mainPanel,Task[] tasks){
        this.frame = frame;
        this.mainPanel = mainPanel;
        this.tasks = tasks;
        this.task_num = 0;
        taskText.setText(tasks[task_num].getText());
        ansField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained (FocusEvent e) {
                if(ansField.getText().equals("Введите ответ...")) ansField.setText("");
            }
            @Override
            public void focusLost (FocusEvent e) {
                if(ansField.getText().equals("")) ansField.setText("Введите ответ...");
            }
        });
        nextBtn.addActionListener(l->next());
    }
    private void next(){
        tasks[task_num].setAnswer(ansField.getText());
        task_num++;
        if(task_num==tasks.length){
            new TestEndDialog(this.mainPanel,this, tasks);
            return;
        }
        taskText.setText(tasks[task_num].getText());
        ansField.setText("Введите ответ...");
    }
    public JPanel getPanel () {
        return panel;
    }
    public JFrame getFrame () {
        return frame;
    }
}

