package com.iknoortech.mitshubishidemo.model.survey;

public class SurveyPojo {

    private String status_code;

    private SurveyData survey;

    private String id;

    private String message;

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public SurveyData getSurvey ()
    {
        return survey;
    }

    public void setSurvey (SurveyData survey)
    {
        this.survey = survey;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [status_code = "+status_code+", survey = "+survey+", id = "+id+", message = "+message+"]";
    }
}
