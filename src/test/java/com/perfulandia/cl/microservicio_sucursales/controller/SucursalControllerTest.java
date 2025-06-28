package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.service.SucursalService;

/**
 * Pruebas unitarias para {@link SucursalController}.
 * <p>
 * Utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los endpoints principales del controlador, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class SucursalControllerTest {

    /**
     * Controlador de sucursales inyectado para pruebas.
     */
    @Autowired
    private SucursalController sucursalController;

    /**
     * Servicio de sucursales mockeado para simular la lógica de negocio.
     */
    @MockitoBean
    private SucursalService sucursalService;

    /**
     * Prueba la creación de una sucursal y verifica que la respuesta sea 201 CREATED.
     */
    @Test
    void testCrearSucursal() {
        Ciudad ciudad = new Ciudad(1, "Santiago", null);
        Sucursal sucursal = new Sucursal(null, "Sucursal Central", ciudad);
        Sucursal created = new Sucursal(1, "Sucursal Central", ciudad);

        when(sucursalService.createSucursal(sucursal)).thenReturn(created);

        ResponseEntity<Sucursal> response = sucursalController.crearSucursal(sucursal);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(sucursalService, times(1)).createSucursal(sucursal);
    }

    /**
     * Prueba la obtención de todas las sucursales y verifica que la respuesta sea 200 OK con datos.
     */
    @Test
    void testListarSucursales() {
        List<Sucursal> sucursales = List.of(
                new Sucursal(1, "Sucursal 1", null),
                new Sucursal(2, "Sucursal 2", null)
        );
        when(sucursalService.getAllSucursales()).thenReturn(sucursales);

        ResponseEntity<List<Sucursal>> response = sucursalController.listarSucursales();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(sucursalService, times(1)).getAllSucursales();
    }

    /**
     * Prueba la obtención de sucursales cuando la lista está vacía y verifica que la respuesta sea 200 OK.
     */
    @Test
    void testListarSucursales_Vacio() {
        when(sucursalService.getAllSucursales()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Sucursal>> response = sucursalController.listarSucursales();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(sucursalService, times(1)).getAllSucursales();
    }

    /**
     * Prueba la obtención de una sucursal por ID existente y verifica que la respuesta sea 200 OK.
     */
    @Test
    void testObtenerSucursalPorId() {
        Sucursal sucursal = new Sucursal(1, "Sucursal 1", null);
        when(sucursalService.getSucursalById(1)).thenReturn(Optional.of(sucursal));

        ResponseEntity<Sucursal> response = sucursalController.obtenerSucursalPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sucursal, response.getBody());
        verify(sucursalService, times(1)).getSucursalById(1);
    }

    /**
     * Prueba la obtención de una sucursal por ID inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testObtenerSucursalPorId_NotFound() {
        when(sucursalService.getSucursalById(99)).thenReturn(Optional.empty());

        ResponseEntity<Sucursal> response = sucursalController.obtenerSucursalPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(sucursalService, times(1)).getSucursalById(99);
    }

    /**
     * Prueba la actualización de una sucursal existente y verifica que la respuesta sea 200 OK.
     */
    @Test
    void testActualizarSucursal() {
        Ciudad ciudad = new Ciudad(2, "Valparaíso", null);
        Sucursal sucursal = new Sucursal(1, "Sucursal Actualizada", ciudad);
        when(sucursalService.updateSucursal(1, sucursal)).thenReturn(sucursal);

        ResponseEntity<Sucursal> response = sucursalController.actualizarSucursal(1, sucursal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sucursal, response.getBody());
        verify(sucursalService, times(1)).updateSucursal(1, sucursal);
    }

    /**
     * Prueba la actualización de una sucursal inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testActualizarSucursal_NotFound() {
        Ciudad ciudad = new Ciudad(2, "Valparaíso", null);
        Sucursal sucursal = new Sucursal(1, "Sucursal Actualizada", ciudad);
        when(sucursalService.updateSucursal(1, sucursal)).thenReturn(null);

        ResponseEntity<Sucursal> response = sucursalController.actualizarSucursal(1, sucursal);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(sucursalService, times(1)).updateSucursal(1, sucursal);
    }

    /**
     * Prueba la eliminación de una sucursal existente y verifica que la respuesta sea 204 NO CONTENT.
     */
    @Test
    void testEliminarSucursal() {
        when(sucursalService.getSucursalById(1)).thenReturn(Optional.of(new Sucursal(1, "Sucursal 1", null)));
        doNothing().when(sucursalService).eliminarSucursal(1);

        ResponseEntity<Void> response = sucursalController.eliminarSucursal(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sucursalService, times(1)).getSucursalById(1);
        verify(sucursalService, times(1)).eliminarSucursal(1);
    }

    /**
     * Prueba la eliminación de una sucursal inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testEliminarSucursal_NotFound() {
        when(sucursalService.getSucursalById(99)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = sucursalController.eliminarSucursal(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sucursalService, times(1)).getSucursalById(99);
        verify(sucursalService, never()).eliminarSucursal(any());
    }
}
