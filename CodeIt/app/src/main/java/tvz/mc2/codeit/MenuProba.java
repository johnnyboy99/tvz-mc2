package tvz.mc2.codeit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MenuProba extends Activity implements AdapterView.OnItemClickListener {

    int maxScrollX;
    private String [] izborArray;
    @InjectView(R.id.horizontalScrollView) HorizontalScrollView hsv;
    @InjectView(R.id.arrowl) ImageView arrowl;
    @InjectView(R.id.arrowr) ImageView arrowr;
    @InjectView(R.id.drawerGumb) ImageButton drawerGumb;
    @InjectView(R.id.mainContent) FrameLayout frame;
    @InjectView(R.id.navList) ListView mDrawerList;
    @InjectView(R.id.drawer_layout) DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_proba);
        ButterKnife.inject(this);
        izborArray = getResources().getStringArray(R.array.izbor);
        mDrawerList.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        mDrawerList.setOnItemClickListener(this);
    }

    @OnClick(R.id.drawerGumb)
    public void onClick (View v)
    {
        drawerLayout.openDrawer(mDrawerList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_proba, menu);
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        maxScrollX = hsv.getChildAt(0).getMeasuredWidth() - hsv.getMeasuredWidth();

        if (hsv.getScrollX() == 0) {
            arrowl.setVisibility(View.GONE);
        }

        if (hsv.getScrollX() == maxScrollX)
        {
            arrowr.setVisibility(View.GONE);
        }

        hsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getScrollX() != 0)
                {
                    arrowl.setVisibility(View.VISIBLE);
                }

                else
                {
                    arrowl.setVisibility(View.GONE);
                }

                if (v.getScrollX() == maxScrollX)
                {
                    arrowr.setVisibility(View.GONE);
                }
                else
                {
                    arrowr.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 0:
                Toast.makeText(this, "Ovdje bude popup za tekst zadatka", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                restart();
                break;
            case 2:
                izlaz();
                break;
            default:
                break;
        }
        selectItem(position);
    }

    public void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
    }

    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void izlaz()
    {
        Intent intent = new Intent(MenuProba.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
