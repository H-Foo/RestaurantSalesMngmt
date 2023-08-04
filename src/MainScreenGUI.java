import java.util.ArrayList;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class MainScreenGUI extends JFrame{
    public static ArrayList<Food> foodMenu = new ArrayList<Food>();
    public static ArrayList<Beverage> drinkMenu = new ArrayList<Beverage>();
    public static final String[] column = {"Item","Quantity","Price (RM)"};
    public static Bill aBill = new Bill(); 
    
    public int qtyFood, qtyDrink;
    public String foodCode, drinkCode;
    
    JButton bRice;
    JButton bBurg;
    JButton bNood;
    JButton bSald;
    JButton bSpgt;
    JButton bWatr;
    JButton bChoc;
    JButton bCofe;
    JButton bTea;
    JButton bJuic;   
    JPanel billSect;
    DefaultTableModel model;        
    JTable item;
    
    public static void initMenu(){
        foodMenu.add(new Food("F0","Fried Rice",5.9));
        foodMenu.add(new Food("F1","Burger",3.5));
        foodMenu.add(new Food("F2","Noodle Soup",5.0));
        foodMenu.add(new Food("F3","Salad",3.0));
        foodMenu.add(new Food("F4","Spagetthi",5.9));
        drinkMenu.add(new Beverage("B0","Sparkling Water",3.0));
        drinkMenu.add(new Beverage("B1","Iced Chocolate",4.0));
        drinkMenu.add(new Beverage("B2","Coffee",4.9));
        drinkMenu.add(new Beverage("B3","Tea",3.9));
        drinkMenu.add(new Beverage("B4","Orange Juice",4.0));
    }
    
    public static void main(String[] args) throws IOException {
        initMenu();
        MainScreenGUI myScreen = new MainScreenGUI();
    }
    
    public MainScreenGUI(){
        JFrame f = new JFrame("Restaurant Sales Tracker");
        f.setLayout(new BorderLayout());
        f.setBounds(100,200,1080,720);     
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        dispBanner(f);
        dispMenu(f);

        JButton discount = new JButton("Got a PromoCode?  Click HERE to claim it!");
        discount.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
               String p = JOptionPane.showInputDialog(null,"Enter Promo Code.","DISCOUNT COUPON",JOptionPane.QUESTION_MESSAGE);
               try{
                   if(aBill.checkPromoCode(p)){
                    aBill.addPromoCode(p);
                    JOptionPane.showMessageDialog(null,"Voucher applied!");
                   }
                   else
                       JOptionPane.showMessageDialog(null,"Sorry, code invalid!");
               }
               catch(Exception e){
                   System.out.println("Error in discount action Listener!");
               }
            }                   
        });
        
        JButton payment = new JButton("Show Payment");
        payment.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                aBill.calcDiscount();
                String text = "Total Cost: RM" + aBill.getTotal() + "\nDiscount: RM" + aBill.getDiscount() + " \nNet Payable: RM" + aBill.getToPay();
                JOptionPane.showMessageDialog(null, text, "Payment",JOptionPane.PLAIN_MESSAGE);
            }
        });
        
        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent evt){
                if(item.getSelectedRow() != -1){
                    int rowIndex = item.getSelectedRow();
                    int colIndex = item.getSelectedColumn();
                    String temp =  (String)item.getValueAt(rowIndex,colIndex);
                    aBill.deleteItem(temp);
                    aBill.calcDiscount();
                    model.removeRow(item.getSelectedRow());
                    
                    JOptionPane.showMessageDialog(null,"Selected item deleted!");
                }
            }
        });
        
        JPanel bPanel = new JPanel(new FlowLayout());
        bPanel.add(discount);
        bPanel.add(payment);
        bPanel.add(remove);
        
        model = new DefaultTableModel(column,0);
        item = new JTable(model);
        item.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        billSect = new JPanel(new BorderLayout());
        billSect.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, rootPaneCheckingEnabled));
        billSect.add(item,"Center");
        billSect.add(item.getTableHeader(),"North");
        billSect.add(bPanel,"South");
        f.getContentPane().add(billSect,"Center");
        
        f.setVisible(true);
    }
    
    private void dispBanner(JFrame f){
        JLabel Banner = new JLabel("FOODIE STREET");
        Banner.setVerticalAlignment(JLabel.TOP);
        Banner.setBackground(new Color(255,204,51));
        Banner.setOpaque(true);
        Banner.setFont(new Font("Batang", Font.BOLD,20));
        f.getContentPane().add(Banner,"North");
    }
    
    private class FButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            if(evt.getSource() == bRice)
                foodCode = "F0";
            if(evt.getSource() == bBurg)
                foodCode = "F1";
            if(evt.getSource() == bNood)
                foodCode = "F2";
            if(evt.getSource() == bSald)
                foodCode = "F3";
            if(evt.getSource() == bSpgt)
                foodCode = "F4"; 
            
            String n = JOptionPane.showInputDialog(null,"Enter desired quantity.","Quantity of Item(s)", 
                JOptionPane.QUESTION_MESSAGE);
            try{
                int temp = Integer.parseInt(n);
                if(temp < 1)
                    JOptionPane.showConfirmDialog(null,"Value cannot be less than 1!", "WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                else{
                    qtyFood = temp;
                    aBill.orderFood(foodCode,qtyFood,foodMenu);
                    model.addRow(new Object[]{aBill.getFoodName(foodMenu),
                        convertQty(qtyFood),
                        aBill.getFoodPrice(foodMenu)});
                }
            }
            catch(NumberFormatException ex){
            }                                        
        }        
    }
    
    protected String convertQty(int qtty){
        String qty = qtty + " ";
        return qty;
    }
    
    private class DButtonActionListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent evt){
            if(evt.getSource() == bWatr)
                drinkCode = "B0";
            if(evt.getSource() == bChoc)
                drinkCode = "B1";
            if(evt.getSource() == bCofe)
                drinkCode = "B2";
            if(evt.getSource() == bTea)
                drinkCode = "B3";
            if(evt.getSource() == bJuic)
                drinkCode = "B4";
            
            String n = JOptionPane.showInputDialog(null,"Enter desired quantity.","Quantity of Item(s)", 
                JOptionPane.QUESTION_MESSAGE);
            try{
                int temp = Integer.parseInt(n);
                if(temp < 1)
                    JOptionPane.showConfirmDialog(null,"Value cannot be less than 1!", "WARNING", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
                else{
                    qtyDrink = temp;
                    aBill.orderBev(drinkCode, qtyDrink, drinkMenu);
                    model.addRow(new Object[]{aBill.getBevName(drinkMenu),
                        convertQty(qtyDrink),
                        aBill.getDrinkPrice(drinkMenu)});
                }
            }
            catch(NumberFormatException ex){
            }
        } 
    }
    
   private void dispMenu(JFrame f){
        JLabel lRice = new JLabel("RM 5.90");
        lRice.setIcon(new ImageIcon("src/Images/friedRice.png"));
        JLabel lBurg = new JLabel("RM 3.50");
        lBurg.setIcon(new ImageIcon("src/Images/beefburg.png"));
        JLabel lNood = new JLabel("RM 5.00");
        lNood.setIcon(new ImageIcon("src/Images/chickennoodle.png"));
        JLabel lSald = new JLabel("RM 3.00");
        lSald.setIcon(new ImageIcon("src/Images/salad.png"));
        JLabel lSpgt = new JLabel("RM 5.90");
        lSpgt.setIcon(new ImageIcon("src/Images/spagetthi.png"));
        bRice = new JButton("Add Fried Rice");
        bBurg = new JButton("Add Burger");
        bNood = new JButton("Add Noodle Soup");
        bSald = new JButton("Add Salad");
        bSpgt = new JButton("Add Spagetthi");        
        bRice.addActionListener(new FButtonActionListener());
        bBurg.addActionListener(new FButtonActionListener());
        bNood.addActionListener(new FButtonActionListener());
        bSald.addActionListener(new FButtonActionListener());
        bSpgt.addActionListener(new FButtonActionListener());
       
        JPanel foodSect = new JPanel();
        foodSect.setLayout(new BoxLayout(foodSect,BoxLayout.Y_AXIS));
        foodSect.setBorder(BorderFactory.createTitledBorder(null,"FOOD", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", Font.BOLD,14)));
        foodSect.add(lRice);
        foodSect.add(bRice);
        foodSect.add(lBurg);
        foodSect.add(bBurg);
        foodSect.add(lNood);
        foodSect.add(bNood);
        foodSect.add(lSald);
        foodSect.add(bSald);
        foodSect.add(lSpgt);
        foodSect.add(bSpgt);
        foodSect.setAlignmentY(RIGHT_ALIGNMENT);  
        
        JLabel lWatr = new JLabel("RM 3.00");
        lWatr.setIcon(new ImageIcon("src/Images/sparkling.png"));
        JLabel lChoc = new JLabel("RM 4.00");
        lChoc.setIcon(new ImageIcon("src/Images/icedChoc.png"));
        JLabel lCofe = new JLabel("RM 4.90");
        lCofe.setIcon(new ImageIcon("src/Images/coffee.png"));
        JLabel lTea = new JLabel("RM 3.90");
        lTea.setIcon(new ImageIcon("src/Images/tea.png"));
        JLabel lJuic = new JLabel("RM 4.00");        
        lJuic.setIcon(new ImageIcon("src/Images/orange.png"));
        bWatr = new JButton("Add Sparkling Water");
        bChoc = new JButton("Add Iced Chocolate");
        bCofe = new JButton("Add Coffee");
        bTea = new JButton("Add Tea");
        bJuic = new JButton("Add Orange Juice");   
        bWatr.addActionListener(new DButtonActionListener());
        bChoc.addActionListener(new DButtonActionListener());
        bCofe.addActionListener(new DButtonActionListener());
        bTea.addActionListener(new DButtonActionListener());
        bJuic.addActionListener(new DButtonActionListener());
        
        JPanel drinkSect = new JPanel();
        drinkSect.setLayout(new BoxLayout(drinkSect,BoxLayout.Y_AXIS));
        drinkSect.setBorder(BorderFactory.createTitledBorder(null,"BEVERAGE", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, new Font("Tahoma", Font.BOLD,14)));
        drinkSect.add(lWatr);
        drinkSect.add(bWatr);
        drinkSect.add(lChoc);
        drinkSect.add(bChoc);
        drinkSect.add(lCofe);
        drinkSect.add(bCofe);
        drinkSect.add(lTea);
        drinkSect.add(bTea);
        drinkSect.add(lJuic);
        drinkSect.add(bJuic);
        drinkSect.setAlignmentX(CENTER_ALIGNMENT);        
        
        JPanel menu = new JPanel(new GridLayout(1,1));
        menu.add(foodSect);
        menu.add(drinkSect);
        f.getContentPane().add(menu,"East");
    }
    
}
