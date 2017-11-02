package com.example.silenove.menusystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class Main2Activity extends Activity implements AdapterView.OnItemSelectedListener {

    private List<SelectedInfo> packedInfos = new ArrayList<>();
    private int amount;

    private Spinner appSpinner2;
    private List<SpinnerInfo> spinnerInfos2= new ArrayList<>();
    private ListView selectedListView;
    private TextView amountTextView;
    private Button sendButton;

    private Socket socket;
    private BufferedWriter bufferedWriter;

    private String IP;

    private android.os.Handler handler;

    private TelephonyManager telephonyManager;
    private String PhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getPackedInfo();
        initSpinner();
        initListView();
        initamount();
        initbutton();
        inithandler();
        getPhoneNumber();

    }

    private void inithandler() {

        handler = new android.os.Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what == 0){
                    Toast.makeText(getApplicationContext(), "未检测到服务器", Toast.LENGTH_SHORT).show();
                }else if(msg.what == 1){
                    Toast.makeText(getApplicationContext(), "连接成功", Toast.LENGTH_SHORT).show();
                }else if(msg.what == 2){
                    Toast.makeText(getApplicationContext(), "连接失败", Toast.LENGTH_SHORT).show();
                }

            }
        };
    }


    private void initbutton() {
        sendButton = (Button) findViewById(R.id.id_packed_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socket == null) {
                    Log.e("send", "condition-----------flase");
                    Toast.makeText(getApplicationContext(), "未连接服务器", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        bufferedWriter.write("客户订单   手机号:" + PhoneNumber);
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        for (int i = 0; i < packedInfos.size(); i++) {
                            SelectedInfo info = packedInfos.get(i);
                            bufferedWriter.write(info.getInfo() + "     ");
                            bufferedWriter.write(info.getNumber());
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }
                        bufferedWriter.write("总计：" + String.valueOf(amount));
                        bufferedWriter.newLine();
                        bufferedWriter.flush();
                        Toast.makeText(getApplicationContext(), "下单成功", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    private void initamount() {
        amountTextView = (TextView) findViewById(R.id.id_packed_amount);
        amountTextView.setText("总价: ¥" + String.valueOf(amount));
    }

    private void initListView() {
        selectedListView = (ListView) findViewById(R.id.id_packed_listview);
        selectedListView.setAdapter(new SelectedAdapter(packedInfos, this));
    }

    private void getPackedInfo() {
        Bundle bundle = this.getIntent().getExtras();
        packedInfos = (List<SelectedInfo>) bundle.getSerializable("selectedInfos");
        amount = bundle.getInt("amount");
    }


    private void initSpinner() {
        appSpinner2 = (Spinner) findViewById(R.id.id_spinner2);

        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_selected3,"购物车"));
        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_menu3,"菜单"));
        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_link3,"连接"));



        appSpinner2.setAdapter(new SpinnerAdapter(spinnerInfos2, this));
        appSpinner2.setOnItemSelectedListener(this);

    }

    private void updateSpinner() {
        appSpinner2 = (Spinner) findViewById(R.id.id_spinner2);

        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_selected3, "购物车"));
        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_menu3,"菜单"));
        spinnerInfos2.add(new SpinnerInfo(R.drawable.spinner_link3,"连接"));



        appSpinner2.setAdapter(new SpinnerAdapter(spinnerInfos2, this));
        appSpinner2.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SpinnerInfo info = spinnerInfos2.get(position);
        String tab = info.getTab();
        if(tab == "菜单"){

            this.finish();

        }
        
        if(tab == "连接"){
            linkServer();
        }





    }

    private void linkServer() {
        final EditText InputServer = new EditText(this);
        InputServer.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("输入服务器地址").setMessage("请输入ip").setView(InputServer).setNegativeButton("取消", null);
        builder.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IP = InputServer.getText().toString();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            socket = new Socket(IP, 15810);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (socket == null) {
                            Log.e("socket", "condition---------null");
                            Message message = new Message();
                            message.what = 0;
                            handler.sendMessage(message);

                        } else {

                            if (socket.isConnected()) {
                                Log.e("socket", "is connect-----------success");
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);

                            } else {
                                Log.e("socket", "is connect-----------false");
                                Message message = new Message();
                                message.what = 2;
                                handler.sendMessage(message);
                            }

                            try {
                                bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
                            } catch (IOException e) {
                                e.printStackTrace();
                                Log.e("BufferedWriter", "condition----------false");
                            }
                        }
                    }
                });

                thread.start();
            }
        });
        builder.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void getPhoneNumber(){
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        PhoneNumber = telephonyManager.getLine1Number();

    }
}
