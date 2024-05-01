package profiler.test;

import profiler.models.dataAccess.CarDa;
import profiler.models.entities.Car;
import profiler.models.entities.enums.Colors;

import java.time.LocalDate;

public class TestCar {
    public static void main(String[] args) throws Exception {
//        Car car = Car.builder()
//                .name("benz")
//                .color(Colors.Red)
//                .manDate(LocalDate.of(2020,11,19))
//                .build();
        CarDa carDa = new CarDa();
//        carDa.save(car);

        System.out.println(carDa.findAll());
    }
}
