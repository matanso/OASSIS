package BackEnd;

/**
 * Created by matan on 10/20/15.
 */
public class main
{
    public static void main(String[] args)
    {
        Wrapper.init("neo4jDB/", "file/fuckit.ttl");
        Wrapper.submitQuery("select ?x ?y WHERE {  ?x rdfs:subClassOf* <wordnet_food_107555863>.\n" +
                "?y rdfs:subClassOf* <wordnet_beverage_107881800> .\n" +
                "} ");
        Wrapper.getQuestion("", 1);
    }
}