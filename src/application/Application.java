package application;

/**
 * Runnable application class for the {@link se1_bestellsystem}.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class Application {

    /**
     * Welcome message.
     */
    private final String msg;


    /**
     * Private constructor to initialize attributes.
     */
    private Application() {
        this.msg = String.format("Hello, %s!", package_info.RootName);
    }


    /**
     * Public main() function.
     * 
     * @param args arguments passed from command line.
     */
    public static void main(String[] args) {
        var appInstance = new Application();
        appInstance.run();
    }


    /**
     * Private method that runs with application instance.
     */
    private void run() {
        System.out.println(msg);
    }

}
