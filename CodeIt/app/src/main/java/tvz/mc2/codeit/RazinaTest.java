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

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RazinaTest extends Activity implements AdapterView.OnItemClickListener {

    int maxScrollX;
    private String[] izborArrayRazinaTest;
    String zadatak;

    @InjectView(R.id.horizontalScrollViewRazinaTest)
    HorizontalScrollView hsv;
    @InjectView(R.id.arrowLRazinaTest)
    ImageView arrowl;
    @InjectView(R.id.arrowRRazinaTest)
    ImageView arrowr;
    @InjectView(R.id.drawerListRazinaTest)
    ListView drawerListRazinaTest;
    @InjectView(R.id.drawerLayoutRazinaTest)
    DrawerLayout drawerLayoutRazinaTest;
    @InjectView(R.id.frameLayoutRazinaTest)
    FrameLayout frameLayoutRazinaTest;
    @InjectView(R.id.menuRazinaTest)
    LinearLayout menuRazinaTest;
    @InjectView(R.id.drawerGumbRazinaTest)
    ImageButton drawerGumbRazinaTest;
    @InjectView(R.id.menuElementiRazinaTest)
    RelativeLayout menuElementiRazinaTest;

    @InjectView(R.id.gumbGoRazinaTest) ImageButton gumbGoRazinaTest;

    //slike u izborniku elemenata
    @InjectView(R.id.slikaGumbPrviBrojRazinaTest)
    ImageView slikaGumbPrviBrojRazinaTest;
    @InjectView(R.id.slikaGumbDrugiBrojRazinaTest)
    ImageView slikaGumbDrugiBrojRazinaTest;
    @InjectView(R.id.slikaGumbJednakoRazinaTest)
    ImageView slikaGumbJednakoRazinaTest;
    @InjectView(R.id.slikaGumbRezultatRazinaTest)
    ImageView slikaGumbRezultatRazinaTest;
    @InjectView(R.id.slikaGumbRacOperacijeRazinaTest)
    ImageView slikaGumbRacOperacijeRazinaTest;

    //okviri u radnoj plohi
    @InjectView(R.id.okvirEditTextPrviBrojRazinaTest)
    View okvirEditTextPrviBrojRazinaTest;
    @InjectView(R.id.okvirSpinnerRazinaTest)
    View okvirSpinnerRazinaTest;
    @InjectView(R.id.okvirEditTextDrugiBrojRazinaTest)
    View okvirEditTextDrugiBrojRazinaTest;
    @InjectView(R.id.okvirBtnJednakoRazinaTest)
    View okvirBtnJednakoRazinaTest;
    @InjectView(R.id.okvirEditTextRezultatRazinaTest)
    View okvirEditTextRezultatRazinaTest;

    //elementi u radnoj plohi
    @InjectView(R.id.editTextPrviBrojRazinaTest)
    EditText editTextPrviBrojRazinaTest;
    @InjectView(R.id.spinnerRacunskeOperacijeRazinaTest)
    Spinner spinnerRacunskeOperacijeRazinaTest;
    @InjectView(R.id.editTextDrugiBrojRazinaTest)
    EditText editTextDrugiBrojRazinaTest;
    @InjectView(R.id.btnJednakoRazinaTest)
    Button btnJednakoRazinaTest;
    @InjectView(R.id.textViewRezultatRazinaTest)
    TextView textViewRezultatRazinaTest;

    //booleani dal su svi uneseni
    boolean booleanEditTextPrviBroj = false;
    boolean booleanEditTextDrugiBroj = false;
    boolean booleanSpinner = false;
    boolean booleanJednako = false;
    boolean booleanRezultat = false;
    int kolkoIhJePostavljeno = 0;


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

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        //inicijalno postavljanje boje na okvire
        GradientDrawable okvirGradientEditTextPrviBrojRazinaTest = (GradientDrawable) okvirEditTextPrviBrojRazinaTest.getBackground();
        okvirGradientEditTextPrviBrojRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientEditTextDrugiBrojRazinaTest = (GradientDrawable) okvirEditTextDrugiBrojRazinaTest.getBackground();
        okvirGradientEditTextDrugiBrojRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientBtnJednakoRazinaTest = (GradientDrawable) okvirBtnJednakoRazinaTest.getBackground();
        okvirGradientBtnJednakoRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientEditTextRezultatRazinaTest = (GradientDrawable) okvirEditTextRezultatRazinaTest.getBackground();
        okvirGradientEditTextRezultatRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientSpinnerRazinaTest = (GradientDrawable) okvirSpinnerRazinaTest.getBackground();
        okvirGradientSpinnerRazinaTest.setStroke(visinaRuba, Color.BLACK);

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

        zadatak = getResources().getString(R.string.tekstZadatkaTestRazina);
        tekstZadatka();

    }

    @OnClick(R.id.gumbGoRazinaTest)
    public void klikNaGumbGoRazinaTest(View view){
        Intent intent = new Intent(RazinaTest.this, Zvjezdice.class);
        intent.putExtra("poruka", "sve o svemu");
        startActivity(intent);
    }

    @OnClick(R.id.drawerGumbRazinaTest)
    public void onClick(View v) {
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

    @OnClick(R.id.btnJednakoRazinaTest)
    public void klikNaBtnJednako(View view) {

        //provjeri dal su je neki GONE
        if ((editTextPrviBrojRazinaTest.getVisibility() == View.GONE) || (editTextDrugiBrojRazinaTest.getVisibility() == View.GONE)
                || (spinnerRacunskeOperacijeRazinaTest.getVisibility() == View.GONE) && (btnJednakoRazinaTest.getVisibility() == View.GONE)
                || (textViewRezultatRazinaTest.getVisibility() == View.GONE)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
            AlertDialog dialog =
                    builder.setTitle("Greška")
                            .setMessage("Nisi još povukao sve elemente, povuci sve elemente, pa onda probaj izračunati")
                            .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .show();
            dialog.findViewById(dialog.getContext().getResources()
                    .getIdentifier("android:id/titleDivider", null, null))
                    .setBackgroundColor(getResources().getColor(R.color.bijela));
        }


        //provjeri dal je unesen prvi broj
        else if (editTextPrviBrojRazinaTest.getText().toString().equals("Prvi broj") || editTextPrviBrojRazinaTest.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
            AlertDialog dialog =
                    builder.setTitle("Greška kod unosa prvog broja")
                            .setMessage("Nisi dobro unijeo prvi broj, unesi ponovo, pa onda probaj izračunati")
                            .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .show();
            dialog.findViewById(dialog.getContext().getResources()
                    .getIdentifier("android:id/titleDivider", null, null))
                    .setBackgroundColor(getResources().getColor(R.color.bijela));
        }

        //provjeri dal je unesen drugi broj
        else if(editTextDrugiBrojRazinaTest.getText().toString().equals("Drugi broj") || editTextDrugiBrojRazinaTest.getText().toString().equals("")){
            AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
            AlertDialog dialog =
                    builder.setTitle("Greška kod unosa drugog broja")
                            .setMessage("Nisi dobro unijeo drugi broj, unesi ponovo, pa onda probaj izračunati")
                            .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            })
                            .show();
            dialog.findViewById(dialog.getContext().getResources()
                    .getIdentifier("android:id/titleDivider", null, null))
                    .setBackgroundColor(getResources().getColor(R.color.bijela));
        }

        //ako sve izracunaj i zapisi rezultat
        else {

            String racunskaOperacijaString = spinnerRacunskeOperacijeRazinaTest.getSelectedItem().toString();
            float prviBrojFloat = Float.parseFloat(editTextPrviBrojRazinaTest.getText().toString());
            float drugiBrojFloat = Float.parseFloat(editTextDrugiBrojRazinaTest.getText().toString());
            float rezultat;

            DecimalFormat df = new DecimalFormat("#.##");

            if(racunskaOperacijaString.equals("+")){
                rezultat = prviBrojFloat + drugiBrojFloat;
                textViewRezultatRazinaTest.setText(df.format(rezultat));
                gumbGoRazinaTest.setVisibility(View.VISIBLE);
            }
            else if(racunskaOperacijaString.equals("-")){
                rezultat = prviBrojFloat - drugiBrojFloat;
                textViewRezultatRazinaTest.setText(df.format(rezultat));
                gumbGoRazinaTest.setVisibility(View.VISIBLE);
            }
            else if(racunskaOperacijaString.equals("*")){
                rezultat = prviBrojFloat * drugiBrojFloat;
                textViewRezultatRazinaTest.setText(df.format(rezultat));
                gumbGoRazinaTest.setVisibility(View.VISIBLE);
            }
            else if(racunskaOperacijaString.equals("/")) {
                if (drugiBrojFloat == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
                    AlertDialog dialog =
                            builder.setTitle("Greška kod unosa drugog broja")
                                    .setMessage("Ne možeš dijeliti s nulom, unesi ponovo, pa onda probaj izračunati")
                                    .setNegativeButton("Natrag", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    })
                                    .show();
                    dialog.findViewById(dialog.getContext().getResources()
                            .getIdentifier("android:id/titleDivider", null, null))
                            .setBackgroundColor(getResources().getColor(R.color.bijela));
                }
                else {
                    rezultat = prviBrojFloat / drugiBrojFloat;
                    textViewRezultatRazinaTest.setText(df.format(rezultat));
                    gumbGoRazinaTest.setVisibility(View.VISIBLE);
                }
            }
        }
    }



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

                    if(viewGumbTag.equals("slikaGumbPrviBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextPrviBrojRazinaTest")){
                        GradientDrawable okvirGradientEditTextPrviBrojRazinaTest = (GradientDrawable) okvirEditTextPrviBrojRazinaTest.getBackground();
                        okvirGradientEditTextPrviBrojRazinaTest.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }
                    else if(viewGumbTag.equals("slikaGumbDrugiBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextDrugiBrojRazinaTest")){
                        GradientDrawable okvirGradientEditTextDrugiBrojRazinaTest = (GradientDrawable) okvirEditTextDrugiBrojRazinaTest.getBackground();
                        okvirGradientEditTextDrugiBrojRazinaTest.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }
                    else if(viewGumbTag.equals("slikaGumbJednakoRazinaTest") && viewOkvirTag.equals("okvirBtnJednakoRazinaTest")){
                        GradientDrawable okvirGradientBtnJednakoRazinaTest = (GradientDrawable) okvirBtnJednakoRazinaTest.getBackground();
                        okvirGradientBtnJednakoRazinaTest.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }
                    else if(viewGumbTag.equals("slikaGumbRezultatRazinaTest") && viewOkvirTag.equals("okvirEditTextRezultatRazinaTest")){
                        GradientDrawable okvirGradientEditTextRezultatRazinaTest = (GradientDrawable) okvirEditTextRezultatRazinaTest.getBackground();
                        okvirGradientEditTextRezultatRazinaTest.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }
                    else if(viewGumbTag.equals("slikaGumbRacOperacijeRazinaTest") && viewOkvirTag.equals("okvirSpinnerRazinaTest")){
                        GradientDrawable okvirGradientSpinnerRazinaTest = (GradientDrawable) okvirSpinnerRazinaTest.getBackground();
                        okvirGradientSpinnerRazinaTest.setStroke(visinaRuba, Color.GREEN);
                        unutarOkvira = true;
                    }

                    break;


                case DragEvent.ACTION_DRAG_EXITED:

                    if(viewGumbTag.equals("slikaGumbPrviBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextPrviBrojRazinaTest")){
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientEditTextPrviBrojRazinaTest = (GradientDrawable) okvirEditTextPrviBrojRazinaTest.getBackground();
                        okvirGradientEditTextPrviBrojRazinaTest.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }
                    else if(viewGumbTag.equals("slikaGumbDrugiBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextDrugiBrojRazinaTest")){
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientEditTextDrugiBrojRazinaTest = (GradientDrawable) okvirEditTextDrugiBrojRazinaTest.getBackground();
                        okvirGradientEditTextDrugiBrojRazinaTest.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }
                    else if(viewGumbTag.equals("slikaGumbJednakoRazinaTest") && viewOkvirTag.equals("okvirBtnJednakoRazinaTest")){
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientBtnJednakoRazinaTest = (GradientDrawable) okvirBtnJednakoRazinaTest.getBackground();
                        okvirGradientBtnJednakoRazinaTest.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }
                    else if(viewGumbTag.equals("slikaGumbRezultatRazinaTest") && viewOkvirTag.equals("okvirEditTextRezultatRazinaTest")){
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientEditTextRezultatRazinaTest = (GradientDrawable) okvirEditTextRezultatRazinaTest.getBackground();
                        okvirGradientEditTextRezultatRazinaTest.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }
                    else if(viewGumbTag.equals("slikaGumbRacOperacijeRazinaTest") && viewOkvirTag.equals("okvirSpinnerRazinaTest")){
                        zacrniSveOkvire();
                        GradientDrawable okvirGradientSpinnerRazinaTest = (GradientDrawable) okvirSpinnerRazinaTest.getBackground();
                        okvirGradientSpinnerRazinaTest.setStroke(visinaRuba, Color.RED);
                        unutarOkvira = false;
                    }

                    break;


                case DragEvent.ACTION_DRAG_ENDED:

                    reportResult(event.getResult(), viewOkvirTag, viewGumbTag);
                    break;


                case DragEvent.ACTION_DROP:


                    if(viewGumbTag.equals("slikaGumbPrviBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextPrviBrojRazinaTest")){
                        okvirEditTextPrviBrojRazinaTest.setVisibility(View.GONE);
                        editTextPrviBrojRazinaTest.setVisibility(View.VISIBLE);
                        slikaGumbPrviBrojRazinaTest.setVisibility(View.GONE);
                        booleanEditTextPrviBroj = true;
                        kolkoIhJePostavljeno += 1;
                        if(kolkoIhJePostavljeno == 5) {
                            svePostavljenoZadatakDialog();
                        }
                    }

                    else if(viewGumbTag.equals("slikaGumbDrugiBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextDrugiBrojRazinaTest")){
                        okvirEditTextDrugiBrojRazinaTest.setVisibility(View.GONE);
                        editTextDrugiBrojRazinaTest.setVisibility(View.VISIBLE);
                        slikaGumbDrugiBrojRazinaTest.setVisibility(View.GONE);
                        booleanEditTextDrugiBroj = true;
                        kolkoIhJePostavljeno += 1;
                        if(kolkoIhJePostavljeno == 5) {
                            svePostavljenoZadatakDialog();
                        }
                    }

                    else if(viewGumbTag.equals("slikaGumbJednakoRazinaTest") && viewOkvirTag.equals("okvirBtnJednakoRazinaTest")){
                        okvirBtnJednakoRazinaTest.setVisibility(View.GONE);
                        btnJednakoRazinaTest.setVisibility(View.VISIBLE);
                        slikaGumbJednakoRazinaTest.setVisibility(View.GONE);
                        booleanJednako = true;
                        kolkoIhJePostavljeno += 1;
                        if(kolkoIhJePostavljeno == 5) {
                            svePostavljenoZadatakDialog();
                        }
                    }

                    else if(viewGumbTag.equals("slikaGumbRezultatRazinaTest") && viewOkvirTag.equals("okvirEditTextRezultatRazinaTest")){
                        okvirEditTextRezultatRazinaTest.setVisibility(View.GONE);
                        textViewRezultatRazinaTest.setVisibility(View.VISIBLE);
                        slikaGumbRezultatRazinaTest.setVisibility(View.GONE);
                        booleanRezultat = true;
                        kolkoIhJePostavljeno += 1;
                        if(kolkoIhJePostavljeno == 5) {
                            svePostavljenoZadatakDialog();
                        }
                    }

                    else if(viewGumbTag.equals("slikaGumbRacOperacijeRazinaTest") && viewOkvirTag.equals("okvirSpinnerRazinaTest")){
                        okvirSpinnerRazinaTest.setVisibility(View.GONE);
                        spinnerRacunskeOperacijeRazinaTest.setVisibility(View.VISIBLE);
                        slikaGumbRacOperacijeRazinaTest.setVisibility(View.GONE);
                        booleanSpinner = true;
                        kolkoIhJePostavljeno += 1;
                        if(kolkoIhJePostavljeno == 5) {
                            svePostavljenoZadatakDialog();
                        }
                    }
                    return unutarOkvira;
            }
            return true;
        }
    };


    private void reportResult(final boolean result, String viewOkvirTag, String viewGumbTag) {
        if(!result){
            zacrniSveOkvire();
        }
        else {

            if(viewGumbTag.equals("slikaGumbPrviBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextPrviBrojRazinaTest")){
                okvirEditTextPrviBrojRazinaTest.setVisibility(View.GONE);
                editTextPrviBrojRazinaTest.setVisibility(View.VISIBLE);
                slikaGumbPrviBrojRazinaTest.setVisibility(View.GONE);
            }

            else if(viewGumbTag.equals("slikaGumbDrugiBrojRazinaTest") && viewOkvirTag.equals("okvirEditTextDrugiBrojRazinaTest")){
                okvirEditTextDrugiBrojRazinaTest.setVisibility(View.GONE);
                editTextDrugiBrojRazinaTest.setVisibility(View.VISIBLE);
                slikaGumbDrugiBrojRazinaTest.setVisibility(View.GONE);
            }

            else if(viewGumbTag.equals("slikaGumbJednakoRazinaTest") && viewOkvirTag.equals("okvirBtnJednakoRazinaTest")){
                okvirBtnJednakoRazinaTest.setVisibility(View.GONE);
                btnJednakoRazinaTest.setVisibility(View.VISIBLE);
                slikaGumbJednakoRazinaTest.setVisibility(View.GONE);
            }

            else if(viewGumbTag.equals("slikaGumbRezultatRazinaTest") && viewOkvirTag.equals("okvirEditTextRezultatRazinaTest")){
                okvirEditTextRezultatRazinaTest.setVisibility(View.GONE);
                textViewRezultatRazinaTest.setVisibility(View.VISIBLE);
                slikaGumbRezultatRazinaTest.setVisibility(View.GONE);
                booleanRezultat = true;
            }

            else if(viewGumbTag.equals("slikaGumbRacOperacijeRazinaTest") && viewOkvirTag.equals("okvirSpinnerRazinaTest")){
                okvirSpinnerRazinaTest.setVisibility(View.GONE);
                spinnerRacunskeOperacijeRazinaTest.setVisibility(View.VISIBLE);
                slikaGumbRacOperacijeRazinaTest.setVisibility(View.GONE);
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

            shadow.setBounds(0, 0, 10000, 10000);

            shadowSize.set(10000, 10000);
            shadowTouchPoint.set(10000, 10000);
        }
    }

    private void zacrniSveOkvire() {

        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

        GradientDrawable okvirGradientEditTextPrviBrojRazinaTest = (GradientDrawable) okvirEditTextPrviBrojRazinaTest.getBackground();
        okvirGradientEditTextPrviBrojRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientEditTextDrugiBrojRazinaTest = (GradientDrawable) okvirEditTextDrugiBrojRazinaTest.getBackground();
        okvirGradientEditTextDrugiBrojRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientBtnJednakoRazinaTest = (GradientDrawable) okvirBtnJednakoRazinaTest.getBackground();
        okvirGradientBtnJednakoRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientEditTextRezultatRazinaTest = (GradientDrawable) okvirEditTextRezultatRazinaTest.getBackground();
        okvirGradientEditTextRezultatRazinaTest.setStroke(visinaRuba, Color.BLACK);
        GradientDrawable okvirGradientSpinnerRazinaTest = (GradientDrawable) okvirSpinnerRazinaTest.getBackground();
        okvirGradientSpinnerRazinaTest.setStroke(visinaRuba, Color.BLACK);
    }

    private void tekstZadatka(){
        AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
        AlertDialog dialog =
                builder.setTitle("Zadatak")
                        .setMessage(zadatak)
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(getResources().getColor(R.color.bijela));
    }

    private void svePostavljenoZadatakDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RazinaTest.this);
        AlertDialog dialog =
                builder.setTitle("Zadatak")
                        .setMessage("Čestitam, uspješno si povukao sve elemente!\n"
                                    + "Sada napravi neku računsku operaciju")
                        .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        })
                        .show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(getResources().getColor(R.color.bijela));
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
        drawerLayoutRazinaTest.closeDrawer(drawerListRazinaTest);
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

}
