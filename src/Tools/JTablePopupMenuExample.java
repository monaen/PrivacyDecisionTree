/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
 
/**
 * A Swing program that demonstrates how to create a popup menu
 * for a JTable component.
 * @author www.codejava.net
 *
 */

/**
 *
 * @author winfred
 */
public class JTablePopupMenuExample extends JFrame implements ActionListener {
 
    private JTable table;
    private DefaultTableModel tableModel;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemAdd;
    private JMenuItem menuItemRemove;
    private JMenuItem menuItemRemoveAll;
     
    public JTablePopupMenuExample() {
        super("JTable Popup Menu Example");
         
        // sample table data
        String[] columnNames = new String[] {"Title", "Author", "Publisher", "Published Date", "Pages", "Rating"};
        String[][] rowData = new String[][] {
            {"Effective Java", "Joshua Bloch", "Addision-Wesley", "May 08th 2008", "346", "5"},
            {"Thinking in Java", "Bruce Eckel", "Prentice Hall", "Feb 26th 2006", "1150", "4"},
            {"Head First Java", "Kathy Sierra & Bert Bates", "O'Reilly Media", "Feb 09th 2005", "688", "4.5"}, 
        };
         
         
        // constructs the table with sample data
        tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);
         
//        // constructs the popup menu
//        popupMenu = new JPopupMenu();
//        menuItemAdd = new JMenuItem("Add New Row");
//        menuItemRemove = new JMenuItem("Remove Current Row");
//        menuItemRemoveAll = new JMenuItem("Remove All Rows");
//         
//        menuItemAdd.addActionListener(this);
//        menuItemRemove.addActionListener(this);
//        menuItemRemoveAll.addActionListener(this);
//         
//        popupMenu.add(menuItemAdd);
//        popupMenu.add(menuItemRemove);
//        popupMenu.add(menuItemRemoveAll);
//         
//        // sets the popup menu for the table
//        table.setComponentPopupMenu(popupMenu);
//         
//        table.addMouseListener(new TableMouseListener(table));
         
        // adds the table to the frame
        add(new JScrollPane(table));
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 150);
        setLocationRelativeTo(null);
    }
    
    public JTablePopupMenuExample(String title) {
        super(title);
        
        String[] columnNames = new String[] {" ", " ", " ", " ", " ", " "};
        String[][] rowData = new String[][] {
            {" ", " ", " ", " ", " ", " "},{" ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " "},{" ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " "}
        };
        tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);
        add(new JScrollPane(table));
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 150);
        setLocationRelativeTo(null);
    }
    
    
    public JTablePopupMenuExample(String title, String[] columnNames, String[][] rowData) {
        super(title);
        tableModel = new DefaultTableModel(rowData, columnNames);
        table = new JTable(tableModel);
        add(new JScrollPane(table));
         
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 150);
        setLocationRelativeTo(null);
    }
    
    
    
     
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JTablePopupMenuExample().setVisible(true);
            }
        });
    }
 
    @Override
    public void actionPerformed(ActionEvent event) {
        JMenuItem menu = (JMenuItem) event.getSource();
//        if (menu == menuItemAdd) {
//            addNewRow();
//        } else if (menu == menuItemRemove) {
//            removeCurrentRow();
//        } else if (menu == menuItemRemoveAll) {
//            removeAllRows();
//        }
    }
     
//    private void addNewRow() {
//        tableModel.addRow(new String[0]);
//    }
//     
//    private void removeCurrentRow() {
//        int selectedRow = table.getSelectedRow();
//        tableModel.removeRow(selectedRow);
//    }
//     
//    private void removeAllRows() {
//        int rowCount = tableModel.getRowCount();
//        for (int i = 0; i < rowCount; i++) {
//            tableModel.removeRow(0);
//        }
//    }
}