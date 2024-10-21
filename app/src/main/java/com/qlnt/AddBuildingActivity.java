package com.qlnt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.DayNhaTro;

public class AddBuildingActivity extends AppCompatActivity {

    private EditText editTextBuildingName, editTextBuildingDescription;
    private Button buttonAddBuilding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_building);

        editTextBuildingName = findViewById(R.id.editTextBuildingName);
        editTextBuildingDescription = findViewById(R.id.editTextBuildingDescription);
        buttonAddBuilding = findViewById(R.id.buttonAddBuilding);

        // Khởi tạo DatabaseHelper
        databaseHelper = new DatabaseHelper(AddBuildingActivity.this);

        // Xử lý khi nhấn nút thêm dãy trọ
        buttonAddBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy dữ liệu từ các trường
                String buildingName = editTextBuildingName.getText().toString();
                String buildingDescription = editTextBuildingDescription.getText().toString();

                // Kiểm tra dữ liệu đầu vào
                if (buildingName.isEmpty() || buildingDescription.isEmpty()) {
                    Toast.makeText(AddBuildingActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Thêm dãy vào cơ sở dữ liệu
                long result = databaseHelper.addDayNhaTro(buildingName, buildingDescription);

                if (result != -1) {
                    Toast.makeText(AddBuildingActivity.this, "Thêm dãy trọ thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng activity sau khi thêm thành công
                } else {
                    Toast.makeText(AddBuildingActivity.this, "Lỗi khi thêm dãy trọ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
