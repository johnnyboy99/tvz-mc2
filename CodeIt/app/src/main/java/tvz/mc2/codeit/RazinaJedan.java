package tvz.mc2.codeit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


//TODO da promjeni velicinu sjene

public class RazinaJedan extends Activity implements AdapterView.OnItemClickListener {

    int maxScrollX;
    private String [] izborArray;

    @InjectView(R.id.slikaGumbRazinaJedan) ImageView slikaGumbRazinaJedan;
    @InjectView(R.id.okvirGumbRazinaJedan) View okvirGumbRazinaJedan;
    @InjectView(R.id.gumbRazinaJedan) Button gumbRazinaJedan;
    @InjectView(R.id.arrowLRazinaJedan) ImageView arrowl;
    @InjectView(R.id.arrowRRazinaJedan) ImageView arrowr;
    @InjectView(R.id.horizontalScrollViewRazinaJedan) HorizontalScrollView hsv;
    @InjectView(R.id.frameLayoutRazinaJedan) FrameLayout frameLayoutRazinaJedan;
    @InjectView(R.id.drawerListRazinaJedan) ListView drawerListRazinaJedan;
    @InjectView(R.id.drawerLayoutRazinaJedan) DrawerLayout drawerLayoutRazinaJedan;
    @InjectView(R.id.menuElementiRazinaJedan) RelativeLayout menuElementiRazinaJedan;
    @InjectView(R.id.radnaPlohaRazinaJedan) LinearLayout radnaPlohaRazinaJedan;
    @InjectView(R.id.prazanKrugZaMenuDrawerPlohuRazinaJedan) View prazanKrugZaMenuDrawerPlohuRazinaJedan;
    @InjectView(R.id.menuRazinaJedan) LinearLayout menuRazinaJedan;
    @InjectView(R.id.drawerGumbRazinaJedan) ImageButton drawerGumbRazinaJedan;

    Handler handler = new Handler();

    private static int VRIJEME_ANIMACIJE = 2000;
    private static int CEKANJE_ZA_PRVU_ANIMACIJU = 1000;

