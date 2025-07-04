package com.perfulandia.cl.microservicio_sucursales.controller;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.cl.microservicio_sucursales.assemblers.SucursalModelAssembler;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;
import com.perfulandia.cl.microservicio_sucursales.service.SucursalService;

@WebMvcTest(SucursalControllerV2.class)
public class SucursalControllerV2Test {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SucursalService sucursalService;

    @MockitoBean
    private SucursalModelAssembler sucursalModelAssembler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerSucursalPorId() throws Exception {
        Ciudad ciudad = new Ciudad(1, "Santiago", new Region(1, "Metropolitana"));
        Sucursal sucursal = new Sucursal(1, "Sucursal Central", ciudad);
        when(sucursalService.getSucursalById(1)).thenReturn(Optional.of(sucursal));
        when(sucursalModelAssembler.toModel(sucursal)).thenReturn(EntityModel.of(sucursal));

        mockMvc.perform(get("/api/v2/sucursales/1").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testObtenerSucursalPorIdNoEncontrada() throws Exception {
        when(sucursalService.getSucursalById(99)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v2/sucursales/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListarSucursales() throws Exception {
        Ciudad ciudad = new Ciudad(1, "Santiago", null);
        Sucursal sucursal = new Sucursal(1, "Sucursal Central", ciudad);
        when(sucursalService.getAllSucursales()).thenReturn(List.of(sucursal));
        when(sucursalModelAssembler.toModel(any(Sucursal.class))).thenReturn(EntityModel.of(sucursal));

        mockMvc.perform(get("/api/v2/sucursales").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearSucursal() throws Exception {
        Region region = new Region(1, "Metropolitana");
        Ciudad ciudad = new Ciudad(1, "Santiago", region);
        Sucursal sucursal = new Sucursal(null, "Sucursal Nueva", ciudad);
        Sucursal creada = new Sucursal(10, "Sucursal Nueva", ciudad);

        // Crea un EntityModel con un self link para evitar errores de HATEOAS
        EntityModel<Sucursal> entityModel = EntityModel.of(
            creada,
            org.springframework.hateoas.Link.of("/api/v2/sucursales/10").withSelfRel()
        );

        when(sucursalService.createSucursal(any(Sucursal.class))).thenReturn(creada);
        when(sucursalModelAssembler.toModel(creada)).thenReturn(entityModel);

        mockMvc.perform(post("/api/v2/sucursales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isCreated());
    }

    @Test
    void testActualizarSucursal() throws Exception {
        Ciudad ciudad = new Ciudad(1, "Santiago", null);
        Sucursal sucursal = new Sucursal(1, "Sucursal Actualizada", ciudad);
        when(sucursalService.updateSucursal(eq(1), any(Sucursal.class))).thenReturn(sucursal);
        when(sucursalModelAssembler.toModel(sucursal)).thenReturn(EntityModel.of(sucursal));

        mockMvc.perform(put("/api/v2/sucursales/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sucursal)))
                .andExpect(status().isOk());
    }

    @Test
    void testActualizarSucursalNoEncontrada() throws Exception {
        when(sucursalService.updateSucursal(eq(99), any(Sucursal.class))).thenReturn(null);

        mockMvc.perform(put("/api/v2/sucursales/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Sucursal())))
                .andExpect(status().isNotFound());
    }
}
