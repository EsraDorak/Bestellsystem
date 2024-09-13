package datamodel;

/**
 * Class of a line item referring to an article that is part of an Order. Orders may have multiple order items.
 * 
 * @version <code style=color:green>{@value application.package_info#Version}</code>
 * @author <code style=color:blue>{@value application.package_info#Author}</code>
 */
public class OrderItem {

    /**
     * Default constructor
     */
    public OrderItem() {
    }

    /**
     * Ordered article, is never null.
     */
    private Article article;

    /**
     * Number of units ordered, is always a positive number {@code > 0}.
     */
    private int unitsOrdered;


    /**
     * Constructor of ordered line item with article and units arguments.
     * @param article ordered article, throws IllegalArgumentException if article is null
     * @param unitsOrdered number of articles ordered
     */
    protected OrderItem(Article article, int unitsOrdered) {
        if(article == null) {
            throw new IllegalArgumentException("Article can't be null.");
        }
        if(unitsOrdered <= 0) {
            throw new IllegalArgumentException("Units ordered must be greater than 0.");
        }
        this.article = article;
        this.unitsOrdered = unitsOrdered;
    }

    /**
     * Article getter.
     * @return ordered article.
     */
    public Article getArticle() {
        return article;
    }

    /**
     * UnitsOrdered getter.
     * @return number of article ordered.
     */
    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    /**
     * UnitsOrdered setter.
     * @param units units updated number of articles ordered, must be {@code >= 0}.
     * @throws IllegalArgumentException if units not a positive {@code units >0} number.
     */
    public void setUnitsOrdered(int units) {
        if(units < 0) {
            throw new IllegalArgumentException("Number of units ordered must be positive!");
        }
    }

}