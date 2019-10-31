package com.iknoortech.mitshubishidemo.model.feedbackList;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class FeedbackListData implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("ImageOne")
    @Expose
    private String imageOne;
    @SerializedName("ImageTwo")
    @Expose
    private String imageTwo;
    @SerializedName("ImageThree")
    @Expose
    private String imageThree;
    @SerializedName("ImageFour")
    @Expose
    private String imageFour;
    @SerializedName("Category")
    @Expose
    private String category;
    @SerializedName("CategoryGeneratedID")
    @Expose
    private String categoryGeneratedID;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("PostAsAnonymous")
    @Expose
    private String postAsAnonymous;
    @SerializedName("addeddate")
    @Expose
    private String addeddate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("Reply")
    @Expose
    private List<FeedbackListReply> reply = new ArrayList<>();

    public final static Parcelable.Creator<FeedbackListData> CREATOR = new Creator<FeedbackListData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FeedbackListData createFromParcel(Parcel in) {
            return new FeedbackListData(in);
        }

        public FeedbackListData[] newArray(int size) {
            return (new FeedbackListData[size]);
        }

    };

    protected FeedbackListData(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.imageOne = ((String) in.readValue((String.class.getClassLoader())));
        this.imageTwo = ((String) in.readValue((String.class.getClassLoader())));
        this.imageThree = ((String) in.readValue((String.class.getClassLoader())));
        this.imageFour = ((String) in.readValue((String.class.getClassLoader())));
        this.category = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryGeneratedID = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.postAsAnonymous = ((String) in.readValue((String.class.getClassLoader())));
        this.addeddate = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.reply, (FeedbackListReply.class.getClassLoader()));
    }

    public FeedbackListData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageOne() {
        return imageOne;
    }

    public void setImageOne(String imageOne) {
        this.imageOne = imageOne;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public String getImageFour() {
        return imageFour;
    }

    public void setImageFour(String imageFour) {
        this.imageFour = imageFour;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryGeneratedID() {
        return categoryGeneratedID;
    }

    public void setCategoryGeneratedID(String categoryGeneratedID) {
        this.categoryGeneratedID = categoryGeneratedID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostAsAnonymous() {
        return postAsAnonymous;
    }

    public void setPostAsAnonymous(String postAsAnonymous) {
        this.postAsAnonymous = postAsAnonymous;
    }

    public String getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FeedbackListReply> getReply() {
        return reply;
    }

    public void setReply(List<FeedbackListReply> reply) {
        this.reply = reply;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(imageOne);
        dest.writeValue(imageTwo);
        dest.writeValue(imageThree);
        dest.writeValue(imageFour);
        dest.writeValue(category);
        dest.writeValue(categoryGeneratedID);
        dest.writeValue(description);
        dest.writeValue(postAsAnonymous);
        dest.writeValue(addeddate);
        dest.writeValue(status);
        dest.writeList(reply);
    }

    public int describeContents() {
        return 0;
    }
}
