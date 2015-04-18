package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class Razine extends Activity {

    @InjectView(R.id.expandableListView) ExpandableListView expListView;
    ExpandableListAdapter listAdapter;
    List<String> listaRazina;
    List<List<String>> listaPodrazina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razine);
        ButterKnife.inject(this);

        popuniListe();
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id) {
                Intent intent = new Intent(Razine.this, RazinaJedan.class);
                startActivity(intent);
                return false;
            }
        });
        ExpandableListAdapter adapter = new ExpandableListAdapter(this.getApplicationContext(),
                listaRazina, listaPodrazina);
        expListView.setAdapter(adapter);
    }

    private void popuniListe() {
        listaRazina = Arrays.asList(getResources().getStringArray(R.array.razine));
        listaPodrazina = new ArrayList<>();
        listaPodrazina.add(Arrays.asList(getResources().getStringArray(R.array.podrazine1)));
        listaPodrazina.add(Arrays.asList(getResources().getStringArray(R.array.podrazine2)));
        listaPodrazina.add(Arrays.asList(getResources().getStringArray(R.array.podrazine3)));
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
