package com.iknoortech.mitshubishidemo.model.feedbackList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackListReply implements Parcelable {

    @SerializedName("ReplyText")
    @Expose
    private String replyText;

    @SerializedName("addeddate")
    @Expose
    private String addeddate;

    @SerializedName("ReplyFrom")
    @Expose
    private String ReplyFrom;

    public final static Parcelable.Creator<FeedbackListReply> CREATOR = new Creator<FeedbackListReply>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FeedbackListReply createFromParcel(Parcel in) {
            return new FeedbackListReply(in);
        }

        public FeedbackListReply[] newArray(int size) {
            return (new FeedbackListReply[size]);
        }
    };

    protected FeedbackListReply(Parcel in) {
        this.replyText = ((String) in.readValue((String.class.getClassLoader())));
        this.addeddate = ((String) in.readValue((String.class.getClassLoader())));
        this.ReplyFrom = ((String) in.readValue((String.class.getClassLoader())));
    }

    public FeedbackListReply() {
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public String getReplyFrom() {
        return ReplyFrom;
    }

    public void setReplyFrom(String replyFrom) {
        ReplyFrom = replyFrom;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(replyText);
        dest.writeValue(addeddate);
        dest.writeValue(ReplyFrom);
    }

    public int describeContents() {
        return 0;
    }

}
