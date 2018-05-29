package com.spaikergrn.vkclient.db.models;

import com.spaikergrn.vkclient.db.annotations.dbString;
import com.spaikergrn.vkclient.db.annotations.dbTable;
import com.spaikergrn.vkclient.serviceclasses.Constants;

@dbTable(Constants.USERS_DB)
public final class UsersDb {

    @dbString
    public static final String FIRST_NAME = Constants.Parser.FIRST_NAME;

    @dbString
    public static final String LAST_NAME = Constants.Parser.LAST_NAME;

    @dbString
    public static final String PHOTO_50 = Constants.Parser.PHOTO_50;

    @dbString
    public static final String PHOTO_100 = Constants.Parser.PHOTO_100;

    @dbString
    public static final String DEACTIVATED = Constants.Parser.DEACTIVATED;
}
