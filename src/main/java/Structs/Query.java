package Structs;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by matan on 12/10/15.
 */
public class Query
{
    String name;
    String query;
    float progress;
    int id;
    int users;
    int answers;

    public Query(String name, String query, float progress, int id, int users, int answers)
    {
        this.name = name;
        this.query = query;
        this.progress = progress;
        this.id = id;
        this.users = users;
        this.answers = answers;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getQuery()
    {
        return query;
    }

    public void setQuery(String query)
    {
        this.query = query;
    }

    public float getProgress()
    {
        return progress;
    }

    public void setProgress(float progress)
    {
        this.progress = progress;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getUsers()
    {
        return users;
    }

    public void setUsers(int users)
    {
        this.users = users;
    }

    public int getAnswers()
    {
        return answers;
    }

    public void setAnswers(int answers)
    {
        this.answers = answers;
    }

    public String toString()
    {
        return toJSON().toString();
    }

    public JSONObject toJSON()
    {
        JSONObject res = new JSONObject();
        try
        {
            res.put("name", name);
            res.put("query", query);
            res.put("progress", progress);
            res.put("id", id);
            res.put("users", users);
            res.put("answers", answers);
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
        return res;
    }
}
