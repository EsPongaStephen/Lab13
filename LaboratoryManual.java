
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LaboratoryManual {

    private JTextField nameField;
    private JTextField emailField;
    private JTextArea displayArea;
    private int offset;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LaboratoryManual().initializeGUI();
        });
    }

    private void initializeGUI() {
        offset = 0;
        JFrame frame = new JFrame("Laboratory Manual - Database Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 600));

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Database Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        mainPanel.add(titleLabel, constraints);

        addFormElements(mainPanel, constraints);
        addTransactionButtons(mainPanel, constraints);
        addDisplayArea(mainPanel, constraints);

        frame.add(mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
    private void addFormElements(JPanel panel, GridBagConstraints constraints) {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("User Details"));
        GridBagConstraints formConstraints = new GridBagConstraints();
        formConstraints.insets = new Insets(5, 5, 5, 5);
        formConstraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        formConstraints.gridx = 0;
        formConstraints.gridy = 0;
        formPanel.add(nameLabel, formConstraints);

        nameField = new JTextField(20);
        formConstraints.gridx = 1;
        formPanel.add(nameField, formConstraints);

        JLabel emailLabel = new JLabel("Email:");
        formConstraints.gridy = 1;
        formConstraints.gridx = 0;
        formPanel.add(emailLabel, formConstraints);

        emailField = new JTextField(20);
        formConstraints.gridx = 1;
        formPanel.add(emailField, formConstraints);

        constraints.gridy = 1;
        panel.add(formPanel, constraints);
    }
    private void addTransactionButtons(JPanel panel, GridBagConstraints constraints) {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));
        GridBagConstraints btnConstraints = new GridBagConstraints();
        btnConstraints.insets = new Insets(5, 5, 5, 5);
        btnConstraints.fill = GridBagConstraints.HORIZONTAL;

        JButton addButton = new JButton("Add User");
        addButton.addActionListener(e -> addUser());
        btnConstraints.gridy = 0;
        buttonPanel.add(addButton, btnConstraints);

        JButton showButton = new JButton("Show Records");
        showButton.addActionListener(e -> showRecords());
        btnConstraints.gridy = 1;
        buttonPanel.add(showButton, btnConstraints);

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
        String username = "root";
        String password = "";
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
