package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class UserDaoJDBCImpl implements UserDao {

    Util util = new Util();
    static Logger LOGGER;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        try {
            connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS Users(" +
                    "id bigint PRIMARY KEY AUTO_INCREMENT," +
                    "name varchar(30) NOT NULL," +
                    "lastName varchar(30) NOT NULL," +
                    "age int)");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось создать таблицу пользователей");
        } finally {
            connection.close();
        }
    }

    public void dropUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        try {
            connection.createStatement().executeUpdate("DROP TABLE IF EXISTS Users");
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось удалить пользователей из таблицы");
        } finally {
            connection.close();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        Connection connection = util.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Users ( name, lastName, age) VALUES(?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            System.out.println("Добавлен пользователь: " + name + " "+ lastName + "Возраст: " + age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось сохранить пользователя в таблицу");
        } finally {
            connection.close();
        }
    }

    public void removeUserById(long id) throws SQLException {
        Connection connection = util.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users WHERE(?)");
            preparedStatement.setLong(1,id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось удалить пользователя по id");
        } finally {
            connection.close();
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> userList = new ArrayList<>();
        Connection connection = util.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Users");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось получить данные всех пользователей");
        } finally {
            connection.close();
        }
        return userList;
    }

    public void cleanUsersTable() throws SQLException {
        Connection connection = util.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Users");
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            LOGGER.info("Не удалось удалить таблицу");
        } finally {
            connection.close();
        }
    }
}
