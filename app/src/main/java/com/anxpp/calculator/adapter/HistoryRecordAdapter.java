package com.anxpp.calculator.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.anxpp.calculator.R;
import com.anxpp.calculator.adapter.entity.HistoryRecord;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 可扩展的list view适配器
 * Created by anxpp on 2016/3/3.
 * @author anxpp.com
 */
public class HistoryRecordAdapter extends BaseExpandableListAdapter {

    List<String> parents;
    Map<String,List<HistoryRecord>> children;
    Context context;
    //今天开始的时间
    long todayBegin = System.currentTimeMillis()/(1000*60*60*24);

    public HistoryRecordAdapter(List<String> parents, Map<String, List<HistoryRecord>> children, Context context){
        this.parents = parents;
        this.children = children;
        this.context = context;
    }

    //得到子节点需要关联的数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return (children.get(parents.get(groupPosition)).get(childPosition));
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_history_list_child,null);
        }
        ChildView childView = new ChildView();
        childView.valueA = (TextView)convertView.findViewById(R.id.child_value_A);
        childView.valueB = (TextView)convertView.findViewById(R.id.child_value_B);
        childView.valueN = (TextView)convertView.findViewById(R.id.child_value_N);
        childView.resultX = (TextView)convertView.findViewById(R.id.child_result_X);
        childView.resultY = (TextView)convertView.findViewById(R.id.child_result_Y);
        childView.remark = (TextView)convertView.findViewById(R.id.child_remark);
        HistoryRecord historyRecord = children.get(parents.get(groupPosition)).get(childPosition);
        childView.valueA.setText("A:" + historyRecord.getA());
        childView.valueB.setText("B:" + historyRecord.getB());
        childView.valueN.setText("N:" + historyRecord.getN());
        childView.resultX.setText("X:" + historyRecord.getX());
        childView.resultY.setText("Y:" + historyRecord.getY());
        long time = historyRecord.getTime();
        if(time>=todayBegin)
            childView.remark.setText(new SimpleDateFormat("hh:mm:ss SSS").format(new Date(time)));
        else
            childView.remark.setText(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss SSS").format(new Date(time)));
        return convertView;
    }
    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(parents.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return parents.get(groupPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
    @Override
    public int getGroupCount() {
        return parents.size();
    }
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_history_list_parent,null);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text_parent);
        textView.setText(parents.get(groupPosition));
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //子节点布局
    class ChildView{
        public TextView valueA,valueB,valueN,resultX,resultY,remark;
    }
}
