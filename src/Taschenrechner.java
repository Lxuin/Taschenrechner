import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

public class Taschenrechner extends JFrame {
    private JTextField display;
    private double result;
    private String operator;
    private boolean isNewOperation;
    public Taschenrechner() {
        createUI();
    }

    private void createUI() {
        setTitle("Taschenrechner");
        display = new JTextField("0", 12); // Anzeige mit Startwert auf 0
        display.setEditable(false); // Anzeigefeld nicht bearbeitbar
        display.setHorizontalAlignment(JTextField.RIGHT); // Text rechtsbündig

        //4x4 Grid-Layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));
        // Beschriftungen der Buttons
        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "C", "0", "=", "+"
        };

        for (String buttonText : buttons) {
            JButton button = new JButton(buttonText);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    buttonPressed(e.getActionCommand());
                }
            });
            buttonPanel.add(button);
        }

        setLayout(new BorderLayout());
        add(display, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);

        resetCalculator();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(250, 250);
        setVisible(true);
    }

    // Reset vom Taschenrechner
    private void resetCalculator() {
        result = 0; // setzt das Ergebnis zurück
        operator = "="; // setzt den Operator auf "="
        isNewOperation = true; // nächste Eingabe startet eine neue Operation
        display.setText("0"); // Setzt die Anzeige zurück auf 0
    }

    private void buttonPressed(String button) {
        if ('0' <= button.charAt(0) && button.charAt(0) <= '9' || button.equals(".")) {

            if (isNewOperation) {
                display.setText(button);
            } else {
                display.setText(display.getText() + button);
            }
            isNewOperation = false;
        } else {

            if (isNewOperation) {
                if (button.equals("-")) {
                    // erlaubt negative zahlen
                    display.setText(button);
                    isNewOperation = false;
                } else {
                    operator = button;
                }
            } else {
                double x = Double.parseDouble(display.getText());
                calculate(x);
                operator = button;
                isNewOperation = true;
            }
        }
        if (button.equals("C")) {
            resetCalculator(); // reset wenn C gedrückt
        }
    }

    private void calculate(double n) {
        switch (operator) {
            case "+": result += n; break;
            case "-": result -= n; break;
            case "*": result *= n; break;
            case "/": result /= n; break;
            case "=": result = n; break;
        }
        display.setText("" + result); // zeigt das ergebnis
    }

    public static void main(String[] args) {
        new Taschenrechner();
    }
}
