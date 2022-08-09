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

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            if (Util.tableExists(connection)) {
                System.out.println("Таблица уже существует!");
            } else {
                statement.executeUpdate(Util.getSqlCreateTable());
                System.out.println("Таблица успешно создана!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании таблицы", e);
        }
    }

    public void dropUsersTable() {

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            if (!Util.tableExists(connection)) {
                System.out.println("Таблица не существует!");
            } else {
                statement.executeUpdate(Util.getSqlDropTable());
                System.out.println("Таблица успешно удалена!");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении таблицы", e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sqlSaveUser = "INSERT INTO Task1.User (name, lastName, age) VALUES(?, ?, ?)";

        try (Connection connection = Util.getConnection()) {

            PreparedStatement prepareStatement = connection.prepareStatement(sqlSaveUser);

            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setByte(3, age);

            prepareStatement.executeUpdate();
            System.out.printf("User с именем – %s добавлен в базу дынных%n", name);

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при добавлении пользователя", e);
        }
    }

    public void removeUserById(long id) {

        String sqlRemoveUserById = String.format("DELETE from Task1.User where user_id=%d", id);

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            statement.executeUpdate(sqlRemoveUserById);
            System.out.println("Пользователь успешно удален!");

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пользователя", e);
        }

    }

    public List<User> getAllUsers() {

        String sqlGetAllUsers = "SELECT user_id, name, lastName, age FROM Task1.User";
        List<User> usersList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);

            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);

                User user = new User(name, lastName, age);
                user.setId(id);
                usersList.add(user);

            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обработке пользователей", e);
        }

        return usersList;
    }

    public void cleanUsersTable() {

        String sqlCleanTable = "DELETE FROM Task1.User";

        try (Connection connection = Util.getConnection();
             Statement statement = Util.getStatement(connection)) {

            statement.executeUpdate(sqlCleanTable);
            System.out.println("Все строки успешно удалены!");


        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении строк таблицы", e);
        }
    }
}
