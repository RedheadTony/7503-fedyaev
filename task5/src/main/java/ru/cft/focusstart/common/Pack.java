package ru.cft.focusstart.common;

import java.io.Serializable;

public class Pack implements Serializable {
    private String type;
    private String content;

    public Pack(String type, String content) {
        this.type = type;
        this.content = content;
    }


    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
