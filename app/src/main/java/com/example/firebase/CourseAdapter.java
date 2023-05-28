package com.example.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class CourseAdapter extends ArrayAdapter<Course> {
    private Context context;
    private List<Course> courseList;

    public CourseAdapter(Context context, List<Course> courseList) {
        super(context, 0, courseList);
        this.context = context;
        this.courseList = courseList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.course_item, parent, false);
        }

        Course currentCourse = courseList.get(position);

        TextView textViewTitle = itemView.findViewById(R.id.textViewTitle);
        TextView textViewInstructor = itemView.findViewById(R.id.textViewInstructor);
        TextView textViewDescription = itemView.findViewById(R.id.textViewDescription);

        textViewTitle.setText(currentCourse.getTitle());
        textViewInstructor.setText(currentCourse.getInstructor());
        textViewDescription.setText(currentCourse.getDescription());

        return itemView;
    }
}
