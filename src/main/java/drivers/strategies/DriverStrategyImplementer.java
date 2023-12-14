package drivers.strategies;

import utils.Constants;

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
