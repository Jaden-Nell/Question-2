// This works out the VAT, commission and total price.
public class ProductCalculations implements IProductCalculations {
    double price;

    
    public ProductCalculations(double productPrice) {
        price = productPrice;
    }

    
    public double GetVat() {
        return price * 0.15;
    }

    
    public double GetCommission() {
        return price * 0.05;
    }

    
    public double GetTotal() {
        return price + GetVat() + GetCommission();
    }
}
