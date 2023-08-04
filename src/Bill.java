import java.util.ArrayList;

public class Bill implements Discount{
    private String orderedFood;
    private String orderedBev;
    private int orderedQty;
    private String DiscountCode;
    private double total, toPay, discounted;
    
    public Bill(){
      DiscountCode = "";
      orderedFood = " ";
      orderedBev = " ";
      orderedQty = 0;
      total = 0.0;
      toPay = 0.0;
      discounted = 0.0;
    }
    
    public void orderFood(String itemCode, int quantity, ArrayList<Food> menu){
        orderedFood = itemCode;
        orderedQty = quantity;

        for(int i=0;i<menu.size();i++){
            if(itemCode.equals(menu.get(i).getCode()))
                total += quantity * menu.get(i).getPrice();
        }
    }

    public void orderBev(String itemCode, int quantity, ArrayList<Beverage> menu){
        orderedBev = itemCode;
        orderedQty = quantity;

        for(int i=0;i<menu.size();i++){
            if(itemCode.equals(menu.get(i).getCode()))
                total += quantity * menu.get(i).getPrice();
        }
    }    
        
    public String getFoodName(ArrayList<Food> menu){
        String name = " ";
        for(int i=0;i<menu.size();i++){
            if(orderedFood == menu.get(i).getCode())
                name = menu.get(i).getName();
        }
        return name;
    }
    
    public String getBevName(ArrayList<Beverage> menu){
        String name = " ";
        for(int i=0;i<menu.size();i++){
            if(orderedBev == menu.get(i).getCode())
                name = menu.get(i).getName();
        }
        return name;
    }
    
    public String getFoodPrice(ArrayList<Food> menu){
        String price = " ";
        for(int i=0;i<menu.size();i++){
            if(orderedFood == menu.get(i).getCode())
                price = (menu.get(i).getPrice()*orderedQty)+ " ";
        }
        return price;    
    }

    public String getDrinkPrice(ArrayList<Beverage> menu){
        String price = " ";
        for(int i=0;i<menu.size();i++){
            if(orderedBev == menu.get(i).getCode())
                price = (menu.get(i).getPrice()*orderedQty)+ " ";
        }
        return price;    
    }
    
    public double getTotal(){
        return total;
    }
    
    public double getToPay(){
        return toPay;
    }
    
    public double getDiscount(){
        return discounted;
    }
    
    public void deleteItem(String p){
        double price = Double.parseDouble(p);
        total = total - price;
    }
    
    @Override
    public void addPromoCode(String pcode){
        DiscountCode = pcode;
    }
    
    public boolean checkPromoCode(String pcode){
        if(pcode.equals("PROMO20") || pcode.equals("PROMO10"))
            return true;
        else
            return false;
    }
    
    @Override
    public void calcDiscount(){
    try{
        if(!DiscountCode.isEmpty()){
           if(DiscountCode.equals("PROMO20")){
               toPay = total - (total*0.2);
               discounted = total*0.2;
               
           }
           if(DiscountCode.equals("PROMO10")){
               toPay = total - (total*0.1);
               discounted = total*0.1;
           }
        }
        else{
            toPay = total;
        }
    }
    catch(Exception e){
        System.out.println("Error in calcDiscount!");
    }
    }
}
