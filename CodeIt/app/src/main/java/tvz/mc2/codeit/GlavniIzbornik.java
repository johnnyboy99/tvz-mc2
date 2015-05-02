package tvz.mc2.codeit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GlavniIzbornik extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_izbornik);
        ButterKnife.inject(this);
    }

    /**
     * OnClick metoda za otvaranje pomoći.
     */
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

    /**
     * OnClick metoda za liste razina.
     */
    @OnClick(R.id.btnKreni)
    public void klikNaRazine(View v){
        Intent intent = new Intent(GlavniIzbornik.this, Razine.class);
        startActivity(intent);
    }

    /**
     * OnClick metoda za izlazak iz aplikacije.
     */
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
}
