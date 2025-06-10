
package com.mycompany.tarea1u2;

/**
 *@author Mishelle Nuñez
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;
import java.util.*;
import java.util.Scanner;

public class Tarea1U2 {

    public static void main(String[] args) {
         try (Scanner scanner = new Scanner(System.in)) {
            Menu menu = new Menu(scanner);
            Random random = new Random();

            int numPersonas = menu.solicitarNumeroPersonas();

            Persona.resetContador(); // Assegurar que els IDs comencin des d'1
            Persona[] personasOriginal = new Persona[numPersonas];

            // Generar objectos Persona aleatorias
            for (int i = 0; i < numPersonas; i++) {
                personasOriginal[i] = new Persona(i+1,"Persona " + (i + 1), random.nextInt(100) + 1); // Edats entre 1 i 100
            }

            // Mostrar les primeres persones generades
            System.out.println("\n--- Primeras 10 Personas Generadas (Antes de ordenar) ---");
            for (int i = 0; i < Math.min(10, personasOriginal.length); i++) {
                System.out.println(personasOriginal[i]);
            }
            if (personasOriginal.length > 10) System.out.println("...");
            System.out.println();

            // Guardar en CSV
            guardarPersonasCSV(personasOriginal, "personas_generadas.csv");

            // Seleccionar metodo de ordenamiento
            int opcionOrdenamiento = menu.mostrarMenuOrdenamientoYObtenerOpcion();

            Persona[] personasParaProcesar = Arrays.copyOf(personasOriginal, personasOriginal.length);
            String metodoSeleccionado = "N/A";

            if (opcionOrdenamiento >= 1 && opcionOrdenamiento <= 6) {
                long startTimeOrdenacion = System.nanoTime();
                switch (opcionOrdenamiento) {
                    case 1: metodoSeleccionado = "Burbuja"; MetodosArreglos.bubbleSort(personasParaProcesar); break;
                    case 2: metodoSeleccionado = "Seleccion"; MetodosArreglos.selectionSort(personasParaProcesar); break;
                    case 3: metodoSeleccionado = "Insercion"; MetodosArreglos.insertionSort(personasParaProcesar); break;
                    case 4: metodoSeleccionado = "Shell Sort"; MetodosArreglos.shellSort(personasParaProcesar); break;
                    case 5: metodoSeleccionado = "MergeSort"; MetodosArreglos.mergeSort(personasParaProcesar); break;
                    case 6: metodoSeleccionado = "QuickSort"; MetodosArreglos.quickSort(personasParaProcesar, 0, personasParaProcesar.length - 1); break;
                }
                long endTimeOrdenacion = System.nanoTime();
                long durationOrdenacion = (endTimeOrdenacion - startTimeOrdenacion);

                System.out.println("\n--- Ordenacion por " + metodoSeleccionado + " ---");
                System.out.println("Tiempo transcurrido: " + durationOrdenacion + " ns (" + (durationOrdenacion / 1_000_000.0) + " ms)");
                System.out.println("Primeros 10 elementos ordenados:");
                for (int i = 0; i < Math.min(10, personasParaProcesar.length); i++) {
                    System.out.println(personasParaProcesar[i]);
                }
                if (personasParaProcesar.length > 10) System.out.println("...");
                System.out.println();
                
                // Guardar en CSV despues de ordenar
                guardarPersonasCSV(personasParaProcesar, "personas_ordenadas_" + metodoSeleccionado.toLowerCase().replace(" ", "") + ".csv");

            } else if (opcionOrdenamiento == 7) {
                System.out.println("\nSe omite la ordenacion. Se usara el arreglo original para las busquedas.");
                metodoSeleccionado = "Original (sin ordenar)";
            } else { // Esta condición no debería suceder a causa de la validación en Menu
                System.out.println("\nOpcion de ordenamiento no valida. Se usara el arreglo original para las busquedas.");
                metodoSeleccionado = "Original (opcion invalida)";
            }

            // para busqueda binaria por edad (necesita estar ordenado por edad)
            Persona[] personasOrdenadasParaBusquedaBinariaEdad;
            if (opcionOrdenamiento >= 1 && opcionOrdenamiento <= 6) {
                // Si ya se ordenó por edad, se reutiliza el array procesado
                personasOrdenadasParaBusquedaBinariaEdad = personasParaProcesar;
            } else {
                // Si no, se crea una copia y se ordena específicamente para la búsqueda binaria
                System.out.println("\nPreparando arreglo para busqueda binaria por edad (ordenando una copia con QuickSort)...");
                personasOrdenadasParaBusquedaBinariaEdad = Arrays.copyOf(personasOriginal, personasOriginal.length);
                MetodosArreglos.quickSort(personasOrdenadasParaBusquedaBinariaEdad, 0, personasOrdenadasParaBusquedaBinariaEdad.length - 1);
            }

            System.out.println("\n--- Pruebas de Busqueda con Arreglos ---");

            // busqueda por edad
            int edadBuscada = menu.solicitarEdadParaBusqueda();

            System.out.println("\nBusqueda Lineal por Edad: Buscando personas con edad = " + edadBuscada + " (sobre arreglo " + metodoSeleccionado + ")");
            long startTimeLinealEdad = System.nanoTime();
            Persona[] resultadosLinealEdad = MetodosArreglos.busquedaLinealPorEdad(personasParaProcesar, edadBuscada);
            long endTimeLinealEdad = System.nanoTime();
            long durationLinealEdad = (endTimeLinealEdad - startTimeLinealEdad);

            if (resultadosLinealEdad.length == 0) {
                System.out.println("No se encontraron personas con edad " + edadBuscada);
            } else {
                System.out.println("Personas encontradas (" + resultadosLinealEdad.length + "):");
                for (Persona p : resultadosLinealEdad) System.out.println(p);
            }
            System.out.println("Tiempo de busqueda lineal por edad: " + durationLinealEdad + " ns (" + (durationLinealEdad / 1_000_000.0) + " ms)");

            System.out.println("\nBusqueda Binaria por Edad: Buscando la primera persona con edad = " + edadBuscada + " (en arreglo ordenado para busqueda)");
            long startTimeBinariaEdad = System.nanoTime();
            Persona resultadoBinariaEdad = MetodosArreglos.busquedaBinariaPorEdad(personasOrdenadasParaBusquedaBinariaEdad, edadBuscada);
            long endTimeBinariaEdad = System.nanoTime();
            long durationBinariaEdad = (endTimeBinariaEdad - startTimeBinariaEdad);

            if (resultadoBinariaEdad != null) {
                System.out.println("Primera persona encontrada: " + resultadoBinariaEdad);
            } else {
                System.out.println("No se encontro ninguna persona con edad " + edadBuscada);
            }
            System.out.println("Tiempo de busqueda binaria por edad: " + durationBinariaEdad + " ns (" + (durationBinariaEdad / 1_000_000.0) + " ms)");

            // Cerca per ID
            System.out.println("\n--- Busqueda por ID con Arreglos ---");
            int idBuscado = menu.solicitarIdParaBusqueda();

            System.out.println("\nBuscando persona con ID = " + idBuscado + " (sobre arreglo " + metodoSeleccionado + ")");
            long startTimeBusquedaId = System.nanoTime();
            // La búsqueda por ID se puede realizar sobre 'personas Para Procesar' ya que el ID no cambia con la ordenación por edad.
            Persona personaEncontradaPorId = MetodosArreglos.busquedaPorId(personasParaProcesar, idBuscado);
            long endTimeBusquedaId = System.nanoTime();
            long durationBusquedaId = (endTimeBusquedaId - startTimeBusquedaId);

            if (personaEncontradaPorId != null) {
                System.out.println("Persona encontrada: " + personaEncontradaPorId);
            } else {
                System.out.println("ID INEXISTENTE: No se encontro ninguna persona con ID " + idBuscado);
            }
            System.out.println("Tiempo de busqueda por ID: " + durationBusquedaId + " ns (" + (durationBusquedaId / 1_000_000.0) + " ms)");

            // NUEVA SECCIÓN: Demostración de Colecciones (ArrayList, HashSet, HashMap)
            if (menu.preguntarDemostracionColecciones()) {
                demonstrarColecciones(personasOriginal, personasParaProcesar, edadBuscada, idBuscado);
            }

            menu.mostrarMensajeDespedida();

        }
    }
    /**
     * Guarda un arreglo de objetos Persona en un archivo CSV.
     * @param personas El arreglo de Persona a guardar.
     * @param fileName El nombre del archivo CSV.
     */
    public static void guardarPersonasCSV(Persona[] personas, String fileName) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(fileName))) {
            pw.println("ID,Nombre,Edad"); // Encabezado del CSV
            for (Persona persona : personas) {
                pw.println(persona.toCSVString());
            }
            System.out.println("Datos de personas guardados en '" + fileName + "'");
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo CSV '" + fileName + "': " + e.getMessage());
        }
    }
    
    /**
     * Método que demuestra el uso de ArrayList, HashSet y HashMap
     */
    public static void demonstrarColecciones(Persona[] personasOriginal, Persona[] personasProcesadas, int edadBuscada, int idBuscado) {
        System.out.println("\n==========================================");
        System.out.println("    DEMOSTRACION DE COLECCIONES JAVA");
        System.out.println("==========================================");
        
        // 1. ARRAYLIST
        System.out.println("\n--- 1. ARRAYLIST ---");
        System.out.println("Convirtiendo arreglo a ArrayList...");
        
        long start = System.nanoTime();
        ArrayList<Persona> arrayListPersonas = ManejoColecciones.convertirArregloAArrayList(personasOriginal);
        long tiempoConversion = System.nanoTime() - start;
        
        System.out.println("ArrayList creado con " + arrayListPersonas.size() + " elementos");
        System.out.println("Tiempo de conversión: " + tiempoConversion + " ns");
        
        // Ordenar ArrayList
        System.out.println("\nOrdenando ArrayList por edad usando Collections.sort()...");
        start = System.nanoTime();
        ManejoColecciones.ordenarArrayListPorEdad(arrayListPersonas);
        long tiempoOrdenArrayList = System.nanoTime() - start;
        System.out.println("Tiempo de ordenacion: " + tiempoOrdenArrayList + " ns (" + (tiempoOrdenArrayList / 1_000_000.0) + " ms)");
        
        System.out.println("Primeros 5 elementos del ArrayList ordenado:");
        for (int i = 0; i < Math.min(5, arrayListPersonas.size()); i++) {
            System.out.println("  " + arrayListPersonas.get(i));
        }
        
        // 2. HASHSET
        System.out.println("\n--- 2. HASHSET ---");
        System.out.println("Creando HashSet de edades unicas...");
        
        start = System.nanoTime();
        HashSet<Integer> edadesUnicas = ManejoColecciones.obtenerEdadesUnicas(arrayListPersonas);
        long tiempoHashSet = System.nanoTime() - start;
        
        System.out.println("HashSet creado con " + edadesUnicas.size() + " edades unicas");
        System.out.println("Tiempo de creacion: " + tiempoHashSet + " ns");
        
        // Mostrar algunas edades únicas (ordenadas para visualizar mejor)
        System.out.println("Primeras 10 edades unicas encontradas:");
        edadesUnicas.stream()
                .sorted()
                .limit(10)
                .forEach(edad -> System.out.print(edad + " "));
        System.out.println();
        
        // Verificar si existe una edad
        boolean existeEdad = ManejoColecciones.existeEdadEnSet(edadesUnicas, edadBuscada);
        System.out.println("¿Existe la edad " + edadBuscada + " en el conjunto? " + (existeEdad ? "SI" : "NO"));
        
        // 3. HASHMAP
        System.out.println("\n--- 3. HASHMAP ---");
        System.out.println("Creando HashMap para agrupar personas por edad...");
        
        start = System.nanoTime();
        HashMap<Integer, List<Persona>> personasPorEdad = ManejoColecciones.agruparPersonasPorEdad(arrayListPersonas);
        long tiempoHashMapAgrupacion = System.nanoTime() - start;
        
        System.out.println("HashMap creado con " + personasPorEdad.size() + " grupos de edad");
        System.out.println("Tiempo de creacion: " + tiempoHashMapAgrupacion + " ns");
        
        // Crear HashMap de ID -> Persona para búsquedas rápidas
        System.out.println("\nCreando HashMap de ID -> Persona para busquedas rapidas...");
        start = System.nanoTime();
        HashMap<Integer, Persona> mapaIdPersona = ManejoColecciones.crearMapaIdPersona(arrayListPersonas);
        long tiempoHashMapId = System.nanoTime() - start;
        System.out.println("Tiempo de creacion del mapa ID: " + tiempoHashMapId + " ns");
        
        // 4. ESTADÍSTICAS
        ManejoColecciones.mostrarEstadisticasColecciones(arrayListPersonas, edadesUnicas, personasPorEdad);
        
        // 5. BÚSQUEDAS CON COLECCIONES
        System.out.println("\n--- BUSQUEDAS CON COLECCIONES ---");
        
        // Búsqueda por edad usando HashMap
        System.out.println("\nBusqueda por edad " + edadBuscada + " usando HashMap:");
        start = System.nanoTime();
        List<Persona> resultadosHashMap = ManejoColecciones.buscarPorEdadEnHashMap(personasPorEdad, edadBuscada);
        long tiempoBusquedaEdadHashMap = System.nanoTime() - start;
        
        System.out.println("Personas encontradas: " + resultadosHashMap.size());
        System.out.println("Tiempo de busqueda: " + tiempoBusquedaEdadHashMap + " ns");
        if (!resultadosHashMap.isEmpty()) {
            System.out.println("Primeras personas encontradas:");
            resultadosHashMap.stream().limit(3).forEach(p -> System.out.println("  " + p));
        }
        
        // Búsqueda por ID usando HashMap
        System.out.println("\nBusqueda por ID " + idBuscado + " usando HashMap:");
        start = System.nanoTime();
        Persona personaEncontrada = ManejoColecciones.buscarPorIdEnHashMap(mapaIdPersona, idBuscado);
        long tiempoBusquedaIdHashMap = System.nanoTime() - start;
        
        if (personaEncontrada != null) {
            System.out.println("Persona encontrada: " + personaEncontrada);
        } else {
            System.out.println("ID no encontrado");
        }
        System.out.println("Tiempo de busqueda: " + tiempoBusquedaIdHashMap + " ns");
        
        // 6. BENCHMARK COMPARATIVO
        ManejoColecciones.benchmarkBusquedas(personasOriginal, arrayListPersonas, mapaIdPersona, 
                                              personasPorEdad, idBuscado, edadBuscada);
        
        // 7. GUARDAR RESULTADOS DE COLECCIONES
        guardarResultadosColecciones(arrayListPersonas, edadesUnicas, personasPorEdad);
        
        System.out.println("\n==========================================");
        System.out.println("   FIN DEMOSTRACION DE COLECCIONES");
        System.out.println("==========================================");
    }
    
    /**
     * Guarda los resultados de las colecciones en archivos CSV
     */
    public static void guardarResultadosColecciones(ArrayList<Persona> arrayList, 
                                                   HashSet<Integer> edadesUnicas, 
                                                   HashMap<Integer, List<Persona>> personasPorEdad) {
        
        // Guardar ArrayList ordenado
        try (PrintWriter pw = new PrintWriter(new FileWriter("personas_arraylist_ordenado.csv"))) {
            pw.println("ID, Nombre, Edad");
            for (Persona persona : arrayList) {
                pw.println(persona.toCSVString());
            }
            System.out.println("\nArrayList guardado en 'personas_arraylist_ordenado.csv'");
        } catch (IOException e) {
            System.err.println("Error al guardar ArrayList: " + e.getMessage());
        }
        
        // Guardar edades únicas
        try (PrintWriter pw = new PrintWriter(new FileWriter("edades_unicas.csv"))) {
            pw.println("Edad");
            edadesUnicas.stream()
                    .sorted()
                    .forEach(pw::println);
            System.out.println("Edades unicas guardadas en 'edades_unicas.csv'");
        } catch (IOException e) {
            System.err.println("Error al guardar edades unicas: " + e.getMessage());
        }
        
        // Guardar estadísticas de agrupación por edad
        try (PrintWriter pw = new PrintWriter(new FileWriter("estadisticas_edades.csv"))) {
            pw.println("Edad, CantidadPersonas, PrimeraPersona");
            personasPorEdad.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> {
                        int edad = entry.getKey();
                        List<Persona> personas = entry.getValue();
                        String primeraPersona = personas.isEmpty() ? "N/A" : personas.get(0).getNombre();
                        pw.println(edad + "," + personas.size() + "," + primeraPersona);
                    });
            System.out.println("Estadisticas de edades guardadas en 'estadisticas_edades.csv'");
        } catch (IOException e) {
            System.err.println("Error al guardar estadi5sticas de edades: " + e.getMessage());
        }
}    
}