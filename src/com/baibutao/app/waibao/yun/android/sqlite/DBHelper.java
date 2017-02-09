
package com.baibutao.app.waibao.yun.android.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author niepeng
 *
 * @date 2012-9-15 ����11:40:59
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context) {
		// ���ݿ�Ĭ�ϱ����ڣ� <��ǰ��>/databases/
		super(context, "huijuNew.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table person(id integer primary key autoincrement, name)");
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("alter table person add phone null");
	}

}
