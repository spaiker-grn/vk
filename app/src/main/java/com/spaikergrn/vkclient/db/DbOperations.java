package com.spaikergrn.vkclient.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.spaikergrn.vkclient.serviceclasses.Constants;

public class DbOperations {

    private final SQLiteOpenHelper mHelper;

    public DbOperations(final SQLiteOpenHelper pHelper) {
        mHelper = pHelper;
    }

    public int insert(final String pTable, final ContentValues pValues) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();
        long id = 0;

        database.beginTransaction();

        try {
            id = database.insert(pTable, Constants.Parser.EMPTY_STRING, pValues);

            database.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        } finally {
            database.endTransaction();
        }
        return (int) id;
    }

    public int bulkInsert(final String pTable, final ContentValues[] pValues) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();
        int inserted = 0;

        database.beginTransaction();

        try {
            for (final ContentValues value : pValues) {
                database.insert(pTable, Constants.Parser.EMPTY_STRING, value);

                inserted++;
            }

            database.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        } finally {
            database.endTransaction();
        }
        return inserted;
    }

    public Cursor query(final String pTable, @Nullable final String pSelection,
                        @Nullable final String[] pSelectionArgs, @Nullable final String pSortOrder) {
        return mHelper.getReadableDatabase().query(pTable, null, pSelection, pSelectionArgs, null, null, pSortOrder);
    }

    public int delete(final String pTable, @Nullable final String pSelection, @Nullable final String[] pSelectionArgs) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();
        int count = 0;

        database.beginTransaction();

        try {
            count = database.delete(pTable, pSelection, pSelectionArgs);

            database.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        } finally {
            database.endTransaction();
        }
        return count;
    }

    public int update(@NonNull final String pTable, @Nullable final ContentValues pValues,
                      @Nullable final String pSelection, @Nullable final String[] pSelectionArgs) {
        final SQLiteDatabase database = mHelper.getWritableDatabase();
        int count = 0;

        database.beginTransaction();

        try {
            count = database.update(pTable, pValues, pSelection, pSelectionArgs);

            database.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(Constants.ERROR, pE.getMessage(), pE.initCause(pE.getCause()));
        } finally {
            database.endTransaction();
        }
        return count;
    }
}
