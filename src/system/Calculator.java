package system;

import datamodel.Order;
import datamodel.OrderItem;
import datamodel.TAX;

/**
 * {@link Calculator} is a singleton {@link system} component that performs calculations.
 */
public interface Calculator {

    /**
     * Calculate the value of an order, which is comprised of the value of each ordered item.
     *
     * @param order the order to calculate the value for.
     * @return the value of the order.
     * @throws IllegalArgumentException if the order is null.
     */
    long calculateOrderValue(Order order);

    /**
     * Calculate the value of an order item, which is calculated by multiplying the unit price of the article by the number of units ordered.
     *
     * @param item the order item to calculate the value for.
     * @return the value of the order item.
     */
    long calculateOrderItemValue(OrderItem item);

    /**
     * Calculate the VAT (Value Added Tax) of an order, which is comprised of the VAT of each ordered item.
     *
     * @param order the order to calculate the VAT tax for.
     * @return the VAT calculated for the order.
     * @throws IllegalArgumentException if the order is null.
     */
    long calculateOrderVAT(Order order);

    /**
     * Calculate the included VAT of an order item based on the applicable tax rate and the calculated order item value.
     *
     * @param item the order item to calculate the VAT for.
     * @return the VAT for the order item.
     */
    long calculateOrderItemVAT(OrderItem item);

    /**
     * Calculate the included VAT (Value Added Tax) from a gross price/value based on a tax rate.
     *
     * @param grossValue the value that includes the tax.
     * @param tax        the applicable tax rate in percent.
     * @return the tax included in the gross value.
     */
    long calculateVAT(long grossValue, TAX tax);

    /**
     * Return the tax rate as a double value.
     *
     * @param taxRate the applicable tax rate.
     * @return the tax rate as a double value.
     */
    double value(TAX taxRate);
}