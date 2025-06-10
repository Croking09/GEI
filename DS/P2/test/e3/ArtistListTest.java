package e3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ArtistListTest {
    @Test
    public void testOrdenarID() {
        List<Artist> artistas = new ArrayList<>();
        artistas.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        artistas.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));

        ArtistList artistList = new ArtistList(artistas);
        artistList.ordenar(); //Usando ordenar() sin argumentos se ordena por ID usando la interfaz comparable<>

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));
        result.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));

        assertEquals(result, artistList.getArtistList());
    }

    @Test
    public void testOrdenarMedia(){
        List<Artist> artistas = new ArrayList<>();

        List<Artist.Puntuacion> a1 = new ArrayList<>();
        a1.add(new Artist.Puntuacion(10.0));
        a1.add(new Artist.Puntuacion(20.0));

        List<Artist.Puntuacion> a2 = new ArrayList<>();
        a2.add(new Artist.Puntuacion(5.0));
        a2.add(new Artist.Puntuacion(15.0));

        artistas.add(new Artist("1", Artist.Type.GRUPO, 1990, 2005, a2, new HashSet<>()));
        artistas.add(new Artist("2", Artist.Type.PERSONA, 2000, 2020, a1, new HashSet<>()));

        ArtistList artistList = new ArtistList(artistas);
        Artist.ComparadorPuntuacion comparador = new Artist.ComparadorPuntuacion();
        artistList.ordenar(comparador);

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("2", Artist.Type.PERSONA, 2000, 2020, a1, new HashSet<>()));
        result.add(new Artist("1", Artist.Type.GRUPO, 1990, 2005, a2, new HashSet<>()));

        assertEquals(result,artistList.getArtistList());
    }

    @Test
    public void testOrdenarGeneros(){
        List<Artist> artistas = new ArrayList<>();

        Set<Artist.Genero> a1 = new HashSet<>();
        a1.add(Artist.Genero.ROCK);
        a1.add(Artist.Genero.BLUES);
        a1.add(Artist.Genero.POP);

        Set<Artist.Genero> a2 = new HashSet<>();
        a2.add(Artist.Genero.FUNK);
        a2.add(Artist.Genero.CLASICO);

        artistas.add(new Artist("1", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), a2));
        artistas.add(new Artist("2", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), a1));

        ArtistList artistList = new ArtistList(artistas);
        Artist.ComparadorGeneros comparador = new Artist.ComparadorGeneros();
        artistList.ordenar(comparador);

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("2", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), a1));
        result.add(new Artist("1", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), a2));

        assertEquals(result,artistList.getArtistList());
    }

    @Test
    public void testOrdenarInicio() {
        List<Artist> artistas = new ArrayList<>();
        artistas.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        artistas.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));

        ArtistList artistList = new ArtistList(artistas);
        Artist.ComparadorInicio comparador = new Artist.ComparadorInicio();
        artistList.ordenar(comparador);

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        result.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));

        assertEquals(result, artistList.getArtistList());
    }

    @Test
    public void testOrdenarFinal() {
        List<Artist> artistas = new ArrayList<>();
        artistas.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        artistas.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));

        ArtistList artistList = new ArtistList(artistas);
        Artist.ComparadorFinal comparador = new Artist.ComparadorFinal();
        artistList.ordenar(comparador);

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));
        result.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));

        assertEquals(result, artistList.getArtistList());
    }

    @Test
    public void testOrdenarTipo() {
        List<Artist> artistas = new ArrayList<>();
        artistas.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        artistas.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));
        artistas.add(new Artist("3", Artist.Type.PERSONA, 2003, 2005, new ArrayList<>(), new HashSet<>()));

        ArtistList artistList = new ArtistList(artistas);
        Artist.ComparadorTipo comparador = new Artist.ComparadorTipo();
        artistList.ordenar(comparador);

        List<Artist> result = new ArrayList<>();
        result.add(new Artist("2", Artist.Type.GRUPO, 1990, 2005, new ArrayList<>(), new HashSet<>()));
        result.add(new Artist("1", Artist.Type.PERSONA, 2000, 2020, new ArrayList<>(), new HashSet<>()));
        result.add(new Artist("3", Artist.Type.PERSONA, 2003, 2005, new ArrayList<>(), new HashSet<>()));

        assertEquals(result, artistList.getArtistList());
    }

}