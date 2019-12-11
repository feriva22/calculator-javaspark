import com.squareup.okhttp.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public static final MediaType JSON = MediaType.parse("application/json");
    public static final String host = "http://localhost";
    public static final int port = 8080;
    public static final OkHttpClient client = new OkHttpClient();
    public static final JSONParser parser = new JSONParser();
    public static Double total = null;

    public static void main(String[] args) throws IOException, ParseException {
        Scanner input = new Scanner(System.in);

        while (true){
            if(total == null){
                System.out.println("Total : "+0);
            }else{
                System.out.println("Total : "+total);
            }

            double val1 = 0;
            double val2 = 0;
            if(total == null){
                System.out.print("input val1:");
                while (!input.hasNextDouble()) {
                    System.out.println("Masukkan angka!");
                    input.next(); // this is important!
                    System.out.print("input val1:");
                }
                val1 = input.nextDouble();
                System.out.print("input val2:");
                while (!input.hasNextDouble()) {
                    System.out.println("Masukkan angka!");
                    input.next(); // this is important!
                    System.out.print("input val2:");
                }
                val2 = input.nextDouble();
            }else{
                val1 = total;
                System.out.print("input val:");
                while (!input.hasNextDouble()) {
                    System.out.println("Masukkan angka!");
                    input.next(); // this is important!
                    System.out.print("input val:");
                }
                val2 = input.nextDouble();
            }

            while(true){
                System.out.print("Operasi :");
                String operation = input.next();
                if(operation.equals("+")){
                    total = operationPlus(val1,val2);
                    System.out.println("Total : "+total);
                    break;
                }
                else if(operation.equals("-")){
                    total =  operationMinus(val1,val2);
                    System.out.println("Total :"+total);
                    break;
                }
                else if(operation.equals("*")){
                    total = operationMultiply(val1,val2);
                    System.out.println("Total :"+total);
                    break;
                }
                else if(operation.equals("/")){
                    total = operationDiv(val1,val2);
                    System.out.println("Total :"+total);
                    break;
                }
                else {
                    continue;
                }
            }
            System.out.print("Command  (exit/clear/continue) ?");
            String prompt = input.next();
            if(prompt.equals("exit"))
                return;
            else if(prompt.equals("clear")) {
                total = null;
                continue;
            }
            else{
                continue;
            }
        }
    }

    public static Response CreatePostRequest(String host,int port, String path, String body) throws IOException {
        RequestBody reqBodyObj = RequestBody.create(JSON, body);
        Request request = new Request.Builder()
                .url(host+":"+port+path)
                .post(reqBodyObj)
                .build();
        return client.newCall(request).execute();
    }

    public static double operationPlus(double val1, double val2) throws IOException, ParseException {
        //request json object
        JSONObject reqObj = new JSONObject();
        reqObj.put("val1",val1);
        reqObj.put("val2",val2);
        Response response = CreatePostRequest(host,port,"/operation/plus",reqObj.toJSONString());

        JSONObject json = (JSONObject) parser.parse(response.body().string());  //result json object

        if(response.code() == 200){
            Double result = Double.parseDouble(json.get("result").toString());
            return result;
        }
        else{
            System.out.println(json.get("message"));
            System.exit(1);
        }
        return -1;
    }

    public static double operationMinus(double val1, double val2) throws IOException, ParseException {
        //request json object
        JSONObject reqObj = new JSONObject();
        reqObj.put("val1",val1);
        reqObj.put("val2",val2);
        Response response = CreatePostRequest(host,port,"/operation/minus",reqObj.toJSONString());

        JSONObject json = (JSONObject) parser.parse(response.body().string());  //result json object

        if(response.code() == 200){
            Double result = Double.parseDouble(json.get("result").toString());
            return result;
        }
        else{
            System.out.println(json.get("message"));
            System.exit(1);
        }
        return -1;
    }

    public static double operationMultiply(double val1, double val2) throws IOException, ParseException {
        //request json object
        JSONObject reqObj = new JSONObject();
        reqObj.put("val1",val1);
        reqObj.put("val2",val2);
        Response response = CreatePostRequest(host,port,"/operation/multiply",reqObj.toJSONString());

        JSONObject json = (JSONObject) parser.parse(response.body().string());  //result json object

        if(response.code() == 200){
            Double result = Double.parseDouble(json.get("result").toString());
            return result;
        }
        else{
            System.out.println(json.get("message"));
            System.exit(1);
        }
        return -1;
    }

    public static double operationDiv(double val1, double val2) throws IOException, ParseException {
        //request json object
        JSONObject reqObj = new JSONObject();
        reqObj.put("val1",val1);
        reqObj.put("val2",val2);
        Response response = CreatePostRequest(host,port,"/operation/div",reqObj.toJSONString());

        try{
            JSONObject json = (JSONObject) parser.parse(response.body().string());  //result json object
            if(response.code() == 200){
                Double result = Double.parseDouble(json.get("result").toString());
                return  result;
            }
            else{
                System.out.println(json.get("message"));
                System.exit(1);
            }
        }
        catch (ParseException e){
            System.out.println("Error Program");
            System.exit(1);
        }
        return -1;
    }
}
