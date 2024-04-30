package entities;

public class Menu {
  private String name;
  private int price;
  private Category category;

  public Menu(String name, int price, Category category) {
    this.name = name;
    this.price = price;
    this.category = category;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  public Category getCategory() {
    return category;
  }
}
