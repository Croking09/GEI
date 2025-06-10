package e1;

public abstract class EstadoHabitacion {
    public void getInfoEstado(Habitacion hab){
        hab.setInfo("Room no. " + hab.getNum() + ":");
    }
    public void reservarHabitacion(Habitacion hab, String huesped){
        hab.setErrores("Habitacion no disponible");
    }
    public void terminarReserva(Habitacion hab){
        hab.setErrores("Habitacion no reservada");
    }
    public void limpiarHabitacion(Habitacion hab, String limpiador){
        hab.setErrores("Habitacion ocupada o ya aprobada");
    }
    public void liberarHabitacion(Habitacion hab){
        hab.setErrores("Habitacion no ocupada");
    }
    public void aprobarLimpieza(Habitacion hab, String supervisor, boolean aprobar){
        hab.setErrores("Habitacion ocupada o no limpiada");
    }
}