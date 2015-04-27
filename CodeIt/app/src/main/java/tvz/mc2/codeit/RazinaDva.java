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

    @InjectView(R.id.slikaLabelaRazinaDva) ImageView slikaLabelaRazinaDva;
    @InjectView(R.id.okvirLabelaRazinaDva) View okvirLabelaRazinaDva;
    //@InjectView(R.id.labelaRazinaDva) static TextView labelaRazinaDva;
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
    //@InjectView(R.id.gumb_go_razina2) ImageButton gumbGoRazina2;

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

        izborArray = getResources().getStringArray(R.array.izbor);
        View header = View.inflate(this, R.layout.list_header, null);
        drawerListRazinaDva.addHeaderView(header, "Header", false);
        drawerListRazinaDva.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        drawerListRazinaDva.setOnItemClickListener(this);

        slikaLabelaRazinaDva.setOnTouchListener(touchListener);
        okvirLabelaRazinaDva.setOnDragListener(dragListener);

        zadatak = getResources().getString(R.string.zadatakDva);
        dialogZadatak();
    }

    @OnClick(R.id.drawerGumbRazinaDva)
    public void onKlikNaDrawerMenu (View v)
    {
        drawerLayoutRazinaDva.openDrawer(drawerListRazinaDva);
    }

    public void klikZaDalje(View view)
    {
        Intent intent = new Intent(RazinaDva.this, Zvjezdice.class);
        intent.putExtra("poruka", "Sve o labeli");
        startActivity(intent);
    }

    @OnClick(R.id.labelaRazinaDva)
    public void onKlikNaLabelu (View v)
    {
        dialogSvojstvaLabele();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_razina_dva, menu);
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

    public void selectItem(int position) {
        drawerListRazinaDva.setItemChecked(position, true);
    }

    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void izlaz()
    {
        Intent intent = new Intent(RazinaDva.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void dialogRestart()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ponovno pokretanje");
        builder.setMessage("Želiš li ponovno pokrenuti razinu?");

        builder.setPositiveButton("Da", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                restart();
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

    public void dialogZadatak()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Zadatak:");
        builder.setMessage(zadatak);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //ništa
            }
        });
        builder.create().show();
    }

    public void dialogSvojstvaLabele()
    {
        SvojstvaLabeleDialog dialog = new SvojstvaLabeleDialog();
        dialog.show(getFragmentManager(), "Svojstva labele");
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            //TODO koristit ovo za pretvaranje px u dp

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView labelaViewRazinaDva = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, labelaViewRazinaDva, 0);
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
                labelaRazinaDva.setTextColor(Color.BLUE);
                break;

            case "Zelena":
                labelaRazinaDva.setTextColor(Color.GREEN);
                break;

            case "Žuta":
                labelaRazinaDva.setTextColor(Color.YELLOW);
                break;

            case "Ljubičasta":
                labelaRazinaDva.setTextColor(Color.parseColor("#E040FB"));
                break;

            default:
                labelaRazinaDva.setTextColor(Color.BLACK);
                break;
        }

        if (!(velicina==0))
        labelaRazinaDva.setTextSize(TypedValue.COMPLEX_UNIT_SP, velicina);

        if (!(labelaRazinaDva.getText().equals("Labela")) && !(labelaRazinaDva.getCurrentTextColor()==Color.BLACK))
        {
            gumbGoRazina2.setVisibility(View.VISIBLE);
        }

    }
}
