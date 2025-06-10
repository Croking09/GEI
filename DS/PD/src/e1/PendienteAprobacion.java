package e1;

public class PendienteAprobacion extends EstadoHabitacion{
    private static final PendienteAprobacion instancia = new PendienteAprobacion();
    private PendienteAprobacion(){};
    public static PendienteAprobacion getInstancia(){
        return instancia;
    }

    @Override
    public void getInfoEstado(Habitacion hab){

        String info = "Room no. " +
                hab.getNum() +
                ": Free. Room cleaned by " +
                hab.getLimpiador() +
                ", pending approval.";

        hab.setInfo(info);
    }

    @Override
    public void aprobarLimpieza(Habitacion hab, String supervisor, boolean aprobar) {
        hab.setSupervisor(supervisor);
        if(aprobar) {
            hab.setEstado(Aprobada.getInstancia());
        }
        else{
            hab.setEstado(PendienteDeLimpieza.getInstancia());
        }
    }
}