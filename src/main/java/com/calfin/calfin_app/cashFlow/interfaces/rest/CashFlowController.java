package com.calfin.calfin_app.cashFlow.interfaces.rest;

import com.calfin.calfin_app.cashFlow.domain.model.aggregates.CashFlow;
import com.calfin.calfin_app.cashFlow.domain.services.CashFlowCommandService;
import com.calfin.calfin_app.cashFlow.domain.services.CashFlowQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController
public class CashFlowController {
    private final CashFlowQueryService cashFlowQueryService;
    private final CashFlowCommandService cashFlowCommandService;

    public CashFlowController(CashFlowQueryService cashFlowQueryService, CashFlowCommandService cashFlowCommandService) {
        this.cashFlowQueryService = cashFlowQueryService;
        this.cashFlowCommandService = cashFlowCommandService;
    }

    @GetMapping("/cashflow/{id}")
    public ResponseEntity<CashFlow> getCashFlowById(@PathVariable Long id){
        var optionalCashFlow = this.cash
0
        return
    }
}
*/