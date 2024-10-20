package com.qlnt.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import com.qlnt.Model.NhaTro;

import javax.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper{
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
    private static final String COLUMN_NHA_DAY_MA = "ma_day_fk";
    private final Context context;
    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    // Phương thức onCreate để tạo bảng cơ sở dữ liệu
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Dãy nhà trọ
        String createDayTable = "CREATE TABLE " + TABLE_DAY_NHA_TRO + " ("
                + COLUMN_DAY_MA + " INTEGER PRIMARY KEY, "
                + COLUMN_DAY_TEN + " TEXT, "
                + COLUMN_DAY_MOTA + " TEXT)";
        db.execSQL(createDayTable);

        // Tạo bảng Nhà trọ
        String createNhaTable = "CREATE TABLE " + TABLE_NHA_TRO + " ("
                + COLUMN_NHA_MA + " INTEGER PRIMARY KEY, "
                + COLUMN_NHA_PHONG + " INTEGER, "
                + COLUMN_NHA_GIA + " INTERGER, "
                + COLUMN_NHA_NGUOI + " INTEGER, "
                + COLUMN_NHA_DAY_MA + " INTEGER, "
                + "FOREIGN KEY (" + COLUMN_NHA_DAY_MA + ") REFERENCES "
                + TABLE_DAY_NHA_TRO + "(" + COLUMN_DAY_MA + "))";
        db.execSQL(createNhaTable);
    }

    // Phương thức onUpgrade để xử lý khi nâng cấp cơ sở dữ liệu
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAY_NHA_TRO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NHA_TRO);
        onCreate(db);
    }

    // Phương thức thêm dãy nhà trọ
    public void addDayNhaTro(int maDay, String tenDay, String moTa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY_MA, maDay);
        values.put(COLUMN_DAY_TEN, tenDay);
        values.put(COLUMN_DAY_MOTA, moTa);
        db.insert(TABLE_DAY_NHA_TRO, null, values);
        db.close();
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



    // Phương thức thêm nhà trọ
    public void addNhaTro(int maNha, int soPhong, int giaPhong, int soNguoiToida) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NHA_MA, maNha);
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
}
