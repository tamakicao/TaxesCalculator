import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemDetailsTest {
    @BeforeEach
    public void initialStaticVariable(){
        ItemDetails.sumTax=0;
        ItemDetails.sumPrice=0;
        ItemDetails.receipt.delete(0,ItemDetails.receipt.length());
    }
    //Testing roundingRules1
    //consider only non-negative numbers with 0-4 decimal places due to setTextFormatter
    @Test
    public void test_roundingRules1_1() {
        assertEquals(0,ItemDetails.roundingRules1(0));
    }
    @Test
    public void test_roundingRules1_2() {
        assertEquals(1,ItemDetails.roundingRules1(1));
    }
    @Test
    public void test_roundingRules1_3() {
        assertEquals(0.1,ItemDetails.roundingRules1(0.1));
    }
    @Test
    public void test_roundingRules1_4() {
        assertEquals(0.05,ItemDetails.roundingRules1(0.01));
    }
    @Test
    public void test_roundingRules1_5() {
        assertEquals(0.05,ItemDetails.roundingRules1(0.05));
    }
    @Test
    public void test_roundingRules1_6() {
        assertEquals(0.1,ItemDetails.roundingRules1(0.06));
    }
    @Test
    public void test_roundingRules1_7() {
        assertEquals(0.1,ItemDetails.roundingRules1(0.051));
    }
    @Test
    public void test_roundingRules1_8() {
        assertEquals(0.1,ItemDetails.roundingRules1(0.0501));
    }
    /*
    @Test
    public void test_roundingRules1_9() {
        assertEquals(0.15,ItemDetails.roundingRules1(0.1001));
    }
    Test failed
    */

    //Testing roundingRules2
    //consider only non-negative numbers
    @Test
    public void roundingRules2_1(){
        assertEquals(0,ItemDetails.roundingRules2(0));
    }
    @Test
    public void roundingRules2_2(){
        assertEquals(1,ItemDetails.roundingRules2(1));
    }
    @Test
    public void roundingRules2_3(){
        assertEquals(0.1,ItemDetails.roundingRules2(0.1));
    }
    @Test
    public void roundingRules2_4(){
        assertEquals(0.25,ItemDetails.roundingRules2(0.25));
    }
    @Test
    public void roundingRules2_5(){
        assertEquals(0.2,ItemDetails.roundingRules2(0.201));
    }
    @Test
    public void roundingRules2_6(){
        assertEquals(0.21,ItemDetails.roundingRules2(0.205));
    }
    @Test
    public void roundingRules2_7(){
        assertEquals(0.47,ItemDetails.roundingRules2(0.4711));
    }
    @Test
    public void roundingRules2_8(){
        assertEquals(0.48,ItemDetails.roundingRules2(0.4751));
    }
    /*
    Test case 0.15,roundingRules1(0.1001) failed
    expected: <0.15> but was: <0.15000000000000002>
    add method roundingRules2 to round it to 2 decimal places
    and test if these 2 methods work together
    */
    @Test
    public void test_roundingRules1and2_1() {
        assertEquals(0.15,ItemDetails.roundingRules2(ItemDetails.roundingRules1(0.1001)));
    }

    //Testing ItemDetails constructor
    @Test
    public void test_ItemDetailsConstructor(){
        ItemDetails item = new ItemDetails("apple",1,1,true,true);
        assertEquals("apple",item.name);
        assertEquals(1,item.quantity);
        assertEquals(1,item.unitPrice);
        assertTrue(item.imported);
        assertTrue(item.exempt);
    }

    //Testing calculator
    /*
    check if changes of item name applied
    remove whitespace on both sides of name
    add "imported " if item is imported
    add "s" if more than one item
    */
    @Test
    public void test_calculator_1(){
        ItemDetails item = new ItemDetails(" apple ",2,1,true,false);
        item.calculate();
        assertEquals("imported apples",item.name);
    }
    //check tax rate
    //non-imported & exempt ---> rate=0
    @Test
    public void test_calculator_2(){
        ItemDetails item = new ItemDetails("apple",1,1,false,true);
        item.calculate();
        assertEquals(0,item.rate);
    }
    //non-imported & non-exempt ---> rate=0.1
    @Test
    public void test_calculator_3(){
        ItemDetails item = new ItemDetails("apple",1,1,false,false);
        item.calculate();
        assertEquals(0.1,item.rate);
    }
    //imported & exempt ---> rate=0.05
    @Test
    public void test_calculator_4(){
        ItemDetails item = new ItemDetails("apple",1,1,true,true);
        item.calculate();
        assertEquals(0.05,item.rate);
    }
    //imported & non-exempt ---> rate=0.15
    //expected: <0.15> but was: <0.15000000000000002> double precision issues refactored
    @Test
    public void test_calculator_5(){
        ItemDetails item = new ItemDetails("apple",1,1,true,false);
        item.calculate();
        assertEquals(0.15,item.rate);
    }
    //testing tax and price
    @Test
    public void test_calculator_6(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        assertEquals(0,item.tax);
        assertEquals(12.99,item.price);
    }
    @Test
    public void test_calculator_7(){
        ItemDetails item = new ItemDetails("book",2,12.99,false,true);
        item.calculate();
        assertEquals(0,item.tax);
        assertEquals(25.98,item.price);
    }
    @Test
    public void test_calculator_8(){
        ItemDetails item = new ItemDetails("perfume",1,27.99,true,false);
        item.calculate();
        assertEquals(4.2,item.tax);
        assertEquals(32.19,item.price);
    }
    @Test
    public void test_calculator_9(){
        ItemDetails item = new ItemDetails("perfume",3,27.99,true,false);
        item.calculate();
        assertEquals(12.6,item.tax);
        assertEquals(96.57,item.price);
    }
    @Test
    public void test_calculator_10(){
        ItemDetails item = new ItemDetails("chocolates",1,11.25,true,true);
        item.calculate();
        assertEquals(0.6,item.tax);
        assertEquals(11.85,item.price);
    }
    //expected: <2.4> but was: <2.25>
    //tax should be round to the nearest 0.05 before multiply by quantity
    //double precision---->call roundingRules2 at last
    //fixed itemDetails line 56
    @Test
    public void test_calculator_11(){
        ItemDetails item = new ItemDetails("chocolates",4,11.25,true,true);
        item.calculate();
        assertEquals(2.4,item.tax);
        assertEquals(47.4,item.price);
    }
    @Test
    public void test_calculator_12(){
        ItemDetails item = new ItemDetails("CD",1,14.99,false,false);
        item.calculate();
        assertEquals(1.5,item.tax);
        assertEquals(16.49,item.price);
    }
    @Test
    public void test_calculator_13(){
        ItemDetails item = new ItemDetails("CD",5,14.99,false,false);
        item.calculate();
        assertEquals(7.5,item.tax);
        assertEquals(82.45,item.price);
    }
    @Test
    public void test_calculator_14(){
        ItemDetails item = new ItemDetails("free sample",1,0,false,false);
        item.calculate();
        assertEquals(0,item.tax);
        assertEquals(0,item.price);
    }
    //testing add to receipt
    @Test
    public void test_calculator_15(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        String expect = "1 book at 12.99\n";
        assertEquals(expect,item.lineItem);
        assertEquals(0,ItemDetails.sumTax);
        assertEquals(12.99,ItemDetails.sumPrice);
        assertEquals(expect,ItemDetails.receipt.toString());
    }
    @Test
    public void test_calculator_16(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        String expect = "1 book at 12.99\n";
        assertEquals(expect,item.lineItem);
        ItemDetails item2 = new ItemDetails("CD",5,14.99,false,false);
        item2.calculate();
        String expect2 = "5 CDs at 82.45\n";
        assertEquals(expect2,item2.lineItem);

        assertEquals(7.5,ItemDetails.sumTax);
        assertEquals(95.44,ItemDetails.sumPrice);
        assertEquals(expect+expect2,ItemDetails.receipt.toString());
    }
    //testing updateReceipt
    @Test
    public void test_updateReceipt_1(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        String expect = "1 book at 12.99\n";

        ItemDetails item2 = new ItemDetails("CD",5,14.99,false,false);
        item2.calculate();
        String expect2 = "5 CDs at 82.45\n";

        String sumT = "\nSales Taxes: 7.5";
        String sumP = "\nTotal: 95.44";

        assertEquals(expect+expect2+sumT+sumP,item2.updateReceipt());
    }
    //testing initialSumTax initialSumPrice initialReceipt
    @Test
    public void test_initialSumTax(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        item.updateReceipt();
        item.initialSumTax();
        assertEquals(0,ItemDetails.sumTax);
    }
    @Test
    public void test_initialSumPrice(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        item.updateReceipt();
        item.initialSumPrice();
        assertEquals(0,ItemDetails.sumPrice);
    }
    @Test
    public void test_initialReceipt(){
        ItemDetails item = new ItemDetails("book",1,12.99,false,true);
        item.calculate();
        item.updateReceipt();
        item.initialReceipt();
        assertEquals(0,ItemDetails.receipt.length());
    }
}
