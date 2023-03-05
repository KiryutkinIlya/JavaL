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
    private double[] dataT = new double[4];
    private double[] dataCount = new double[4];
    private double[] tableCursor=new double[4];

    private JButton buttonUp;
    int num =0;
    int realRow;
    int realColumn;
    boolean flagSave=false;
    boolean flagPoint=false;
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
                        System.out.println(tableCursor[0]);
                        System.out.println(tableCursor[1]);
                        System.out.println(tableCursor[2]);
                        System.out.println(tableCursor[3]);
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
        return 1/(Math.log(x));
    }
    private void delete()
    {   flagSave=false;
        Object[][] dt=new Object[8][4];
        for(int i=0;i<8;i++)
        {   dt[i][0]="";
            dt[i][1]="";
            dt[i][2]="";
            dt[i][3]="";
            dt[i][0]="";
            dt[i][1]=data[i][1];
            dt[i][2]=data[i][2];
            dt[i][3]=data[i][3];

        }
        int j=0;
        for(int i=0;i<8;i++)
        {
            if(realRow==i)
            {
                j++;
            };
                data[i][0]=dt[j][0];
                data[i][1]=dt[j][1];
                data[i][2]=dt[j][2];
                data[i][3]=dt[j][3];
                j++;
        }
        num--;
        table1.setModel(new DefaultTableModel(data, columnNames));
    }
    private void Calc() {
        flagSave=true;
        data[realRow][0]= tableCursor[0];//1
        data[realRow][1]=tableCursor[1];//2
        data[realRow][2]=tableCursor[2];//3
        data[realRow][3]=Trap(tableCursor[1],tableCursor[0],tableCursor[2]);//4
        table1.setModel(new DefaultTableModel(data, columnNames));

    }
    private void CalcToField() {
        textField4.setText(""+Trap(Double.valueOf(textField1.getText()),Double.valueOf(textField2.getText()),Double.valueOf(textField3.getText())));
    }
    private void AddTable() {
        dataCount[0] =Double.valueOf(textField2.getText());
        dataCount[1] =Double.valueOf(textField1.getText());
        dataCount[2] =Double.valueOf(textField3.getText());
        if(!String.valueOf(textField4.getText()).equals(""))
        {
            dataCount[3] =Double.valueOf(textField4.getText());
            data[num][0]=dataCount[0];//1
            data[num][1]=dataCount[1];//2
            data[num][2]=dataCount[2];//3
            data[num][3]=dataCount[3];//4
            table1.setModel(new DefaultTableModel(data, columnNames));
        }else{
            data[num][0]=dataCount[0];//1
            data[num][1]=dataCount[1];//2
            data[num][2]=dataCount[2];//3
            table1.setModel(new DefaultTableModel(data, columnNames));
        }

        //createTable();
        num++;
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }

    public void createTable(){
        data[num][0]=dataCount[0];//1
        data[num][1]=dataCount[1];//2
        data[num][2]=dataCount[2];//3
        data[num][3]=dataCount[3];//4
        table1.setModel(new DefaultTableModel(data, columnNames));
    }
    public double Trap(double a,double b, double h){
        double result=0;
        int n = (int)((a-b)/h);
        result += (InFunction(a)+InFunction(b))/2;
        for(int i = 1; i < n; i++) {
            result += InFunction(b + h * i);
        }
        return h*result;
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
