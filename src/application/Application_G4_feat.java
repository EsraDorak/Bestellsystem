package application;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import system.IoC;
import system.Repository;
import datamodel.*;


/**
 * Runnable application class that prints address labels for orders.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_G4_feat {

    /**
     * Reference to {@link system.IoC} container.
     */
    private final IoC ioc;

    /**
     * Reference to Order {@link system.Repository}.
     */
    private final Repository<Order, String> orderRepository;
    

    /**
     * Private constructor.
     */
    private Application_G4_feat() {
        this.ioc = IoC.getInstance();
        this.orderRepository = ioc.getDataStore().orders();
    }


    /**
     * Public main() function.
     * 
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        var appInstance = new Application_G4_feat();
        appInstance.run();
    }


    /**
     * Private method that runs with application instance.
     */
    private void run() {
        String app = this.getClass().getSimpleName();
        String msg = String.format("Hello, %s: %s (print address labels for orders)", package_info.RootName, app);
        System.out.println(msg);
        msg = "";
        StringBuilder sb = new StringBuilder(String.format("Remove comments in %s.run()", app));

        ioc.getDataStore().build(ds -> ioc.createDataFactory(ds).create());
        var orders = orderRepository.findAll();
        var labelPrinter = ioc.getLabelPrinter();

        /*
         * Filter orders before printing:
         *  - convert Iterable<Order> from findAll() to Stream<Order>,
         *  - apply filters on order stream,
         *  - return as filtered Iterable<Order>.
         */
       orders = StreamSupport.stream(orders.spliterator(), false)
            .collect(Collectors.toList());

        long count = StreamSupport.stream(orders.spliterator(), false).count();
        msg = String.format("Printing %d address-labels:\n", count);

        /*
         * Print labels for order.
         */
        sb = labelPrinter.printLabels(orders);
        System.out.println(sb.insert(0, msg).toString());
    }

}
