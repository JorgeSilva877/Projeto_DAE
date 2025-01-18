package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Order;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Product;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Warehouse;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {
    @PersistenceContext
    private EntityManager entityManager;
    @EJB
    private WarehouseBean warehouseBean;


    public void create(String name, String category, int limite, int stock, double price, int idWarehouse) throws MyEntityNotFoundException, MyEntityExistsException {
        var warehouse = warehouseBean.find(idWarehouse);
        if (warehouse == null) {
            throw new MyEntityNotFoundException("Warehouse" + warehouse + " does not exist"); //comentei para testar por n termos nenhum ainda
        }

        int code = findLastId() + 1;

        var product = new Product( code,  name,  category, limite, stock,  price, warehouse);
        entityManager.persist(product);
    }

    public List<Product> findAll() throws MyEntityNotFoundException {
        if (entityManager.createNamedQuery("getAllProducts", Product.class).getResultList().isEmpty()) {
            throw new MyEntityNotFoundException("No products found");
        }
        return entityManager.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int code) throws MyEntityNotFoundException {
        var produto = entityManager.find(Product.class, code);
        if (produto == null) {
            throw new MyEntityNotFoundException("Product " + code + " not found");
        }
        return produto;
    }

    public void shipOrder(int code, int quantity) {
        var product = find(code);
        if (product == null) {
            throw new IllegalArgumentException("Product not found");
        }
        int newStock = product.getStock() - quantity;
        if (newStock < 0) {
            throw new IllegalArgumentException("Not enough stock available");
        }
        product.setStock(newStock);
    }

    public Product update(int code, String name, String category, int limite, int stock, double price, int idWarehouse) {
        var product = find(code);
        product.setName(name);
        product.setCategory(category);
        product.setLimite(limite);
        product.setStock(stock);
        product.setPrice(price);
        product.setWarehouse(warehouseBean.find(idWarehouse));
        entityManager.merge(product);
        return product;
    }

    public int findLastId() {
        return entityManager.createNamedQuery("getAllProducts", Product.class)
                .getResultList()
                .stream()
                .mapToInt(Product::getCode)
                .max()
                .orElse(0);
    }
}
