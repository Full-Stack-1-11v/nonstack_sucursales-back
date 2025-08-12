package com.perfulandia.cl.microservicio_sucursales.assemblers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Region;

public class CiudadModelAssemblerTest {

    private CiudadModelAssembler assembler;

    @BeforeEach
    public void setUp() {
        assembler = new CiudadModelAssembler();
    }

    @Test
    public void toModel_debeRetornarEntityModelConLinks_CuandoRegionPresente() {
        // Arrange
        Region region = new Region(5, "Región Central");
        Ciudad ciudad = new Ciudad(10, "Santiago", region);

        // Act
        EntityModel<Ciudad> model = assembler.toModel(ciudad);

        // Assert
        assertThat(model.getContent()).isEqualTo(ciudad);

        // Self link
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("self").get().getHref())
            .contains("/api/v2/sucursales/ciudades/10");

        // Collection link
        assertThat(model.getLink("ciudades")).isPresent();
        assertThat(model.getLink("ciudades").get().getHref())
            .contains("/api/v2/sucursales/ciudades");

        // Region link
        assertThat(model.getLink("ciudades-por-region")).isPresent();
        assertThat(model.getLink("ciudades-por-region").get().getHref())
            .contains("/api/v2/sucursales/ciudades/region/5");
    }

    @Test
    public void toModel_noDebeAgregarLinkRegionCuandoEsNulo() {
        // Arrange
        Ciudad ciudad = new Ciudad(20, "Valdivia", null);

        // Act
        EntityModel<Ciudad> model = assembler.toModel(ciudad);

        // Assert
        assertThat(model.getLink("ciudades-por-region")).isNotPresent();
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("ciudades")).isPresent();
    }
}
