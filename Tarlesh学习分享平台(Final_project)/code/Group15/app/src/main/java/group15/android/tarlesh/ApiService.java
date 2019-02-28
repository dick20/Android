package group15.android.tarlesh;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface ApiService {
    @POST("/api/file/fileList")
    Observable<ResponseBody> getFileList(@Header("x-access-token") String token, @Body RequestBody requestBody);

    @POST("/api/qa/getQuestionList")
    Observable<QuizResponseBody> getQuizList(@Header("x-access-token") String token);

    @POST("/api/qa/createQuestion")
    Observable<QuizPostResponseBody> postQuiz(@Header("x-access-token") String token, @Body RequestBody requestBody);

    @POST("/api/file/download")
    Observable<FileDownloadResponse> downloadFile(@Header("x-access-token") String token, @Body RequestBody requestBody);

    @Multipart
    @POST("/api/file/upload")
    Observable<UploadResponseBody> uploadFile(@Header("x-access-token") String token,
                                              @Part("description") RequestBody description,
                                              @Part MultipartBody.Part file);
    @POST("api/file/comment")
    Observable<CommentResponseBody> postComment(@Header("x-access-token") String token, @Body RequestBody requestBody);
}