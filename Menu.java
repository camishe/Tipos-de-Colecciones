
package com.mycompany.tarea1u2;

/**
 *
 * @author Personal
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
     private Scanner scanner;

    public Menu(Scanner scanner) {
        this.scanner = scanner;
    }

    public int solicitarNumeroPersonas() {
        int numPersonas = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Ingrese el numero de personas a generar: ");
                numPersonas = scanner.nextInt();
                if (numPersonas <= 0) {
                    System.out.println("Por favor, ingrese un numero positivo.");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
            } finally {
                scanner.nextLine(); // Consumir el salt de línia residual
            }
        }
        return numPersonas;
    }

    public int mostrarMenuOrdenamientoYObtenerOpcion() {
        int opcion = 0;
        boolean entradaValida = false;
        System.out.println("\nSeleccione el metodo de ordenamiento:");
        System.out.println("1. Burbuja");
        System.out.println("2. Seleccion");
        System.out.println("3. Insercion");
        System.out.println("4. Shell Sort");
        System.out.println("5. MergeSort");
        System.out.println("6. QuickSort");
        System.out.println("7. No ordenar (continuar con busqueda sobre original)");

        while (!entradaValida) {
            try {
                System.out.print("Opcion: ");
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <= 7) {
                    entradaValida = true;
                } else {
                    System.out.println("Opcion no valida. Por favor, ingrese un numero entre 1 y 7.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
            } finally {
                scanner.nextLine(); // Consumir el salt de línia residual
            }
        }
        return opcion;
    }

    public int solicitarEdadParaBusqueda() {
        int edad = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Ingrese la EDAD a buscar: ");
                edad = scanner.nextInt();
                if (edad < 0) { // Edat no pot ser negativa
                    System.out.println("Por favor, ingrese una edad valida (numero no negativo).");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero para la edad.");
            } finally {
                scanner.nextLine(); // Consumir el salt de línia residual
            }
        }
        return edad;
    }

    public int solicitarIdParaBusqueda() {
        int id = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Ingrese el ID de la persona a buscar: ");
                id = scanner.nextInt();
                 if (id <= 0) { // IDs són positius
                    System.out.println("Por favor, ingrese un ID valido (numero entero positivo).");
                } else {
                    entradaValida = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero para el ID.");
            } finally {
                scanner.nextLine(); // Consumir el salt de línia residual
            }
        }
        return id;
    }

    public boolean preguntarDemostracionColecciones() {
        System.out.println("\n Desea ver la demostracion de ArrayList, HashSet y HashMap?");
        System.out.println("1. Si");
        System.out.println("2. No");
        
        int opcion = 0;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Opcion: ");
                opcion = scanner.nextInt();
                if (opcion == 1 || opcion == 2) {
                    entradaValida = true;
                } else {
                    System.out.println("Opcion no valida. Por favor, ingrese 1 o 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada no valida. Por favor, ingrese un numero entero.");
            } finally {
                scanner.nextLine(); // Consumir el salto de línea residual
            }
        }
        return opcion == 1;
    }

    public void mostrarMensajeDespedida() {
        System.out.println("\n Programa finalizado.");
    }
}
