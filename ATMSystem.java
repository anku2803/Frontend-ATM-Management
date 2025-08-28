import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ATMSystem {
    private double balance = 1000;
    private JFrame frame;
    private JTextArea textArea;
    private JTextField inputField;

    public ATMSystem() {
        frame = new JFrame("ATM Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLayout(new BorderLayout(10, 10));
        
        // Display Area
        textArea = new JTextArea(6, 20);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 14));
        textArea.setBackground(Color.LIGHT_GRAY);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        
        // Unified Panel for Input and Buttons
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setPreferredSize(new Dimension(250, 450));
        controlPanel.setBackground(new Color(50, 50, 50));

        // Input Field
        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.BOLD, 18));
        inputField.setHorizontalAlignment(JTextField.CENTER);
        inputField.setMaximumSize(new Dimension(200, 40));
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(inputField);

        // Keypad Panel
        JPanel keypadPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        keypadPanel.setBackground(new Color(50, 50, 50));
        String[] keys = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "Clear", "Enter"};
        
        for (String key : keys) {
            JButton button = new JButton(key);
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            
            button.addActionListener(e -> {
                if (key.equals("Clear")) {
                    inputField.setText("");
                } else if (key.equals("Enter")) {
                    textArea.append("Entered Amount: " + inputField.getText() + "\n");
                } else {
                    inputField.setText(inputField.getText() + key);
                }
            });
            keypadPanel.add(button);
        }
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(keypadPanel);

        // Action Buttons
        String[] buttonLabels = {"Check Balance", "Deposit", "Withdraw", "Exit"};
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        buttonPanel.setBackground(new Color(50, 50, 50));

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setBackground(new Color(70, 130, 180));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    switch (label) {
                        case "Check Balance" -> displayBalance();
                        case "Deposit" -> depositAmount();
                        case "Withdraw" -> withdrawAmount();
                        case "Exit" -> exitATM();
                    }
                }
            });
            buttonPanel.add(button);
        }
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(buttonPanel);
        
        frame.add(controlPanel, BorderLayout.EAST);
        frame.setVisible(true);
    }

    private void displayBalance() {
        textArea.append(String.format("Current balance: $%.2f\n", balance));
    }

    private void depositAmount() {
        try {
            double deposit = Double.parseDouble(inputField.getText());
            if (deposit > 0) {
                balance += deposit;
                textArea.append(String.format("Deposited $%.2f. New balance: $%.2f\n", deposit, balance));
            } else {
                textArea.append("Please enter a valid deposit amount.\n");
            }
        } catch (NumberFormatException ex) {
            textArea.append("Invalid input. Please enter a valid number.\n");
        }
        inputField.setText("");
    }

    private void withdrawAmount() {
        try {
            double withdraw = Double.parseDouble(inputField.getText());
            if (withdraw > 0 && withdraw <= balance) {
                balance -= withdraw;
                textArea.append(String.format("Withdrew $%.2f. New balance: $%.2f\n", withdraw, balance));
            } else if (withdraw <= 0) {
                textArea.append("Withdrawal amount must be greater than zero.\n");
            } else {
                textArea.append("Insufficient funds.\n");
            }
        } catch (NumberFormatException ex) {
            textArea.append("Invalid input. Please enter a valid number.\n");
        }
        inputField.setText("");
    }

    private void exitATM() {
        textArea.append("Exiting...\n");
        System.exit(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ATMSystem::new);
    }
}