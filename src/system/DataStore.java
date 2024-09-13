package system;

import java.util.function.Consumer;

import datamodel.*;


/**
 * {@link DataStore} is a singleton {@link system} component that stores collections
 * of {@link datamodel} objects such as objects of classes {@link Customer},
 * {@link Article} and  {@link Order}.
 * <p>
 * The {@link DataStore} interface offers methods to create and access objects.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

public interface DataStore {

    /**
     * Save object (entity) to a repository. Object replaces a prior object
     * with the same {@literal id}.
     * 
     * @param <T> generic type of a {@link datamodel} class.
     * @param entity object saved to the repository.
     * @return chainable self-reference.
     * @throws IllegalArgumentException {@literal entity} or entity's {@literal id}
     * is {@literal null}.
     */
    <T> DataStore save(T entity);


    /**
     * Save a collection of objects (entities) to a repository. Objects replace
     * prior objects with the same {@literal id}.
     * 
     * @param <T> generic type of a {@link datamodel} class.
     * @param entities collection of objects (entities) saved to repository.
     * @return chainable self-reference.
     * @throws IllegalArgumentException {@literal entities} is {@literal null}.
     */
    <T> DataStore saveAll(Iterable<T> entities);


    /**
     * Builder method to create objects in DataStore.
     * 
     * @param factory callout to factory that creates objects.
     * @return chainable self-reference.
     */
    DataStore build(Consumer<DataStore> factory);

    /**
     * Return repository of customers.
     * 
     * @return repository of customers.
     */
    Repository<Customer, Long> customers();

    /**
     * Return repository of articles.
     * 
     * @return repository of articles.
     */
    Repository<Article, String> articles();

    /**
     * Return repository of orders.
     * 
     * @return repository of orders.
     */
    Repository<Order, String> orders();

}
