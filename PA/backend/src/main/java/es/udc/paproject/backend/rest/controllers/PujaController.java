package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.entities.Producto;
import es.udc.paproject.backend.model.entities.Puja;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.PujaFueraDePlazoException;
import es.udc.paproject.backend.model.exceptions.PujaUsuarioPublicadorException;
import es.udc.paproject.backend.model.exceptions.PujaValorNoValidoException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.ProductoService;
import es.udc.paproject.backend.model.services.PujaService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.dtos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/pujas")
public class PujaController {

    private final static String PUJA_USUARIO_PUBLICADOR_EXCEPTION="project.exceptions.PujaUsuarioPublicadorException";
    private final static String PUJA_FUERA_DE_PLAZO_EXCEPCION="project.exceptions.PujaFueraDePlazoException";
    private final static String PUJA_VALOR_NO_VALIDO_EXCEPTION = "project.exceptions.PujaValorNoValidoException";

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(PujaUsuarioPublicadorException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handlePujaUsuarioPublicadorException(PujaUsuarioPublicadorException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(
                PUJA_USUARIO_PUBLICADOR_EXCEPTION,
                null,
                PUJA_USUARIO_PUBLICADOR_EXCEPTION,
                locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(PujaFueraDePlazoException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorsDto handlePujaFueraDePlazoException(PujaFueraDePlazoException exception, Locale locale) {

        String errorMessage = messageSource.getMessage(
                PUJA_FUERA_DE_PLAZO_EXCEPCION,
                null,
                PUJA_FUERA_DE_PLAZO_EXCEPCION,
                locale);

        return new ErrorsDto(errorMessage);

    }

    @ExceptionHandler(PujaValorNoValidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Puedes cambiar el código de estado si lo necesitas
    @ResponseBody
    public ErrorsDto handlePujaValorNoValidoException(PujaValorNoValidoException exception, Locale locale) {
        // Obtener el mensaje internacionalizado
        String errorMessage = messageSource.getMessage(
                PUJA_VALOR_NO_VALIDO_EXCEPTION,
                new Object[]{exception.getProductoId(), exception.getValorActualProducto(), exception.getValorPuja()    },  // Pasamos los parámetros si es necesario
                PUJA_VALOR_NO_VALIDO_EXCEPTION,  // Mensaje por defecto
                locale);

        return new ErrorsDto(errorMessage);
    }

    @Autowired
    PujaService pujaService;

    @Autowired
    ProductoService productoService;

    @PostMapping
    public PujaRealizadaDto insertarPuja(
            @RequestAttribute Long userId,
            @Validated @RequestBody AddPujaParamsDto params
            ) throws InstanceNotFoundException, PujaUsuarioPublicadorException, PujaFueraDePlazoException, PujaValorNoValidoException {
        Puja puja = pujaService.insertarPuja(params.getValor(), params.getProductoId(), userId);
        Producto producto = productoService.buscarProducto(params.getProductoId());
        return new PujaRealizadaDto(puja.getId(), puja.obtenerEstadoActual(), producto.obtenerTiempoRestante(), producto.getValorActual());
    }

    @GetMapping("/user")
    public BlockDto<VerPujaDto> verPujasPorUsuario(
            @RequestAttribute Long userId,
            @RequestParam(defaultValue = "0") int page
    ) throws InstanceNotFoundException {

        Block<Puja> pujaBlock = pujaService.getPujasByUser(userId, page, 2);

        List<VerPujaDto> pujasDto = PujaConversor.toVerPujaDtos(pujaBlock.getItems());

        return new BlockDto<>(pujasDto, pujaBlock.getExistMoreItems());
    }

}