    Animation fadeIn;
    Animation fadeOut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_jedan);
        ButterKnife.inject(this);

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbRazinaJedan.getBackground();
        okvirGradientGumbaJedan.setStroke(visinaRuba, Color.BLACK);


        izborArray = getResources().getStringArray(R.array.izbor);
        drawerListRazinaJedan.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        drawerListRazinaJedan.setOnItemClickListener(this);

        //start animacija
        prvaAnimacijaSveUSivo();



        slikaGumbRazinaJedan.setOnTouchListener(touchListener);
        okvirGumbRazinaJedan.setOnDragListener(dragListener);
    }

    @OnClick(R.id.drawerGumbRazinaJedan)
    public void onKlikNaDrawerMenu (View v)
    {
        drawerLayoutRazinaJedan.openDrawer(drawerListRazinaJedan);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            /*
            //TODO koristit ovo za pretvaranje px u dp
            int height2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(height2, height2);
            slikaGumbRazinaJedan.setLayoutParams(layoutParams);
            slikaGumbRazinaJedan.requestLayout();
            dragLayoutRazinaJedan.invalidate();
            */

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView gumbViewRazinaJedan = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, gumbViewRazinaJedan, 0);
            }
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        private boolean unutarOkvira = false;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            String viewRectangleTag = v.getTag().toString();

            View viewGumb = (View) event.getLocalState();
            String viewGumbTag = viewGumb.getTag().toString();
            GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbRazinaJedan.getBackground();
            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            int visinaSlike = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:

                    okvirGradientGumbaJedan.setStroke(visinaRuba, Color.RED);
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    unutarOkvira = true;
                    okvirGradientGumbaJedan.setStroke(visinaRuba, Color.GREEN);

                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    unutarOkvira = false;
                    okvirGradientGumbaJedan.setStroke(visinaRuba, Color.RED);

                    break;

                case DragEvent.ACTION_DRAG_ENDED:

                    reportResult(event.getResult());
                    break;

                case DragEvent.ACTION_DROP:

                    okvirGumbRazinaJedan.setVisibility(View.GONE);
                    gumbRazinaJedan.setVisibility(View.VISIBLE);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(visinaSlike, visinaSlike);
                    slikaGumbRazinaJedan.setLayoutParams(layoutParams);
                    slikaGumbRazinaJedan.requestLayout();
                    //dragLayoutRazinaJedan.invalidate();

                    return unutarOkvira;
            }
            return true;
        }
    };

    private void reportResult(final boolean result) {
        if(!result){
            GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbRazinaJedan.getBackground();
            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            okvirGradientGumbaJedan.setStroke(visinaRuba, Color.BLACK);
        }
        else {
            okvirGumbRazinaJedan.setVisibility(View.GONE);
            GradientDrawable bgRectangle = (GradientDrawable)okvirGumbRazinaJedan.getBackground();
            bgRectangle.setStroke(0, Color.WHITE);
            drawerLayoutRazinaJedan.invalidate();
        }
    }

    private class MyShadowBuilder extends View.DragShadowBuilder {
        private Drawable shadow;

        public MyShadowBuilder(View v) {
            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas) {
            canvas.scale(10000, 10000);

            shadow.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint) {
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());

            shadow.setBounds(0, 0, 10000, 10000);


            shadowSize.set(10000, 10000);
            shadowTouchPoint.set(10000, 10000);
        }
    }

    @OnClick(R.id.gumbRazinaJedan)
    public void klikNaGumbRazinaJedan(View view) {
        final EditText input = new EditText(this);
        new AlertDialog.Builder(RazinaJedan.this)
                .setTitle("Promjeni tekst")
                .setMessage("Odaberi tekst za gumb")
                .setView(input)
                .setPositiveButton("Promjeni", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        gumbRazinaJedan.setText(input.getText());
                    }
                }).setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Do nothing.
            }
        }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_razina_jedan, menu);
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

        maxScrollX = hsv.getChildAt(0).getMeasuredWidth()-hsv.getMeasuredWidth();

        //TODO promjenit da ne prikazuje!!!

        arrowl.setVisibility(View.VISIBLE);
        arrowr.setVisibility(View.VISIBLE);

        /*
        if (hsv.getScrollX() == 0) {
            arrowl.setVisibility(View.GONE);
            arrowr.setVisibility(View.GONE);
        }

        if (hsv.getScrollX() == maxScrollX)
        {
            arrowr.setVisibility(View.GONE);
        }
        */

        /*
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
        */
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
        drawerListRazinaJedan.setItemChecked(position, true);
    }

    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void izlaz()
    {
        Intent intent = new Intent(RazinaJedan.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void prvaAnimacijaSveUSivo(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //cekaj prije rendera na sivu
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                fadeIn.setDuration(1000);

                menuElementiRazinaJedan.setBackgroundColor(getResources().getColor(R.color.tamnoSiva));
                drawerGumbRazinaJedan.setBackgroundColor(getResources().getColor(R.color.tamnoSiva));
                menuRazinaJedan.setBackgroundColor(getResources().getColor(R.color.tamnoSiva));
                radnaPlohaRazinaJedan.setBackgroundColor(getResources().getColor(R.color.svjetloSiva));
                GradientDrawable bgRectangle = (GradientDrawable)okvirGumbRazinaJedan.getBackground();
                bgRectangle.setStroke(2, Color.WHITE);

                menuElementiRazinaJedan.setAnimation(fadeIn);
                radnaPlohaRazinaJedan.setAnimation(fadeIn);
                okvirGumbRazinaJedan.setAnimation(fadeIn);
                prazanKrugZaMenuDrawerPlohuRazinaJedan.setAnimation(fadeIn);

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
                drugaAnimacijaDrawerMenu();
            }

        });
    }

    private void drugaAnimacijaDrawerMenu(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                fadeIn.setDuration(VRIJEME_ANIMACIJE);

                prazanKrugZaMenuDrawerPlohuRazinaJedan.setVisibility(View.VISIBLE);
                drawerGumbRazinaJedan.setBackgroundColor(getResources().getColor(R.color.crvenaPozadina));
                menuRazinaJedan.setBackgroundColor(getResources().getColor(R.color.crvenaPozadinaSvjetlije));

                prazanKrugZaMenuDrawerPlohuRazinaJedan.setAnimation(fadeIn);
                drawerGumbRazinaJedan.setAnimation(fadeIn);
                menuRazinaJedan.setAnimation(fadeIn);

            }

        }, CEKANJE_ZA_PRVU_ANIMACIJU);
    }
}
