package com.example.plant4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.NumberFormat;

import java.util.HashMap;



public class MainActivity extends AppCompatActivity {
    private Button waterbtn; private double sunnum;
    private Button lightbtn; private double humnum;
    private ImageButton tree; private double tempnum;
    private ImageButton sunbtn; private double phnum;
    private ImageButton humbtn; private double weightnum;
    private ImageButton tempbtn;
    private ImageButton phbtn; private String Ssunnum = String.format("%.1f",sunnum).concat("LUX") ;
    private TextView sunlevel;
    private TextView humlevel; private String Shumnum = String.format("%.1f",humnum*100).concat("%");
    private TextView templevel;private String Stempnum = String.format("%.1f",tempnum).concat("℃");
    private TextView phlevel;  private String Sphnum = String.format("%.1f",phnum);;
    private TextView sundata;  private String Sweightnum = String.format("%.1f",weightnum).concat("g");
    private TextView humdata;
    private TextView tempdata;
    private TextView weightdata;
    private TextView phdata;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        waterbtn = (Button) findViewById(R.id.waterbtn);//id后面为上方button的id
        lightbtn = (Button) findViewById(R.id.lightbtn);
        tree = (ImageButton) findViewById(R.id.tree);
        sunbtn = (ImageButton) findViewById(R.id.sunbtn);
        humbtn = (ImageButton) findViewById(R.id.humbtn);
        phbtn = (ImageButton) findViewById(R.id.phbtn);
        tempbtn = (ImageButton) findViewById(R.id.tempbtn);
        humlevel =(TextView)findViewById(R.id.humlevel);
        templevel =(TextView)findViewById(R.id.templevel);
        phlevel =(TextView)findViewById(R.id.phlevel);
        sunlevel =(TextView)findViewById(R.id.sunlevel);//id后面为上方button的id
        sundata =(TextView)findViewById(R.id.sundata);
        phdata =(TextView)findViewById(R.id.phdata);
        tempdata =(TextView)findViewById(R.id.tempdata);
        humdata =(TextView)findViewById(R.id.humdata);
        weightdata =(TextView)findViewById(R.id.weightdata);

        setListener();
       waterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(com.example.plant4.MainActivity.this, chartActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent1);
            }
        });
    }
    private void setListener() {

        // 按钮点击事件
       lightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Connection[] conn = new Connection[1];     //一个成员变量

//加载数据库驱动
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Log.d("MainActivity", "加载JDBC驱动成功！");
                } catch (ClassNotFoundException e) {
                    Log.d("MainActivity", "加载JDBC驱动失败！");
                    return;
                }
                //连接数据库（开辟一个新线程）
                final Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 反复尝试连接，直到连接成功后退出循环
                        while (!Thread.interrupted()) {
                            try {
                                Thread.sleep(100);  // 每隔0.1秒尝试连接
                            } catch (InterruptedException e) {
                                Log.e("MainActivity", e.toString());
                            }

                            // 2.设置好IP/端口/数据库名/用户名/密码等必要的连接信息
                            String ip = "mysql.studev.groept.be";                 //本机IP
                            int port = 3306;                              //mysql默认端口
                            String dbName = "a21ux01";             //自己的数据库名
                            String url = "jdbc:mysql://" + ip + ":" + port
                                    + "/" + dbName; // 构建连接mysql的字符串
                            String user = "a21ux01";                //自己的用户名
                            String password = "hWOeoG0p";           //自己的密码

                            // 3.连接JDBC
                            try {
                                conn[0] = DriverManager.getConnection(url, user, password);
                                Log.d("MainActivity", "连接数据库成功!");
                                if (conn[0]== null)
                                {
                                    sunlevel.setText("successful");
                                }
                                //查询学生表
                                String sql = "SELECT * FROM Answer ";

                                //关闭数据库

                                return;
                            } catch (SQLException e) {
                                Log.d("MainActivity", "连接数据库失败!");
                            }
                        }
                    }
                });
                thread.start();



                // 创建一个线程来连接数据库并获取数据库中对应表的数据


            }
        });
    }

public void showdata()
{
    sundata.setText(Ssunnum);
    humdata.setText(Shumnum);
    phdata.setText(Sphnum);
    tempdata.setText(Stempnum);
    weightdata.setText(Sweightnum);
}
public void judgeLevel()
{
    if(sunnum>3000)
    {
        sunlevel.setText("too strong");
    }
    else if (sunnum>2000)
    {
        sunlevel.setText("suitable");
    }
    else
    {
        sunlevel.setText("too weak");
    }

    if(humnum>0.7)
    {
        humlevel.setText("too moist");
    }
    else if (humnum>0.4)
    {
        humlevel.setText("suitable");
    }
    else
    {
        humlevel.setText("too dry");
    }

    if(tempnum>30)
    {
        templevel.setText("too hot");
    }
    else if (tempnum>25)
    {
        templevel.setText("suitable");
    }
    else
    {
        templevel.setText("too cold");
    }

    if(phnum>6.8)
    {
        phlevel.setText("too high");
    }
    else if (phnum>5.2)
    {
        phlevel.setText("suitable");
    }
    else
    {
        phlevel.setText("too low");
    }
}

}