import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class InventoryManagementSwing extends JFrame {

    JTextField idField, nameField, qtyField, priceField;
    JTable table;
    DefaultTableModel model;

    Object[] lastDeletedRow = null;

    public InventoryManagementSwing(){

        setTitle("Inventory Management System");
        setSize(900,550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JLabel title = new JLabel("Inventory Management System", JLabel.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,26));
        title.setForeground(new Color(33,150,243));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(
                new String[]{"ID","Name","Quantity","Price"},0);

        table = new JTable(model);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.BOLD,14));

        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,15));
        table.getTableHeader().setBackground(new Color(33,150,243));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane,BorderLayout.CENTER);

        JPanel formPanel = new JPanel(new GridLayout(4,2,10,10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));

        idField = new JTextField();
        nameField = new JTextField();
        qtyField = new JTextField();
        priceField = new JTextField();

        formPanel.add(new JLabel("Product ID:"));
        formPanel.add(idField);

        formPanel.add(new JLabel("Product Name:"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(qtyField);

        formPanel.add(new JLabel("Price:"));
        formPanel.add(priceField);

        JPanel buttonPanel = new JPanel();

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton undoBtn = new JButton("Undo Delete");
        JButton clearBtn = new JButton("Clear All");

        styleButton(addBtn,new Color(46,204,113));
        styleButton(updateBtn,new Color(52,152,219));
        styleButton(deleteBtn,new Color(231,76,60));
        styleButton(undoBtn,new Color(243,156,18));
        styleButton(clearBtn,new Color(155,89,182));

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        buttonPanel.add(undoBtn);
        buttonPanel.add(clearBtn);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(formPanel,BorderLayout.CENTER);
        southPanel.add(buttonPanel,BorderLayout.SOUTH);

        add(southPanel,BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addProduct());
        updateBtn.addActionListener(e -> updateProduct());
        deleteBtn.addActionListener(e -> deleteProduct());
        undoBtn.addActionListener(e -> undoDelete());
        clearBtn.addActionListener(e -> clearAllData());

        setVisible(true);
    }

    void styleButton(JButton btn, Color color){

        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(true);
        btn.setBorderPainted(false);

        btn.setFont(new Font("Segoe UI",Font.BOLD,14));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(120,35));
    }

    void addProduct(){

        try{

            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            int qty = Integer.parseInt(qtyField.getText());
            double price = Double.parseDouble(priceField.getText());

            // Maximum value validation
            if(id > 9999){
                JOptionPane.showMessageDialog(this,"Product ID cannot exceed 9999");
                return;
            }

            if(name.length() > 30){
                JOptionPane.showMessageDialog(this,"Product Name must be under 30 characters");
                return;
            }

            if(qty > 1000){
                JOptionPane.showMessageDialog(this,"Quantity cannot exceed 1000");
                return;
            }

            if(price > 100000){
                JOptionPane.showMessageDialog(this,"Price cannot exceed 100000");
                return;
            }

            model.addRow(new Object[]{id,name,qty,price});

            clearFields();

        }catch(Exception ex){

            JOptionPane.showMessageDialog(this,"Enter valid data");
        }
    }

    void updateProduct(){

        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a row to update");
            return;
        }

        try{

            if(!idField.getText().isEmpty()){
                int id = Integer.parseInt(idField.getText());
                if(id > 9999){
                    JOptionPane.showMessageDialog(this,"Product ID cannot exceed 9999");
                    return;
                }
                model.setValueAt(id,row,0);
            }

            if(!nameField.getText().isEmpty()){
                if(nameField.getText().length() > 30){
                    JOptionPane.showMessageDialog(this,"Name must be under 30 characters");
                    return;
                }
                model.setValueAt(nameField.getText(),row,1);
            }

            if(!qtyField.getText().isEmpty()){
                int qty = Integer.parseInt(qtyField.getText());
                if(qty > 1000){
                    JOptionPane.showMessageDialog(this,"Quantity cannot exceed 1000");
                    return;
                }
                model.setValueAt(qty,row,2);
            }

            if(!priceField.getText().isEmpty()){
                double price = Double.parseDouble(priceField.getText());
                if(price > 100000){
                    JOptionPane.showMessageDialog(this,"Price cannot exceed 100000");
                    return;
                }
                model.setValueAt(price,row,3);
            }

        }catch(Exception ex){
            JOptionPane.showMessageDialog(this,"Invalid input");
        }

        clearFields();
    }

    void deleteProduct(){

        int row = table.getSelectedRow();

        if(row == -1){
            JOptionPane.showMessageDialog(this,"Select a row to delete");
            return;
        }

        lastDeletedRow = new Object[]{
                model.getValueAt(row,0),
                model.getValueAt(row,1),
                model.getValueAt(row,2),
                model.getValueAt(row,3)
        };

        model.removeRow(row);
    }

    void undoDelete(){

        if(lastDeletedRow != null){

            model.addRow(lastDeletedRow);
            lastDeletedRow = null;

        }else{

            JOptionPane.showMessageDialog(this,"Nothing to undo");
        }
    }

    void clearAllData(){

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Backup File");

        int userSelection = fileChooser.showSaveDialog(this);

        if(userSelection == JFileChooser.APPROVE_OPTION){

            File fileToSave = fileChooser.getSelectedFile();

            try{

                FileWriter writer = new FileWriter(fileToSave);

                for(int i=0;i<model.getRowCount();i++){

                    writer.write(
                            model.getValueAt(i,0)+" , "+
                            model.getValueAt(i,1)+" , "+
                            model.getValueAt(i,2)+" , "+
                            model.getValueAt(i,3)+"\n"
                    );
                }

                writer.close();

                model.setRowCount(0);

                JOptionPane.showMessageDialog(this,
                        "Backup saved successfully!");

            }catch(Exception e){

                JOptionPane.showMessageDialog(this,
                        "Error saving file");
            }
        }
    }

    void clearFields(){

        idField.setText("");
        nameField.setText("");
        qtyField.setText("");
        priceField.setText("");
    }

    public static void main(String[] args){

        new InventoryManagementSwing();
    }
}