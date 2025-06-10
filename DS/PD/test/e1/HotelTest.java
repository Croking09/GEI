package e1;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HotelTest {
    private static Hotel getHotel() {
        Hotel h1 = new Hotel("UDC-Hills");

        h1.addHab("Juanito"); //Habitacion aprobada

        h1.addHab("Pepito");
        h1.reservarHab(2, "Flora"); //Habitacion reservada

        h1.addHab("Menganito");
        h1.reservarHab(3, "Juan");
        h1.terminarReserva(3); //Habitacion aprobada despues de reserva

        h1.addHab("Susana");
        h1.reservarHab(4, "Francisco");
        h1.liberarHab(4); //Habitacion pendiente de limpieza

        h1.addHab("Manuel");
        h1.reservarHab(5, "Manuela");
        h1.liberarHab(5);
        h1.limpiarHab(5, "Ramon"); //Habitacion pendiente de aprobacion

        h1.addHab("Bosco");
        h1.reservarHab(6, "Paula");
        h1.liberarHab(6);
        h1.limpiarHab(6, "Belen");
        h1.aprobarLimp(6, "Joselito", true); //Habitacion aprobada despues de limpieza

        h1.addHab("Jesus");
        h1.reservarHab(7, "Navarrosa");
        h1.liberarHab(7);
        h1.limpiarHab(7, "Ibai");
        h1.aprobarLimp(7, "Pique", false); //Habitacion pendiente de limpieza por aprobacion

        return h1;
    }
    @Test
    void TestInfoHabitaciones(){
        Hotel h1 = getHotel();

        String prueba = h1.getHabInformacion();
        String info = """
                ***********************
                Hotel UDC-Hills
                ***********************
                Room no. 1: Free. This room was approved by Juanito.
                Room no. 2: Booked by Flora. Occupied.
                Room no. 3: Free. This room was approved by Menganito.
                Room no. 4: Free. Cleaning pending.
                Room no. 5: Free. Room cleaned by Ramon, pending approval.
                Room no. 6: Free. This room was approved by Joselito.
                Room no. 7: Free. Cleaning pending.
                ***********************""";
        assertEquals(info,prueba);
    }

    @Test
    void TestListas(){
        Hotel h1 = getHotel();

        boolean test=true;
        List<Habitacion> prueba = new ArrayList<>();
        prueba.add(new Habitacion(1,"Juanito"));
        prueba.add(new Habitacion(3,"Menganito"));
        prueba.add(new Habitacion(6,"Joselito"));

        for(int i=0;i<h1.getHabDisponibles().size();i++){
            if((h1.getHabDisponibles().get(i).getNum() != prueba.get(i).getNum()) ||
                    (!Objects.equals(h1.getHabDisponibles().get(i).getSupervisor(), prueba.get(i).getSupervisor()))){
                test = false;
            }
        }

        assertTrue(test);
    }

    @Test
    void TestErrores(){ //En este test sale un coverage muy bajo de la clase "EstadoHabitacion" pero todos los casos son practicamente identicos por lo que no los testeamos
        Hotel h1 = getHotel();

        assertEquals("Sin errores", h1.getErrores());

        h1.reservarHab(11, "Pedrito");
        assertEquals("Habitacion no encontrada", h1.getErrores());

        h1.reservarHab(4, "Fulanito");
        assertEquals("Habitacion no disponible",h1.getErrores(4));

        //Si realizamos una accion correcta no muestra errores aun despues de haber cometido otros
        h1.reservarHab(1, "Luis");
        assertEquals("Sin errores", h1.getErrores());

        h1.aprobarLimp(6, "Shakira", true);
        assertEquals("No puedes aprobar una habitacion ya aprobada", h1.getErrores(6));

        h1.reservarHab(6, "Bizarap");
        assertEquals("Sin errores", h1.getErrores(6));
    }
}