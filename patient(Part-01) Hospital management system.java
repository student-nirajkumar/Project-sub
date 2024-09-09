package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

public class patient {
    private Connection connection;
    private Scanner scanner;

    public patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    // making the first methods //
    public void addPatient() {
        System.out.println("Enter Patient Name:");
        String name = scanner.next();

        System.out.println("Enter Patient Age:");
        int age = scanner.nextInt();

        System.out.println("Enter Patient Gender:");
        String gender = scanner.next();

        // connection with the databases//
        try {
            String query = "INSERT INTO patient(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully.");
            } else {
                System.out.println("Failed to add Patient.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
// making the 2nd methods //
public void viewPatients() {
    String query = "SELECT * FROM patient"; // Corrected table name
    try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("Patients");
        System.out.println("+------------+-------------------------------+----------+----------------+");
        System.out.println("| Patient_ID | Name                          | Age      | Gender         |");
        System.out.println("+------------+-------------------------------+----------+----------------+");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            int age = resultSet.getInt("age");
            String gender = resultSet.getString("gender");
            System.out.printf("|%-12s|%-31s|%-10s|%-16s|\n", id, name, age, gender);
            System.out.println("+------------+-------------------------------+----------+----------------+");
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    // making the 3rd methods //
    public boolean getPatientById(int id)
    {
        String query = "SELECT *from patients WHERE id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
        }
            else {
                return false;
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

}
