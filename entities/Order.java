package entities;

public class Order {
  private Menu menu;
  private int qty;

  public Order(Menu menu, int qty) {
    this.menu = menu;
    this.qty = qty;
  }

  public String getMenuName() {
    return menu.getName();
  }

  public int getQty() {
    return qty;
  }

  public int subTotal() {
    return menu.getPrice() * qty;
  }
}
