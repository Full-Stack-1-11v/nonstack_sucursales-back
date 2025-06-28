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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.CiudadRepository;

/**
 * Pruebas unitarias para {@link CiudadService}.
 * <p>
 * Utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los métodos principales del servicio, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class CiudadServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CiudadServiceTest.class);

    @Autowired
    private CiudadService ciudadService;

    @MockitoBean
    private CiudadRepository ciudadRepository;

    @MockitoBean
    private RegionService regionService;

    /**
     * Prueba la obtención de todas las ciudades y verifica que la lista tenga el tamaño esperado.
     */
    @Test
    public void testGetAllCiudades() {
        logger.info("Iniciando testGetAllCiudades");
        List<Ciudad> ciudades = List.of(new Ciudad(1, "Ciudad prueba 1", null), new Ciudad(2, "Ciudad prueba 2", null));
        when(ciudadRepository.findAll()).thenReturn(ciudades);

        List<Ciudad> expectedCiudades = ciudadService.getAllCiudades();

        assertEquals(2, expectedCiudades.size());
        verify(ciudadRepository, times(1)).findAll();
        logger.info("Finalizado testGetAllCiudades OK");
    }

    /**
     * Prueba la obtención de una ciudad por ID existente.
     */
    @Test
    public void testGetCiudadById() {
        logger.info("Iniciando testGetCiudadById");
        Ciudad mockCiudad = new Ciudad(1, "Ciudad prueba 1", null);
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(mockCiudad));

        Ciudad ciudad = ciudadService.getCiudadById(1);

        assertNotNull(ciudad);
        assertEquals("Ciudad prueba 1", ciudad.getNombreCiudad());
        verify(ciudadRepository, times(1)).findById(1);
        logger.info("Finalizado testGetCiudadById OK");
    }

    /**
     * Prueba la obtención de una ciudad por ID inexistente.
     */
    @Test
    public void testGetCiudadById_noExiste() {
        logger.info("Iniciando testGetCiudadById_noExiste");
        when(ciudadRepository.findById(99)).thenReturn(Optional.empty());

        Ciudad result = ciudadService.getCiudadById(99);

        assertNull(result);
        verify(ciudadRepository, times(1)).findById(99);
        logger.info("Finalizado testGetCiudadById_noExiste OK");
    }

    /**
     * Prueba la creación de una ciudad.
     */
    @Test
    public void testCreateCiudad() {
        logger.info("Iniciando testCreateCiudad");
        Ciudad mockCiudad = new Ciudad(null, "Ciudad prueba 1", null);
        when(ciudadRepository.save(mockCiudad)).thenReturn(new Ciudad(1, "Ciudad prueba 1", null));

        Ciudad ciudad = ciudadService.createCiudad(mockCiudad);

        assertNotNull(ciudad);
        assertEquals(1, ciudad.getIdCiudad());
        verify(ciudadRepository, times(1)).save(mockCiudad);
        logger.info("Finalizado testCreateCiudad OK");
    }

    /**
     * Prueba la creación de una ciudad asociada a una región.
     */
    @Test
    public void testCreateCiudadByRegion() {
        logger.info("Iniciando testCreateCiudadByRegion");
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(null, "Ciudad con región", null);
        Ciudad savedCiudad = new Ciudad(2, "Ciudad con región", region);

        when(regionService.getRegionById(1)).thenReturn(region);
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(savedCiudad);

        Ciudad result = ciudadService.createCiudadByRegion(1, ciudad);

        assertNotNull(result);
        assertEquals(2, result.getIdCiudad());
        assertEquals(region, result.getRegion());
        verify(regionService, times(1)).getRegionById(1);
        verify(ciudadRepository, times(1)).save(any(Ciudad.class));
        logger.info("Finalizado testCreateCiudadByRegion OK");
    }

    /**
     * Prueba la actualización de una ciudad existente.
     */
    @Test
    public void testUpdateCiudad() {
        logger.info("Iniciando testUpdateCiudad");
        Ciudad mockCiudad = new Ciudad(1, "Ciudad prueba 1", null);
        when(ciudadRepository.existsById(1)).thenReturn(true);
        when(ciudadRepository.save(mockCiudad)).thenReturn(mockCiudad);

        Ciudad ciudad = ciudadService.updateCiudad(1, mockCiudad);

        assertNotNull(ciudad);
        assertEquals(1, ciudad.getIdCiudad());
        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).save(mockCiudad);
        logger.info("Finalizado testUpdateCiudad OK");
    }

    /**
     * Prueba la eliminación de una ciudad existente.
     */
    @Test
    public void testDeleteCiudad() {
        logger.info("Iniciando testDeleteCiudad");
        when(ciudadRepository.existsById(1)).thenReturn(true);

        ciudadService.deleteCiudad(1);

        verify(ciudadRepository, times(1)).existsById(1);
        verify(ciudadRepository, times(1)).deleteById(1);
        logger.info("Finalizado testDeleteCiudad OK");
    }

    /**
     * Prueba la eliminación de una ciudad inexistente.
     */
    @Test
    public void testDeleteCiudad_NotFound() {
        logger.info("Iniciando testDeleteCiudad_NotFound");
        when(ciudadRepository.existsById(99)).thenReturn(false);

        ciudadService.deleteCiudad(99);

        verify(ciudadRepository, times(1)).existsById(99);
        verify(ciudadRepository, never()).deleteById(anyInt());
        logger.info("Finalizado testDeleteCiudad_NotFound OK");
    }

    /**
     * Prueba la obtención de ciudades por ID de región.
     */
    @Test
    public void testGetCiudadesByRegionId() {
        logger.info("Iniciando testGetCiudadesByRegionId");
        List<Ciudad> mockCiudades = List.of(new Ciudad(1, "Ciudad prueba 1", null), new Ciudad(2, "Ciudad2", null));
        when(ciudadRepository.findByRegionIdRegion(1)).thenReturn(mockCiudades);

        List<Ciudad> ciudades = ciudadService.getCiudadesByRegionId(1);

        assertEquals(2, ciudades.size());
        verify(ciudadRepository, times(1)).findByRegionIdRegion(1);
        logger.info("Finalizado testGetCiudadesByRegionId OK");
    }

    /**
     * Prueba la obtención de ciudades por ID de región cuando la lista está vacía.
     */
    @Test
    public void testGetCiudadesByRegionId_Empty() {
        logger.info("Iniciando testGetCiudadesByRegionId_Empty");
        when(ciudadRepository.findByRegionIdRegion(2)).thenReturn(Collections.emptyList());

        List<Ciudad> result = ciudadService.getCiudadesByRegionId(2);

        assertTrue(result.isEmpty());
        verify(ciudadRepository, times(1)).findByRegionIdRegion(2);
        logger.info("Finalizado testGetCiudadesByRegionId_Empty OK");
    }
}
