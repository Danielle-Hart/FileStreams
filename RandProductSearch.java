import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandProductSearch extends JFrame {
    private JTextField SearchField;
    private JTextArea OutputArea;

    private static final int NameLength = 35;
    private static final int DescriptionLength = 75;
    private static final int IDLength = 6;
    private static final int RecordSize = NameLength + DescriptionLength + IDLength + 8;

    public RandProductSearch() {
        setTitle("Random Product Search");
        setLayout(new FlowLayout());
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SearchField = new JTextField(20);
        JButton SearchButton = new JButton("Search");
        JButton QuitButton = new JButton("Quit");

        OutputArea = new JTextArea(10,40);
        OutputArea.setEditable(false);
        JScrollPane ScrollPane = new JScrollPane(OutputArea);

        add(new JLabel("Enter Product ID"));
        add(SearchField);
        add(SearchButton);
        add(QuitButton);
        add(ScrollPane);

        SearchButton.addActionListener(e -> SearchProduct());
        QuitButton.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
    private void SearchProduct() {
        String SearchID = SearchField.getText();

        try(RandomAccessFile raf = new RandomAccessFile("products.dat", "rw")){
            long NumberofRecords = raf.length() / RecordSize;

            for (int i = 0; i < NumberofRecords; i++) {
                raf.seek(i * RecordSize);
                String Name = readFixedLengthString(raf, NameLength);
                String Description = readFixedLengthString(raf, DescriptionLength);
                String ID = readFixedLengthString(raf, IDLength);
                double price = raf.readDouble();

                if (ID.equals(SearchID)) {
                    OutputArea.append("Found Product: \n");
                    OutputArea.append("ID: " + ID + ", Name: " + Name + ", Description" + Description + ", Price: " + price + "\n");
                    return;
                }
            }
            OutputArea.append("Product ID" + SearchID + "not found.\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String readFixedLengthString(RandomAccessFile raf, int length) throws IOException {
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = raf.readChar();
        }
        return new String(chars).trim();
    }
    public static void main(String[] args) {
        new RandProductSearch();
    }
}
