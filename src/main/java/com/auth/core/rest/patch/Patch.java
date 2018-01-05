package com.auth.core.rest.patch;

public class Patch {

    private PatchType patchType;
    private String field;
    private String value;

    public PatchType getPatchType() {
        return patchType;
    }

    public void setPatchType(PatchType patchType) {
        this.patchType = patchType;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
