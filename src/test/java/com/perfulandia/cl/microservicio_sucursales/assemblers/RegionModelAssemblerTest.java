package com.perfulandia.cl.microservicio_sucursales.assemblers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import com.perfulandia.cl.microservicio_sucursales.model.Region;

public class RegionModelAssemblerTest {

    private RegionModelAssembler assembler;

    @BeforeEach
    public void setUp() {
        assembler = new RegionModelAssembler();
    }

    @Test
    public void toModel_deberiaRetornarEntityModelConLinks() {
        // Arrange
        Region region = new Region(1, "Región de Prueba");

        // Act
        EntityModel<Region> model = assembler.toModel(region);

        // Assert
        assertThat(model.getContent()).isEqualTo(region);

        // Verifica self link
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("self").get().getHref())
            .contains("/api/v2/regiones/1");

        // Verifica link de colección
        assertThat(model.getLink("regiones")).isPresent();
        assertThat(model.getLink("regiones").get().getHref())
            .contains("/api/v2/regiones");
    }
}
