package tk.gjveron.seriousapps.wearonemini;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private ImageView mMainImageView;
    //private TextView mClockView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
    }

    public String getUrl() {
        String imageId = this.getImageId();
        String Out = "http://i.imgur.com/" + imageId + "s.png";
        return Out;
    }

    public String getImageId() {
        int len = 5;
        String out = "";
        StringBuilder text = new StringBuilder();
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        for (int i = 0; i < len; i++) {
            int nextpossiblecharposition = (int) Math.floor(Math.random() * possible.length());
            String imgurchar = Character.toString(possible.charAt(nextpossiblecharposition));
            if (text.indexOf(imgurchar) == -1) {
                text.append(imgurchar);
            } else {
                i--;
            }
        }
        out = text.toString();
        /*if (imgurcache.indexOf(text) == -1) {
            imgurcache.push(text);
            return text;
        } else {
            self.random(5);
            return false;
        }
        */
        return out;
    }


    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        ImageView MainImageView = (ImageView) findViewById(R.id.MainImageView);
        String url = this.getUrl();
        Context context = this.getApplicationContext();
        Picasso s = Picasso.with(context);
        RequestCreator r = s.load(url);
        r.into(MainImageView, new Callback(){

            @Override
            public void onSuccess() {
                Log.w("WearOneMini", "picasso.onSuccess");
            }

            @Override
            public void onError() {
                Log.w("WearOneMini", "picasso.onFailure");
            }
        });
        Log.w("WearOneMini","updateDisplay done");
        /*if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            //mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            //mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }*/
    }
}
