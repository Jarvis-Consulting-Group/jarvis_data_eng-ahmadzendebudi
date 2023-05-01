package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ca.jrvs.apps.jdbc.util.DataAccessObject;

public class CustomerDAO extends DataAccessObject<Customer> {

  private static final String INSERT = "INSERT INTO customer (name) values(?)";
  private static final String GET_ONE = "SELECT name FROM customer where id = ?";
  private static final String GET_ALL = "SELECT id, name FROM customer";
  private static final String UPDATE = "UPDATE customer set name=? where id = ?";
  private static final String DELETE = "DELETE FROM customer where id = ?";

  public CustomerDAO(Connection connection) {
    super(connection);
  }

  @Override
  public Customer findById(long id) {
    Customer customer = new Customer();
    customer.setId(id);
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ONE)) {
      statement.setLong(1, id);
      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        customer.setName(resultSet.getString(1));
        return customer;
      }
      return null;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public List<Customer> findAll() {
    List<Customer> customers = new ArrayList<>();
    try (PreparedStatement statement = this.connection.prepareStatement(GET_ALL)) {
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Customer customer = new Customer();
        customer.setId(resultSet.getLong(1));
        customer.setName(resultSet.getString(2));
        customers.add(customer);
      }
      return customers;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Customer update(Customer dto) {
    try (PreparedStatement statement = this.connection.prepareStatement(UPDATE)) {
      statement.setString(1, dto.getName());
      statement.setLong(2, dto.getId());
      statement.execute();
      return findById(dto.getId());
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Customer create(Customer dto) {
    try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
      statement.setString(1, dto.getName());
      statement.execute();
      return null;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void delete(long id) {
    try (PreparedStatement statement = this.connection.prepareStatement(DELETE)) {
      statement.setLong(1, id);
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
  
}
