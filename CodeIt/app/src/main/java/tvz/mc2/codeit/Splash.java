package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

/**
 * Početni ekran aplikacije koji prikazuje logo te
 * nakon 2 sekunde se automatski miče
 *
 * @author Kristina Marinić
 * @date 2015-04-05
 */
public class Splash extends Activity {

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
     * Handler za timeout ovog ekrana.
     */
    private Handler splashHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOPSPLASH:
                    poziv();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Message msg = new Message();
        msg.what = STOPSPLASH;
        splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }

    /**
     * Poziva glavni izbornik u ovisnosti o zastavici.
     * Poziva se kada istekne timeout ili kada korisnik
     * dodirne ekran.
     */
    private void poziv(){
        if (flag == false) {
            Intent intent = new Intent(Splash.this, GlavniIzbornik.class);
            startActivity(intent);
            flag = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        poziv();
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
        getMenuInflater().inflate(R.menu.menu_splash, menu);
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
