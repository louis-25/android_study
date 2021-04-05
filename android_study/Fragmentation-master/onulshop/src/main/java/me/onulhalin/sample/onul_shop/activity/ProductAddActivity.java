package me.onulhalin.sample.onul_shop.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.connection.API;
import me.onulhalin.sample.onul_shop.connection.RestAdapter;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackCategory;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackProduct;
import me.onulhalin.sample.onul_shop.connection.callback.CallbackProductDetails;
import me.onulhalin.sample.onul_shop.model.Category;
import me.onulhalin.sample.onul_shop.model.Checkout;
import me.onulhalin.sample.onul_shop.model.Product;
import me.onulhalin.sample.onul_shop.model.ProductUpdate;
import me.onulhalin.sample.onul_shop.tool.poto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static me.onulhalin.sample.onul_shop.data.Constant.WEB_URL;

public class ProductAddActivity extends OHActivitiy {
    private static int PICK_IMAGE_REQUEST = 1;
    ImageView imgView;
    Button button;
    static final String TAG = "MainActivity";
    private Call<CallbackCategory> callbackCall;
    private Call<CallbackProductDetails> callbackCall1;
    Button uploadButton,updatebtn;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;
    String potopath;
    String potoname;

    EditText name;
    EditText description;
    EditText price_discount;
    EditText price;
    EditText stock;

