package com.calfin.calfin_app.bondManagement.application.internal.CommandService;

import com.calfin.calfin_app.bondManagement.domain.model.aggregates.Bond;
import com.calfin.calfin_app.bondManagement.domain.model.aggregates.CashFlows;
import com.calfin.calfin_app.bondManagement.domain.model.entities.CashFlow;
import com.calfin.calfin_app.bondManagement.domain.model.queries.DeleteCashFlowsCommand;
import com.calfin.calfin_app.bondManagement.domain.model.valueObjects.GracePeriodType;
import com.calfin.calfin_app.bondManagement.domain.services.CashFlowsCommandService;
import com.calfin.calfin_app.bondManagement.infrastructure.persistence.jpa.repositories.CashFlowsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CashFlowsCommandServiceImpl implements CashFlowsCommandService {
    private final CashFlowsRepository repository;

    public CashFlowsCommandServiceImpl(CashFlowsRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(DeleteCashFlowsCommand command){
        var optionalCashFlows = this.repository.findById(command.cashFlowsId());
        if (optionalCashFlows.isPresent()) {
            this.repository.delete(optionalCashFlows.get());
        } else {
            throw new RuntimeException("Cash flows not found with ID: " + command.cashFlowsId());
        }
    }

    @Override
    public Long handle(Bond bond) {

        var cashFlows = new CashFlows(bond);

        // Generar los flujos de efectivo
        List<CashFlow> flows = generateCashFlows(bond, cashFlows);
        cashFlows.setFlows(flows);

        // Calcular el precio del bono
        double bondPrice = calculateBondPrice(flows, bond.getDiscountRate());
        cashFlows.setPrecioTeorico((int) Math.round(bondPrice));

        // Calcular duración, duración modificada, TCEA, TREA y precio máximo
        double duration = calculateDuration(flows, bond.getDiscountRate());
        double modifiedDuration = calculateModifiedDuration(duration, bond.getDiscountRate());
        double tcea = calculateTCEA(flows, bond.getNominalValue());
        double maxPrice = calculateMaxPrice(flows, bond.getDiscountRate());

        cashFlows.setDuracion(duration);
        cashFlows.setDuracionModificada(modifiedDuration);
        cashFlows.setTcea(tcea);
        cashFlows.setPrecioMaximo(maxPrice);

        System.out.println("Setteo con exito");

        try {
            this.repository.save(cashFlows);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving cash flows: " + e.getMessage());
        }

        System.out.println("No errores");
        return cashFlows.getId();
    }

    private List<CashFlow> generateCashFlows(Bond bond, CashFlows cashFlows) {
        System.out.println("Generando cash flows");

        int paymentsPerYear = getPaymentsPerYear(bond.getPaymentFrequency().getDescription());
        double periodicRate = getPeriodicRate(bond);

        int termInMonths = bond.getTermUnit().getValue().equalsIgnoreCase("years") ? bond.getTerm() * 12 : bond.getTerm();
        int totalPayments = (int) Math.ceil(termInMonths / (12.0 / paymentsPerYear));
        int paymentIntervalMonths = 12 / paymentsPerYear;

        int gracePeriodPayments = bond.isHasGracePeriod() ? bond.getGracePeriodLength() : 0;
        int remainingPayments = totalPayments - gracePeriodPayments;

        if (remainingPayments <= 0) {
            throw new IllegalArgumentException("El número de pagos restantes debe ser mayor a cero.");
        }

        double paymentAmount = calculatePaymentAmount(bond.getNominalValue(), periodicRate, remainingPayments);

        List<CashFlow> cashFlowsList = new ArrayList<>();
        double balance = bond.getNominalValue();
        LocalDate currentDate = bond.getIssueDate();

        for (int period = 1; period <= totalPayments; period++) {
            currentDate = currentDate.plusMonths(paymentIntervalMonths);

            double interestPayment = balance * periodicRate;
            double principalPayment = 0;
            double totalPayment = 0;

            if (period <= gracePeriodPayments) {
                if (bond.getGracePeriodType() == GracePeriodType.TOTAL) {
                    // Período de gracia total: no hay pagos
                    principalPayment = 0;
                    totalPayment = 0;
                } else {
                    // Período de gracia parcial: solo pagos de intereses
                    principalPayment = 0;
                    totalPayment = interestPayment;
                }
            } else {
                // Período regular de pagos
                totalPayment = paymentAmount;
                principalPayment = totalPayment - interestPayment;

                // Ajustar el último pago para evitar problemas de redondeo
                if (balance - principalPayment < 0.01) {
                    principalPayment = balance;
                    totalPayment = principalPayment + interestPayment;
                }
            }

            double endingBalance = Math.max(0, balance - principalPayment);

            CashFlow cashFlow = new CashFlow(
                    period,
                    currentDate,
                    balance,
                    interestPayment,
                    principalPayment,
                    totalPayment,
                    endingBalance
            );

            // Asignar el objeto CashFlows al flujo
            cashFlow.setCashFlows(cashFlows);
            cashFlowsList.add(cashFlow);

            balance = endingBalance;

            if (balance == 0) break;
        }

        return cashFlowsList;
    }

    private double calculatePaymentAmount(double principal, double periodicRate, int numberOfPayments) {
        System.out.println("Calculando payment amount");
        return (principal * periodicRate) / (1 - Math.pow(1 + periodicRate, -numberOfPayments));
    }

    private double calculateDuration(List<CashFlow> cashFlows, double discountRate) {
        System.out.println("Calculando duracion");

        double duration = 0;
        double presentValueTotal = 0;
        double periodicDiscountRate = discountRate / 100 / 2; // Semi-anual

        for (int i = 0; i < cashFlows.size(); i++) {
            CashFlow flow = cashFlows.get(i);
            int periodNumber = i + 1;
            double discountFactor = Math.pow(1 + periodicDiscountRate, -periodNumber);
            double presentValue = flow.getTotalPayment() * discountFactor;

            duration += periodNumber * presentValue;
            presentValueTotal += presentValue;
        }

        return duration / presentValueTotal;
    }

    private double calculateModifiedDuration(double duration, double discountRate) {
        double periodicDiscountRate = discountRate / 100 / 2; // Semi-anual
        System.out.println("Calculando duracion modificada");

        return duration / (1 + periodicDiscountRate);
    }

    private double calculateTCEA(List<CashFlow> cashFlows, double nominalValue) {
        System.out.println("Calculando TCEA");

        double tcea = 0;
        double guessRate = 0.1; // Tasa inicial para iterar
        double tolerance = 1e-6;
        int maxIterations = 100;

        for (int i = 0; i < maxIterations; i++) {
            double npv = 0;
            double derivative = 0;

            for (int j = 0; j < cashFlows.size(); j++) {
                CashFlow flow = cashFlows.get(j);
                int period = j + 1;
                double discountFactor = Math.pow(1 + guessRate, -period);

                npv += flow.getTotalPayment() * discountFactor;
                derivative += -period * flow.getTotalPayment() * discountFactor / (1 + guessRate);
            }

            npv -= nominalValue;

            if (Math.abs(npv) < tolerance) {
                tcea = Math.pow(1 + guessRate, 2) - 1; // Convertir a anual
                break;
            }

            guessRate -= npv / derivative;
        }

        return tcea * 100; // En porcentaje
    }

    private double calculateMaxPrice(List<CashFlow> cashFlows, double discountRate) {
        System.out.println("Calculando precio maximo");

        double maxPrice = 0;
        double periodicDiscountRate = discountRate / 100 / 2; // Semi-anual

        for (int i = 0; i < cashFlows.size(); i++) {
            CashFlow flow = cashFlows.get(i);
            int periodNumber = i + 1;
            double discountFactor = Math.pow(1 + periodicDiscountRate, -periodNumber);
            maxPrice += flow.getTotalPayment() * discountFactor;
        }

        return maxPrice;
    }

    private double calculateBondPrice(List<CashFlow> cashFlows, double discountRate) {
        System.out.println("Calculando precio del bono");

        double periodicDiscountRate = discountRate / 100 / 2; // Semi-annual
        double presentValue = 0;

        for (int i = 0; i < cashFlows.size(); i++) {
            CashFlow flow = cashFlows.get(i);
            int periodNumber = i + 1;
            double discountFactor = Math.pow(1 + periodicDiscountRate, -periodNumber);
            presentValue += flow.getTotalPayment() * discountFactor;
        }

        return presentValue;
    }

    private int getPaymentsPerYear(String frequency) {
        System.out.println("Get payment per year");

        switch (frequency.toLowerCase()) {
            case "monthly":
                return 12;
            case "quarterly":
                return 4;
            case "semi-annual":
                return 2;
            case "annual":
                return 1;
            default:
                return 12;
        }
    }

    private double getPeriodicRate(Bond bond) {
        System.out.println("Get periodic rate");

        int paymentsPerYear = getPaymentsPerYear(bond.getPaymentFrequency().getDescription());
        double effectiveAnnualRate = bond.isEffectiveRate()
                ? bond.getInterestRate() / 100
                : nominalToEffective(bond.getInterestRate(), bond.getCompoundingFrequency().getDescription());
        return Math.pow(1 + effectiveAnnualRate, 1.0 / paymentsPerYear) - 1;
    }

    private double nominalToEffective(double nominalRate, String compoundingFrequency) {
        System.out.println("Nominal to efective");

        int m = getCompoundingFactor(compoundingFrequency);
        if (m == Integer.MAX_VALUE) {
            return Math.exp(nominalRate / 100) - 1;
        } else {
            return Math.pow(1 + nominalRate / 100 / m, m) - 1;
        }
    }

    private int getCompoundingFactor(String frequency) {
        System.out.println("Get compounding factor");

        switch (frequency.toLowerCase()) {
            case "monthly":
                return 12;
            case "quarterly":
                return 4;
            case "semi-annual":
                return 2;
            case "annual":
                return 1;
            case "continuous":
                return Integer.MAX_VALUE;
            default:
                return 12;
        }
    }
}