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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


public class RazinaDva extends Activity implements AdapterView.OnItemClickListener{

    int maxScrollX;
    private String [] izborArray;
    String zadatak;
    static TextView labelaRazinaDva;
    static ImageButton gumbGoRazina2;
    static boolean zastavica;

    @InjectView(R.id.slikaLabelaRazinaDva) ImageView slikaLabelaRazinaDva;
    @InjectView(R.id.okvirLabelaRazinaDva) View okvirLabelaRazinaDva;
    @InjectView(R.id.arrowLRazinaDva) ImageView arrowl;
    @InjectView(R.id.arrowRRazinaDva) ImageView arrowr;
    @InjectView(R.id.horizontalScrollViewRazinaDva) HorizontalScrollView hsv;
    @InjectView(R.id.frameLayoutRazinaDva) FrameLayout frameLayoutRazinaDva;
    @InjectView(R.id.drawerListRazinaDva) ListView drawerListRazinaDva;
    @InjectView(R.id.drawerLayoutRazinaDva) DrawerLayout drawerLayoutRazinaDva;
    @InjectView(R.id.menuElementiRazinaDva) RelativeLayout menuElementiRazinaDva;
    @InjectView(R.id.radnaPlohaRazinaDva) LinearLayout radnaPlohaRazinaDva;
    @InjectView(R.id.menuRazinaDva) LinearLayout menuRazinaDva;
    @InjectView(R.id.drawerGumbRazinaDva) ImageButton drawerGumbRazinaDva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_dva);
        ButterKnife.inject(this);

        labelaRazinaDva = (TextView) findViewById(R.id.labelaRazinaDva);
        gumbGoRazina2 = (ImageButton) findViewById(R.id.gumb_go_razina2);

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        GradientDrawable okvirGradientLabelaDva = (GradientDrawable) okvirLabelaRazinaDva.getBackground();
        okvirGradientLabelaDva.setStroke(visinaRuba, Color.BLACK);

        /**
         * Inicijalizacija drawer menu-a.
         */
        izborArray = getResources().getStringArray(R.array.izbor);
        View header = View.inflate(this, R.layout.list_header, null);
        drawerListRazinaDva.addHeaderView(header, "Header", false);
        drawerListRazinaDva.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        drawerListRazinaDva.setOnItemClickListener(this);

        /**
         * Postavljanje touch i drag listenera.
         */
        slikaLabelaRazinaDva.setOnTouchListener(touchListener);
        okvirLabelaRazinaDva.setOnDragListener(dragListener);

        zastavica = false;

        zadatak = getResources().getString(R.string.zadatakDva);
        dialogZadatak();
    }

    /**
     * OnClick metoda za drawer menu.
     */
    @OnClick(R.id.drawerGumbRazinaDva)
    public void onKlikNaDrawerMenu (View v)
    {
        drawerLayoutRazinaDva.openDrawer(drawerListRazinaDva);
    }

    /**
     * OnClick metoda na gumbu za zavrsetak razine.
     */
    @OnClick(R.id.gumb_go_razina2)
    public void klikZaDalje(View view)
    {
        Intent intent = new Intent(RazinaDva.this, Zvjezdice.class);
        intent.putExtra("poruka", getResources().getString(R.string.mess3));
        intent.putExtra("razina", getResources().getString(R.string.mess6));
        RazinaDva.this.finish();
        startActivity(intent);
    }

    /**
     * OnClick metoda na labeli za promjenu svojstva.
     */
    @OnClick(R.id.labelaRazinaDva)
    public void onKlikNaLabelu (View v)
    {
        dialogSvojstvaLabele();
    }

    /**
     * Metoda za prikazivanje strelica u izborniku.
     */
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

    /**
     * OnClik metoda za opcije iz drawera.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position)
        {
            case 1:
                dialogZadatak();
                break;
            case 2:
                dialogRestart();
                break;
            case 3:
                dialogIzlaz();
                break;
            default:
                break;
        }
        drawerLayoutRazinaDva.closeDrawer(drawerListRazinaDva);
        selectItem(position);

    }

    /**
     * Metoda koja govori koja je opcija iz drawera odabrana.
     */
    public void selectItem(int position) {
        drawerListRazinaDva.setItemChecked(position, true);
    }

    /**
     * Metoda za ponovno pokretanje zadatka.
     */
    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    /**
     * Metoda za izlaz iz zadatka i povratak na glavni izbornik.
     */
    public void izlaz()
    {
        Intent intent = new Intent(RazinaDva.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Dialog za ponovno pokretanje zadatka.
     */
    public void dialogRestart()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ponovno pokretanje");
        builder.setMessage("Želiš li ponovno pokrenuti razinu?");

        builder.setPositiveButton(R.string.poz, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                restart();
            }
        })
                .setNegativeButton(R.string.neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //ništa
                    }
                });
        builder.create().show();
    }

    /**
     * Dialog za izlaz na glavni izbornik.
     */
    public void dialogIzlaz()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izlaz");
        builder.setMessage("Želiš li izaći na glavni izbornik?");

        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                izlaz();
            }
        })
                .setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //ništa
                    }
                });
        builder.create().show();
    }

    /**
     * Dialog za tekst zadatka.
     */
    public void dialogZadatak()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.nas1);
        builder.setMessage(zadatak);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //ništa
            }
        });
        builder.create().show();
    }

    /**
     * Dialog za promjenu svojstva labele.
     */
    public void dialogSvojstvaLabele()
    {
        SvojstvaLabeleDialog dialog = new SvojstvaLabeleDialog();
        dialog.show(getFragmentManager(), "Svojstva labele");
    }

    /**
     * Touch listener za slike iz izbornika.
     */
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView labelaViewRazinaDva = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, labelaViewRazinaDva, 0);
            }
            return true;
        }
    };

    /**
     * Drag listener za slike iz izbornika.
     */
    View.OnDragListener dragListener = new View.OnDragListener() {
        private boolean unutarOkvira = false;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            String viewRectangleTag = v.getTag().toString();

            View viewLabela = (View) event.getLocalState();
            String viewLabelaTag = viewLabela.getTag().toString();
            GradientDrawable okvirGradientLabelaDva = (GradientDrawable) okvirLabelaRazinaDva.getBackground();
            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            int visinaSlike = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:

                    okvirGradientLabelaDva.setStroke(visinaRuba, Color.RED);
                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    unutarOkvira = true;
                    okvirGradientLabelaDva.setStroke(visinaRuba, Color.GREEN);

                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    unutarOkvira = false;
                    okvirGradientLabelaDva.setStroke(visinaRuba, Color.RED);

                    break;

                case DragEvent.ACTION_DRAG_ENDED:

                    reportResult(event.getResult());
                    break;

                case DragEvent.ACTION_DROP:

                    okvirLabelaRazinaDva.setVisibility(View.GONE);
                    labelaRazinaDva.setVisibility(View.VISIBLE);
                    slikaLabelaRazinaDva.setVisibility(View.GONE);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(visinaSlike, visinaSlike);
                    slikaLabelaRazinaDva.setLayoutParams(layoutParams);
                    slikaLabelaRazinaDva.requestLayout();

                    return unutarOkvira;
            }
            return true;
        }
    };

    /**
     * Rezultat da li je slika stavljena u odgovarajući okvir.
     */
    private void reportResult(final boolean result) {
        if(!result){
            GradientDrawable okvirGradientLabelaDva = (GradientDrawable) okvirLabelaRazinaDva.getBackground();
            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            okvirGradientLabelaDva.setStroke(visinaRuba, Color.BLACK);
        }
        else {
            okvirLabelaRazinaDva.setVisibility(View.GONE);
            GradientDrawable bgRectangle = (GradientDrawable)okvirLabelaRazinaDva.getBackground();
            bgRectangle.setStroke(0, Color.WHITE);
            drawerLayoutRazinaDva.invalidate();
        }
    }

    /**
     * Metoda za izradu sjene za sliku koja se povlači.
     */
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

    /**
     * Metoda za promjenu svojstva labele.
     */
    public static void promjeniSvojstva(String text, String boja, int velicina) {

        if (!(text.equals("")))
        labelaRazinaDva.setText(text);

        switch (boja)
        {
            case "------":
                labelaRazinaDva.setTextColor(labelaRazinaDva.getCurrentTextColor());
                break;

            case "Crvena":
                labelaRazinaDva.setTextColor(Color.parseColor("#FF5252"));
                break;

            case "Plava":
                labelaRazinaDva.setTextColor(Color.parseColor("#1E88E5"));
                break;

            case "Zelena":
                labelaRazinaDva.setTextColor(Color.parseColor("#43A047"));
                break;

            case "Žuta":
                labelaRazinaDva.setTextColor(Color.parseColor("#FFB300"));
                break;

            case "Ljubičasta":
                labelaRazinaDva.setTextColor(Color.parseColor("#5E35B1"));
                break;

            default:
                labelaRazinaDva.setTextColor(Color.BLACK);
                break;
        }

        if (!(velicina==0))
        {
            labelaRazinaDva.setTextSize(TypedValue.COMPLEX_UNIT_SP, velicina);
            zastavica = true;
        }

        if (!(labelaRazinaDva.getText().equals("Labela")) && !(labelaRazinaDva.getCurrentTextColor()==Color.BLACK) && (zastavica))
        {
            gumbGoRazina2.setVisibility(View.VISIBLE);
        }

    }
}
