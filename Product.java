import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String ProductName;
    private String ProductDescription;
    private String ID;
    private double Price;

    public Product(String ProductName, String ProductDescription, String ID, double Cost) {
        this.ProductName = ProductName;
        this.ProductDescription = ProductDescription;
        this.ID = ID;
        this.Price = Price;
    }

    //GETTERS
    public String getProductName() {
        return ProductName;
    }

    public String getProductDescription() {
        return ProductDescription;
    }

    public String getID() {
        return ID;
    }

    public double getCost() {
        return Price;
    }

    //SETTERS

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public void setProductDescription(String productDescription) {
        ProductDescription = productDescription;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setCost(double Price) {
        Price = Price;
    }

    @Override
    public String toString() {
        return String.format("Product{" + "ProductName=' " + ProductName + '\'' + ", ProductDescription=' " + ProductDescription + '\'' + ", ID=' " + ID + '\'' + ", Price=" + Price);
    }
}