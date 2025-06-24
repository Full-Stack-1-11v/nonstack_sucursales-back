package com.perfulandia.cl.microservicio_sucursales.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;

import com.perfulandia.cl.microservicio_sucursales.controller.RegionController;
import com.perfulandia.cl.microservicio_sucursales.model.Region;

@Component
public class RegionModelAssembler extends RepresentationModelAssemblerSupport<Region, EntityModel<Region>> {

    public RegionModelAssembler() {
        super(RegionController.class, (Class<EntityModel<Region>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Region> toModel(Region region) {
        return EntityModel.of(region,
                linkTo(methodOn(RegionController.class).obtenerRegionPorId(region.getIdRegion())).withSelfRel(),
                linkTo(methodOn(RegionController.class).listarRegiones()).withRel("regiones")
        );
    }
}