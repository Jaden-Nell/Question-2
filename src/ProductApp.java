import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import java.io.FileWriter;
import java.io.IOException;

// This is the window for the mattress sales program.
public class ProductApp extends JFrame {
    
    JTextField nameField;
    JTextField priceField;
    JComboBox<String> productBox;

    // Builds the window when the program starts.
    public ProductApp() {
        
        setTitle("Product App");
        setSize(550, 330);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        
        nameField = new JTextField(20);
        priceField = new JTextField(20);
        
        String[] products = {"Cozy Nights Monarch", "Restonic Barbados", "Sealy Phuket"};
        productBox = new JComboBox<String>(products);

        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        panel.add(new JLabel("Customer Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Select Product:"));
        panel.add(productBox);
        panel.add(new JLabel("Enter Price:"));
        panel.add(priceField);
        panel.add(new JLabel(""));

        
        JButton processButton = new JButton("PROCESS");
        panel.add(processButton);
        
        processButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSale();
            }
        });
        add(panel);

        
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exitItem);
        // Exit closes the program.
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Create the Tools menu with Process and Clear.
        JMenu toolsMenu = new JMenu("Tools");
        JMenuItem processItem = new JMenuItem("Process");
        JMenuItem clearItem = new JMenuItem("Clear");
        toolsMenu.add(processItem);
        toolsMenu.add(clearItem);
        
        processItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processSale();
            }
        });
        
        clearItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        menuBar.add(fileMenu);
        menuBar.add(toolsMenu);
        setJMenuBar(menuBar);
    }

    
    public void processSale() {
        
        String customer = nameField.getText().trim();
        String product = (String) productBox.getSelectedItem();
        String priceText = priceField.getText().trim();

        
        if (customer.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter a customer name.");
            return;
        }
        if (priceText.equals("")) {
            JOptionPane.showMessageDialog(this, "Please enter a product price.");
            return;
        }

        
        double price;
        try {
            price = Double.parseDouble(priceText);
        } catch (NumberFormatException e) {
            
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the price.");
            return;
        }
        
        if (Double.isNaN(price) || Double.isInfinite(price) || price <= 0) {
            JOptionPane.showMessageDialog(this, "Price must be greater than zero.");
            return;
        }

        
        ProductCalculations calc = new ProductCalculations(price);
        
        double vat = calc.GetVat();
        double commission = calc.GetCommission();
        double total = calc.GetTotal();

        
        String report = "Customer Name: " + customer + "\n"
                + "Product: " + product + "\n"
                + "VAT: R " + String.format("%.2f", vat) + "\n"
                + "COMMISSION: R " + String.format("%.2f", commission) + "\n"
                + "TOTAL: R " + String.format("%.2f", total);

         try {
            
            FileWriter writer = new FileWriter("data.txt", true);
            writer.write("DATA LOG\n");
            writer.write("***************************************\n");
            writer.write(report + "\n");
            writer.write("***************************************\n\n");
        
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Could not save data.txt.");
            return;
        }

       
        JOptionPane.showMessageDialog(this, report, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    
    public void clearForm() {
        
        nameField.setText("");
        priceField.setText("");
        productBox.setSelectedIndex(0);
    }

    // Starts the program.
    public static void main(String[] args) {
        
        ProductApp app = new ProductApp();
        app.setVisible(true);
    }
}
