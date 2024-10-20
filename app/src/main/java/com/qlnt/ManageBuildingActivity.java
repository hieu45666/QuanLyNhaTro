package com.qlnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.DayNhaTro;
import com.qlnt.Model.NhaTro;
import com.qlnt.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManageBuildingActivity extends AppCompatActivity {

    private ExpandableListView expandableListViewBuildings; // Sử dụng ExpandableListView
    private BuildingExpandableListAdapter buildingAdapter;
    private ListView listViewUnassignedRooms;
    private ImageButton buttonAddBuilding;
    private ArrayList<DayNhaTro> buildingList; // Danh sách dãy trọ
    private ArrayList<NhaTro> unassignedRooms; // Danh sách phòng chưa có dãy
    private DatabaseHelper databaseHelper;
    private HashMap<DayNhaTro, ArrayList<NhaTro>> buildingRoomsMap; // Danh sách phòng cho từng dãy trọ


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_building);

        expandableListViewBuildings = findViewById(R.id.expandableListViewBuildings);
        listViewUnassignedRooms = findViewById(R.id.listViewUnassignedRooms);
        buttonAddBuilding = findViewById(R.id.buttonAddBuilding);
        databaseHelper = new DatabaseHelper(ManageBuildingActivity.this);

        loadBuildings();
        loadUnassignedRooms();

        // Sự kiện khi nhấn vào một nhà trọ chưa có dãy
        listViewUnassignedRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Chuyển đến màn hình sửa nhà trọ
                Intent intent = new Intent(ManageBuildingActivity.this, EditRoomActivity.class);
                intent.putExtra("MA_NHA_TRO", unassignedRooms.get(position).getMaNhaTro()); // Truyền mã nhà trọ
                startActivity(intent);
            }
        });

        // Xử lý khi bấm vào nút thêm dãy trọ
        buttonAddBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm dãy trọ
                Intent intent = new Intent(ManageBuildingActivity.this, AddBuildingActivity.class);
                startActivity(intent);
            }
        });


        // Xử lý khi bấm vào dãy trọ
        expandableListViewBuildings.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false; // Trả về false để tiếp tục xử lý click
            }
        });
    }

    // Tải dãy và phòng
    private void loadBuildingsAndRooms() {
        buildingList = databaseHelper.getAllDayNhaTro(); // Lấy tất cả dãy trọ từ cơ sở dữ liệu
        buildingRoomsMap = new HashMap<>();
        for (DayNhaTro building : buildingList) {
            ArrayList<NhaTro> rooms = databaseHelper.getRoomsWithBuildingID(building.getMaDay());
            buildingRoomsMap.put(building, rooms);
        }

        buildingAdapter = new BuildingExpandableListAdapter(this, buildingList, buildingRoomsMap);
        expandableListViewBuildings.setAdapter(buildingAdapter);
    }

        // Tải danh sách các dãy trọ
        public void loadBuildings () {
            buildingList = databaseHelper.getAllDayNhaTro(); // Lấy tất cả dãy trọ từ cơ sở dữ liệu
            buildingRoomsMap = new HashMap<>();

            for (DayNhaTro building : buildingList) {
                ArrayList<NhaTro> rooms = databaseHelper.getRoomsWithBuildingID(building.getMaDay());
                buildingRoomsMap.put(building, rooms);
            }

            ExpandableListAdapter adapter = new ExpandableListAdapter(this, buildingList, buildingRoomsMap);
            expandableListViewBuildings.setAdapter(adapter);
        }

        // Tải danh sách nhà trọ chưa có dãy
        private void loadUnassignedRooms () {
            unassignedRooms = databaseHelper.getRoomsWithBuildingID(0); // Lấy danh sách từ DB
            ArrayList<String> roomNames = new ArrayList<>();
            for (NhaTro room : unassignedRooms) {
                roomNames.add("Phòng " + room.getSoPhong() + " - Giá: " + room.getGiaPhong() + " VND");
            }
            ArrayAdapter<String> unassignedRoomsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, roomNames);
            listViewUnassignedRooms.setAdapter(unassignedRoomsAdapter);
        }

    @Override
    protected void onResume() {
        super.onResume();
        loadBuildings(); // Tải lại danh sách khi quay lại activity
        loadUnassignedRooms(); // Tải lại danh sách nhà trọ chưa có dãy
    }
}
