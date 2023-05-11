import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.text.NumberFormatter;

public class timer {
   private JButton button;
   private JFormattedTextField hours;
   private JFormattedTextField minutes;
   private JFormattedTextField seconds;

   private Timer timer;
   private int delay = 1000;
   private ActionListener taskPerformer = new ActionListener() {
       public void actionPerformed(ActionEvent evt) {

                   if(sec >= 1) {
                       sec = sec - 1;
                       seconds.setText(String.valueOf(sec));
                   }
                   else if(sec == 0 && min > 0) {
                       sec = 59;
                       min = min - 1;
                       seconds.setText(String.valueOf(sec));
                       minutes.setText(String.valueOf(min));
                   }
                   else if(min == 0 && hrs > 0) {
                       sec = 59;
                       min = 59;
                       hrs = hrs - 1;
                       seconds.setText(String.valueOf(sec));
                       minutes.setText(String.valueOf(min));
                       hours.setText(String.valueOf(hrs));
                   }
                   if(hrs == 0 && min == 0 && sec == 0) {
                       Toolkit.getDefaultToolkit().beep();
                       JOptionPane.showMessageDialog(null,"Countdown ended!","Ende", JOptionPane.CANCEL_OPTION);
                       timer.stop();
                   }
       }
   };

   private int hrs;
   private int min;
   private int sec;

   public static void main(String[] args) throws InterruptedException {

       SwingUtilities.invokeLater(Counter::new);
   }

   timer() {
       JFrame frame = new JFrame();
    
       frame.setSize(300, 200);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       JPanel panel = new JPanel(new BorderLayout());
       JPanel subpanel1 = new JPanel(new GridLayout(2, 3));

       /* 
        * The following lines ensure that the user can 
        * only enter numbers.
       */

       NumberFormat format = NumberFormat.getInstance();
       NumberFormatter formatter = new NumberFormatter(format);
       formatter.setValueClass(Integer.class);
       formatter.setMinimum(0);
       formatter.setMaximum(Integer.MAX_VALUE);
       formatter.setAllowsInvalid(false);
       formatter.setCommitsOnValidEdit(true);

       //"labeling"

       JTextField text1 = new JTextField();
       text1.setText("hours:");
       text1.setEditable(false);

       JTextField text2 = new JTextField();
       text2.setText("minutes:");
       text2.setEditable(false);


       JTextField text3 = new JTextField();
       text3.setText("seconds:");
       text3.setEditable(false);

       //fields for minutes and seconds
       hours = new JFormattedTextField(formatter);
       minutes = new JFormattedTextField(formatter);
       seconds = new JFormattedTextField(formatter);
       hours.setText("0");
       minutes.setText("0");
       seconds.setText("0");

       JPanel subpanel2 = new JPanel();

       /*
        * When the user presses the OK-button, the program will
        * start to count down.
       */

       button = new JButton("OK");
       button.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent actionEvent) {
               hrs = Integer.valueOf(hours.getText());
               min = Integer.valueOf(minutes.getText());
               sec = Integer.valueOf(seconds.getText());
               button.setEnabled(false);

               //Timer for one second delay
               Timer timer = new Timer(delay, taskPerformer);
               timer.start();  


           }
       });

       //Reset-button
       JButton button2 = new JButton("Reset");
       button2.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent actionEvent) {
               hours.setText("0");
               minutes.setText("0");
               seconds.setText("0");
               button.setEnabled(true);
               hrs = 0;
               min = 0;
               sec = 0;
           }
       });

       subpanel1.add(text1);
       subpanel1.add(text2);
       subpanel1.add(text3);
       subpanel1.add(hours);
       subpanel1.add(minutes);
       subpanel1.add(seconds);
       subpanel2.add(button);
       subpanel2.add(button2);
       panel.add(subpanel1, BorderLayout.CENTER);
       panel.add(subpanel2, BorderLayout.PAGE_END);
       frame.add(panel);
       frame.setVisible(true);
   }
}
