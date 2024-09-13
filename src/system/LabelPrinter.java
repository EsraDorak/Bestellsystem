package system;

import datamodel.Customer;
import datamodel.Order;


/**
 * {@link LabelPrinter} is a singleton {@link system} component to print
 * address labels.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface LabelPrinter {

    /**
     * Print address label for an order with the address of the
     * customer owning the order.
     * 
     * @param order print label for this order.
     * @return StringBuilder with rendered address label.
     */
    StringBuilder printLabel(Order order);

    /**
     * Print address labels for orders with customer addresses obtained
     * from orders.
     * 
     * @param orders print labels for orders.
     * @return StringBuilder with rendered address label.
     */
    StringBuilder printLabels(Iterable<Order> orders);

    /**
     * Print address label with customer address.
     * 
     * @param customer print label for this customer.
     * @return StringBuilder with rendered address label.
     */
    StringBuilder printLabel(Customer customer);

    /**
     * Print label from lines, e.g. from five lines:
     * <pre>
     * +------------------------------------+
     * |                          7372561535| - line 1
     * |  Meyer, Eric                       | - line 2
     * |  Luxemburger Strasse 10            | - line 3
     * |  D-13353 Berlin                    | - line 4
     * |                                    | - line 5
     * +------------------------------------+
     * </pre>
     * 
     * @param lines vararg Array of lines for each label. 
     * @return StringBuilder with rendered address label.
     */
    StringBuilder printLabel(String... lines);

    /**
     * Return inner with of label, default is 36 characters.
     * 
     * @return inner with of label.
     */
    int getWidth();

    /**
     * Clear internal StringBuilder, remove all content and set length to 0.
     */
    void clear();

}
