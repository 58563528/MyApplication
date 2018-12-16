package com.example.myinterview.database;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myinterview.R;
import com.example.myinterview.common.model.Person;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Person> list;
    private LayoutInflater inflater;

    public ListAdapter(List<Person> list, Context context) {
        this.list = list;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void setList(List<Person> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Person getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
//        ViewHolder viewHolder;
//        Person person = getItem(position);
//
//        if (convertView == null) {
//            view = inflater.inflate(R.layout.activity_main_list1, null);
//            viewHolder = new ViewHolder();
//            viewHolder.id = (TextView) view.findViewById(R.id.text_id);
//            viewHolder.name = (TextView) view.findViewById(R.id.text_name);
//            viewHolder.age = (TextView) view.findViewById(R.id.text_age);
//            viewHolder.sex = (TextView) view.findViewById(R.id.text_sex);
//        } else {
//            view = convertView;
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        viewHolder.id.setText(String.valueOf(person.getId()));
//        viewHolder.name.setText(person.getName());
//        viewHolder.age.setText(String.valueOf(person.getAge()));
//        viewHolder.sex.setText(person.getSex());
//
//        return view;
//    }
//
//    class ViewHolder {
//        TextView id;
//        TextView name;
//        TextView age;
//        TextView sex;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = inflater.inflate(R.layout.activity_main_list1, null);
        TextView idView = (TextView) convertView.findViewById(R.id.text_id);
        TextView nameView = (TextView) convertView.findViewById(R.id.text_name);
        TextView ageView = (TextView) convertView.findViewById(R.id.text_age);
        TextView sexView = (TextView) convertView.findViewById(R.id.text_sex);
        Person person = list.get(position);
        idView.setText(String.valueOf(person.getId()));
        nameView.setText(person.getName());
        ageView.setText(String.valueOf(person.getAge()));
        sexView.setText(String.valueOf(person.getSex()));
        return convertView;
    }


}
