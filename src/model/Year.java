package model;

/**
 * Created by Ph3ncyclidine on 10/31/16.
 */
public enum Year {
    Y2016("2016"),
    Y2017("2017"),
    Y2018("2018"),
    Y2019("2019");

    private String year;

    /**
     * Create a new year
     * @param year the year to choose
     */
    Year(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return year;
    }
}
