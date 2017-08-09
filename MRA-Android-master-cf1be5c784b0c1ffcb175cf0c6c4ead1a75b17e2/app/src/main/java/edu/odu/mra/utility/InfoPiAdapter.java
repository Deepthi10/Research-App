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
import edu.odu.mra.models.PiDetails;

/**
 * Created by Kevin Racheal on 5/20/2016.
 */
public class InfoPiAdapter extends BaseAdapter {
    private Context contextl;
    private List<PiDetails>pi_details;
    public InfoPiAdapter(InfoActivity infoActivity, List<PiDetails> pi_details) {
        this.contextl=infoActivity;
        this.pi_details=pi_details;

    }

    @Override
    public int getCount() {
        return pi_details.size();
    }

    @Override
    public Object getItem(int position) {
        return pi_details.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PiDetails pidata=pi_details.get(position);
        LayoutInflater inflater = (LayoutInflater) contextl.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowVieww = inflater.inflate(R.layout.pidetailslayout, null);
         TextView tv3 = (TextView) rowVieww.findViewById(R.id.piname);
        final TextView tv4 = (TextView) rowVieww.findViewById(R.id.piphone);
        TextView tv5 = (TextView) rowVieww.findViewById(R.id.pidept);
        TextView piemail=(TextView)rowVieww.findViewById(R.id.piemail);

        tv3.setText(pidata.getPi());
        tv4.setText(pidata.getPi_phone());
        tv5.setText(pidata.getPi_dept());
        piemail.setText(pidata.getPi_email());
        System.out.println(" Copi details: " + pidata.getPi());

        return rowVieww;
    }
}
