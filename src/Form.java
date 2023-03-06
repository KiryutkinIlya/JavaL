import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private double[] tableCursor=new double[4];
    private DataNumber dataNumber=new DataNumber();
    private ArrayList<DataNumber> dataNumbers=new ArrayList();

    DefaultTableModel modelData = (DefaultTableModel) table1.getModel();
    private JButton buttonUp;
    int num =0;
    int realRow;
    int realColumn;
    boolean flagSave=false;
    boolean flagPoint=false;

    public Form() {
        flagSave=false;
        //contentPane.add(table1);
        setContentPane(contentPane);
        createTable();
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
       buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        //добавление в таблицу
        ButtonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                flagPoint=false;
                AddTable();

            }
        });
        //удаление
        ButtonDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(flagPoint){
                    delete();
                }
                flagPoint=false;

            }
        });
        //вычисление
        ButtonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(flagPoint) {
                    Calc();
                }else{
                    CalcToField();
                }
                flagPoint=false;
                }
        });
        //вернуть
        buttonUp.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(flagPoint) {
                    textField1.setText("" + tableCursor[1]);
                    textField2.setText("" + tableCursor[0]);
                    textField3.setText("" + tableCursor[2]);
                    //textField4.setText("" + tableCursor[3]);
                }
                flagPoint=false;
            }
        });
        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {//одинарный шелчок
                    int row = table1.rowAtPoint(e.getPoint());
                    int column = table1.columnAtPoint(e.getPoint());
                    //путь попроще без selectionMode
                    if (row > -1) {
                        flagPoint=true;
                         realRow = table1.convertRowIndexToModel(row);
                         realColumn = table1.convertColumnIndexToModel(column);
                        System.out.println("[" + realRow + "],[" + realColumn + "]");
                        tableCursor[0]=(double)table1.getValueAt(realRow, 0);
                        tableCursor[1]=(double)table1.getValueAt(realRow, 1);
                        tableCursor[2]=(double)table1.getValueAt(realRow, 2);
                        //tableCursor[3]=(double)table1.getValueAt(realRow, 3);
                        //System.out.println(tableCursor[0]);
                        //System.out.println(tableCursor[1]);
                        //System.out.println(tableCursor[2]);
                        // System.out.println(tableCursor[3]);
                        //номер строки из модели данных
                        //здесь должна быть выборка объекта из модели по номеру строки и его отображение
                    }
                }
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
        modelData.removeRow(realRow);
        dataNumbers.remove(realRow);
        num--;
    }
    private void Calc() {
        flagSave=true;
        tableCursor[0]=(double)modelData.getValueAt(realRow,0);
        tableCursor[1]=(double)modelData.getValueAt(realRow,1);
        tableCursor[2]=(double)modelData.getValueAt(realRow,2);
        tableCursor[3]=Trap(tableCursor[1],tableCursor[0],tableCursor[2]);
        modelData.setValueAt(tableCursor[3],realRow,3);//4

    }
    private void CalcToField() {
        textField4.setText(""+Trap(Double.valueOf(textField1.getText()),Double.valueOf(textField2.getText()),Double.valueOf(textField3.getText())));
    }
    private void AddTable() {
        dataNumber.setMin(Double.valueOf(textField2.getText()));
        dataNumber.setMax(Double.valueOf(textField1.getText()));
        dataNumber.setStep(Double.valueOf(textField3.getText()));
        dataNumber.setResult(0);
        if(!String.valueOf(textField4.getText()).equals(""))
        {
            dataNumber.setResult(Double.valueOf(textField4.getText()));
            dataNumbers.add(dataNumber);
            modelData.addRow(dataNumbers.get(num).addMod());

        }else{
            dataNumbers.add(dataNumber);
            modelData.addRow(dataNumbers.get(num).addMod());
        }
        num++;
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }

    public void createTable(){
        modelData.addColumn("Верхняя граница");
        modelData.addColumn("Нижняя граница");
        modelData.addColumn("Шаг");
        modelData.addColumn("Результат");

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
