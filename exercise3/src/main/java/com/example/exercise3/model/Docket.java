package com.example.exercise3.model;

public class Docket {
    private String id;
    private String reference;
    private String courtId;
    private String judge;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getCourtId() { return courtId; }
    public void setCourtId(String courtId) { this.courtId = courtId; }

    public String getJudge() { return judge; }
    public void setJudge(String judge) { this.judge = judge; }

    @Override
    public String toString() {
        return "Docket{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", courtId='" + courtId + '\'' +
                ", judge='" + judge + '\'' +
                '}';
    }
}
