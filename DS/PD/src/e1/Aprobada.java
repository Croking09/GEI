package e1;

public class Aprobada extends EstadoHabitacion{
    private static final Aprobada instancia = new Aprobada();
    private Aprobada(){};
    public static Aprobada getInstancia(){
        return instancia;
    }
    @Override
    public void getInfoEstado(Habitacion hab){

        String info = "Room no. " +
                hab.getNum() +
                ": Free. This room was approved by " +
                hab.getSupervisor() +
                ".";

        hab.setInfo(info);
    }
    @Override
    public void reservarHabitacion(Habitacion hab, String huesped) {
        hab.setHuesped(huesped);
        hab.setEstado(Reservada.getInstancia());
    }

    @Override
    public void aprobarLimpieza(Habitacion hab, String supervisor, boolean aprobar) {
        hab.setSupervisor(supervisor);
        if(!aprobar){
            hab.setEstado(PendienteDeLimpieza.getInstancia());
        }
        else{
            hab.setErrores("No puedes aprobar una habitacion ya aprobada");
        }
    }
}