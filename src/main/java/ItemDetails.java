public class ItemDetails {
    String name;
    int quantity;
    double unitPrice;
    boolean exempt;
    boolean imported;

    double rate;
    double price;
    double tax;
    String lineItem="";

    static StringBuilder receipt= new StringBuilder();
    static double sumPrice;
    static double sumTax;

    public ItemDetails (String n, int q, double u,boolean i, boolean e){
        this.name=n;
        this.quantity=q;
        this.unitPrice=u;
        this.imported=i;
        this.exempt=e;
    }

    //rounding rules
    //round up to the nearest 0.05
    public static double roundingRules1(double d1) {
        return Math.ceil((d1 / 0.05)) * 0.05;
    }

    //round to 2 decimal places
    public static double roundingRules2(double d2) {
        return (double) Math.round(d2 * 100) / 100;
    }

    public void calculate() {
        name = name.trim();
        if (quantity > 1){
            name  = name + "s";
        }
        //determine tax rate
        if (imported) {
            rate += 0.05;
            name = "imported " + name;
        }
        if (!exempt) {
            rate += 0.1;
            rate = roundingRules2(rate);
        }

        //calculate taxes and end-price
        //tax = rate * unitPrice * quantity;
        //tax = roundingRules2(roundingRules1(tax));
        tax = rate * unitPrice;
        tax = roundingRules1(tax);
        tax = roundingRules2(tax * quantity);
        price = unitPrice * quantity + tax;
        price = roundingRules2(price);

        //add item to receipt
        lineItem += quantity + " " + name + " at " + price + "\n";
        receipt.append(lineItem);
        sumPrice = roundingRules2(sumPrice + price);
        sumTax = roundingRules2(sumTax+tax);
    }

    public String updateReceipt(){
        receipt.append("\nSales Taxes: ").append(sumTax).append("\nTotal: ").append(sumPrice);
        return receipt.toString();
    }

    public void initialSumPrice(){
        sumPrice = 0;
    }

    public void initialSumTax(){
        sumTax = 0;
    }

    public void initialReceipt(){
        receipt.delete(0,receipt.length());
    }
}

