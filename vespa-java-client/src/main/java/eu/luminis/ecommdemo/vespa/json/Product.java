package eu.luminis.ecommdemo.vespa.json;

import org.immutables.gson.Gson;
import org.immutables.value.Value;

@Gson.TypeAdapters
@Value.Immutable
public abstract class Product {
    public abstract String id();
    public abstract String img_src();
    public abstract Double price();
    public abstract String[] tags();
    public abstract String title();
    public abstract String type();
    public abstract String vendor();
    public abstract String description();
    public abstract Integer numberOfRatings();
    public abstract Double rating();
}
