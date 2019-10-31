package com.iknoortech.mitshubishidemo.model.privacy;

public class PrivacyData {
    private String pdf;

    private String title;

    public String getPdf ()
    {
        return pdf;
    }

    public void setPdf (String pdf)
    {
        this.pdf = pdf;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pdf = "+pdf+", title = "+title+"]";
    }

}
