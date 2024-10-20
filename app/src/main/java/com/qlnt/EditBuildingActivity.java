package com.qlnt;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.DayNhaTro;
import com.qlnt.Model.NhaTro;

import java.util.ArrayList;

public class EditBuildingActivity extends AppCompatActivity {

    private EditText editTextBuildingName, editTextBuildingDescription;
    private Button buttonUpdateBuilding, buttonDeleteBuilding;
    private DatabaseHelper databaseHelper;
    private int buildingId; // ID của dãy trọ

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_building);

        // Ánh xạ view
        editTextBuildingName = findViewById(R.id.editTextBuildingName);
        editTextBuildingDescription = findViewById(R.id.editTextBuildingDescription);
        buttonUpdateBuilding = findViewById(R.id.buttonUpdateBuilding);
        buttonDeleteBuilding = findViewById(R.id.buttonDeleteBuilding);

        databaseHelper = new DatabaseHelper(this);

        // Lấy thông tin ID dãy trọ từ Intent
        Intent intent = getIntent();
        buildingId = intent.getIntExtra("building_id", -1);

        if (buildingId != -1) {
            // Nếu ID hợp lệ, tải thông tin dãy trọ
            loadBuildingInfo(buildingId);
        } else {
            Toast.makeText(this, "Không tìm thấy dãy trọ", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Cập nhật dãy trọ
        buttonUpdateBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBuilding();
            }
        });

        // Xóa dãy trọ
        buttonDeleteBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });
    }

    // Tải thông tin dãy trọ từ cơ sở dữ liệu
    private void loadBuildingInfo(int buildingId) {
        DayNhaTro building = databaseHelper.getBuildingById(buildingId);
        if (building != null) {
            editTextBuildingName.setText(building.getTenDay());
            editTextBuildingDescription.setText(building.getMoTa());
        }
    }

    // Cập nhật thông tin dãy trọ
    private void updateBuilding() {
        String name = editTextBuildingName.getText().toString().trim();
        String description = editTextBuildingDescription.getText().toString().trim();

        if (name.isEmpty()) {
            Toast.makeText(this, "Tên dãy trọ không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật dãy trọ vào cơ sở dữ liệu
        databaseHelper.updateDayNhaTro(buildingId, name, description);
        Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        finish(); // Quay lại màn hình trước đó sau khi cập nhật
    }

    // Xác nhận xóa dãy trọ
    private void confirmDelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa dãy trọ");
        builder.setMessage("Bạn có chắc chắn muốn xóa dãy trọ này?");

        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xóa dãy trọ từ cơ sở dữ liệu
                ArrayList<NhaTro> nt = databaseHelper.getRoomsWithBuildingID(buildingId);
                for (NhaTro n : nt){
                    databaseHelper.updateNhaTro(n.getMaNhaTro(), n.getSoPhong(), n.getGiaPhong(), n.getSoNguoiToiDa(), 0);
                }
                databaseHelper.deleteDayNhaTro(buildingId);
                Toast.makeText(EditBuildingActivity.this, "Đã xóa dãy trọ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        builder.setNegativeButton("Hủy", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
