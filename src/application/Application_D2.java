package application;

import java.util.*;
import java.util.stream.*;
import java.util.function.Function;

import datamodel.*;
import datamodel.Currency;


/**
 * Runnable application class that creates sample data for {@link datamodel}
 * classes and prints orders in table format.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Application_D2 {

	/**
	 * list with Customers, List.of(eric, anne, tim, nadine, khaled)
	 */
	private final List<Customer> customers = new ArrayList<>();

	/**
	 * list with Articles, List.of(aTasse, aBecher, aKanne, aTeller)
	 */
	private final List<Article> articles = new ArrayList<>();

	/**
	 * list with Orders, List.of(o5234,  o8592,  o3563,  o6135)
	 */
	private final List<Order> orders = new ArrayList<>();


	/**
	 * Private constructor to initialize local attributes.
	 */
	private Application_D2() { }


	/**
	 * Public main() function.
	 * 
	 * @param args arguments passed from command line.
	 */
	public static void main(String[] args) {
		var appInstance = new Application_D2();
		appInstance.run();
	}


	/**
	 * Private method that runs with application instance.
	 */
	private void run() {
		String app = this.getClass().getSimpleName();
		String msg = String.format("Hello, %s: %s", package_info.RootName, app);
		System.out.println(msg);
		StringBuilder sb;

		/*
		 * build sample objects of datamodel classes and store them in
		 * collections: customers, articles, orders
		 */
		build(customers, articles, orders);
		buildMore(customers, articles, orders);

		sb = printCustomers(customers);
		System.out.println(sb.insert(0, "Kunden:\n").toString());   // print table from returned StringBuilder

		sb = printArticles(articles);
		System.out.println(sb.insert(0, "Artikel:\n").toString());

		/*
		 * remove comments to print orders:
		 */

		sb = printOrders(orders);
		System.out.println(sb.insert(0, "Bestellungen:\n").toString());
			System.out.println("Zusatzpunkte--------------");
		        /*
		         * Print Customers with names in alphabetical order.
		         */
		        sb = printStreamed(customers, stream -> stream
		            .sorted((c1, c2) -> c1.getLastName().compareTo(c2.getLastName()))
		        );
		        System.out.println(sb.insert(0, "Kunden, Namen alphabetisch sortiert:\n").toString());
		
		
		        /*
		         * Print the three most expensive articles.
		         */
		        sb = printStreamed(articles, stream -> stream
		            .sorted((a1, a2) -> Long.compare(a2.getUnitPrice(), a1.getUnitPrice()))
		            .limit(3)
		        );
		        System.out.println(sb.insert(0, "Top 3 teuerste Artikel:\n").toString());
	}


	/**
	 * Build sample objects of datamodel classes and add to collections.
	 * 
	 * @param customers collection of Customer objects.
	 * @param articles collection of Article objects.
	 * @param orders collection of Order objects.
	 */
	void build(List<Customer> customers, List<Article> articles, List<Order> orders) {
		//
		var eric = new Customer("Eric Meyer")
				.setId(892474L)
				.addContact("eric98@yahoo.com").addContact("(030) 3945-642298");
		//
		var anne = new Customer("Bayer, Anne")
				.setId(643270L)
				.addContact("(030) 3481-23352").addContact("fax: (030)23451356");
		//
		var tim = new Customer("Tim Schulz-Mueller")
				.setId(286516L)
				.addContact("tim2346@gmx.de");
		//
		var nadine = new Customer("Nadine-Ulla Blumenfeld")
				.setId(412396L)
				.addContact("+49 152-92454");
		//
		var khaled = new Customer("Khaled Saad Mohamed Abdelalim").setId(456454L)
				.addContact("+49 1524-12948210");
		//
		// add customers to collection
		customers.addAll(List.of(eric, anne, tim, nadine, khaled));

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
		articles.addAll(List.of(tasse, becher, kanne, teller, buch_Java, buch_OOP));

		// Eric's 1st order
		var o8592 = new Order(eric)	  // new order for Eric
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
		orders.addAll(List.of(o8592, o3563, o5234, o6135));

		System.out.println(String.format(   // print numbers of objects in collections
				"(%d) Customer objects built.\n" +
				"(%d) Article objects built.\n" +
				"(%d) Order objects built.\n---",
				customers.size(), articles.size(), orders.size()));
	}
	private <T> StringBuilder printStreamed(List<T> liste, Function<Stream<T>,  Stream<T>> streamFunction) {
		Stream stream = liste.stream();
		List sortedList = streamFunction.apply(stream).toList();
		var obj = liste.get(0);
		if(obj instanceof Customer) {
			return printCustomers(sortedList);
		}
		if(obj instanceof Article) {
			return printArticles(sortedList);
		}
		if(obj instanceof Order) {
			return printOrders(sortedList);
		}
		else {
			return null;
		}
	}


	/**
	 * Build more sample objects of datamodel classes and add to collections.
	 * 
	 * @param customers collection of Customer objects.
	 * @param articles collection of Article objects.
	 * @param orders collection of Order objects.
	 */
	void buildMore(List<Customer> customers, List<Article> articles, List<Order> orders) {
		var sz = new int[] {customers.size(), articles.size(), orders.size()};

		var eric = find(customers, c -> c.getId()==892474L).get();
		var tasse = find(articles, c -> c.getId().equals("SKU-458362")).get();
		var becher = find(articles, c -> c.getId().equals("SKU-693856")).get();
		var kanne = find(articles, c -> c.getId().equals("SKU-518957")).get();
		var buch_Java = find(articles, c -> c.getId().equals("SKU-278530")).get();
		var pfanne = new Article("Pfanne", 4999).setId("SKU‐300926");
		var fahrradhelm = new Article("Fahrradhelm", 16900).setId("SKU‐663942");
		var fahrradkarte = new Article("Fahrradkarte", 695).setId("SKU‐583978").setTax(TAX.GER_VAT_REDUCED);

		var o7356 = new Order(eric)	  
				.setId("7356613535")    
				.addItem(fahrradhelm, 1)
				.addItem(fahrradkarte, 1);

		var o4450 = new Order(eric)
				.setId("4450735661")
				.addItem(tasse, 3)
				.addItem(becher, 3)
				.addItem(kanne, 1);

		Customer lena = new Customer("Lena Neumann")
				.setId(651286L)
				.addContact("lena228@gmail.com");

		var o6173 = new Order(lena)
				.setId("6173535635")
				.addItem(buch_Java, 1)
				.addItem(fahrradkarte, 1);
		articles.addAll(List.of(pfanne,fahrradhelm, fahrradkarte));
		orders.addAll(List.of(o7356, o4450, o6173));
		int addedC = customers.size()-sz[0];
		int addedA = articles.size()-sz[1];
		int addedO = orders.size()-sz[2];
		//
		if(addedC + addedA + addedO > 0) {
			System.out.println(String.format(
					"(+%d) Customer objects added.\n" +
							"(+%d) Article objects added.\n" +
							"(+%d) Order objects added.\n---",
							addedC, addedA, addedO));
		}
	}


	/**
	 * Find first matching object from the collection. Return empty Optional
	 * if no object was found. 
	 * 
	 * @param <T> generic type of objects in collection.
	 * @param collection collection of objects of type T.
	 * @param matcher function that returns matching result for an object.
	 * @return Optional with first matching object or empty Optional.
	 */
	public <T> Optional<T> find(List<T> collection, Function<T, Boolean> matcher) {
		if(collection==null)
			throw new IllegalArgumentException("collection is null.");
		//
		return collection.stream()
				.filter(e -> matcher.apply(e))
				.findFirst();
	}


	/**
	 * Print customers in table format into a StringBuilder.
	 * 
	 * @param customers collection of customers to print.
	 * @return StringBuilder with customers rendered as table.
	 */
	public StringBuilder printCustomers(List<Customer> customers) {
		//
		final TableFormatter tf = new TableFormatter(
				// column specification
				"| %8s ", "| %-32s", "| %-36s |"
				)
				.line()
				.row("Kund.-ID", "Name", "Kontakt")  // table header
				.line();
		//
		customers.stream().forEach(c -> {
			String id = String.format("%d", c.getId());
			String name = fmtCustomerName(c);
			String contact = fmtCustomerContacts(c, 1);
			//
			tf.row(id, name, contact);  // write row into table
		});
		tf.line();
		//
		return tf.get();
	}


	/**
	 * Print articles in table format into a StringBuilder.
	 * 
	 * @param articles collection of articles to print.
	 * @return StringBuilder with customers rendered as table.
	 */
	public StringBuilder printArticles(List<Article> articles) {
		//
		final TableFormatter tf = new TableFormatter(
				// column specification
				"|%-10s", "| %-32s", "| %10s", "%4s", "|  %-18s  |"
				)
				.line()
				.row("Artikel-ID", "Beschreibung", "Preis", "CUR", "Mehrwertsteuersatz")  // table header
				.line();
		//
		articles.stream().forEach(a -> {
			String id = a.getId();
			String description = a.getDescription();
			String price = fmtPrice(a.getUnitPrice());
			String currency = a.getCurrency().toString();
			double rate = taxRateMapper.get(a.getTax());
			String vat = String.format("%4s%s %s", String.valueOf(rate), "%", a.getTax());
			//
			tf.row(id, description, price, currency, vat);  // write row into table
		});
		tf.line();
		//
		return tf.get();
	}


	/**
	 * Print orders in table format into a StringBuilder.
	 * 
	 * @param orders collection of orders to print.
	 * @return StringBuilder with orders rendered as table.
	 */
	public StringBuilder printOrders(List<Order> orders) {
		long[] totals = new long[] {0L, 0L};

		final var tf = new TableFormatter(
				// column specification
				"|%-10s|", " %-25s", " %8s", "%1s", " %12s", "| %8s", " %12s|"
				)
				.line()         // table header
				.row("Bestell-ID", "Bestellungen", "MwSt", "*", "Preis", "MwSt", "Gesamt")
				.line();

		orders.stream()
		.map(order -> {
			printOrder(order, tf, totals).line();
			return order;
		})
		.forEach(order -> { 

		});
		//
		tf.row(null, null, null, null, "Gesamt:", fmtPrice(totals[1]), fmtPrice(totals[0], 1));
		tf.line(null, null, null, null, null, "=", "=");
		//
		return tf.get();
	}


	/**
	 * Print one order in table format into a StringBuilder, used by printOrders().
	 * 
	 * @param order order to print into table.
	 * @param tf table formatter used by printOrders().
	 * @return table formatter used by printOrders().
	 */
	public TableFormatter printOrder(Order order, TableFormatter tf, long[] totals) {
		if(order==null || tf==null)
			throw new IllegalArgumentException("order or table formatter tf is null.");
		//
		// "*" indicates reduced VAT rate
		tf.row(order.getId(), order.getCustomer().getFirstName()+ "'s Bestellungen","","","","","");
		String mwstA = "";
		String gesamt ="";

		int i = order.itemsCount()-1;

		for(OrderItem oi : order.getItems()) {
			i--;
			long units = oi.getUnitsOrdered();
			Article art = oi.getArticle();
			String tx = " ";
			if(art.getTax() == TAX.GER_VAT_REDUCED) {
				tx = "*";
			}
			if(i == -1){
				mwstA = fmtPrice(calculateOrderVAT(order),0);
				gesamt = fmtPrice(calculateOrderValue(order),1);
			}
			else {
				mwstA = "";
				gesamt = "";
			}
			if(units == 1) {
				//id, bestellung, mwst einzeln, preis, mwst für alle, gesamt preis
				tf.row("","- "+ units + " " + art.getDescription(), fmtPrice(calculateOrderItemVAT(oi), 0) + tx, "",
						fmtPrice(calculateOrderItemValue(oi),1), mwstA,gesamt);
			}
			else {
				tf.row("","- "+ units + " " + art.getDescription() + ", " + units + "x " + fmtPrice(art.getUnitPrice(),0),
						fmtPrice(calculateOrderItemVAT(oi), 0)+tx,"", fmtPrice(calculateOrderItemValue(oi),1),mwstA, gesamt
						);
			}


		}
		totals[1] += calculateOrderVAT(order);
		totals[0] += calculateOrderValue(order);
		return tf;
	}


	/**
	 * Calculate value of an order, which is comprised of the value of each
	 * ordered item. The value of each item is calculated with
	 * calculateOrderItemValue(item).
	 * 
	 * @param order to calculate value for.
	 * @return value of order.
	 */
	public long calculateOrderValue(final Order order) {
		if(order==null)
			throw new IllegalArgumentException("order is null.");
		long value = 0;
		for(OrderItem oi : order.getItems()) {
			value += calculateOrderItemValue(oi);
		}
		return value;
	}


	/**
	 * Calculate value of an order item, which is calculated by:
	 * article.unitPrice * number of units ordered. 
	 * 
	 * @param item to calculate value for.
	 * @return value of item.
	 */
	public long calculateOrderItemValue(final OrderItem item) {
		return item.getArticle().getUnitPrice() * item.getUnitsOrdered();
	}


	/**
	 * Calculate VAT of an order, which is comprised of the VAT of each
	 * ordered item. The VAT of each item is calculated with
	 * calculateOrderItemVAT(item).
	 * 
	 * @param order to calculate VAT tax for.
	 * @return VAT calculated for order.
	 */
	public long calculateOrderVAT(final Order order) {
		if(order==null)
			throw new IllegalArgumentException("order is null.");

		long value = 0;
		for(OrderItem oi : order.getItems()) {
			value += calculateOrderItemVAT(oi);
		}
		return value;
	}


	/**
	 * Calculate the included VAT of an order item calculated by the
	 * applicable tax rate and the calculated order item value.
	 * 
	 * @param item to calculate VAT for.
	 * @return VAT for ordered item.
	 */
	public long calculateOrderItemVAT(final OrderItem item) {
		double percentage = 0;
		if(item.getArticle().getTax() == TAX.GER_VAT) percentage = 0.19;
		if(item.getArticle().getTax() == TAX.GER_VAT_REDUCED) percentage = 0.07;

		long priceInCent = item.getArticle().getUnitPrice() * item.getUnitsOrdered();
		double priceInEuro = (double) priceInCent / 100;
		double nettoPreis = priceInEuro/(1.0+percentage);
		double mehrwertsteuer = nettoPreis * percentage;

		return Math.round(mehrwertsteuer * 100);
	}


	/**
	 * Map TAX enum to tax rate as number.
	 */
	final Map<TAX, Double> taxRateMapper = Map.of(
			TAX.TAXFREE,             0.0,   // tax free rate
			TAX.GER_VAT,            19.0,   // German VAT tax (MwSt) 19.0%
			TAX.GER_VAT_REDUCED,     7.0    // German reduced VAT tax (MwSt) 7.0%
			);


	/**
	 * Calculate included VAT (Value-Added Tax) from a gross price/value based on
	 * a tax rate (VAT is called <i>"Mehrwertsteuer" (MwSt.)</i> in Germany).
	 * 
	 * @param grossValue value that includes the tax.
	 * @param tax applicable tax rate in percent.
	 * @return tax included in gross value.
	 */
	public long calculateVAT(final long grossValue, final TAX tax) {
    if (tax == null) {
        throw new IllegalArgumentException("Argument 'tax' is null.");
    }
    
    double taxRate = tax.getRate() / 100.0; // Umwandlung in einen Dezimalwert

    double vat = grossValue * taxRate;
    long roundedVAT = Math.round(vat); // Rundung auf den nächsten ganzen Betrag

    return roundedVAT;
}


	/**
	 * Map Currency-enum to Unicode-Strings.
	 */
	private final Map<Currency, String> CurrencySymbol = Map.of(
			Currency.EUR, "\u20ac",		// Unicode: EURO
			Currency.USD, "$",			// ASCII: US Dollar
			Currency.GBP, "\u00A3",		// Unicode: UK Pound Sterling
			Currency.YEN, "\u00A5",		// Unicode: Japanese Yen
			Currency.BTC, "BTC"			// no Unicode for Bitcoin
			);


	/**
	 * Format long value to price according to a format (0 is default):
	 * <pre>
	 * Example: long value: 499
	 * fmt:   0: "4.99"
	 *        1: "4.99 EUR"
	 *        2: "4.99EUR"
	 *        3: "4.99€"
	 *        4: "4.99$"
	 *        5: "4.99£"
	 *        6:  "499¥"
	 *        7:  "499"
	 * </pre>
	 * 
	 * @param price long value as price.
	 * @param fmt price formatting style.
	 * @return formatted price according to style.
	 */
	public String fmtPrice(final long price, final int... fmt) {
		final int ft = fmt.length > 0? fmt[0] : 0;	// 0 is default format
		switch(ft) {
		case 0: return fmtDecimal(price, 2);
		case 1: return fmtDecimal(price, 2, " EUR");
		case 2: return fmtDecimal(price, 2, "EUR");
		case 3: return fmtDecimal(price, 2, CurrencySymbol.get(Currency.EUR));
		case 4: return fmtDecimal(price, 2, CurrencySymbol.get(Currency.USD));
		case 5: return fmtDecimal(price, 2, CurrencySymbol.get(Currency.GBP));
		case 6: return fmtDecimal(price, 0, CurrencySymbol.get(Currency.YEN));
		case 7: return fmtDecimal(price, 0);
		//
		default: return fmtPrice(price, 0);
		}
	}


	/**
	 * Format long value to a decimal String with a specified number of digits.
	 * 
	 * @param value value to format to String in decimal format.
	 * @param decimalDigits number of digits.
	 * @param unit appended unit as String.
	 * @return formatted decimal value as String.
	 */
	public String fmtDecimal(final long value, final int decimalDigits, final String... unit) {
		final String unitStr = unit.length > 0? unit[0] : null;
		final Object[][] dec = {
				{      "%,d", 1L },		// no decimal digits:  16,000Y
				{ "%,d.%01d", 10L },
				{ "%,d.%02d", 100L },	// double-digit price: 169.99E
				{ "%,d.%03d", 1000L },	// triple-digit unit:  16.999-
		};
		String result;
		String fmt = (String)dec[decimalDigits][0];
		if(unitStr != null && unitStr.length() > 0) {
			fmt += "%s";	// add "%s" to format for unit string
		}
		int decdigs = Math.max(0, Math.min(dec.length - 1, decimalDigits));
		//
		if(decdigs==0) {
			Object[] args = {value, unitStr};
			result = String.format(fmt, args);
		} else {
			long digs = (long)dec[decdigs][1];
			long frac = Math.abs( value % digs );
			Object[] args = {value/digs, frac, unitStr};
			result = String.format(fmt, args);
		}
		return result;
	}


	/**
	 * Format Customer name according to a format (0 is default):
	 * <pre>
	 * fmt: 0: "Meyer, Eric"  10: "MEYER, ERIC"
	 *      1: "Eric Meyer"   11: "ERIC MEYER"
	 *      2: "Meyer, E."    12: "MEYER, E."
	 *      3: "E. Meyer"     13: "E. MEYER"
	 *      4: "Meyer"        14: "MEYER"
	 *      5: "Eric"         15: "ERIC"
	 * </pre>
	 * 
	 * @param customer Customer object.
	 * @param fmt name formatting style.
	 * @return formatted Customer name.
	 */
	public String fmtCustomerName(final Customer customer, final int... fmt) {
		if(customer==null)
			throw new IllegalArgumentException("Customer null.");
		//
		String ln = customer.getLastName();
		String fn = customer.getFirstName();
		String fn1 = fn.substring(0, 1).toUpperCase();
		//
		final int ft = fmt.length > 0? fmt[0] : 0;  // 0 is default format
		switch(ft) {    // 0 is default
		case 0: return String.format("%s, %s", ln, fn);
		case 1: return String.format("%s %s", fn, ln);
		case 2: return String.format("%s, %s.", ln, fn1);
		case 3: return String.format("%s. %s", fn1, ln);
		case 4: return ln;
		case 5: return fn;
		//
		case 10: case 11: case 12: case 13: case 14: case 15:
			return fmtCustomerName(customer, ft - 10).toUpperCase();
			//
		default: return fmtCustomerName(customer, 0);
		}
	}


	/**
	 * Format Customer contacts according to a format (0 is default):
	 * <pre>
	 * fmt: 0: first contact: "anne24@yahoo.de"
	 *      1: first contact with extension indicator: "anne24@yahoo.de, (+2 contacts)"
	 *      2: all contacts as list: "anne24@yahoo.de, (030) 3481-23352, fax: (030)23451356"
	 * </pre>
	 * 
	 * @param customer Customer object.
	 * @param fmt name formatting style.
	 * @return formatted Customer contact information.
	 */
	public String fmtCustomerContacts(final Customer customer, final int... fmt) {
		if(customer==null)
			throw new IllegalArgumentException("Customer null.");
		//
		var clen = customer.getContacts().length;
		final int ft = fmt.length > 0? fmt[0] : 0;  // 0 is default format
		switch(ft) {    // 0 is default
		case 0:
			return String.format("%s", clen > 0? customer.getContacts()[0] : "");

		case 1:
			String ext = clen > 1? String.format(", (+%d contacts)", clen - 1) : "";
			return String.format("%s%s", fmtCustomerContacts(customer, 0), ext);

		case 2:
			StringBuilder sb = new StringBuilder();
			Arrays.stream(customer.getContacts()).forEach(contact -> sb.append(contact).append(sb.length() > 0? ", " : ""));
			return sb.toString();
			//
		default: return fmtCustomerContacts(customer, 0);
		}
	}


	/**
	 * Class of a table formatter that uses String.format(fmt) expressions
	 * to format cells.
	 * 
	 * @author sgra64
	 *
	 */
	class TableFormatter {

		/**
		 * Format specifiers for each column.
		 */
		private final List<String> fmts;

		/**
		 * Width of each column.
		 */
		private final List<Integer> widths;

		/**
		 * Collect formatted rows.
		 */
		private final StringBuilder sb;


		/**
		 * Constructor with String.format(fmt) specifiers for each column.
		 * 
		 * @param fmtArgs String.format(fmt) specifiers for each column.
		 */
		public TableFormatter(String... fmtArgs) {
			this((StringBuilder)null, fmtArgs);
		}

		/**
		 * Constructor with external collector of table rows and String.format(fmt)
		 * specifiers for each column.
		 * 
		 * @param sb external collector for table rows.
		 * @param fmtArgs String.format(fmt) specifiers for each column.
		 */
		public TableFormatter(StringBuilder sb, String... fmtArgs) {
			this.sb = sb != null? sb : new StringBuilder();
			this.fmts = Arrays.stream(fmtArgs).toList();
			this.widths = fmts.stream().map(fmt -> String.format(fmt, "").length()).toList();
		}


		/**
		 * Add row to table. Each cell is formatted according to the column fmt specifier.
		 * 
		 * @param cells variable array of cells.
		 * @return chainable self-reference.
		 */
		public TableFormatter row(String... cells) {
			IntStream.range(0, Math.min(fmts.size(), cells.length)).forEach(i -> {
				sb.append(fillCell(i, cells[i], t -> {
					String fmt = fmts.get(i);
					int i1 = fmt.indexOf('%');  // offset width by format chars, e.g. '%-20s'
					int i2 = Math.max(fmt.indexOf('s'), fmt.indexOf('d'));  // end '%s', '%d'
					int offset = fmt.length() - (i2 - i1) -1;
					// cut cell text to effective column width
					t = t.substring(0, Math.min(t.length(), widths.get(i) - offset));
					return String.format(fmt, t);
				}));
			});
			return this.endRow();
		}


		/**
		 * Add line comprised of segments for each column to the table.
		 * Segments are drawn based on segment spefifiers with:
		 * <pre>
		 * seg: null    - empty or blank segment
		 *      ""      - segment filled with default character: "-"
		 *      "="     - segment is filled with provided character.
		 * </pre>
		 * 
		 * @param segs variable array of segment specifiers.
		 * @return chainable self-reference.
		 */
		public TableFormatter line(String... segs) {
			if(segs.length==0) {    // print full line when segs is empty
				String[] args = fmts.stream().map(f -> "").toArray(String[]::new);
				return line(args);  // invoke recursively with ""-args
			}
			IntStream.range(0, Math.min(fmts.size(), segs.length)).forEach(i -> {
				sb.append(fillCell(i, segs[i], s -> {
					s = s.length() > 0? s.substring(0, 1) : "-"; // filler char
					return String.format(fmts.get(i), "")
							.replaceAll("[^\\|]", s).replaceAll("[\\|]", "+");
				}));
			});
			return this.endRow();
		}


		/**
		 * Getter to collected table content.
		 * 
		 * @return table content.
		 */
		public StringBuilder get() { return sb; }


		/*
		 * private helper methods.
		 */

		private String fillCell(int i, String text, Function<String, String> cellFiller) {
			return text != null? cellFiller.apply(text) : " ".repeat(widths.get(i));
		}

		private TableFormatter endRow() { sb.append("\n"); return this; }
	}

}