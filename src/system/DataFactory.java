package system;


/**
 * Interface of a factory that creates objects of {@link datamodel} classes and
 * loads them into the provided DataStore.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface DataFactory {

   

    /**
     * Create objects and load them into the provided DataStore.
     * 
     * @return chainable self-reference.
     */
    DataFactory create();

}
