package com.qlnt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import com.qlnt.Model.NhaTro;
import com.qlnt.Model.DayNhaTro;

public class BuildingExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<DayNhaTro> listDataHeader; // danh sách dãy
    private HashMap<DayNhaTro, ArrayList<NhaTro>> listDataChild; // danh sách phòng theo dãy

    public BuildingExpandableListAdapter(Context context, ArrayList<DayNhaTro> listDataHeader, HashMap<DayNhaTro, ArrayList<NhaTro>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition);
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
        DayNhaTro building = (DayNhaTro) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView textViewBuildingName = convertView.findViewById(R.id.textViewBuildingName);
        textViewBuildingName.setText(building.getTenDay());

        // Thêm nút sửa (icon edit) ở đây nếu cần
        // Bạn có thể thêm logic cho nút edit ở đây

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        NhaTro room = (NhaTro) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }

        TextView textViewRoomName = convertView.findViewById(R.id.textViewRoomName);
        textViewRoomName.setText("Phòng " + room.getSoPhong() + " - Giá: " + room.getGiaPhong() + " VND");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}

