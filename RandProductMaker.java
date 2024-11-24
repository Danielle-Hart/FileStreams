import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.CREATE;

public class RandProductMaker extends JFrame {
    private JTextField NameField, DescriptionField, IDField, PriceField;
    private JTextArea OutputArea;

    private static final int NameLength = 35;
    private static final int DescriptionLength = 75;
    private static final int IDLength = 6;
    private static final int RecordSize = 1;

    public RandProductMaker() {
        setTitle("Random Product Maker");
        setLayout(new FlowLayout());
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        NameField = new JTextField(20);
        DescriptionField = new JTextField(20);
        IDField = new JTextField(20);
        PriceField = new JTextField(20);

        JButton AddButton = new JButton("Add Product");
        JButton QuitButton = new JButton("Quit");

        OutputArea = new JTextArea(10,40);
        OutputArea.setEditable(false);
        JScrollPane ScrollPane = new JScrollPane(OutputArea);

        add(new JLabel("Prodcut Name:"));
        add(NameField);
        add(new JLabel("Description:"));
        add(DescriptionField);
        add(new JLabel("ID:"));
        add(IDField);
        add(new JLabel("Price:"));
        add(PriceField);

        add(AddButton);
        add(QuitButton);
        add(ScrollPane);

        AddButton.addActionListener(e -> AddProduct());
        QuitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
    private void AddProduct() {
        String Name = NameField.getText();
        String Description = DescriptionField.getText();
        String ID = IDField.getText();
        double price;

        try{
            price = Double.parseDouble(PriceField.getText());
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid Price!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try(RandomAccessFile raf = new RandomAccessFile("products.dat", "rw")) {
            long fileLength = raf.length();
            raf.seek(raf.length());
            writeFixedLengthString(raf, Name, NameLength);
            writeFixedLengthString(raf, Description, DescriptionLength);
            writeFixedLengthString(raf, ID, IDLength);
            raf.writeDouble(price);

            OutputArea.setText("Record Count ");
            OutputArea.append(RecordSize + "\n");

            JOptionPane.showMessageDialog(RandProductMaker.this, "Product Added Successfully!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            ex.printStackTrace();
            OutputArea.setText("Error Writing Product" + ex.getMessage());
        }

        NameField.setText("");
        DescriptionField.setText("");
        IDField.setText("");
        PriceField.setText("");
    }
    private void writeFixedLengthString(RandomAccessFile raf, String str, int length) throws IOException {
        StringBuilder sb = new StringBuilder(str);
        sb.setLength(length);
        raf.writeChars(sb.toString());
    }
    public static void main(String[] args) {
        new RandProductMaker();
    }
}
