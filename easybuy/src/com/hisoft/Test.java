package com.hisoft;

import com.hisoft.entity.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
//            iterator.remove();
            list.remove(next);
        }
    }
}
