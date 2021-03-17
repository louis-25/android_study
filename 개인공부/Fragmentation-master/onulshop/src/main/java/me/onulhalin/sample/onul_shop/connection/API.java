package me.onulhalin.sample.onul_shop.connection;


import android.database.Observable;



import java.util.List;

import me.onulhalin.sample.onul_shop.connection.callback.CallbackCategory;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackCerification;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackDevice;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackJoin;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrder;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackOrderCancel;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackProduct;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackProductDetails;
import me.onulhalin.sample.onul_shop.data.Constant;
import me.onulhalin.sample.onul_shop.model.Category;
import me.onulhalin.sample.onul_shop.model.Order;
import me.onulhalin.sample.onul_shop.model.Product;
import me.onulhalin.sample.onul_shop.model.ProductUpdate;
import me.onulhalin.sample.onul_shop.model.login;
import me.onulhalin.sample.onul_shop.model.pushKey;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {

    String CACHE = "Cache-Control: max-age=0";
    String AGENT = "User-Agent: Markeet";
    String SECURITY = "Security: " + Constant.SECURITY_CODE;

    /* Recipe API transaction ------------------------------- */



    //    @Headers({CACHE, AGENT})
//    @GET("services/info")
//    Call<CallbackInfo> getInfo(
//            @Query("version") int version
//    );
//
//    /* Fcm API ----------------------------------------------------------- */
////    @Headers({CACHE, AGENT, SECURITY})
////    @POST("services/insertOneFcm")
////    Call<CallbackDevice> registerDevice(
////            @Body DeviceInfo deviceInfo
////    );
//
//    /* News Info API ---------------------------------------------------- */
//
//    @Headers({CACHE, AGENT})
//    @GET("services/listFeaturedNews")
//    Call<CallbackFeaturedNews> getFeaturedNews();
//
//    @Headers({CACHE, AGENT})
//    @GET("services/listNews1")
//    Call<CallbackNewsInfo> getListNewsInfo(
//            @Query("page") int page,
//            @Query("count") int count,
//            @Query("q") String query
//    );
//
//    @Headers({CACHE, AGENT})
//    @GET("services/getNewsDetails")
//    Call<CallbackNewsInfoDetails> getNewsDetails(
//            @Query("id") long id
//    );
//
//    /* Category API ---------------------------------------------------  */
//    @Headers({CACHE, AGENT})
//    @GET("services/listCategory")
//    Call<CallbackCategory> getListCategory();
//
//

    @Headers({CACHE, AGENT})
    @GET("services/selectUserIdAndSTATUS")
    Call<CallbackOrder> getAllProductOrder(
            @Query("status") String status,
            @Query("userid") String userid
    );


    @Headers({CACHE, AGENT})
    @GET("services/findoneOrder")
    Call<CallbackOrder> getOneProductOrder(
            @Query("id") String id
    );



    @Headers({CACHE, AGENT})
    @GET("certification.php")
    Call<CallbackCerification> getCerification(
            @Query("phone") String phone
    );


//    /* Product API ---------------------------------------------------- */
//
    @Headers({CACHE, AGENT})
    @GET("services/listProduct")
    Call<CallbackProduct> getListProduct(
            @Query("page") int page,
            @Query("count") int count,
            @Query("q") String query,
            @Query("category_id") long category_id
    );

//    @Headers({CACHE, AGENT})
//    @GET("services/getUserProduct")
//    Call<CallbackProduct> getAllListProduct(
//            @Query("id") String id
//    );
@Headers({CACHE, AGENT})
@GET("services/getUserProduct")
Call<CallbackProduct> getAllListProduct(
        @Query("id") String id
);


    @Headers({CACHE, AGENT})
    @GET("services/getProductDetails")
    Call<CallbackProductDetails> getProductDetails(
            @Query("id") long id
    );

    @Headers({CACHE, AGENT})
    @GET("services/deleteOneProduct")
    Call<CallbackProduct> ProductDel(
            @Query("id") Long id
    );


    /* Category API ---------------------------------------------------  */
    @Headers({CACHE, AGENT})
    @GET("services/listCategory")
    Call<CallbackCategory> getListCategory();


    @Headers({CACHE, AGENT})
    @GET("services/test123")
    Call<CallbackProduct> setUpdateStock(
            @Query("user_id") int id,
              @Query("stock") int stock

    );

    @Headers({CACHE, AGENT})
    @POST("services/insertAllProductCategory")
//    Call<List<CallbackCategory>> Category(
//            @Query("product_id") String id,
//            @Query("category_id") String cid
//    );
    Call<CallbackCategory> Category(
            @Body List<Category> checkout
    );

//
//    @Headers({CACHE, AGENT})
//    @GET("services/getProductDetails")
//    Call<CallbackProductDetails> getProductDetails(
//            @Query("id") long id
//    );
//
//    /* Checkout API ---------------------------------------------------- */
////    @Headers({CACHE, AGENT, SECURITY})
////    @POST("services/submitProductOrder")
////    Call<CallbackOrder> submitProductOrder(
////            @Body Checkout checkout
////    );

//    /* Checkout API ---------------------------------------------------- */
//    @Headers({CACHE, AGENT, SECURITY})
//    @POST("services/submitProductOrder")
//    Call<CallbackOrder> submitProductOrder(
//            @Body Checkout checkout
//    );

    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/shopuserlogin")
    Call<CallbackDevice> registerDevice(
                    @Body login login
            );

    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/shopuserpushkey")
    Call<CallbackDevice> pushkey(
            @Body pushKey login
    );

    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/getUidAndCancelreason")
    Call<CallbackOrderCancel> cancelOrder(
            @Body Order order
            );



    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/fcmsend")
    Call<CallbackDevice> fdmsend(
            @Body login login
    );

    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/insertOneProduct")
    Call<CallbackProduct> register(
            @Body Product Product
    );


    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/updateOneProduct")
    Call<CallbackProduct> updateProduct(
            @Body ProductUpdate Product
    );




    @Headers({CACHE, AGENT, SECURITY})
    @POST("services/shopuserjoin")
    Call<CallbackJoin> registerJoin(
            @Body login login
    );

    @Multipart
    @POST("services/join")
    Observable<Response<String>> uploadPhoto(@Header("Access-Token") String header, @Part MultipartBody.Part imageFile);


}
