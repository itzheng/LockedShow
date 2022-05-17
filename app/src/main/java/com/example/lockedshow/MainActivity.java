package com.example.lockedshow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lockedshow.utils.FloatPermissionUtils;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ViewPager vpViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vpViewPager = findViewById(R.id.vpViewPager);
        initData();
        initView();
        checkPermissions();

    }

    private void checkPermissions() {
        if (!(FloatPermissionUtils.checkFloatPermission(this))) {
            Toast.makeText(getApplicationContext(), "请给软件设置悬浮窗权限，否则无法正常使用！", Toast.LENGTH_SHORT).show();
            FloatPermissionUtils.gotoSetting(this);
        }
    }

    private void initView() {
        vpViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
//                return list.length;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                int newPos = position % list.length;
                ImageView view = new ImageView(container.getContext());
                ViewGroup.LayoutParams layoutParams =
                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT);
                view.setLayoutParams(layoutParams);
                String path = ASSET_DIR + "/" + list[newPos];
                showImageForAssets(view, path);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        vpViewPager.setCurrentItem(vpViewPager.getAdapter().getCount() / 2);
    }

    private static void showImageForAssets(ImageView imageView, String path) {
        Log.w(TAG, "showImageForAssets: " + path);
        InputStream input = null;
        try {
            input = imageView.getContext().getAssets().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        if (bitmapDrawable != null && !bitmapDrawable.getBitmap().isRecycled()) {
            bitmapDrawable.getBitmap().recycle();
        }

        imageView.setImageBitmap(BitmapFactory.decodeStream(input));
    }

    String[] list;
    private static final String ASSET_DIR = "my_images";

    private void initData() {
        try {
            list = getAssets().list(ASSET_DIR);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
    // except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

}