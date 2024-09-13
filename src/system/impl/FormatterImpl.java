    package system.impl;

    import java.util.Arrays;

    import datamodel.Customer;
    
    import system.Formatter;

    class FormatterImpl implements Formatter {

        @Override
        public String fmtPrice(long price, int... fmt) {
            if (fmt.length > 0 && fmt[0] == 1) {
                return String.format("%.2f EUR", price / 100.0);
            } else {
                return String.format("%.2f", price / 100.0);
            }
        }

        @Override
        public String fmtDecimal(long value, int decimalDigits, String... unit) {
            String formatString = "%." + decimalDigits + "f";
            String formattedValue = String.format(formatString, value / Math.pow(10, decimalDigits));
            if (unit.length > 0) {
                return formattedValue + unit[0];
            } else {
                return formattedValue;
            }
        }

        @Override
        public String fmtCustomerName(Customer customer, int... fmt) {
            String firstName = customer.getFirstName();
            String lastName = customer.getLastName();

            if (fmt.length > 0 && fmt[0] == 10) {
                return lastName.toUpperCase() + ", " + firstName.toUpperCase();
            } else if (fmt.length > 0 && fmt[0] >= 11 && fmt[0] <= 15) {
                String formattedFirstName = firstName.toUpperCase();
                String formattedLastName = lastName.toUpperCase();
                if (fmt[0] % 10 == 1) {
                    return formattedFirstName + " " + formattedLastName;
                } else if (fmt[0] % 10 == 2) {
                    return formattedLastName + ", " + formattedFirstName.charAt(0) + ".";
                } else if (fmt[0] % 10 == 3) {
                    return formattedFirstName.charAt(0) + ". " + formattedLastName;
                }
                else if (fmt[0] % 10 == 4) {
                    return formattedFirstName.charAt(0) + ". " + formattedLastName.charAt(0) + ".";
                }
                else if (fmt[0] % 10 == 5) {
                    return formattedLastName.charAt(0) + ". " + formattedFirstName;
                } else {
                    return formattedLastName + ", " + formattedFirstName;
                }
            } else {
                return firstName + " " + lastName;
            }
        }

        /*@Override
        public String fmtOrder(Order order) {
            StringBuilder sb = new StringBuilder();

            sb.append("Order ID: ").append(order.getId()).append("\n");
            sb.append("Customer: ").append(fmtCustomerName(order.getCustomer())).append("\n");
            sb.append("Items:\n");

            int itemNumber = 1;
            for (OrderItem item : order.getItems()) {
                sb.append(itemNumber).append(". ")
                        .append(item.getArticle().getName()).append(": ")
                        .append(fmtDecimal(item.getUnitPrice(), 2, " EUR")).append(" x ")
                        .append(item.getQuantity()).append(" = ")
                        .append(fmtPrice(item.getTotalPrice(), 1)).append("\n");
                itemNumber++;
            }

            sb.append("Total: ").append(fmtPrice(order.getTotalPrice(), 1)).append("\n");

            return sb.toString();
        }*/

        @Override
public String fmtCustomerContacts(final Customer customer, final int... fmt) {
    if (customer == null)
        throw new IllegalArgumentException("Customer is null.");

    var contacts = customer.getContacts();
    final int formatType = fmt.length > 0 ? fmt[0] : 0;  // 0 is the default format

    switch (formatType) {
        case 0:
            return String.format("%s", contacts.length > 0 ? contacts[0] : "");

        case 1:
            String extension = contacts.length > 1 ? String.format(", (+%d contacts)", contacts.length - 1) : "";
            return String.format("%s%s", fmtCustomerContacts(customer, 0), extension);

        case 2:
            StringBuilder sb = new StringBuilder();
            Arrays.stream(contacts).forEach(contact -> sb.append(contact).append(sb.length() > 0 ? ", " : ""));
            return sb.toString();

        default:
            return fmtCustomerContacts(customer, 0);
    }
}

    }