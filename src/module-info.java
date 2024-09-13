/**
 * Module of an order processing application:
 * <code>{@value application.package_info#RootName}.</code>
 * <p>
 * The {@link application} package contains runnable application classes including
 * the main class {@link application.Application}.java.
 * </p>
 * <p>
 * The {@link datamodel} package provides classes for entities and relations managed by
 * the order processing application.
 * </p>
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */

module se1_bestellsystem {
    opens application;
    exports datamodel;  // to enable Javadocs

    requires org.junit.jupiter.api;
}
