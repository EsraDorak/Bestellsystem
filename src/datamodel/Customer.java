package datamodel;

import java.util.*;

/**
 * Class of entity type <i>Customer</i>.
 * <p>
 * Customer is a person who creates and holds (owns) orders in the system.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Customer {

   

    /**
     * Unique Customer id attribute, {@code id < 0} is invalid, id can only be set once.
     */
    private long id = -1;

    /**
     * Customer's surname attribute, never null.
     */
    private String lastName = "";

    /**
     * None-surname name parts, never null.
     */
    private String firstName = "";

    boolean IDisSet = false;

    /**
     * Customer contact information with multiple contacts.
     */
    private final List<String> contacts = new LinkedList<>();

    private Address address;


    public Customer() {
        this.address = new Address("", "", "");
    }

    /**
     * Default constructor.
     */
    public Customer(String first, String Last) {
        firstName= first;
        lastName = Last;
    }
    
 
    /**
     * Constructor with single-String name argument.
     * @param name single-String Customer name, e.g. "Eric Meyer".
     * @throws IllegalArgumentException if name argument is null.
     */
    public Customer(String name) {
        if(name == null){
            throw new IllegalArgumentException("name null.");
        } else if(name.equals("")){
            throw new IllegalArgumentException("name empty.");
        }

        setName(name);
        }

    /**
    /**
     * Id getter.
     * @return customer id, returns {@code null}, if id is unassigned.
     */
    public Long getId() {
        if (id == -1) {
            return null;
        } else {
            return id;
        }
    }

    /**
     * Id setter. Id can only be set once with valid id, id is immutable after assignment.
     * @param id value to assign if this.id attribute is still unassigned {@code id < 0} and id argument is valid.
     * @throws IllegalArgumentException if id argument is invalid ({@code id < 0}).
     * @return chainable self-reference.
     */
 

     public Customer setId(long id){

        if(id < 0){
            throw new IllegalArgumentException("invalid id (negative).");
        } else if(IDisSet){
            // ID wurde bereits gesetzt, also verlassen wir einfach die Methode
            return this;
        }
    
        this.id = id;
        IDisSet = true;
        return this;
    }

    /**
     * LastName getter.
     * @return value of lastName attribute, never null, mapped to "".
     */
    public String getLastName() {
         if(firstName == null){
            return "";
        }
    
        return  lastName.trim();
    }

    /**
     * FirstName getter.
     * @return value of firstName attribute, never null, mapped to "".
     */
    public String getFirstName() {
        if(firstName == null){
            return "";
        }
     
        return firstName.trim();
    }

    /**
     * Setter that splits a single-String name (for example, "Eric Meyer") into first-
     * ("Eric") and lastName ("Meyer") parts and assigns parts to corresponding attributes.
     * @param first value assigned to firstName attribute, null is ignored.
     * @param last value assigned to lastName attribute, null is ignored.
     * @return chainable self-reference.
     */
    public Customer setName(String first, String last) {
        if(first == null){
            first = "";
        }
        if(last == null){
            last = "";
        }
      this.firstName = first;
      this.lastName = last;
    
      return this;
    }
    
    /**
     * Setter that splits a single-String name (e.g. "Eric Meyer") into first- and
     * lastName parts and assigns parts to corresponding attributes.
     * @param name single-String name to split into first- and lastName parts.
     * @throws IllegalArgumentException if name argument is null.
     * @return chainable self-reference.
     */
    public Customer setName(String name) {
            if(name == null || name.equals("")){
                throw new IllegalArgumentException("name empty.");
            }
        splitName(name);

        return this;
    }

    /**
     * Return number of contacts.
     * @return number of contacts.
     */
    public int contactsCount() {
        int count = 0;
        for (int i = 0; i < contacts.size(); i++) {
            count++;
        }

        return count;
    }

    /**
     * Contacts getter (as {@code String[]}).
     * @return contacts (as {@code String[]}).
     */
    public String[ ] getContacts() {
        String[] cont = new String[contacts.size()];
        
        int count = 0;
        for (String string : contacts) {
            cont[count] = string.trim();
            count++;
        }
        
        
        return cont;
    }

    /**
     * Add new contact for Customer. Only valid contacts (not null, "" nor duplicates) are added.
     * @param contact valid contact (not null or "" nor duplicate), invalid contacts are ignored.
     * @throws IllegalArgumentException if contact argument is null or empty "" String.
     * @return chainable self-reference.
     */
    public Customer addContact(String contact) {
        if (contact == null || contact == "")
            throw new IllegalArgumentException();
        if (contact.replaceAll("[\"t]", "").trim().length() < 6) {
            String d = "contact less than 6 characters: \"" + contact + "\".";
            throw new IllegalArgumentException(d.toString());
        }
        if (contacts.contains(contact))
            return this;
        contacts.add(contact.replaceAll("[\"\t\n;',]", "").trim());
        return this;
    }
    

    /**
     * Delete the i-th contact with {@code i >= 0} and {@code i < contactsCount()}, otherwise method has no effect.
     * @param i index of contact to delete.
     */
    public void deleteContact(int i) {
        if (i >= 0 && i < contacts.size())
            for (int z = i; z < contacts.size(); z++) {
                contacts.remove(z);
            }
    }

    /**
     * Delete all contacts.
     */
    public void deleteAllContacts() {
        contacts.removeAll(contacts);
    }

    /**
     * Split single-String name into last- and first name parts.
     * @param name single-String name to split into first- and last name parts.
     * @throws IllegalArgumentException if name argument is null.
     * @return chainable self-reference.
     */
    private Customer splitName(String name) {
        if (name == null)
            throw new IllegalArgumentException();
        if (name.contains(" ")) {
            String[] s = name.split(" ");
            String anfang = "";
            String ende = "";
            if (s.length >= 3) {
                ende = s[s.length - 1];
                for (int i = 0; i < s.length - 1; i++)
                    anfang += " " + s[i];
            } else {
                anfang = s[0];
                ende = s[1];
            }
            String nameTest = anfang + ende;
            if (!(nameTest.contains("-") || nameTest.contains(",") || nameTest.contains(";"))) {
                firstName = anfang.trim();
                lastName = ende.trim();
                return this;
            }
            name = name.trim();
        }
        if (!(name.contains("-") || name.contains(",") || name.contains(";")))
            lastName = name;
        if (name.contains("-")) {
            if (!(name.contains(" "))) {
                firstName = "";
                lastName = name;
            }
            if (name.contains(" ")) {
                String[] s = name.split(" ");
                String anfang = "";
                String ende = "";
                if (s.length >= 3) {
                    ende = s[s.length - 1];
                    for (int i = 0; i < s.length - 1; i++) {
                        if (i == 0)
                            anfang = s[i].trim();
                        else
                            anfang += " " + s[i].trim();
                    }
                    firstName = anfang;
                    lastName = ende;
                    String nameTest = anfang + ende;
                    if (!(nameTest.contains(",") || nameTest.contains(";")))
                        return this;
                }
                firstName = s[0].trim();
                lastName = s[1].trim();
            }
        }
        if (name.contains(",") || name.contains(";")) {
            String[] s = name.split("[,;]");
            firstName = s[1].trim();
            lastName = s[0].trim();
        }

        return this;
    }

    /**
     * Setter for the address of the customer.
     * 
     * @param address the address to set for the customer.
     * @return chainable self-reference.
     */
    public Customer setAddress(Address address) {
        if (address != null) {
            this.address = address;
        }
        return this;
    }

   public Address getAddress() {
    if (address == null) {
        address = new Address("D-00000", "XXXXXXXXX", "XXXXXXXXXXXXX");
    }
    return address;
}
}    