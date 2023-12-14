package automation.drivers.strategies;

import automation.utils.Constants;

public class DriverStrategyImplementer {
    public static DriverStategy chooseStrategy (String driver) {
        switch(driver){
            case Constants.CHROME:
                return new Chrome();

            case Constants.FIREFOX:
                return new Firefox();

            default:
                return null;
        }
    }
}
