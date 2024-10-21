package com.qlnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.NhaTro;

public class ManageRoomsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRooms;
    private ImageButton buttonAddRoom;
    private ArrayList<NhaTro> rooms;
    private RoomAdapter adapter;
    private DatabaseHelper databaseHelper;
    private CheckBox checkBoxFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms);

        recyclerViewRooms = findViewById(R.id.recyclerViewRooms);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);
        checkBoxFilter = findViewById(R.id.checkBoxFilter);
        databaseHelper = new DatabaseHelper(this);

        // Lấy danh sách nhà trọ từ database
        rooms = databaseHelper.getAllNhaTro();

        // Thiết lập adapter cho RecyclerView
        adapter = new RoomAdapter(this, rooms);

        recyclerViewRooms.setAdapter(adapter);
        recyclerViewRooms.setLayoutManager(new LinearLayoutManager(this));

        buttonAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(ManageRoomsActivity.this, AddRoomActivity.class);
            startActivity(intent);
        });

        checkBoxFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            filterRooms(isChecked);
        });
    }

    private void loadRooms(ArrayList<NhaTro> rooms) {
        adapter.updateRooms(rooms);  // Cập nhật danh sách phòng trong adapter
    }

    private void filterRooms(boolean isChecked) {
        ArrayList<NhaTro> filteredRooms = new ArrayList<>();
        if (isChecked) {
            for (NhaTro room : rooms) {
                if (room.getGiaPhong() < 1000000) {
                    filteredRooms.add(room);
                }
            }
        } else {
            filteredRooms.addAll(rooms);  // Hiển thị lại toàn bộ danh sách nếu bỏ chọn checkbox
        }
        loadRooms(filteredRooms);  // Gọi lại hàm loadRooms để cập nhật dữ liệu
    }

    @Override
    protected void onResume() {
        super.onResume();
        rooms = databaseHelper.getAllNhaTro();  // Lấy lại danh sách phòng từ database
        loadRooms(rooms);  // Cập nhật lại danh sách phòng trong adapter
    }
}
