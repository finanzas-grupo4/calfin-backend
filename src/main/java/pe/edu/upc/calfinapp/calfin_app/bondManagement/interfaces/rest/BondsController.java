package pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest;

import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.DeleteBondCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.commands.DeleteCashFlowsCommand;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetAllBondsQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondByIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetBondsByUserIdQuery;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.model.queries.GetCashFlowsByBondId;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondCommandService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.BondQueryService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.CashFlowsCommandService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.domain.services.CashFlowsQueryService;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.BondResource;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.CashFlowsResource;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.CreateBondResource;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.resources.UpdateBondResource;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform.BondResourceFromEntityAssembler;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform.CashFlowResourceFromEntityAssembler;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform.CreateBondCommandFromResourceAssembler;
import pe.edu.upc.calfinapp.calfin_app.bondManagement.interfaces.rest.transform.UpdateBondCommandFromResourceAssembler;
import pe.edu.upc.calfinapp.calfin_app.iam.infrastructure.security.SecurityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.out;

@RestController
@RequestMapping(value = "/api/v1/bonds", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bonds", description = "Bond Endpoints")
public class BondsController {
    private final BondQueryService bondQueryService;
    private final BondCommandService bondCommandService;

    private final CashFlowsQueryService cashFlowsQueryService;
    private final CashFlowsCommandService cashFlowsCommandService;

    public BondsController(BondQueryService bondQueryService, BondCommandService bondCommandService, CashFlowsQueryService cashFlowsQueryService, CashFlowsCommandService cashFlowsCommandService) {
        this.bondQueryService = bondQueryService;
        this.bondCommandService = bondCommandService;
        this.cashFlowsQueryService = cashFlowsQueryService;
        this.cashFlowsCommandService = cashFlowsCommandService;
    }

    @PostMapping("/create-bonds")
    public ResponseEntity<BondResource> createBond(@RequestBody CreateBondResource resource){
        Long userId = SecurityUtils.getCurrentUserId();
        out.println(">>> UserID actual: " + userId);  // <-- Asegura que no sea null
        var createBondCommand = CreateBondCommandFromResourceAssembler.toCommandFromResource(resource,userId);
        var bond = bondCommandService.handle(createBondCommand);
        if (bond==null) {
            return ResponseEntity.badRequest().build();
        }

        //crear cash flows
        this.cashFlowsCommandService.handle(bond);

        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond);
        return new ResponseEntity<>(bondResource,HttpStatus.CREATED);
    }

    @GetMapping("/{bondId}")
    public ResponseEntity<BondResource> getBondById(@PathVariable Long bondId){
        if (bondId == null) {
            return ResponseEntity.badRequest().build();
        }
        var getBondByIdQuery = new GetBondByIdQuery(bondId);
        var bond = bondQueryService.handle(getBondByIdQuery);
        if (bond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(bond.get());
        return new ResponseEntity<>(bondResource, HttpStatus.OK);
    }

    @GetMapping("/all-bonds")
    public ResponseEntity<List<BondResource>> getAllBonds(){
        var getAllBondsQuery = new GetAllBondsQuery();
        var bonds = bondQueryService.handle(getAllBondsQuery);
        var bondResources = bonds.stream()
                .map(BondResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
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

    @DeleteMapping("/{bondId}/delete-bond")
    public ResponseEntity<Void> deleteBond(@PathVariable Long bondId){

        var cashFlowsByBondIdQuery = new GetCashFlowsByBondId(bondId);
        var optionalCashFlows = this.cashFlowsQueryService.handle(cashFlowsByBondIdQuery);
        var DeleteleCashFlows = new DeleteCashFlowsCommand(optionalCashFlows.get().getId());
        this.cashFlowsCommandService.handle(DeleteleCashFlows);

        if (bondId == null) {
            return ResponseEntity.badRequest().build();
        }
        bondCommandService.handle(new DeleteBondCommand(bondId));
        out.println(">>> Bond with ID " + bondId + " deleted successfully.");
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{bondId}/update-bond")
    public ResponseEntity<BondResource> updateBond(@PathVariable Long bondId, @RequestBody UpdateBondResource resource){
        var getBondByIdQuery = new GetBondByIdQuery(bondId);
        var optionalBond = this.bondQueryService.handle(getBondByIdQuery);
        if (optionalBond.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var updateBondCommand = UpdateBondCommandFromResourceAssembler.toCommandFromResource(bondId, resource);
        var updatedBond = this.bondCommandService.handle(updateBondCommand);
        if (updatedBond.isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        var cashFlowsByBondIdQuery = new GetCashFlowsByBondId(updatedBond.get().getId());
        var optionalCashFlows = this.cashFlowsQueryService.handle(cashFlowsByBondIdQuery);
        var DeleteleCashFlows = new DeleteCashFlowsCommand(optionalCashFlows.get().getId());
        this.cashFlowsCommandService.handle(DeleteleCashFlows);
        this.cashFlowsCommandService.handle(updatedBond.get());

        var bondResource = BondResourceFromEntityAssembler.toResourceFromEntity(updatedBond.get());
        return ResponseEntity.ok(bondResource);
    }

    //cash flows
    @GetMapping("/bonds/{bondId}/cashflows")
    public ResponseEntity<CashFlowsResource> getBondCashFlow(@PathVariable Long bondId){
        var getCashFlowsByBondId = new GetCashFlowsByBondId(bondId);
        var optionalCashFlows = this.cashFlowsQueryService.handle(getCashFlowsByBondId);
        if (optionalCashFlows.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var cashFlowsResource = CashFlowResourceFromEntityAssembler.toResourceFromEntity(optionalCashFlows.get());
        return ResponseEntity.ok(cashFlowsResource);
    }

}

