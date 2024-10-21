package com.qlnt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.qlnt.Data.DatabaseHelper;
import com.qlnt.Model.DayNhaTro;
import com.qlnt.Model.NhaTro;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<DayNhaTro> buildingList; // Danh sách dãy nhà trọ
    private HashMap<DayNhaTro, ArrayList<NhaTro>> buildingRoomsMap;

    public ExpandableListAdapter(Context context, ArrayList<DayNhaTro> buildingList,
                                 HashMap<DayNhaTro, ArrayList<NhaTro>> buildingRoomsMap) {
        this.context = context;
        this.buildingList = buildingList;
        this.buildingRoomsMap = buildingRoomsMap;
    }

    @Override
    public int getGroupCount() {
        return buildingList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return buildingRoomsMap.get(buildingList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return buildingList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return buildingRoomsMap.get(buildingList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_building, null);
            holder = new ViewHolderGroup();
            holder.textViewBuildingName = convertView.findViewById(R.id.textViewBuildingName);
            holder.iconEdit = convertView.findViewById(R.id.iconEdit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderGroup) convertView.getTag();
        }

        DayNhaTro building = (DayNhaTro) getGroup(groupPosition);
        holder.textViewBuildingName.setText(building.getTenDay()+" ID: "+building.getMaDay());

        // Xử lý sự kiện cho icon sửa
        holder.iconEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến activity sửa dãy trọ
                Intent intent = new Intent(context, EditBuildingActivity.class);
                intent.putExtra("building_id", building.getMaDay());
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_room_b, null);
            holder = new ViewHolderChild();
            holder.textViewRoomName = convertView.findViewById(R.id.textViewRoomName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolderChild) convertView.getTag();
        }

        NhaTro room = (NhaTro) getChild(groupPosition, childPosition);
        holder.textViewRoomName.setText("Phòng " + room.getMaNhaTro() + " - Giá: " + String.format("%,d", room.getGiaPhong()) + " VND");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    // ViewHolder cho nhóm
    static class ViewHolderGroup {
        TextView textViewBuildingName;
        ImageView iconEdit;
    }

    // ViewHolder cho phòng
    static class ViewHolderChild {
        TextView textViewRoomName;
    }
}


