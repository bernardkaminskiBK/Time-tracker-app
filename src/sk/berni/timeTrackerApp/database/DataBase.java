package sk.berni.timeTrackerApp.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataBase {

    private static final String URL = "jdbc:derby:timeTrackerDB;create=true";

    private Connection conn = null;
    private Statement createStatement = null;

    public DataBase() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection Successful.");
        } catch (SQLException e) {
            System.out.println("" + e);
            System.out.println("There is something wrong with the connection.");
            Logger.getLogger(DataBase.class.getName()).log(Level.SEVERE, null, e);
        }

        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException e) {
                System.out.println("There is something wrong with creating the create statement");
                e.printStackTrace();
            }
        }

        DatabaseMetaData dbmd = null;
        try {
            assert conn != null;
            dbmd = conn.getMetaData();
        } catch (SQLException e) {
            System.out.println("There is something wrong with creating DatabaseMetaData.");
        }

        try {
            assert dbmd != null;
            ResultSet rs = dbmd.getTables(null, "APP", "ACTIVITIES", null);
            if (!rs.next()) {
                String sql = "create table activities(id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),Date varchar(35), NameOfActivity varchar(20), Time varchar(30))";
                createStatement.execute(sql);
            }
        } catch (SQLException e) {
            System.out.println("There is something wrong with creating the data table.");
            e.printStackTrace();
        }

    }

    public void addNewActivity(Activity activity) {
        String sql = "insert into activities (Date, NameOfActivity, Time) values (?,?,?)";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, activity.getDate());
            preparedStatement.setString(2, activity.getNameOfActivity());
            preparedStatement.setString(3, activity.getActivityTime());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("Something is wrong with add new activity to database.");
            e.printStackTrace();
        }
    }

    public ArrayList<Activity> getAllActivities() {
        String sql = "select * from activities";
        ArrayList<Activity> activities = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            activities = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("Date");
                String nameOfActivity = rs.getString("NameOfActivity");
                String time = rs.getString("Time");
                int id = rs.getInt("id");
                Activity actualActivity = new Activity(id, date, nameOfActivity, time);
                activities.add(actualActivity);
            }
        } catch (SQLException e) {
            System.out.println("Something is wrong with get all activities.");
            e.printStackTrace();
        }
        return activities;
    }

    public ArrayList<Activity> getActivitiesByName(String name) {
        String sql = "select * from activities where NameOfActivity = '" + name + "'";
        ArrayList<Activity> activities = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            activities = new ArrayList<>();
            while (rs.next()) {
                String date = rs.getString("Date");
                String nameOfActivity = rs.getString("NameOfActivity");
                String time = rs.getString("Time");
                int id = rs.getInt("id");
                Activity actualActivity = new Activity(id, date, nameOfActivity, time);
                activities.add(actualActivity);
            }
        } catch (SQLException e) {
            System.out.println("Something is wrong with get all activities by name.");
            e.printStackTrace();
        }
        return activities;
    }

    public Set<String> getAllActivityNames() {
        String sql = "select NameOfActivity from activities";
        Set<String> activityNames = null;
        try {
            ResultSet rs = createStatement.executeQuery(sql);
            activityNames = new HashSet<>();
            while (rs.next()) {
                String nameOfActivity = rs.getString("NameOfActivity");
                activityNames.add(nameOfActivity);
            }
        } catch (SQLException e) {
            System.out.println("Something is wrong with get all activity names.");
            e.printStackTrace();
        }
        return activityNames;
    }

    public void removeActivity(Activity activity) {
        String sql = "delete from activities where Date = ?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, activity.getDate());
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("There is something wrong with delete activities from database.");
            e.printStackTrace();
        }
    }
    
    public void deleteAllRecords() {
    	String sql = "delete from activities";
    	try {
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("There is something wrong with delete all activites from database.");
            e.printStackTrace();
        }
    }

}
