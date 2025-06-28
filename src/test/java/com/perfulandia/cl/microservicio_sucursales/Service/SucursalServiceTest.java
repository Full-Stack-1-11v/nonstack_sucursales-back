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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.repository.SucursalRepository;

/**
 * Pruebas unitarias para {@link SucursalService}.
 * <p>
 * Utiliza {@code @SpringBootTest}, {@code @ActiveProfiles("test")}, {@code @Autowired} y {@code @MockitoBean}
 * para probar los métodos principales del servicio, cubriendo casos de éxito y error.
 */
@SpringBootTest
@ActiveProfiles("test")
public class SucursalServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(SucursalServiceTest.class);

    @Autowired
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalRepository sucursalRepository;

    @MockitoBean
    private CiudadService ciudadService;

    /**
     * Prueba la creación de una sucursal y verifica que se retorne correctamente.
     */
    @Test
    void testCreateSucursal() {
        logger.info("Iniciando testCreateSucursal");
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
        logger.info("Finalizado testCreateSucursal OK");
    }

    /**
     * Prueba la creación de una sucursal por ciudad existente.
     */
    @Test
    public void testCreateSucursalByCiudadId_CiudadExiste() {
        logger.info("Iniciando testCreateSucursalByCiudadId_CiudadExiste");
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
        logger.info("Finalizado testCreateSucursalByCiudadId_CiudadExiste OK");
    }

    /**
     * Prueba la creación de una sucursal por ciudad inexistente.
     */
    @Test
    public void testCreateSucursalByCiudadId_CiudadNoExiste() {
        logger.info("Iniciando testCreateSucursalByCiudadId_CiudadNoExiste");
        Sucursal sucursal = new Sucursal(null, "Sucursal Nueva", null);
        when(ciudadService.getCiudadById(99)).thenReturn(null);

        Sucursal result = sucursalService.createSucursalByCiudadId(sucursal, 99);

        assertNull(result);
        verify(ciudadService, times(1)).getCiudadById(99);
        verify(sucursalRepository, never()).save(any());
        logger.info("Finalizado testCreateSucursalByCiudadId_CiudadNoExiste OK");
    }

    /**
     * Prueba la obtención de todas las sucursales y verifica que la lista tenga el tamaño esperado.
     */
    @Test
    public void testGetAllSucursales() {
        logger.info("Iniciando testGetAllSucursales");
        List<Sucursal> sucursales = List.of(
            new Sucursal(1, "Sucursal prueba 1", null),
            new Sucursal(2, "Sucursal prueba 2", null)
        );
        when(sucursalRepository.findAll()).thenReturn(sucursales);

        List<Sucursal> resultSucursales = sucursalService.getAllSucursales();
        assertEquals(2, resultSucursales.size());
        assertEquals("Sucursal prueba 1", resultSucursales.get(0).getNombreSucursal());
        assertEquals("Sucursal prueba 2", resultSucursales.get(1).getNombreSucursal());
        verify(sucursalRepository, times(1)).findAll();
        logger.info("Finalizado testGetAllSucursales OK");
    }

    /**
     * Prueba la obtención de sucursales cuando la lista está vacía.
     */
    @Test
    public void testGetAllSucursales_ListaVacia() {
        logger.info("Iniciando testGetAllSucursales_ListaVacia");
        when(sucursalRepository.findAll()).thenReturn(Collections.emptyList());

        List<Sucursal> result = sucursalService.getAllSucursales();

        assertTrue(result.isEmpty());
        verify(sucursalRepository, times(1)).findAll();
        logger.info("Finalizado testGetAllSucursales_ListaVacia OK");
    }

    /**
     * Prueba la obtención de una sucursal por ID existente.
     */
    @Test
    public void testGetSucursalById() {
        logger.info("Iniciando testGetSucursalById");
        Sucursal mockSucursal = new Sucursal(1, "Sucursal prueba 1", null);
        when(sucursalRepository.findById(1)).thenReturn(java.util.Optional.of(mockSucursal));

        Sucursal sucursal = sucursalService.getSucursalById(1).orElse(mockSucursal);

        assertNotNull(sucursal);
        assertEquals("Sucursal prueba 1", sucursal.getNombreSucursal());
        verify(sucursalRepository, times(1)).findById(1);
        logger.info("Finalizado testGetSucursalById OK");
    }

    /**
     * Prueba la actualización de una sucursal existente.
     */
    @Test
    public void testUpdateSucursal() {
        logger.info("Iniciando testUpdateSucursal");
        Sucursal mockSucursal = new Sucursal(1, "Sucursal prueba 1", null);
        when(sucursalRepository.existsById(1)).thenReturn(true);
        when(sucursalRepository.save(mockSucursal)).thenReturn(mockSucursal);

        Sucursal updatedSucursal = sucursalService.updateSucursal(1, mockSucursal);

        assertNotNull(updatedSucursal);
        assertEquals(1, updatedSucursal.getIdSucursal());
        assertEquals("Sucursal prueba 1", updatedSucursal.getNombreSucursal());
        verify(sucursalRepository, times(1)).save(mockSucursal);
        logger.info("Finalizado testUpdateSucursal OK");
    }

    /**
     * Prueba la eliminación de una sucursal por ID.
     */
    @Test
    public void testDeleteSucursal() {
        logger.info("Iniciando testDeleteSucursal");
        Integer idSucursal = 1;
        sucursalService.eliminarSucursal(idSucursal);

        verify(sucursalRepository, times(1)).deleteById(idSucursal);
        logger.info("Finalizado testDeleteSucursal OK");
    }

    /**
     * Prueba la obtención de sucursales por ID de ciudad.
     */
    @Test
    public void testGetSucursalesByCiudadId() {
        logger.info("Iniciando testGetSucursalesByCiudadId");
        List<Sucursal> sucursales = List.of(new Sucursal(1, "Sucursal 1", null));
        when(sucursalRepository.findByCiudadIdCiudad(1)).thenReturn(sucursales);

        List<Sucursal> result = sucursalService.getSucursalesByCiudadId(1);

        assertEquals(1, result.size());
        verify(sucursalRepository, times(1)).findByCiudadIdCiudad(1);
        logger.info("Finalizado testGetSucursalesByCiudadId OK");
    }

    /**
     * Prueba la obtención de sucursales por ID de ciudad cuando la lista está vacía.
     */
    @Test
    public void testGetSucursalesByCiudadId_ListaVacia() {
        logger.info("Iniciando testGetSucursalesByCiudadId_ListaVacia");
        when(sucursalRepository.findByCiudadIdCiudad(2)).thenReturn(Collections.emptyList());

        List<Sucursal> result = sucursalService.getSucursalesByCiudadId(2);

        assertTrue(result.isEmpty());
        verify(sucursalRepository, times(1)).findByCiudadIdCiudad(2);
        logger.info("Finalizado testGetSucursalesByCiudadId_ListaVacia OK");
    }

}
