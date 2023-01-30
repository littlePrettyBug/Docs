package com.example.docs;

import static com.example.docs.DBmain.TABLENAME;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    int singledata;
    ArrayList<Model> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public MyAdapter(Context context, int singledata, ArrayList<Model> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {

        final Model model = modelArrayList.get(position);
        byte[] image = model.getProavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.iv_avatar.setImageBitmap(bitmap);
        holder.tv_name.setText(model.getUsername());

        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(menuItem.getItemId()){
                            case(R.id.edit_menu):
                                Bundle bundle = new Bundle();
                                bundle.putInt("id", model.getId());
                                bundle.putByteArray("avatar", model.getProavatar());
                                bundle.putString("name", model.getUsername());
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.putExtra("userdata", bundle);
                                context.startActivity(intent);
                                break;

                            case(R.id.delete_menu):
                                DBmain dBmain = new DBmain(context);
                                sqLiteDatabase = dBmain.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(TABLENAME, "id = "+model.getId(), null);
                                if (recdelete != -1){
                                    Toast.makeText(context, "Документ удалён", Toast.LENGTH_SHORT).show();
                                    modelArrayList.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                }
                                break;

                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });

        // open the full-scaled photo from new activity
        holder.iv_avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle bundle = new Bundle();
                bundle.putInt("id", model.getId());
                bundle.putByteArray("avatar", model.getProavatar());
                bundle.putString("name", model.getUsername());

                Intent intent = new Intent(context, FullScreen.class);
                intent.putExtra("userdata", bundle);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_avatar;
        TextView tv_name;
        ImageButton flowmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_avatar = itemView.findViewById(R.id.iv_avatar);
            tv_name = itemView.findViewById(R.id.tv_name);
            flowmenu = itemView.findViewById(R.id.flowmenu);

        }
    }
}
