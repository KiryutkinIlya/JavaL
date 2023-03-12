import javax.swing.*;
import static javax.swing.JOptionPane.showMessageDialog;

public class InExceptions extends Exception
{
    public InExceptions() {
        showMessageDialog(null,"Егор, собака суталая","Чертлысый", JOptionPane.ERROR_MESSAGE);
    }
}
