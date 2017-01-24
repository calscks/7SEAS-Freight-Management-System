package org.svseas.operations;

import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.utils.Tester;

/**
 * Coded by Seong Chee Ken on 23/01/2017, 23:55.
 */
@SuppressWarnings("unchecked")
public abstract class Operation {

    public <T> boolean create(T type, XMLOperation xmlops, DataFile df){
        if (!DataFile.analyse(df)) {
            ObjectList<T> list = new ObjectList<>();
            list.add(type);
            xmlops.write(df, list);
            Tester.SUCCESS.printer();
            return true;
        }
        ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
        if (list.size() == 0){
            list.add(type);
            xmlops.write(df, list);
            Tester.SUCCESS.printer();
            return true;
        }
        for (T types : list.getList()) {
            if (!types.equals(type)) {
                list.add(type);
                xmlops.write(df, list);
                Tester.SUCCESS.printer();
                return true;
            }
        }
        Tester.FAIL.printer();
        return false;
    }

    public <T> ObjectList<T> read(DataFile df, XMLOperation xmlops){
        if (DataFile.analyse(df)) {
            ObjectList<T> list = (ObjectList<T>) xmlops.read(df);
            Tester.SUCCESS_READ.printer();
            return list;
        } else Tester.FAIL_READ.printer();
        return null;
    }

    public abstract void update(String match);

    public abstract boolean delete(String match);
}
