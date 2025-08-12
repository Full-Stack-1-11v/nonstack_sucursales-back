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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;

/**
 * Pruebas unitarias para {@link CiudadController}.
 * <p>
 * Utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los endpoints principales del controlador, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CiudadControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(CiudadControllerTest.class);

    @Autowired
    private CiudadController ciudadController;

    @MockitoBean
    private CiudadService ciudadService;

    /**
     * Prueba la obtención de todas las ciudades y verifica que la respuesta sea 200 OK con datos.
     */
    @Test
    void testListarCiudades() {
        logger.info("Iniciando testListarCiudades");
        List<Ciudad> ciudades = List.of(new Ciudad(1, "Santiago", null), new Ciudad(2, "Valparaíso", null));
        when(ciudadService.getAllCiudades()).thenReturn(ciudades);

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudades();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(ciudadService, times(1)).getAllCiudades();
        logger.info("Finalizado testListarCiudades OK");
    }

    /**
     * Prueba la obtención de ciudades por región y verifica que la respuesta sea 200 OK con datos.
     */
    @Test
    void testListarCiudadesPorRegion() {
        logger.info("Iniciando testListarCiudadesPorRegion");
        List<Ciudad> ciudades = List.of(new Ciudad(1, "Santiago", null));
        when(ciudadService.getCiudadesByRegionId(1)).thenReturn(ciudades);

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudadesPorRegion(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        verify(ciudadService, times(1)).getCiudadesByRegionId(1);
        logger.info("Finalizado testListarCiudadesPorRegion OK");
    }

    /**
     * Prueba la obtención de ciudades por región inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testListarCiudadesPorRegion_NotFound() {
        logger.info("Iniciando testListarCiudadesPorRegion_NotFound");
        when(ciudadService.getCiudadesByRegionId(99)).thenReturn(Collections.emptyList());

        ResponseEntity<List<Ciudad>> response = ciudadController.listarCiudadesPorRegion(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).getCiudadesByRegionId(99);
        logger.info("Finalizado testListarCiudadesPorRegion_NotFound OK");
    }

    /**
     * Prueba la creación de una ciudad y verifica que la respuesta sea 201 CREATED.
     */
    @Test
    void testCrearCiudad() {
        logger.info("Iniciando testCrearCiudad");
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", null);
        Ciudad created = new Ciudad(1, "Nueva Ciudad", null);
        when(ciudadService.createCiudad(ciudad)).thenReturn(created);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudad(ciudad);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(ciudadService, times(1)).createCiudad(ciudad);
        logger.info("Finalizado testCrearCiudad OK");
    }

    /**
     * Prueba la actualización de una ciudad existente y verifica que la respuesta sea 200 OK.
     */
    @Test
    void testActualizarCiudad() {
        logger.info("Iniciando testActualizarCiudad");
        Ciudad ciudad = new Ciudad(1, "Actualizada", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(ciudad);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudad(1, ciudad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ciudad, response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
        logger.info("Finalizado testActualizarCiudad OK");
    }

    /**
     * Prueba la actualización de una ciudad inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testActualizarCiudad_NotFound() {
        logger.info("Iniciando testActualizarCiudad_NotFound");
        Ciudad ciudad = new Ciudad(1, "Actualizada", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudad(1, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
        logger.info("Finalizado testActualizarCiudad_NotFound OK");
    }

    /**
     * Prueba la actualización parcial de una ciudad y verifica que la respuesta sea 200 OK.
     */
    @Test
    void testActualizarCiudadParcial() {
        logger.info("Iniciando testActualizarCiudadParcial");
        Ciudad ciudad = new Ciudad(null, "Parcial", null);
        Ciudad updated = new Ciudad(1, "Parcial", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(updated);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudadParcial(1, ciudad);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
        logger.info("Finalizado testActualizarCiudadParcial OK");
    }

    /**
     * Prueba la actualización parcial de una ciudad inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testActualizarCiudadParcial_NotFound() {
        logger.info("Iniciando testActualizarCiudadParcial_NotFound");
        Ciudad ciudad = new Ciudad(null, "Parcial", null);
        when(ciudadService.updateCiudad(1, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.actualizarCiudadParcial(1, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).updateCiudad(1, ciudad);
        logger.info("Finalizado testActualizarCiudadParcial_NotFound OK");
    }

    /**
     * Prueba la creación de una ciudad por región y verifica que la respuesta sea 201 CREATED.
     */
    @Test
    void testCrearCiudadPorRegion() {
        logger.info("Iniciando testCrearCiudadPorRegion");
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", region);
        Ciudad created = new Ciudad(1, "Nueva Ciudad", region);
        when(ciudadService.createCiudadByRegion(1, ciudad)).thenReturn(created);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudadPorRegion(1, ciudad);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(created, response.getBody());
        verify(ciudadService, times(1)).createCiudadByRegion(1, ciudad);
        logger.info("Finalizado testCrearCiudadPorRegion OK");
    }

    /**
     * Prueba la creación de una ciudad por región inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    void testCrearCiudadPorRegion_RegionNoExiste() {
        logger.info("Iniciando testCrearCiudadPorRegion_RegionNoExiste");
        Ciudad ciudad = new Ciudad(null, "Nueva Ciudad", null);
        when(ciudadService.createCiudadByRegion(99, ciudad)).thenReturn(null);

        ResponseEntity<Ciudad> response = ciudadController.crearCiudadPorRegion(99, ciudad);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(ciudadService, times(1)).createCiudadByRegion(99, ciudad);
        logger.info("Finalizado testCrearCiudadPorRegion_RegionNoExiste OK");
    }

    /**
     * Prueba la eliminación de una ciudad existente y verifica que la respuesta sea 204 NO CONTENT.
     */
    @Test
    void testEliminarCiudad() {
        logger.info("Iniciando testEliminarCiudad");
        doNothing().when(ciudadService).deleteCiudad(1);

        ResponseEntity<Void> response = ciudadController.eliminarCiudad(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(ciudadService, times(1)).deleteCiudad(1);
        logger.info("Finalizado testEliminarCiudad OK");
    }

}
