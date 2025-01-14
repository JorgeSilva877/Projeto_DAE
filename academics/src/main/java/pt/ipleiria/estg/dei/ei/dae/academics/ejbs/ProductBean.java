package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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


    public void create(int code, String name, String category, String limite, int stock, double price, int idWarehouse) throws MyEntityNotFoundException, MyEntityExistsException {
        var warehouse = warehouseBean.find(idWarehouse);
        if (warehouse == null) {
            throw new MyEntityNotFoundException("Warehouse" + warehouse + " does not exist"); //comentei para testar por n termos nenhum ainda
        }
        var product = entityManager.find(Product.class, code);
        if (product != null) {
            throw new MyEntityExistsException("Product " + code + " already exists");
        }
        product = new Product( code,  name,  category, limite, stock,  price, warehouse);
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

    public Product update(int code, String name, String category, String limite, int stock, double price, int idWarehouse) {
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
}
