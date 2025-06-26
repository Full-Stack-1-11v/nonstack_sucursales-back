package com.perfulandia.cl.microservicio_sucursales.service;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository;

@SpringBootTest
@ActiveProfiles("test")
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @MockitoBean
    private CiudadService ciudadService;

    @Test
    void testCreateSucursal() {
        Ciudad ciudad = new Ciudad(1, "Santiago", null);
        Sucursal sucursal = new Sucursal(null, "Sucursal Nueva", ciudad);
        Sucursal created = new Sucursal(1, "Sucursal Nueva", ciudad);

        when(ciudadService.getCiudadById(1)).thenReturn(ciudad);
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(created);

        Sucursal result = sucursalService.createSucursal(sucursal);

        assertNotNull(result);
        assertEquals(1, result.getIdSucursal());
        assertEquals("Sucursal Nueva", result.getNombreSucursal());
        verify(ciudadService, times(1)).getCiudadById(1);
        verify(sucursalRepository, times(1)).save(sucursal);
    }
    
    @Test
    public void testCreateSucursalByCiudadId_CiudadExiste() {
        Ciudad ciudad = new Ciudad(1, "Santiago", null);
        Sucursal sucursal = new Sucursal(null, "Sucursal Nueva", null);
        Sucursal created = new Sucursal(1, "Sucursal Nueva", ciudad);

        when(ciudadService.getCiudadById(1)).thenReturn(ciudad);
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(created);

        Sucursal result = sucursalService.createSucursalByCiudadId(sucursal, 1);

        assertNotNull(result);
        assertEquals(1, result.getIdSucursal());
        assertEquals("Santiago", result.getCiudad().getNombreCiudad());
        verify(ciudadService, times(2)).getCiudadById(1); // Se le llama dos veces: una al buscar la ciudad y otra al asignarla a la sucursal
        verify(sucursalRepository, times(1)).save(sucursal);
    }

    @Test
    public void testCreateSucursalByCiudadId_CiudadNoExiste() {
        Sucursal sucursal = new Sucursal(null, "Sucursal Nueva", null);
        when(ciudadService.getCiudadById(99)).thenReturn(null);

        Sucursal result = sucursalService.createSucursalByCiudadId(sucursal, 99);

        assertNull(result);
        verify(ciudadService, times(1)).getCiudadById(99);
        verify(sucursalRepository, never()).save(any());
    }

    @Test
    public void testGetAllSucursales() {

        // Given
        List<Sucursal> sucursales = List.of( new Sucursal(1, "Sucursal prueba 1", null), 
                                             new Sucursal(2, "Sucursal prueba 2", null));

        //When
        when(sucursalRepository.findAll()).thenReturn(sucursales);

        // Then
        List<Sucursal> resultSucursales = sucursalService.getAllSucursales();
        assertEquals(2, resultSucursales.size());
        assertEquals("Sucursal prueba 1", resultSucursales.get(0).getNombreSucursal());
        assertEquals("Sucursal prueba 2", resultSucursales.get(1).getNombreSucursal());

        verify(sucursalRepository, times(1)).findAll();
        
    }

    @Test
    public void testGetAllSucursales_ListaVacia() {
        when(sucursalRepository.findAll()).thenReturn(Collections.emptyList());

        List<Sucursal> result = sucursalService.getAllSucursales();

        assertTrue(result.isEmpty());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test 
    public void testGetSucursalById() {

        // Given
        Sucursal mockSucursal = new Sucursal(1, "Sucursal prueba 1", null);
        when(sucursalRepository.findById(1)).thenReturn(java.util.Optional.of(mockSucursal));
        
        // When
        Sucursal sucursal = sucursalService.getSucursalById(1).orElse(mockSucursal);

        // Then 
        assertNotNull(sucursal);
        assertEquals("Sucursal prueba 1", sucursal.getNombreSucursal());
        verify(sucursalRepository, times(1)).findById(1);

    }

    @Test
    public void testUpdateSucursal() {

        // Given
        Sucursal mockSucursal = new Sucursal(1, "Sucursal prueba 1", null);
        when(sucursalRepository.existsById(1)).thenReturn(true);
        when(sucursalRepository.save(mockSucursal)).thenReturn(mockSucursal);

        // When
        Sucursal updatedSucursal = sucursalService.updateSucursal(1, mockSucursal);

        // Then
        assertNotNull(updatedSucursal);
        assertEquals(1, updatedSucursal.getIdSucursal());
        assertEquals("Sucursal prueba 1", updatedSucursal.getNombreSucursal());
        verify(sucursalRepository, times(1)).save(mockSucursal);
    }

    @Test
    public void testDeleteSucursal() {
        // Given
        Integer idSucursal = 1;
        // When
        sucursalService.eliminarSucursal(idSucursal);
            
        // Then
        verify(sucursalRepository, times(1)).deleteById(idSucursal);

    }

    @Test
    public void testGetSucursalesByCiudadId() {
        List<Sucursal> sucursales = List.of(new Sucursal(1, "Sucursal 1", null));
        when(sucursalRepository.findByCiudadIdCiudad(1)).thenReturn(sucursales);

        List<Sucursal> result = sucursalService.getSucursalesByCiudadId(1);

        assertEquals(1, result.size());
        verify(sucursalRepository, times(1)).findByCiudadIdCiudad(1);
    }

    @Test
    public void testGetSucursalesByCiudadId_ListaVacia() {
        when(sucursalRepository.findByCiudadIdCiudad(2)).thenReturn(Collections.emptyList());

        List<Sucursal> result = sucursalService.getSucursalesByCiudadId(2);

        assertTrue(result.isEmpty());
        verify(sucursalRepository, times(1)).findByCiudadIdCiudad(2);
    }

}
