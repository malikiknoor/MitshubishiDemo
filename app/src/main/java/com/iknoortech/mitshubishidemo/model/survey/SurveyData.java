package com.iknoortech.mitshubishidemo.model.survey;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SurveyData {

    private String question;

    private String option3;

    private String option4;

    private String option1;

    private String option2;

    private String question_id;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion ()
    {
        return question;
    }

    public void setQuestion (String question)
    {
        this.question = question;
    }

    public String getOption3 ()
    {
        return option3;
    }

    public void setOption3 (String option3)
    {
        this.option3 = option3;
    }

    public String getOption4 ()
    {
        return option4;
    }

    public void setOption4 (String option4)
    {
        this.option4 = option4;
    }

    public String getOption1 ()
    {
        return option1;
    }

    public void setOption1 (String option1)
    {
        this.option1 = option1;
    }

    public String getOption2 ()
    {
        return option2;
    }

    public void setOption2 (String option2)
    {
        this.option2 = option2;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [question = "+question+", option3 = "+option3+", option4 = "+option4+", option1 = "+option1+", option2 = "+option2+"]";
    }

}