package tvz.mc2.codeit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


//TODO da promjeni velicinu sjene

public class RazinaJedan extends Activity {

    @InjectView(R.id.slikaGumbRazinaJedan) ImageView slikaGumbRazinaJedan;
    @InjectView(R.id.okvirGumbRazinaJedan) View okvirGumbRazinaJedan;
    @InjectView(R.id.gumbRazinaJedan) Button gumbRazinaJedan;
    @InjectView(R.id.dragDropLayoutRazinaJedan) ViewGroup dragLayoutRazinaJedan;

    HorizontalScrollView hsv;
    ImageView arrowl, arrowr;
    int maxScrollX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razina_jedan);
        ButterKnife.inject(this);
        int visinaRuba = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        GradientDrawable okvirGradientGumbaJedan = (GradientDrawable) okvirGumbRazinaJedan.getBackground();
        okvirGradientGumbaJedan.setStroke(visinaRuba, Color.BLACK);
        slikaGumbRazinaJedan.setOnTouchListener(touchListener);
        okvirGumbRazinaJedan.setOnDragListener(dragListener);
        hsv = (HorizontalScrollView) findViewById(R.id.horizontalScrollViewRazinaJedan);
        arrowl = (ImageView) findViewById(R.id.arrowlRazinaJedan);
        arrowr = (ImageView) findViewById(R.id.arrowrRazinaJedan);
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
                    dragLayoutRazinaJedan.invalidate();

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
}
