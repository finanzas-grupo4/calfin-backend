package com.calfin.calfin_app.bondManagement.interfaces.rest;

import com.calfin.calfin_app.bondManagement.application.internal.outboundServices.ExternaluserService;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteBondCommand;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import com.calfin.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;
import com.calfin.calfin_app.bondManagement.domain.services.BondCommandService;
import com.calfin.calfin_app.bondManagement.domain.services.BondQueryService;
import com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.BondRepository;
import com.calfin.calfin_app.bondManagement.interfaces.rest.resources.BondResource;
import com.calfin.calfin_app.bondManagement.interfaces.rest.resources.CreateBondResource;
import com.calfin.calfin_app.bondManagement.interfaces.rest.transform.BondResourceFromEntityAssembler;
import com.calfin.calfin_app.bondManagement.interfaces.rest.transform.CreateBondCommandFromResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping//(value = "/api/v1/bonds", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bonds", description = "Bond Endpoints")
public class BondsController {
    private final BondQueryService bondQueryService;
    private final BondCommandService bondCommandService;
    private final ExternaluserService externaluserService;

    public BondsController(BondQueryService bondQueryService, BondCommandService bondCommandService, ExternaluserService externaluserService) {
        this.bondQueryService = bondQueryService;
        this.bondCommandService = bondCommandService;
        this.externaluserService = externaluserService;
    }

    @PostMapping("/users/{userId}/bonds")
    public ResponseEntity<BondResource> createBond(@PathVariable Long userId, @RequestBody CreateBondResource resource){
        var optionalUser = externaluserService.fetchUserById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        var createBondCommand = CreateBondCommandFromResourceAssembler.toCommandFromResource(
                optionalUser.get(), resource);

        var bondId = bondCommandService.handle(createBondCommand);
        if (bondId == null) {
            throw new RuntimeException("Error while creating bond");
        }

        var getBondByIdQuery = new GetBondByIdQuery(bondId);
        var bond = bondQueryService.handle(getBondByIdQuery);
        if (bond.isEmpty()) {
            throw new RuntimeException("Bond not found with id: " + bondId);
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond.get());

        return new ResponseEntity<>(bondResource, HttpStatus.CREATED);

    }

    @GetMapping("/bonds/{bondId}")
    public ResponseEntity<BondResource> getBondById(@PathVariable Long bondId){
        var getBondByIdQuery = new GetBondByIdQuery(bondId);
        var optinalBond = this.bondQueryService.handle(getBondByIdQuery);
        if (optinalBond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(optinalBond.get());
        return new ResponseEntity<>(bondResource, HttpStatus.OK);
    }

    @GetMapping("/bonds")
    public ResponseEntity<List<BondResource>> getAllBonds(){
        var getAllBondsQuery = new GetAllBondsQuery();
        var bonds = this.bondQueryService.handle(getAllBondsQuery);
        if (bonds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var bondResources = bonds.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(bondResources, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}/bonds")
    public ResponseEntity<List<BondResource>> getBondsByUserId(@PathVariable Long userId){
        var getBondsByUserIdQuery = new GetBondsByUserIdQuery(userId);
        var bonds = this.bondQueryService.handle(getBondsByUserIdQuery);
        if (bonds.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var bondResources = bonds.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return new ResponseEntity<>(bondResources, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{bondId}")
    public ResponseEntity<Void> deleteBond(@PathVariable Long bondId){
        var getBondByIdQuery= new GetBondByIdQuery(bondId);
        var optionalBond = this.bondQueryService.handle(getBondByIdQuery);
        if (optionalBond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var deleteBondCommand = new DeleteBondCommand(bondId);
        this.bondCommandService.handle(deleteBondCommand);
        return ResponseEntity.noContent().build();
    }



}
