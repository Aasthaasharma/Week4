package com.example.exercise3.model;

import java.time.LocalDate;

public class Decision {
    private String id;
    private String reference;
    private String judgmentDate;
    private String level;
    private String nature;
    private String robotSource;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getJudgmentDate() { return judgmentDate; }
    public void setJudgmentDate(String judgmentDate) { this.judgmentDate = judgmentDate; }

    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }

    public String getNature() { return nature; }
    public void setNature(String nature) { this.nature = nature; }

    public String getRobotSource() { return robotSource; }
    public void setRobotSource(String robotSource) { this.robotSource = robotSource; }

    @Override
    public String toString() {
        return "Decision{" +
                "id='" + id + '\'' +
                ", reference='" + reference + '\'' +
                ", judgmentDate='" + judgmentDate + '\'' +
                ", level='" + level + '\'' +
                ", nature='" + nature + '\'' +
                ", robotSource='" + robotSource + '\'' +
                '}';
    }
}
