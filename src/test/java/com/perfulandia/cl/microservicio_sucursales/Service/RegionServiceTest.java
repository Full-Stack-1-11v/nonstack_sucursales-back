package com.perfulandia.cl.microservicio_sucursales.Service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


import java.util.List;


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

        // When    // When
        when(regionRepository.findAll()).thenReturn(expectedRegions);        

        // Then
        List<Region> result = regionService.getAllRegions();   
        assertEquals(expectedRegions, result);
        assertEquals(1, result.size());
    }

    @Test
    public void testSaveRegion() {

        // Given
        Region region = new Region(1, "Region prueba 1");
        when(regionRepository.save(region)).thenReturn(region);

        Region savedRegion = regionService.saveRegion(region);
        assertNotNull(savedRegion);
        assertEquals(1, savedRegion.getIdRegion());


    }

    @Test
    public void testDeleteById() {

        // Given
        Integer id = 1;
        doNothing().when(regionRepository).deleteById(id);

        regionService.deleteById(id);
        verify(regionRepository, times(1)).deleteById(id);
        
    }

    @Test
    public void testGetRegionById() {
        // Given
        Integer id = 1;
        Region expectedRegion = new Region(1, "Region existente 1");
        when(regionRepository.findByIdRegion(id)).thenReturn(expectedRegion);

        Region result = regionService.getRegionById(id);
        assertNotNull(result);
        assertEquals(expectedRegion.getIdRegion(), result.getIdRegion());
                
    }

   


// >>>> ./mvnw clean verify para ejecutar los test en la terminal


}