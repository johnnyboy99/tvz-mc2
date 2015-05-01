package tvz.mc2.codeit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlavniIzbornik extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_izbornik);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.btnPomoc)
    public void klikNaMenu(View v){
        Intent intent = new Intent(GlavniIzbornik.this, Pomoc.class);
        startActivity(intent);
    }

    /**
     *  Pozivanje web stranice.
     */
    @OnClick(R.id.btnNauciVise)
    public void web(){
        String url = getResources().getString(R.string.poveznica);
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @OnClick(R.id.btnKreni)
    public void klikNaRazine(View v){
        Intent intent = new Intent(GlavniIzbornik.this, Razine.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnIzlaz)
    public void klikNaIzlaz(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izlaz");
        builder.setMessage("Želiš li izaći iz aplikacije?");

        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                finish();
                System.exit(0);
            }
        })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        
                    }
                });
        builder.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_glavni_izbornik, menu);
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
