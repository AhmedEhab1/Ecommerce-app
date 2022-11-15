package com.macaria.app.utilities;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.HttpException;

public class JsonHelper {

    public static String isHttpException(Throwable error){
        if (error instanceof HttpException) {
            HttpException error2 = (HttpException)error;
            try {
                String errorBody = error2.response().errorBody().string();
                return getErrorMessageDetails(errorBody) ;
            } catch (IOException e) {
                e.printStackTrace();
                return error.toString() ;
            }
        }else return error.toString() ;
    }

    public static String getErrorMessageDetails(String errorMessage) {
        try {
            JSONObject jsonObject = new JSONObject(errorMessage);
            String userMessage = jsonObject.getString("message");
            return userMessage;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "error";
    }


}
