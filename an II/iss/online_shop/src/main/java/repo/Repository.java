package repo;

import model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    //private final DBUtils dbUtils;
    private final SessionFactory sessionFactory;

    public Repository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Client getClient(String email, String password){
        Client client = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "FROM Client E WHERE E.email = :usrn AND E.password = :psswd";
                Query query = session.createQuery(hql);
                query.setParameter("usrn", email);
                query.setParameter("psswd", password);
                client = (Client) query
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Logam clientul " + client.getEmail());
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la get client " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return client;

//        Connection connection = dbUtils.getConnection();
//        Client client = null;
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from client where email=? and password=?")){
//            preparedStatement.setString(1, email);
//            preparedStatement.setString(2, password);
//            try(ResultSet result = preparedStatement.executeQuery()){
//                String e = result.getString("email");
//                String p = result.getString("password");
//                Integer id = result.getInt("id");
//                client = new Client(e, p);
//                client.setId(id);
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        return client;
    }

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                products = session.createQuery("from Product ", Product.class)
                        .list();
                System.out.println(products.size() + " product(s) found:");
                for (Product p: products) {
                    ProductStatus status = ProductStatus.AVAILABLE;
                    if(p.getQuantity() == 0)
                        status = ProductStatus.SOLD_OUT;
                    if(p.getQuantity() <= 10)
                        status = ProductStatus.LIMITED;
                    p.setStatus(status);
                    System.out.println(p.getName() + " " + p.getId());
                }
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la find all products: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return products;



//        Connection connection = dbUtils.getConnection();
//        List<Product> products = new ArrayList<>();
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product")){
//            try(ResultSet result = preparedStatement.executeQuery()){
//                while(result.next()){
//                    int id = result.getInt("id");
//                    String name = result.getString("name");
//                    String category = result.getString("category");
//                    float price = result.getFloat("price");
//                    String size = result.getString("size");
//                    int quantity = result.getInt("quantity");
//                    ProductStatus status = ProductStatus.AVAILABLE;
//                    if(quantity == 0)
//                        status = ProductStatus.SOLD_OUT;
//                    if(quantity <= 10)
//                        status = ProductStatus.LIMITED;
//                    Product product = new Product(name, Category.valueOf(category), Size.valueOf(size), price, quantity);
//                    product.setId(id);
//                    product.setStatus(status);
//                    products.add(product);
//                }
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        return products;
    }

    public ShoppingCart findShoppingCart(Client client){
        ShoppingCart cart = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "FROM ShoppingCart S WHERE S.productNumber != -1 AND S.client.email =: usrn AND S.client.password =: psswd";
                Query query = session.createQuery(hql);
                query.setParameter("usrn", client.getEmail());
                query.setParameter("psswd", client.getPassword());
                cart = (ShoppingCart) query
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Cautam cos de cumparaturi pentru clientul " + client.getEmail());
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la get client " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return cart;

//
//
//
//
//        ShoppingCart cart = null;
//        Connection connection = dbUtils.getConnection();
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from shopping_cart where client_id=? and nr_products != -1")){
//            preparedStatement.setInt(1, client.getId());
//            try(ResultSet result = preparedStatement.executeQuery()){
//                if(result.next()) {
//                    Integer nrProducts = result.getInt("nr_products");
//                    Float price = result.getFloat("total_price");
//                    Integer id = result.getInt("id");
//                    cart = new ShoppingCart();
//                    cart.setTotalPrice(price);
//                    cart.setProductNumber(nrProducts);
//                    cart.setId(id);
//                    cart.setClient(client);
//                    if(cart.getProductNumber() > 0)
//                        loadProductsInCart(cart);
//                }
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        return cart;
    }

    private Product getProduct(Integer id){
        Product product = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "FROM Product WHERE id =: id";
                Query query = session.createQuery(hql);
                query.setParameter("id", id);
                product = (Product) query
                        .setMaxResults(1)
                        .uniqueResult();
                System.err.println("Cautam produs cu id: " + id);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la get produs " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return product;



//        Connection connection = dbUtils.getConnection();
//        Product product = null;
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product where id=?")){
//            preparedStatement.setInt(1, id);
//            try(ResultSet result = preparedStatement.executeQuery()){
//                //int id = result.getInt("id");
//                String name = result.getString("name");
//                String category = result.getString("category");
//                float price = result.getFloat("price");
//                String size = result.getString("size");
//                int quantity = result.getInt("quantity");
//                ProductStatus status = ProductStatus.AVAILABLE;
//                if(quantity == 0)
//                    status = ProductStatus.SOLD_OUT;
//                if(quantity <= 10)
//                    status = ProductStatus.LIMITED;
//                product = new Product(name, Category.valueOf(category), Size.valueOf(size), price, quantity);
//                product.setStatus(status);
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        return product;
    }

    private void loadProductsInCart(ShoppingCart cart){
        List<Integer> productIDs = new ArrayList<>();
        List<Product> products = new ArrayList<>();
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "from Product p where p.id in (select id from ShoppingCart where ShoppingCart.id =: id)";
                Query query = session.createQuery(hql);
                query.setParameter("id", cart.getId());
                products = query.list();
                for (Product p: products) {
                    productIDs.add(p.getId());
                }
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la find all products: " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        for(Integer p: productIDs)
            cart.add(getProduct(p));


//
//        Connection connection = dbUtils.getConnection();
//        List<Integer> products = new ArrayList<>();
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from product_order where cart_id=?")){
//            preparedStatement.setInt(1, cart.getId());
//            try(ResultSet result = preparedStatement.executeQuery()){
//                while(result.next()){
//                    int id = result.getInt("product_id");
//                    products.add(id);
//                }
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        for(Integer p: products)
//            cart.add(getProduct(p));
    }

    public ShoppingCart initializeCart(Client client){
        ShoppingCart cart = findShoppingCart(client);
        if(cart == null || cart.getProductNumber() == -1) {
            cart = new ShoppingCart();
            cart.setClient(client);
            try (Session session = sessionFactory.openSession()) {
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    session.save(cart);
                    tx.commit();
                } catch (RuntimeException ex) {
                    System.err.println("Eroare la inserare " + ex);
                    if (tx != null)
                        tx.rollback();
                }
            }
        }
        return cart;


