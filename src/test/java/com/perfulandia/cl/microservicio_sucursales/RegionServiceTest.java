package com.perfulandia.cl.microservicio_sucursales;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


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
        //Given
        Region regionTest = regionRepository.findByNombreRegion("Region prueba 1");
        

        
    }



// >>>> ./mvnw clean verify para ejecutar los test en la terminal


}