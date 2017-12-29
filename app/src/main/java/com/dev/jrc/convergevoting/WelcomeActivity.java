package com.dev.jrc.convergevoting;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dev.jrc.convergevoting.Adapters.SlidePagerAdapter;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.welcome_slide_1,R.layout.welcome_slide_2,R.layout.welcome_slide_3,R.layout.welcome_slide_4};
    private SlidePagerAdapter slidePagerAdapter;

    private LinearLayout dotsLayout;
    private ImageView[] dots;
    private Button btnSkip,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(SharedPrefManager.getInstance(getApplicationContext()).isSlideDone()){
            loadWelcome();
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_welcome);
        mPager = (ViewPager) findViewById(R.id.welcomeViewPager);

        slidePagerAdapter = new SlidePagerAdapter(layouts,this);
        mPager.setAdapter(slidePagerAdapter);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnSkip = (Button) findViewById(R.id.btnSkip);

        dotsLayout = (LinearLayout) findViewById(R.id.dotsLayout);
        createDots(0);

        btnSkip.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
                if(position==layouts.length-1){
                    btnNext.setText("Start");
                    btnSkip.setVisibility(View.INVISIBLE);
                }else{
                    btnNext.setText("Next");
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void createDots (int current_position){
        if(dotsLayout != null){
            dotsLayout.removeAllViews();
            dots = new ImageView[layouts.length];
            for (int i=0;i<layouts.length;i++){
                dots[i] = new ImageView(this);
                if(i==current_position){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
                }else{
                    dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.default_dots));
                }
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.setMargins(4,0,4,0);
                dotsLayout.addView(dots[i],params);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if(view == btnNext){
            loadNextSlide();
        }else{
            loadWelcome();
            SharedPrefManager.getInstance(getApplicationContext()).userSlideDone("True");
        }
    }

    private void loadWelcome (){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void loadNextSlide(){
        int next_slide = mPager.getCurrentItem()+1;

        if(next_slide<layouts.length){
            mPager.setCurrentItem(next_slide);
        }else{
            loadWelcome();
            SharedPrefManager.getInstance(getApplicationContext()).userSlideDone("True");
        }
    }
}
