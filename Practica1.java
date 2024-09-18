import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Clase Cliente
class Cliente {
    private String nombre;
    private String direccion;

    public Cliente(String nombre, String direccion) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
}

// Clase Pedido
class Pedido {
    private int numero;
    private String descripcion;
    private List<String> articulos;
    private Cliente cliente;

    public Pedido(int numero, String descripcion, Cliente cliente) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.articulos = new ArrayList<>();
        this.cliente = cliente;
    }

    public void agregarArticulo(String articulo) {
        articulos.add(articulo);
    }

    public List<String> getArticulos() {
        return articulos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "Pedido #" + numero + " (" + descripcion + ") para " + cliente.getNombre();
    }
}

// Clase Pago
class Pago {
    private int numero;
    private Pedido pedido;
    private String metodoPago;
    private double total;

    public Pago(int numero, Pedido pedido, String metodoPago, double total) {
        this.numero = numero;
        this.pedido = pedido;
        this.metodoPago = metodoPago;
        this.total = total;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public double getTotal() {
        return total;
    }

    @Override
    public String toString() {
        return "Pago #" + numero + " para el " + pedido.toString() + " con metodo de pago: " + metodoPago + ". Total: $" + total;
    }
}

// Clase Notificación
class Notiicacion {
    private int numero;
    private Pedido pedido;
    private String guia;
    private String fechaEntrega;
    private String correo;

    public Notiicacion(int numero, Pedido pedido, String guia, String fechaEntrega, String correo) {
        this.numero = numero;
        this.pedido = pedido;
        this.guia = guia;
        this.fechaEntrega = fechaEntrega;
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Notificacion #" + numero + ": Pedido #" + pedido.toString() + " sera entregado el " + fechaEntrega + ". Se envio a " + correo;
    }
}

// Clase Hilo para el proceso completo 
class ClienteHilo extends Thread {
    private int clienteId;
    private List<String> articulosDisponibles;

    public ClienteHilo(int clienteId, List<String> articulosDisponibles) {
        this.clienteId = clienteId;
        this.articulosDisponibles = articulosDisponibles;
    }

    @Override
    public void run() {
        // Crear cliente
        Cliente cliente = new Cliente("Cliente-" + clienteId, "Direccion-" + clienteId);

        // Crear un pedido
        Pedido pedido = new Pedido(clienteId, "Pedido de Cliente-" + clienteId, cliente);
        Random random = new Random();
        int articuloIndex = random.nextInt(articulosDisponibles.size());  // Elegir un articulo aleatorio
        String articulo = articulosDisponibles.get(articuloIndex);

        pedido.agregarArticulo(articulo);
        System.out.println(cliente.getNombre() + " ha realizado un " + pedido.toString() + " con el articulo: " + articulo);

        //el pago
        try {
            Thread.sleep(2000); //el tiempo de procesamiento del pago
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        double total = random.nextDouble() * 1000;  //un total aleatorio
        Pago pago = new Pago(clienteId, pedido, "Tarjeta de Credito", total);
        System.out.println(pago.toString());

        //notificacion
        Notiicacion notiicacion = new Notiicacion(clienteId, pedido, "Guia-" + clienteId, "2024-12-01", "cliente" + clienteId + "@correo.com");
        System.out.println(notiicacion.toString());
    }
}

// Clase principal
public class Practica1 {
    public static void main(String[] args) {
        // Crea una lista de artículos disponibles en la tienda
        List<String> articulosDisponibles = new ArrayList<>();
        articulosDisponibles.add("Laptop");
        articulosDisponibles.add("Smartphone");
        articulosDisponibles.add("Tablet");
        articulosDisponibles.add("Audífonos");

        // Crea e iniciar hilos para 5 clientes
        int numClientes = 5;  // numero de clientes
        List<ClienteHilo> hilosClientes = new ArrayList<>();

        for (int i = 1; i <= numClientes; i++) {
            ClienteHilo hilo = new ClienteHilo(i, articulosDisponibles);  // Pasamos el ID de cliente y la lista de articulos
            hilosClientes.add(hilo);
            hilo.start();  // Inicia el hilo 
        }

        // Esperar a que todos terminen
        for (ClienteHilo hilo : hilosClientes) {
            try {
                hilo.join();  // Espera a que cada uno termine
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Todos los clientes han completado sus pedidos.");
    }
}


//saludos chavales
