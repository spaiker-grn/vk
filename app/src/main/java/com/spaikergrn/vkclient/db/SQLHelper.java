package com.spaikergrn.vkclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.spaikergrn.vkclient.db.annotations.dbInteger;
import com.spaikergrn.vkclient.db.annotations.dbLong;
import com.spaikergrn.vkclient.db.annotations.dbString;
import com.spaikergrn.vkclient.db.annotations.dbTable;
import com.spaikergrn.vkclient.db.models.DbModels;
import com.spaikergrn.vkclient.serviceclasses.Constants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SQLHelper extends SQLiteOpenHelper {

    private static final String TAG = SQLHelper.class.getSimpleName();

    private static final String NAME = Constants.VK_CLIENT_DB;

    private static final int VERSION = 1;

    public SQLHelper(final Context pContext) {
        super(pContext, NAME, null, VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase pDb) {
        createTables(pDb, DbModels.DB_MODELS);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase pDb, final int pOldVersion, final int pNewVersion) {
    }

    private void createTables(final SQLiteDatabase pReadableConnection, final Class<?>[] pTableClassArray) {

        pReadableConnection.beginTransaction();
        try {
            for (final Class<?> tableClass : pTableClassArray) {
                final dbTable dbTableAnnotation = tableClass.getAnnotation(dbTable.class);
                if (dbTableAnnotation != null) {
                    final String dbTableName = dbTableAnnotation.value();
                    if (TextUtils.isEmpty(dbTableName)) {
                        return;
                    }
                    final StringBuilder stringBuilder = new StringBuilder();

                    final Field[] fields = tableClass.getFields();
                    for (final Field field : fields) {
                        final Annotation[] fieldAnnotations = field.getAnnotations();
                        String fieldName = null;
                        if((field.get(null) instanceof String)) {
                            fieldName = (String) field.get(null);
                        }
                        String fieldType = null;
                        for (final Annotation fieldAnnotation : fieldAnnotations) {
                            final Class<?> fieldAnnotationType = fieldAnnotation.annotationType();
                            if (fieldAnnotationType.equals(dbString.class)) {
                                fieldType = ((dbString) fieldAnnotation).value();
                            } else if (fieldAnnotationType.equals(dbLong.class)) {
                                fieldType = ((dbLong) fieldAnnotation).value();
                            } else if (fieldAnnotationType.equals(dbInteger.class)) {
                                fieldType = ((dbInteger) fieldAnnotation).value();
                            }
                            if (!TextUtils.isEmpty(fieldType) && !TextUtils.isEmpty(fieldName)) {
                                stringBuilder.append(fieldName).append(" ").append(fieldType).append(",");
                            }
                        }
                    }
                    stringBuilder.deleteCharAt((stringBuilder.length()) - 1);
                    final String tableCreteQuery = String.format(Constants.TABLE_TEMPLATE, dbTableName, BaseColumns._ID, stringBuilder.toString());
                    pReadableConnection.execSQL(tableCreteQuery);
                }
            }
            pReadableConnection.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(TAG, pE.getMessage(), pE.getCause());
        } finally {
            pReadableConnection.endTransaction();
        }
    }
}
