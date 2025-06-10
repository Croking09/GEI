package e1;

public class PendienteDeLimpieza extends EstadoHabitacion{
    private static final PendienteDeLimpieza instancia = new PendienteDeLimpieza();
    private PendienteDeLimpieza(){};
    public static PendienteDeLimpieza getInstancia(){
        return instancia;
    }
    @Override
    public void getInfoEstado(Habitacion hab){

        String info = "Room no. " +
                hab.getNum() +
                ": Free. Cleaning pending.";
        hab.setInfo(info);
    }

    @Override
    public void limpiarHabitacion(Habitacion hab, String limpiador) {
        hab.setLimpiador(limpiador);
        hab.setEstado(PendienteAprobacion.getInstancia());
    }
}