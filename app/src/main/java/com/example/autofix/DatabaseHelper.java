package com.example.autofix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {


    final static String DATABASE_NAME = "AutoFixApp.db";
    final static int DATABASE_VERSION = 2;
    final static String USER_TABLE = "user";

    final static String APPOINTMENT_TABLE = "appointment";
    final static String SERVICE_TABLE = "service";

    final static String SERV_PROVIDER_TABLE = "ServiceProvider";

    final static String ID = "id";
    final static String USERNAME = "username";
    final static String PASSWORD = "password";
    final static String FULLNAME = "fullname";
    final static String EMAIL = "email";
    final static String PHONE = "phone";
    final static String USER_TYPE = "type";
    final static String ADDRESS = "address";
    final static String CITY = "city";

    final static String APPOINTMENT_ID = "appointment_id";
    final static String USER_ID = "user_id";
    final static String PROVIDER_ID ="provider_id";
    final static String SERVICE1_ID = "service1_id";
    final static String DATE = "date";
    final static String TIME = "time";
    final static String APP_TYPE = "app_type";
    //final static String STATUS = "status";

    final static String SERVICE_ID = "service_id";
    final static String SERVICE_NAME = "name";
    final static String SERVICE_PROVIDER_ID = "provider_id";
    final static String SERVICE_PRICE = "price";
    final static String SERVICE_DESC = "description";



    final static String PROVIDERID = "ProviderID";
    final static String PROVIDERNAME = "ServProviderName";
    final static String PROV_PHONE = "PhoneNumber";
    final static String PROV_CITY = "City";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + USER_TABLE + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERNAME + " TEXT UNIQUE,"
                + PASSWORD + " TEXT,"
                + FULLNAME + " TEXT,"
                + EMAIL + " TEXT,"
                + PHONE + " TEXT,"
                + ADDRESS + " TEXT,"
                + CITY + " TEXT,"
                + USER_TYPE + " INTEGER,"
                + PROVIDERID + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(query);
        query ="CREATE TABLE " + SERVICE_TABLE + "("
                + SERVICE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SERVICE_NAME + " TEXT,"
                + SERVICE_PROVIDER_ID + " INTEGER,"
                + SERVICE_PRICE + " TEXT,"
                + SERVICE_DESC + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(query);
        query ="CREATE TABLE " + APPOINTMENT_TABLE + "("
                + APPOINTMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_ID + " INTEGER,"
                + PROVIDER_ID + " INTEGER,"
                + SERVICE1_ID + " INTEGER,"
                + DATE + " DATE,"
                + TIME + " TEXT,"
                + APP_TYPE + " INTEGER"
                + ")";
        sqLiteDatabase.execSQL(query);
        query = "CREATE TABLE " + SERV_PROVIDER_TABLE + "("
                + PROVIDER_ID + " INTEGER PRIMARY KEY,"
                + PROVIDERNAME + " TEXT,"
                + PROV_PHONE + " TEXT,"
                +PROV_CITY + " TEXT)";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SERVICE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + APPOINTMENT_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String uName, String uPass, String fName, String email, String phNum, String uType, String address, String city, int provID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,uName);
        values.put(PASSWORD, uPass);
        values.put(FULLNAME, fName);
        values.put(EMAIL, email);
        values.put(PHONE, phNum);
        values.put(ADDRESS, address);
        values.put(CITY, city);
        values.put(USER_TYPE,uType);
        values.put(PROVIDERID,provID);
        long l = sqLiteDatabase.insert(USER_TABLE,null,values);
        if(l>0)
            return true;
        else
            return false;
    }
    public boolean addService(String sName, int sProviderId, String sPrice, String sDesc){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SERVICE_NAME, sName);
        values.put(SERVICE_PROVIDER_ID, sProviderId);
        values.put(SERVICE_PRICE,sPrice);
        values.put(SERVICE_DESC,sDesc);
        long l = sqLiteDatabase.insert(SERVICE_TABLE,null,values);
        if(l>0)
            return true;
        else
            return false;
    }
    public boolean addAppointment(int userId, int providerId, int serviceId, String date, String time, String aType){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID,userId);
        values.put(PROVIDER_ID, providerId);
        values.put(SERVICE1_ID, serviceId);
        values.put(DATE, date);
        values.put(TIME, time);
        values.put(APP_TYPE,aType);
        long l =sqLiteDatabase.insert(APPOINTMENT_TABLE,null,values);
        if(l>0)
            return true;
        else
            return false;

    }
