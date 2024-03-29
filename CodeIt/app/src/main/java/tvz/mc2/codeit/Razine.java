package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Prikazuje listu razina.
 *
 * @author Kristina Marinić
 * @date 2015-04-18
 */
public class Razine extends Activity {

    /** GUI objekt koji prikazuje listu. */
    @InjectView(R.id.expandableListView) ExpandableListView expListView;
    /** Lista razina. */
    List<String> listaRazina;
    /** Lista podrazina. */
    List<List<String>> listaPodrazina;
    /** */
    Class[][] razine = { {RazinaJedan.class, RazinaDva.class, RazinaTri.class, RazinaTest.class } };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razine);
        ButterKnife.inject(this);

        popuniListe();
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Intent intent = new Intent(Razine.this, razine[groupPosition][childPosition]);
                startActivity(intent);
                return false;
            }
        });
        ExpandableListAdapter adapter = new ExpandableListAdapter(this.getApplicationContext(),
                listaRazina, listaPodrazina);
        expListView.setAdapter(adapter);
        expListView.expandGroup(0);
    }

    /**
     * Popunjava listu razina i listu podrazina s podacima.
     */
    private void popuniListe() {
        listaRazina = Arrays.asList(getResources().getStringArray(R.array.razine));
        listaPodrazina = new ArrayList<>();
        listaPodrazina.add(Arrays.asList(getResources().getStringArray(R.array.podrazine1)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_razine, menu);
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
