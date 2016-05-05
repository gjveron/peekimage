package tk.seriousapps.peekimage;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

/**
 * Created by taquigrafiau on 23/04/2016.
 */
public class ImgurGridViewAdapter extends BaseAdapter {

    Context context;
    //private List<String> urls = new ArrayList<String>();
    private ArrayList<String> urls = new ArrayList<>();
    Picasso picasso = null;
    public ImgurGridViewAdapter(Context context) {
        this.context = context;

        OkHttpClient client = new OkHttpClient.Builder()
                .followRedirects(false)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request request = chain.request();
                        Response response = chain.proceed(request);
                        Response out = response;
                        boolean isRedirect = response.isRedirect();
                        if (isRedirect){
                            out = response.newBuilder()
                                .code(404)
                                .build();
                        }
                        return out;
                    }
                })
                .build();

        final ImgurGridViewAdapter This = this;
        picasso = new Picasso.Builder(context)
            .downloader(new OkHttp3Downloader(client))
            .listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    String url = uri.toString();
                    Log.d("ImgurGridViewAdapter", "Failed to load url: " + url);
                    int urlIndex = urls.indexOf(url);
                    if (urlIndex == 0)
                    {
                        Log.e("ImgurGridViewAdapter", "Cannot find url in array: " + url);
                    }
                    urls.remove(url);
                    This.notifyDataSetChanged();
                }
            })
            .build();

        //Picasso.setSingletonInstance(picasso);

        this.addNewPage();
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public String getItem(int position) {
        return urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if(view == null) {
            view = new SquaredImageView(context) ;
            view.setScaleType(CENTER_CROP);
        }

        String url = getItem(position);

        picasso.load(url) //
                //.placeholder(R.drawable.placeholder) //
                //.error(R.drawable.error) //
                .fit() //
                .tag(context) //
                .into(view);

        return view;
    }

    public void addNewPage(){
        for(int i = 0; i < 4; i++) {
            String url = ImgurRequest.getUrl();
            urls.add(url);
        }
    }
}
