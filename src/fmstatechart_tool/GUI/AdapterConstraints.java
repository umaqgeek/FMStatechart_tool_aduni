/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fmstatechart_tool.GUI;

import javax.swing.AbstractListModel;
import fmstatechart_tool.Interface.Model;

/**
 *
 * @author user
 */
public class AdapterConstraints extends AbstractListModel {

    private Model model;

    public AdapterConstraints(Model model) {
        this.model = model;
    }

    @Override
    public int getSize() {
        return model.getFeatureModelConstraintsString().size();
    }

    @Override
    public Object getElementAt(int index) {
        return model.getFeatureModelConstraintsString().get(index);

    }

    public void update() {
        fireContentsChanged(this, 0, getSize() - 1);
    }
}
