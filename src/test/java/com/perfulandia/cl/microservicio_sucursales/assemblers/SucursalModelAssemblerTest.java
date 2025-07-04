package com.perfulandia.cl.microservicio_sucursales.assemblers;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.EntityModel;

import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;

public class SucursalModelAssemblerTest {

    private SucursalModelAssembler assembler;

    @BeforeEach
    public void setUp() {
        assembler = new SucursalModelAssembler();
    }
    /**
     * Verifica que el método toModel retorne un EntityModel con los enlaces correctos.
     * Se asegura de que el enlace self y el enlace a la colección de sucursales estén presentes.
     */
    @Test
    public void toModel_debeRetornarEntityModelConLinks() {
        // Arrange
        Ciudad ciudad = new Ciudad(2, "Valdivia", null);
        Sucursal sucursal = new Sucursal(5, "Sucursal Centro", ciudad);

        // Act
        EntityModel<Sucursal> model = assembler.toModel(sucursal);

        // Assert
        assertThat(model.getContent()).isEqualTo(sucursal);

        // Self link
        assertThat(model.getLink("self")).isPresent();
        assertThat(model.getLink("self").get().getHref())
            .contains("/api/v2/sucursales/5");

        // Collection link
        assertThat(model.getLink("sucursales")).isPresent();
        assertThat(model.getLink("sucursales").get().getHref())
            .contains("/api/v2/sucursales");
    }
}
