package repo;

import model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {
    private DBUtils dbUtils;

    public Repository(Properties props) {
        dbUtils = new DBUtils(props);
    }

    public Client getClient(String email, String password){
        Connection connection = dbUtils.getConnection();
        Client client = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from client where email=? and password=?")){
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            try(ResultSet result = preparedStatement.executeQuery()){
                String e = result.getString("email");
                String p = result.getString("password");
                Integer id = result.getInt("id");
                client = new Client(e, p);
                client.setId(id);
            }
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
        return client;
    }

    public List<Product> findAll() {
        Connection connection = dbUtils.getConnection();
        List<Product> products = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product")){
            try(ResultSet result = preparedStatement.executeQuery()){
                while(result.next()){
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    String category = result.getString("category");
                    float price = result.getFloat("price");
                    String size = result.getString("size");
                    int quantity = result.getInt("quantity");
                    ProductStatus status = ProductStatus.AVAILABLE;
                    if(quantity == 0)
                        status = ProductStatus.SOLD_OUT;
                    if(quantity <= 10)
                        status = ProductStatus.LIMITED;
                    Product product = new Product(name, Size.valueOf(size), price, Category.valueOf(category), quantity, status);
                    product.setId(id);
                    products.add(product);
                }
            }
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
        return products;
    }

    public ShoppingCart findShoppingCart(Client client){
        ShoppingCart cart = null;
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from shopping_cart where client_id=?")){
            preparedStatement.setInt(1, client.getId());
            try(ResultSet result = preparedStatement.executeQuery()){
                if(result.next()) {
                    Integer nrProducts = result.getInt("nr_products");
                    Float price = result.getFloat("total_price");
                    Integer id = result.getInt("id");
                    cart = new ShoppingCart();
                    cart.setTotalPrice(price);
                    cart.setProductNumber(nrProducts);
                    cart.setId(id);
                    cart.setClient(client);
                    loadProductsInCart(cart);
                }
            }
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
        return cart;
    }

    private Product getProduct(Integer id){
        Connection connection = dbUtils.getConnection();
        Product product = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id=?")){
            preparedStatement.setInt(1, id);
            try(ResultSet result = preparedStatement.executeQuery()){
                //int id = result.getInt("id");
                String name = result.getString("name");
                String category = result.getString("category");
                float price = result.getFloat("price");
                String size = result.getString("size");
                int quantity = result.getInt("quantity");
                ProductStatus status = ProductStatus.AVAILABLE;
                if(quantity == 0)
                    status = ProductStatus.SOLD_OUT;
                if(quantity <= 10)
                    status = ProductStatus.LIMITED;
                product = new Product(name, Size.valueOf(size), price, Category.valueOf(category), quantity, status);
            }
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
        return product;
    }

    private void loadProductsInCart(ShoppingCart cart){
        Connection connection = dbUtils.getConnection();
        List<Integer> products = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product_order where cart_id=?")){
            preparedStatement.setInt(1, cart.getId());
            try(ResultSet result = preparedStatement.executeQuery()){
                while(result.next()){
                    int id = result.getInt("product_id");
                    products.add(id);
                }
            }
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
        for(Integer p: products)
            cart.add(getProduct(p));
    }

    public ShoppingCart initializeCart(Client client){
        ShoppingCart cart = findShoppingCart(client);
        if(cart == null) {
            cart = new ShoppingCart();
            Connection connection = dbUtils.getConnection();
            try (PreparedStatement ps = connection.prepareStatement("insert into shopping_cart (nr_products, total_price, client_id) values (?, ?, ?)")) {

                ps.setInt(1, cart.getProductNumber());
                ps.setFloat(2, cart.getTotalPrice());
                ps.setInt(3, client.getId());
                int result = ps.executeUpdate();

                PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid()");
                ResultSet result2 = ps2.executeQuery();
                int id = result2.getInt("last_insert_rowid()");
                cart.setId(id);
                cart.setClient(client);
            } catch (SQLException e) {
                System.err.println("Error DB" + e);
            }
        }
        return cart;
    }

    public void updateCart(ShoppingCart cart){
        Connection connection = dbUtils.getConnection();
        try(PreparedStatement ps = connection.prepareStatement("update shopping_cart set nr_products=?, total_price=? where id=?")){
            ps.setInt(1, cart.getProductNumber());
            ps.setFloat(2, cart.getTotalPrice());
            ps.setInt(3, cart.getId());
            int result = ps.executeUpdate();
        } catch(SQLException e){
            System.err.println("Error DB" + e);
        }
    }

    public void addProductCartPair(Product product, ShoppingCart cart){
        Connection connection = dbUtils.getConnection();
        try (PreparedStatement ps = connection.prepareStatement("insert into product_order (product_id, cart_id) values (?, ?)")) {

            ps.setInt(1, product.getId());
            ps.setFloat(2, cart.getId());
            int result = ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error DB" + e);
        }
    }

    public Administrator getAdmin(Integer nr, String e, String p) {
        Connection connection = dbUtils.getConnection();
        Administrator admin = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from admin where email=? and password=? and id=?")){
            preparedStatement.setString(1, e);
            preparedStatement.setString(2, p);
            preparedStatement.setInt(3, nr);
            try(ResultSet result = preparedStatement.executeQuery()){
                String email = result.getString("email");
                String passwd = result.getString("password");
                Integer id = result.getInt("id");
                admin = new Administrator(email, passwd);
                admin.setNumber(id);
            }
        } catch(SQLException ex){
            System.err.println("Error DB" + ex);
        }
        return admin;
    }
}
