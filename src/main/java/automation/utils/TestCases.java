package automation.utils;

public enum TestCases {
    T1("Testing the authentication"),
    T2("Testing the checkout process");

    private String testName;

    TestCases(String value){
        this.testName = value;
    }

    public String getTestName() {
        return testName;
    }
}
