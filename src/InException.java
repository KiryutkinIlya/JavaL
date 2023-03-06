import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class InException extends Exception
{
    public InException() {
        showMessageDialog(null,"Егор, собака суталая","Чертлысый", JOptionPane.ERROR_MESSAGE);
    }
}
