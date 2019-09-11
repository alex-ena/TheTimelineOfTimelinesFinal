package enacica.alex.thetimelineoftimelines.data;

import android.provider.BaseColumns;

public class DbContract {
    public static final class EventEntry implements BaseColumns{

        public static final String TABLE_NAME = "events";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_START = "start";
        public static final String COLUMN_END = "end";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_SOURCE = "source";
        public static final String COLUMN_SOURCE_URL = "source_url";
        public static final String COLUMN_ADDITIONAL_RESOURCES = "additional_resources";
        public static final String COLUMN_YEAR = "year";

    }
}
