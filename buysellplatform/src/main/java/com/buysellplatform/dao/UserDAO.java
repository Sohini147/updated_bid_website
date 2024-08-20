package com.buysellplatform.dao;

import com.buysellplatform.DatabaseUtil;
import com.buysellplatform.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private static final String INSERT_USER_SQL = "INSERT INTO users (name, email, college, whatsapp_number, password) VALUES (?, ?, ?, ?, ?)";
    private static final String LOGIN_SQL = "SELECT * FROM users WHERE email = ?";

    public boolean registerUser(User user) {
        boolean isRegistered = false;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getCollege());
            preparedStatement.setString(4, user.getWhatsappNumber());
            preparedStatement.setString(5, user.getPassword()); // Store plaintext password

            int rowsAffected = preparedStatement.executeUpdate();
            isRegistered = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isRegistered;
    }

    public User loginUser(String email, String password) {
        User user = null;

        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(LOGIN_SQL)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");

                // Check if the provided password matches the stored password
                if (password.equals(storedPassword)) {
                    user = new User();
                    user.setId(resultSet.getInt("id"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setCollege(resultSet.getString("college"));
                    user.setWhatsappNumber(resultSet.getString("whatsapp_number"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
