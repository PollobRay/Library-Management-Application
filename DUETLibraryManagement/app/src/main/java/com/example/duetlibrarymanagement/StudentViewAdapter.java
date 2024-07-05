package com.example.duetlibrarymanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class StudentViewAdapter extends ArrayAdapter<Student>
{
    private Activity context;
    private List<Student> studentList;

    StudentViewAdapter(Activity context, List<Student> studentList)
    {
        super(context, R.layout.studentviewlayout,studentList);

        this.context=context;
        this.studentList=studentList;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.studentviewlayout,null,true);

        Student std=studentList.get(position);

        TextView idText=view.findViewById(R.id.layoutStdIDId);
        TextView nameText=view.findViewById(R.id.layoutStudentNameId);

        idText.setText(std.getId().toString());
        nameText.setText(std.getName().toString());

        return view;
    }
}
