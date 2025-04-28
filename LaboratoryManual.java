


import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.*;

public class LaboratoryManual {

    private JTextField nameField;
    private JTextField emailField;
    private JTextArea displayArea;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LaboratoryManual().initializeGUI();
        });
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("CRUD Database Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 600));

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Database Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        panel.add(titleLabel, constraints);

        addFormElements(panel, constraints);
        addTransactionButtons(panel, constraints);
        addDisplayArea(panel, constraints);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void addFormElements(JPanel panel, GridBagConstraints constraints) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("User Details"));

        JLabel nameLabel = new JLabel("Name:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        formPanel.add(nameLabel, constraints);

        nameField = new JTextField(20);
        constraints.gridx = 1;
        formPanel.add(nameField, constraints);

        JLabel emailLabel = new JLabel("Email:");
        constraints.gridy = 1;
        constraints.gridx = 0;
        formPanel.add(emailLabel, constraints);

        emailField = new JTextField(20);
        constraints.gridx = 1;
        formPanel.add(emailField, constraints);

        constraints.gridy = 1;
        panel.add(formPanel, constraints);
    }

    private void addTransactionButtons(JPanel panel, GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> addUser());
        constraints.gridy = 0;
        buttonPanel.add(addButton, constraints);

        JButton showButton = new JButton("Show Records");
        showButton.addActionListener(e -> showRecords());
        constraints.gridy = 1;
        buttonPanel.add(showButton, constraints);

        constraints.gridy = 2;
        panel.add(buttonPanel, constraints);
    }

    private void addDisplayArea(JPanel panel, GridBagConstraints constraints) {
        displayArea = new JTextArea(10, 30);
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        constraints.gridy = 3;
        panel.add(scrollPane, constraints);
    }

    private void addUser() {
        String username = "root"; // your MySQL username
        String password = ""; // your MySQL password
        String databaseUrl = "jdbc:mysql://localhost:3306/myoop";

        try (Connection con = DriverManager.getConnection(databaseUrl, username, password)) {
            String insertQuery = "INSERT INTO users (name, email) VALUES (?, ?)";
            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
            insertStatement.setString(1, nameField.getText());
            insertStatement.setString(2, emailField.getText());
            insertStatement.executeUpdate();
            displayArea.append("User added successfully!\n");
        } catch (Exception e) {
            displayArea.append("Error: " + e.getMessage() + "\n");
        }
    }

    private void showRecords() {
        String username = "root";
        String password = "";
        String databaseUrl = "jdbc:mysql://localhost:3306/myoop";

        try (Connection con = DriverManager.getConnection(databaseUrl, username, password)) {
            String selectQuery = "SELECT * FROM users";
            PreparedStatement selectStatement = con.prepareStatement(selectQuery);
            ResultSet rs = selectStatement.executeQuery();
            displayArea.setText("");
            while (rs.next()) {
                displayArea.append("ID: " + rs.getInt("id") + " Name: " + rs.getString("name") + " Email: " + rs.getString("email") + "\n");
            }
        } catch (Exception e) {
            displayArea.append("Error: " + e.getMessage() + "\n");
        }
    }
}
