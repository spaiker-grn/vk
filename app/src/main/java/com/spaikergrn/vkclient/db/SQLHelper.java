package com.spaikergrn.vk_client.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import com.spaikergrn.vk_client.db.annotations.dbInteger;
import com.spaikergrn.vk_client.db.annotations.dbLong;
import com.spaikergrn.vk_client.db.annotations.dbString;
import com.spaikergrn.vk_client.db.annotations.dbTable;
import com.spaikergrn.vk_client.db.models.DbModels;
import com.spaikergrn.vk_client.serviceclasses.Constants;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class SQLHelper extends SQLiteOpenHelper {


    private static final String TAG = SQLHelper.class.getSimpleName();

    private static final String NAME = Constants.VK_CLIENT_DB;

    private static final int VERSION = 1;

    public SQLHelper(final Context pContext) {
        super(pContext, NAME, null, VERSION);
        Log.d(Constants.MY_TAG, "Constructor called with: db = []");
    }

    @Override
    public void onCreate(final SQLiteDatabase pDb) {
        Log.d(TAG, "onCreate() called with: db = [" + pDb + "]");
        createTables(pDb, DbModels.DB_MODELS);
    }

    @Override
    public void onUpgrade(final SQLiteDatabase pDb, final int pOldVersion, final int pNewVersion) {
        Log.d("SqlConnector", "onUpgrade from " + pOldVersion + " to " + pNewVersion);
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
                    Log.d(Constants.MY_TAG, "createTables table name: " + dbTableName);
                    final StringBuilder stringBuilder = new StringBuilder();

                    final Field[] fields = tableClass.getFields();

                    for (final Field field : fields) {
                        final Annotation[] fieldAnnotations = field.getAnnotations();
                        final String fieldName = (String) field.get(null);

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
                            if (!TextUtils.isEmpty(fieldType)) {
                                stringBuilder.append(fieldName).append(" ").append(fieldType).append(",");
                            }
                            Log.d(Constants.MY_TAG, "createTables table name: " + fieldName + " " + fieldType);
                        }
                    }
                    Log.d(Constants.MY_TAG, "createTables: " + stringBuilder);
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                    final String tableCreteQuery = String.format(Constants.TABLE_TEMPLATE, dbTableName, BaseColumns._ID, stringBuilder.toString());
                    pReadableConnection.execSQL(tableCreteQuery);
                    Log.d(TAG, "createTables() returned: " + tableCreteQuery);
                }
            }
            pReadableConnection.setTransactionSuccessful();
        } catch (final Exception pE) {
            Log.e(TAG, "create table exception", pE);
        } finally {
            pReadableConnection.endTransaction();
        }
    }
}
