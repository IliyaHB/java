package profiler.models.bl;

import profiler.controller.exceptions.DuplicateIDException;
import profiler.controller.exceptions.NoProductFoundException;
import profiler.models.da.ProductDa;
import profiler.models.entities.Product;

import java.util.List;

public class BusinessLogic {
    public static Product save(Product product) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            if (productDa.findByID(product.getId()) == null) {
                productDa.save(product);
                return product;
            } else {
                throw new DuplicateIDException();
            }
        }
    }

    public static Product edit(Product product) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            if (productDa.findByID(product.getId()) != null) {
                productDa.edit(product);
                return product;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static Product remove (int id) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            Product product = productDa.findByID(id);

            if (product != null) {
                productDa.remove(id);
                return product;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static List<Product> findAll() throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findAll();

            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static List<Product> findByName(String name) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findByName(name);
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static List<Product> findByType(String name) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            List<Product> productList = productDa.findByType(name);
            if (!productList.isEmpty()) {
                return productList;
            } else {
                throw new NoProductFoundException();
            }
        }
    }

    public static Product findByID(int id) throws Exception {
        try (ProductDa productDa = new ProductDa()) {
            return productDa.findByID(id);
        }
    }
}
