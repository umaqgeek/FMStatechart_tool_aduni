/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.Interface;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author user
 */
public class TSet implements Serializable {

    private Set<Integer> vals;
    static final long serialVersionUID = -6618469844567325812L;

    public TSet(int[] vals) {
        this.vals = new HashSet<Integer>();
        for (Integer i : vals) {
            this.vals.add(i);
        }

    }

    public int getSize() {
        return vals.size();
    }

    public TSet() {
        this.vals = new HashSet<Integer>();
    }

    public void add(Integer i) {
        this.vals.add(i);
    }

    public Set<Integer> getVals() {
        return vals;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TSet other = (TSet) obj;

        return vals.equals(other.vals);
    }

    @Override
    public int hashCode() {
        return vals.hashCode();
    }

    @Override
    public String toString() {
        return vals.toString();
    }
}

