package com.aviom.aviomplay.Models;

/**
 * Created by SOS on 3/2/2017.
 */

public class Interaction {
    int Id;
    int status;
    long leadid;
    String remarks;

    public int getPlanned_by() {
        return planned_by;
    }

    public void setPlanned_by(int planned_by) {
        this.planned_by = planned_by;
    }

    int planned_by;

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    String created_on;
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public long getLeadid() {
        return leadid;
    }

    public void setLeadid(long leadid) {
        this.leadid = leadid;
    }



    public String getPlannedon() {
        return plannedon;
    }

    public void setPlannedon(String plannedon) {
        this.plannedon = plannedon;
    }

    String plannedon;
}
