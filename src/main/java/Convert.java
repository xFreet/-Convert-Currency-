import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class Convert {
    static String from,to;

    public static void main(String[] args) throws IOException {

        System.out.println("<amount> <from> <to>");
        Scanner sc=new Scanner(System.in);

        double amout=sc.nextInt();
        from=sc.next();
        to=sc.next();

        Double rate=readFromJSON("https://api.exchangeratesapi.io/latest?base="+from+"&symbols="+to);

        System.out.printf("%.2f %s: %.2f %s",amout,from,amout*rate,to);
    }

    public static Double  readFromJSON(String apiURL) throws IOException {
        String s=readFromApi(apiURL).toString();
        JsonElement jsonElement= new JsonParser().parse(s);
        JsonObject jsonObject=jsonElement.getAsJsonObject();
        jsonObject=jsonObject.getAsJsonObject("rates");
        return (jsonObject.get(to).getAsDouble());
    }
    public static StringBuilder readFromApi(String apiUrl) throws IOException {
        URL url=new URL(apiUrl);
        InputStream is=url.openStream();
        StringBuilder data=new StringBuilder();

        try(BufferedReader br=new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line=br.readLine())!=null) data.append(line);
        }
        return data;
    }
}
