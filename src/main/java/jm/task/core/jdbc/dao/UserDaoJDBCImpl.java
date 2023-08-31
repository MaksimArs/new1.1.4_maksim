package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sqlCommand = "CREATE TABLE users (id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(45), lastName VARCHAR(45), age INT)";
        try (Statement statement = Util.connection().createStatement()) {
            statement.executeUpdate(sqlCommand);
            System.out.println("Таблица создана");
        } catch (SQLException e) {
            System.err.println("таблица уже существует");
        }
    }

    public void dropUsersTable() {
        String sqlCommand2 = "DROP TABLE IF EXISTS users";
        try (Statement statement = Util.connection().createStatement()) {
            statement.executeUpdate(sqlCommand2);
            System.out.println("таблица удалена");
        } catch (SQLException e) {
            System.err.println("таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlCommand3 = "INSERT INTO users (name,lastName,age) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = Util.connection().prepareStatement(sqlCommand3);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + ", добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        String sqlCommand5 = "delete FROM users WHERE id = ?";
        try (PreparedStatement PStatement = Util.connection().prepareStatement(sqlCommand5)) {
            PStatement.setLong(1, id);
            PStatement.executeUpdate();
            System.out.println("с таблицы удален User с id: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sqlCommand4 = "SELECT * FROM users";
        List<User> list = null;
        try (Statement statement = Util.connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlCommand4);
            list = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            System.out.println("ne ok");
        }
        return list;
    }

    public void cleanUsersTable() {
        String sqlCommand5 = "delete FROM users";
        try (Statement statement = Util.connection().createStatement()) {
            statement.executeUpdate(sqlCommand5);
            System.out.println("таблица очищена");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
