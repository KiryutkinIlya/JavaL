import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.io.*;
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
    private JMenuBar menuBar;
    private JMenu menu, submenu;
    private JMenuItem menuItem;
    private JRadioButtonMenuItem rbMenuItem;
    private JCheckBoxMenuItem cbMenuItem;
    private double[] tableCursor=new double[4];
    private ArrayList<DataNumber> dataNumbers=new ArrayList();
    private JFileChooser chooser= new JFileChooser();
    private File chosenFile;
    DefaultTableModel modelData = (DefaultTableModel) table1.getModel();
    private JButton buttonUp;
    private JButton btnCleanTable;
    private JButton buttonFromCollection;
    private JCheckBox checkBoxTrap;
    private JCheckBox checkBoxSimpson;
    private JButton buttonDownload;
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
        createMenuBar();
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
                try {
                if(checkSelMetods()) {
                    flagPoint = false;
                    AddTable();
                };
                }catch (InExceptions ex)
                {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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
                try {
                    if (checkSelMetods()) {
                        if (flagPoint) {
                            Calc();
                        } else {
                            CalcToField();
                        }
                    }
                    ;
                }catch (InExceptions ex)
                {
                    throw new RuntimeException(ex);
                }
                flagPoint=false;
                }
        });
        buttonFromCollection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i< dataNumbers.size();i++) {
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
    private void Calc() throws InExceptions {
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
    private void CalcToField()throws InExceptions  {
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
    private void AddTable() throws InExceptions, InterruptedException {
        DataNumber dataNumber=new DataNumber();
        if(textField1.getText().equals(""))
        {
            throw new InExceptions();
        }
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

    private void createMenuBar() {

        var menuBar = new JMenuBar();

        var iconOpen = new ImageIcon("src/resources/open.png");
        var iconSave = new ImageIcon("src/resources/save.png");

        var exitIcon = new ImageIcon("src/resources/exit.png");

        var fileMenu = new JMenu("Меню");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        var eMenuItem = new JMenuItem("Выход", exitIcon);
        var openMenuItem = new JMenuItem("Открыть", iconOpen);
        var saveMenuItem = new JMenuItem("Сохранить", iconSave);
        var saveMenuItemInF = new JMenuItem("Сохранить в формате", iconSave);
        var openBinMenuItem = new JMenuItem("Открыть бинарный файл", iconOpen);
        var saveBinMenuItem = new JMenuItem("Сохранить бинарный файл", iconSave);
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((event) -> System.exit(0));

        fileMenu.add(eMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveMenuItemInF);
        fileMenu.add(openBinMenuItem);
        fileMenu.add(saveBinMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        saveBinMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Data List Object Type", "dlot");
                chooser.setFileFilter(filter);
                int choice = chooser.showSaveDialog(chooser);
                //if (choice != JFileChooser.APPROVE_OPTION) return;
                chosenFile = chooser.getSelectedFile();
                saveBinFile();

            }
        });
        openBinMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Data List Object Type", "dlot");
                chooser.setFileFilter(filter);
                int choice = chooser.showOpenDialog(chooser);
                if (choice != JFileChooser.APPROVE_OPTION) return;
                chosenFile = chooser.getSelectedFile();
                loadbinFile();
            }
        });
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = chooser.showSaveDialog(chooser);
                //if (choice != JFileChooser.APPROVE_OPTION) return;
                chosenFile = chooser.getSelectedFile();
                try {
                    saveFileToApi();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        saveMenuItemInF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int choice = chooser.showSaveDialog(chooser);
                //if (choice != JFileChooser.APPROVE_OPTION) return;
                chosenFile = chooser.getSelectedFile();
                try {
                    saveFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileNameExtensionFilter filter = new FileNameExtensionFilter("text", "txt");
                chooser.setFileFilter(filter);
                int choice = chooser.showOpenDialog(chooser);
                if (choice != JFileChooser.APPROVE_OPTION) return;
                chosenFile = chooser.getSelectedFile();
                try {
                    loadFile();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (NumberFormatException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    } public void saveBinFile(){

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(chosenFile)))
        {
            oos.writeObject(dataNumbers);
            System.out.println("File has been written");
        }
        catch(Exception ex){

            System.out.println(ex.getMessage());
        }


    }
    public void loadbinFile() {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(chosenFile)))
        {
           dataNumbers=((ArrayList<DataNumber>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        AddInCollection();
    }
    public void saveFile() throws IOException {
        int countData = dataNumbers.size();

        FileWriter myfile = new FileWriter(chosenFile);

        for(int i=0;i<countData;i++) {
            myfile.write(dataNumbers.get(i).toString() + "\n");
        }
        myfile.flush();
        myfile.close();
    }
    public void saveFileToApi() throws IOException {
        int countData = dataNumbers.size();

        FileWriter myfile = new FileWriter(chosenFile);

        for(int i=0;i<countData;i++) {
            myfile.write(dataNumbers.get(i).toStringApi() + "\n");
        }
        myfile.flush();
        myfile.close();
    }
    public void loadFile() throws IOException, NumberFormatException {
        FileReader myfile = new FileReader(chosenFile);
        BufferedReader reader = new BufferedReader(myfile);
        int i=0;
        dataNumbers.add(i, new DataNumber());
        String line = reader.readLine();

        String[] dblArray = line.split(",");

        dataNumbers.get(0).setMin(Double.valueOf(dblArray[0]));
        dataNumbers.get(0).setMax(Double.valueOf(dblArray[1]));
        dataNumbers.get(0).setStep(Double.valueOf(dblArray[2]));
        dataNumbers.get(0).setResult(Double.valueOf(dblArray[3]));
        dataNumbers.get(0).setMethod(dblArray[4]);

        while (line != null) {
            // считываем остальные строки в цикле
            i++;
            line = reader.readLine();
            if(line==null)break;
            dblArray = line.split(",");
            dataNumbers.add(i, new DataNumber());
            dataNumbers.get(i).setMin(Double.valueOf(dblArray[0]));
            dataNumbers.get(i).setMax(Double.valueOf(dblArray[1]));
            dataNumbers.get(i).setStep(Double.valueOf(dblArray[2]));
            dataNumbers.get(i).setResult(Double.valueOf(dblArray[3]));
            dataNumbers.get(i).setMethod(dblArray[4]);

        }
       // for(int k=0;i< dataNumbers.size();k++) {
          //  modelData.addRow(dataNumbers.get(k).addMod());
       // }
        AddInCollection();

    }
   private void AddInCollection()
   {
       for(int k=0;k< dataNumbers.size();k++) {
           modelData.addRow(dataNumbers.get(k).addMod());
       }
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
