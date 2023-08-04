public class Food implements Order{
    private String code;
    private String name;
    private double price;
    
    Food(){}
    
    public Food(String code, String name, double price){
        this.code = code;
        this.name = name;
        this.price = price;
    }
    
    @Override
    public String getCode(){
        return code;
    }
    
    @Override
    public String getName(){
        return name;
    }
    
    @Override
    public double getPrice(){
        return price;
    }
    
}
