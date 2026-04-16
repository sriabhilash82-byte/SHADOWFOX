import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentManagementSystem extends JFrame {

    private JTextField idField, nameField, deptField, marksField, searchField;
    private DefaultTableModel model;
    private JTable table;

    public StudentManagementSystem() {

        setTitle("Student Information System");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // ---------------- TOP PANEL (FORM) ----------------
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        formPanel.add(new JLabel("Student ID:"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Department:"));
        deptField = new JTextField();
        formPanel.add(deptField);

        formPanel.add(new JLabel("Marks:"));
        marksField = new JTextField();
        formPanel.add(marksField);

        formPanel.add(new JLabel("Search by ID:"));
        searchField = new JTextField();
        formPanel.add(searchField);

        panel.add(formPanel, BorderLayout.NORTH);

        // ---------------- CENTER PANEL (TABLE) ----------------
        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Department", "Marks"});

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane, BorderLayout.CENTER);

        // ---------------- BUTTON PANEL ----------------
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Operations"));

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton clearBtn = new JButton("Clear");
        JButton searchBtn = new JButton("Search");
        JButton exitBtn = new JButton("Exit");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(searchBtn);
        buttonPanel.add(exitBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);

        // ---------------- BUTTON ACTIONS ----------------

        // ADD STUDENT
        addBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            String marks = marksField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || dept.isEmpty() || marks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Student ID must contain only numbers!");
                return;
            }

            if (!marks.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Marks must contain only numbers!");
                return;
            }

            if (isIdExists(id)) {
                JOptionPane.showMessageDialog(this, "Student ID already exists!");
                return;
            }

            model.addRow(new Object[]{id, name, dept, marks});
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
        });

        // UPDATE STUDENT
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to update!");
                return;
            }

            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String dept = deptField.getText().trim();
            String marks = marksField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || dept.isEmpty() || marks.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            if (!id.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Student ID must contain only numbers!");
                return;
            }

            if (!marks.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Marks must contain only numbers!");
                return;
            }

            model.setValueAt(id, selectedRow, 0);
            model.setValueAt(name, selectedRow, 1);
            model.setValueAt(dept, selectedRow, 2);
            model.setValueAt(marks, selectedRow, 3);

            JOptionPane.showMessageDialog(this, "Student updated successfully!");
            clearFields();
        });

        // DELETE STUDENT
        deleteBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete this student?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                model.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Student deleted successfully!");
                clearFields();
            }
        });

        // CLEAR
        clearBtn.addActionListener(e -> clearFields());

        // SEARCH STUDENT BY ID
        searchBtn.addActionListener(e -> {
            String searchId = searchField.getText().trim();

            if (searchId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Student ID to search!");
                return;
            }

            boolean found = false;

            for (int i = 0; i < model.getRowCount(); i++) {
                String tableId = model.getValueAt(i, 0).toString();

                if (tableId.equals(searchId)) {
                    table.setRowSelectionInterval(i, i);

                    idField.setText(model.getValueAt(i, 0).toString());
                    nameField.setText(model.getValueAt(i, 1).toString());
                    deptField.setText(model.getValueAt(i, 2).toString());
                    marksField.setText(model.getValueAt(i, 3).toString());

                    found = true;
                    JOptionPane.showMessageDialog(this, "Student Found!");
                    break;
                }
            }

            if (!found) {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        });

        // CLICK ROW TO AUTO FILL
        table.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow >= 0) {
                idField.setText(model.getValueAt(selectedRow, 0).toString());
                nameField.setText(model.getValueAt(selectedRow, 1).toString());
                deptField.setText(model.getValueAt(selectedRow, 2).toString());
                marksField.setText(model.getValueAt(selectedRow, 3).toString());
            }
        });

        // EXIT
        exitBtn.addActionListener(e -> System.exit(0));
    }

    // CHECK IF ID EXISTS
    private boolean isIdExists(String id) {
        for (int i = 0; i < model.getRowCount(); i++) {
            String existingId = model.getValueAt(i, 0).toString();
            if (existingId.equals(id)) {
                return true;
            }
        }
        return false;
    }

    // CLEAR INPUT FIELDS
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        marksField.setText("");
        searchField.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentManagementSystem().setVisible(true);
        });
    }
}