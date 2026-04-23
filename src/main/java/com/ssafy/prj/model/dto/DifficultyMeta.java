package com.ssafy.prj.model.dto;


public class DifficultyMeta {

    private String id;
    private String color;
    private String label;
    private String desc;

    public DifficultyMeta(String id, String color, String label, String desc) {
        this.id    = id;
        this.color = color;
        this.label = label;
        this.desc  = desc;
    }

    public String getId()    { return id; }
    public String getColor() { return color; }
    public String getLabel() { return label; }
    public String getDesc()  { return desc; }
}