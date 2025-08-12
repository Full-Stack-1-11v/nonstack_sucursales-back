package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.cl.microservicio_sucursales.assemblers.CiudadModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.CiudadService;

@WebMvcTest(CiudadControllerV2.class)
public class CiudadControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CiudadService ciudadService;

    @MockitoBean
    private CiudadModelAssembler ciudadModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerCiudadPorId() throws Exception {
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(1, "Santiago", region);
        when(ciudadService.getCiudadById(1)).thenReturn(ciudad);
        when(ciudadModelAssembler.toModel(ciudad)).thenCallRealMethod();

        mockMvc.perform(get("/api/v2/sucursales/ciudades/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerCiudadPorIdNoEncontrada() throws Exception {
        when(ciudadService.getCiudadById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v2/sucursales/ciudades/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListarCiudades() throws Exception {
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(1, "Santiago", region);
        when(ciudadService.getAllCiudades()).thenReturn(List.of(ciudad));
        when(ciudadModelAssembler.toModel(any(Ciudad.class))).thenCallRealMethod();

        mockMvc.perform(get("/api/v2/sucursales/ciudades").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListarCiudadesPorRegion() throws Exception {
        Ciudad ciudad = new Ciudad(1, "Santiago", new Region(1, "Metropolitana"));
        when(ciudadService.getCiudadesByRegionId(1)).thenReturn(List.of(ciudad));
        when(ciudadModelAssembler.toModel(any(Ciudad.class))).thenCallRealMethod();

        mockMvc.perform(get("/api/v2/sucursales/ciudades/region/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListarCiudadesPorRegionVacio() throws Exception {
        when(ciudadService.getCiudadesByRegionId(2)).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/sucursales/ciudades/region/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCrearCiudad() throws Exception {
        Ciudad ciudad = new Ciudad(null, "Nueva", null);
        Ciudad creada = new Ciudad(10, "Nueva", null);
        when(ciudadService.createCiudad(any(Ciudad.class))).thenReturn(creada);
        when(ciudadModelAssembler.toModel(creada)).thenReturn(org.springframework.hateoas.EntityModel.of(creada));

        mockMvc.perform(post("/api/v2/sucursales/ciudades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ciudad)))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarCiudad() throws Exception {
        Ciudad ciudad = new Ciudad(1, null, null);
        Ciudad updated = new Ciudad(1, "Actualizada", null);
        when(ciudadService.getCiudadById(1)).thenReturn(ciudad);
        when(ciudadService.updateCiudad(eq(1), any(Ciudad.class))).thenReturn(updated);
        when(ciudadModelAssembler.toModel(ciudad)).thenCallRealMethod();
        when(ciudadModelAssembler.toModel(updated)).thenReturn(org.springframework.hateoas.EntityModel.of(updated));

        mockMvc.perform(put("/api/v2/sucursales/ciudades/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarCiudadNoEncontrada() throws Exception {
        when(ciudadService.updateCiudad(eq(99), any(Ciudad.class))).thenReturn(null);

        mockMvc.perform(put("/api/v2/sucursales/ciudades/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Ciudad())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarCiudad() throws Exception {
        Mockito.doNothing().when(ciudadService).deleteCiudad(1);

        mockMvc.perform(delete("/api/v2/sucursales/ciudades/1"))
                .andExpect(status().isNoContent());
    }
}
