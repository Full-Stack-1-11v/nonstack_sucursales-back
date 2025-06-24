package com.perfulandia.cl.microservicio_sucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.perfulandia.cl.microservicio_sucursales.controller.SucursalController;
import com.perfulandia.cl.microservicio_sucursales.model.Sucursal;

@Component
public class SucursalModelAssembler extends RepresentationModelAssemblerSupport<Sucursal, EntityModel<Sucursal>> {

    public SucursalModelAssembler() {
        super(SucursalController.class, (Class<EntityModel<Sucursal>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Sucursal> toModel(Sucursal sucursal) {
        return EntityModel.of(sucursal,
                linkTo(methodOn(SucursalController.class).obtenerSucursalPorId(sucursal.getIdSucursal())).withSelfRel(),
                linkTo(methodOn(SucursalController.class).listarSucursales()).withRel("sucursales")
        );
    }
}