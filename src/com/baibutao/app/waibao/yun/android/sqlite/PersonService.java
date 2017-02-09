/**
 * 
 */
package com.baibutao.app.waibao.yun.android.sqlite;

import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.baibutao.app.waibao.yun.android.biz.dataobject.Person;
import com.baibutao.app.waibao.yun.android.util.CollectionUtil;

/**
 * @author niepeng
 *
 * @date 2012-9-15 ����12:51:48
 */
public class PersonService {
	
	private DBHelper dbHelper;
	
	public PersonService(Context context) {
		dbHelper = new DBHelper(context);
	}
	
	public void save(Person person) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("insert into person(name,phone) values(?,?)", new Object[]{person.getName(), person.getPhone()});
		// ���ֻ��һ���ط�ʹ�ã��Ͳ���Ҫ�ر�
//		db.close();
		
		/*ContentValues values = new ContentValues();
		values.put("name", meetingDO.getTitle());
		values.put("phone", meetingDO.getCity());
		
		db.insert("person", "phone", values);*/
	}
	
	public void delete(long id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("delete from person where id = ? ", new Object[]{id});
//		db.delete("person", "id = ?", new String[]{String.valueOf(id)});
	}
	
	public void update(Person person) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.execSQL("update person set name = ? , phone= ? where id=?", new Object[]{person.getName(), person.getPhone(), person.getId()});
	}
	
	public Person find(long id) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from person where id= ? ", new String[] { String.valueOf(id) });
		if (cursor.moveToFirst()) {
			long idd = cursor.getLong(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			Person person = new Person();
			person.setId(idd);
			person.setName(name);
			person.setPhone(phone);
			return person;
		}
		cursor.close();
		return null;
	}
	
	public List<Person> getList(int offset, int pageSize) {
		List<Person> personList = CollectionUtil.newArrayList();
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select * from person  order by id desc limit ?,?", new String[]{String.valueOf(offset), String.valueOf(pageSize)});
		while (cursor.moveToNext()) {
			long idd = cursor.getLong(cursor.getColumnIndex("id"));
			String name = cursor.getString(cursor.getColumnIndex("name"));
			String phone = cursor.getString(cursor.getColumnIndex("phone"));
			Person person = new Person();
			person.setId(idd);
			person.setName(name);
			person.setPhone(phone);
			personList.add(person);
		}
		cursor.close();
		return personList;
	}
	
	public long getCount() {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from person ",null);
		cursor.moveToFirst();
		long result = cursor.getLong(0);
		cursor.close();
		return result;
	}
	
	public void pay() {
		// ������ύ��ع���������ı�Ǿ����ģ�true:�ύ��false:�ع��� Ĭ����false
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		try {
			db.beginTransaction();
			db.setTransactionSuccessful();
			/**
			 * �м�n��sqlִ��
			 */
		} finally {
			db.endTransaction();
		}
	}
	

}
