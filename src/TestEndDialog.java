import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TestEndDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JLabel label;
    private JPanel panel;
    private MainPanel mainPanel;

    public TestEndDialog (MainPanel mainPanel,TestPanel panel, Task[] tasks) {
        this.mainPanel = mainPanel;
        this.panel = panel;
        setContentPane(contentPane);
        setSize(400, 300);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        int successful_count = 0;
        for(Task i:tasks){
            if(i.WasSolvedCorrectly()) successful_count++;
            DataBase.updateTaskFlags(i);
        }
        label.setText("<html>Решено правильно: "+successful_count+"<br>Доля правильных решений: "+(successful_count/(double)tasks.length*100)+"%");

        buttonOK.addActionListener(e -> onOK());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onOK () {
        TestPanel panel_ = (TestPanel) panel;
        this.mainPanel.updateList();
        CardLayout c = (CardLayout)panel_.getFrame().getContentPane().getLayout();
        panel_.getFrame().getContentPane().remove(1);
        c.next(panel_.getFrame().getContentPane());
        dispose();
    }
}
