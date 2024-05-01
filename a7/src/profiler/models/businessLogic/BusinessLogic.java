package profiler.models.businessLogic;

import profiler.controller.exceptions.DuplicateUsernameException;
import profiler.controller.exceptions.NoUserFoundException;
import profiler.models.dataAccess.CarDa;
import profiler.models.entities.Car;

import java.util.List;

public class BusinessLogic {
    public static Car save(Car car) throws Exception {
        try (CarDa carDa = new CarDa()) {
            if (carDa.findByID(car.getId()) == null) {
                carDa.save(car);
                return car;
            } else {
                throw new DuplicateUsernameException();
            }
        }
    }

    public static Car edit(Car car) throws Exception {
        try (CarDa carDa = new CarDa()) {
            if (carDa.findByID(car.getId()) != null) {
                carDa.edit(car);
                return car;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public static Car remove (int id) throws Exception {
        try (CarDa carDa = new CarDa()) {
            Car car = carDa.findByID(id);

            if (car != null) {
                carDa.remove(id);
                return car;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public static List<Car> findAll() throws Exception {
        try (CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findAll();

            if (!carList.isEmpty()) {
                return carList;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public static List<Car> findByName(String name) throws Exception {
        try (CarDa carDa = new CarDa()) {
            List<Car> carList = carDa.findByName(name);
            if (!carList.isEmpty()) {
                return carList;
            } else {
                throw new NoUserFoundException();
            }
        }
    }

    public static Car findByID(int id) throws Exception {
        try (CarDa carDa = new CarDa()) {
            return carDa.findByID(id);
        }
    }
}
