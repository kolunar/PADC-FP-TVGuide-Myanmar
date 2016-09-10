package com.padc.tvguide.data.vos;

/**
 * Created by user on 9/10/2016.
 */
public class ChannelVO {

    private int id;

    private String name;

    private String desc;

    private String icon;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ChannelVO(int id, String name, String icon) {
        this.id = id;
        this.name = name;
        this.icon = icon;
    }
}
