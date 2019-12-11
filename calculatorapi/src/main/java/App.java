
import spark.Spark;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class App {

    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        JSONParser parser = new JSONParser();
        Spark.port(8080); //set port on 8080

        Spark.post("/operation/plus", (req, res) -> {
            res.type("application/json");
            JSONObject json = (JSONObject) parser.parse(req.body());
            String val1 = json.get("val1").toString();
            String val2 = json.get("val2").toString();

            if(isNullOrEmptyOrNotNumber(val1) || isNullOrEmptyOrNotNumber(val2)){
                res.status(400);
                return "{\"message\" : \"Bad Request\"}";
            }
            return "{\"val1\" : "+val1+"," +
                    "\"val2\" : "+val2+"," +
                    "\"result\" : "+(Double.parseDouble(val1)+Double.parseDouble(val2))+"}";
        });

        Spark.post("/operation/minus", (req, res) -> {
            res.type("application/json");
            JSONObject json = (JSONObject) parser.parse(req.body());
            String val1 = json.get("val1").toString();
            String val2 = json.get("val2").toString();

            if(isNullOrEmptyOrNotNumber(val1) || isNullOrEmptyOrNotNumber(val2)){
                res.status(400);
                return "{\"message\" : \"Bad Request\"}";
            }
            return "{\"val1\" : "+val1+"," +
                    "\"val2\" : "+val2+"," +
                    "\"result\" : "+(Double.parseDouble(val1)-Double.parseDouble(val2))+"}";
        });

        Spark.post("/operation/multiply", (req, res) -> {
            res.type("application/json");
            JSONObject json = (JSONObject) parser.parse(req.body());
            String val1 = json.get("val1").toString();
            String val2 = json.get("val2").toString();

            if(isNullOrEmptyOrNotNumber(val1) || isNullOrEmptyOrNotNumber(val2)){
                res.status(400);
                return "{\"message\" : \"Bad Request\"}";
            }

            return "{\"val1\" : "+val1+"," +
                    "\"val2\" : "+val2+"," +
                    "\"result\" : "+(Double.parseDouble(val1)*Double.parseDouble(val2))+"}";
        });

        Spark.post("/operation/div", (req, res) -> {
            res.type("application/json");
            JSONObject json = (JSONObject) parser.parse(req.body());
            String val1 = json.get("val1").toString();
            String val2 = json.get("val2").toString();

            if(isNullOrEmptyOrNotNumber(val1) || isNullOrEmptyOrNotNumber(val2) || Integer.parseInt(val2) == 0){
                res.status(400);
                return "{\"message\" : \"Bad Request\"}";
            }
            return "{\"val1\" : "+val1+"," +
                    "\"val2\" : "+val2+"," +
                    "\"result\" : "+(Double.parseDouble(val1)/Double.parseDouble(val2))+"}";
        });

        // Using Route
        Spark.notFound((req, res) -> {
            res.status(404);
            res.type("application/json");
            return "{\"message\":\"Route not found\"}";
        });

    }

    public static boolean isNullOrEmptyOrNotNumber(String val) {
        if(val != null || !val.isEmpty()){
            try{
                Double.parseDouble(val);
                return false;
            }catch(Exception e) {
                return true;
            }
        }
        return true;
    }



}
