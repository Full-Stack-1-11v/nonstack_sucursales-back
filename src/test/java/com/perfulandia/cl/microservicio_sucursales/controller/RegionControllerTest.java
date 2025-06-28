package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;

/**
 * Pruebas unitarias para {@link RegionController}.
 * <p>
 * Se utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los endpoints principales del controlador, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class RegionControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(RegionControllerTest.class);

    @Autowired
    private RegionController regionController;

    @MockitoBean
    private RegionService regionService;

    /**
     * Prueba la obtención de todas las regiones y verifica que la respuesta sea 200 OK con datos.
     */
    @Test
    public void testListarRegiones() {
        logger.info("Iniciando testListarRegiones");
        List<Region> regiones = List.of(new Region(1, "Region 1"), new Region(2, "Region 2"));
        when(regionService.getAllRegions()).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.listarRegiones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(regionService, times(1)).getAllRegions();
        logger.info("Finalizado testListarRegiones OK");
    }

    /**
     * Prueba la obtención de regiones cuando la lista está vacía y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testListarRegiones_Empty() {
        logger.info("Iniciando testListarRegiones_Empty");
        when(regionService.getAllRegions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.listarRegiones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(regionService, times(1)).getAllRegions();
        logger.info("Finalizado testListarRegiones_Empty OK");
    }

    /**
     * Prueba la obtención de una región por ID existente y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testObtenerRegionPorId() {
        logger.info("Iniciando testObtenerRegionPorId");
        Region region = new Region(1, "Region 1");
        when(regionService.getRegionById(1)).thenReturn(region);

        ResponseEntity<Region> response = regionController.obtenerRegionPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(region, response.getBody());
        verify(regionService, times(1)).getRegionById(1);
        logger.info("Finalizado testObtenerRegionPorId OK");
    }

    /**
     * Prueba la obtención de una región por ID inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    public void testObtenerRegionPorId_NotFound() {
        logger.info("Iniciando testObtenerRegionPorId_NotFound");
        when(regionService.getRegionById(99)).thenReturn(null);

        ResponseEntity<Region> response = regionController.obtenerRegionPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).getRegionById(99);
        logger.info("Finalizado testObtenerRegionPorId_NotFound OK");
    }

    /**
     * Prueba la creación de una región y verifica que la respuesta sea 201 CREATED.
     */
    @Test
    public void testCrearRegion() {
        logger.info("Iniciando testCrearRegion");
        Region region = new Region(null, "Nueva Region");
        Region saved = new Region(1, "Nueva Region");
        when(regionService.saveRegion(region)).thenReturn(saved);

        ResponseEntity<Region> response = regionController.crearRegion(region);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
        verify(regionService, times(1)).saveRegion(region);
        logger.info("Finalizado testCrearRegion OK");
    }

    /**
     * Prueba la actualización de una región existente y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testActualizarRegion() {
        logger.info("Iniciando testActualizarRegion");
        Region region = new Region(1, "Actualizada");
        when(regionService.updateRegion(1, region)).thenReturn(region);

        ResponseEntity<Region> response = regionController.actualizarRegion(1, region);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(region, response.getBody());
        verify(regionService, times(1)).updateRegion(1, region);
        logger.info("Finalizado testActualizarRegion OK");
    }

    /**
     * Prueba la actualización de una región inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    public void testActualizarRegion_NotFound() {
        logger.info("Iniciando testActualizarRegion_NotFound");
        Region region = new Region(1, "Actualizada");
        when(regionService.updateRegion(1, region)).thenReturn(null);

        ResponseEntity<Region> response = regionController.actualizarRegion(1, region);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).updateRegion(1, region);
        logger.info("Finalizado testActualizarRegion_NotFound OK");
    }

    /**
     * Prueba la actualización parcial de una región y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testActualizarRegionParcial() {
        logger.info("Iniciando testActualizarRegionParcial");
        Region region = new Region(null, "Parcial");
        Region updated = new Region(1, "Parcial");
        when(regionService.patchRegion(1, region)).thenReturn(updated);

        ResponseEntity<Region> response = regionController.actualizarRegionParcial(1, region);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(regionService, times(1)).patchRegion(1, region);
        logger.info("Finalizado testActualizarRegionParcial OK");
    }

    /**
     * Prueba la actualización parcial de una región inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    public void testActualizarRegionParcial_NotFound() {
        logger.info("Iniciando testActualizarRegionParcial_NotFound");
        Region region = new Region(null, "Parcial");
        when(regionService.patchRegion(1, region)).thenReturn(null);

        ResponseEntity<Region> response = regionController.actualizarRegionParcial(1, region);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).patchRegion(1, region);
        logger.info("Finalizado testActualizarRegionParcial_NotFound OK");
    }

    /**
     * Prueba la eliminación de una región existente y verifica que la respuesta sea 204 NO CONTENT.
     */
    @Test
    public void testEliminarRegion() {
        logger.info("Iniciando testEliminarRegion");
        Region region = new Region(1, "Region 1");
        when(regionService.getRegionById(1)).thenReturn(region);
        doNothing().when(regionService).deleteById(1);

        ResponseEntity<Void> response = regionController.eliminarRegion(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(regionService, times(1)).getRegionById(1);
        verify(regionService, times(1)).deleteById(1);
        logger.info("Finalizado testEliminarRegion OK");
    }

    /**
     * Prueba la eliminación de una región inexistente y verifica que la respuesta sea 404 NOT FOUND.
     */
    @Test
    public void testEliminarRegion_NotFound() {
        logger.info("Iniciando testEliminarRegion_NotFound");
        when(regionService.getRegionById(99)).thenReturn(null);

        ResponseEntity<Void> response = regionController.eliminarRegion(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(regionService, times(1)).getRegionById(99);
        verify(regionService, never()).deleteById(anyInt());
        logger.info("Finalizado testEliminarRegion_NotFound OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando JPQL y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testBuscarRegionPorNombreJPQL() {
        logger.info("Iniciando testBuscarRegionPorNombreJPQL");
        List<Region> regiones = List.of(new Region(1, "Test"));
        when(regionService.buscarRegionPorNombreJPQL("Test")).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombre("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regiones, response.getBody());
        verify(regionService, times(1)).buscarRegionPorNombreJPQL("Test");
        logger.info("Finalizado testBuscarRegionPorNombreJPQL OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando JPQL y verifica que la respuesta sea 404 NOT FOUND si no hay resultados.
     */
    @Test
    public void testBuscarRegionPorNombreJPQL_NotFound() {
        logger.info("Iniciando testBuscarRegionPorNombreJPQL_NotFound");
        when(regionService.buscarRegionPorNombreJPQL("Nada")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombre("Nada");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isEmpty());
        verify(regionService, times(1)).buscarRegionPorNombreJPQL("Nada");
        logger.info("Finalizado testBuscarRegionPorNombreJPQL_NotFound OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando consulta nativa y verifica que la respuesta sea 200 OK.
     */
    @Test
    public void testBuscarRegionPorNombreNative() {
        logger.info("Iniciando testBuscarRegionPorNombreNative");
        List<Region> regiones = List.of(new Region(1, "Test"));
        when(regionService.buscarRegionPorNombreNative("Test")).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombreNative("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regiones, response.getBody());
        verify(regionService, times(1)).buscarRegionPorNombreNative("Test");
        logger.info("Finalizado testBuscarRegionPorNombreNative OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando consulta nativa y verifica que la respuesta sea 404 NOT FOUND si no hay resultados.
     */
    @Test
    public void testBuscarRegionPorNombreNative_NotFound() {
        logger.info("Iniciando testBuscarRegionPorNombreNative_NotFound");
        when(regionService.buscarRegionPorNombreNative("Nada")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombreNative("Nada");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isEmpty());
        verify(regionService, times(1)).buscarRegionPorNombreNative("Nada");
        logger.info("Finalizado testBuscarRegionPorNombreNative_NotFound OK");
    }    

}
