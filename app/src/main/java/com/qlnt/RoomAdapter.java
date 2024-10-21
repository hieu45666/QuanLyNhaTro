package com.qlnt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.qlnt.Model.NhaTro;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private ArrayList<NhaTro> roomList;
    private Context context; // Thêm context để gọi Intent

    public RoomAdapter(Context context, ArrayList<NhaTro> roomList) {
        this.context = context;
        this.roomList = roomList;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        NhaTro room = roomList.get(position);
        holder.textRoomCode.setText(String.valueOf(room.getMaNhaTro()));
        holder.textRoomNumber.setText(String.valueOf(room.getSoPhong()));
        holder.textPrice.setText(String.valueOf(String.format("%,d", room.getGiaPhong())));
        holder.textMaxPeople.setText(String.valueOf(room.getSoNguoiToiDa()));

        // Khi nhấn nút edit, mở EditRoomActivity và truyền ID phòng
        holder.buttonEditRoom.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditRoomActivity.class);
            intent.putExtra("MA_NHA_TRO", room.getMaNhaTro()); // Truyền ID phòng
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class RoomViewHolder extends RecyclerView.ViewHolder {

        TextView textRoomCode, textRoomNumber, textPrice, textMaxPeople;
        ImageButton buttonEditRoom;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            textRoomCode = itemView.findViewById(R.id.textRoomCode);
            textRoomNumber = itemView.findViewById(R.id.textRoomNumber);
            textPrice = itemView.findViewById(R.id.textPrice);
            textMaxPeople = itemView.findViewById(R.id.textMaxPeople);
            buttonEditRoom = itemView.findViewById(R.id.buttonEditRoom);
        }
    }

    public void updateRooms(ArrayList<NhaTro> updatedRooms) {
        this.roomList.clear();
        this.roomList.addAll(updatedRooms);
        notifyDataSetChanged();
    }
}
