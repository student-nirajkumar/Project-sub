package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Manish12345@987";

    public static <Patient> void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);
        try {
            Connection connection = DriverManager.getConnection(url, username, password);

            // Create instances of Patient and Doctor using the connection
            patient patient = new patient(connection, scanner);
            doctor doctor = new doctor(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patient");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter Your Choice:");
                int choice = scanner.nextInt();

                switch(choice){
                    case 1:
                        // Add patient
                        patient.addPatient();
                        System.out.println();

                    case 2:
                        // View Patient
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // View Doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book Appointment
                        bookAppointment(patient , doctor,connection, scanner );
                        System.out.println();
                        break;
                    case 5:
                        return;
                    default:
                        System.out.println("Enter valid choice !!!");
                        break;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void bookAppointment(patient patient, doctor doctor,Connection connection, Scanner scanner) {
        System.out.println("Enter patient Id:");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctor Id:");
        int doctorId = scanner.nextInt();

        System.out.println("Enter Appointment date (YYYY-MM-DD): ");
        String appointmentDate = scanner.next();
        if (patient.getPatientById(patientId) && doctor.getDoctorById(doctorId)) {

        if (checkDoctorAvailability(doctorId, appointmentDate, connection)) {
            String appointmentQuery = "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(? , ? ,?)";
        try {
        PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
        preparedStatement.setInt(1, patientId);
            preparedStatement.setInt(2, doctorId);
            preparedStatement.setString(3, appointmentDate);
       int rowsAffected=preparedStatement.executeUpdate();
           if(rowsAffected>0)
            {
                System.out.println("Appointment Booked:");
            }else{
                System.out.println("Filed to Book Appointment");
            }


        }catch(SQLException e){
            e.printStackTrace();
        }



        }else {
            System.out.println("Doctor not available on this date!");

        }
    } else {

            System.out.println("Either doctor or patient doesn't exists:");
        }
        }
        public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
            String query = "SELECT COUNT(*) FROM appointment WHERE doctor_id = ? AND appointment_date= ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, doctorId);
                preparedStatement.setString(2, appointmentDate);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    if (count == 0) {
                        return true;

                    } else {
                        return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return false;




        }

    }


