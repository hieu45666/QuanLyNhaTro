package com.qlnt.Data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.content.ContentValues;

import com.qlnt.Model.NhaTro;
import com.qlnt.Model.DayNhaTro;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "quan_ly_nha_tro";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và cột
    private static final String TABLE_DAY_NHA_TRO = "day_nha_tro";
    private static final String COLUMN_DAY_MA = "ma_day";
    private static final String COLUMN_DAY_TEN = "ten_day";
    private static final String COLUMN_DAY_MOTA = "mo_ta";

    private static final String TABLE_NHA_TRO = "nha_tro";
    private static final String COLUMN_NHA_MA = "ma_nha";
    private static final String COLUMN_NHA_PHONG = "so_phong";
    private static final String COLUMN_NHA_GIA = "gia_phong";
    private static final String COLUMN_NHA_NGUOI = "so_nguoi_toi_da";
    private static final String COLUMN_NHA_DAY_MA = "ma_day_fk"; // Khóa ngoại

    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_PASS_WORD = "pass_word";

    // Constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Phương thức onCreate để tạo bảng cơ sở dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Dãy nhà trọ
        String createDayTable = "CREATE TABLE " + TABLE_DAY_NHA_TRO + " ("
                + COLUMN_DAY_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_DAY_TEN + " TEXT, "
                + COLUMN_DAY_MOTA + " TEXT)";
        db.execSQL(createDayTable);

        // Tạo bảng Nhà trọ
        String createNhaTable = "CREATE TABLE " + TABLE_NHA_TRO + " ("
                + COLUMN_NHA_MA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NHA_PHONG + " INTEGER, "
                + COLUMN_NHA_GIA + " INTERGER, "
                + COLUMN_NHA_NGUOI + " INTEGER, "
                + COLUMN_NHA_DAY_MA + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_NHA_DAY_MA + ") REFERENCES "
                + TABLE_DAY_NHA_TRO + "(" + COLUMN_DAY_MA + "))";
        db.execSQL(createNhaTable);

        String createUserTable = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_PASS_WORD + " TEXT)";
        db.execSQL(createUserTable);
    }

    // Phương thức onUpgrade để xử lý khi nâng cấp cơ sở dữ liệu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_NHA_TRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHA_TRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //Lấy thông tin toàn bộ dãy nhà trọ
    public ArrayList<DayNhaTro> getAllDayNhaTro() {
        ArrayList<DayNhaTro> buildingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DAY_NHA_TRO, null);

        if (cursor.moveToFirst()) {
            do {
                int maDayTro = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DAY_MA));
                if (maDayTro != 0) {
                String tenDay = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_TEN));
                String moTa = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_MOTA));
                DayNhaTro daytro = new DayNhaTro(maDayTro,tenDay,moTa);
                buildingList.add(daytro);}
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return buildingList;
    }

    // Phương thức thêm dãy nhà trọ
    public long addDayNhaTro(String tenDay, String moTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_TEN, tenDay);
        values.put(COLUMN_DAY_MOTA, moTa);
        long i = db.insert(TABLE_DAY_NHA_TRO, null, values);
        db.close();
        return i;
    }

    // Phương thức sửa dãy nhà trọ
    public void updateDayNhaTro(int maDay, String tenDay, String moTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_TEN, tenDay);
        values.put(COLUMN_DAY_MOTA, moTa);
        db.update(TABLE_DAY_NHA_TRO, values, COLUMN_DAY_MA + " = ?", new String[]{String.valueOf(maDay)});
        db.close();
    }

    // Phương thức xóa dãy nhà trọ
    public void deleteDayNhaTro(int maDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DAY_NHA_TRO, COLUMN_DAY_MA + " = ?", new String[]{String.valueOf(maDay)});
        db.close();
    }

    public DayNhaTro getBuildingById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DAY_NHA_TRO, null, COLUMN_DAY_MA + " = ?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String tenDay = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_TEN));
            String moTa = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_MOTA));
            cursor.close();
            return new DayNhaTro(id, tenDay, moTa);
        }
        return null;
    }

    // Lấy tất cả nhà trọ chưa có dãy (mã dãy bằng 0)
    public ArrayList<NhaTro> getRoomsWithBuildingID(int id) {
        ArrayList<NhaTro> roomsWithoutBuilding = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NHA_TRO, null, COLUMN_NHA_DAY_MA + " = ?", new String[]{Integer.toString(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int maNhaTro = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_MA));
                int soPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_PHONG));
                int giaPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_GIA));
                int soNguoiToiDa = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_NGUOI));

                NhaTro nhaTro = new NhaTro(maNhaTro, soPhong, giaPhong, soNguoiToiDa, id); // Mã dãy bằng 0
                roomsWithoutBuilding.add(nhaTro);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return roomsWithoutBuilding;
    }


    // Phương thức thêm nhà trọ
    public void addNhaTro(int soPhong, int giaPhong, int soNguoiToida) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHA_PHONG, soPhong);
        values.put(COLUMN_NHA_GIA, giaPhong);
        values.put(COLUMN_NHA_NGUOI, soNguoiToida);
        values.put(COLUMN_NHA_DAY_MA, 0);
        db.insert(TABLE_NHA_TRO, null, values);
        db.close();
    }

    // Phương thức sửa nhà trọ
    public boolean updateNhaTro(int maNha, int soPhong, int giaPhong, int soNguoiToida, int maDay) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHA_PHONG, soPhong);
        values.put(COLUMN_NHA_GIA, giaPhong);
        values.put(COLUMN_NHA_NGUOI, soNguoiToida);
        values.put(COLUMN_NHA_DAY_MA, maDay);
        Integer i = db.update(TABLE_NHA_TRO, values, COLUMN_NHA_MA + " = ?", new String[]{String.valueOf(maNha)});
        db.close();
        if (i == 0) return false;
        return true;
    }

    // Phương thức xóa nhà trọ
    public boolean deleteNhaTro(int maNha) {
        SQLiteDatabase db = this.getWritableDatabase();
        Integer i = db.delete(TABLE_NHA_TRO, COLUMN_NHA_MA + " = ?", new String[]{String.valueOf(maNha)});
        db.close();
        if (i == 0) return false;
        return true;
    }

    //Lấy thông tin 1 nhà trọ
    public NhaTro getNhaTro(int maNhaTro) {
        NhaTro room = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NHA_TRO, null, COLUMN_NHA_MA + " = ?", new String[]{String.valueOf(maNhaTro)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int maDayTro = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_DAY_MA));
                int soPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_PHONG));
                int giaPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_GIA));
                int soNguoiToiDa = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_NGUOI));

                room = new NhaTro(maNhaTro, soPhong, giaPhong, soNguoiToiDa, maDayTro);
            }
            cursor.close();
        }
        db.close();
        return room;
    }

    //Lấy thông tin toàn bộ nhà trọ
    public ArrayList<NhaTro> getAllNhaTro() {
        ArrayList<NhaTro> roomList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NHA_TRO, null);

        if (cursor.moveToFirst()) {
            do {
                int maDayTro = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_DAY_MA));
                int maNhaTro = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_MA));
                int soPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_PHONG));
                int giaPhong = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_GIA));
                int soNguoiToiDa = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_NHA_NGUOI));

                NhaTro room = new NhaTro(maNhaTro, soPhong, giaPhong, soNguoiToiDa, maDayTro);
                roomList.add(room);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return roomList;
    }

    // Thêm người dùng mới sau khi kiểm tra username
    public long addUser(String username, String password) {
        if (checkUserExists(username)) {
            return -1; // Trả về -1 nếu username đã tồn tại
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, username);
        values.put(COLUMN_PASS_WORD, password);

        // Thêm người dùng mới
        long id = db.insert(TABLE_USER, null, values);
        db.close();
        return id; // Trả về UserID mới được tạo
    }

    // Kiểm tra xem username đã tồn tại chưa
    public boolean checkUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Hàm để kiểm tra thông tin đăng nhập
    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_NAME + " = ? AND " + COLUMN_PASS_WORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean userExists = cursor.getCount() > 0; // Kiểm tra có kết quả trả về không
        cursor.close();
        db.close();
        return userExists; // Trả về true nếu đúng thông tin đăng nhập
    }

}
