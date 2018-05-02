package com.fdi.pad.pethouse.database;

import com.fdi.pad.pethouse.entities.User;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class DatabaseUser {
    private static String SERVER_PATH = "https://manuhida.000webhostapp.com/user/";

    public static User checkUser(String email, String password) {
        User user = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("password", password);

            JSONObject object = myQuery(jsonObject, "checkUser.php");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    private static JSONObject myQuery(JSONObject jsonObject, String text) throws Exception{
        List list = new LinkedList();
        list.addAll(Arrays.asList(jsonObject));
        String jsonString = list.toString();

        String urlStr = SERVER_PATH + text;
        URL url = new URL(urlStr);

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("User-Agent", "your user agent");
        connection.setRequestProperty("Accept-Language", "sp,SP;q=0.5");

        String urlParameters = "json=" + jsonString;

        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        InputStream input_stream;

        int status = connection.getResponseCode();

        if (status != HttpsURLConnection.HTTP_OK)
            input_stream = connection.getErrorStream();
        else
            input_stream = connection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input_stream));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return new JSONObject(response.toString());
    }
}
