package e1;

public class Habitacion {
    //ATRIBUTOS
    private final int num;
    private String huesped;
    private String limpiador;
    private String supervisor;
    private EstadoHabitacion estado = Aprobada.getInstancia();
    private String info;
    private String errores;

    //CONSTRUCTOR
    public Habitacion(int num, String supervisor){
        if(supervisor == null || supervisor.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.num=num;
        this.supervisor=supervisor;
        this.errores="Sin errores";
    }

    //GETTERS
    public EstadoHabitacion getEstado(){
        return this.estado;
    }
    public int getNum() { return num; }
    public String getHuesped() { return huesped; }
    public String getLimpiador() { return limpiador; }
    public String getSupervisor() { return supervisor; }
    public String getInformacion(){
        estado.getInfoEstado(this);
        return info;
    }
    public String getErrores(){
        return errores;
    }

    //SETTERS
    public void setEstado(EstadoHabitacion estado){
        this.estado=estado;
    }
    public void setHuesped(String huesped){
        if(huesped==null || huesped.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.huesped=huesped;
    }
    public void setLimpiador(String limpiador){
        if(limpiador==null || limpiador.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.limpiador=limpiador;
    }
    public void setSupervisor(String supervisor){
        if(supervisor==null || supervisor.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.supervisor=supervisor;
    }
    public void setInfo(String info){
        if(info==null || info.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.info=info;
    }
    public void setErrores(String errores){
        if(errores==null || errores.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }
        this.errores=errores;
    }

    //METODOS
    public void reservar(String huesped){
        errores="Sin errores";
        estado.reservarHabitacion(this, huesped);
    }
    public void terminarReserva(){
        errores="Sin errores";
        estado.terminarReserva(this);
    }
    public void liberar(){
        errores="Sin errores";
        estado.liberarHabitacion(this);
    }
    public void limpiar(String limpiador){
        errores="Sin errores";
        estado.limpiarHabitacion(this, limpiador);
    }
    public void aprobar(String supervisor, boolean veredicto){
        errores="Sin errores";
        estado.aprobarLimpieza(this, supervisor, veredicto);
    }
}