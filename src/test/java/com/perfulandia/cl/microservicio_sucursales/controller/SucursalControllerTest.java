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

@SpringBootTest
@ActiveProfiles("test")
public class SucursalControllerTest {

    @Autowired
    private SucursalController sucursalController;

    @MockitoBean
    private SucursalService sucursalService;

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

    @Test
    void testListarSucursales_Vacio() {
        when(sucursalService.getAllSucursales()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Sucursal>> response = sucursalController.listarSucursales();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(sucursalService, times(1)).getAllSucursales();
    }

    @Test
    void testObtenerSucursalPorId() {
        Sucursal sucursal = new Sucursal(1, "Sucursal 1", null);
        when(sucursalService.getSucursalById(1)).thenReturn(Optional.of(sucursal));

        ResponseEntity<Sucursal> response = sucursalController.obtenerSucursalPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sucursal, response.getBody());
        verify(sucursalService, times(1)).getSucursalById(1);
    }

    @Test
    void testObtenerSucursalPorId_NotFound() {
        when(sucursalService.getSucursalById(99)).thenReturn(Optional.empty());

        ResponseEntity<Sucursal> response = sucursalController.obtenerSucursalPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(sucursalService, times(1)).getSucursalById(99);
    }

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

    @Test
    void testEliminarSucursal() {
        when(sucursalService.getSucursalById(1)).thenReturn(Optional.of(new Sucursal(1, "Sucursal 1", null)));
        doNothing().when(sucursalService).eliminarSucursal(1);

        ResponseEntity<Void> response = sucursalController.eliminarSucursal(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(sucursalService, times(1)).getSucursalById(1);
        verify(sucursalService, times(1)).eliminarSucursal(1);
    }

    @Test
    void testEliminarSucursal_NotFound() {
        when(sucursalService.getSucursalById(99)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = sucursalController.eliminarSucursal(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(sucursalService, times(1)).getSucursalById(99);
        verify(sucursalService, never()).eliminarSucursal(any());
    }
}
