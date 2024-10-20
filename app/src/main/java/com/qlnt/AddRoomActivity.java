package com.qlnt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.qlnt.Data.DatabaseHelper;

public class AddRoomActivity extends AppCompatActivity {

    private EditText editTextMaNhaTro, editTextSoPhong, editTextGiaPhong, editTextSoNguoiToiDa;
    private Button buttonAdd;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        // Khởi tạo các EditText và Button
        editTextMaNhaTro = findViewById(R.id.editTextMaNhaTro);
        editTextSoPhong = findViewById(R.id.editTextSoPhong);
        editTextGiaPhong = findViewById(R.id.editTextGiaPhong);
        editTextSoNguoiToiDa = findViewById(R.id.editTextSoNguoiToiDa);
        buttonAdd = findViewById(R.id.buttonAdd);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(peekAvailableContext());

        // Thiết lập sự kiện cho nút thêm
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các EditText
                int maNhaTro = Integer.parseInt(editTextMaNhaTro.getText().toString().trim());
                int soPhong = Integer.parseInt(editTextSoPhong.getText().toString().trim());
                int giaPhong = Integer.parseInt(editTextGiaPhong.getText().toString().trim());
                int soNguoiToiDa = Integer.parseInt(editTextSoNguoiToiDa.getText().toString().trim());

                // Gọi phương thức thêm nhà trọ trong DatabaseHelper
                databaseHelper.addNhaTro(maNhaTro, soPhong, giaPhong, soNguoiToiDa);

                // Hiển thị thông báo thành công
                Toast.makeText(peekAvailableContext(), "Thêm nhà trọ thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }

        });
    }
}
