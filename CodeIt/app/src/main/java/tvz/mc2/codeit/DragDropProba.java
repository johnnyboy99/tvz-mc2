package tvz.mc2.codeit;

import android.app.Activity;
import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;


public class DragDropProba extends Activity {

    @InjectView(R.id.slika) ImageView slika;
    @InjectView(R.id.slika2) ImageView slika2;
    @InjectView(R.id.tekst) TextView tekst;
    @InjectView(R.id.rectangle) View rectangle;
    @InjectView(R.id.rectangle2) View rectangle2;
    @InjectView(R.id.buttonProba) Button gumbNovi;
    @InjectView(R.id.buttonProba2) Button gumbNovi2;
    @InjectView(R.id.dragDropLayout) ViewGroup grupa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop_proba);
        ButterKnife.inject(this);
        slika.setOnLongClickListener(longListener);
        slika2.setOnLongClickListener(longListener);
        rectangle.setOnDragListener(dragListener);
        rectangle2.setOnDragListener(dragListener);
        //slika.setOnDragListener(dragListener);
    }

    @OnClick(R.id.buttonProba)
    public void klikNaProbu(View v){
        Toast.makeText(getApplicationContext(), "klikkkk", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.buttonProba2)
    public void klikNaProbu2(View v){
        Toast.makeText(getApplicationContext(), "klik222", Toast.LENGTH_SHORT).show();
    }

    View.OnLongClickListener longListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {

            String kojiGumb = v.getTag().toString();

            if(kojiGumb.equals("gumb1")){

                //Toast.makeText(getApplicationContext(), "gumb1111", Toast.LENGTH_SHORT).show();
                //TextView fruit = (TextView) v;
                ImageView fruit = (ImageView) v;
                //Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(DragDropProba.this, "Text long clicked - " + fruit.getText(), Toast.LENGTH_SHORT).show();

                //View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(v);

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);


                ClipData data = ClipData.newPlainText("", "");
                v.startDrag(data, shadowBuilder, fruit, 0);
            }

            else if(kojiGumb.equals("gumb2")) {

                //Toast.makeText(getApplicationContext(), "gumb222", Toast.LENGTH_SHORT).show();
                //TextView fruit = (TextView) v;
                ImageView fruit = (ImageView) v;
                //Toast.makeText(DragDropProba.this, "Text long clicked - " + fruit.getText(), Toast.LENGTH_SHORT).show();

                //View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(v);

                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);


                ClipData data = ClipData.newPlainText("", "");
                v.startDrag(data, shadowBuilder, fruit, 0);
            }

            return true;
        }

    };

    View.OnDragListener dragListener = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            //Toast.makeText(getApplicationContext(), v.getTag().toString(), Toast.LENGTH_SHORT).show();
            int dragEvent = event.getAction();
            String viewRectangleTag = v.getTag().toString();

            //Toast.makeText(getApplicationContext(), viewRectangleTag, Toast.LENGTH_SHORT).show();

            View viewGumb = (View) event.getLocalState();
            String viewGumbTag = viewGumb.getTag().toString();


            //TextView dropText = (TextView) v;

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_ENTERED:

                    //Toast.makeText(getApplicationContext(), viewRectangleTag, Toast.LENGTH_SHORT).show();
                    if(viewGumbTag.equals("gumb1") && viewRectangleTag.equals("rectangle1")){
                        GradientDrawable bgRectangle = (GradientDrawable)rectangle.getBackground();
                        //bgRectangle.setStroke(2, Color.GREEN);
                        GradientDrawable bgRectangle2 = (GradientDrawable)rectangle2.getBackground();
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                        bgRectangle2.setStroke(height, Color.BLACK);
                    }
                    else if(viewGumbTag.equals("gumb2") && viewRectangleTag.equals("rectangle2")){
                        Toast.makeText(getApplicationContext(), "tuuuu", Toast.LENGTH_SHORT).show();
                        GradientDrawable bgRectangle2 = (GradientDrawable)rectangle2.getBackground();
                        bgRectangle2.setStroke(2, Color.GREEN);
                    }
                    tekst.setTextColor(Color.GREEN);

                    //dropText.setTextColor(Color.GREEN);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    if(viewGumbTag.equals("gumb1") && viewRectangleTag.equals("rectangle1")){
                        GradientDrawable bgRectangle = (GradientDrawable)rectangle.getBackground();
                        bgRectangle.setStroke(2, Color.RED);
                        GradientDrawable bgRectangle2 = (GradientDrawable)rectangle2.getBackground();
                        bgRectangle2.setStroke(2, Color.BLACK);
                    }
                    else if(viewGumbTag.equals("gumb2") && viewRectangleTag.equals("rectangle2")){
                        GradientDrawable bgRectangle2 = (GradientDrawable)rectangle2.getBackground();
                        bgRectangle2.setStroke(2, Color.RED);
                        GradientDrawable bgRectangle = (GradientDrawable)rectangle.getBackground();
                        bgRectangle.setStroke(2, Color.BLACK);
                    }
                    tekst.setTextColor(Color.RED);

                    //dropText.setTextColor(Color.RED);
                    break;

                case DragEvent.ACTION_DROP:
                    //TextView draggedText = (TextView)event.getLocalState();
                   // dropText.setText("promjena");
                    //rectangle.setBackgroundResource(R.drawable.cro);

                    if(viewGumbTag.equals("gumb1") && viewRectangleTag.equals("rectangle1")){
                        rectangle.setVisibility(View.GONE);
                        gumbNovi.setVisibility(View.VISIBLE);
                        GradientDrawable bgRectangle2 = (GradientDrawable)rectangle2.getBackground();
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
                        bgRectangle2.setStroke(height, Color.BLACK);
                        //slika.getLayoutParams().height = 100;
                        //slika.getLayoutParams().width = 100;
                        //final float scale = getResources().getDisplayMetrics().density;
                        //int pixels = (int) (100 * scale + 0.5f);

                        //TODO koristit ovo za pretvaranje px u dp
                        int height2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(height2, height2);
                        slika.setLayoutParams(layoutParams);
                        slika.requestLayout();
                        grupa.invalidate();
                    }
                    else if(viewGumbTag.equals("gumb2") && viewRectangleTag.equals("rectangle2")){
                        rectangle2.setVisibility(View.GONE);
                        gumbNovi2.setVisibility(View.VISIBLE);
                        GradientDrawable bgRectangle = (GradientDrawable)rectangle.getBackground();
                        bgRectangle.setStroke(2, Color.BLACK);
                        //slika.getLayoutParams().height = 100;
                        //slika.getLayoutParams().width = 100;
                        //final float scale = getResources().getDisplayMetrics().density;
                        //int pixels = (int) (100 * scale + 0.5f);

                        //TODO koristit ovo za pretvaranje px u dp
                        int height2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, getResources().getDisplayMetrics());


                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(height2, height2);
                        slika2.setLayoutParams(layoutParams);
                        slika2.requestLayout();
                        grupa.invalidate();
                    }


                    break;
            }

            return true;
        }

    };

    private class MyShadowBuilder extends View.DragShadowBuilder
    {
        private Drawable shadow;

        public MyShadowBuilder(View v)
        {
            super(v);
            shadow = new ColorDrawable(Color.LTGRAY);
        }

        @Override
        public void onDrawShadow(Canvas canvas)
        {
            shadow.draw(canvas);
        }

        @Override
        public void onProvideShadowMetrics(Point shadowSize, Point shadowTouchPoint)
        {
            int height, width;
            height = (int) getView().getHeight();
            width = (int) getView().getHeight();

            shadow.setBounds(0, 0, width, height);

            shadowSize.set(width, height);
            shadowTouchPoint.set(width, height);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drag_drop_proba, menu);
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
