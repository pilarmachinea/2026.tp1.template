package com.BiblioTech;

import com.BiblioTech.exception.BibliotecaException;
import com.BiblioTech.model.*;
import com.BiblioTech.repository.*;
import com.BiblioTech.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        LibroRepositoryImpl libroRepo = new LibroRepositoryImpl();
        SocioRepositoryImpl socioRepo = new SocioRepositoryImpl();
        PrestamoRepositoryImpl prestamoRepo = new PrestamoRepositoryImpl();

        LibroService libroService = new LibroServiceImpl(libroRepo);
        SocioService socioService = new SocioServiceImpl(socioRepo);
        PrestamoService prestamoService = new PrestamoServiceImpl(libroRepo, socioRepo, prestamoRepo);

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- BiblioTech ---");
            System.out.println("1. Registrar libro");
            System.out.println("2. Registrar socio");
            System.out.println("3. Buscar recursos");
            System.out.println("4. Realizar prestamo");
            System.out.println("5. Registrar devolucion");
            System.out.println("6. Ver historial");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            switch (opcion) {
                case 1 -> {
                    System.out.println("\n-- Registrar Libro --");
                    System.out.print("Tipo (1=Libro, 2=Ebook): ");
                    int tipo = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    System.out.print("Titulo: ");
                    String titulo = scanner.nextLine().trim();
                    System.out.print("Autor: ");
                    String autor = scanner.nextLine().trim();
                    System.out.print("Anio: ");
                    int anio = Integer.parseInt(scanner.nextLine().trim());
                    System.out.println("Categoria (1=CIENCIA 2=TECNOLOGIA 3=HISTORIA 4=LITERATURA 5=MATEMATICA 6=ARTE 7=OTRO): ");
                    int cat = Integer.parseInt(scanner.nextLine().trim());
                    Categoria categoria = Categoria.values()[cat - 1];

                    if (tipo == 1) {
                        System.out.print("Paginas: ");
                        int paginas = Integer.parseInt(scanner.nextLine().trim());
                        libroService.registrarLibro(new Libro(isbn, titulo, autor, anio, categoria, paginas));
                    } else {
                        System.out.print("Formato (PDF/EPUB): ");
                        String formato = scanner.nextLine().trim();
                        System.out.print("Tamanio MB: ");
                        double tamanio = Double.parseDouble(scanner.nextLine().trim());
                        libroService.registrarLibro(new Ebook(isbn, titulo, autor, anio, categoria, formato, tamanio));
                    }
                    System.out.println("Recurso registrado correctamente.");
                }
                case 2 -> {
                    System.out.println("\n-- Registrar Socio --");
                    System.out.print("DNI: ");
                    String dni = scanner.nextLine().trim();
                    System.out.print("Nombre: ");
                    String nombre = scanner.nextLine().trim();
                    System.out.print("Apellido: ");
                    String apellido = scanner.nextLine().trim();
                    System.out.print("Email: ");
                    String email = scanner.nextLine().trim();
                    System.out.print("Tipo (1=Estudiante, 2=Docente): ");
                    int tipo = Integer.parseInt(scanner.nextLine().trim());
                    try {
                        Socio socio = tipo == 1
                                ? new SocioEstudiante(dni, nombre, apellido, email)
                                : new SocioDocente(dni, nombre, apellido, email);
                        socioService.registrarSocio(socio);
                        System.out.println("Socio registrado correctamente.");
                    } catch (BibliotecaException | IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Buscar Recursos --");
                    System.out.println("1. Por titulo");
                    System.out.println("2. Por autor");
                    System.out.println("3. Por categoria");
                    System.out.println("4. Ver todos");
                    System.out.print("Opcion: ");
                    int subOpcion = Integer.parseInt(scanner.nextLine().trim());

                    List<Recurso> resultados = switch (subOpcion) {
                        case 1 -> {
                            System.out.print("Titulo: ");
                            yield libroService.buscarPorTitulo(scanner.nextLine().trim());
                        }
                        case 2 -> {
                            System.out.print("Autor: ");
                            yield libroService.buscarPorAutor(scanner.nextLine().trim());
                        }
                        case 3 -> {
                            System.out.println("Categoria (1=CIENCIA 2=TECNOLOGIA 3=HISTORIA 4=LITERATURA 5=MATEMATICA 6=ARTE 7=OTRO): ");
                            int cat = Integer.parseInt(scanner.nextLine().trim());
                            yield libroService.buscarPorCategoria(Categoria.values()[cat - 1]);
                        }
                        default -> libroService.buscarTodos();
                    };

                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron resultados.");
                    } else {
                        resultados.forEach(r -> System.out.println(
                                "- [" + r.isbn() + "] " + r.titulo() + " - " + r.autor() + " (" + r.categoria() + ")"
                        ));
                    }
                }
                case 4 -> {
                    System.out.println("\n-- Realizar Prestamo --");
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    System.out.print("DNI: ");
                    String dni = scanner.nextLine().trim();
                    try {
                        prestamoService.realizarPrestamo(isbn, dni);
                    } catch (BibliotecaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 5 -> {
                    System.out.println("\n-- Registrar Devolucion --");
                    System.out.print("ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    System.out.print("DNI: ");
                    String dni = scanner.nextLine().trim();
                    try {
                        prestamoService.registrarDevolucion(isbn, dni);
                    } catch (BibliotecaException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case 6 -> {
                    System.out.println("\n-- Historial de Prestamos --");
                    List<Prestamo> historial = prestamoService.obtenerHistorial();
                    if (historial.isEmpty()) {
                        System.out.println("No hay prestamos registrados.");
                    } else {
                        historial.forEach(p -> System.out.println(
                                "ISBN: " + p.isbn() + " | DNI: " + p.dniSocio() +
                                        " | Vence: " + p.fechaVencimiento() +
                                        " | Estado: " + (p.estaDevuelto()
                                        ? "Devuelto (retraso: " + p.calcularDiasRetraso() + " dias)"
                                        : "Activo")
                        ));
                    }
                }
                case 0 -> System.out.println("Hasta luego!");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}