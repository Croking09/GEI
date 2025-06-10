package e1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CuentaBancariaTest {
    Cliente c1 = new ClienteEstandar(new Cliente.DNI(34311119));
    Cliente c2 = new ClientePreferente(new Cliente.DNI(34311119));
    Cliente c3 = new ClienteVIP(new Cliente.DNI(34311119));

    CuentaCorriente b1 = new CuentaCorriente(new CuentaBancaria.IBAN(),250000,c1);
    CuentaCorriente b2 = new CuentaCorriente(new CuentaBancaria.IBAN(),250000,c2);
    CuentaCorriente b3 = new CuentaCorriente(new CuentaBancaria.IBAN(),250000,c3);

    CuentaAhorro a1 = new CuentaAhorro(new CuentaBancaria.IBAN(),250000,c1);
    CuentaAhorro a2 = new CuentaAhorro(new CuentaBancaria.IBAN(),250000,c2);
    CuentaAhorro a3 = new CuentaAhorro(new CuentaBancaria.IBAN(),250000,c3);

    @Test
    void testIngreso(){
        assertThrows(IllegalArgumentException.class, () -> b1.ingresar(-200));

        assertThrows(IllegalArgumentException.class, () -> a1.ingresar(-200));
        assertThrows(IllegalArgumentException.class, () -> a1.ingresar(5000));
        assertThrows(IllegalArgumentException.class, () -> a2.ingresar(5000));

        b1.ingresar(10000);
        assertEquals(260000,b1.getSaldo());

        a3.ingresar(5000);
        assertEquals(255000,a3.getSaldo());

        a1.ingresar(150000);
        assertEquals(400000,a1.getSaldo());

        a2.ingresar(60000);
        assertEquals(310000,a2.getSaldo());
    }

    @Test
    void testRetirada(){
        assertThrows(IllegalArgumentException.class, () -> b1.retirar(-200));
        assertThrows(IllegalArgumentException.class, () -> a1.retirar(-200));
        assertThrows(IllegalArgumentException.class, () -> b1.retirar(260000));

        assertThrows(IllegalArgumentException.class, () -> b2.retirar(360000));

        b3.retirar(300000);
        assertEquals(-50000,b3.getSaldo());

        a1.retirar(100000); //250000 - 100000 de retiradad - 4000 de comisiones
        assertEquals(146000,a1.getSaldo());

        assertThrows(IllegalArgumentException.class, () -> a1.retirar(146000));

    }

    @Test
    void testGettersIBAN(){
        assertEquals(c1,b1.getTitular());

        CuentaBancaria.IBAN prueba=b1.getIBAN();
        assertEquals(2,Integer.toString(prueba.getNumControl()).length());
        assertEquals(5,Integer.toString(prueba.getNumCuenta()).length());
        assertEquals("ES",prueba.getPais());

        assertEquals(9,prueba.toString().length());
    }

    @Test
    void testGettersDNI(){
        Cliente.DNI prueba=c3.getDNI();
        assertEquals('H',prueba.getLetra());
        assertEquals(34311119,prueba.getNum());
        assertEquals("34311119H",prueba.toString());
    }
}