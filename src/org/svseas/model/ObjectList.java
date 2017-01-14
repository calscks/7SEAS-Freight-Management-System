package org.svseas.model;

/**
 * Codes by Seong Chee Ken on 11/01/2017, 23:11. ObjectList for arraylist type T.
 */

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

public class ObjectList<T> {
    private List<T> list;

    public ObjectList(){
        list = new ArrayList<>();
    }

    public void add(T t){
        list.add(t);
    }

    public T get(int index){
        return list.get(index);
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

