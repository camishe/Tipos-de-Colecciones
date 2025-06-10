
package com.mycompany.tarea1u2;

/**
 * @author Mishelle Nuñez
 */

//Clase que maneja operaciones con ArrayList, HashSet y HashMap

import java.util.*;
import java.util.stream.Collectors;

public class ManejoColecciones {
    /**
     * Convierte un arreglo de Persona a ArrayList
     */
    public static ArrayList<Persona> convertirArregloAArrayList(Persona[] personas) {
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        Collections.addAll(listaPersonas, personas);
        return listaPersonas;
    }
    
    /**
     * Ordena un ArrayList de Persona por edad usando Collections.sort()
     */
    public static void ordenarArrayListPorEdad(ArrayList<Persona> personas) {
        Collections.sort(personas, Comparator.comparingInt(Persona::getEdad));
    }
    
    /**
     * Busca personas por edad en ArrayList usando streams
     */
    public static List<Persona> buscarPorEdadEnArrayList(ArrayList<Persona> personas, int edad) {
        return personas.stream()
                .filter(p -> p.getEdad() == edad)
                .collect(Collectors.toList());
    }
    
    /**
     * Busca persona por ID en ArrayList
     */
    public static Persona buscarPorIdEnArrayList(ArrayList<Persona> personas, int id) {
        return personas.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Crea un HashSet de edades únicas desde un ArrayList de Persona
     */
    public static HashSet<Integer> obtenerEdadesUnicas(ArrayList<Persona> personas) {
        HashSet<Integer> edadesUnicas = new HashSet<>();
        for (Persona persona : personas) {
            edadesUnicas.add(persona.getEdad());
        }
        return edadesUnicas;
    }
    
    /**
     * Verifica si existe una edad específica en el HashSet
     */
    public static boolean existeEdadEnSet(HashSet<Integer> edades, int edad) {
        return edades.contains(edad);
    }
    
    /**
     * Crea un HashMap que agrupa personas por edad
     */
    public static HashMap<Integer, List<Persona>> agruparPersonasPorEdad(ArrayList<Persona> personas) {
        HashMap<Integer, List<Persona>> personasPorEdad = new HashMap<>();
        
        for (Persona persona : personas) {
            int edad = persona.getEdad();
            personasPorEdad.computeIfAbsent(edad, k -> new ArrayList<>()).add(persona);
        }
        
        return personasPorEdad;
    }
    
    /**
     * Busca personas por edad usando HashMap (más eficiente para múltiples búsquedas)
     */
    public static List<Persona> buscarPorEdadEnHashMap(HashMap<Integer, List<Persona>> personasPorEdad, int edad) {
        return personasPorEdad.getOrDefault(edad, new ArrayList<>());
    }
    
    /**
     * Crea un HashMap que mapea ID -> Persona para búsquedas rápidas por ID
     */
    public static HashMap<Integer, Persona> crearMapaIdPersona(ArrayList<Persona> personas) {
        HashMap<Integer, Persona> mapaIdPersona = new HashMap<>();
        for (Persona persona : personas) {
            mapaIdPersona.put(persona.getId(), persona);
        }
        return mapaIdPersona;
    }
    
    /**
     * Busca persona por ID usando HashMap (O(1) en promedio)
     */
    public static Persona buscarPorIdEnHashMap(HashMap<Integer, Persona> mapaIdPersona, int id) {
        return mapaIdPersona.get(id);
    }
    
    /**
     * Muestra estadísticas de las colecciones
     */
    public static void mostrarEstadisticasColecciones(ArrayList<Persona> personas, 
                                                    HashSet<Integer> edadesUnicas, 
                                                    HashMap<Integer, List<Persona>> personasPorEdad) {
        System.out.println("\n--- Estadisticas de Colecciones ---");
        System.out.println("ArrayList - Total de personas: " + personas.size());
        System.out.println("HashSet - Edades unicas: " + edadesUnicas.size());
        System.out.println("HashMap - Grupos de edad: " + personasPorEdad.size());
        
        // Mostrar distribución de edades
        System.out.println("\nDistribucion de edades (primeras 10):");
        personasPorEdad.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .limit(10)
                .forEach(entry -> 
                    System.out.println("Edad " + entry.getKey() + ": " + entry.getValue().size() + " personas"));
        
        if (personasPorEdad.size() > 10) {
            System.out.println("...");
        }
    }
    
    /**
     * Realiza benchmark de búsquedas comparando diferentes estructuras
     */
    public static void benchmarkBusquedas(Persona[] arregloOriginal, 
                                        ArrayList<Persona> arrayList, 
                                        HashMap<Integer, Persona> mapaIdPersona,
                                        HashMap<Integer, List<Persona>> personasPorEdad,
                                        int idBuscado, int edadBuscada) {
        
        System.out.println("\n--- Benchmark de Busquedas ---");
        
        // Búsqueda por ID en Arreglo
        long start = System.nanoTime();
        Persona resultadoArreglo = MetodosArreglos.busquedaPorId(arregloOriginal, idBuscado);
        long tiempoArreglo = System.nanoTime() - start;
        
        // Búsqueda por ID en ArrayList
        start = System.nanoTime();
        Persona resultadoArrayList = buscarPorIdEnArrayList(arrayList, idBuscado);
        long tiempoArrayList = System.nanoTime() - start;
        
        // Búsqueda por ID en HashMap
        start = System.nanoTime();
        Persona resultadoHashMap = buscarPorIdEnHashMap(mapaIdPersona, idBuscado);
        long tiempoHashMap = System.nanoTime() - start;
        
        System.out.println("Búsqueda por ID = " + idBuscado + ":");
        System.out.println("  Arreglo:   " + tiempoArreglo + " ns (" + (tiempoArreglo / 1_000_000.0) + " ms)");
        System.out.println("  ArrayList: " + tiempoArrayList + " ns (" + (tiempoArrayList / 1_000_000.0) + " ms)");
        System.out.println("  HashMap:   " + tiempoHashMap + " ns (" + (tiempoHashMap / 1_000_000.0) + " ms)");
        
        // Búsqueda por edad
        start = System.nanoTime();
        Persona[] resultadoEdadArreglo = MetodosArreglos.busquedaLinealPorEdad(arregloOriginal, edadBuscada);
        long tiempoEdadArreglo = System.nanoTime() - start;
        
        start = System.nanoTime();
        List<Persona> resultadoEdadArrayList = buscarPorEdadEnArrayList(arrayList, edadBuscada);
        long tiempoEdadArrayList = System.nanoTime() - start;
        
        start = System.nanoTime();
        List<Persona> resultadoEdadHashMap = buscarPorEdadEnHashMap(personasPorEdad, edadBuscada);
        long tiempoEdadHashMap = System.nanoTime() - start;
        
        System.out.println("\nBusqueda por Edad = " + edadBuscada + ":");
        System.out.println("  Arreglo:   " + tiempoEdadArreglo + " ns (" + (tiempoEdadArreglo / 1_000_000.0) + " ms)");
        System.out.println("  ArrayList: " + tiempoEdadArrayList + " ns (" + (tiempoEdadArrayList / 1_000_000.0) + " ms)");
        System.out.println("  HashMap:   " + tiempoEdadHashMap + " ns (" + (tiempoEdadHashMap / 1_000_000.0) + " ms)");
        
        System.out.println("\nResultados encontrados:");
        System.out.println("  Arreglo: " + resultadoEdadArreglo.length + " personas");
        System.out.println("  ArrayList: " + resultadoEdadArrayList.size() + " personas");
        System.out.println("  HashMap: " + resultadoEdadHashMap.size() + " personas");
    }
}