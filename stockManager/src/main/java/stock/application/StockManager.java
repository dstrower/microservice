package stock.application;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane; 
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import stock.listener.ButtonListener; 
public class StockManager {
	// frame 
    JFrame f; 
    // Table 
    JTable j;
    DefaultTableModel tableModel = null;
    
    public DefaultTableModel getTableModel() {
    	return tableModel;
    }
    
    public JFrame getFrame() {
    	return f;
    }
  
    // Constructor 
    StockManager() 
    { 
        // Frame initiallization 
        f = new JFrame(); 
  
        // Frame Title 
        f.setTitle("JTable Example"); 
  
        // Data to be displayed in the JTable 
        String[][] data = {{}};
        
  
        // Column Names 
        String[] columnNames = { "Stock", "Symbol","Number Of Shares", "Price" }; 
  
        tableModel= new DefaultTableModel(data, columnNames);
        // Initializing the JTable 
        j = new JTable(tableModel); 
        j.setBounds(30, 40, 200, 300); 
  
        // adding it to JScrollPane 
        JScrollPane sp = new JScrollPane(j); 
        f.add(sp,BorderLayout.CENTER); 
        // Frame Size 
        f.setSize(500, 200); 
        // Frame Visible = true 
        f.setVisible(true); 
    } 
    
    public JPanel createButtons(DefaultTableModel defaultTableModel) {
    	JPanel panel = new JPanel();
    	JButton porfolioButton = new JButton("Get Portfolio");
    	JButton pricesButton = new JButton("Get Prices");
    	ButtonListener buttonListener = new ButtonListener(defaultTableModel);
    	porfolioButton.addActionListener(buttonListener);
    	pricesButton.addActionListener(buttonListener);
    	panel.add(porfolioButton);
    	panel.add(pricesButton);
    	return panel;
    }
  
    // Driver  method 
    public static void main(String[] args) 
    { 
    	StockManager stockManager = new StockManager(); 
    	JPanel buttonsPanel = stockManager.createButtons(stockManager.getTableModel());
    	stockManager.getFrame().add(buttonsPanel,BorderLayout.SOUTH);
    } 
}
