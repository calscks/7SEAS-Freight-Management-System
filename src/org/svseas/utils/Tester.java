package org.svseas.utils;

/**
 * Coded by Seong Chee Ken on 15/01/2017, 13:10.
 */
public enum Tester {
    TEST_BUTTON("Button tested"),
    TEST_FIELD("Field tested"),
    SUCCESS("Succeeded"),
    SUCCESS_MATCH("Match successful"),
    SUCCESS_READ("Read successful"),
    FAIL("Failed"),
    FAIL_MATCH("Match failed"),
    FAIL_READ("Read failed"),;


    private String string;

    Tester(String string){
        this.string = string;
    }

    public void printer(){
        System.out.println(string);
    }
}
