package com.example.exercise3.model;

public class Right {
    private String id;
    private boolean opponent;
    private Classification classification;
    private String name;
    private String type;
    private String reference;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public boolean isOpponent() { return opponent; }
    public void setOpponent(boolean opponent) { this.opponent = opponent; }

    public Classification getClassification() { return classification; }
    public void setClassification(Classification classification) { this.classification = classification; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference;}

    @Override
    public String toString() {
        return "Right{" +
                "id='" + id + '\'' +
                ", opponent=" + opponent +
                ", classification=" + classification +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}
