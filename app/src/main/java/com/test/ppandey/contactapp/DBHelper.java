package com.test.ppandey.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ppandey on 12/15/2015.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contactdb.sqlite";
    public static final String CONTACTS_TABLE_NAME = "mycontacts";
    public static final String CONTACTS_COLUMN_ID  = "id";
    public static final String CONTACTS_COLUMN_STUNAME = "name";
    public static final String CONTACTS_COLUMN_STUPHONE = "phone";
    public static final String CONTACTS_COLUMN_STUSTREET = "street";
    public static final String CONTACTS_COLUMN_STUEMAIL = "email";
    public static final String CONTACTS_COLUMN_STUCITY = "place";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table mycontacts " +
                        "(id integer primary key autoincrement, name text,phone text,email text, street text,place text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS mycontacts");
        onCreate(db);
    }
    public boolean addStudentContact(String contactname,String contactphone,String contactstreet,String contactemail, String contactplace){
        /*,*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("name",contactname);
        contantValues.put("phone", contactphone);
        contantValues.put("street",contactstreet);
        contantValues.put("email",contactemail);
        contantValues.put("place",contactplace);
        db.insert("mycontacts", null, contantValues);
        db.close();
        return true;
    }
    public boolean updateStudentContact(Integer contactid,String contactname,String contactphone,String contactstreet,String contactemail, String contactplace)
    {
        /*,String contactname,*/
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contantValues = new ContentValues();
        contantValues.put("name",contactname);
        contantValues.put("phone", contactphone);
        contantValues.put("street",contactstreet);
        contantValues.put("email",contactemail);
        contantValues.put("place",contactplace);
        db.update("mycontacts", contantValues, "id = ?", new String[]{Integer.toString(contactid)});
        db.close();
        return true;
    }
    public Integer deleteContact(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete("mycontacts","id = ?",new String[]{Integer.toString(id)});
    }
    public Cursor getData(int contactid){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select * from mycontacts where id = " + contactid + "", null);
        return res;
    }
    public int numberOfRows(){
        SQLiteDatabase db=this.getWritableDatabase();
        int numRows=(int) DatabaseUtils.queryNumEntries(db,CONTACTS_TABLE_NAME);
        return numRows;
    }
    public ArrayList<String> getAllStudentContacts(){
        ArrayList<String> arraylist= new ArrayList<String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from mycontacts",null);

        if (cursor.moveToFirst()) {
            do {
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUNAME)));
                /*arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUPHONE)));
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUSTREET)));
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUEMAIL)));
                arraylist.add(cursor.getString(cursor.getColumnIndex(CONTACTS_COLUMN_STUCITY)));*/
            } while (cursor.moveToNext());
        }
        return arraylist;
    }
}
