package system.impl;

import java.util.Arrays;
import java.util.List;

import datamodel.*;
import system.DataFactory;
import system.DataStore;


/**
 * Class that creates objects of {@link datamodel} classes and loads
 * them into the provided DataStore.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

class MockDataFactoryImpl implements DataFactory {

    /**
     * reference to DataStore as destination of created objects.
     */
    private final DataStore dataStore;


    /**
     * Constructor.
     * 
     * @param dataStore {@link DataStore} instance as destination of created objects. 
     */
    MockDataFactoryImpl(DataStore dataStore) {
        if(dataStore==null)
            throw new IllegalArgumentException("argument dataStore is null.");
        //
        this.dataStore = dataStore;
    }


    /**
     * Create objects and load them into the provided DataStore.
     * 
     * @return chainable self-reference.
     */
    @Override
    public DataFactory create() {
        buildMockData();
        buildMoreMockData();
        return this;
    }


    DataFactory buildMockData() {
        
        var eric = new Customer("Eric Meyer")
                    .setId(892474L)
                    .addContact("eric98@yahoo.com").addContact("(030) 3945-642298");
                    eric.setAddress(new Address("D", "13353", "Berlin Wedding", "Samoastrasse 23"));
        var anne = new Customer("Bayer, Anne")
                    .setId(643270L)
                    .addContact("(030) 3481-23352").addContact("fax: (030)23451356");
                    anne.setAddress(new Address("D", "58089", "Hagen", "Bruehlgasse 7"));
        var tim = new Customer("Tim Schulz-Mueller")
                    .setId(286516L)
                    .addContact("tim2346@gmx.de");
                    tim.setAddress(new Address("D", "14542", "Werder", "Am Schulweg 23"));
        var nadine = new Customer("Nadine-Ulla Blumenfeld")
                    .setId(412396L)
                    .addContact("+49 152-92454");
                    nadine.setAddress(new Address("AU", "1060", "Wien", "Anton‐Stilling‐Platz 24"));
        var khaled = new Customer("Khaled Saad Mohamed Abdelalim").setId(456454L)
                    .addContact("+49 1524-12948210");
                    khaled.setAddress(new Address("D", "12055", "Berlin", "Richardplatz 17"));
        // add customers to collection
        dataStore.saveAll(List.of(eric, anne, tim, nadine, khaled));

        var tasse = new Article("Tasse", 299).setId("SKU-458362");
        var becher = new Article("Becher", 149).setId("SKU-693856");
        var kanne = new Article("Kanne", 1999).setId("SKU-518957");
        var teller = new Article("Teller", 649).setId("SKU-638035");
        //
        var buch_Java = new Article("Buch \"Java\"", 4990)
                .setId("SKU-278530")
                .setTax(TAX.GER_VAT_REDUCED);   // reduced tax rate on books
        //
        var buch_OOP = new Article("Buch \"OOP\"", 7995)
                .setId("SKU-425378")
                .setTax(TAX.GER_VAT_REDUCED);   // reduced tax rate on books
        //
        // add articles to collection
        dataStore.saveAll(List.of(tasse, becher, kanne, teller, buch_Java, buch_OOP));

        // Eric's 1st order
        var o8592 = new Order(eric)   // new order for Eric
                .setId("8592356245")    // assign order-id: 8592356245
                .addItem(teller, 4)     // + item: 4 Teller, 4x 6.49 €
                .addItem(becher, 8)     // + item: 8 Becher, 8x 1.49 €
                .addItem(buch_OOP, 1)   // + item: 1 Buch "OOP", 1x 79.95 €, 7% MwSt (5.23€)
                .addItem(tasse, 4);     // + item: 4 Tassen, 4x 2.99 €
        //
        // Anne's order
        var o3563 = new Order(anne)
                .setId("3563561357")
                .addItem(teller, 2)
                .addItem(tasse, 2);
        //
        // Eric's 2nd order
        var o5234 = new Order(eric)
                .setId("5234968294")
                .addItem(kanne, 1);
        //
        // Nadine's order
        var o6135 = new Order(nadine)
                .setId("6135735635")
                .addItem(teller, 12)
                .addItem(buch_Java, 1)
                .addItem(buch_OOP, 1);
        //
        // add orders to collection
        dataStore.saveAll(List.of(o8592, o3563, o5234, o6135));

        System.out.println(String.format(   // print numbers of objects in collections
            "(%d) Customer objects built.\n" +
            "(%d) Article objects built.\n" +
            "(%d) Order objects built.\n---",
            //
            dataStore.customers().count(),
            dataStore.articles().count(),
            dataStore.orders().count()));
        //
        return this;
    }


    DataFactory buildMoreMockData() {
        //
        var sz = new long[] {
                dataStore.customers().count(),
                dataStore.articles().count(),
                dataStore.orders().count()
            };

        Customer eric = dataStore.customers().findById(892474L).get();
    //
    // @REMOVE.BEGIN
    //
        var pfanne = new Article("Pfanne", 4999).setId("SKU-300926");
        var helm = new Article("Fahrradhelm", 16900).setId("SKU-663942");
        var karte = new Article("Fahrradkarte", 695).setId("SKU-583978")
                .setTax(TAX.GER_VAT_REDUCED);   // reduced tax rate on maps;
        //
        dataStore.saveAll(Arrays.asList(pfanne, helm, karte));

        Customer lena = new Customer("Lena Neumann")
                .setId(651286L)
                .addContact("lena228@gmail.com");
                lena.setAddress(new Address("D", "14482", "Potsdam", "Jaegersteig 16"));
        dataStore.saveAll(List.of(lena));

        // Lena's order
        Article buch_Java = dataStore.articles().findById("SKU-278530").get();
        Order o6173 = new Order(lena)
                .setId("6173043537")
                .addItem(buch_Java, 1)
                .addItem(karte, 1);
        //
        dataStore.saveAll(List.of(o6173));

        // Eric's 3rd order
        Order o7356 = new Order(eric)
                .setId("7372561535")
                .addItem(helm, 1)
                .addItem(karte, 1);
        //
        // Eric's 4th order
        Article tasse = dataStore.articles().findById("SKU-458362").get();
        Article becher = dataStore.articles().findById("SKU-693856").get();
        Article kanne = dataStore.articles().findById("SKU-518957").get();
        Order o4450 = new Order(eric)
                .setId("4450305661")
                .addItem(tasse, 3)
                .addItem(becher, 3)
                .addItem(kanne, 1);
        //
        dataStore.saveAll(Arrays.asList(o7356, o4450));
    //
    // @REMOVE.END
    //
        long addedC = dataStore.customers().count() - sz[0];
        long addedA = dataStore.articles().count() - sz[1];
        long addedO = dataStore.orders().count() - sz[2];
        //
        if(addedC + addedA + addedO > 0) {
            System.out.println(String.format(
                "(+%d) Customer objects added.\n" +
                "(+%d) Article objects added.\n" +
                "(+%d) Order objects added.\n---",
                addedC, addedA, addedO));
        }
        //
        return this;
    }

   
}
