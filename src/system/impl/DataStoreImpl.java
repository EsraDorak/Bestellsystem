package system.impl;

import java.util.Optional;
import java.util.function.Consumer;

import datamodel.*;
import system.Repository;
import system.DataStore;


/**
 * Non-public singleton {@link system} component that implements the {@link DataStore}
 * interface.
 * <p>
 * {@link DataStore} stores collections of {@link datamodel} objects such as
 * objects of classes {@link Customer}, {@link Article} and  {@link Order}.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

class DataStoreImpl implements DataStore {

    /**
     * Repository of Customer objects (entities).
     */
    private final Repository<Customer, Long> customersRepository;

    /**
     * Repository of Article objects (entities).
     */
    private final Repository<Article, String> articlesRepository;

    /**
     * Repository of Order objects (entities).
     */
    private final Repository<Order, String> ordersRepository;


    /**
     * Constructor.
     */
    DataStoreImpl() {
        this.customersRepository = new RepositoryImpl<Customer, Long>(c -> c.getId());
        this.articlesRepository = new RepositoryImpl<Article, String>(a -> a.getId());
        this.ordersRepository = new RepositoryImpl<Order, String>(o -> o.getId());
    }


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
    @Override
    public <T> DataStore save(T entity) {
        if(entity==null)
            throw new IllegalArgumentException("argument entity is null.");
        //
        Optional.ofNullable(
            entity instanceof Customer? customers().save((Customer)entity) :
            entity instanceof Article? articles().save((Article)entity) :
            entity instanceof Order? orders().save((Order)entity) : null);
        //
        return this;
    }


    /**
     * Save a collection of objects (entities) to a repository. Objects replace
     * prior objects with the same {@literal id}.
     * 
     * @param <T> generic type of a {@link datamodel} class.
     * @param entities collection of objects (entities) saved to repository.
     * @return chainable self-reference.
     * @throws IllegalArgumentException {@literal entities} is {@literal null}.
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> DataStore saveAll(Iterable<T> entities) {
        if(entities==null)
            throw new IllegalArgumentException("argument entities is null.");
        //
        var it = entities.iterator();
        if(it.hasNext()) {
            var entity = it.next();
            Optional.ofNullable(
                entity instanceof Customer? customers().saveAll((Iterable<Customer>)entities) :
                entity instanceof Article? articles().saveAll((Iterable<Article>)entities) :
                entity instanceof Order? orders().saveAll((Iterable<Order>)entities) : null);
        }
        return this;
    }


    /**
     * Builder method to create objects in DataStore.
     * 
     * @param factory callout to factory that creates objects.
     * @return chainable self-reference.
     */
    @Override
    public DataStore build(Consumer<DataStore> factory) {
        if(factory==null)
            throw new IllegalArgumentException("argument factory is null.");
        //
        factory.accept(this);   // callout to factory
        return this;
    }


    /**
     * Return repository of customers.
     * 
     * @return repository of customers.
     */
    @Override
    public Repository<Customer, Long> customers() {
        return customersRepository;
    }


    /**
     * Return repository of articles.
     * 
     * @return repository of articles.
     */
    @Override
    public Repository<Article, String> articles() {
        return articlesRepository;
    }


    /**
     * Return repository of orders.
     * 
     * @return repository of orders.
     */
    @Override
    public Repository<Order, String> orders() {
        return ordersRepository;
    }

}
