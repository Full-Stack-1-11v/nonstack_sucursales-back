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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;

@SpringBootTest
@ActiveProfiles("test")
public class RegionControllerTest {

    @Autowired
    private RegionController regionController;

    @MockitoBean
    private RegionService regionService;



    @Test
    public void testListarRegiones() {
        List<Region> regiones = List.of(new Region(1, "Region 1"), new Region(2, "Region 2"));
        when(regionService.getAllRegions()).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.listarRegiones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        verify(regionService, times(1)).getAllRegions();
    }

    @Test
    public void testListarRegiones_Empty() {
        when(regionService.getAllRegions()).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.listarRegiones();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(regionService, times(1)).getAllRegions();
    }

    @Test
    public void testObtenerRegionPorId() {
        Region region = new Region(1, "Region 1");
        when(regionService.getRegionById(1)).thenReturn(region);

        ResponseEntity<Region> response = regionController.obtenerRegionPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(region, response.getBody());
        verify(regionService, times(1)).getRegionById(1);
    }

    @Test
    public void testObtenerRegionPorId_NotFound() {
        when(regionService.getRegionById(99)).thenReturn(null);

        ResponseEntity<Region> response = regionController.obtenerRegionPorId(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).getRegionById(99);
    }

    @Test
    public void testCrearRegion() {
        Region region = new Region(null, "Nueva Region");
        Region saved = new Region(1, "Nueva Region");
        when(regionService.saveRegion(region)).thenReturn(saved);

        ResponseEntity<Region> response = regionController.crearRegion(region);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(saved, response.getBody());
        verify(regionService, times(1)).saveRegion(region);
    }

    @Test
    public void testActualizarRegion() {
        Region region = new Region(1, "Actualizada");
        when(regionService.updateRegion(1, region)).thenReturn(region);

        ResponseEntity<Region> response = regionController.actualizarRegion(1, region);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(region, response.getBody());
        verify(regionService, times(1)).updateRegion(1, region);
    }

    @Test
    public void testActualizarRegion_NotFound() {
        Region region = new Region(1, "Actualizada");
        when(regionService.updateRegion(1, region)).thenReturn(null);

        ResponseEntity<Region> response = regionController.actualizarRegion(1, region);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).updateRegion(1, region);
    }

    @Test
    public void testActualizarRegionParcial() {
        Region region = new Region(null, "Parcial");
        Region updated = new Region(1, "Parcial");
        when(regionService.patchRegion(1, region)).thenReturn(updated);

        ResponseEntity<Region> response = regionController.actualizarRegionParcial(1, region);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updated, response.getBody());
        verify(regionService, times(1)).patchRegion(1, region);
    }

    @Test
    public void testActualizarRegionParcial_NotFound() {
        Region region = new Region(null, "Parcial");
        when(regionService.patchRegion(1, region)).thenReturn(null);

        ResponseEntity<Region> response = regionController.actualizarRegionParcial(1, region);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(regionService, times(1)).patchRegion(1, region);
    }

    @Test
    public void testEliminarRegion() {
        Region region = new Region(1, "Region 1");
        when(regionService.getRegionById(1)).thenReturn(region);
        doNothing().when(regionService).deleteById(1);

        ResponseEntity<Void> response = regionController.eliminarRegion(1);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(regionService, times(1)).getRegionById(1);
        verify(regionService, times(1)).deleteById(1);
    }

    @Test
    public void testEliminarRegion_NotFound() {
        when(regionService.getRegionById(99)).thenReturn(null);

        ResponseEntity<Void> response = regionController.eliminarRegion(99);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(regionService, times(1)).getRegionById(99);
        verify(regionService, never()).deleteById(anyInt());
    }

    @Test
    public void testBuscarRegionPorNombreJPQL() {
        List<Region> regiones = List.of(new Region(1, "Test"));
        when(regionService.buscarRegionPorNombreJPQL("Test")).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombre("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regiones, response.getBody());
        verify(regionService, times(1)).buscarRegionPorNombreJPQL("Test");
    }

    @Test
    public void testBuscarRegionPorNombreJPQL_NotFound() {
        when(regionService.buscarRegionPorNombreJPQL("Nada")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombre("Nada");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isEmpty());
        verify(regionService, times(1)).buscarRegionPorNombreJPQL("Nada");
    }

    @Test
    public void testBuscarRegionPorNombreNative() {
        List<Region> regiones = List.of(new Region(1, "Test"));
        when(regionService.buscarRegionPorNombreNative("Test")).thenReturn(regiones);

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombreNative("Test");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(regiones, response.getBody());
        verify(regionService, times(1)).buscarRegionPorNombreNative("Test");
    }

    @Test
    public void testBuscarRegionPorNombreNative_NotFound() {
        when(regionService.buscarRegionPorNombreNative("Nada")).thenReturn(Collections.emptyList());

        ResponseEntity<List<Region>> response = regionController.buscarRegionPorNombreNative("Nada");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() == null || response.getBody().isEmpty());
        verify(regionService, times(1)).buscarRegionPorNombreNative("Nada");
    }    

}
