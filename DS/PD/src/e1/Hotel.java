package e1;

import java.util.ArrayList;
import java.util.List;

public class Hotel {
    //ATRIBUTOS
    private final String nombre;
    private final List<Habitacion> habitaciones;
    private String errores;

    //CONSTRUCTOR
    public Hotel(String nombre) {
        if(nombre==null || nombre.isEmpty()){
            throw new IllegalArgumentException("Nombre no valido");
        }
        this.nombre=nombre;
        this.habitaciones=new ArrayList<>();
        this.errores="Sin errores";
    }

    //GETTTERS
    //Se utiliza sobrecarga para poder obtener o bien los errores de cada habitacion o bien el error de no encontrado de la clase "Hotel"
    public String getErrores(){
        return errores;
    }
    public String getErrores(int num){
        boolean correcto=false;

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                correcto=true;
                errores=h.getErrores();
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada";
        }

        return errores;
    }

    //FUNCIONES ENUNCIADO
    public void addHab(String supervisor){
        Habitacion hab = new Habitacion(this.habitaciones.size()+1,supervisor);

        this.habitaciones.add(hab);
    }
    public void reservarHab(int num, String huesped){
        boolean correcto=false;

        if(huesped==null || huesped.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                correcto=true;
                errores="Sin errores";
                h.reservar(huesped);
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada";
        }
    }
    public void terminarReserva(int num){
        boolean correcto=false;

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                correcto=true;
                errores="Sin errores";
                h.terminarReserva();
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada";
        }
    }
    public void liberarHab(int num){
        boolean correcto=false;

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                correcto=true;
                errores="Sin errores";
                h.liberar();
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada";
        }
    }
    public void limpiarHab(int num, String limpiador){
        boolean correcto=false;

        if(limpiador==null || limpiador.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                correcto=true;
                errores="Sin errores";
                h.limpiar(limpiador);
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada";
        }
    }
    public void aprobarLimp(int num, String supervisor, boolean veredicto){
        boolean correcto=false;

        if(supervisor==null || supervisor.isEmpty()){
            throw new IllegalArgumentException("Argumento no valido");
        }

        for(Habitacion h : habitaciones){
            if(h.getNum()==num){
                errores="Sin errores";
                correcto=true;
                h.aprobar(supervisor, veredicto);
            }
        }

        if(!correcto){
            errores="Habitacion no encontrada - aprobar";
        }
    }
    public List<Habitacion> getHabDisponibles(){
        List<Habitacion> result = new ArrayList<>();

        for(Habitacion h : habitaciones){
            if(h.getEstado()==Aprobada.getInstancia()){
                result.add(h);
            }
        }

        return result;
    }
    public List<Habitacion> getHabPendLimpieza(){
        List<Habitacion> result = new ArrayList<>();

        for(Habitacion h : habitaciones){
            if(h.getEstado()==PendienteDeLimpieza.getInstancia()){
                result.add(h);
            }
        }

        return result;
    }
    public List<Habitacion> getHabPendAprobacion(){
        List<Habitacion> result = new ArrayList<>();

        for(Habitacion h : habitaciones){
            if(h.getEstado()==PendienteAprobacion.getInstancia()){
                result.add(h);
            }
        }

        return result;
    }
    public String getHabInformacion(){
        StringBuilder info = new StringBuilder();

        info.append("***********************\nHotel ");
        info.append(this.nombre);
        info.append("\n***********************\n");
        for(Habitacion h : habitaciones){
            info.append(h.getInformacion());
            info.append("\n");
        }
        info.append("***********************");

        return info.toString();
    }
}