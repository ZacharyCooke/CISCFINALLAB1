package Database;

import Database.CrudRepository;
import edu.sdccd.cisc191.Workout;

import java.sql.*;
import java.util.*;
import java.util.Optional;

public class WorkoutRepository implements CrudRepository<Workout, String> {
    private Connection connection = null;

    public WorkoutRepository() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
            createTable();
        } catch (SQLException e) {
            System.err.println("SQLState: " + e.getSQLState());
            System.err.println("VendorError: " + e.getErrorCode());
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }

    private void createTable() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS WORKOUT (" +
                "id VARCHAR(255) PRIMARY KEY, " +
                "name VARCHAR(255) NOT NULL)";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createTableSQL);
        }
    }

    @Override
    public <S extends Workout> S save(S entity) {
        String insertSQL = "INSERT INTO WORKOUT (id, name) VALUES (?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            pstmt.setString(1, entity.getId());
            pstmt.setString(2, entity.getName());
            pstmt.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save the workout", e);
        }
    }

    @Override
    public Optional<Workout> findById(String id) {
        String selectSQL = "SELECT * FROM WORKOUT WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectSQL)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Workout workout = new Workout(rs.getString("id"), rs.getString("name"));
                    return Optional.of(workout);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the workout", e);
        }
        return Optional.empty();
    }

    @Override
    public Iterable<Workout> findAll() {
        List<Workout> workouts = new ArrayList<>();
        String selectAllSQL = "SELECT * FROM WORKOUT";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(selectAllSQL)) {
            while (rs.next()) {
                workouts.add(new Workout(rs.getString("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find workouts", e);
        }
        return workouts;
    }

    @Override
    public void deleteById(String id) {
        String deleteSQL = "DELETE FROM WORKOUT WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(deleteSQL)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete the workout", e);
        }
    }
}
