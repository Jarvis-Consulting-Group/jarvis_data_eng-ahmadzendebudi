package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;

public class Customer implements DataTransferObject {

  private long id;
  private String name;


  public void setId(long id) {
    this.id = id;
  }

  @Override
  public long getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  @Override
  public String toString() {
    return "Customer [id=" + id + ", name=" + name + "]";
  }

  
}
