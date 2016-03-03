package com.anxpp.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anxpp.calculator.adapter.entity.HistoryRecord;

/**
 * 主Activity
 * 带侧边栏
 * @author anxpp.com
 */
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    //三个输入框
    private EditText[] editTexts;
    //输入框对应的位置（默认第一个）:0、1、2
    private int currentEdit = 0;
    private TextView t1,t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        editTexts = new EditText[]{(EditText)findViewById(R.id.Value_A),
                (EditText)findViewById(R.id.Value_B),(EditText)findViewById(R.id.Value_N)};
        Button[] buttons = new Button[]{
                (Button) findViewById(R.id.Btn_0), (Button) findViewById(R.id.Btn_1),
                (Button) findViewById(R.id.Btn_2), (Button) findViewById(R.id.Btn_3),
                (Button) findViewById(R.id.Btn_4), (Button) findViewById(R.id.Btn_5),
                (Button) findViewById(R.id.Btn_6), (Button) findViewById(R.id.Btn_7),
                (Button) findViewById(R.id.Btn_8), (Button) findViewById(R.id.Btn_9),
                (Button) findViewById(R.id.Btn_point), (Button) findViewById(R.id.Btn_next),
                (Button) findViewById(R.id.Btn_clear)};
        t1 = (TextView)findViewById(R.id.t_exp1);
        t2 = (TextView)findViewById(R.id.t_exp2);
        //添加监听
        for(Button button: buttons){
            button.setOnClickListener(this);
        }
        //长按
        buttons[12].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                editTexts[currentEdit].setText("");
                return false;
            }
        });
        //关闭系统键盘
        for(EditText editText: editTexts){
            editText.setInputType(InputType.TYPE_NULL);
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int id = v.getId();
                    switch (id){
                        case R.id.Value_A:
                            currentEdit = 0;
                            break;
                        case R.id.Value_B:
                            currentEdit = 1;
                            break;
                        case R.id.Value_N:
                            currentEdit =2;
                            break;
                    }
                    Log.d("EditTouch","" + id);
                    //设置焦点
                    v.requestFocus();
                    setEditColor();
                    return true;
                }
            });
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAlpha(0.8f);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double A,B,N;
                try {
                    A = Double.parseDouble(editTexts[0].getText().toString());
                    B = Double.parseDouble(editTexts[1].getText().toString());
                    N = Double.parseDouble(editTexts[2].getText().toString());
                }catch (NumberFormatException NumberFormatException){
                    Toast.makeText(MainActivity.this,"参数有错！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(N==1){
                    Toast.makeText(MainActivity.this,"N不能等于1",Toast.LENGTH_SHORT).show();
                    return;
                }
                //结果
                double X = MyCalculator.exp1(A,B,N);
                double Y = MyCalculator.exp2(A,B,N);
                t1.setText("补资金：" + X);
                t2.setText("卖股票：" + Y);
                //存数据库
                new HistoryRecord(A,B,N,X,Y,System.currentTimeMillis(),"").save();
//Snackbar.make(view, "公式1结果：" + r1 + "\n公式1结果：" + r2,Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //主菜单
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        switch (id){
            //重置
            case R.id.action_clear:
                restore();
                Log.d("清空","clear");
                break;
            case R.id.action_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    //侧拉菜单
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_manage:
                simpleToast("设置");
                break;
            case R.id.nav_reset:
                restore();
                break;
            case R.id.nav_history:
                startActivity(new Intent(this, HistoryActivity.class));
                break;
            case R.id.nav_exp1:
//                simpleToast("编辑");
                break;
            case R.id.nav_exp2:
//                simpleToast("编辑");
                break;
            default:break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //简化Toast
    private void simpleToast(String msg){
        Toast.makeText(MainActivity.this,msg, Toast.LENGTH_SHORT).show();
    }

    //重置参数
    private void restore(){
        //重置当前编辑框
        currentEdit = 0;
        //重置焦点
        editTexts[currentEdit].requestFocus();
        //重置内容
        for(EditText editText:editTexts){
            editText.setText("");
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        final int id = view.getId();
        int flag = 0;
        switch(id){
            case R.id.Btn_0:
                flag = 0;
                break;
            case R.id.Btn_1:
                flag = 1;
                break;
            case R.id.Btn_2:
                flag = 2;
                break;
            case R.id.Btn_3:
                flag = 3;
                break;
            case R.id.Btn_4:
                flag = 4;
                break;
            case R.id.Btn_5:
                flag = 5;
                break;
            case R.id.Btn_6:
                flag = 6;
                break;
            case R.id.Btn_7:
                flag = 7;
                break;
            case R.id.Btn_8:
                flag = 8;
                break;
            case R.id.Btn_9:
                flag = 9;
                break;
            case R.id.Btn_point:
                flag = 10;
                break;
            case R.id.Btn_next:
                flag = 11;
                break;
            case R.id.Btn_clear:
                flag = 15;
                break;
        }
        dealClick(flag);
    }

    private void setEditColor(){
        for(EditText editText: editTexts) {
            editText.setTextColor(getResources().getColor(R.color.colorAccentDark));
        }
        editTexts[currentEdit].setTextColor(getResources().getColor(R.color.colorAccent));
    }

    //处理点击
    private void dealClick(int flag){
        Log.d("flag:",""+flag);
        EditText editText = editTexts[currentEdit];
        String oldValue = editText.getText().toString();
        //点击继续
        if(flag==11){
            //当前编辑对象标志
            currentEdit=(currentEdit+1)%3;
            //设置焦点
            editTexts[currentEdit].requestFocus();
            setEditColor();
        }
        //点击清理
        else if(flag==15){
            String oldExp = editTexts[currentEdit].getText().toString();
            if(!oldExp.equals("") && !oldExp.isEmpty())
                editTexts[currentEdit].setText(oldExp.substring(0,oldExp.length()-1));
        }
        //点击数字
        else if(flag<=9)
            editText.setText(oldValue+flag);
            //小数点
        else
            editText.setText(oldValue+".");
        //重新定位光标
//        editText.setSelection(oldValue.length() + 1);
    }
}
