import javax.swing.*;
import java.awt.*;

class Node {
    int data;
    Node next;
    Node(int data) {
        this.data = data;
        this.next = null;
    }
}

class LinkedList {
    Node head;

    public void add(int data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            return;
        }
        Node curr = head;
        while (curr.next != null) {
            curr = curr.next;
        }
        curr.next = newNode;
    }

    public void delete(int value) {
        if (head == null) return;
        if (head.data == value) {
            head = head.next;
            return;
        }
        Node curr = head;
        while (curr.next != null && curr.next.data != value) {
            curr = curr.next;
        }
        if (curr.next != null) {
            curr.next = curr.next.next;
        }
    }

    public void insertAt(int index, int data) {
        Node newNode = new Node(data);
        if (index == 0) {
            newNode.next = head;
            head = newNode;
            return;
        }
        Node curr = head;
        for (int i = 0; i < index - 1 && curr != null; i++) {
            curr = curr.next;
        }
        if (curr != null) {
            newNode.next = curr.next;
            curr.next = newNode;
        }
    }

    public String display() {
        StringBuilder sb = new StringBuilder();
        Node curr = head;
        while (curr != null) {
            sb.append(curr.data).append(" → ");
            curr = curr.next;
        }
        return sb.append("null").toString();
    }
}

public class Main extends JFrame {
    LinkedList list = new LinkedList();
    JTextField addField, deleteField, insertIndexField, insertDataField;
    JTextArea displayArea;

    public Main() {
        setTitle("Linked List Manager");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(245, 245, 245));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel title = new JLabel("🔗 Linked List Manager", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        add(title, gbc);

        addField = new JTextField(5);
        addField.setFont(font);
        JButton addButton = new JButton("Add Node");
        styleButton(addButton);

        gbc.gridy = 1; gbc.gridwidth = 1;
        add(new JLabel("Add:"), gbc);
        gbc.gridx = 1;
        add(addField, gbc);
        gbc.gridx = 2;
        add(addButton, gbc);

        deleteField = new JTextField(5);
        deleteField.setFont(font);
        JButton deleteButton = new JButton("Delete Value");
        styleButton(deleteButton);

        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Delete:"), gbc);
        gbc.gridx = 1;
        add(deleteField, gbc);
        gbc.gridx = 2;
        add(deleteButton, gbc);

        insertIndexField = new JTextField(3);
        insertDataField = new JTextField(5);
        insertIndexField.setFont(font);
        insertDataField.setFont(font);
        JButton insertButton = new JButton("Insert at Index");
        styleButton(insertButton);

        gbc.gridx = 0; gbc.gridy = 3;
        add(new JLabel("Insert at:"), gbc);
        gbc.gridx = 1;
        add(insertIndexField, gbc);
        gbc.gridx = 2;
        add(new JLabel("Value:"), gbc);
        gbc.gridx = 3;
        add(insertDataField, gbc);
        gbc.gridx = 2; gbc.gridy = 4;
        add(insertButton, gbc);

        JButton displayButton = new JButton("Show List");
        styleButton(displayButton);
        gbc.gridx = 1; gbc.gridy = 4;
        add(displayButton, gbc);

        displayArea = new JTextArea(10, 45);
        displayArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        displayArea.setEditable(false);
        displayArea.setBackground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(displayArea);
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 4;
        add(scroll, gbc);

        addButton.addActionListener(e -> {
            try {
                int val = Integer.parseInt(addField.getText());
                list.add(val);
                addField.setText("");
                updateDisplay();
            } catch (NumberFormatException ex) {
                showError("Invalid number for adding.");
            }
        });

        deleteButton.addActionListener(e -> {
            try {
                int val = Integer.parseInt(deleteField.getText());
                list.delete(val);
                deleteField.setText("");
                updateDisplay();
            } catch (NumberFormatException ex) {
                showError("Invalid number for deletion.");
            }
        });

        insertButton.addActionListener(e -> {
            try {
                int index = Integer.parseInt(insertIndexField.getText());
                int data = Integer.parseInt(insertDataField.getText());
                list.insertAt(index, data);
                insertIndexField.setText("");
                insertDataField.setText("");
                updateDisplay();
            } catch (NumberFormatException ex) {
                showError("Invalid input for insert.");
            }
        });

        displayButton.addActionListener(e -> updateDisplay());
    }

    void styleButton(JButton btn) {
        btn.setBackground(new Color(100, 149, 237));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
    }

    void updateDisplay() {
        displayArea.setText("Linked List:\n" + list.display());
    }

    void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
