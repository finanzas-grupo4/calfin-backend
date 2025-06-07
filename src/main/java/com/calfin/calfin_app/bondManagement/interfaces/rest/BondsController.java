package com.calfin.calfin_app.bondManagement.interfaces.rest;

import com.calfin.calfin_app.bondManagement.domain.services.BondCommandService;
import com.calfin.calfin_app.bondManagement.domain.services.BondQueryService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value = "/api/v1/bonds", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Bonds", description = "Bond Endpoints")
public class BondsController {
    private final BondQueryService bondQueryService;
    private final BondCommandService bondCommandService;


    public BondsController(BondQueryService bondQueryService, BondCommandService bondCommandService) {
        this.bondQueryService = bondQueryService;
        this.bondCommandService = bondCommandService;
    }


}
