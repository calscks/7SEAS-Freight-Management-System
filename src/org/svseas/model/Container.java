package org.svseas.model;

/**
 * Codes by Seong Chee Ken on 11/01/2017, 23:11. Container for array type T.
 */

import javax.xml.bind.annotation.XmlAnyElement;
import java.util.ArrayList;
import java.util.List;

public class Container<T> {
    private List<T> root;

    public Container(){
        root = new ArrayList<T>();
    }

    public void add(T t){
        root.add(t);
    }

    public T get(int index){
        return root.get(index);
    }

    public int size(){
        return root.size();
    }

    public void remove(int index){
        root.remove(index);
    }

    public void remove(T t){
        root.remove(t);
    }

    @XmlAnyElement(lax = true)

    public List<T> getRoot() {
        return root;
    }

    public void setRoot(List<T> root) {
        this.root = root;
    }

}

