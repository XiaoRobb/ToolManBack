package com.whu.toolman.util;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {

    public static String GetContent(String jsonString){
        String returnString = "";
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray=  jsonObject.getJSONArray("block");
            jsonObject = jsonArray.getJSONObject(0);
            jsonArray = jsonObject.getJSONArray("line");
            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                JSONArray jsonArrayTemp = jsonObject.getJSONArray("word");
                String lineString = "";
                for(int j=0; j<jsonArrayTemp.length(); j++){
                    jsonObject = jsonArrayTemp.getJSONObject(j);
                    lineString += jsonObject.getString("content") + " ";
                }
                returnString += lineString + "\n";
            }
            return returnString;
        }catch (Exception e){
            return "";
        }
    }
}
