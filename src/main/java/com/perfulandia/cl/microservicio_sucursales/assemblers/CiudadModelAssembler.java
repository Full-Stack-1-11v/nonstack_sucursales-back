package com.perfulandia.cl.microservicio_sucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.perfulandia.cl.microservicio_sucursales.controller.CiudadController;
import com.perfulandia.cl.microservicio_sucursales.model.Ciudad;

@Component
public class CiudadModelAssembler extends RepresentationModelAssemblerSupport<Ciudad, EntityModel<Ciudad>> {

    public CiudadModelAssembler() {
        super(CiudadController.class, (Class<EntityModel<Ciudad>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Ciudad> toModel(Ciudad ciudad) {
        return EntityModel.of(ciudad,
                linkTo(methodOn(CiudadController.class).actualizarCiudad(ciudad.getIdCiudad(), ciudad)).withSelfRel(),
                linkTo(methodOn(CiudadController.class).listarCiudades()).withRel("ciudades"),
                linkTo(methodOn(CiudadController.class).listarCiudadesPorRegion(
                        ciudad.getRegion() != null ? ciudad.getRegion().getIdRegion() : null
                )).withRel("ciudades-por-region")
        );
    }
}