package com.example.cacau2.ecovoicetest;

import com.google.gson.annotations.Expose;

/**
 * Created by Cacau2 on 23/06/2018.
 */

public class TreePOST {
    @Expose
    TreeForm tree;

    public TreePOST(TreeForm tree ){
        this.tree = tree;
    }

}
