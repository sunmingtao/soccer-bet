package com.smt.soccerbetrestapi.utils;

import com.smt.soccerbetrestapi.model.LinkedNode;

import java.util.ArrayList;

public class DoublyLinkedList<T extends LinkedNode> extends ArrayList<T>{

    @Override
    public boolean add(T t) {
        super.add(t);
        if (super.size() >= 2) {
            T lastNode = super.get(super.size() - 2);
            lastNode.setNext(t);
            t.setPrev(lastNode);
        }
        return true;
    }

}
