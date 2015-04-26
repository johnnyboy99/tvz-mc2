package tvz.mc2.codeit;

import android.app.Activity;
import android.content.ClipData;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RazinaTest extends Activity implements AdapterView.OnItemClickListener {

    int maxScrollX;
    private String [] izborArrayRazinaTest;

    @InjectView(R.id.horizontalScrollViewRazinaTest) HorizontalScrollView hsv;
    @InjectView(R.id.arrowLRazinaTest) ImageView arrowl;
    @InjectView(R.id.arrowRRazinaTest) ImageView arrowr;
    @InjectView(R.id.drawerListRazinaTest) ListView drawerListRazinaTest;
    @InjectView(R.id.drawerLayoutRazinaTest) DrawerLayout drawerLayoutRazinaTest;
    @InjectView(R.id.frameLayoutRazinaTest) FrameLayout frameLayoutRazinaTest;
    @InjectView(R.id.menuRazinaTest) LinearLayout menuRazinaTest;
    @InjectView(R.id.drawerGumbRazinaTest) ImageButton drawerGumbRazinaTest;
    @InjectView(R.id.menuElementiRazinaTest) RelativeLayout menuElementiRazinaTest;

    //slike u izborniku elemenata
    @InjectView(R.id.slikaGumbPrviBrojRazinaTest) ImageView slikaGumbPrviBrojRazinaTest;
    @InjectView(R.id.slikaGumbDrugiBrojRazinaTest) ImageView slikaGumbDrugiBrojRazinaTest;
    @InjectView(R.id.slikaGumbJednakoRazinaTest) ImageView slikaGumbJednakoRazinaTest;
    @InjectView(R.id.slikaGumbRezultatRazinaTest) ImageView slikaGumbRezultatRazinaTest;
    @InjectView(R.id.slikaGumbRacOperacijeRazinaTest) ImageView slikaGumbRacOperacijeRazinaTest;

    //okviri u radnoj plohi
    @InjectView(R.id.okvirEditTextPrviBrojRazinaTest) View okvirEditTextPrviBrojRazinaTest;
    @InjectView(R.id.okvirSpinnerRazinaTest) View okvirSpinnerRazinaTest;
    @InjectView(R.id.okvirEditTextDrugiBrojRazinaTest) View okvirEditTextDrugiBrojRazinaTest;
    @InjectView(R.id.okvirBtnJednakoRazinaTest) View okvirBtnJednakoRazinaTest;
    @InjectView(R.id.okvirEditTextRezultatRazinaTest) View okvirEditTextRezultatRazinaTest;

    //elementi u radnoj plohi
    @InjectView(R.id.editTextPrviBrojRazinaTest) EditText editTextPrviBrojRazinaTest;
    @InjectView(R.id.spinnerRacunskeOperacijeRazinaTest) Spinner spinnerRacunskeOperacijeRazinaTest;
    @InjectView(R.id.editTextDrugiBrojRazinaTest) EditText editTextDrugiBrojRazinaTest;
    @InjectView(R.id.btnJednakoRazinaTest) Button btnJednakoRazinaTest;
    @InjectView(R.id.textViewRezultatRazinaTest) TextView textViewRezultatRazinaTest;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_test);
        ButterKnife.inject(this);

        //drawer menu
        izborArrayRazinaTest = getResources().getStringArray(R.array.izbor);
        View v = View.inflate(this, R.layout.list_header, null);
        drawerListRazinaTest.addHeaderView(v, "Header", false);
        drawerListRazinaTest.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArrayRazinaTest));
        drawerListRazinaTest.setOnItemClickListener(this);

        //touch listeneri za slike
        slikaGumbPrviBrojRazinaTest.setOnTouchListener(touchListener);
        slikaGumbDrugiBrojRazinaTest.setOnTouchListener(touchListener);
        slikaGumbRacOperacijeRazinaTest.setOnTouchListener(touchListener);
        slikaGumbJednakoRazinaTest.setOnTouchListener(touchListener);
        slikaGumbRezultatRazinaTest.setOnTouchListener(touchListener);

        //drag listeneri
        okvirEditTextPrviBrojRazinaTest.setOnDragListener(dragListener);
        okvirEditTextDrugiBrojRazinaTest.setOnDragListener(dragListener);
        okvirSpinnerRazinaTest.setOnDragListener(dragListener);
        okvirBtnJednakoRazinaTest.setOnDragListener(dragListener);
        okvirEditTextRezultatRazinaTest.setOnDragListener(dragListener);



    }

    @OnClick(R.id.drawerGumbRazinaTest)
    public void onClick (View v)
    {
        drawerLayoutRazinaTest.openDrawer(drawerListRazinaTest);
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView gumbViewRazinaTest = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, gumbViewRazinaTest, 0);
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

            int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

            switch (dragEvent) {
                case DragEvent.ACTION_DRAG_STARTED:

                    if(viewGumbTag.equals("slikaGumbPrviBrojRazinaTest")){
                        GradientDrawable okvirGradientEditTextPrviBrojRazinaTest = (GradientDrawable) okvirEditTextPrviBrojRazinaTest.getBackground();
                        okvirGradientEditTextPrviBrojRazinaTest.setStroke(visinaRuba, Color.RED);
                    }

                    else if(viewGumbTag.equals("slikaGumbDrugiBrojRazinaTest")){
                        GradientDrawable okvirGradientEditTextDrugiBrojRazinaTest = (GradientDrawable) okvirEditTextDrugiBrojRazinaTest.getBackground();
                        okvirGradientEditTextDrugiBrojRazinaTest.setStroke(visinaRuba, Color.RED);
                    }

                    else if(viewGumbTag.equals("slikaGumbJednakoRazinaTest")){
                        GradientDrawable okvirGradientBtnJednakoRazinaTest = (GradientDrawable) okvirBtnJednakoRazinaTest.getBackground();
                        okvirGradientBtnJednakoRazinaTest.setStroke(visinaRuba, Color.RED);
                    }

                    else if(viewGumbTag.equals("slikaGumbRezultatRazinaTest")){
                        GradientDrawable okvirGradientEditTextRezultatRazinaTest = (GradientDrawable) okvirEditTextRezultatRazinaTest.getBackground();
                        okvirGradientEditTextRezultatRazinaTest.setStroke(visinaRuba, Color.RED);
                    }

                    else if(viewGumbTag.equals("slikaGumbRacOperacijeRazinaTest")){
                        GradientDrawable okvirGradientSpinnerRazinaTest = (GradientDrawable) okvirSpinnerRazinaTest.getBackground();
                        okvirGradientSpinnerRazinaTest.setStroke(visinaRuba, Color.RED);
                    }

                    break;


                case DragEvent.ACTION_DRAG_ENTERED:

                    unutarOkvira = true;
                    //okvirGradientGumbaJedan.setStroke(visinaRuba, Color.GREEN);

                    break;

                /*

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

                    */
            }

            return true;
        }
    };



    /*
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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

        maxScrollX = hsv.getChildAt(0).getMeasuredWidth()-hsv.getMeasuredWidth();

        arrowl.setVisibility(View.VISIBLE);
        arrowr.setVisibility(View.VISIBLE);

        if (hsv.getScrollX() == 0) {
            arrowl.setVisibility(View.GONE);
            arrowr.setVisibility(View.GONE);
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
        drawerListRazinaTest.setItemChecked(position, true);
    }

    public void restart()
    {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void izlaz()
    {
        Intent intent = new Intent(RazinaTest.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
