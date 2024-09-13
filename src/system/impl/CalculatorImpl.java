package system.impl;

import system.Calculator;
import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;

import java.util.Map;

class CalculatorImpl implements Calculator {

    private final Map<TAX, Double> taxRateMapper = Map.of(
            TAX.TAXFREE, 0.0,   // tax free rate
            TAX.GER_VAT, 0.19,   // German VAT tax (MwSt) 19.0%
            TAX.GER_VAT_REDUCED, 0.07    // German reduced VAT tax (MwSt) 7.0%
    );

    @Override
    public long calculateOrderValue(final Order order) {
        if (order == null)
            throw new IllegalArgumentException("argument order is null.");

        long value = 0;
        for (OrderItem oi : order.getItems()) {
            value += calculateOrderItemValue(oi);
        }
        return value;
    }

    @Override
    public long calculateOrderItemValue(final OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("argument item is null.");
        }

        return item.getArticle().getUnitPrice() * item.getUnitsOrdered();
    }

    @Override
    public long calculateOrderVAT(final Order order) {
        if (order == null)
            throw new IllegalArgumentException("argument order is null.");

        long value = 0;
        for (OrderItem oi : order.getItems()) {
            value += calculateOrderItemVAT(oi);
        }
        return value;
    }

    @Override
    public long calculateOrderItemVAT(final OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("argument item is null.");
        }

        double percentage = 0;
        if (item.getArticle().getTax() == TAX.GER_VAT) {
            percentage = 0.19;
        } else if (item.getArticle().getTax() == TAX.GER_VAT_REDUCED) {
            percentage = 0.07;
        }

        long priceInCent = item.getArticle().getUnitPrice() * item.getUnitsOrdered();
        double priceInEuro = (double) priceInCent / 100;
        double nettoPreis = priceInEuro / (1.0 + percentage);
        double mehrwertsteuer = nettoPreis * percentage;

        return Math.round(mehrwertsteuer * 100);
    }

    @Override
    public long calculateVAT(final long grossValue, final TAX tax) {
        if (tax == null) {
            throw new IllegalArgumentException("argument taxRate is null.");
        } else if(grossValue < 0){
            return 0;
        }

        double taxRate = taxRateMapper.get(tax);
        double netValue = grossValue / (1.0 + taxRate);
        double calculatedTax = grossValue - netValue;

        return Math.round(calculatedTax);
    }

    @Override
    public double value(final TAX tax) {
        if (tax == null)
            throw new IllegalArgumentException("argument taxRate is null.");

        return Math.round(taxRateMapper.get(tax) * 100);
    }
}