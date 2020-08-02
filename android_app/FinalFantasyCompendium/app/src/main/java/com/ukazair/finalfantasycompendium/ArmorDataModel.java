package com.ukazair.finalfantasycompendium;

public class ArmorDataModel {
    private String name, def, def_p, mdef, mdef_p, cost;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public String getDef_p() {
        return def_p;
    }

    public void setDef_p(String def_p) {
        this.def_p = def_p;
    }

    public String getMdef() {
        return mdef;
    }

    public void setMdef(String mdef) {
        this.mdef = mdef;
    }

    public String getMdef_p() {
        return mdef_p;
    }

    public void setMdef_p(String mdef_p) {
        this.mdef_p = mdef_p;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "name: " + name + '\n' +
                "def: " + def + '\n' +
                "def%: " + def_p + '\n' +
                "mdef: " + mdef + '\n' +
                "mdef%: " + mdef_p + '\n' +
                "cost: " + cost + '\n';
    }
}
