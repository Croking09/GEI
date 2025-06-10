package e3;

import java.util.ArrayList;

public class Release {
    private String ID;
    private String title;
    private String artist;
    private final ArrayList<Track> tracklist;

    public Release(String ID){
        this.ID=ID;
        this.tracklist=new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        String id;
        int cnt;

        if(o==null || getClass()!=o.getClass()) return false;
        if(this==o) return true;

        Release release = (Release) o;

        if(this.tracklist.size()!=release.tracklist.size()){
            return false;
        }

        for(int i=0;i<this.tracklist.size();i++){
            cnt=0;
            id=this.tracklist.get(i).ID();
            for(int j=0;j<release.tracklist.size();j++){
                if(release.tracklist.get(j).ID().equals(id)){
                    cnt++;
                }
            }
            if(cnt==0){
                return false;
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        int hash=0;

        for (Track track : this.tracklist) {
            hash += track.ID().hashCode();
        }

        return hash;
    }

    public void setID(String ID) {
        if(ID==null || ID.isEmpty()){
            throw new IllegalArgumentException("ID no valido");
        }
        this.ID = ID;
    }
    public void setTitle(String title){
        if(title==null || title.isEmpty()){
            throw new IllegalArgumentException("Titulo no valido");
        }
        this.title=title;
    }
    public void setArtist(String artist){
        if(artist==null || artist.isEmpty()){
            throw new IllegalArgumentException("Artista no valido");
        }
        this.artist=artist;
    }
    public void addTrack(Track pista){
        if(pista==null){
            throw new IllegalArgumentException("Pista no valida");
        }
        this.tracklist.add(pista);
    }
    public String getID(){return this.ID;}
    public String getTitle(){return this.title;}
    public String getArtist(){return this.artist;}
    public ArrayList<Track> getTracklist(){return this.tracklist;}

    @Override
    public String toString() {
        StringBuilder resultado=new StringBuilder();

        resultado.append("Release ");
        resultado.append(this.ID);
        resultado.append(": ");
        resultado.append(this.title);
        resultado.append(" by ");
        resultado.append(this.artist);
        resultado.append(".\nTracklist: [\n");
        for (Track track : this.tracklist) {
            resultado.append(track.nombre());
            resultado.append("\n");
        }
        resultado.append("]");

        return resultado.toString();
    }
}