package com.muthootfinance.retreivesdk;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @Multipart
    @POST("uploaddoc")
    Call<AppsModel> uploadFile(
            @Part MultipartBody.Part file,
            @Part("description") RequestBody description
    );
}
