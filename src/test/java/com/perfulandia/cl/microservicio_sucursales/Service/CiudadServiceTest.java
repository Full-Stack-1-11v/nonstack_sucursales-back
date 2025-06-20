package com.perfulandia.cl.microservicio_sucursales.service;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;


@SpringBootTest
@ActiveProfiles("test")
public class CiudadServiceTest {

    @Autowired
    private CiudadService ciudadService;

    @MockBean
    private CiudadRepository ciudadRepository;

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
    public void testUpdateCiudad() {
        // Arrange
        Ciudad mockCiudad = new Ciudad(1, "Ciudad prueba 1", null);
        when(ciudadRepository.existsById(1)).thenReturn(true);
        when(ciudadRepository.save(mockCiudad)).thenReturn(mockCiudad);

        // Act
        Ciudad ciudad = ciudadService.updateCiudad(1, mockCiudad);

        // Assert
        assertNotNull(ciudad);
        assertEquals(1, ciudad.getIdCiudad());
        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).save(mockCiudad);
    }

    @Test
    public void testDeleteCiudad() {
        // Arrange
        when(ciudadRepository.existsById(1)).thenReturn(true);

        // Act
        ciudadService.deleteCiudad(1);

        // Assert
        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).deleteById(1);
    }

    @Test
    public void testGetCiudadesByRegionId() {
        // Arrange
        List<Ciudad> mockCiudades = List.of(new Ciudad(1, "Ciudad prueba 1", null), new Ciudad(2, "Ciudad2", null));
        when(ciudadRepository.findByRegionIdRegion(1)).thenReturn(mockCiudades);

        // Act
        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(1);

        // Assert
        assertEquals(2, ciudades.size());
        verify(ciudadRepository, times(1)).findByRegionIdRegion(1);
    }


}
