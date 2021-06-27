package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import com.bridgelabz.DatabaseException;
import com.bridgelabz.DatabaseException.ExceptionType;



public class EmployeePayrollDBService {

    public ArrayList<EmployeePayrollData> readEmployeeData() throws DatabaseException {
        ArrayList<EmployeePayrollData> list = new ArrayList<EmployeePayrollData>();
        String query = "SELECT * FROM employee_payrolljdbc";
        try (Connection connection = getConnection();) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                System.out.println(name);
                Double salary = resultSet.getDouble("salary");
                LocalDate date = resultSet.getDate("start").toLocalDate();
                list.add(new EmployeePayrollData(id, name, salary, date));
            }
            return list;
        } catch (SQLException e) {
            throw new DatabaseException("Error while executing the query", ExceptionType.UNABLE_TO_EXECUTE_QUERY);
        }
    }

    private Connection getConnection() throws DatabaseException {
        String jdbcURL = "jdbc:mysql://localhost:3306/payroll_servicejdbc";
        String user = "root";
        String password = "Sql@777fa";
        Connection connection;
        try {
            connection = DriverManager.getConnection(jdbcURL, user, password);
            System.out.println("Connection successfully established!" + connection);
        } catch (SQLException e) {
            throw new DatabaseException("Unable to connect to the database", ExceptionType.UNABLE_TO_CONNECT);
        }
        return connection;
    }
}