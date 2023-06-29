import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class GUIApplication extends JFrame implements ActionListener {
    private JButton createButton;
    private JButton readButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField nameTextField;
    private JTextField commentTextField;

    public GUIApplication() {
        // Configuración de la ventana principal
        setTitle("Aplicación de Interfaz Gráfica");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLayout(new FlowLayout());

        // Crear los botones
        createButton = new JButton("Create");
        readButton = new JButton("Read");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        // Crear los campos de texto
        nameTextField = new JTextField(20);
        commentTextField = new JTextField(20);

        // Asignar el ActionListener a cada botón
        createButton.addActionListener(this);
        readButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);

        // Agregar los componentes a la ventana
        add(new JLabel("Nombre: "));
        add(nameTextField);
        add(new JLabel("Contacto: "));
        add(commentTextField);
        add(createButton);
        add(readButton);
        add(updateButton);
        add(deleteButton);

        // Mostrar la ventana
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            // Lógica para el botón "Create"
            String newName = nameTextField.getText();
            String newComment = commentTextField.getText();

            try {
                long newNumber = Long.parseLong(newComment);
                String nameNumberString;
                String name;
                long number;
                int index;

                File file = new File("friendsContact.txt");

                if (!file.exists()) {
                    file.createNewFile();
                }

                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                boolean found = false;

                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();
                    String[] lineSplit = nameNumberString.split("!");
                    name = lineSplit[0];
                    number = Long.parseLong(lineSplit[1]);

                    if (name.equals(newName) || number == newNumber) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    nameNumberString = newName + "!" + String.valueOf(newNumber);
                    raf.writeBytes(nameNumberString);
                    raf.writeBytes(System.lineSeparator());
                    JOptionPane.showMessageDialog(this, "Friend added!");
                    raf.close();
                } else {
                    raf.close();
                    JOptionPane.showMessageDialog(this, "Input name or number already exists!");
                }
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (e.getSource() == readButton) {
            // Lógica para el botón "Read"
            try {
                File file = new File("friendsContact.txt");

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "No contacts found!");
                    return;
                }

                RandomAccessFile raf = new RandomAccessFile(file, "rw");

                String nameNumberString;
                String name;
                long number;
                int index;

                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();
                    String[] lineSplit = nameNumberString.split("!");
                    name = lineSplit[0];
                    number = Long.parseLong(lineSplit[1]);

                    JOptionPane.showMessageDialog(this, "Friend Name: " + name + "\n" + "Contact Number: " + number);
                }

                raf.close();
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (e.getSource() == updateButton) {
            // Lógica para el botón "Update"
            String newName = nameTextField.getText();
            String newComment = commentTextField.getText();

            try {
                long newNumber = Long.parseLong(newComment);
                String nameNumberString;
                String name;
                long number;
                int index;

                File file = new File("friendsContact.txt");

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "No contacts found!");
                    return;
                }

                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                boolean found = false;

                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();
                    String[] lineSplit = nameNumberString.split("!");
                    name = lineSplit[0];
                    number = Long.parseLong(lineSplit[1]);

                    if (name.equals(newName) || number == newNumber) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    File tempFile = new File("temp.txt");
                    RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw");

                    raf.seek(0);
                    while (raf.getFilePointer() < raf.length()) {
                        nameNumberString = raf.readLine();
                        index = nameNumberString.indexOf('!');
                        name = nameNumberString.substring(0, index);

                        if (name.equals(newName)) {
                            nameNumberString = name + "!" + String.valueOf(newNumber);
                        }

                        tempRaf.writeBytes(nameNumberString);
                        tempRaf.writeBytes(System.lineSeparator());
                    }

                    raf.seek(0);
                    tempRaf.seek(0);
                    while (tempRaf.getFilePointer() < tempRaf.length()) {
                        raf.writeBytes(tempRaf.readLine());
                        raf.writeBytes(System.lineSeparator());
                    }

                    raf.setLength(tempRaf.length());

                    tempRaf.close();
                    raf.close();

                    tempFile.delete();

                    JOptionPane.showMessageDialog(this, "Friend updated.");
                } else {
                    raf.close();
                    JOptionPane.showMessageDialog(this, "Input name does not exist.");
                }
            } catch (IOException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        } else if (e.getSource() == deleteButton) {
            // Lógica para el botón "Delete"
            String newName = nameTextField.getText();

            try {
                String nameNumberString;
                String name;
                long number;
                int index;

                File file = new File("friendsContact.txt");

                if (!file.exists()) {
                    JOptionPane.showMessageDialog(this, "No contacts found!");
                    return;
                }

                RandomAccessFile raf = new RandomAccessFile(file, "rw");
                boolean found = false;

                while (raf.getFilePointer() < raf.length()) {
                    nameNumberString = raf.readLine();
                    String[] lineSplit = nameNumberString.split("!");
                    name = lineSplit[0];

                    if (name.equals(newName)) {
                        found = true;
                        break;
                    }
                }

                if (found) {
                    File tempFile = new File("temp.txt");
                    RandomAccessFile tempRaf = new RandomAccessFile(tempFile, "rw");

                    raf.seek(0);
                    while (raf.getFilePointer() < raf.length()) {
                        nameNumberString = raf.readLine();
                        index = nameNumberString.indexOf('!');
                        name = nameNumberString.substring(0, index);

                        if (name.equals(newName)) {
                            continue;
                        }

                        tempRaf.writeBytes(nameNumberString);
                        tempRaf.writeBytes(System.lineSeparator());
                    }

                    raf.seek(0);
                    tempRaf.seek(0);
                    while (tempRaf.getFilePointer() < tempRaf.length()) {
                        raf.writeBytes(tempRaf.readLine());
                        raf.writeBytes(System.lineSeparator());
                    }

                    raf.setLength(tempRaf.length());

                    tempRaf.close();
                    raf.close();

                    tempFile.delete();

                    JOptionPane.showMessageDialog(this, "Friend deleted.");
                } else {
                    raf.close();
                    JOptionPane.showMessageDialog(this, "Input name does not exist.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GUIApplication());
    }
}