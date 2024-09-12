import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//coopel mejora tu vida 

class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nombre: " + name + " | Precio: $" + price;
    }
}

class User {
    private String name;
    private Cart cart;

    public User(String name) {
        this.name = name;
        this.cart = new Cart();
    }

    public String getName() {
        return name;
    }

    public Cart getCart() {
        return cart;
    }
}
//carrito
class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Producto añadido al carrito: " + product.getName());
    }
//ver lo que hay en el carrito
    public void viewCart() {
        if (products.isEmpty()) {
            System.out.println("El carrito está vacío.");
        } else {
            System.out.println("|---------------------------------------|");
            System.out.println("Productos en el carrito:");
            System.out.println("|---------------------------------------|");
            for (Product product : products) {
                System.out.println(product);
            }
        }
    }

    public void checkout() {
        if (products.isEmpty()) {
            System.out.println("El carrito está vacío");
        } else {
            double total = 0;
            System.out.println("Procesando compra.............");
            for (Product product : products) {
                total += product.getPrice();
            }
            System.out.println("Esto es lo que vas a pagar: $" + total);
            products.clear();
            System.out.println("La compra se realizo con éxito............................");
        }
    }
}

public class Practica1 {
    private List<Product> products = new ArrayList<>();
    private User user;

    public Practica1(User user) {
        this.user = user;
        products.add(new Product(1, "Italika ", 60000));
        products.add(new Product(2, "Lavadoras", 9500));
        products.add(new Product(3, "Jabón Zote", 400));
        products.add(new Product(4, "Comedor integrado", 20000));
    }

    public void showProducts() {
        System.out.println("Estos son los productos disponibles:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Product getProductById(int id) {
        for (Product product : products) {
            if (product.getId() == id) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Bienvenido al E-Commerce Pada");
        System.out.println("Necesitamos su nombre para facturar.");
        System.out.print("¿Como se llama?: ");
        String userName = scanner.nextLine();
        User user = new User(userName);
        Practica1 store = new Practica1(user);

        while (true) {
            System.out.println("\nMenú:");
            System.out.println("1. Tienda departamental");
            System.out.println("2. Añadir producto al carrito");
            System.out.println("3. Ver carrito");
            System.out.println("4. Realizar compra");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    store.showProducts();
                    break;
                case 2:
                    System.out.print("Ingrese el ID del producto que desea añadir al carrito: ");
                    int productId = scanner.nextInt();
                    Product product = store.getProductById(productId);
                    if (product != null) {
                        user.getCart().addProduct(product);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;
                case 3:
                    user.getCart().viewCart();
                    break;
                case 4:
                    user.getCart().checkout();
                    break;
                case 5:
                    System.out.println("Gracias por comprar en E-Commerce Padalustro..........");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opción invalida");
                    break;
            }
        }
    }
}
