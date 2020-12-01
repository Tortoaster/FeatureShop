package com.trick.featureshop.plugins;

public enum Type {

    FILE, HISTORY, PLUGIN;

    @Override
    public String toString() {
        return switch (this) {
            case FILE -> "File";
            case HISTORY -> "History";
            case PLUGIN -> "Plugins";
        };
    }
}
