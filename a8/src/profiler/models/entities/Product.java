package profiler.models.entities;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import profiler.models.entities.enums.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

public class Product {
    private int id;
    private String name;
    private ProductType productType;
    private double price;

    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
