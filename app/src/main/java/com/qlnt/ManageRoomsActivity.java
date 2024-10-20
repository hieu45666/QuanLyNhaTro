package com.qlnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.text.NumberFormat;
import java.util.Locale;

import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.NhaTro;

public class ManageRoomsActivity extends AppCompatActivity {

    private ListView listViewRooms;
    private ImageButton buttonAddRoom;
    private ArrayList<NhaTro> rooms; // Danh sách nhà trọ
    private ArrayAdapter<String> adapter;
    private DatabaseHelper databaseHelper;
    private CheckBox checkBoxFilter;

    ArrayList<String> roomNames = new ArrayList<>();
    NumberFormat numberFormat = NumberFormat.getInstance(Locale.US); // Dùng định dạng US với dấu chấm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_rooms); // Gọi layout

        listViewRooms = findViewById(R.id.listViewRooms);
        buttonAddRoom = findViewById(R.id.buttonAddRoom);
        checkBoxFilter = findViewById(R.id.checkBoxFilter);
        databaseHelper = new DatabaseHelper(ManageRoomsActivity.this);
        rooms = databaseHelper.getAllNhaTro();
        loadRooms();

        // Sự kiện khi nhấn vào một nhà trọ
        listViewRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Chuyển đến màn hình sửa hoặc xóa nhà trọ
                Intent intent = new Intent(ManageRoomsActivity.this, EditRoomActivity.class);
                intent.putExtra("MA_NHA_TRO", rooms.get(position).getMaNhaTro()); // Truyền tên phòng
                startActivity(intent);
            }
        });

        // Sự kiện khi nhấn vào nút thêm nhà trọ
        buttonAddRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ManageRoomsActivity.this, AddRoomActivity.class);
                startActivity(intent);
            }
        });

        // Thiết lập sự kiện cho CheckBox
        checkBoxFilter.setOnCheckedChangeListener((buttonView, isChecked) -> {
            filterRooms(isChecked);
        });
    }

    private void loadRooms(ArrayList<NhaTro> rooms) {
        // Dùng ArrayAdapter để hiển thị danh sách nhà trọ theo tên
        updateRoomList(rooms);
    }

    private void loadRooms() {
        // Load danh sách phòng từ database vào rooms
        updateRoomList(rooms);
    }

    private void updateRoomList(ArrayList<NhaTro> rooms) {
        ArrayList<String> roomNames = new ArrayList<>();
        for (NhaTro room : rooms) {
            roomNames.add("Phòng " + room.getSoPhong() + " - Giá: " + numberFormat.format(room.getGiaPhong()) + " VND");
        }
        if (adapter == null) {
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomNames);
            listViewRooms.setAdapter(adapter);
        } else {
            adapter.clear();
            adapter.addAll(roomNames);
            adapter.notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
        }
    }

    private void filterRooms(boolean isChecked) {
        if (isChecked) {
            ArrayList<NhaTro> filteredRooms = new ArrayList<>();
            for (NhaTro room : rooms) {
                if (room.getGiaPhong() < 1000000) { // Lọc phòng dưới 1 triệu
                    filteredRooms.add(room);
                }
            }
            updateRoomList(filteredRooms);
        } else {
            updateRoomList(rooms); // Hiện lại danh sách đầy đủ
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        rooms = databaseHelper.getAllNhaTro();
        loadRooms(); // Tải lại danh sách khi quay lại
    }
}