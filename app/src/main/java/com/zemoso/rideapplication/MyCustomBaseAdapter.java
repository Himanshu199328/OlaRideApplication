package com.zemoso.rideapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by zemoso on 13/1/16.
 */
public class MyCustomBaseAdapter extends BaseAdapter {


    public static ArrayList<Display> displayArrayList;
    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context,ArrayList<Display>displays){
        displayArrayList = displays;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount(){
        return displayArrayList.size();

    }

    public Object getItem(int position){
        return displayArrayList.get(position);
    }

    public long getItemId(int postion){
        return  postion;
    }
    public View getView(int position, View convertView,ViewGroup parent){
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.custon_row_view,null);
            holder = new ViewHolder();
            holder.txtUsername = (TextView) convertView.findViewById(R.id.Username);
            holder.txtMobilenumber = (TextView) convertView.findViewById(R.id.Mobilenumber);
//            holder.txtStartingplace = (TextView) convertView.findViewById(R.id.Startingplace);
//            holder.txtRidedistance = (TextView) convertView.findViewById(R.id.Ridedistance);
//            holder.txtTimetaken = (TextView) convertView.findViewById(R.id.Timetaken);
//            holder.txtWaitingtime = (TextView)convertView.findViewById(R.id.Waitingtime);
//            holder.txtCabtype = (TextView)convertView.findViewById(R.id.Cabtype);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String username = displayArrayList.get(position).getUsername();
        String mobilenumber = displayArrayList.get(position).getMobilenumber();
        String startingplace =displayArrayList.get(position).getStartingplace();
        String ridedistance = displayArrayList.get(position).getRidedistance();
        String timetaken  = displayArrayList.get(position).getTimetaken();
        String cabtype = displayArrayList.get(position).getCabtype();
        Timestamp bookingTime = displayArrayList.get(position).getBoookingTime();
        holder.txtUsername.setText( bookingTime + " from " + startingplace + " with cab type "  + cabtype);
        holder.txtMobilenumber.setText("Time Taken : " + timetaken +" and ride distance " + ridedistance);
//        holder.txtStartingplace.setText(displayArrayList.get(position).getStartingplace());
//        holder.txtRidedistance.setText(displayArrayList.get(position).getRidedistance());
//        holder.txtTimetaken.setText(displayArrayList.get(position).getTimetaken());
//        holder.txtWaitingtime.setText(displayArrayList.get(position).getWaitingtime());
//        holder.txtCabtype.setText(displayArrayList.get(position).getCabtype());
        return convertView;
    }
    
    static class ViewHolder {
        TextView txtUsername;
        TextView txtMobilenumber;
        TextView txtStartingplace;
        TextView txtRidedistance;
        TextView txtTimetaken;
        TextView txtWaitingtime;
        TextView txtCabtype;


    }
}
