package tvz.mc2.codeit;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * Koristi se u klasi Razine. Služi za punjenje ExpandableListView-a podacima.
 * @author Kristina Marinić
 * @date 2015-04-18
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    /** Application environment */
    private Context kontekst;
    /** Lista u koju se spremaju nazivi razina. */
    private List<String> listaRazina;
    /** Pozicija unutar liste označava razinu, a vrijednost na poziciji je lista podrazine. */
    private List<List<String>> listaPodrazina;

    /**
     *  Konstruktor.
     * @param context app environment
     * @param listDataHeader lista razina
     * @param listChildData lista podrazina
     */
    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 List<List<String>> listChildData) {
        this.kontekst = context;
        this.listaRazina = listDataHeader;
        this.listaPodrazina = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listaPodrazina.get(groupPosition)
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.kontekst
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.podrazine);

        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listaPodrazina.get(groupPosition)
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listaRazina.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listaRazina.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.kontekst
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.razine);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
