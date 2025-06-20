package com.perfulandia.cl.microservicio_sucursales.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.repository.RegionRepository;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;


@SpringBootTest
@ActiveProfiles("test")
public class RegionServiceTest {
  

    @Autowired    
    private RegionService regionService;

    @MockBean   
    private RegionRepository regionRepository;  

    @Test
    public void testGetAllRegions() {
        // Given       
        List<Region> expectedRegions = List.of(new Region(1, "Region prueba 1"));

        // When    
        when(regionRepository.findAll()).thenReturn(expectedRegions);        

        // Then
        List<Region> result = regionService.getAllRegions();   
        assertEquals(expectedRegions, result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveRegion() {

        Region region = new Region(1, "Region prueba 1");
        when(regionRepository.save(region)).thenReturn(region);

        Region savedRegion = regionService.saveRegion(region);
        assertNotNull(savedRegion);
        assertEquals(1, savedRegion.getIdRegion());


    }

    @Test
    public void testDeleteById() {

        Integer id = 1;
        doNothing().when(regionRepository).deleteById(id);

        regionService.deleteById(id);
        verify(regionRepository, times(1)).deleteById(id);
        
    }

    @Test
    public void testGetRegionById() {

        Integer id = 1;
        doNothing().when(regionRepository).deleteById(id);

        regionService.deleteById(id);
        verify(regionRepository, times(1)).deleteById(id);

   
    }

    @Test
    public void testUpdateRegion() {
        // Given
        Integer id = 1;
        Region region = new Region(id, "Region actualizada");
        when(regionRepository.existsById(id)).thenReturn(true);
        when(regionRepository.save(region)).thenReturn(region);

        // When
        Region updatedRegion = regionService.updateRegion(id, region);

        // Then
        assertNotNull(updatedRegion);
        assertEquals("Region actualizada", updatedRegion.getNombreRegion());
        verify(regionRepository, times(1)).existsById(id);
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    public void testPatchRegion() {
        // Given
        Integer id = 1;
        Region existingRegion = new Region(id, "Region existente");
        Region patchData = new Region(null, "Region parcheada");
        when(regionRepository.findById(id)).thenReturn(Optional.of(existingRegion));
        when(regionRepository.save(existingRegion)).thenReturn(existingRegion);

        // When
        Region patchedRegion = regionService.patchRegion(id, patchData);

        // Then
        assertNotNull(patchedRegion);
        assertEquals("Region parcheada", patchedRegion.getNombreRegion());
        verify(regionRepository, times(1)).findById(id);
        verify(regionRepository, times(1)).save(existingRegion);
    }

    @Test
    public void testBuscarRegionPorNombreJPQL() {
        // Given
        String nombreRegion = "Region Test";
        List<Region> expectedRegions = List.of(new Region(1, nombreRegion));
        when(regionRepository.findByNombreJPQL(nombreRegion)).thenReturn(expectedRegions);

        // When
        List<Region> result = regionService.buscarRegionPorNombreJPQL(nombreRegion);

        // Then
        assertNotNull(result);
        assertEquals(expectedRegions, result);
        verify(regionRepository, times(1)).findByNombreJPQL(nombreRegion);
    }

    @Test
    public void testBuscarRegionPorNombreNative() {
        // Given
        String nombreRegion = "Region Test";
        List<Region> expectedRegions = List.of(new Region(1, nombreRegion));
        when(regionRepository.findByNombreNative(nombreRegion)).thenReturn(expectedRegions);

        // When
        List<Region> result = regionService.buscarRegionPorNombreNative(nombreRegion);

        // Then
        assertNotNull(result);
        assertEquals(expectedRegions, result);
        verify(regionRepository, times(1)).findByNombreNative(nombreRegion);
    }



// >>>> ./mvnw clean verify para ejecutar los test en la terminal


}