package org.svseas.model;

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Coded by Seong Chee Ken on 11/01/2017, 23:11. ObjectList for arraylist type T.
 */

public class ObjectList<T> {
    private List<T> list;

    public ObjectList(){
        list = new ArrayList<>();
    }

    public void add(T t){
        list.add(t);
    }

    public void add(int index, T t) {
        list.add(index, t);
    }

    public T get(int index){
        return list.get(index);
    }

    public T getLast() {
        return list.get(list.size() - 1);
    }

    public int size(){
        return list.size();
    }

    public void remove(T t){
        list.remove(t);
    }

    public void remove(int index){
        list.remove(index);
    }

    @XmlAnyElement(lax = true)

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}

