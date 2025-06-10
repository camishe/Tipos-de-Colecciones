
package com.mycompany.tarea1u2;

/**
 * @author Personal
 */

//Contiene métodos estáticos para ordenar y buscar en arreglos de objetos Persona.
import java.util.Arrays;

public class MetodosArreglos {    
    
    //Ordena un arreglo de objetos Persona por edad usando el algoritmo de Burbuja.
    public static void bubbleSort(Persona[] personas) {
        int n = personas.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (personas[j].getEdad() > personas[j + 1].getEdad()) {
                    // Intercambiar personas[j] y personas[j+1]
                    Persona temp = personas[j];
                    personas[j] = personas[j + 1];
                    personas[j + 1] = temp;
                }
            }
        }
    }

    //Ordena un arreglo de objetos Persona por edad usando el algoritmo de Selección.
    public static void selectionSort(Persona[] personas) {
        int n = personas.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (personas[j].getEdad() < personas[minIndex].getEdad()) {
                    minIndex = j;
                }
            }
            // Intercambiar personas[i] y personas[minIndex]
            Persona temp = personas[i];
            personas[i] = personas[minIndex];
            personas[minIndex] = temp;
        }
    }

    //Ordena un arreglo de objetos Persona por edad usando el algoritmo de Inserción.
    public static void insertionSort(Persona[] personas) {
        int n = personas.length;
        for (int i = 1; i < n; ++i) {
            Persona key = personas[i];
            int j = i - 1;

            while (j >= 0 && personas[j].getEdad() > key.getEdad()) {
                personas[j + 1] = personas[j];
                j = j - 1;
            }
            personas[j + 1] = key;
        }
    }

    //Ordena un arreglo de objetos Persona por edad usando el algoritmo de Shell Sort.
    public static void shellSort(Persona[] personas) {
        int n = personas.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i += 1) {
                Persona temp = personas[i];
                int j;
                for (j = i; j >= gap && personas[j - gap].getEdad() > temp.getEdad(); j -= gap) {
                    personas[j] = personas[j - gap];
                }
                personas[j] = temp;
            }
        }
    }

    //Ordena un arreglo de objetos Persona por edad usando el algoritmo de MergeSort.
    public static void mergeSort(Persona[] personas) {
        if (personas.length < 2) {
            return;
        }
        int mid = personas.length / 2;
        Persona[] left = Arrays.copyOfRange(personas, 0, mid);
        Persona[] right = Arrays.copyOfRange(personas, mid, personas.length);

        mergeSort(left);
        mergeSort(right);

        merge(personas, left, right);
    }

    //Método auxiliar para MergeSort.
    private static void merge(Persona[] personas, Persona[] left, Persona[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i].getEdad() <= right[j].getEdad()) {
                personas[k++] = left[i++];
            } else {
                personas[k++] = right[j++];
            }
        }
        while (i < left.length) {
            personas[k++] = left[i++];
        }
        while (j < right.length) {
            personas[k++] = right[j++];
        }
    }

    /**
     * Ordena un arreglo de objetos Persona por edad usando el algoritmo de QuickSort.
     * @param personas El arreglo de Persona a ordenar.
     * @param low El índice inicial del sub-arreglo.
     * @param high El índice final del sub-arreglo.
     */
    public static void quickSort(Persona[] personas, int low, int high) {
        if (low < high) {
            int pi = partition(personas, low, high);
            quickSort(personas, low, pi - 1);
            quickSort(personas, pi + 1, high);
        }
    }

    //Método auxiliar para QuickSort.
    private static int partition(Persona[] personas, int low, int high) {
        Persona pivot = personas[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (personas[j].getEdad() <= pivot.getEdad()) {
                i++;
                Persona temp = personas[i];
                personas[i] = personas[j];
                personas[j] = temp;
            }
        }
        Persona temp = personas[i + 1];
        personas[i + 1] = personas[high];
        personas[high] = temp;

        return i + 1;
    }


    // --- Métodos de Búsqueda ---

    /**
     * Realiza una búsqueda lineal en un arreglo de Persona para encontrar todas las personas con una edad dada.
     * @param personas El arreglo de Persona donde buscar.
     * @param edadBuscada La edad a buscar.
     * @return Un nuevo arreglo que contiene todas las personas encontradas con la edad especificada.
     */
    public static Persona[] busquedaLinealPorEdad(Persona[] personas, int edadBuscada) {
        Persona[] tempResultados = new Persona[personas.length];
        int contador = 0;
        for (Persona persona : personas) {
            if (persona.getEdad() == edadBuscada) {
                tempResultados[contador++] = persona;
            }
        }
        return Arrays.copyOf(tempResultados, contador);
    }

    /**
     * Realiza una búsqueda binaria en un arreglo de Persona ordenado para encontrar la primera persona con una edad dada.
     * PRECONDICIÓN: El arreglo 'personas' DEBE estar ordenado por edad.
     * @param personas El arreglo de Persona (debe estar ordenado por edad).
     * @param edadBuscada La edad a buscar.
     * @return La primera Persona encontrada con la edad especificada, o null si no se encuentra.
     */
    public static Persona busquedaBinariaPorEdad(Persona[] personas, int edadBuscada) {
        int low = 0;
        int high = personas.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (personas[mid].getEdad() == edadBuscada) {
                return personas[mid];
            } else if (personas[mid].getEdad() < edadBuscada) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return null; // No encontrado
    }

    /**
     * Realiza una búsqueda lineal en un arreglo de Persona para encontrar una persona por su ID.
     * Los IDs son únicos, por lo que devuelve la primera y única coincidencia.
     * @param personas El arreglo de Persona donde buscar.
     * @param idBuscado El ID a buscar.
     * @return La Persona encontrada con el ID especificado, o null si no se encuentra.
     */
    public static Persona busquedaPorId(Persona[] personas, int idBuscado) {
        for (Persona persona : personas) {
            if (persona.getId() == idBuscado) {
                return persona; // Devuelve la persona tan pronto como se encuentra
            }
        }
        return null; 
    }
}