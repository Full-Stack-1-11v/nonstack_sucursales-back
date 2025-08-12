package com.perfulandia.cl.microservicio_sucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.perfulandia.cl.microservicio_sucursales.controller.CiudadControllerV2;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;

@Component
public class CiudadModelAssembler extends RepresentationModelAssemblerSupport<Ciudad, EntityModel<Ciudad>> {

    public CiudadModelAssembler() {
        super(CiudadControllerV2.class, (Class<EntityModel<Ciudad>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Ciudad> toModel(Ciudad ciudad) {
        EntityModel<Ciudad> model = EntityModel.of(
            ciudad,
            linkTo(methodOn(CiudadControllerV2.class).obtenerCiudadPorId(ciudad.getIdCiudad())).withSelfRel(),
            linkTo(methodOn(CiudadControllerV2.class).listarCiudades()).withRel("ciudades")
        );
        if (ciudad.getRegion() != null && ciudad.getRegion().getIdRegion() != null) {
            model.add(
                linkTo(methodOn(CiudadControllerV2.class)
                    .listarCiudadesPorRegion(ciudad.getRegion().getIdRegion()))
                    .withRel("ciudades-por-region")
            );
        }
        return model;
    }
}