//    public boolean anAppointment(int userId, int providerId, int serviceId, String date, String time, String aType){
//        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(USER_ID,userId);
//        values.put(PROVIDER_ID, providerId);
//        values.put(SERVICE1_ID, serviceId);
//        values.put(DATE, date);
//        values.put(TIME, time);
//        values.put(APP_TYPE,aType);
//        long l =sqLiteDatabase.update(APPOINTMENT_TABLE,"PROVIDER_ID =?",PROVIDER_ID);
//        if(l>0)
//            return true;
//        else
//            return false;
//
//    }
    public boolean addServiceProvider(String provName, String phone, String city){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROVIDERNAME, provName);
        values.put(PROV_PHONE,phone);
        values.put(PROV_CITY,city);
        long l = sqLiteDatabase.insert(SERV_PROVIDER_TABLE,null, values);
        if(l>0)
            return true;
        else
            return false;
    }

    public Cursor verifyUser(String uName){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT id,username,password,type FROM " + USER_TABLE
                + " WHERE username = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{uName});
        return cursor;
    }

    public Cursor selectProviderServices(int provID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT service_id,name FROM " + SERVICE_TABLE
                + " WHERE provider_id = ?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(provID)});
        return cursor;
    }

    public Cursor selectAllProviders(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + SERV_PROVIDER_TABLE;
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        return cursor;
    }

    public Cursor selectAllCustomerUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE +
                " WHERE type ='Customer'";
        Cursor cursor = sqLiteDatabase.rawQuery(query,null);
        return cursor;
    }

    public Cursor selectAUser(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * FROM " + USER_TABLE +
                " WHERE id =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(id)});
        return cursor;
    }

    public boolean updateUser(int id,String uName, String uPass, String fName, String email, String phNum, String uType, String address, String city){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME,uName);
        values.put(PASSWORD,uPass);
        values.put(FULLNAME,fName);
        values.put(EMAIL,email);
        values.put(PHONE,phNum);
        values.put(ADDRESS,address);
        values.put(CITY,city);
        values.put(USER_TYPE,uType);
        int i = sqLiteDatabase.update(USER_TABLE,
                values,"Id=?",new String[]{Integer.toString(id)});
        if(i>0)
            return true;
        else
            return false;
    }

    public Cursor selectCustomerAppointments(int uID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT service.name,ServiceProvider.ServProviderName, ServiceProvider.City, date,time,app_type,appointment.service1_id FROM appointment " +
                "inner join ServiceProvider on appointment.provider_id = ServiceProvider.provider_id " +
                "inner join service on appointment.service1_id = service.service_id " +
                " WHERE date < date('now') AND appointment.user_id =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(uID)});
        return cursor;
    }

    public Cursor nextCustomerAppointment(int uID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT service.name,ServiceProvider.ServProviderName, ServiceProvider.City, date,time,app_type,appointment.service1_id FROM appointment " +
                "inner join ServiceProvider on appointment.provider_id = ServiceProvider.provider_id " +
                "inner join service on appointment.service1_id = service.service_id " +
                " WHERE date > date('now') AND appointment.user_id =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(uID)});
        return cursor;
    }

    public Cursor selectAService(int serviceID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT name,description,price FROM " + SERVICE_TABLE +
                " WHERE service_id =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{String.valueOf(serviceID)});
        return cursor;
    }

    public Cursor selectAProvider(String provName) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT provider_id FROM " + SERV_PROVIDER_TABLE +
                " WHERE ServProviderName =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query, new String[]{provName});
        return cursor;
    }

    public boolean deleteAUser(int userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int i = sqLiteDatabase.delete(USER_TABLE,"Id=?",
                new String[]{String.valueOf(userID)});
        if(i>0)
            return true;
        else
            return false;
    }

    public Cursor selectProviderAppointments(int provID){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT service.name,ServiceProvider.ServProviderName, ServiceProvider.City, date,time,app_type,appointment.service1_id FROM appointment " +
                "inner join ServiceProvider on appointment.provider_id = ServiceProvider.provider_id " +
                "inner join service on appointment.service1_id = service.service_id " +
                " WHERE date > date('now') AND appointment.provider_id =?";
        Cursor cursor = sqLiteDatabase.rawQuery(query,new String[]{String.valueOf(provID)});
        return cursor;
    }


}
