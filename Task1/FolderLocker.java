import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;


public class Folderlockr extends JFrame implements ActionListener {
    private JLabel label2, label3;
    private JTextField textField;
    private JPasswordField passwordField;
    private JButton lockButton, unlockButton, browseButton;
    private JFileChooser fileChooser;
    private File file;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;

    public Folderlockr() {
        super("Folderlocker");
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        getContentPane().setLayout(null);
        JLabel label = new JLabel();
        label.setBounds(40, 15, 0, 0);
        getContentPane().add(label);
        JLabel label_1 = new JLabel();
        label_1.setBounds(285, 15, 0, 0);
        getContentPane().add(label_1);
        
        JLabel lblNewLabel = new JLabel("Folder Locker");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 22));
        lblNewLabel.setBounds(276, 21, 141, 34);
        getContentPane().add(lblNewLabel);
        
        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.controlHighlight);
        panel.setBounds(40, 76, 617, 326);
        panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
        getContentPane().add(panel);
        panel.setLayout(null);
        browseButton = new JButton("Browse");
        browseButton.setFont(new Font("Times New Roman", Font.BOLD, 12));
        browseButton.setBounds(53, 76, 99, 38);
        panel.add(browseButton);
        textField = new JTextField(20);
        textField.setBounds(261, 76, 310, 38);
        panel.add(textField);
        label3 = new JLabel("Enter Password:");
        label3.setFont(new Font("Times New Roman", Font.BOLD, 13));
        label3.setBounds(53, 150, 112, 38);
        panel.add(label3);
        passwordField = new JPasswordField(20);
        passwordField.setBounds(261, 151, 310, 38);
        panel.add(passwordField);
        label2 = new JLabel("Select Folder:");
        label2.setFont(new Font("Times New Roman", Font.BOLD, 12));
        label2.setBounds(53, 50, 85, 13);
        panel.add(label2);
        unlockButton = new JButton("Unlock");
        unlockButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
        unlockButton.setBounds(199, 255, 99, 38);
        panel.add(unlockButton);
        lockButton = new JButton("Lock");
        lockButton.setFont(new Font("Times New Roman", Font.BOLD, 13));
        lockButton.setBounds(53, 255, 99, 38);
        panel.add(lockButton);
        
        lblNewLabel_1 = new JLabel(":-");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_1.setBounds(199, 80, 24, 26);
        panel.add(lblNewLabel_1);
        
        lblNewLabel_2 = new JLabel(":-");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblNewLabel_2.setBounds(199, 154, 24, 26);
        panel.add(lblNewLabel_2);
        
        lockButton.addActionListener(this);
        unlockButton.addActionListener(this);
        browseButton.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(707, 504);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lockButton) {
            String folderPath = textField.getText();
            String password = new String(passwordField.getPassword());

            if (folderPath.equals("")) {
                JOptionPane.showMessageDialog(this, "Please select a folder.");
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter a password.");
            } else {
                try {
                    Runtime.getRuntime().exec("attrib +h +s " + folderPath);
                    BufferedWriter writer = new BufferedWriter(new FileWriter(folderPath + "/.lock"));
                    writer.write(password);
                    writer.close();
                    JOptionPane.showMessageDialog(this, "Folder locked successfully.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while locking the folder.");
                }
            }
        } else if (e.getSource() == unlockButton) {
            String folderPath = textField.getText();
            String password = new String(passwordField.getPassword());

            if (folderPath.equals("")) {
                JOptionPane.showMessageDialog(this, "Please select a folder.");
            } else if (password.equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter a password.");
            } else {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(folderPath + "/.lock"));
                    String storedPassword = reader.readLine();
                    reader.close();

                    if (password.equals(storedPassword)) {
                        Runtime.getRuntime().exec("attrib -h -s " + folderPath);
                        File lockFile = new File(folderPath + "/.lock");
                        lockFile.delete();
                        JOptionPane.showMessageDialog(this, "Folder unlocked successfully.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Incorrect password.");
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(this, "The folder is not locked.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "An error occurred while unlocking the folder.");
                }
            }
        } else if (e.getSource() == browseButton) {
            int returnValue = fileChooser.showOpenDialog(this);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                textField.setText(file.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        new Folderlockr();
    }
}


