package com.hartakarun;

import java.util.jar.Attributes.Name;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class AccountData {
	private static final String DATABASE_NAME = "account.db";

	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_NAME = "member";
	private Context context;

	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT = "insert into "

	+ TABLE_NAME + "(username) values (?)";

	public AccountData(Context context) {
		this.context = context;

		OpenHelper openHelper = new OpenHelper(this.context);

		this.db = openHelper.getWritableDatabase();

		this.insertStmt = this.db.compileStatement(INSERT);

	}

	public long insert(String name) {
		this.insertStmt.bindString(1, name);
		return this.insertStmt.executeInsert();
	}
	

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE " + TABLE_NAME
					+ "(id INTEGER PRIMARY KEY, username TEXT)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Example",
					"Upgrading database, this will drop tables and recreate.");
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

}
