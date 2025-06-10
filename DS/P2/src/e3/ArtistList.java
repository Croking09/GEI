package e3;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ArtistList{
    private final List<Artist> ArtistList;
    public ArtistList(List<Artist> artistList) {
        ArtistList = artistList;
    }
    public List<Artist> getArtistList() {
        return ArtistList;
    }

    //Utilizo sobrecarga de metodos para tener un metodo ordenar() que admita tanto
    //la ordenacion con comparable<> como comparator<>
    public void ordenar(){
        Collections.sort(this.ArtistList);
    }
    public void ordenar(Comparator<Artist> comparator){
        Collections.sort(this.ArtistList,comparator);
    }
}