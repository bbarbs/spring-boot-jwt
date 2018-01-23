package com.auth.core.rest.patch;

public class Patch {

    private PatchEnum patchEnum;
    private String field;
    private String value;

    public PatchEnum getPatchEnum() {
        return patchEnum;
    }

    public void setPatchEnum(PatchEnum patchEnum) {
        this.patchEnum = patchEnum;
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
