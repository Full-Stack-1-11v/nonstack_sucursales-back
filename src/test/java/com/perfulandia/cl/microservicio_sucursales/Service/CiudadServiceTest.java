package com.perfulandia.cl.microservicio_sucursales.service;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;


@SpringBootTest
@ActiveProfiles("test")
public class CiudadServiceTest {

    @Autowired
    private CiudadService ciudadService;

    @MockitoBean
    private CiudadRepository ciudadRepository;

    @MockitoBean
    private RegionService regionService;

    @Test
    public void testGetAllCiudades() {
        
        List <Ciudad> ciudades = List.of(new Ciudad(1, "Ciudad prueba 1", null), new Ciudad(2, "Ciudad prueba 2", null));
        when(ciudadRepository.findAll()).thenReturn(ciudades);

        // When
        List<Ciudad> expectedCiudades = ciudadService.getAllCiudades();

        // Then
        assertEquals(2, expectedCiudades.size());
        verify(ciudadRepository, times(1)).findAll();
    }

    @Test
    public void testGetCiudadById() {
        // Given
        Ciudad mockCiudad = new Ciudad(1, "Ciudad prueba 1", null);
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(mockCiudad));

        // When
        Ciudad ciudad = ciudadService.getCiudadById(1);

        // Then
        assertNotNull(ciudad);
        assertEquals("Ciudad prueba 1", ciudad.getNombreCiudad());
        verify(ciudadRepository, times(1)).findById(1);
    }

    @Test
    public void testGetCiudadById_noExiste() {

        when(ciudadRepository.findById(99)).thenReturn(Optional.empty());

        Ciudad result = ciudadService.getCiudadById(99);

        assertNull(result);
        verify(ciudadRepository, times(1)).findById(99);
    }

    @Test
    public void testCreateCiudad() {
        // Given
        Ciudad mockCiudad = new Ciudad(null, "Ciudad prueba 1", null);
        when(ciudadRepository.save(mockCiudad)).thenReturn(new Ciudad(1, "Ciudad prueba 1", null));

        // When
        Ciudad ciudad = ciudadService.createCiudad(mockCiudad);

        // Then
        assertNotNull(ciudad);
        assertEquals(1, ciudad.getIdCiudad());
        verify(ciudadRepository, times(1)).save(mockCiudad);
    }

    @Test
    public void testCreateCiudadByRegion() {
        // Given
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(null, "Ciudad con región", null);
        Ciudad ciudadConRegion = new Ciudad(null, "Ciudad con región", region);
        Ciudad savedCiudad = new Ciudad(2, "Ciudad con región", region);

        // When
        when(regionService.getRegionById(1)).thenReturn(region);
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(savedCiudad);

        Ciudad result = ciudadService.createCiudadByRegion(1, ciudad);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.getIdCiudad());
        assertEquals(region, result.getRegion());
        verify(regionService, times(1)).getRegionById(1);
        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
    }

    @Test
    public void testUpdateCiudad() {
        // Given
        Ciudad mockCiudad = new Ciudad(1, "Ciudad prueba 1", null);
        when(ciudadRepository.existsById(1)).thenReturn(true);
        when(ciudadRepository.save(mockCiudad)).thenReturn(mockCiudad);

        // When
        Ciudad ciudad = ciudadService.updateCiudad(1, mockCiudad);

        // Then
        assertNotNull(ciudad);
        assertEquals(1, ciudad.getIdCiudad());
        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).save(mockCiudad);
    }

    @Test
    public void testDeleteCiudad() {
        // Given
        when(ciudadRepository.existsById(1)).thenReturn(true);

        // When
        ciudadService.deleteCiudad(1);

        // Then
        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).deleteById(1);
    }

    @Test
    public void testDeleteCiudad_NotFound() {
        when(ciudadRepository.existsById(99)).thenReturn(false);

        ciudadService.deleteCiudad(99);

        verify(ciudadRepository, times(1)).existsById(99);
        verify(ciudadRepository, never()).deleteById(anyInt());
    }

    @Test
    public void testGetCiudadesByRegionId() {
        // Given
        List<Ciudad> mockCiudades = List.of(new Ciudad(1, "Ciudad prueba 1", null), new Ciudad(2, "Ciudad2", null));
        when(ciudadRepository.findByRegionIdRegion(1)).thenReturn(mockCiudades);

        // When
        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(1);

        // Then
        assertEquals(2, ciudades.size());
        verify(ciudadRepository, times(1)).findByRegionIdRegion(1);
    }

    @Test
    public void testGetCiudadesByRegionId_Empty() {
        when(ciudadRepository.findByRegionIdRegion(2)).thenReturn(Collections.emptyList());

        List<Ciudad> result = ciudadService.getCiudadesByRegionId(2);

        assertTrue(result.isEmpty());
        verify(ciudadRepository, times(1)).findByRegionIdRegion(2);
    }

}
