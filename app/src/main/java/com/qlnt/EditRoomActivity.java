package com.qlnt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.NhaTro;
import android.content.Intent;

public class EditRoomActivity extends AppCompatActivity {

    private EditText editTextMaDayTro, editTextSoPhong, editTextGiaPhong, editTextSoNguoiToiDa;
    private Button buttonUpdate, buttonDelete;
    private DatabaseHelper databaseHelper;

    // Tham số để nhận mã nhà trọ từ Intent
    private int maNhaTro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);

        // Khởi tạo các EditText và Button
        editTextMaDayTro = findViewById(R.id.editTextMaDayTro);
        editTextSoPhong = findViewById(R.id.editTextSoPhong);
        editTextGiaPhong = findViewById(R.id.editTextGiaPhong);
        editTextSoNguoiToiDa = findViewById(R.id.editTextSoNguoiToiDa);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(peekAvailableContext());

        // Nhận mã nhà trọ từ Intent
        maNhaTro = getIntent().getIntExtra("MA_NHA_TRO", -1);
        if (maNhaTro != -1) {
            loadRoomData(maNhaTro);
        }

        // Thiết lập sự kiện cho nút cập nhật
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRoom();
            }
        });

        // Thiết lập sự kiện cho nút xóa
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteRoom();
            }
        });
    }

    // Phương thức để tải dữ liệu của nhà trọ
    private void loadRoomData(int maNhaTro) {
        // Lấy thông tin nhà trọ từ cơ sở dữ liệu (giả sử có phương thức này trong DatabaseHelper)
        NhaTro room = databaseHelper.getNhaTro(maNhaTro);
        if (room != null) {
            editTextMaDayTro.setText(String.valueOf(room.getMaDay()));
            editTextSoPhong.setText(String.valueOf(room.getSoPhong()));
            editTextGiaPhong.setText(String.valueOf(room.getGiaPhong()));
            editTextSoNguoiToiDa.setText(String.valueOf(room.getSoNguoiToiDa()));
        }
    }

    // Phương thức để cập nhật thông tin nhà trọ
    private void updateRoom() {
        int maDayTro = Integer.parseInt(editTextMaDayTro.getText().toString().trim());
        int soPhong = Integer.parseInt(editTextSoPhong.getText().toString().trim());
        int giaPhong = Integer.parseInt(editTextGiaPhong.getText().toString().trim());
        int soNguoiToiDa = Integer.parseInt(editTextSoNguoiToiDa.getText().toString().trim());

        // Cập nhật thông tin nhà trọ trong cơ sở dữ liệu
        if (databaseHelper.updateNhaTro(maNhaTro, soPhong, giaPhong, soNguoiToiDa, maDayTro)) {
            Toast.makeText(this, "Cập nhật nhà trọ thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
    }

    // Phương thức để xóa nhà trọ
    private void deleteRoom() {
        // Xóa nhà trọ khỏi cơ sở dữ liệu
        if (databaseHelper.deleteNhaTro(maNhaTro)) {
            Toast.makeText(this, "Xóa nhà trọ thành công!", Toast.LENGTH_SHORT).show();
            finish(); // Đóng Activity
        } else {
            Toast.makeText(this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
        }
    }
}
