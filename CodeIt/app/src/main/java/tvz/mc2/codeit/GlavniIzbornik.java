package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class GlavniIzbornik extends Activity {

    //ImageButton zastavaBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glavni_izbornik);
        ButterKnife.inject(this);

       // zastavaBtn = (ImageButton)findViewById(R.id.zastava);
        //zastavaBtn.setTag("eng");
        //zastavaBtn.setOnClickListener(imgButtonHandler);

    }


    /*View.OnClickListener imgButtonHandler = new View.OnClickListener()
    {
        public void onClick(View v)
        {
            if (zastavaBtn.getTag() == "eng")
            {
                zastavaBtn.setImageResource(R.drawable.cro);
                zastavaBtn.setTag("cro");
            }

            else
            {
                zastavaBtn.setImageResource(R.drawable.eng);
                zastavaBtn.setTag("eng");
            }
        }
    };
    }*/

    @OnClick(R.id.btnPomoc)
    public void klikNaMenu(View v){
        Intent intent = new Intent(GlavniIzbornik.this, MenuProba.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnNauciVise)
    public void klikNaRazinaJedan(View v){
        Intent intent = new Intent(GlavniIzbornik.this, RazinaJedan.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnKreni)
    public void klikNaRazine(View v){
        Intent intent = new Intent(GlavniIzbornik.this, Razine.class);
        startActivity(intent);
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
