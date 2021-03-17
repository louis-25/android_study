package me.onulhalin.sample.onul_shop.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

import me.onulhalin.sample.R;
import me.onulhalin.sample.onul_shop.entity.Article;
import me.onulhalin.sample.onul_shop.listener.OnItemClickListener;
import me.onulhalin.sample.onul_shop.model.Product;
import me.onulhalin.sample.onul_shop.tool.poto;

import static me.onulhalin.sample.onul_shop.data.Constant.WEB_URL;

/**
 *
 */
public class FirstHomeAdapter extends RecyclerView.Adapter<FirstHomeAdapter.VH> {
    private List<Article> mItems = new ArrayList<>();
    private LayoutInflater mInflater;

    public List<Product> products = new ArrayList<>();
    private OnItemClickListener mClickListener;

    Bitmap bitmap;
    URL url;
    Bitmap image;

    public FirstHomeAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_zhihu_home_first, parent, false);
        final VH holder = new VH(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, holder);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Article item = mItems.get(position);

        ViewCompat.setTransitionName(holder.img, String.valueOf(position) + "_image");
        ViewCompat.setTransitionName(holder.tvTitle, String.valueOf(position) + "_tv");

        // holder.img.setImageResource(item.getImgRes());
        holder.tvTitle.setText(item.getTitle());

        try {
            url = new URL(WEB_URL + "uploads/product/"+item.getImgRes());
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

                    image = Bitmap.createScaledBitmap(bitmap, 500, 500, true);


                    // 이미지를 상황에 맞게 회전시킨다
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
            holder.img.setImageBitmap(image);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // img.setImageURI(Uri.parse("http://192.168.0.8/admin_project/uploads/product/Derek Heart Juniors.jpg"));




    }

    public void setDatas(List<Article> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public Article getItem(int position) {
        return mItems.get(position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public class VH extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public ImageView img;

        public VH(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
