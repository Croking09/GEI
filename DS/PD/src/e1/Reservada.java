package e1;

public class Reservada extends EstadoHabitacion{
    private static final Reservada instancia = new Reservada();
    private Reservada(){};
    public static Reservada getInstancia(){
        return instancia;
    }
    @Override
    public void getInfoEstado(Habitacion hab){

        String info = "Room no. " +
                hab.getNum() +
                ": Booked by " +
                hab.getHuesped() +
                "." +
                " Occupied.";

        hab.setInfo(info);
    }
    @Override
    public void terminarReserva(Habitacion hab) {
        hab.setEstado(Aprobada.getInstancia());
    }
    @Override
    public void liberarHabitacion(Habitacion hab) {
        hab.setEstado(PendienteDeLimpieza.getInstancia());
    }
}