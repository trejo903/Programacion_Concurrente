import java.util.ArrayList;
import java.util.List;

// Clase Producto
class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
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

// Clase Carrito
class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public double checkout() {
        double total = 0;
        for (Product p : products) {
            total += p.getPrice();
        }
        return total;
    }
}

// Clase Usuario
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

// Clase Hilo para simular una compra
class CompraHilo extends Thread {
    private User user;
    private Product product;

    public CompraHilo(User user, Product product) {
        this.user = user;
        this.product = product;
    }

    @Override
    public void run() {
        System.out.println(user.getName() + " está agregando " + product.getName() + " al carrito.");
        try {
            Thread.sleep(1000); // Simula el tiempo que tarda en agregar un producto al carrito
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        user.getCart().addProduct(product);
        System.out.println(user.getName() + " ha agregado " + product.getName() + " al carrito.");
    }
}

// Clase Hilo para notificar al usuario cuando la compra haya finalizado
class NotificacionHilo extends Thread {
    private User user;

    public NotificacionHilo(User user) {
        this.user = user;
    }

    @Override
    public void run() {
        System.out.println(user.getName() + " está procesando la compra.");
        try {
            Thread.sleep(2000); // Simula el tiempo que tarda en procesar la compra
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double total = user.getCart().checkout();
        System.out.println("Compra de " + user.getName() + " completada. Total: $" + total);
        System.out.println("Notificación: Gracias por tu compra, " + user.getName() + "!");
    }
}


public class Practica1 {
    public static void main(String[] args) {
        // Crear productos
        Product p1 = new Product(1, "Laptop", 800);
        Product p2 = new Product(2, "Smartphone", 500);

        // Crear usuarios
        User user1 = new User("Juan");
        User user2 = new User("Maria");

        // Crear e iniciar hilos para agregar productos
        CompraHilo compraHilo1 = new CompraHilo(user1, p1);
        CompraHilo compraHilo2 = new CompraHilo(user2, p2);

        compraHilo1.start();
        compraHilo2.start();

        // Esperar a que ambos hilos terminen de agregar productos
        try {
            compraHilo1.join();
            compraHilo2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Crear e iniciar hilos de notificación una vez que la compra se haya realizado
        NotificacionHilo notificacionHilo1 = new NotificacionHilo(user1);
        NotificacionHilo notificacionHilo2 = new NotificacionHilo(user2);

        notificacionHilo1.start();
        notificacionHilo2.start();
    }
}
