package com.qlnt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonManageRooms;
    private Button buttonManageBuildings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Gọi layout

        buttonManageRooms = findViewById(R.id.button_manage_rooms);
        buttonManageBuildings = findViewById(R.id.button_manage_buildings);

        buttonManageRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, ManageRoomsActivity.class);
                    startActivity(intent);
            }
        });

        buttonManageBuildings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 Chuyển tới Activity quản lý dãy nhà trọ
                Intent intent = new Intent(MainActivity.this, ManageBuildingActivity.class);
                  v.getContext().startActivity(intent);
            }
        });
    }
}