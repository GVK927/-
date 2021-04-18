import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class MainPanel extends JPanel {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 820;

    private JFrame frame;

    private JTable table1;
    private JTextArea textField;
    private JPanel mainPanel;
    private JButton delBtn;
    private JButton addBtn;
    private JButton red_updBtn;
    private JButton runTestBtn;
    private JButton helpBtn;
    private JTextField searchTextField;
    private JButton searchBtn;
    private JPanel ButtonsPanel;
    private JTextField ansField;
    private JPanel bottomPanel;
    private JList testTasksList;
    private JButton addToTestBtn;
    private JButton delTestTaskBtn;

    private boolean redBtn_pressed;

    private String[] table_columns = new String[]{"Номер", "Текст", "Правильный ответ", "Решена", "Решена правильно"};
    private ArrayList<Task> tasks;
    private LinkedHashSet<Task> testTasks;

    public static void main (String[] args) {
        JFrame frame = new JFrame("Электронный задачник");
        MainPanel mainPanel = new MainPanel(frame);
        JPanel panels = new JPanel(new CardLayout());
        panels.add(mainPanel.mainPanel, 0);
        frame.setContentPane(panels);
        frame.setResizable(false);
        mainPanel.updateList();
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public MainPanel (JFrame frame) {
        this.frame = frame;
        searchTextField.setToolTipText(null);
        testTasksList.setToolTipText(null);
        this.testTasks = new LinkedHashSet<>();
        this.redBtn_pressed = false;
        searchTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained (FocusEvent e) {
                if (searchTextField.getText().equals("Поиск...")) searchTextField.setText("");
            }

            @Override
            public void focusLost (FocusEvent e) {
                if (searchTextField.getText().isEmpty()) searchTextField.setText("Поиск...");
            }
        });
        ansField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained (FocusEvent e) {
                if(redBtn_pressed) table1.getSelectionModel().clearSelection();
                if (ansField.getText().equals("Введите ответ...")) ansField.setText("");
            }
            @Override
            public void focusLost (FocusEvent e) {
                if (ansField.getText().isEmpty()) ansField.setText("Введите ответ...");
            }
        });
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained (FocusEvent e) {
                if(redBtn_pressed) table1.getSelectionModel().clearSelection();
                if(textField.getText().equals("Введите текст задачи...")) textField.setText("");
            }
            @Override
            public void focusLost (FocusEvent e) {
                if(textField.getText().isEmpty()) textField.setText("Введите текст задачи...");
            }
        });

        runTestBtn.addActionListener(l->{
            if(testTasks.size()==0) return;
            frame.getContentPane().add(new TestPanel(this.frame, this, testTasks.toArray(new Task[testTasks.size()])).getPanel(), 1);
            CardLayout cardLayout = (CardLayout) frame.getContentPane().getLayout();
            cardLayout.next(frame.getContentPane());
        });
        helpBtn.addActionListener(l->{
            new HelpDialog(this.frame);
        });
        delTestTaskBtn.addActionListener(l->{
            if(testTasksList.getSelectedValue()==null) return;
            removeFromTestTasksList((Task)testTasksList.getSelectedValue());
        });
        addToTestBtn.addActionListener(l->{
            if(table1.getSelectedRow()==-1) return;
            addToTestTasksList(tasks.get(table1.getSelectedRow()));
        });
        searchBtn.addActionListener(l->{
            if(searchTextField.getText().equals("")||searchTextField.getText().equals("Поиск...")) {
                updateList();
                return;
            }
            tasks = DataBase.getTasksByText(searchTextField.getText());
            updateTable();
        });
        delBtn.addActionListener(l->{
            if(table1.getSelectedRow()==-1) return;
            DataBase.deleteTask(tasks.get(table1.getSelectedRow()));
            updateList();
        });
        addBtn.addActionListener(l->{
            if(textField.getText().equals("")||ansField.getText().equals("")) return;
            if(textField.getText().equals("Введите текст задачи...")||ansField.getText().equals("Введите ответ...")) return;
            DataBase.addTask(new Task(textField.getText(), ansField.getText()));
            textField.setText("Введите текст задачи...");
            ansField.setText("Введите ответ...");
            updateList();
        });
        red_updBtn.addActionListener(l->{
            if(table1.getSelectedRow()==-1) return;
            if(redBtn_pressed){
                red_updBtn.setToolTipText("Применить редактирование");
                red_updBtn.setIcon(new ImageIcon("imgs/w256h2561386955471success2.png"));
                textField.setText(tasks.get(table1.getSelectedRow()).getText());
                ansField.setText(tasks.get(table1.getSelectedRow()).getRight_answer());
            }else{
                red_updBtn.setToolTipText("Редактировать задачу");
                red_updBtn.setIcon(new ImageIcon("imgs/—Pngtree—vector pencil icon_4183784.png"));
                DataBase.updateTask(tasks.get(table1.getSelectedRow()), textField.getText(), ansField.getText());
                updateList();
            }
            redBtn_pressed = !redBtn_pressed;
        });
    }

    private void updateTable(){
        Object[][] tasks_ = new Object[this.tasks.size()][];
        for (int i = 0; i < tasks_.length; i++) {
            tasks_[i] = this.tasks.get(i).toArray();
            tasks_[i][0] = i+1;
        }
        this.table1.setModel(new DefaultTableModel(tasks_, this.table_columns));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < 5; i++) {
            if (i == 1) continue;
            this.table1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TableColumnModel columnModel = this.table1.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(905);
        columnModel.getColumn(2).setPreferredWidth(125);
        columnModel.getColumn(3).setPreferredWidth(60);
        columnModel.getColumn(4).setPreferredWidth(125);
        this.table1.getTableHeader().setResizingAllowed(false);
    }
    public void updateList(){
        this.tasks = DataBase.getTasks();
        updateTable();
    }
    private void addToTestTasksList (Task task){
        testTasks.add(task);
        testTasksList.setListData(testTasks.toArray());
    }
    private void removeFromTestTasksList(Task task){
        testTasks.remove(task);
        testTasksList.setListData(testTasks.toArray());
    }

    public JFrame getFrame () {
        return frame;
    }
}
