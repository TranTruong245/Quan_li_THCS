package com.example.demo.model;

public class Class {
    private int classID;
    private String className;
    private int size;
    private String session;

    public Class(int classID, String className, int size, String session) {
        this.classID = classID;
        this.className = className;
        this.size = size;
        this.session = session;
    }
    public Class(){};
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }
}
