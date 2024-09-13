package system.impl;

import datamodel.Address;
import datamodel.Customer;
import datamodel.Order;
import system.*;


/**
 * Implementation class of the {@link system.IoC} interface.
 * <p>
 * "Inversion-of-Control" (IoC) container creates and contains system component objects
 * such as {@link Calculator}, {@link DataStore}, {@link Formatter} and {@link Printer}.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class IoC_Impl implements IoC {

    /**
     * Singleton instance of IoC component.
     */
    private final static IoC iocSingleton = new IoC_Impl();

    /**
     * Singleton instance of DataStore component.
     */
    private final DataStore dataStore;

    /**
     * Singleton instance of Calculator component.
     */
    private final Calculator calculator;


    /**
     * Singleton instance of Formatter component.
     */
    private final Formatter formatter;

    /**
     * Singleton instance of Printer component.
     */
    private final Printer printer;


    /**
     * Private constructor to implement Singleton pattern of IoC instance.
     */
    private IoC_Impl() {
        this.calculator = new CalculatorImpl();    // replace with new class CalculatorImpl.java
        this.formatter = new FormatterImpl();      // replace with new class FormatterImpl.java
        this.dataStore = new DataStoreImpl();
        //
        // inject dependencies into PrinterImpl constructor
        this.printer = new PrinterImpl(calculator, formatter);
    }


    /**
     * IoC component getter.
     *  
     * @return reference to IoC singleton instance. 
     */
    public static IoC getInstance() {
        return iocSingleton;
    }


    /**
     * DataStore component getter.
     *  
     * @return reference to DataStore singleton instance. 
     */
    @Override
    public DataStore getDataStore() {
        return dataStore;
    }


    /**
     * Calculator component getter.
     *  
     * @return reference to Calculator singleton instance. 
     */
    @Override
    public Calculator getCalculator() {
        return calculator;
    }


    /**
     * Formatter component getter.
     *  
     * @return reference to Formatter singleton instance. 
     */
    @Override
    public Formatter getFormatter() {
        return formatter;
    }


    /**
     * Printer component getter.
     *  
     * @return reference to Printer singleton instance. 
     */
    @Override
    public Printer getPrinter() {
        return printer;
    }



    /*
     * Private methods used in handouts.
     */

    /**
     * REPLACE with OWN CLASS: CalculatorImpl.java.
     * Return dummy-Calculator instance that returns 0 for all calculations.
     * 
     * @return dummy-Calculator instance that returns 0 for all calculations.
     */
    private Calculator CalculatorImpl() {
        var dummy = new Calculator() {

            @Override
            public long calculateOrderItemValue(datamodel.OrderItem item) {
                
                return 0;
            }

            @Override
            public long calculateOrderItemVAT(datamodel.OrderItem item) {
                
                return 0;
            }

            @Override
            public long calculateOrderValue(datamodel.Order order) {
                
                return 0;
            }

            @Override
            public long calculateOrderVAT(datamodel.Order order) {
                
                return 0;
            }

            @Override
            public long calculateVAT(long grossValue, datamodel.TAX taxRate) {
                
                return 0;
            }

            @Override
            public double value(datamodel.TAX taxRate) {
                
                return 0;
            }
        };
        return dummy;
    }


    /**
     * REPLACE with OWN CLASS: FormatterImpl.java.
     * Return dummy-Formatter instance that returns an empty String "" for all methods.
     * 
     * @return dummy-Formatter instance that returns empty String "" for all methods.
     */
    private Formatter FormatterImpl() {
        var dummy = new Formatter() {

            @Override
            public String fmtCustomerName(datamodel.Customer customer, int... fmt) {
                
                return "";
            }

            @Override
            public String fmtCustomerContacts(datamodel.Customer customer, int... fmt) {
                
                return "";
            }

            @Override
            public String fmtPrice(long price, int... fmt) {
                
                return "";
            }

            @Override
            public String fmtDecimal(long value, int decimalDigits, String... unit) {
                
                return "";
            }
        };
        return dummy;
    }


   
    @Override
    public DataFactory createDataFactory(DataStore dataStore) {
    return new MockDataFactoryImpl(dataStore);
}


   @Override public LabelPrinter getLabelPrinter() {
    return new LabelPrinter() { // return mock instance of LabelPrinter interface
        @Override public StringBuilder printLabels(Iterable<Order> orders) {
             sb.append("PRINT CUSTOMER addresses:\n");
              // print mock addresses for all customers rather than for orders
             dataStore.customers().findAll().forEach(customer -> {
                var adr = customer.getAddress();
                var name = customer.getFirstName() + " " + customer.getLastName() + ", ";
                var city = adr.getCountry() + "-" + adr.getZip() + " " + adr.getCity() + ", ";
                sb.append(" - ").append(name).append(city).append(adr.getStreet()).append("\n");
             });
            return sb;
        }
        private Object printLabel(Address address) {
            return address;
        }
        @Override public StringBuilder printLabel(Order order) { 
            return sb.append("Printing address label for order ").append(order.getId()).append(":\n")
            .append(printLabel(order.getCustomer().getAddress())).append("\n");
        
        }
        @Override public StringBuilder printLabel(Customer customer) { 
             return sb.append(printLabel(customer.getAddress())).append("\n");
        }
        @Override public StringBuilder printLabel(String... lines) { 
             for (String line : lines) {
                sb.append(line).append("\n");
            }
            return sb;
        }

        @Override public int getWidth() { 
            return 80;
        }
        @Override public void clear() { 
            sb.setLength(0);
        }
        private final StringBuilder sb = new StringBuilder();
    };
}

}
