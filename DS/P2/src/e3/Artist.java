package e3;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

public record Artist(String id, Artist.Type type, int beginYear, int endYear, List<Puntuacion> puntuaciones, Set<Genero> generos) implements Comparable<Artist> {
    public enum Type {
        GRUPO,
        PERSONA
    }

    public enum Genero {
        POP,
        ROCK,
        METAL,
        CLASICO,
        HIPHOP,
        RAP,
        JAZZ,
        BLUES,
        SOUL,
        COUNTRY,
        DISCO,
        TECHNO,
        REGGAE,
        SALSA,
        FUNK
    }
    public record Puntuacion(double puntos) {
    }

    @Override
    public int compareTo(Artist o) {
        //Compara por ID usando el metodo compareTo de la clase String
        return this.id.compareTo(o.id);
    }

    public static class ComparadorPuntuacion implements Comparator<Artist> {
        //Compara por media de puntuaciones (mayor a menor)
        @Override
        public int compare(Artist o1, Artist o2) {
            double mediaO1 = 0;
            double mediaO2 = 0;

            for (Puntuacion pts : o1.puntuaciones) {
                mediaO1 += pts.puntos();
            }
            mediaO1 /= o1.puntuaciones.size();
            for (Puntuacion pts : o2.puntuaciones) {
                mediaO2 += pts.puntos();
            }
            mediaO2 /= o2.puntuaciones.size();

            //Compara usando el metodo compare de la clase Double
            return Double.compare(mediaO2, mediaO1);
        }
    }

    public static class ComparadorGeneros implements Comparator<Artist> {
        //Compara por generos distintos (mas a menos)
        @Override
        public int compare(Artist o1, Artist o2) {
            //Utilizo un set (Una lista que no permite elementos repetidos) para almacenar los
            //distintos generos y comparo su tamaño
            return Integer.compare(o2.generos.size(), o1.generos.size());
        }
    }

    public static class ComparadorInicio implements Comparator<Artist>{
        @Override
        public int compare(Artist o1, Artist o2) {
            return Integer.compare(o1.beginYear(), o2.beginYear());
        }
    }
    public static class ComparadorFinal implements Comparator<Artist>{
        @Override
        //Mas recientes primero (los que mas tarde terminaron)
        public int compare(Artist o1, Artist o2) {
            return Integer.compare(o2.endYear(), o1.endYear());
        }
    }

    public static class ComparadorTipo implements Comparator<Artist> {
        //Compara si el artista es un grupo o una persona (primero los grupos)
        @Override
        public int compare(Artist o1, Artist o2) {
            if(o1.type!=o2.type){
                //El metodo compareTo en enums funciona siguiendo el orden de declaracion de las constantes
                return o1.type.compareTo(o2.type);
            }
            else {
                //Si ambos artistas son del mismo tipo compara la antiguedad (mas antiguo primero)
                return Integer.compare(o1.beginYear, o2.beginYear);
            }
        }
    }
}