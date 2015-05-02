package tvz.mc2.codeit;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
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
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RazinaJedan extends Activity implements AdapterView.OnItemClickListener {

    private String [] izborArray;
    boolean zastavica;

    @InjectView(R.id.slikaGumbRazinaJedan) ImageView slikaGumbRazinaJedan;
    @InjectView(R.id.okvirGumbRazinaJedan) View okvirGumbRazinaJedan;
    @InjectView(R.id.gumbRazinaJedan) Button gumbRazinaJedan;
    @InjectView(R.id.arrowLRazinaJedan) ImageView arrowl;
    @InjectView(R.id.arrowRRazinaJedan) ImageView arrowr;
    @InjectView(R.id.drawerListRazinaJedan) ListView drawerListRazinaJedan;
    @InjectView(R.id.drawerLayoutRazinaJedan) DrawerLayout drawerLayoutRazinaJedan;
    @InjectView(R.id.menuElementiRazinaJedan) RelativeLayout menuElementiRazinaJedan;
    @InjectView(R.id.radnaPlohaRazinaJedan) LinearLayout radnaPlohaRazinaJedan;
    @InjectView(R.id.prazanKrugZaMenuDrawerPlohuRazinaJedan) View prazanKrugZaMenuDrawerPlohuRazinaJedan;
    @InjectView(R.id.menuRazinaJedan) LinearLayout menuRazinaJedan;
    @InjectView(R.id.drawerGumbRazinaJedan) ImageButton drawerGumbRazinaJedan;
    @InjectView(R.id.krugZaElementeRazinaJedan) View krugZaElementeRazinaJedan;
    @InjectView(R.id.krugZaRadnuPlohuRazinaJedan) View krugZaRadnuPlohuRazinaJedan;
    @InjectView(R.id.okvirPrividniGumbRazinaJedan) View okvirPrividniGumbRazinaJedan;
    @InjectView(R.id.opisniTekstUzAnimaciju) TextView opisniTekstUzAnimaciju;
    @InjectView(R.id.gumb_go) ImageButton gumb_go;

    Handler handler = new Handler();

    private static int VRIJEME_ANIMACIJE = 3000;
    private static int VRIJEME_ANIMACIJE_DUZE = 4000;
    private static int VRIJEME_MJENJANJA_BOJA = 2000;
    private static int CEKANJE_ZA_PRVU_ANIMACIJU = 1000;
    private static int CEKANJE_NA_ANIMACIJU_KRATKO = 500;

    Animation fadeIn;
    Animation fadeOut;
    Animation fadeInDuzi;

    Integer tamnoSiva;
    Integer svjetloSiva;
    Integer tamnoSivaAnimacija;
    Integer svjetloSivaAnimacija;
    Integer sivaPozadina;
    Integer crvenaPozadina;
    Integer crvenaPozadinaSvjetlije;
    Integer plava;
    Integer zelena;
    Integer ljubicasta;
    Integer bijela;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_jedan);
        ButterKnife.inject(this);

        /**
         * Inicijalno postavljanje boje na okvire.
         */
        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbRazinaJedan.getBackground();
        okvirGradientGumbaJedan.setStroke(visinaRuba, Color.BLACK);

        /**
         * Inicijalizacija drawer menu-a.
         */
        izborArray = getResources().getStringArray(R.array.izbor);
        View header = View.inflate(this, R.layout.list_header, null);
        drawerListRazinaJedan.addHeaderView(header, "Header", false);
        drawerListRazinaJedan.setAdapter(new ArrayAdapter<>(this, R.layout.unsimple_list_item, izborArray));
        drawerListRazinaJedan.setOnItemClickListener(this);

        /**
         * Postavljanje boja.
         */
        tamnoSiva = getResources().getColor(R.color.tamnoSiva);
        svjetloSiva = getResources().getColor(R.color.svjetloSiva);
        tamnoSivaAnimacija = getResources().getColor(R.color.tamnoSivaAnimacija);
        svjetloSivaAnimacija = getResources().getColor(R.color.svjetloSivaAnimacija);
        sivaPozadina = getResources().getColor(R.color.sivaPozadina);
        crvenaPozadina = getResources().getColor(R.color.crvenaPozadina);
        crvenaPozadinaSvjetlije = getResources().getColor(R.color.crvenaPozadinaSvjetlije);
        plava = getResources().getColor(R.color.plava);
        zelena = getResources().getColor(R.color.zelena);
        ljubicasta = getResources().getColor(R.color.ljubicasta);
        bijela = getResources().getColor(R.color.bijela);

        /**
         * Postavljanje vidljivosti strelica.
         */
        arrowl.setVisibility(View.VISIBLE);
        arrowr.setVisibility(View.VISIBLE);

        zastavica = false;

        /**
         * Start prve animacije.
         */
        prvaAnimacijaSveUSivo();
    }

    /**
     * Touch listener za slike iz izbornika.
     */
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                ImageView gumbViewRazinaJedan = (ImageView) view;
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                ClipData data = ClipData.newPlainText("", "");
                view.startDrag(data, shadowBuilder, gumbViewRazinaJedan, 0);
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
                    slikaGumbRazinaJedan.setVisibility(View.GONE);
                    zastavica = true;
                    prikazDrugePoruke();
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
            shadow.setBounds(0, 0, 10000, 10000);
            shadowSize.set(10000, 10000);
            shadowTouchPoint.set(10000, 10000);
        }
    }

    /**
     * Pozivanje dijaloga s prvim tekstom zadatka.
     */
    public void pozivZadatka() {
        arrowl.setVisibility(View.GONE);
        arrowr.setVisibility(View.GONE);

        AlertDialog.Builder builder = new AlertDialog.Builder(RazinaJedan.this);
        AlertDialog dialog =
        builder.setTitle(R.string.nas1)
                .setMessage(R.string.zad)
                .setPositiveButton(R.string.poz, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(bijela);
    }

    /**
     * Pozivanje dijaloga s drugim tekstom zadatka.
     */
    public void prikazDrugePoruke() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RazinaJedan.this);
        AlertDialog dialog =
                builder.setTitle(R.string.nas1)
                        .setMessage(R.string.zad2)
                        .setPositiveButton(R.string.poz, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                
                            }
                        })
                        .show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(bijela);

    }

    /**
     * Pozivanje dialoga za ponovno pokretanje zadatka.
     */
    public void dialogRestart()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ponovno pokretanje");
        builder.setMessage("Želiš li ponovno pokrenuti razinu?");

        builder.setPositiveButton(R.string.poz2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                restart();
            }
        })
                .setNegativeButton(R.string.neg2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //ništa
                    }
                });
        AlertDialog dialog = builder.show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(bijela);
    }

    /**
     * Pozivanje dialoga za izlazak iz zadatka.
     */
    public void dialogIzlaz()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Izlaz");
        builder.setMessage("Želiš li izaći na glavni izbornik?");

        builder.setPositiveButton(R.string.poz2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                izlaz();
            }
        })
                .setNegativeButton(R.string.neg2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        //ništa
                    }
                });
        AlertDialog dialog = builder.show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(bijela);
    }

    /**
     * Pozivanje dijaloga za promjenu boje teksta. Odabirom elementa liste mijenja se boja.
     */
    @OnClick(R.id.gumbRazinaJedan)
    public void klikNaGumbRazinaJedan() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RazinaJedan.this);
        AlertDialog dialog =
        builder.setTitle(R.string.nas2)
                .setItems(R.array.dialog_boje, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                gumbRazinaJedan.setBackgroundColor(crvenaPozadina);
                                gumbRazinaJedan.setTextColor(bijela);
                                break;
                            case 1:
                                gumbRazinaJedan.setBackgroundColor(plava);
                                gumbRazinaJedan.setTextColor(bijela);
                                break;
                            case 2:
                                gumbRazinaJedan.setBackgroundColor(zelena);
                                gumbRazinaJedan.setTextColor(bijela);
                                break;
                            case 3:
                                gumbRazinaJedan.setBackgroundColor(ljubicasta);
                                gumbRazinaJedan.setTextColor(bijela);
                                break;
                        }
                        gumb_go.setVisibility(View.VISIBLE);
                    }
                })
                .setNegativeButton(R.string.neg, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .show();
        dialog.findViewById(dialog.getContext().getResources()
                .getIdentifier("android:id/titleDivider", null, null))
                .setBackgroundColor(bijela);
    }

    /**
     * OnClick metoda na gumbu za zavrsetak razine.
     */
    @OnClick(R.id.gumb_go)
    public void onGoClick() {
        Intent intent = new Intent(RazinaJedan.this, Zvjezdice.class);
        intent.putExtra("poruka", getResources().getString(R.string.mess));
        intent.putExtra("razina", getResources().getString(R.string.mess2));
        RazinaJedan.this.finish();
        startActivity(intent);
    }

    /**
     * OnClik metoda za opcije iz drawera.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position)
        {
            case 1:
                if (!zastavica) pozivZadatka();
                else prikazDrugePoruke();
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
        selectItem(position);
        drawerLayoutRazinaJedan.closeDrawer(drawerListRazinaJedan);
    }

    /**
     * Metoda koja govori koja je opcija iz drawera odabrana.
     */
    public void selectItem(int position) {
        drawerListRazinaJedan.setItemChecked(position, true);
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
        Intent intent = new Intent(RazinaJedan.this, GlavniIzbornik.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    /**
     * Pokretanje prve animacije, postavljanje svih elemenata u sivo.
     */
    private void prvaAnimacijaSveUSivo(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //cekaj prije rendera na sivu
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                fadeIn.setDuration(1000);

                menuElementiRazinaJedan.setBackgroundColor(tamnoSivaAnimacija);
                drawerGumbRazinaJedan.setBackgroundColor(tamnoSivaAnimacija);
                menuRazinaJedan.setBackgroundColor(tamnoSivaAnimacija);
                radnaPlohaRazinaJedan.setBackgroundColor(svjetloSivaAnimacija);
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
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                drugaAnimacijaDrawerMenu();
            }
        });
    }

    /**
     * Pokretanje druge animacije, prikazivanje drawer menu-a.
     */
    private void drugaAnimacijaDrawerMenu(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                fadeIn.setDuration(VRIJEME_ANIMACIJE);

                prazanKrugZaMenuDrawerPlohuRazinaJedan.setVisibility(View.VISIBLE);
                drawerGumbRazinaJedan.setBackgroundColor(crvenaPozadina);
                menuRazinaJedan.setBackgroundColor(crvenaPozadinaSvjetlije);

                opisniTekstUzAnimaciju.setText(getResources().getString(R.string.tekstZaDrawerMenu));

                opisniTekstUzAnimaciju.setAnimation(fadeIn);
                prazanKrugZaMenuDrawerPlohuRazinaJedan.setAnimation(fadeIn);
                drawerGumbRazinaJedan.setAnimation(fadeIn);
                menuRazinaJedan.setAnimation(fadeIn);

            }
        }, CEKANJE_ZA_PRVU_ANIMACIJU);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                trecaAnimacijaMenuElementi();
            }
        });
    }

    /**
     * Pokretanje druge animacije, prikazivanje izbornika elemenata.
     */
    private void trecaAnimacijaMenuElementi(){

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        fadeInDuzi= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                fadeIn.setDuration(VRIJEME_ANIMACIJE);
                fadeInDuzi.setDuration(VRIJEME_ANIMACIJE_DUZE);
                fadeOut.setDuration(CEKANJE_NA_ANIMACIJU_KRATKO);

                ValueAnimator colorAnimationTamnoSivaUCrvenu = ValueAnimator.ofObject(new ArgbEvaluator(), tamnoSivaAnimacija, crvenaPozadina);
                colorAnimationTamnoSivaUCrvenu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationTamnoSivaUCrvenu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        menuElementiRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }
                });
                colorAnimationTamnoSivaUCrvenu.start();

                ValueAnimator colorAnimationCrvenaUTamnoSivu = ValueAnimator.ofObject(new ArgbEvaluator(), crvenaPozadina, tamnoSivaAnimacija);
                colorAnimationCrvenaUTamnoSivu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationCrvenaUTamnoSivu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        drawerGumbRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                        menuRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }
                });
                colorAnimationCrvenaUTamnoSivu.start();




                opisniTekstUzAnimaciju.setText(getResources().getString(R.string.tekstZaIzbornikElemenata));
                opisniTekstUzAnimaciju.setAnimation(fadeInDuzi);

                prazanKrugZaMenuDrawerPlohuRazinaJedan.setVisibility(View.GONE);
                krugZaElementeRazinaJedan.setVisibility(View.VISIBLE);

                //animacije
                prazanKrugZaMenuDrawerPlohuRazinaJedan.setAnimation(fadeOut);
                krugZaElementeRazinaJedan.setAnimation(fadeIn);

            }
        }, CEKANJE_ZA_PRVU_ANIMACIJU);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cetvrtaAnimacijaRadnaPovrsina();
            }
        });
    }

    /**
     * Pokretanje treće animacije, prikazivanje radne plohe.
     */
    private void cetvrtaAnimacijaRadnaPovrsina() {

        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                fadeIn.setDuration(VRIJEME_ANIMACIJE);
                fadeOut.setDuration(CEKANJE_NA_ANIMACIJU_KRATKO);

                int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

                GradientDrawable okvirGradientPrividniGumb = (GradientDrawable) okvirPrividniGumbRazinaJedan.getBackground();
                okvirGradientPrividniGumb.setStroke(visinaRuba, Color.WHITE);

                ValueAnimator colorAnimationCrvenaUTamnoSivu = ValueAnimator.ofObject(new ArgbEvaluator(), crvenaPozadina, tamnoSivaAnimacija);
                colorAnimationCrvenaUTamnoSivu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationCrvenaUTamnoSivu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        menuElementiRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }
                });
                colorAnimationCrvenaUTamnoSivu.start();

                ValueAnimator colorAnimationTamnoSivaUSivuPozadinu = ValueAnimator.ofObject(new ArgbEvaluator(), svjetloSivaAnimacija, sivaPozadina);
                colorAnimationTamnoSivaUSivuPozadinu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationTamnoSivaUSivuPozadinu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        krugZaRadnuPlohuRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }
                });
                colorAnimationTamnoSivaUSivuPozadinu.start();

                opisniTekstUzAnimaciju.setText(getResources().getString(R.string.tekstZaRadnuPlohu));
                opisniTekstUzAnimaciju.setAnimation(fadeIn);

                krugZaElementeRazinaJedan.setVisibility(View.GONE);
                krugZaRadnuPlohuRazinaJedan.setVisibility(View.VISIBLE);
                okvirPrividniGumbRazinaJedan.setVisibility(View.VISIBLE);

                //animacije
                krugZaElementeRazinaJedan.setAnimation(fadeOut);
                okvirPrividniGumbRazinaJedan.setAnimation(fadeIn);

            }
        }, CEKANJE_ZA_PRVU_ANIMACIJU);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                petaAnimacijaStartAplikacije();
            }
        });
    }

    /**
     * Pokretanje četvrte animacije, vraćanje boja i pokretanje zadatka.
     */
    private void petaAnimacijaStartAplikacije() {

        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        //prva animacija, cekaj vrijeme prije pokretanja prve animacije
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {

                fadeOut.setDuration(CEKANJE_NA_ANIMACIJU_KRATKO);

                ValueAnimator colorAnimationTamnoSivaUCrvenuPozadinu = ValueAnimator.ofObject(new ArgbEvaluator(), tamnoSivaAnimacija, crvenaPozadina);
                colorAnimationTamnoSivaUCrvenuPozadinu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationTamnoSivaUCrvenuPozadinu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        menuRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                        menuElementiRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                        drawerGumbRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }

                });
                colorAnimationTamnoSivaUCrvenuPozadinu.start();

                ValueAnimator colorAnimationSvjetloSivaUSivuPozadinu = ValueAnimator.ofObject(new ArgbEvaluator(), svjetloSivaAnimacija, sivaPozadina);
                colorAnimationSvjetloSivaUSivuPozadinu.setDuration(VRIJEME_MJENJANJA_BOJA);
                colorAnimationSvjetloSivaUSivuPozadinu.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        radnaPlohaRazinaJedan.setBackgroundColor((Integer)animator.getAnimatedValue());
                    }

                });
                colorAnimationSvjetloSivaUSivuPozadinu.start();

                opisniTekstUzAnimaciju.setText("");
                opisniTekstUzAnimaciju.setAnimation(fadeIn);

                krugZaRadnuPlohuRazinaJedan.setVisibility(View.GONE);
                okvirPrividniGumbRazinaJedan.setVisibility(View.GONE);

                //animacije
                krugZaRadnuPlohuRazinaJedan.setAnimation(fadeOut);
                okvirPrividniGumbRazinaJedan.setAnimation(fadeOut);

            }
        }, CEKANJE_ZA_PRVU_ANIMACIJU);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                slikaGumbRazinaJedan.setOnTouchListener(touchListener);
                okvirGumbRazinaJedan.setOnDragListener(dragListener);

                drawerGumbRazinaJedan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drawerLayoutRazinaJedan.openDrawer(drawerListRazinaJedan);
                    }
                });

                pozivZadatka();
            }
        });
    }
}
