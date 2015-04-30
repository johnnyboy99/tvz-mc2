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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RazinaTri extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {

    private String [] izborArray;
    int maxScrollX;
    String zadatak;
    String [] sveBojeSpinnera;

    boolean zastavicaCheckBox1, zastavicaCheckBox2;

    @InjectView(R.id.okvirLabelaRazinaTri) View okvirLabelaRazinaTri;
    @InjectView(R.id.okvirCheckBox1RazinaTri) View okvirCheckBox1Razina3;
    @InjectView(R.id.okvirCheckBox2RazinaTri) View okvirCheckBox2Razina3;
    @InjectView(R.id.arrowLRazinaTri) ImageView arrowl;
    @InjectView(R.id.arrowRRazinaTri) ImageView arrowr;
    @InjectView(R.id.horizontalScrollViewRazinaTri) HorizontalScrollView hsv3;
    @InjectView(R.id.frameLayoutRazinaTri) FrameLayout frameLayoutRazinaTri;
    @InjectView(R.id.drawerListRazinaTri) ListView drawerListRazinaTri;
    @InjectView(R.id.drawerLayoutRazinaTri) DrawerLayout drawerLayoutRazinaTri;
    @InjectView(R.id.menuElementiRazinaTri) RelativeLayout menuElementiRazinaTri;
    @InjectView(R.id.radnaPlohaRazinaTri) GridLayout radnaPlohaRazinaTri;
    @InjectView(R.id.menuRazinaTri) LinearLayout menuRazinaTri;
    @InjectView(R.id.drawerGumbRazinaTri) ImageButton drawerGumbRazinaTri;
    @InjectView(R.id.labelaRazinaTri) TextView labelaRazinaTri;
    @InjectView(R.id.gumb_go_razina3) ImageButton gumbGoRazina3;
    @InjectView(R.id.spinnerRazinaTri) Spinner spinnerRazinaTri;
    @InjectView(R.id.checkBox1RazinaTri) CheckBox checkBox1Razina3;
    @InjectView(R.id.checkBox2RazinaTri) CheckBox checkBox2Razina3;
    @InjectView(R.id.slikaCheckBox1RazinaTri) ImageView slikaCheckBox1RazinaTri;
    @InjectView(R.id.slikaCheckBox2RazinaTri) ImageView slikaCheckBox2RazinaTri;

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_tri);
        ButterKnife.inject(this);

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        GradientDrawable okvirGradientLabelaTri = (GradientDrawable) okvirLabelaRazinaTri.getBackground();
        okvirGradientLabelaTri.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientCheckBox1RazinaTri = (GradientDrawable) okvirCheckBox1Razina3.getBackground();
        okvirGradientCheckBox1RazinaTri.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientCheckBox2Razina3 = (GradientDrawable) okvirCheckBox2Razina3.getBackground();
        okvirGradientCheckBox2Razina3.setStroke(visinaRuba, Color.BLACK);

        izborArray = getResources().getStringArray(R.array.izbor);
        View header = View.inflate(this, R.layout.list_header, null);
        drawerListRazinaTri.addHeaderView(header, "Header", false);
        drawerListRazinaTri.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        drawerListRazinaTri.setOnItemClickListener(this);

        sveBojeSpinnera = getResources().getStringArray(R.array.bojeSpinner3);
        adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sveBojeSpinnera);
        spinnerRazinaTri.setAdapter(adapter);
        spinnerRazinaTri.setOnItemSelectedListener(this);

        slikaCheckBox1RazinaTri.setOnTouchListener(touchListener);
        slikaCheckBox2RazinaTri.setOnTouchListener(touchListener);
        okvirCheckBox1Razina3.setOnDragListener(dragListener);
        okvirCheckBox2Razina3.setOnDragListener(dragListener);

        zadatak = getResources().getString(R.string.zadatakTri);
        dialogZadatak();
    }

    @OnClick(R.id.drawerGumbRazinaTri)
    public void onKlikNaDrawerMenu (View v)
    {
        drawerLayoutRazinaTri.openDrawer(drawerListRazinaTri);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        maxScrollX = hsv3.getChildAt(0).getMeasuredWidth() - hsv3.getMeasuredWidth();

        if (hsv3.getScrollX() == 0) {
            arrowl.setVisibility(View.GONE);
        }

        if (hsv3.getScrollX() == maxScrollX)
        {
            arrowr.setVisibility(View.GONE);
        }

        hsv3.setOnTouchListener(new View.OnTouchListener() {
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

    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void izlaz()
    {
        Intent intent = new Intent(RazinaTri.this, GlavniIzbornik.class);
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
        builder.setTitle(R.string.nas1);
        builder.setMessage(zadatak);

        builder.setPositiveButton(R.string.neg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //ništa
            }
        });
        builder.create().show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_razina_tri, menu);
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
        drawerLayoutRazinaTri.closeDrawer(drawerListRazinaTri);
        selectItem(position);
    }

    public void selectItem(int position) {
        drawerListRazinaTri.setItemChecked(position, true);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            //TODO koristit ovo za pretvaranje px u dp

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView labelaViewRazinaTri = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, labelaViewRazinaTri, 0);
            }
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        private boolean unutarOkvira = false;

        @Override
        public boolean onDrag(View v, DragEvent event) {

            int dragEvent = event.getAction();
            String viewOkvirTag = v.getTag().toString();

            View viewGumb = (View) event.getLocalState();
            String viewGumbTag = viewGumb.getTag().toString();

           // GradientDrawable okvirGradientLabelaTri = (GradientDrawable) okvirLabelaRazinaTri.getBackground();
            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
            int visinaSlike = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:

                    if (viewGumbTag.equals("slikaCheckBox1Razina3"))
                    {
                        GradientDrawable okvirGradientCheckBox1Razina3 = (GradientDrawable) okvirCheckBox1Razina3.getBackground();
                        okvirGradientCheckBox1Razina3.setStroke(visinaRuba, Color.RED);
                    }

                    else if(viewGumbTag.equals("slikaCheckBox2Razina3"))
                    {
                        GradientDrawable okvirGradientCheckBox2Razina3 = (GradientDrawable) okvirCheckBox2Razina3.getBackground();
                        okvirGradientCheckBox2Razina3.setStroke(visinaRuba, Color.RED);
                    }

                    break;

                case DragEvent.ACTION_DRAG_ENTERED:

                    if (viewGumbTag.equals("slikaCheckBox1Razina3") && viewOkvirTag.equals("okvirCheckBox1Razina3"))
                    {
                        GradientDrawable okvirGradientCheckBox1Razina3 = (GradientDrawable) okvirCheckBox1Razina3.getBackground();
                        okvirGradientCheckBox1Razina3.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }

                    else if(viewGumbTag.equals("slikaCheckBox2Razina3") && viewOkvirTag.equals("okvirCheckBox2Razina3"))
                    {
                        GradientDrawable okvirGradientCheckBox2Razina3 = (GradientDrawable) okvirCheckBox2Razina3.getBackground();
                        okvirGradientCheckBox2Razina3.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }

                    break;

                case DragEvent.ACTION_DRAG_EXITED:

                    if (viewGumbTag.equals("slikaCheckBox1Razina3") && viewOkvirTag.equals("okvirCheckBox1Razina3"))
                    {
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientCheckBox1Razina3 = (GradientDrawable) okvirCheckBox1Razina3.getBackground();
                        okvirGradientCheckBox1Razina3.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }

                    else if(viewGumbTag.equals("slikaCheckBox2Razina3") && viewOkvirTag.equals("okvirCheckBox2Razina3"))
                    {
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientCheckBox2Razina3 = (GradientDrawable) okvirCheckBox2Razina3.getBackground();
                        okvirGradientCheckBox2Razina3.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }

                    break;

                case DragEvent.ACTION_DRAG_ENDED:

                    reportResult(event.getResult(), viewOkvirTag, viewGumbTag);
                    break;

                case DragEvent.ACTION_DROP:

                    if (viewGumbTag.equals("slikaCheckBox1Razina3") && viewOkvirTag.equals("okvirCheckBox1Razina3"))
                    {
                        okvirCheckBox1Razina3.setVisibility(View.GONE);
                        checkBox1Razina3.setVisibility(View.VISIBLE);
                        slikaCheckBox1RazinaTri.setVisibility(View.GONE);
                        zastavicaCheckBox1 = true;

                        if (zastavicaCheckBox2)
                        {
                            zadatak = getResources().getString(R.string.zadatakTri2);
                            dialogZadatak();
                        }
                    }

                    else if (viewGumbTag.equals("slikaCheckBox2Razina3") && viewOkvirTag.equals("okvirCheckBox2Razina3"))
                    {
                        okvirCheckBox2Razina3.setVisibility(View.GONE);
                        checkBox2Razina3.setVisibility(View.VISIBLE);
                        slikaCheckBox2RazinaTri.setVisibility(View.GONE);
                        zastavicaCheckBox2 = true;

                        if (zastavicaCheckBox1)
                        {
                            zadatak = getResources().getString(R.string.zadatakTri2);
                            dialogZadatak();
                        }
                    }

                    return unutarOkvira;
            }
            return true;
        }
    };

    private void reportResult(final boolean result, String viewOkvirTag, String viewGumbTag) {
        if(!result)
        {
            zacrniSveOkvire();
        }

        else
        {
            if (viewGumbTag.equals("slikaCheckBox1Razina3") && viewOkvirTag.equals("okvirCheckBox1Razina3"))
            {
                okvirCheckBox1Razina3.setVisibility(View.GONE);
                checkBox1Razina3.setVisibility(View.VISIBLE);
                slikaCheckBox1RazinaTri.setVisibility(View.GONE);
            }

            else if (viewGumbTag.equals("slikaCheckBox2Razina3") && viewOkvirTag.equals("okvirCheckBox2Razina3"))
            {
                okvirCheckBox2Razina3.setVisibility(View.GONE);
                checkBox2Razina3.setVisibility(View.VISIBLE);
                slikaCheckBox2RazinaTri.setVisibility(View.GONE);
            }
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


    @OnClick(R.id.checkBox1RazinaTri)
    public void klikNaCB1 (View v)
    {
        if (checkBox1Razina3.isChecked())
        {
            labelaRazinaTri.setVisibility(View.VISIBLE);
        }

        else
        {
            labelaRazinaTri.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.checkBox2RazinaTri)
    public void klikNaCB2 (View v)
    {
        if (checkBox2Razina3.isChecked())
        {
            spinnerRazinaTri.setVisibility(View.VISIBLE);
        }

        else
        {
            spinnerRazinaTri.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position)
        {
            case 1:
                labelaRazinaTri.setTextColor(Color.BLACK);
                break;

            case 2:
                labelaRazinaTri.setTextColor(Color.parseColor("#FF5252"));
                break;

            case 3:
                labelaRazinaTri.setTextColor(Color.parseColor("#1E88E5"));
                break;

            case 4:
                labelaRazinaTri.setTextColor(Color.parseColor("#43A047"));
                break;

            case 5:
                labelaRazinaTri.setTextColor(Color.parseColor("#FF5252"));
                break;

            case 6:
                labelaRazinaTri.setTextColor(Color.parseColor("#5E35B1"));
                break;

            default:
                labelaRazinaTri.setTextColor((labelaRazinaTri.getCurrentTextColor()));
                break;
        }

            if ((labelaRazinaTri.getCurrentTextColor() != Color.BLACK) && checkBox1Razina3.isChecked() && checkBox2Razina3.isChecked())
            {
                gumbGoRazina3.setVisibility(View.VISIBLE);
            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void zacrniSveOkvire() {

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        GradientDrawable okvirGradientCheckBox1RazinaTri = (GradientDrawable) okvirCheckBox1Razina3.getBackground();
        okvirGradientCheckBox1RazinaTri.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientCheckBox2Razina3 = (GradientDrawable) okvirCheckBox2Razina3.getBackground();
        okvirGradientCheckBox2Razina3.setStroke(visinaRuba, Color.BLACK);

    }

    @OnClick(R.id.gumb_go_razina3)
    public void klikZaDalje(View view)
    {
        Intent intent = new Intent(RazinaTri.this, Zvjezdice.class);
        intent.putExtra("poruka", getResources().getString(R.string.mess4));
        intent.putExtra("razina", getResources().getString(R.string.mess2));
        finish();
        startActivity(intent);
    }

}