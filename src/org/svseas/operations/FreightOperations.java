package org.svseas.operations;

import javafx.concurrent.Task;
import org.svseas.data.DataFile;
import org.svseas.model.ObjectList;
import org.svseas.model.freight.Freight;
import org.svseas.utils.Tester;

import java.util.Objects;

/**
 * Coded by Seong Chee Ken on 25/01/2017, 12:13.
 */
@SuppressWarnings("unchecked")

//TODO: Show during presentation - AGGREGATION

public class FreightOperations extends Operation {

    private XMLOperation xmlops;
    private DataFile df = DataFile.FREIGHT;
    private Freight freight;

    public FreightOperations(Freight freight) {
        this.freight = freight;
        xmlops = new XMLOperation(ObjectList.class, Freight.class);
    }

    public FreightOperations() {
        xmlops = new XMLOperation(ObjectList.class, Freight.class);
    }

    public boolean create() {
        return super.create(freight, xmlops, df);
    }

    public ObjectList<Freight> read(){
        return super.read(df, xmlops);
    }

    @Override
    public void update(String book_id) {
        ObjectList<Freight> freightList = (ObjectList<Freight>) xmlops.read(df);
        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Freight freights : freightList.getList()) {
                    if (Objects.equals(freights.getBookingId(), book_id)) {
                        int index = freightList.getList().indexOf(freights);
                        freightList.remove(freights);
                        freightList.add(index, freight);
                        xmlops.write(df, freightList);
                    }
                }
                return null;
            }
        }.run();
        Tester.SUCCESS.printer();
    }

    //TODO: Show during presentation - CONCURRENCY (MINOR)

    @Override
    public boolean delete(String book_id) {
        ObjectList<Freight> freightList = (ObjectList<Freight>) xmlops.read(df);
        new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                for (Freight freights : freightList.getList()) {
                    if (Objects.equals(freights.getBookingId(), book_id)) {
                        freightList.remove(freights);
                        xmlops.write(df, freightList);
                    }
                }
                return null;
            }
        }.run();
        return true;
    }
}
