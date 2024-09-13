package datamodel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Class of entity type <i>Order</i>.
 * <p>
 * Order defines a relationship between a Customer and the seller over items  to purchase.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Order {



    /**
     * Unique id, null or "" are invalid values, id can be set only once.
     */
    private String id = null;

    /**
     * Reference to owning Customer, final, never null.
     */
    private final Customer customer;

    /**
     * Date/time the order was created.
     */
    private final Date creationDate;

    /**
     * Items that are ordered as part of this order.
     */
    private final List<OrderItem> items;

    /**
     * 
     */
    public Customer owns;




    /**
     * Constructor with customer owning the order.
     * @param customer customer as owner of order, customer who placed that order.
     * @throws IllegalArgumentException when customer argument is null or has invalid id.
     */
    public Order(Customer customer) {
        if(customer == null)throw new IllegalArgumentException("Customer empty.");
        if(customer.getId() == null)throw new IllegalArgumentException("Customer has invalid id.");
        this.customer = customer;         
        this.creationDate = new Date();
        this.items = new ArrayList<>();
    }

    /**
     * Id getter.
     * @return order id, returns {@code null}, if id is unassigned.
     */
    public String getId() {
        
        return id;
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id only valid id (not null or "") updates id attribute on first invocation.
     * @throws IllegalArgumentException if id argument is invalid ({@code id==null} or {@code id==""}).
     * 
     * @return chainable self-reference.
     */
    public Order setId(String id) {
        if(id ==  null || id.isEmpty())throw new IllegalArgumentException("invalid id (null or \"\").");
        if(this.id == null) this.id = id;
        return this;
    }

    /**
     * Customer getter.
     * @return owning customer, cannot be null.
     */
    public Customer getCustomer() {
        
        return customer;
    }

    /**
     * CreationDate getter, returns the time/date when the order was created.
     * @return time/date when order was created as long in ms since 01/01/1970.
     */
    public long getCreationDate() {
        
        return creationDate.getTime();
    }

    /**
     * CreationDate setter for date/time, which is valid for {@code 01/01/2020 <= datetime <= now() + 1day}.
     * Orders cannot be older than the lower bound and younger than the current datetime (+1day).
     * @param datetime time/date when order was created (in milliseconds since 01/01/1970).
     * @throws IllegalArgumentException if datetime is outside valid range {@code 01/01/2020 <= datetime <= now() + 1day}.
     * @return chainable self-reference.
     */
    public Order setCreationDate(long datetime) {
        long lowerBound = dateFormat("2020-01-01 00:00:00");
        long upperBound = System.currentTimeMillis() + (24*60*60*1000L) -10;
        if(datetime < lowerBound || datetime > upperBound) throw new IllegalArgumentException("invalid datetime argument (outside bounds 01/01/2020 <= datetime <= now() + 1day).");
        creationDate.setTime(datetime);
        return this;
    }

    private long dateFormat(String datetime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long datetime_ms = 0L;
        try {
            datetime_ms = df.parse(datetime).getTime();
        } catch(ParseException e) {
            e.printStackTrace();
        }
        //System.err.println(String.format("\"%s\" -> %d", datetime, datetime_ms));
        return datetime_ms;
    }

    /**
     * Number of items that are part of the order.
     * @return number of ordered items.
     */
    public int itemsCount() {
        
        return items.size();
    }

    /**
     * Ordered items getter.
     * @return ordered items.
     */
    public Iterable<OrderItem> getItems() {
        
        return items;
    }

    /**
     * Create new item and add to order.
     * @param article article ordered from catalog.
     * @param units units ordered.
     * @throws IllegalArgumentException if article is null or units not a positive {@code units > 0} number.
     * @return chainable self-reference.
     */
    public Order addItem(Article article, int units) {
        if(article == null)throw new IllegalArgumentException("article is null.");
        if(units <= 0)throw new IllegalArgumentException("units are negative.");
        items.add(new OrderItem(article, units));
        return this;
    }

    /**
     * Delete i-th item from order, {@code i >= 0 && i < items.size()}, otherwise method has no effect.
     * @param i index of item to delete, only a valid index deletes item.
     */
    public void deleteItem(int i) {
        if(i >= 0 && i < itemsCount()) items.remove(i);
    }

    /**
     * Delete all ordered items.
     */
    public void deleteAllItems() {
        items.clear();
    }

}