package com.nick.finalyearproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends FragmentActivity {
	
	ViewPager viewpager;
	Button signin;
	Button signup;


	android.app.ActionBar bar;
	//
	int dotsCount=4;    //No of tabs or images
	ImageView[] dots;
	LinearLayout linearLayout;
//
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
     //   bar= getActionBar();
    //	bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#29A9D2")));

    	

        signup=(Button)findViewById(R.id.button1);
        signin=(Button)findViewById(R.id.button2);


        viewpager=(ViewPager)findViewById(R.id.pager);
     PagerAdapter padapter=new com.nick.finalyearproject.PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);
        
        drawPageSelectionIndicators(0);
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                drawPageSelectionIndicators(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        





        	signin.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent i=new Intent(MainActivity.this,LoginActivity.class);
					startActivity(i);
				}
			});
        	
        	signup.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					Intent i=new Intent(MainActivity.this,RegisterActivity.class);
					startActivity(i);
				}
			});
    }
    
    public void drawPageSelectionIndicators(int mPosition){
        if(linearLayout!=null) {
            linearLayout.removeAllViews();
        }
        linearLayout=(LinearLayout)findViewById(R.id.viewPagerCountDots);
        dots = new ImageView[dotsCount];
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getBaseContext());
            if(i==mPosition)
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.item_selected));
            else
                dots[i].setImageDrawable(getResources().getDrawable(R.drawable.item_unselected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(4, 0, 4, 0);
            linearLayout.addView(dots[i], params);
        }
    }
    

        

    
    
}