package edu.odu.mra.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkallepalli on 3/18/2016.
 */
public class SurveyListResponse {

    List<SurveyDetails> surveys=new ArrayList<SurveyDetails>();

    public List<SurveyDetails> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<SurveyDetails> surveys) {
        this.surveys = surveys;
    }
}
