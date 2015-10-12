package Libs;

import java.sql.*;

/**
 * Created by matan on 06/10/15.
 */
public class mysqlConnector
{
    private static String[] tables =
            new String[]{
                    "CREATE TABLE IF NOT EXISTS users(" +
                            "id int not null auto_increment, " +
                            "name nvarchar(50), " +
                            "email varchar(60), " +
                            "passhash varchar(60)," +
                            "unique(email)," +
                            "primary key(id))",

                    "CREATE TABLE IF NOT EXISTS queries(" +
                            "id int not null auto_increment," +
                            "name nvarchar(60)," +
                            "sparql TEXT CHARACTER SET utf8," +
                            "userId int not null," +
                            "primary key(id))",

                    "CREATE TABLE IF NOT EXISTS questions(" +
                            "id int not null auto_increment," +
                            "user_id int not null," +
                            "query_id int not null," +
                            "timeout int not null" +
                            "primary key(id))"};
    private Connection con;

    public boolean init()
    {
        try
        {
            Statement statement = con.createStatement();
            for(String query: tables)
            {
                statement.execute(query);
            }
            return true;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public mysqlConnector() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        String host = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String name = "test";
        String user = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
        String url = "jdbc:mysql://" + host + ":" + port + "/" + name;
        con = DriverManager.getConnection(url, user, password);
    }

    public void createTables()
    {

    }

    public int loginVerify(String email, String passhash)
    {
        try
        {
            PreparedStatement statement = con.prepareStatement("SELECT id FROM users WHERE email=? AND passhash=?");
            statement.setString(1, email);
            statement.setString(2, passhash);
            ResultSet result = statement.executeQuery();
            if (result.first())
            {
                return result.getInt("id");
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean signup(String email, String passhash, String name)
    {
        try
        {
            PreparedStatement statement = con.prepareStatement("INSERT INTO users (email, passhash, name) VALUES(?, ?, ?)");
            statement.setString(1, email);
            statement.setString(2, passhash);
            statement.setString(3, name);
            return statement.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    public boolean submitQuery(String name, int user_id, String sparql){
        try
        {
            PreparedStatement statement = con.prepareStatement("INSERT INTO queries (name, userId, sparql) VALUES (?, ?, ?)");
            statement.setString(1, name);
            statement.setInt(2, user_id);
            statement.setString(3, sparql);
            statement.execute();
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
