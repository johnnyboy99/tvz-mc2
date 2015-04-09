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
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


public class DragDropProba extends Activity {

    @InjectView(R.id.slika) ImageView slika;
    @InjectView(R.id.tekst) TextView tekst;
    @InjectView(R.id.rectangle) View rectangle;
    @InjectView(R.id.buttonProba) Button gumbNovi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_drop_proba);
        ButterKnife.inject(this);
        slika.setOnLongClickListener(longListener);
        rectangle.setOnDragListener(dragListener);
        //slika.setOnDragListener(dragListener);
    }

    View.OnLongClickListener longListener = new View.OnLongClickListener()
    {
        @Override
        public boolean onLongClick(View v)
        {
            //TextView fruit = (TextView) v;
            ImageView fruit = (ImageView) v;
            //Toast.makeText(DragDropProba.this, "Text long clicked - " + fruit.getText(), Toast.LENGTH_SHORT).show();

            //View.DragShadowBuilder myShadowBuilder = new MyShadowBuilder(v);
            v.getLayoutParams().height = 100;
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);


            ClipData data = ClipData.newPlainText("", "");
            v.startDrag(data, shadowBuilder, fruit, 0);

            return true;
        }

    };

    View.OnDragListener dragListener = new View.OnDragListener()
    {
        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            int dragEvent = event.getAction();
            //TextView dropText = (TextView) v;

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    tekst.setTextColor(Color.GREEN);
                    GradientDrawable bgRectangle = (GradientDrawable)rectangle.getBackground();
                    bgRectangle.setStroke(2, Color.BLACK);
                    //dropText.setTextColor(Color.GREEN);
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    tekst.setTextColor(Color.RED);
                    GradientDrawable bgRectangle1 = (GradientDrawable)rectangle.getBackground();
                    bgRectangle1.setStroke(2, Color.GREEN);
                    //dropText.setTextColor(Color.RED);
                    break;

                case DragEvent.ACTION_DROP:
                    //TextView draggedText = (TextView)event.getLocalState();
                   // dropText.setText("promjena");
                    //rectangle.setBackgroundResource(R.drawable.cro);
                    rectangle.setVisibility(View.GONE);
                    gumbNovi.setVisibility(View.VISIBLE);
                    //tekst.setText("promjena");
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
            height = (int) getView().getHeight()/2;
            width = (int) getView().getHeight()/2;

            shadow.setBounds(0, 0, width, height);

            shadowSize.set(width, height);
            shadowTouchPoint.set(width/2, height/2);
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