    String category ;
    SharedPreferences setting;
     URL url;
    Bitmap bitmap;
    Long id;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actibity_productadd);
        button = (Button)findViewById(R.id.buttonLoadPic);
        imgView = (ImageView) findViewById(R.id.imageView);
        uploadButton = (Button)findViewById(R.id.regbtn);
        updatebtn = (Button)findViewById(R.id.updatebtn);
        setting = getSharedPreferences("setting", 0);
        init();
        upLoadServerUri = WEB_URL+"UploadToServer.php";// ip주소
        uploadButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {
                dialog = ProgressDialog.show(ProductAddActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {

                    public void run() {
                        runOnUiThread(new Runnable() {

                            public void run() {
//                                messageText.setText("uploading started.....");
                            }

                        });
                        uploadFile(potopath);
                    }

                }).start();
            }

        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = ProgressDialog.show(ProductAddActivity.this, "", "Uploading file...", true);
                new Thread(new Runnable() {

                    public void run() {
                        runOnUiThread(new Runnable() {

                            public void run() {
//                                messageText.setText("uploading started.....");
                            }

                        });
                        uploadFile(potopath);
                    }

                }).start();
            }
        });

        requestListCategory();
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImagefromGallery(v);
            }
        });

        intent = getIntent();
        if (intent.getStringExtra("name1").equals("true")){
            updatebtn.setVisibility(View.VISIBLE);
            uploadButton.setVisibility(View.GONE);
            requestNewsInfoDetailsApi(intent.getLongExtra("name",0));

             id = intent.getLongExtra("name",0);

        }
        else {
            updatebtn.setVisibility(View.GONE);
            uploadButton.setVisibility(View.VISIBLE);
            //name.setText("");
        }
    }

    public void init(){

        name =(EditText)findViewById(R.id.product_name);
        description = (EditText)findViewById(R.id.description) ;
        price = (EditText)findViewById(R.id.price) ;
        price_discount = (EditText)findViewById(R.id.price_discount) ;
        stock = (EditText)findViewById(R.id.stock) ;
        setTitle("서비스 등록");

    }


    private void requestNewsInfoDetailsApi(Long id) {
        API api = RestAdapter.createAPI();
        callbackCall1 = api.getProductDetails(id);
        callbackCall1.enqueue(new Callback<CallbackProductDetails>() {
            @Override
            public void onResponse(Call<CallbackProductDetails> call, Response<CallbackProductDetails> response) {
                CallbackProductDetails resp = response.body();
                if (resp != null && resp.status.equals("success")) {

                    name.setText(resp.product.name);
                    price.setText(String.valueOf(resp.product.price));
                    stock.setText(String.valueOf(resp.product.stock));

//                    html_data = "<style>img{max-width:100%;height:auto;} iframe{width:100%;}</style> ";
//                    html_data += resp.product.description;
//                    content.getSettings().setJavaScriptEnabled(true);
//                    content.getSettings().setBuiltInZoomControls(true);
//                    content.setBackgroundColor(Color.TRANSPARENT);
//                    content.setWebChromeClient(new WebChromeClient());
//                    content.loadData(html_data, "text/html; charset=UTF-8", null);
//                    // disable scroll on touch
//                    content.setOnTouchListener(new View.OnTouchListener() {
//                        public boolean onTouch(View v, MotionEvent event) {
//                            return (event.getAction() == MotionEvent.ACTION_MOVE);
//                        }
//                    });


                    try {
                        url = new URL(WEB_URL + "uploads/product/"+resp.product.image);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    Thread mThread = new Thread() {
                        @Override
                        public void run() {
                            try {

                                Log.d("kimtest","@@dwdwdwd@"+url);
                                // Web에서 이미지를 가져온 뒤
                                // ImageView에 지정할 Bitmap을 만든다
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setDoInput(true); // 서버로 부터 응답 수신
                                conn.connect();

                                InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                                bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    mThread.start(); // Thread 실행

                    try {
                        // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
                        // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
                        mThread.join();

                        // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
                        // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
                       imgView.setImageBitmap(bitmap);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d("kimtest","@@~~~"+resp.product.name);

                } else {

                }
            }

            @Override
            public void onFailure(Call<CallbackProductDetails> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
//                if (!call.isCanceled()) onFailRequest();
            }
        });
    }




    public int uploadFile(String sourceFileUri) {
        final String fileName = sourceFileUri;
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 500 * 500;
        //ㅇ


        File sourceFile = new File(sourceFileUri);




        if (!sourceFile.isFile()) {
            dialog.dismiss();
          //  Log.e("uploadFile", "Source File not exist :" + uploadFilePath + "" + uploadFileName);
            runOnUiThread(new Runnable() {

                public void run() {
              //      messageText.setText("Source File not exist :" + uploadFilePath + "" + uploadFileName);
                }

            });
            return 0;
        } else {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);
                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\"" + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.max(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                    runOnUiThread(new Runnable() {

                        public void run() {
                            String aaa = fileName.substring(fileName.lastIndexOf("/"));
                            potoname = aaa.substring(1);

                            if (intent.getStringExtra("name1").equals("true")) {
                                updateProductData();
                            }
                            else {
                                insertProductData();
                            }
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();
            } catch (MalformedURLException ex) {
                dialog.dismiss();
                ex.printStackTrace();
                runOnUiThread(new Runnable() {

                    public void run() {
                      //  messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(ProductAddActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }

                });
                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                dialog.dismiss();
                e.printStackTrace();
                runOnUiThread(new Runnable() {

                    public void run() {
                      //  messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(ProductAddActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }

                });
               // Log.e("Upload file to server Exception", "Exception : " + e.getMessage(), e);
            }
            dialog.dismiss();
            return serverResponseCode;
        } // End else block
    }


    public void loadImagefromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //이미지 선택작업을 후의 결과 처리
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        try {
            //이미지를 하나 골랐을때
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && null != data) {
                //data에서 절대경로로 이미지를 가져옴
                Uri uri = data.getData();
               Log.d("kimtest","TTT"+getPath(uri));

//
                potopath= getPath(uri);

                     try {
                            // 비트맵 이미지로 가져온다
                         String imagePath =data.getData().getPath();
                         Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                           // Bitmap image = BitmapFactory.decodeFile(imagePath);
                         Bitmap image = Bitmap.createScaledBitmap(bitmap, 500, 500, true);
                           // 이미지를 상황에 맞게 회전시킨다
                            ExifInterface exif = new ExifInterface(getPath(uri));
                           int exifOrientation = exif.getAttributeInt(
                                  ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                            int exifDegree = poto.exifOrientationToDegrees(exifOrientation);
                         image = poto.rotate(image, exifDegree);
                         imgView.setImageBitmap(image);
                           // 변환된 이미지 사용
                        // imgView.setImageBitmap(image);
                         } catch(Exception e) {

                            Toast.makeText(this, "오류발생: " + e.getLocalizedMessage(),
                                  Toast.LENGTH_LONG).show();
                         }
            } else {
                Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Oops! 로딩에 오류가 있습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    public String getPath(Uri uri) {

        if( uri == null ) {
            return null;
        }
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }





// 상품등록 API
    private void insertProductData() {

        Product user = new Product();
        user.name = name.getText().toString();
//        user.price = adminpw.getText().toString();
        user.image=potoname;
        user.description= description.getText().toString();
        user.price_discount= Integer.valueOf(price_discount.getText().toString());
        user.price = Integer.valueOf(price.getText().toString());
      //  user.category = Integer.parseInt(category);
        user.stock = Integer.valueOf(stock.getText().toString());
        user.draft = 0;
        user.status = "READY STOCK";
        user.userid= setting.getString("ID", "");


        API api = RestAdapter.createAPI();
        Call<CallbackProduct> callbackCall = api.register(user);

        callbackCall.enqueue(new Callback<CallbackProduct>() {
            @Override
            public void onResponse(Call<CallbackProduct> call, Response<CallbackProduct> response) {
                CallbackProduct resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    submitOrderData1(String.valueOf(resp.data.id));
                }
                else if (resp.status.equals("failed")){

                }
            }

            @Override
            public void onFailure(Call<CallbackProduct> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }


    // 상품업데이트 API
    private void updateProductData() {

        ProductUpdate user1 = new ProductUpdate();

        user1.id= id;


        Product user = new Product();
       ;

        user1.product.name = name.getText().toString();
//        user.price = adminpw.getText().toString();
        user1.product.image=potoname;
        user1.product.description= description.getText().toString();
        user1.product.price_discount= Integer.valueOf(price_discount.getText().toString());
        user1.product.price = Integer.valueOf(price.getText().toString());
      //  user1.product.category = Integer.parseInt(category);
        user1.product.stock = Integer.valueOf(stock.getText().toString());
        user1.product.draft = 0;
        user1.product.status = "READY STOCK";

       // user1.product.userid= "aaaa";


        API api = RestAdapter.createAPI();
        Call<CallbackProduct> callbackCall = api.updateProduct(user1);

        callbackCall.enqueue(new Callback<CallbackProduct>() {
            @Override
            public void onResponse(Call<CallbackProduct> call, Response<CallbackProduct> response) {
                CallbackProduct resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    submitOrderData1(String.valueOf(resp.data.id));
                }
                else if (resp.status.equals("failed")){

                }
            }

            @Override
            public void onFailure(Call<CallbackProduct> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }



    private void submitOrderData1(String id) {

        final Map<String, String> data = new HashMap<>();

        Checkout a = new Checkout();
        List<Category> product_category1 = new ArrayList<>();


        a.product_category1 = new ArrayList<>();
        Category pod = new Category(id,category);
        product_category1.add(pod);

        API api = RestAdapter.createAPI();
        Call<CallbackCategory> callbackCall = api.Category(product_category1);

        callbackCall.enqueue(new Callback<CallbackCategory>() {
            @Override
            public void onResponse(Call<CallbackCategory> call, Response<CallbackCategory> response) {
                CallbackCategory resp = response.body();
                //수정
                Toast.makeText(ProductAddActivity.this, "등록완료", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<CallbackCategory> call, Throwable t) {
                Toast.makeText(ProductAddActivity.this, "ㄴㄴㄴㄴ", Toast.LENGTH_SHORT).show();

                Iterator<String> mapIter = data.keySet().iterator();

                while(mapIter.hasNext()){

                    String key = mapIter.next();
                    String value = data.get( key );

                    Log.e("dd", key+" : "+value);


                }

                Log.e("onFailure", t.getMessage());
            }
        });
    }


    //카테고리 리스트 가져오기
    private void requestListCategory() {
        API api = RestAdapter.createAPI();
        callbackCall = api.getListCategory();
        callbackCall.enqueue(new Callback<CallbackCategory>() {
            @Override
            public void onResponse(Call<CallbackCategory> call, Response<CallbackCategory> response) {
                CallbackCategory resp = response.body();
                if (resp != null && resp.status.equals("success")) {
                    category = String.valueOf(resp.categories.get(0).id);
                   // Toast.makeText(ProductAddActivity.this, ""+resp.categories.get(0).id, Toast.LENGTH_SHORT).show();
//                    recyclerView.setVisibility(View.VISIBLE);
//                    adapter.setItems(resp.categories);
//                    ActivityMain.getInstance().category_load = true;
//                    ActivityMain.getInstance().showDataLoaded();
                } else {
//                    onFailRequest();
                }
            }

            @Override
            public void onFailure(Call<CallbackCategory> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
              //  if (!call.isCanceled()) onFailRequest();
            }

        });
    }


}