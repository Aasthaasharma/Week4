package com.example.exercise3.model;

import java.util.List;

public class Classification {
    private String name;
    private String type;
    private String className;
    private String image;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }


    @Override
    public String toString() {
        return "Classification{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", className='" + className + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
