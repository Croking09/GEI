package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.Puja;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PujaFueraDePlazoException;
import es.udc.paproject.backend.model.exceptions.PujaUsuarioPublicadorException;
import es.udc.paproject.backend.model.exceptions.PujaValorNoValidoException;

public interface PujaService {

    // FUNC-4

    Puja insertarPuja(Double valor, Long productoId, Long userId) throws InstanceNotFoundException, PujaFueraDePlazoException, PujaUsuarioPublicadorException, PujaValorNoValidoException;


    // FUNC-5

    Block<Puja> getPujasByUser(Long userId, int page, int size) throws InstanceNotFoundException;
    //Devuelve un bloque de pujas realizadas por un usuario.

}
