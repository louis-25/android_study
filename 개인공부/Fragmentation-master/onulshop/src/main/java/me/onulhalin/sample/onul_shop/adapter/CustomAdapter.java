package me.onulhalin.sample.onul_shop.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.data.Dictionary;
import me.onulhalin.sample.onul_shop.listener.OnItemClickListener;
import me.onulhalin.sample.onul_shop.tool.poto;

import static me.onulhalin.sample.onul_shop.data.Constant.WEB_URL;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    Bitmap bitmap;
    URL url;
    Bitmap image;
    private Activity Context;
    private ArrayList<Dictionary> mList;
    private OnItemClickListener mClickListener;



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView english;
        protected TextView korean;
        protected ImageView proimg;

        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textview_recyclerview_id);
            this.english = (TextView) view.findViewById(R.id.textview_recyclerview_english);
            this.korean = (TextView) view.findViewById(R.id.textview_recyclerview_korean);
            this.proimg = (ImageView)view.findViewById(R.id.proimg);
        }
    }


    public CustomAdapter(ArrayList<Dictionary> list ,Activity context) {
        this.mList = list;
        Context= context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        final CustomViewHolder viewHolder = new CustomViewHolder(view);


        viewHolder.korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, viewHolder);
                }
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {

        viewholder.id.setGravity(Gravity.CENTER);
        viewholder.english.setGravity(Gravity.CENTER);
        viewholder.korean.setGravity(Gravity.CENTER);

        //string res 따로 빼야함
        if (mList.get(position).getKorean().equals("0")){
             viewholder.id.setText("메뉴 준비 중");
        }
        else {
            viewholder.id.setText("판매 중");
        }

        viewholder.english.setText(mList.get(position).getEnglish());
        viewholder.korean.setText(mList.get(position).getKorean());

        // util로 만들어서 한꺼번에 사용할수 있게..
        try {
            url = new URL(WEB_URL + "uploads/product/"+mList.get(position).getImgname());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {

                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                    image = Bitmap.createScaledBitmap(bitmap, 500, 500, true);

                    ExifInterface exif = new ExifInterface(String.valueOf(url));
                    int exifOrientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int exifDegree = poto.exifOrientationToDegrees(exifOrientation);
                    image = poto.rotate(image, exifDegree);
//                                img.setImageBitmap(image);


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
            //mImgDetail.setImageBitmap(image);
            viewholder.proimg.setImageBitmap(image);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
     //   Log.d("kimtest","@@~~~"+resp.product.name);

    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }


    public Dictionary getItem(int position) {
        return mList.get(position);
    }

    public void setText(int position , int stock){
         mList.get(position).setKorean(String.valueOf(stock));
         notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
