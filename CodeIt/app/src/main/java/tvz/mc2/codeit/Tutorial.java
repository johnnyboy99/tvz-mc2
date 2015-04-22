package tvz.mc2.codeit;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Tutorial extends Activity {

    @InjectView(R.id.krugZaElementeTutorial) View krugZaElementeTutorial;
    @InjectView(R.id.tekstObjasnjenjeTutorial) TextView tekstObjasnjenjeTutorial;
    @InjectView(R.id.okvirGumbTutorail) View okvirGumbTutorial;
    @InjectView(R.id.krugZaRadnuPlohuTutorial) View krugZaRadnuPlohu;
    @InjectView(R.id.izbornikElemenataLayoutTutorial) RelativeLayout izbornikElemanataLayoutTutorial;

    Handler handler = new Handler();
    private static int VRIJEME_ANIMACIJE = 2000;
    private static int CEKANJE_ZA_PRVU_ANIMACIJU = 1000;
    Animation fadeIn;
    Animation fadeOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.inject(this);

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbTutorial.getBackground();
        okvirGradientGumbaJedan.setStroke(visinaRuba, Color.BLACK);


        prviRender();
        animacije();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tutorial, menu);
        return true;
    }

    private void prviRender(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //cekaj prije rendera na sivu
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                fadeIn.setDuration(500);
                izbornikElemanataLayoutTutorial.setAnimation(fadeIn);
                okvirGumbTutorial.setAnimation(fadeIn);
                izbornikElemanataLayoutTutorial.setBackgroundColor(getResources().getColor(R.color.svjetloSiva));
                GradientDrawable bgRectangle = (GradientDrawable)okvirGumbTutorial.getBackground();
                bgRectangle.setStroke(2, Color.WHITE);
            }

        }, CEKANJE_ZA_PRVU_ANIMACIJU);
    }

    private void animacije(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                fadeIn.setDuration(VRIJEME_ANIMACIJE);
                krugZaElementeTutorial.setVisibility(View.VISIBLE);
                tekstObjasnjenjeTutorial.setVisibility(View.VISIBLE);
                tekstObjasnjenjeTutorial.setText("OVO JE GUMB!!!!!");
                tekstObjasnjenjeTutorial.setAnimation(fadeIn);
                krugZaElementeTutorial.startAnimation(fadeIn);
            }

        }, CEKANJE_ZA_PRVU_ANIMACIJU);



        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                drugaAnimacijaTutorijala();
            }
        });



        /*
        TranslateAnimation animation = new TranslateAnimation(
                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, -150.0f);
        animation.setDuration(2000);
        okvirGumbTutorial.startAnimation(animation);

        final TranslateAnimation animation1 = new TranslateAnimation(
                Animation.ABSOLUTE, 0.0f, Animation.ABSOLUTE, 0.0f,
                Animation.ABSOLUTE, 150.0f, Animation.ABSOLUTE, 0.0f);
        animation1.setDuration(2000);


        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getApplicationContext(), "tu sam", Toast.LENGTH_SHORT).show();
                okvirGumbTutorial.startAnimation(animation1);

            }
        });
        */
    }

    private void drugaAnimacijaTutorijala(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        fadeIn.setDuration(VRIJEME_ANIMACIJE);
        fadeOut.setDuration(VRIJEME_ANIMACIJE);


        tekstObjasnjenjeTutorial.startAnimation(fadeOut);
        krugZaElementeTutorial.startAnimation(fadeOut);
        krugZaElementeTutorial.setVisibility(View.GONE);

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tekstObjasnjenjeTutorial.setText("OVO JE RADNA PLOHA!!!!!");
                tekstObjasnjenjeTutorial.setVisibility(View.VISIBLE);
                krugZaRadnuPlohu.setVisibility(View.VISIBLE);
                GradientDrawable bgRectangle = (GradientDrawable)okvirGumbTutorial.getBackground();
                bgRectangle.setStroke(2, Color.WHITE);
                //okvirGumbTutorial.setAnimation(fadeIn);
                krugZaRadnuPlohu.setAnimation(fadeIn);
                tekstObjasnjenjeTutorial.setAnimation(fadeIn);
            }

        }, VRIJEME_ANIMACIJE);



        //fade out starih, fade in novih elemnata


        //okvirGumbTutorial.startAnimation(fadeIn);
    }

    private void prvaAnimacijaTutorijala(){
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "prviiiii", Toast.LENGTH_SHORT).show();
                tekstObjasnjenjeTutorial.setText("Prvi tekst");
            }

        }, VRIJEME_ANIMACIJE);

        //drugaAnimacijaTutorijala();
    }

    /*
    private void drugaAnimacijaTutorijala(){
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                tekstObjasnjenjeTutorial.setText("Drugi tekst");
            }

        }, VRIJEME_ANIMACIJE);
    }
    */

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
