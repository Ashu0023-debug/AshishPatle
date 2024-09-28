package Assessment.Assessment;

import javax.swing.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class App extends JFrame {
    private JTextField insertField1, insertField2, deleteField, updateField1, updateField2;
    private JTextArea outputArea;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public App() {
        setTitle("CRUD Application - Hibernate & MySQL");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Colors and Fonts
        Color bgColor = new Color(245, 245, 245);  // Light grey background
        Color btnColor = new Color(0, 153, 76);    // Green button color
        Color hoverColor = new Color(34, 139, 34); // Darker green on hover
        Color textColor = Color.WHITE;
        Font customFont = new Font("Segoe UI", Font.PLAIN, 14);

        // North Panel - Title
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(50, 50, 50));
        JLabel titleLabel = new JLabel("CRUD Operations with Hibernate");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // South Panel - Output Area
        JPanel outputPanel = new JPanel();
        outputPanel.setBackground(bgColor);
        outputArea = new JTextArea(10, 60);
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        outputPanel.add(scrollPane);
        add(outputPanel, BorderLayout.SOUTH);

        // CardLayout for different sections
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        add(cardPanel, BorderLayout.CENTER);

        cardPanel.add(createInsertPanel(btnColor, textColor, hoverColor), "Insert");
        cardPanel.add(createDeletePanel(btnColor, textColor, hoverColor), "Delete");
        cardPanel.add(createUpdatePanel(btnColor, textColor, hoverColor), "Update");
        cardPanel.add(createViewPanel(btnColor, textColor, hoverColor), "View");

        // Sidebar for selecting actions
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(4, 1, 5, 5));
        sidebar.setBackground(new Color(230, 230, 230));
        sidebar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton insertButton = createStyledButton("Insert", btnColor, textColor, hoverColor);
        JButton deleteButton = createStyledButton("Delete", btnColor, textColor, hoverColor);
        JButton updateButton = createStyledButton("Update", btnColor, textColor, hoverColor);
        JButton viewButton = createStyledButton("View", btnColor, textColor, hoverColor);

        sidebar.add(insertButton);
        sidebar.add(deleteButton);
        sidebar.add(updateButton);
        sidebar.add(viewButton);

        add(sidebar, BorderLayout.WEST);

        // Button Actions
        insertButton.addActionListener(e -> cardLayout.show(cardPanel, "Insert"));
        deleteButton.addActionListener(e -> cardLayout.show(cardPanel, "Delete"));
        updateButton.addActionListener(e -> cardLayout.show(cardPanel, "Update"));
        viewButton.addActionListener(e -> cardLayout.show(cardPanel, "View"));
    }

    private JPanel createInsertPanel(Color btnColor, Color textColor, Color hoverColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel insertLabel = new JLabel("Insert First & Last Name:");
        insertLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(insertLabel, gbc);

        insertField1 = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(insertField1, gbc);

        insertField2 = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(insertField2, gbc);

        JButton insertButton = createStyledButton("Insert", btnColor, textColor, hoverColor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(insertButton, gbc);

        insertButton.addActionListener(e -> insertData());

        return panel;
    }

    private JPanel createDeletePanel(Color btnColor, Color textColor, Color hoverColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel deleteLabel = new JLabel("Delete by ID:");
        deleteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(deleteLabel, gbc);

        deleteField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(deleteField, gbc);

        JButton deleteButton = createStyledButton("Delete", btnColor, textColor, hoverColor);
        gbc.gridx = 2;
        gbc.gridy = 0;
        panel.add(deleteButton, gbc);

        deleteButton.addActionListener(e -> deleteData());

        return panel;
    }

    private JPanel createUpdatePanel(Color btnColor, Color textColor, Color hoverColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(245, 245, 245));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel updateLabel = new JLabel("Update Last Name by ID:");
        updateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(updateLabel, gbc);

        updateField1 = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(updateField1, gbc);

        updateField2 = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(updateField2, gbc);

        JButton updateButton = createStyledButton("Update", btnColor, textColor, hoverColor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(updateButton, gbc);

        updateButton.addActionListener(e -> updateData());

        return panel;
    }

    private JPanel createViewPanel(Color btnColor, Color textColor, Color hoverColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(245, 245, 245));

        JButton viewButton = createStyledButton("View All Records", btnColor, textColor, hoverColor);
        viewButton.setPreferredSize(new Dimension(200, 40));
        panel.add(viewButton, BorderLayout.NORTH);

        viewButton.addActionListener(e -> viewData());

        return panel;
    }

    private JButton createStyledButton(String text, Color defaultColor, Color textColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setBackground(defaultColor);
        button.setForeground(textColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0, 0), 1));
        button.setPreferredSize(new Dimension(150, 30));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });
        return button;
    }

    private void insertData() {
        String field1 = insertField1.getText();
        String field2 = insertField2.getText();

        try (SessionFactory sf = new Configuration().configure().addAnnotatedClass(Person.class).buildSessionFactory();
             Session s = sf.openSession()) {
            Transaction tx = s.beginTransaction();
            Person per = new Person();
            per.setFname(field1);
            per.setLname(field2);
            s.save(per);
            tx.commit();
            outputArea.append("Inserted: First name: " + field1 + ", Last name: " + field2 + "\n");
        } catch (Exception e) {
            outputArea.append("Error inserting data: " + e.getMessage() + "\n");
        }
    }

    private void deleteData() {
        String field = deleteField.getText();

        try (SessionFactory sf = new Configuration().configure().addAnnotatedClass(Person.class).buildSessionFactory();
             Session s = sf.openSession()) {
            Transaction tx = s.beginTransaction();
            Person per = s.get(Person.class, field);
            if (per != null) {
                s.delete(per);
                tx.commit();
                outputArea.append("Deleted: First name: " + field + "\n");
            } else {
                outputArea.append("No record found with First name: " + field + "\n");
            }
        } catch (Exception e) {
            outputArea.append("Error deleting data: " + e.getMessage() + "\n");
        }
    }

    private void updateData() {
        String field1 = updateField1.getText();
        String field2 = updateField2.getText();

        try (SessionFactory sf = new Configuration().configure().addAnnotatedClass(Person.class).buildSessionFactory();
             Session s = sf.openSession()) {
            Transaction tx = s.beginTransaction();
            Person per = s.get(Person.class, field1);
            if (per != null) {
                per.setLname(field2);
                s.update(per);
                tx.commit();
                outputArea.append("Updated: First name: " + field1 + " with new Last name: " + field2 + "\n");
            } else {
                outputArea.append("No record found with First name: " + field1 + "\n");
            }
        } catch (Exception e) {
            outputArea.append("Error updating data: " + e.getMessage() + "\n");
        }
    }

    private void viewData() {
        try (SessionFactory sf = new Configuration().configure().addAnnotatedClass(Person.class).buildSessionFactory();
             Session session = sf.openSession()) {
            List<Person> persons = session.createQuery("from Person", Person.class).getResultList();
            outputArea.setText("");
            for (Person person : persons) {
                outputArea.append("First name: " + person.getFname() + ", Last name: " + person.getLname() + "\n");
            }
        } catch (Exception e) {
            outputArea.append("Error retrieving data: " + e.getMessage() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
