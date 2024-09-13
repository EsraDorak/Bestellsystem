package datamodel;


/**
 * Class of a type to represent a postal address that is comprised of:
 * <pre>
 * - country code, e.g. "D" for Germany, "AU" for Austria, etc.
 * - zip code as 4 or 5 digit postal routing number (Postleitzahl).
 * - city as name of a city, town or village as used in a postal address.
 * - street as name of the street with house number.
 * </pre>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public class Address {

    /**
     * Enum to represent country codes, e.g. "D" for Germany, "AU" for Austria, etc.
     *
     */
    enum Country { 
        D, AU, CH, NL, GB, IT, SP, USA 
    };

    /**
     * Country code, e.g. "D" for Germany, "AU" for Austria, etc.
     */
    private final Country country;
    /**
     * 4 or 5 digit postal routing number (Postleitzahl).
     */
    private final String zip;
    /**
     * Name of a city, town or village as used in a postal address.
     */
    private final String city;
    /**
     * Name of the street including street number.
     */
    private final String street;

    public Address(String zip, String city, String street) {
        this.country = null;  
        this.zip = zip;
        this.city = city;
        this.street = street;
    }
    public Address(String country, String zip, String city, String street) {
        this.country = Country.valueOf(country);
        this.zip = zip;
        this.city = city;
        this.street = street;
    }

    /**
     * Return country code.
     * 
     * @return country code.
     */
    public Country getCountry() {
        return country;
    }

    /**
     * Return zip code (Postleitzahl).
     * 
     * @return zip code.
     */
    public String getZip() {
        return zip;
    }

    /**
     * Return name of city, town or village as used in a postal address.
     * 
     * @return name of city, town or village.
     */
    public String getCity() {
        return city;
    }

    /**
     * Return name of the street with house number.
     * 
     * @return name of the street with house number.
     */
    public String getStreet() {
        return street;
    }

    @Override
public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("\n");
    sb.append("+-------------------------+\n");
    sb.append("|" + formatString(street, 25) + "|\n");
    sb.append("|" + formatString(zip + " " + city, 25) + "|\n");
    sb.append("+-------------------------+");
    return sb.toString();
}

private String formatString(String str, int length) {
    StringBuilder sb = new StringBuilder();
    sb.append(str);
    for (int i = 0; i < length - str.length(); i++) {
        sb.append(" ");
    }
    return sb.toString();
    }

}
