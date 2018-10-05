/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.Interface;

import java.util.List;
import java.util.Set;
import org.sat4j.core.VecInt;
import org.sat4j.minisat.core.Solver;
import org.sat4j.specs.IVecInt;
import org.sat4j.specs.TimeoutException;

/**
 *
 * @author user
 */
public class Util {
      public static void nCk(int n, int k, Set<TSet> tsets, List<Integer> featuresList, boolean checkValid, Solver solver) throws TimeoutException {
        int[] a = new int[k];
        nCkH(n, k, 0, a, k, tsets, featuresList, checkValid, solver);
    }
    
    public static void nCkH(int n, int loopno, int ini, int[] a, int k, Set<TSet> tsets, List<Integer> featuresList, boolean checkValid, Solver solver) throws TimeoutException {
        
        if (k == 0) {
            return;
        }
        
        int i;
        loopno--;
        if (loopno < 0) {
            a[k - 1] = ini - 1;
            TSet p = new TSet();
            for (i = 0; i < k; i++) {
                p.add(featuresList.get(a[i]));
            }
            
            if (checkValid) {
                IVecInt prod = new VecInt(p.getSize());
                
                for (Integer in : p.getVals()) {
                    prod.push(in);
                }
                
                if (solver.isSatisfiable(prod) && p.getSize() == k) {
                    tsets.add(p);
                }
            } else {
		if (p.getSize() == k)
                	tsets.add(p);
            }
            return;
            
        }
        for (i = ini; i <= n - loopno - 1; i++) {
            a[k - 1 - loopno] = i;
            nCkH(n, loopno, i + 1, a, k, tsets, featuresList, checkValid, solver);
        }
        
        
    }
    
}