//            Connection connection = dbUtils.getConnection();
//            try (PreparedStatement ps = connection.prepareStatement("insert into shopping_cart (nr_products, total_price, client_id) values (?, ?, ?)")) {
//
//                ps.setInt(1, cart.getProductNumber());
//                ps.setFloat(2, cart.getTotalPrice());
//                ps.setInt(3, client.getId());
//                int result = ps.executeUpdate();
//
//                PreparedStatement ps2 = connection.prepareStatement("SELECT last_insert_rowid()");
//                ResultSet result2 = ps2.executeQuery();
//                int id = result2.getInt("last_insert_rowid()");
//                cart.setId(id);
//                cart.setClient(client);
//            } catch (SQLException e) {
//                System.err.println("Error DB" + e);
//            }
//        }
//        return cart;
    }

    public void updateCart(ShoppingCart cart){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hqlUpdate = "update ShoppingCart s set s.productNumber =: nrProd, s.totalPrice =: tPrice WHERE id = :id";
                session.createQuery(hqlUpdate)
                        .setParameter("nrProd", cart.getProductNumber())
                        .setParameter("tPrice", cart.getTotalPrice())
                        .setParameter("id", cart.getId())
                        .executeUpdate();
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }




//        Connection connection = dbUtils.getConnection();
//        try(PreparedStatement ps = connection.prepareStatement("update shopping_cart set nr_products=?, total_price=? where id=?")){
//            ps.setInt(1, cart.getProductNumber());
//            ps.setFloat(2, cart.getTotalPrice());
//            ps.setInt(3, cart.getId());
//            int result = ps.executeUpdate();
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
    }

    public void addProductCartPair(Product product, ShoppingCart cart){
        ProductOrder productOrder = new ProductOrder(product, cart);
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(productOrder);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }





//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("insert into product_order (product_id, cart_id) values (?, ?)")) {
//
//            ps.setInt(1, product.getId());
//            ps.setFloat(2, cart.getId());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public Administrator getAdmin(Integer nr, String e, String p) {
        Administrator admin = null;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "FROM Administrator A WHERE A.username = :usrn AND A.password = :psswd AND A.id =: id";
                Query query = session.createQuery(hql);
                query.setParameter("usrn", e);
                query.setParameter("psswd", p);
                query.setParameter("id", nr);
                admin = (Administrator) query
                        .setMaxResults(1)
                        .uniqueResult();
//                System.err.println("Logam clientul " + client.getEmail());
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la get admin " + ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return admin;




