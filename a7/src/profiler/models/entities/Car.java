package profiler.models.entities;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import profiler.models.entities.enums.Colors;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Car {
    private int id;
    protected String name;
    protected Colors color;
    protected LocalDate manDate;


    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}