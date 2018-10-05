/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.Interface;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.sat4j.specs.TimeoutException;

/**
 *
 * @author user
 */
public class Product extends HashSet<Integer> implements Serializable {

    /* Relative coverage of this product. This value depends on the number of pairs 
     * covered by the previous products when we evaluate the coverage of a set of 
     * products.     */
    private double coverage;
    static final long serialVersionUID = -6618469841127325812L;

    /**
     * Create a product.
     */
    public Product() {
        super();
        coverage = 0;
    }

    public Set<TSet> getCoveredPairs() throws TimeoutException {

        List<Integer> pl = new ArrayList<Integer>(this);
        int size = size();
        Set<TSet> pairs = new HashSet<TSet>(size * (size - 1) / 2);
        Util.nCk(size, 2, pairs, pl, false, null);
        return pairs;
    }

    
}

