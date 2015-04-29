package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Zvjezdice extends Activity {

    @InjectView(R.id.textView2) TextView textView2;
    @InjectView(R.id.textView) TextView textView;
    @InjectView(R.id.zvijezda1) ImageView zvijezda1;
    @InjectView(R.id.zvijezda2) ImageView zvijezda2;
    @InjectView(R.id.zvijezda3) ImageView zvijezda3;
    /**
     * Tip poruke koji označava da je timeout istekao.
     */
    private static final int STOPSPLASH = 0;
    /**
     * Milisekunde do micanja ekrana.
     */
    private static final long SPLASHTIME = 2000;
    /**
     * Osigurava da se glavni prozor stvori samo jednom.
     */
    private boolean flag = false;
    /**
     * Poruka korisniku o tome sto je naucio.
     */
    private String poruka = "";
    /**
     * Preusmjeravanje na sljedecu razinu.
     */
    private boolean sljedeciEkran = false;
    /**
     * Handler za timeout ovog ekrana.
     */
    private Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    mjenjanjeEkrana();
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zvjezdice);
        ButterKnife.inject(this);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
        zvijezda1.setImageResource(R.drawable.star);
        zvijezda2.setImageResource(R.drawable.star);
        zvijezda3.setImageResource(R.drawable.star);
        poruka = getIntent().getExtras().getString("poruka");
    }

    /**
     * Poziva glavni izbornik u ovisnosti o zastavici.
     * Poziva se kada istekne timeout ili kada korisnik
     * dodirne ekran.
     */
    private void mjenjanjeEkrana(){
        if (flag == false) {
            textView2.setText("Naučio si "+ poruka);
            textView.setVisibility(View.VISIBLE);
            zvijezda1.setVisibility(View.VISIBLE);
            zvijezda2.setVisibility(View.VISIBLE);
            zvijezda3.setVisibility(View.VISIBLE);
            flag = true;
            sljedeciEkran = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (sljedeciEkran == false) {
                mjenjanjeEkrana();
            } else {
                Zvjezdice.this.finish();
            }
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        flag = true;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_zvjezdice, menu);
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
