package Wishlist.M.repository;

import Wishlist.M.model.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class WishlistRepository {

    private final DataSource dataSource;

    @Autowired
    public WishlistRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Gem ny wishlist
    public void save(Wishlist wishlist) {
        String query = "INSERT INTO wishlists (wishlist_Name, user_id) VALUES (?, ?)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, wishlist.getWishlistName());
            statement.setLong(2, wishlist.getUserId());

            int affectedRows = statement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    wishlist.setWishlistId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Find a wishlist by its ID
    public Optional<Wishlist> findById(Long id) {
        String query = "SELECT * FROM wishlists WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Wishlist wishlist = new Wishlist(
                        resultSet.getLong("wishlist_id"),
                        resultSet.getString("wishlist_name"),
                        resultSet.getLong("user_id")
                );
                return Optional.of(wishlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // Find wishlist specifik bruger
    public List<Wishlist> findByUserId(Long userId) {
        List<Wishlist> wishlists = new ArrayList<>();
        String query = "SELECT * FROM wishlists WHERE user_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setWishlistId(resultSet.getLong("wishlist_id"));
                wishlist.setWishlistName(resultSet.getString("wishlist_name"));
                wishlist.setUserId(resultSet.getLong("user_id"));
                wishlists.add(wishlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wishlists;
    }


    // ikke færdig
    public void deleteById(Long id) {
        String query = "DELETE FROM wishlists WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ikke færdig
    public void updateName(Long id, String newName) {
        String query = "UPDATE wishlists SET name = ? WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, newName);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

