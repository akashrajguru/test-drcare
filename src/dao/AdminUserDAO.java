package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.AdminUser;

public enum AdminUserDAO {
  instance;

  public Connection getConnection() {

    Connection connection = null;
    try {
      Class.forName("com.mysql.jdbc.Driver");
      connection = DriverManager.getConnection(
    		  "jdbc:mysql://79.97.123.177:3306/drcare", "Doctors_Care", "MSc_2017");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return connection;
  }

  public void save(AdminUser user) {
    Connection connection = getConnection();

    try {
      PreparedStatement psmt = connection
          .prepareStatement("INSERT INTO ADMIN_USER (email, password, address) VALUES (?, ?, ?)");
      psmt.setString(1, user.getEmail());
      psmt.setString(2, user.getPassword());
      psmt.setString(3, user.getAddress());

      psmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public AdminUser checkLogin(String email, String password) {
    Connection connection = getConnection();
    AdminUser user = null ;

    try {
      PreparedStatement psmt = connection
          .prepareStatement("SELECT * FROM ADMIN_USER WHERE EMAIL = ? AND PASSWORD = ?");
      psmt.setString(1, email);
      psmt.setString(2, password);
      ResultSet rs = psmt.executeQuery();
      if (rs.next()) {
        user = new AdminUser(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("address")) ;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return user ;
  }

}
