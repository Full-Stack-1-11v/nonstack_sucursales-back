package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.cl.microservicio_sucursales.assemblers.RegionModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.service.RegionService;

@WebMvcTest(RegionControllerV2.class)
public class RegionControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegionService regionService;

    @MockitoBean
    private RegionModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerRegionPorId() throws Exception {
        Region region = new Region(1, "Metropolitana");
        when(regionService.getRegionById(1)).thenReturn(region);
        when(assembler.toModel(region)).thenReturn(EntityModel.of(region));

        mockMvc.perform(get("/api/v2/regiones/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerRegionPorIdNoEncontrada() throws Exception {
        when(regionService.getRegionById(99)).thenReturn(null);

        mockMvc.perform(get("/api/v2/regiones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListarRegiones() throws Exception {
        Region region = new Region(1, "Metropolitana");
        when(regionService.getAllRegions()).thenReturn(List.of(region));
        when(assembler.toModel(any(Region.class))).thenReturn(EntityModel.of(region));

        mockMvc.perform(get("/api/v2/regiones").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearRegion() throws Exception {
        Region region = new Region(null, "Nueva");
        Region creada = new Region(10, "Nueva");
        when(regionService.saveRegion(any(Region.class))).thenReturn(creada);
        when(assembler.toModel(creada)).thenReturn(EntityModel.of(creada));

        mockMvc.perform(post("/api/v2/regiones")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarRegion() throws Exception {
        Region region = new Region(1, "Actualizada");
        when(regionService.updateRegion(eq(1), any(Region.class))).thenReturn(region);
        when(assembler.toModel(region)).thenReturn(EntityModel.of(region));

        mockMvc.perform(put("/api/v2/regiones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarRegionNoEncontrada() throws Exception {
        when(regionService.updateRegion(eq(99), any(Region.class))).thenReturn(null);

        mockMvc.perform(put("/api/v2/regiones/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Region())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarRegionParcial() throws Exception {
        Region region = new Region(1, "Parcial");
        when(regionService.patchRegion(eq(1), any(Region.class))).thenReturn(region);
        when(assembler.toModel(region)).thenReturn(EntityModel.of(region));

        mockMvc.perform(patch("/api/v2/regiones/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(region)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarRegionParcialNoEncontrada() throws Exception {
        when(regionService.patchRegion(eq(99), any(Region.class))).thenReturn(null);

        mockMvc.perform(patch("/api/v2/regiones/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Region())))
                .andExpect(status().isNotFound());
    }

    @Test
    void testEliminarRegion() throws Exception {
        Region region = new Region(1, "Metropolitana");
        when(regionService.getRegionById(1)).thenReturn(region);
        Mockito.doNothing().when(regionService).deleteById(1);

        mockMvc.perform(delete("/api/v2/regiones/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarRegionNoEncontrada() throws Exception {
        when(regionService.getRegionById(99)).thenReturn(null);

        mockMvc.perform(delete("/api/v2/regiones/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarRegionPorNombreJPQL() throws Exception {
        Region region = new Region(1, "Metropolitana");
        when(regionService.buscarRegionPorNombreJPQL("Metropolitana")).thenReturn(List.of(region));
        when(assembler.toModel(any(Region.class))).thenReturn(EntityModel.of(region));

        mockMvc.perform(get("/api/v2/regiones/buscar/jpql/Metropolitana").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarRegionPorNombreJPQLVacio() throws Exception {
        when(regionService.buscarRegionPorNombreJPQL("Desconocida")).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/regiones/buscar/jpql/Desconocida"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testBuscarRegionPorNombreNative() throws Exception {
        Region region = new Region(1, "Metropolitana");
        when(regionService.buscarRegionPorNombreNative("Metropolitana")).thenReturn(List.of(region));
        when(assembler.toModel(any(Region.class))).thenReturn(EntityModel.of(region));

        mockMvc.perform(get("/api/v2/regiones/buscar/native/Metropolitana").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testBuscarRegionPorNombreNativeVacio() throws Exception {
        when(regionService.buscarRegionPorNombreNative("Desconocida")).thenReturn(List.of());

        mockMvc.perform(get("/api/v2/regiones/buscar/native/Desconocida"))
                .andExpect(status().isNotFound());
    }
}
