package com.example.saurav.bkuse;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MyActivity extends ActionBarActivity {
    CustomScrollView customScrollView;
    @InjectView(R.id.viewPagerCheck)
    ViewPager viewPagerCheck;
    @InjectView(R.id.scrollViewHeader)
    RelativeLayout scrollViewHeader;
    @InjectView(R.id.footerView)
    LinearLayout footerView;
    @InjectView(R.id.image_progress_bar)
    ProgressBar imageProgressBar;
    MyPagerAdapter myPagerAdapter;
    private DisplayImageOptions displayImageOptions;
    String imageUri="";
    ImageLoadingListener imageLoadingListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        String actionBarTitle = "";
        Drawable actionBarDrawable = getResources().getDrawable(R.drawable.action_bar_bg);
        getSupportActionBar().setBackgroundDrawable(actionBarDrawable);
        ButterKnife.inject(this);
        customScrollView = (CustomScrollView) findViewById(R.id.scroll_view_content);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(options)
                .build();

        ImageLoader.getInstance().init(config);

        customScrollView.setmActionBar(getSupportActionBar());
        customScrollView.setmBackgroundDrawable(actionBarDrawable);
        customScrollView.setmRelativeLayout(scrollViewHeader);
        customScrollView.setmLinearLayout(footerView);

        myPagerAdapter = new MyPagerAdapter();
        viewPagerCheck.setAdapter(myPagerAdapter);
    }

    private class MyPagerAdapter extends PagerAdapter {

        int NumberOfPages = 5;

        int[] res = {
                android.R.drawable.ic_dialog_alert,
                android.R.drawable.ic_menu_camera,
                android.R.drawable.ic_menu_compass,
                android.R.drawable.ic_menu_directions,
                android.R.drawable.ic_menu_gallery
        };
        int[] backgroundcolor = {
                0xFF101010,
                0xFF202020,
                0xFF303030,
                0xFF404040,
                0xFF505050};

        @Override
        public int getCount() {
            return NumberOfPages;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {




            final ImageView imageView = new ImageView(MyActivity.this);
            ImageLoader imageLoader = ImageLoader.getInstance();
            switch (position){

                case 0:
                    imageUri = "http://emilines.com/wp-content/uploads/2014/09/cars-background-wallpaper-hd1.jpg";
                    imageLoader.displayImage(imageUri,imageView,imageLoadingListener);
                    break;
                case 1:
                    imageUri = "http://cdn.wonderfulengineering.com/wp-content/uploads/2014/07/cars-wallpaper-wallwuzz-hd-wallpaper-242.jpg";
                    imageLoader.displayImage(imageUri,imageView,imageLoadingListener);
                    break;
                case 2:
                    imageUri = "http://images.bwbx.io/cms/2014-05-21/0520_dream_cars_970-630x420.jpg";
                    imageLoader.displayImage(imageUri,imageView,imageLoadingListener);
                    break;
                case 3:
                    imageUri = "http://wallpaperpassion.com/upload/9972/neon-cars-wallpaper.jpg";
                    imageLoader.displayImage(imageUri,imageView,imageLoadingListener);
                    break;
                case 4:
                    imageUri = "http://emilines.com/wp-content/uploads/2014/09/cars-background-wallpaper-hd1.jpg";
                    imageLoader.displayImage(imageUri,imageView,imageLoadingListener);
                    break;

            }
            imageLoadingListener =  new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    imageProgressBar.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    imageProgressBar.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    imageProgressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    imageProgressBar.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                }
            };

            ViewGroup.LayoutParams imageParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(imageParams);

            LinearLayout layout = new LinearLayout(MyActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            layout.setBackgroundColor(backgroundcolor[position]);
            layout.setLayoutParams(layoutParams);
            layout.addView(imageView);

            final int page = position;
            layout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(MyActivity.this,
                            "Page " + page + " clicked",
                            Toast.LENGTH_LONG).show();
                }
            });

            container.addView(layout);
            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
}
