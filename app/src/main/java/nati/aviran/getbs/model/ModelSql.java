package nati.aviran.getbs.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by aviran.nati on 01/07/2017.
 */

public class ModelSql extends SQLiteOpenHelper {
    ModelSql(Context context) {
        super(context, "database.db", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        BabySitterSql.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        BabySitterSql.onUpgrade(db, oldVersion, newVersion);
    }

}
