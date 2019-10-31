package com.iknoortech.mitshubishidemo.model.createFeedback;

public class CreateFeedbackPojo {

    private String status_code;

    private String GeneratedIdString;

    private String TimeDuration;

    private String message;

    public String getStatus_code ()
    {
        return status_code;
    }

    public void setStatus_code (String status_code)
    {
        this.status_code = status_code;
    }

    public String getGeneratedIdString ()
    {
        return GeneratedIdString;
    }

    public void setGeneratedIdString (String GeneratedIdString)
    {
        this.GeneratedIdString = GeneratedIdString;
    }

    public String getTimeDuration ()
    {
        return TimeDuration;
    }

    public void setTimeDuration (String TimeDuration)
    {
        this.TimeDuration = TimeDuration;
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
        return "ClassPojo [status_code = "+status_code+", GeneratedIdString = "+GeneratedIdString+", TimeDuration = "+TimeDuration+", message = "+message+"]";
    }

}
