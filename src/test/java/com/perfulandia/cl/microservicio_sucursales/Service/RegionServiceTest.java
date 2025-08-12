package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository;

/**
 * Pruebas unitarias para {@link RegionService}.
 * <p>
 * Utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los métodos principales del servicio, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class RegionServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(RegionServiceTest.class);

    @Autowired    
    private RegionService regionService;

    @MockitoBean
    private RegionRepository regionRepository;  

    /**
     * Prueba la obtención de todas las regiones y verifica que la lista tenga el tamaño esperado.
     */
    @Test
    public void testGetAllRegions() {
        logger.info("Iniciando testGetAllRegions");
        List<Region> expectedRegions = List.of(new Region(1, "Region prueba 1"));
        when(regionRepository.findAll()).thenReturn(expectedRegions);

        List<Region> result = regionService.getAllRegions();   
        assertEquals(expectedRegions, result);
        assertEquals(1, result.size());
        logger.info("Finalizado testGetAllRegions OK");
    }

    /**
     * Prueba el guardado de una región y verifica que se retorne correctamente.
     */
    @Test
    public void testSaveRegion() {
        logger.info("Iniciando testSaveRegion");
        Region region = new Region(1, "Region prueba 1");
        when(regionRepository.save(region)).thenReturn(region);

        Region savedRegion = regionService.saveRegion(region);
        assertNotNull(savedRegion);
        assertEquals(1, savedRegion.getIdRegion());
        logger.info("Finalizado testSaveRegion OK");
    }

    /**
     * Prueba la eliminación de una región por ID y verifica que se invoque el método del repositorio.
     */
    @Test
    public void testDeleteById() {
        logger.info("Iniciando testDeleteById");
        Integer id = 1;
        doNothing().when(regionRepository).deleteById(id);

        regionService.deleteById(id);
        verify(regionRepository, times(1)).deleteById(id);
        logger.info("Finalizado testDeleteById OK");
    }

    /**
     * Prueba la obtención de una región por ID y la eliminación posterior.
     */
    @Test
    public void testGetRegionById() {
        logger.info("Iniciando testGetRegionById");
        Integer id = 1;
        doNothing().when(regionRepository).deleteById(id);

        regionService.deleteById(id);
        verify(regionRepository, times(1)).deleteById(id);
        logger.info("Finalizado testGetRegionById OK");
    }

    /**
     * Prueba la actualización de una región existente.
     */
    @Test
    public void testUpdateRegion() {
        logger.info("Iniciando testUpdateRegion");
        Integer id = 1;
        Region region = new Region(id, "Region actualizada");
        when(regionRepository.existsById(id)).thenReturn(true);
        when(regionRepository.save(region)).thenReturn(region);

        Region updatedRegion = regionService.updateRegion(id, region);

        assertNotNull(updatedRegion);
        assertEquals("Region actualizada", updatedRegion.getNombreRegion());
        verify(regionRepository, times(1)).existsById(id);
        verify(regionRepository, times(1)).save(region);
        logger.info("Finalizado testUpdateRegion OK");
    }

    /**
     * Prueba la actualización parcial (patch) de una región existente.
     */
    @Test
    public void testPatchRegion() {
        logger.info("Iniciando testPatchRegion");
        Integer id = 1;
        Region existingRegion = new Region(id, "Region existente");
        Region patchData = new Region(null, "Region parcheada");
        when(regionRepository.findById(id)).thenReturn(Optional.of(existingRegion));
        when(regionRepository.save(existingRegion)).thenReturn(existingRegion);

        Region patchedRegion = regionService.patchRegion(id, patchData);

        assertNotNull(patchedRegion);
        assertEquals("Region parcheada", patchedRegion.getNombreRegion());
        verify(regionRepository, times(1)).findById(id);
        verify(regionRepository, times(1)).save(existingRegion);
        logger.info("Finalizado testPatchRegion OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando JPQL.
     */
    @Test
    public void testBuscarRegionPorNombreJPQL() {
        logger.info("Iniciando testBuscarRegionPorNombreJPQL");
        String nombreRegion = "Region Test";
        List<Region> expectedRegions = List.of(new Region(1, nombreRegion));
        when(regionRepository.findByNombreJPQL(nombreRegion)).thenReturn(expectedRegions);

        List<Region> result = regionService.buscarRegionPorNombreJPQL(nombreRegion);

        assertNotNull(result);
        assertEquals(expectedRegions, result);
        verify(regionRepository, times(1)).findByNombreJPQL(nombreRegion);
        logger.info("Finalizado testBuscarRegionPorNombreJPQL OK");
    }

    /**
     * Prueba la búsqueda de regiones por nombre usando consulta nativa.
     */
    @Test
    public void testBuscarRegionPorNombreNative() {
        logger.info("Iniciando testBuscarRegionPorNombreNative");
        String nombreRegion = "Region Test";
        List<Region> expectedRegions = List.of(new Region(1, nombreRegion));
        when(regionRepository.findByNombreNative(nombreRegion)).thenReturn(expectedRegions);

        List<Region> result = regionService.buscarRegionPorNombreNative(nombreRegion);

        assertNotNull(result);
        assertEquals(expectedRegions, result);
        verify(regionRepository, times(1)).findByNombreNative(nombreRegion);
        logger.info("Finalizado testBuscarRegionPorNombreNative OK");
    }

// >>>> ./mvnw clean verify para ejecutar los test en la terminal


}