import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

import static javax.swing.JOptionPane.showMessageDialog;

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
    private ArrayList<DataNumber> dataNumbers=new ArrayList();

    DefaultTableModel modelData = (DefaultTableModel) table1.getModel();
    private JButton buttonUp;
    private JButton btnCleanTable;
    private JButton buttonFromCollection;
    private JCheckBox checkBoxTrap;
    private JCheckBox checkBoxSimpson;
    int num = 0;
    int realRow;
    int realColumn;
    boolean flagSave=false;
    boolean flagPoint=false;
    boolean flagSimpson=false;
    boolean flagTrap=false;
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
        /*
        checkBoxTrap.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    flagTrap=true;
                } else {
                flagTrap=false;
                }
            }
        });
        checkBoxSimpson.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == 1) {
                    flagSimpson=true;
                } else {
                    flagSimpson=false;
                }
            }
        });*/
        checkBoxTrap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              SelFlagTrap();
            }
        });
        checkBoxSimpson.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SelFlagSimpson();
            }
        });
        //добавление в таблицу
        ButtonAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(checkSelMetods()) {
                    flagPoint = false;
                    AddTable();
                };
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

                if(checkSelMetods()) {
                if(flagPoint) {
                    Calc();
                }else{
                    CalcToField();
                }};
                flagPoint=false;
                }
        });
        buttonFromCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
             for(int i=0;i<num;i++) {
                 modelData.addRow(dataNumbers.get(i).addMod());
             }
            }});
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
        btnCleanTable.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            modelData.setRowCount(0);
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
    private boolean checkSelMetods()
    {
        if((flagSimpson==true && flagTrap==true)||(flagSimpson==false && flagTrap==false))
        {
            showMessageDialog(null, "Выберите только один метод интегрирования", "Внимание",JOptionPane.INFORMATION_MESSAGE );
            return false;
        }else{
            return true;
        }

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
        if(flagSimpson==true){
            tableCursor[3]=Simpson(tableCursor[1],tableCursor[0],tableCursor[2]);

        }else{
            if(flagTrap==true)
            {
                tableCursor[3]=Trap(tableCursor[1],tableCursor[0],tableCursor[2]);
            }else{
                tableCursor[3]=0;
            }
        }
        modelData.setValueAt(tableCursor[3],realRow,3);//4

    }
    private void CalcToField() {
        if(flagSimpson==true){
            textField4.setText(""+Simpson(Double.valueOf(textField1.getText()),Double.valueOf(textField2.getText()),Double.valueOf(textField3.getText())));
        }else{
            if(flagTrap==true)
            {
                textField4.setText(""+Trap(Double.valueOf(textField1.getText()),Double.valueOf(textField2.getText()),Double.valueOf(textField3.getText())));
            }else{
                textField4.setText("");
            }
        }

    }
    private void AddTable() {
        DataNumber dataNumber=new DataNumber();
        dataNumber.setAllField(Double.valueOf(textField1.getText()),Double.valueOf(textField2.getText()),Double.valueOf(textField3.getText()),NumberMethod());
        dataNumbers.add(dataNumber);
        modelData.addRow(dataNumbers.get(num).addMod());
        num++;
        textField1.setText("");
        textField2.setText("");
        textField3.setText("");
        textField4.setText("");
    }
    private int NumberMethod()
    {
        if(flagTrap==true)
        {
            return 0;
        } else{ if (flagSimpson==true) {
            return 1;
        }else {
            return 2;
        }}
    }
   private void SelFlagTrap()
   {
       if(flagTrap ==false)
       {
           flagTrap=true;
       }else{
           flagTrap=false;
       }
   }
   private void SelFlagSimpson()
    {
        if(flagSimpson ==false)
        {
            flagSimpson=true;
        }else{
            flagSimpson=false;
        }
    }
    public double Trap(double a,double b, double h){
        double result=0;
        double num=0;
        for(double i=a;i<=b-(h*2);i+=h)
        {num=i+h;
            if(i>b)
            {
                num=b;
            }
            result+=(InFunction(i)+InFunction(num))*(b-i)/2;
        }
        //double result=0;
        // int n = (int)((a-b)/h);
        //result += (InFunction(a)+InFunction(b))/2;
        //for(int i = 1; i < n; i++) {
        //    result += InFunction(b + h * i);
        //}
        //}
        return result;
    }
    public double Simpson(double a,double b, double n){
        int i,z;
        double h,s;

        n=n+n;
        s = InFunction(a)*InFunction(b);
        h = (b-a)/n;
        z = 4;

        for(i = 1; i<n; i++){
            s = s + z * InFunction(a+i*h);
            z = 6 - z;
        }
        return (s * h)/3;
    }
    public static double InFunction(double x) //Подынтегральная функция
    {
        return Math.sin(Math.pow(x,2));
    }
    public void createTable(){
        modelData.addColumn("Верхняя граница");
        modelData.addColumn("Нижняя граница");
        modelData.addColumn("Шаг");
        modelData.addColumn("Результат");
        modelData.addColumn("Метод интегр");

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
