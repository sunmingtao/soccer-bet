package com.smt.soccerbetrestapi.model;


import lombok.Data;
@Data
public class LinkedNode<T> {
    private T prev;
    private T next;

}
