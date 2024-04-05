import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Libro {
    private String titulo;
    private String autor;
    private int yearPublicacion;

    public Libro(String titulo, String autor, int yearPublicacion) {
        this.titulo = titulo;
        this.autor = autor;
        this.yearPublicacion = yearPublicacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getYearPublicacion() {
        return yearPublicacion;
    }
}

class Biblioteca {
    private List<Libro> librosDisponibles;
    private Map<Integer, Libro> librosPrestados;
    private Map<Integer, Usuario> usuarios;
    private int nextIdLibro = 1;

    public Biblioteca() {
        librosDisponibles = new ArrayList<>();
        librosPrestados = new HashMap<>();
        usuarios = new HashMap<>();
    }

    public void agregarLibro(Libro libro) {
        librosDisponibles.add(libro);
    }

    public void prestarLibro(String titulo, String nombre, int identificador) {
        Libro libroPrestamo = librosDisponibles.stream()
                .filter(libro -> libro.getTitulo().equals(titulo))
                .findFirst()
                .orElse(null);
        Usuario usuario = usuarios.get(identificador);

        if (libroPrestamo != null && usuario != null) {
            librosDisponibles.remove(libroPrestamo);
            librosPrestados.put(nextIdLibro, libroPrestamo);
            nextIdLibro++;
            System.out.println("Libro '" + titulo + "' prestado exitosamente a " + nombre);
        } else {
            System.out.println("No se pudo prestar el libro. Verifique el titulo del libro y la identificacion del usuario.");
        }
    }
    public void devolverLibro(String titulo) {
        Libro libroDevuelto = null;
        for (Libro libro : librosPrestados.values()) {
            if (libro.getTitulo().equals(titulo)) {
                libroDevuelto = libro;
                break;
            }
        }

        if (libroDevuelto != null) {
            librosPrestados.remove(libroDevuelto);
            librosDisponibles.add(libroDevuelto);
            System.out.println("Libro '" + titulo + "' devuelto exitosamente.");
        } else {
            System.out.println("No se pudo devolver el libro. Verifique el titulo del libro.");
        }
    }

    public void mostrarCatalogo() {
        System.out.println("Catalogo de la biblioteca:");
        for (Libro libro : librosDisponibles) {
            System.out.println(libro.getTitulo() + " - " + libro.getAutor() + " - " + libro.getYearPublicacion());
        }
    }

    public void mostrarLibrosPrestados() {
        System.out.println("Libros prestados actualmente:");
        for (Map.Entry<Integer, Libro> entry : librosPrestados.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue().getTitulo());
        }
    }

    public void agregarUsuario(Usuario usuario) {
        if (!usuarios.containsKey(usuario.getIdentificador())) {
            usuarios.put(usuario.getIdentificador(), usuario);
            System.out.println("Usuario agregado con exito.");
        } else {
            System.out.println("El identificador de usuario ya existe.");
        }
    }
}

class Usuario {
    private String nombre;
    private int identificador;

    public Usuario(String nombre, int identificador) {
        this.nombre = nombre;
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdentificador() {
        return identificador;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Biblioteca biblioteca = new Biblioteca();

        while (true) {
            System.out.println("\nMenu de opciones:");
            System.out.println("1. Agregar libro");
            System.out.println("2. Prestar libro");
            System.out.println("3. Devolver libro");
            System.out.println("4. Mostrar catalogo");
            System.out.println("5. Mostrar libros prestados");
            System.out.println("6. Agregar usuario");
            System.out.println("7. Salir");

            System.out.print("Seleccione una opcion: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el titulo del libro: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese el autor del libro: ");
                    String autor = scanner.nextLine();
                    System.out.print("Ingrese el year de publicacion del libro: ");
                    int yearPublicacion = scanner.nextInt();
                    scanner.nextLine();
                    biblioteca.agregarLibro(new Libro(titulo, autor, yearPublicacion));
                    System.out.println("Libro agregado al catalogo.");
                    break;
                case 2:
                    System.out.print("Ingrese el titulo del libro a prestar: ");
                    String tituloLibro = scanner.nextLine();
                    System.out.print("Ingrese su nombre: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese su identificacion: ");
                    int identificador = scanner.nextInt();
                    scanner.nextLine();
                    biblioteca.prestarLibro(tituloLibro, nombre, identificador);
                    break;
                case 3:
                    System.out.print("Ingrese el titulo del libro a devolver: ");
                    String tituloLibroDevolver = scanner.nextLine();
                    biblioteca.devolverLibro(tituloLibroDevolver);
                    break;
                case 4:
                    biblioteca.mostrarCatalogo();
                    break;
                case 5:
                    biblioteca.mostrarLibrosPrestados();
                    break;
                case 6:
                    System.out.print("Ingrese su nombre: ");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Ingrese su identificacion: ");
                    int identificacionUsuario = scanner.nextInt();
                    scanner.nextLine();
                    biblioteca.agregarUsuario(new Usuario(nombreUsuario, identificacionUsuario));
                    break;
                case 7:
                    System.out.println("Hasta luego");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }
    }
}
