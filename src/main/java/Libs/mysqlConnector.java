package Libs;

import Structs.Query;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                            "timeout int not null," +
                            "answered boolean," +
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
        boolean dev = System.getenv("OPENSHIFT_HOMEDIR") == null;
        String host = dev ? "localhost": System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = dev ? "3306": System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String name = "test";
        String user = dev ? "root": System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = dev ? "10101010": System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");
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
            statement.execute();
            int count = statement.getUpdateCount();
            System.out.println("Update count: " + count);
            return count > 0;
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

    public Query getQuery(int id)
    {
        Query query = new Query("", "", 0, id, 0, 0);
        try
        {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM queries WHERE id=?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(!resultSet.first()){
                return null;
            }
            query.setName(resultSet.getString("name"));
            query.setQuery(resultSet.getString("sparql"));

            statement = con.prepareStatement("SELECT user_id from questions WHERE query_id=? AND answered=TRUE");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            int answers = 0;
            Set<Integer> userSet = new HashSet<Integer>();
            while(resultSet.next())
            {
                answers++;
                userSet.add(resultSet.getInt("user_id"));
            }
            query.setAnswers(answers);
            query.setUsers(userSet.size());
            query.setProgress(answers/20);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return query;
    }

    public List<Query> getQueries(int user_id)
    {
        List<Query> result = new ArrayList<Query>();
        try
        {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM queries WHERE userId=?");
            statement.setInt(1, user_id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
            {
                result.add(new Query(
                        resultSet.getString("name"),
                        resultSet.getString("sparql"),
                        0, // TODO progress
                        resultSet.getInt("id"),
                        0,
                        0
                ));
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}