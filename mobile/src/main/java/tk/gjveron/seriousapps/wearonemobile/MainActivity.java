package tk.gjveron.seriousapps.wearonemobile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {//AppCompatActivity {
    //http://randomimages.info/
    @BindView(R.id.mainGridView) GridView mainGridView;
    @BindView(R.id.fab) Button fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mainGridView.setAdapter(new ImgurGridViewAdapter(this));
        mainGridView.setOnScrollListener(new EndlessScrollListener(){
            @Override
            public boolean onLoadMore(int page, int totalItemsCount, AbsListView view) {
                ImgurGridViewAdapter adapter = (ImgurGridViewAdapter) view.getAdapter();
                adapter.addNewPage();
                Log.d("MainActivity", "onLoadMore");
                return true;
            }
        });
    }

    private void updateImage() {
        mainGridView.invalidateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    protected void fabOnClick(View view){
        Log.d("MainActivity", "fabOnClick");
        Snackbar.make(view, "Refresh", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        updateImage();
    }
}