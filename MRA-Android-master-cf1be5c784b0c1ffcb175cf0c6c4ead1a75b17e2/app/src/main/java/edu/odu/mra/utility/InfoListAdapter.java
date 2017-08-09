package edu.odu.mra.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.odu.mra.InfoActivity;
import edu.odu.mra.R;
import edu.odu.mra.models.CoPiDetails;

/**
 * Created by Kevin Racheal on 5/19/2016.
 */
public class InfoListAdapter extends BaseAdapter {
    private  Context context;
    private  List<CoPiDetails> copiDetails;

    public InfoListAdapter(InfoActivity infoActivity, List<CoPiDetails> copiDetails) {
        this.context=infoActivity;
        this.copiDetails=copiDetails;
    }

    @Override
    public int getCount() {
        return copiDetails.size();
    }

    @Override
    public Object getItem(int position) {
        return copiDetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CoPiDetails data=copiDetails.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.copidetailslayout, null);

        TextView tv6 = (TextView) rowView.findViewById(R.id.copiname);
        TextView tv7 = (TextView) rowView.findViewById(R.id.copiphone);
        TextView tv8 = (TextView) rowView.findViewById(R.id.copidept);
        TextView tvemail=(TextView)rowView.findViewById(R.id.copiemail);

        tv6.setText(data.getCo_pi());
        tv7.setText(data.getCo_pi_phone());
        tv8.setText(data.getCo_pi_dept());
        tvemail.setText(data.getCo_pi_email());

        System.out.println(" Copi details: " + data.getCo_pi());

        return rowView;
    }
}
