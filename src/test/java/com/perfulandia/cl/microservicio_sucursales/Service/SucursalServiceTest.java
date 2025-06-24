package com.perfulandia.cl.microservicio_sucursales.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository;

@SpringBootTest
@ActiveProfiles("test")
public class SucursalServiceTest {

    @Autowired
    private SucursalService sucursalService;

    @MockBean
    private SucursalRepository sucursalRepository;

    @Test
    public void testCreateSucursal() {
        // Given
        Sucursal mockSucursal = new Sucursal(null, "Sucursal nueva 1", null);
        when(sucursalRepository.save(mockSucursal)).thenReturn(new Sucursal(1, "Sucursal nueva 1", null));

        // When
        Sucursal sucursal = sucursalService.createSucursalByCiudadId(mockSucursal, 1);

        // Then
        assertNotNull(sucursal);
        assertEquals(1, sucursal.getIdSucursal());
        verify(sucursalRepository, times(1)).save(mockSucursal);

    }

    @Test
    public void testGetAllCiudades() {

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

}
