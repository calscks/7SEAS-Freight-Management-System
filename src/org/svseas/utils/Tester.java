package org.svseas.utils;

/**
 * Codes by Seong Chee Ken on 15/01/2017, 13:10.
 */
public enum Tester {
    TEST_BUTTON("Button tested"),
    TEST_FIELD("Field tested"),
    SUCCESS("Succeeded"),
    FAIL("Failed");


    private String string;

    Tester(String string){
        this.string = string;
    }

    public void printer(){
        System.out.println(string);
    }
}
