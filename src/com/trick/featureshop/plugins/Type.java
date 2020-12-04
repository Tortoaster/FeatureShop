package com.trick.featureshop.plugins; 

public enum  Type {

    FILE ,  HISTORY ,  PLUGIN; 

    @Override
    public String toString() {
        switch (this) {
            case FILE: return "File";
            case HISTORY: return "History";
            default: return "Plugins";
        }
    }}
