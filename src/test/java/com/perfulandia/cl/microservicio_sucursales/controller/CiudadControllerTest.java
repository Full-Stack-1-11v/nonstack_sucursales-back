package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
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
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;

@SpringBootTest
@ActiveProfiles("test")
public class CiudadControllerTest {

    @Autowired
    private CiudadController ciudadController;

    @MockitoBean
    private CiudadService ciudadService;

     @Test
    void testListarCiudades() {
        List<Ciudad> ciudades = List.of(new Ciudad(1, "Santiago", null), new Ciudad(2, "Valparaíso", null));
        when(ciudadService.getAllCiudades()).thenReturn(ciudades);

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudades();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(ciudadService, times(1)).getAllCiudades();
    }

    @Test
    void testListarCiudadesPorRegion() {
        List<Ciudad> ciudades = List.of(new Ciudad(1, "Santiago", null));
        when(ciudadService.getCiudadesByRegionId(1)).thenReturn(ciudades);

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudadesPorRegion(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(ciudadService, times(1)).getCiudadesByRegionId(1);
    }

    @Test
    void testListarCiudadesPorRegion_NotFound() {
        when(ciudadService.getCiudadesByRegionId(99)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudadesPorRegion(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).getCiudadesByRegionId(99);
    }

    @Test
    void testCrearCiudad() {
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", null);
        Ciudad created = new Ciudad(1, "Nueva Ciudad", null);
        when(ciudadService.createCiudad(ciudad)).thenReturn(created);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudad(ciudad);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(ciudadService, times(1)).createCiudad(ciudad);
    }

    @Test
    void testActualizarCiudad() {
        Ciudad ciudad = new Ciudad(1, "Actualizada", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(ciudad);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudad(1, ciudad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ciudad, response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
    }

    @Test
    void testActualizarCiudad_NotFound() {
        Ciudad ciudad = new Ciudad(1, "Actualizada", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudad(1, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
    }

    @Test
    void testActualizarCiudadParcial() {
        Ciudad ciudad = new Ciudad(null, "Parcial", null);
        Ciudad updated = new Ciudad(1, "Parcial", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(updated);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudadParcial(1, ciudad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
    }

    @Test
    void testActualizarCiudadParcial_NotFound() {
        Ciudad ciudad = new Ciudad(null, "Parcial", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudadParcial(1, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
    }

    @Test
    void testCrearCiudadPorRegion() {
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", region);
        Ciudad created = new Ciudad(1, "Nueva Ciudad", region);
        when(ciudadService.createCiudadByRegion(1, ciudad)).thenReturn(created);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudadPorRegion(1, ciudad);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(ciudadService, times(1)).createCiudadByRegion(1, ciudad);
    }

    @Test
    void testCrearCiudadPorRegion_RegionNoExiste() {
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", null);
        when(ciudadService.createCiudadByRegion(99, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudadPorRegion(99, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).createCiudadByRegion(99, ciudad);
    }

    @Test
    void testEliminarCiudad() {
        doNothing().when(ciudadService).deleteCiudad(1);

        ResponseEntity<Void> response = ciudadController.eliminarCiudad(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ciudadService, times(1)).deleteCiudad(1);
    }

}
