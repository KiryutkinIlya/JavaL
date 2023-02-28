import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class Form extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTable table1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton ButtonAdd;
    private JButton ButtonDelete;
    private JButton ButtonCalc;
    private JTextField textField4;
    private double[] dataT = new double[4];
    private double[] dataCount = new double[4];
    int num =1;
    boolean flagSave=false;
    String[] columnNames = {
            "Верхняя граница интегрирования",
            "Нижняя граница интегрирования",
            "Шаг интегрирования",
            "Результат"
    };
    Object[][] data = new Object[8][4];
    public Form() {
        flagSave=false;
        //contentPane.add(table1);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        table1.setModel(new DefaultTableModel(data, columnNames));
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        ButtonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AddTable();
            }
        });
        ButtonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
        ButtonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Calc();
            }
        });
        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

    }
    public static double InFunction(double x) //Подынтегральная функция
    {
        return Math.sin(Math.pow(x,2));
    }
    private void delete()
    {
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
        flagSave=false;
    }
    private void Calc() {
        dataT[0] =Double.valueOf(textField1.getText());
        dataT[1] =Double.valueOf(textField2.getText());
        dataT[2] =Double.valueOf(textField3.getText());
        dataT[3] =Double.valueOf("0");
        dataT[3]= Trap(dataT[0],dataT[1],dataT[2]);
        for(int i=0;i<4;i++)
        {
            dataCount[i]=dataT[i];
        }
        flagSave=true;
        //createTable();
        textField4.setText(""+dataT[3]);

    }

    private void AddTable() {
       // dataT[0] =Double.valueOf(textField1.getText());
       // dataT[1] =Double.valueOf(textField3.getText());
       // dataT[2] =Double.valueOf(textField2.getText());
       // dataT[3] =Double.valueOf("0");
        //dataT[3]= Trap(dataT[0],dataT[1],dataT[2]);

       if(flagSave) {
           createTable();
           num++;
       }//textField4.setText(""+dataT[3]);
    }

    public void createTable(){
        int i=100;

        data[num-1][0]=(double)dataCount[0];//1
        data[num-1][1]=(double)dataCount[1];//2
        data[num-1][2]=""+dataCount[2];//3
        data[num-1][3]=(double)dataCount[3];//4
        table1.setModel(new DefaultTableModel(data, columnNames));
    }
    public double Trap(double a,double b, double h){
        double result=0;

        for(double i=a;i<=b-(h*2);i+=h)
        {
       result=(InFunction(i)+InFunction(i+h))*(b-i)/2;
        }

     //   double result=0;
       // int n = (int)((a-b)/h);
       // result += (InFunction(a)+InFunction(b))/2;
        //for(int i = 1; i < n; i++) {
          //  result += InFunction(b + h * i);
       // }
        return result;
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        Form dialog = new Form();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
