package com.iknoortech.mitshubishidemo.connection;

import com.iknoortech.mitshubishidemo.model.createFeedback.CreateFeedbackPojo;
import com.iknoortech.mitshubishidemo.model.feedbackList.FeedbackListPojo;
import com.iknoortech.mitshubishidemo.model.privacy.PrivacyPojo;
import com.iknoortech.mitshubishidemo.model.survey.SurveyPojo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    /*create feedback*/
    @FormUrlEncoded
    @POST("feedback.php")
    Call<CreateFeedbackPojo> createFeedback(@Field("data") String data);

    /*get feedback list*/
    @FormUrlEncoded
    @POST("feedback-list.php")
    Call<FeedbackListPojo> getFeedbackList(@Field("Id") String Id);

    /*get feedback detail*/
    @FormUrlEncoded
    @POST("feedback-detail.php")
    Call<FeedbackListPojo> getFeedbackDetail(@Field("DbId") String DbId);

    /*send message*/
    @FormUrlEncoded
    @POST("FeedbackUserReply.php")
    Call<FeedbackListPojo> sendFeedbackMessage(@Field("data") String data);

    /*get SurveyData*/
    @FormUrlEncoded
    @POST("surveylisting.php")
    Call<SurveyPojo> getSurveryData(@Field("Id") String Id);

    /*submit SurveyData*/
    @FormUrlEncoded
    @POST("surveySubmit.php")
    Call<SurveyPojo> submitSurvey(@Field("userId") String userId, @Field("questionId") String questionId, @Field("answer") String answer);

    /*get policy*/
    @FormUrlEncoded
    @POST("PolicyPdf.php")
    Call<PrivacyPojo> getPolicy(@Field("Id") String Id);

    /*register device*/
    @FormUrlEncoded
    @POST("dashboard.php")
    Call<PrivacyPojo> registerDevice(@Field("Id") String Id, @Field("TokenNo") String TokenNo);
}
