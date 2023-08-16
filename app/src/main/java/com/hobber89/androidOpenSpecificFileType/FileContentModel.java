package com.hobber89.androidOpenSpecificFileType;

import org.json.JSONException;
import org.json.JSONObject;

public class FileContentModel {

    private String content;

    public FileContentModel(String content) {
        this.content = content;
    }

    public JSONObject toJson() {
        try {
            JSONObject object = new JSONObject();
            object.put("content", content);
            return object;
        } catch (JSONException error) {
            return null;
        }
    }
}
