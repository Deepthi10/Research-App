package edu.odu.mra.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kahmed on 11/4/2015.
 */
public class SurveyDetails implements Serializable{


    private String id;
    private String title;
    private String crn;
    private String description;
    private String startDate;
    private String endDate;
    private String enrollStartDate;
    private String enrollEndDate;
    private String url;
    private String frequency;
    private String pi;
    private String pi_email;
    private String pi_phone;
    private String pi_dept;
    private String admins;
    private String enrolled;
    private String demographic_questions;
    private String consent_form_url;
    private String demo_que_url;
    private List<CoPiDetails> co_pi_details;
    private List<PiDetails> pi_details;

    public List<CoPiDetails> getCo_pi_details() {
        return co_pi_details;
    }

    public void setCo_pi_details(List<CoPiDetails> co_pi_details) {
        this.co_pi_details = co_pi_details;
    }

    public List<PiDetails> getPi_details() {
        return pi_details;
    }

    public void setPi_details(List<PiDetails> pi_details) {
        this.pi_details = pi_details;
    }

    public String getDemographic_questions() {
        return demographic_questions;
    }

    public void setDemographic_questions(String demographic_questions) {
        this.demographic_questions = demographic_questions;
    }

    public String getDemo_que_url() {
        return demo_que_url;
    }

    public void setDemo_que_url(String demo_que_url) {
        this.demo_que_url = demo_que_url;
    }

    public String getConsent_form_url() {
        return consent_form_url;
    }

    public void setConsent_form_url(String consent_form_url) {
        this.consent_form_url = consent_form_url;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEnrollStartDate() {
        return enrollStartDate;
    }

    public void setEnrollStartDate(String enrollStartDate) {
        this.enrollStartDate = enrollStartDate;
    }

    public String getEnrollEndDate() {
        return enrollEndDate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public void setEnrollEndDate(String enrollEndDate) {
        this.enrollEndDate = enrollEndDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

    public String getPi_email() {
        return pi_email;
    }

    public void setPi_email(String pi_email) {
        this.pi_email = pi_email;
    }

    public String getPi_phone() {
        return pi_phone;
    }

    public void setPi_phone(String pi_phone) {
        this.pi_phone = pi_phone;
    }

    public String getPi_dept() {
        return pi_dept;
    }

    public void setPi_dept(String pi_dept) {
        this.pi_dept = pi_dept;
    }

    public String getAdmins() {
        return admins;
    }

    public void setAdmins(String admins) {
        this.admins = admins;
    }

    public String getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(String enrolled) {
        this.enrolled = enrolled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCrn() {
        return crn;
    }

    public void setCrn(String crn) {
        this.crn = crn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