//
//        Connection connection = dbUtils.getConnection();
//        Administrator admin = null;
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from admin where email=? and password=? and id=?")){
//            preparedStatement.setString(1, e);
//            preparedStatement.setString(2, p);
//            preparedStatement.setInt(3, nr);
//            try(ResultSet result = preparedStatement.executeQuery()){
//                String email = result.getString("email");
//                String passwd = result.getString("password");
//                Integer id = result.getInt("id");
//                admin = new Administrator(email, passwd);
//                admin.setNumber(id);
//            }
//        } catch(SQLException ex){
//            System.err.println("Error DB" + ex);
//        }
//        return admin;
    }

    public void addProduct(Product product){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(product);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("insert into product (name, category, price, size, quantity) values (?, ?, ?, ?, ?)")) {
//
//            ps.setString(1, product.getName());
//            ps.setString(2, String.valueOf(product.getCategory()));
//            ps.setFloat(3, product.getPrice());
//            ps.setString(4, String.valueOf(product.getSize()));
//            ps.setInt(5, product.getQuantity());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void deleteProduct(Product product){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();

                session.delete(product);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }


//
//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("delete from product where id = " + product.getId())) {
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void updateProduct(Product selectedProduct, Product updatedProduct){
        try(Session session = sessionFactory.openSession()){
            Transaction tx=null;
            try{
                tx = session.beginTransaction();
                String hqlUpdate = "update Product p set p.name =: nm, p.category =: c, p.price =: p, p.size =: s, p.quantity =: q where id = :id";
                session.createQuery(hqlUpdate)
                        .setParameter("nm", updatedProduct.getName())
                        .setParameter("c", updatedProduct.getCategory())
                        .setParameter("p", updatedProduct.getPrice())
                        .setParameter("s", updatedProduct.getSize())
                        .setParameter("q", updatedProduct.getQuantity())
                        .setParameter("id", selectedProduct.getId())
                        .executeUpdate();
                tx.commit();

            } catch(RuntimeException ex){
                System.err.println("Eroare la update "+ex);
                if (tx!=null)
                    tx.rollback();
            }
        }




//
//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("update product set name = ?, category = ?, price = ?, size = ?, quantity = ? where id = " + selectedProduct.getId())) {
//
//            ps.setString(1, updatedProduct.getName());
//            ps.setString(2, String.valueOf(updatedProduct.getCategory()));
//            ps.setFloat(3, updatedProduct.getPrice());
//            ps.setString(4, String.valueOf(updatedProduct.getSize()));
//            ps.setInt(5, updatedProduct.getQuantity());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void saveAddress(Address address){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(address);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }


//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("insert into address (county, city, street, number) values (?, ?, ?, ?)")) {
//
//            ps.setString(1, address.getCounty());
//            ps.setString(2, address.getCity());
//            ps.setString(3, address.getStreet());
//            ps.setInt(4, address.getNumber());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void saveCard(BankCard card){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(card);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }

//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("insert into bank_card (number, expiration_date, validation_code) values (?, ?, ?)")) {
//
//            ps.setString(1, card.getNumber());
//            ps.setDate(2, Date.valueOf(card.getExpirationDate()));
//            ps.setString(3, card.getValidationCode());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void saveOrder(Order order){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(order);
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
//        Connection connection = dbUtils.getConnection();
//        try (PreparedStatement ps = connection.prepareStatement("insert into orders (address_id, card_id, cart_id) values (?, ?, ?)")) {
//
//            ps.setInt(1, order.getAddress().getId());
//            ps.setString(2, order.getCard().getNumber());
//            ps.setInt(3, order.getCart().getId());
//            int result = ps.executeUpdate();
//
//        } catch (SQLException e) {
//            System.err.println("Error DB" + e);
//        }
    }

    public void updateProductsQuantity(List<Product> cartProducts){
        for(Product p: cartProducts){
            p.setQuantity(p.getQuantity() - 1);
            updateProduct(p, p);
        }
    }

    public int getMaxIDAddress() {
        int ID = 0;
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                String hql = "select max(id) from Address";
                Query query = session.createQuery(hql);
                ID = (int) query.setMaxResults(1).uniqueResult();
                System.err.println("get max address id");
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la select registered agent "+ex);
                if (tx != null)
                    tx.rollback();
            }
        }
        return ID;






//        int ID = 0;
//        Connection connection = dbUtils.getConnection();
//        try(PreparedStatement preparedStatement = connection.prepareStatement("select max(id) from address")){
//            try(ResultSet result = preparedStatement.executeQuery()){
//                ID = result.getInt(1);
//            }
//        } catch(SQLException e){
//            System.err.println("Error DB" + e);
//        }
//        return ID;
    }
}
