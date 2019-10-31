package com.iknoortech.mitshubishidemo.model.feedbackList;

import java.util.ArrayList;

public class FeedbackListPojo {

    private String status_code;

    private ArrayList<FeedbackListData> data;

    private String message;

    private String Url;

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public ArrayList<FeedbackListData> getData() {
        return data;
    }

    public void setData(ArrayList<FeedbackListData> data) {
        this.data = data;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getUrl ()
    {
        return Url;
    }

    public void setUrl (String Url)
    {
        this.Url = Url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", data = "+data+", message = "+message+", Url = "+Url+"]";
    }

}
