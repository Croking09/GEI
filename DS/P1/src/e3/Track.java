package e3;

public record Track(String cara, String ID, String artista, String nombre, int duracion) {
    @Override
    public boolean equals(Object o) {
        String id=this.getID();

        if(o==null || getClass()!=o.getClass()) return false;
        if(this==o) return true;

        Track Track = (Track) o;

        return Track.getID().equals(id);

    }
    @Override
    public int hashCode() {
        return this.ID().hashCode();
    }

    public String getCara(){return this.cara;}
    public String getID(){return this.ID;}
    public String getName(){return this.nombre;}
    public String getArtist(){return this.artista;}
    public int getDuration(){return this.duracion;}

    @Override
    public String toString() {

        return "Record " +
                this.ID +
                " Track " +
                this.cara +
                ": " +
                this.nombre +
                " by " +
                this.artista +
                " " +
                this.duracion;
    }
}