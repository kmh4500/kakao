package com.example.kakao.db;

import android.provider.BaseColumns;

public class RoomContract {
	 // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public RoomContract() {}

    /* Inner class that defines the table contents */
    public static abstract class RoomEntry implements BaseColumns {
        public static final String TABLE_NAME = "RoomTable";
        public static final String COLUMN_NAME_PROFILE_ID = "ProfileID";
        public static final String COLUMN_NAME_ROOM_NAME = "RoomName";
        public static final String COLUMN_NAME_ROOM_ID = "RoomID";
    }
}
