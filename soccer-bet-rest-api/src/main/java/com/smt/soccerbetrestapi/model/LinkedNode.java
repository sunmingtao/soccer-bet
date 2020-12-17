package com.smt.soccerbetrestapi.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
@Data
public class LinkedNode<T> {
    @JsonIgnore
    private T prev;
    @JsonIgnore
    private T next;

}
