package com.example.zenghui.overyearspaper.Model;

/**
 * Created by zenghui on 16/1/12.
 */
public class ChooseInfo {

    String name;
    boolean select;
    int type = 0;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
