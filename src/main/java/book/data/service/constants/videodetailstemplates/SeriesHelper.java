package book.data.service.constants.videodetailstemplates;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class SeriesHelper {

    public static Series getSeriesFromString(String seriesString) {
        if (seriesString.equalsIgnoreCase(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY.toString())) {
            return Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY;
        }
        else if (seriesString.equalsIgnoreCase(Series.GENIUS_MAGE_TAKES_MEDICINE.toString())) {
            return Series.GENIUS_MAGE_TAKES_MEDICINE;
        }
        else if (seriesString.equalsIgnoreCase(Series.REGRESSION_OF_MAX_LEVEL_PLAYER.toString())) {
            return Series.REGRESSION_OF_MAX_LEVEL_PLAYER;
        }
        else if (seriesString.equalsIgnoreCase(Series.TEST_SERIES.toString())) {
            return Series.TEST_SERIES;
        }
        return null;
    }

    public static List<Series> getSeriesList() {
        //List<Series> seriesList = ImmutableList.of(Series.SURVIVING_AS_A_MAGE_IN_MAGIC_ACADEMY,
        //    Series.GENIUS_MAGE_TAKES_MEDICINE, Series.REGRESSION_OF_MAX_LEVEL_PLAYER);
        List<Series> seriesList = ImmutableList.of(Series.TEST_SERIES);
        return seriesList;
    }

}
