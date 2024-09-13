package application;

import system.IoC;

/**
 * Runnable application class that creates sample data for {@link datamodel}
 * classes and prints orders in table format after refactoring.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_E1_refactoring {

    /**
     * Reference to {@link system.IoC} container.
     */
    private final IoC ioc;


    /**
     * Private constructor.
     */
    private Application_E1_refactoring() {
        this.ioc = IoC.getInstance();
    }


    /**
     * Public main() function.
     * 
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        var appInstance = new Application_E1_refactoring();
        appInstance.run();
    }


    /**
     * Private method that runs with application instance.
     */
    private void run() {
        String app = this.getClass().getSimpleName();
        String msg = String.format("Hello, %s: %s (after refactoring)", package_info.RootName, app);
        System.out.println(msg);
        StringBuilder sb;

        var dataStore = ioc.getDataStore()
            //.build(ds -> DataFactory.getInstance(ds).create())
            //.build(ds -> new MockDataFactoryImpl(ds).buildMockData()) // old
            //.build(ds -> new MockDataFactoryImpl(ds).buildMoreMockData()) // old
            .build(ds -> ioc.createDataFactory(ds).create())
        ;
        var printer = ioc.getPrinter();

        var customers = dataStore.customers().findAll();
        sb = printer.printCustomers(customers);
        System.out.println(sb.insert(0, "Kunden:\n").toString());   // print table from returned StringBuilder

        var articles = dataStore.articles().findAll();
        sb = printer.printArticles(articles);
        System.out.println(sb.insert(0, "Artikel:\n").toString());

        var orders = dataStore.orders().findAll();
        sb = printer.printOrders(orders);
        System.out.println(sb.insert(0, "Bestellungen:\n").toString());


        /*
         * Print Customers with names in alphabetical order.
         */
        sb = printer.printStreamed(customers, stream -> stream
            .sorted((c1, c2) -> c1.getLastName().compareTo(c2.getLastName()))
        );
        System.out.println(sb.insert(0, "Kunden, Namen alphabetisch sortiert:\n").toString());


        /*
         * Print the three most expensive articles.
         */
        sb = printer.printStreamed(articles, stream -> stream
            .sorted((a1, a2) -> Long.compare(a2.getUnitPrice(), a1.getUnitPrice()))
            .limit(3)
        );
        System.out.println(sb.insert(0, "Top 3 teuerste Artikel:\n").toString());
    }

}
