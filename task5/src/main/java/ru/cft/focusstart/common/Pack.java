package ru.cft.focusstart.common;

import java.io.Serializable;

public class Pack implements Serializable {
    private PackTypes type;
    private String content;

    public Pack(PackTypes type, String content) {
        this.type = type;
        this.content = content;
    }


    public PackTypes getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
