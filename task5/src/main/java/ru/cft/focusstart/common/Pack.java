package ru.cft.focusstart.common;

import java.io.Serializable;

public class Pack implements Serializable {
    private PackType type;
    private String content;

    public Pack(PackType type, String content) {
        this.type = type;
        this.content = content;
    }


    public PackType getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
