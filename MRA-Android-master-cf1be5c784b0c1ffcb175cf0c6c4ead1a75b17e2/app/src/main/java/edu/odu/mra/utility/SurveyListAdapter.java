package edu.odu.mra.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import edu.odu.mra.R;
import edu.odu.mra.models.SurveyDetails;

/**
 * Created by kahmed on 11/2/2015.
 */
public class SurveyListAdapter extends BaseAdapter {
    private String TAG = "SurveyListAdapter";
    private final Context context;
    private final List<SurveyDetails> values;

    public SurveyListAdapter(Context context, List<SurveyDetails> values) {
        this.context = context;
        this.values = values;
    }

    public int getCount() {
        return values.size();
    }

    public SurveyDetails getItem(int position) {
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SurveyDetails data = values.get(position);
        //Log.v(TAG, position + data.getTitle() + " " + data.getSurveyEnrollment());
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.surveys_list, null);

        TextView tvSurveyTitle = (TextView) rowView.findViewById(R.id.tvSurveyListTitle);
        TextView tvSurveyEnrollment = (TextView) rowView.findViewById(R.id.tvSurveyListEnrollment);
        TextView tvSurveyEnrollmentStatus = (TextView) rowView.findViewById(R.id.tvSurveyListEnrollmentStatus);

        tvSurveyTitle.setText(data.getTitle());
        tvSurveyEnrollment.setText("Enrollment is allowed between "+data.getEnrollStartDate()+" and "+data.getEnrollEndDate());

        System.out.println(data.getEnrolled());
        //fix
        if(data.getEnrolled().equals("Yes")) {
            tvSurveyEnrollmentStatus.setText("Enrolled");
            tvSurveyEnrollmentStatus.setVisibility(View.VISIBLE);
            tvSurveyEnrollment.setText("Survey is between "+data.getStartDate()+" and "+data.getEndDate());

        }

        else{
            tvSurveyEnrollmentStatus.setVisibility(View.INVISIBLE);
            tvSurveyEnrollmentStatus.setVisibility(View.GONE);
        }
        return rowView;
    }


}
