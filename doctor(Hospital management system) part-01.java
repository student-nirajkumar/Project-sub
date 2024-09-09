
package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;

    public class doctor {
        private Connection connection;


        public doctor(Connection connection) {
            this.connection = connection;

        }

        // making the first methods //
        public void viewDoctors() {
            String query = "select *from doctors";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                System.out.println("Doctors");
                System.out.println("+------------+-------------------------------+---------------------------+");
                System.out.println("| Doctor_ID  | Name                          | Specialization            |");
                System.out.println("+------------+-------------------------------+---------------------------+");
                while (resultSet.next()) {

                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String specialization = resultSet.getString("specialization");
                    System.out.printf("|%-12s|%-31s|%-27s|\n", id , name, specialization);
                    System.out.println("+------------+-------------------------------+---------------------------+");                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        // making the 3rd methods //
        public boolean getDoctorById(int id)
        {
            String query = "SELECT *from doctors WHERE id= ?";
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


