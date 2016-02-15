package com.gmail.mosoft521.jmtpdp.ch08activeobject.example;

import java.io.Serializable;

public class Attachment implements Serializable {
    private static final long serialVersionUID = -313285270497968496L;
    private String contentType;
    private byte[] content = new byte[0];

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Attachment [contentType=" + contentType + ", content=" + content.length + "]";
    }
}