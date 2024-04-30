import java.util.ArrayList;
import java.util.Scanner;

import entities.Category;
import entities.Menu;
import entities.Order;

public class Main {
  static Menu[] menus = {
    new Menu("Nasi Putih", 5000, Category.FOOD),
    new Menu("Pecel Ayam", 15000, Category.FOOD),
    new Menu("Nasi Kebuli", 30000, Category.FOOD),
    new Menu("Rawon", 25000, Category.FOOD),
    new Menu("Es Teh", 5000, Category.BEVERAGE),
    new Menu("Jus Jeruk", 10000, Category.BEVERAGE),
    new Menu("Es Campur", 8000, Category.BEVERAGE),
    new Menu("Kopi Susu", 5000, Category.BEVERAGE),
  };
  static final double TAX = 0.1;
  static final double DISCOUNT = 0.1;
  static final int SERVICE_FEE = 20000;

  static ArrayList<Order> orders = new ArrayList<Order>();
  static ArrayList<Order> freeDrinks = new ArrayList<Order>();

  public static void showMenus() {
    System.out.println("MENU");
    System.out.println("==========================");
    System.out.println("Makanan");
    for (int i = 0; i < menus.length; i++) {
      if (menus[i].getCategory() == Category.FOOD) {
        System.out.println(String.format("%1$s. %2$s Rp.%3$s", i+1, menus[i].getName(), menus[i].getPrice()));
      }
    }
    System.out.println("==========================");
    System.out.println("Minuman");
    for (int i = 0; i < menus.length; i++) {
      if (menus[i].getCategory() == Category.BEVERAGE) {
        System.out.println(String.format("%1$s. %2$s Rp.%3$s", i+1, menus[i].getName(), menus[i].getPrice()));
      }
    }
    System.out.println("==========================");
  }

  public static void onOrderMenu(Scanner scanner) {
    System.out.println("Silahkan Pesan di Bawah Ini");
    System.out.println("Format Pesan: \"Nama Menu\" = \"Jumlah\". Jika sudah selesai tekan enter.");

    // MAX 4 ITEM ORDER
    for (int i = 0; i < 4; i++) {
      // START INPUT ORDER
      System.out.print("-> ");
      String orderInput = scanner.nextLine();

      // ADD INPUT ORDER INTO ORDER LIST
      if (orderInput != "") {
        addItemOrder(orderInput);
      } else {
        break;
      }
    }
  }

  public static void addItemOrder(String orderInput) {
    String[] splittedOrder = orderInput.split("=");
    String name = splittedOrder[0].trim().toLowerCase();
    int qty = Integer.parseInt(splittedOrder[1].trim());

    for (Menu menu: menus) {
      if (menu.getName().toLowerCase().equals(name)) {
        orders.add(new Order(menu, qty));
      }
    }
  }

  public static void addFreeDrinks(String orderInput) {
    String[] splittedOrder = orderInput.split("=");
    String name = splittedOrder[0].trim().toLowerCase();
    int qty = Integer.parseInt(splittedOrder[1].trim().toLowerCase());

    for (Menu menu: menus) {
      if (menu.getName().toLowerCase().equals(name)) {
        freeDrinks.add(new Order(menu, qty));
      }
    }
  }

  public static int sumSubtotal() {
    int sumtotalOrder = 0;
    for(Order order: orders) {
      sumtotalOrder += order.subTotal();
    }

    return sumtotalOrder;
  }

  public static double sumTotal(Scanner scanner) {
    double discountAmount = 0.0;
    int sumtotalOrder = sumSubtotal();

    // BUY 1 DRINK GET 1 FREE
    if (sumtotalOrder > 50000) {
      System.out.println("==========================");
      System.out.println(String.format("SUBTOTAL: %1$s", sumtotalOrder));
      System.out.println("==========================");
      System.out.println("Penawaran: Beli 1 Gratis 1 untuk Minuman");
      System.out.println("Format Pesan: \"Nama Menu\". Jika sudah selesai tekan enter.");
      // START INPUT ORDER
      System.out.print("-> ");
      String orderInput = scanner.nextLine();

      // ADD INPUT ORDER INTO ORDER LIST
      if (orderInput != "") {
        addItemOrder(orderInput + " = 2");
        addFreeDrinks(orderInput + " = 1");
      }

      sumtotalOrder = sumSubtotal();
    }

    // DISCOUNT
    if (sumtotalOrder > 100000) {
      discountAmount = sumtotalOrder * DISCOUNT;
    }

    // SUM TOTAL
    double taxAmount = sumtotalOrder * TAX;
    double total = (sumtotalOrder - discountAmount) + taxAmount + SERVICE_FEE;

    if (freeDrinks.size() > 0) {
      total -= freeDrinks.get(0).subTotal();
    }

    return total;
  }

  public static void printBill(double total) {
    System.out.println("");
    System.out.println("==========================");
    System.out.println("RECEIPT");
    System.out.println("==========================");
    for (Order order: orders) {
      System.out.println(String.format("%1$s x%2$s Rp %3$s", order.getMenuName(), order.getQty(), order.subTotal()));
    }

    int sumtotalOrder = sumSubtotal();
    System.out.println("==========================");
    System.out.println(String.format("SUBTOTAL: Rp %1$s", sumtotalOrder));
    System.out.println(String.format("TAX: Rp %1$s", sumtotalOrder * TAX));
    System.out.println(String.format("SERVICE FEE: Rp %1$s", SERVICE_FEE));
    if (freeDrinks.size() > 0) {
      System.out.println(String.format("FREE DRINK: (Rp %1$s)", freeDrinks.get(0).subTotal()));
    }
    if (sumtotalOrder > 100000) {
      System.out.println(String.format("DISCOUNT: (Rp %1$s)", sumtotalOrder * DISCOUNT));
    }
    System.out.println("==========================");
    System.out.println(String.format("TOTAL: Rp %1$s", total));
    System.out.println("==========================");
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    showMenus();
    onOrderMenu(scanner);
    double total = sumTotal(scanner);
    printBill(total);
  }

}
