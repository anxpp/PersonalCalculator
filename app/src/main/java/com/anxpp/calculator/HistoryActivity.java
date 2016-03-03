package com.anxpp.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.anxpp.calculator.adapter.HistoryRecordAdapter;
import com.anxpp.calculator.adapter.entity.HistoryRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryActivity extends Activity {

    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        init();
    }
    private void init(){

        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.btn_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HistoryActivity.this,"暂未完善",Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView = (ExpandableListView)findViewById(R.id.expanded_list);

        Map<String,List<HistoryRecord>> maps = new HashMap<>();
        List<String> parents = new ArrayList<>();

        //今天开始的时间
        long todayBegin = System.currentTimeMillis()/(1000*60*60*24);
        //今天的记录
        List<HistoryRecord> todayHistoryRecords = HistoryRecord.find(HistoryRecord.class, "time>=?", todayBegin + "");
        parents.add("今天的记录");
        maps.put("今天的记录",todayHistoryRecords);

        //今天之前的记录
        List<HistoryRecord> moreHistoryRecords = HistoryRecord.find(HistoryRecord.class, "time<?", todayBegin + "");
        parents.add("更多记录");
        maps.put("更多记录", moreHistoryRecords);

        expandableListView.setAdapter(new HistoryRecordAdapter(parents, maps, this));
        expandableListView.expandGroup(0);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if(groupPosition==0)
                    expandableListView.collapseGroup(1);
                else
                    expandableListView.collapseGroup(0);
            }
        });
    }
}
