import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HelpDialog extends JDialog {
    private JPanel contentPane;
    private JLabel label;
    private JPanel panel;
    private HelpPane helpPane;

    public HelpDialog (JFrame frame) {
        super(frame);
        setTitle("Справка");
        setSize(800, 600);
        setResizable(false);
        label.setText(
                "<html>" +
                "В верхней части окна расположен список всех имеющихся задач.<br>"+
                "Для взаимодействия с уже имеющимися задачами необходимо выделить нужную кликом по ней в списке.<br>"+
                "В правом нижнем углу расположен список задач, выбранных для теста.<br>"+
                "Для создания теста сначала нужно добавить задачи в этот список.<br>"+
                "Выбирать элементы нужно по одному.<br>"+
                "Функция \"поиск\" выводит все задачи, в тексте которых содержится введенное поисковое слово.<br>"+
                "Подсказки по кнопкам можно посмотреть, наведя и удерживая курсор над кнопкой."
        );
        getContentPane().add(new HelpPane());
        getContentPane().add(panel);
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private class HelpPane extends JPanel{
        @Override
        protected void paintComponent (Graphics g) {
            super.paintComponent(g);
            try {
                g.drawImage(ImageIO.read(new File("imgs/tips.png")), 0, 0, null);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        private HelpPane(){
            super();
            setBounds(45, 150, 700, 400);
            setLayout(null);
        }
    }
}
