
package com.mycompany.tarea1u2;

/**
 *
 * @author Personal
 */
public class Persona {
    private String nombre;
    private int edad;
    private int id;
    private static int contadorPersonas = 0; // Contador estático para generar IDs únicos
    
    
    public Persona( int id,String nombre, int edad){
        this.nombre = nombre;
        this.edad = edad;
        this.id = id;
    }
    
    public int getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public int getEdad(){
        return edad;
    }
    
    @Override
    public String toString(){
       return "Persona{" +
                "id: " + id +
                ",  '" + nombre + '\'' +
                ", " + edad +
                '}';
    }

    /**
     * Devuelve una representación en formato CSV de la Persona.
     * @return Una cadena en formato CSV (id,nombre,edad).
     */
    public String toCSVString() {
        return id + "," + nombre + "," + edad;
    }

    //Reinicia el contador de personas. Útil para pruebas o ejecuciones múltiples.
    public static void resetContador() {
        contadorPersonas = 0;
    }
}
