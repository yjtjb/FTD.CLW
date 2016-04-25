package com.example.zhuce;


import org.apache.http.Header;

import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button ok = (Button)findViewById(R.id.ok);
        final EditText et_username = (EditText)findViewById(R.id.et_username);
        final EditText et_password = (EditText)findViewById(R.id.et_password);
        final EditText ip_info = (EditText)findViewById(R.id.et_ip);
        
        ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String ip = ip_info.getText().toString();
				final String username = et_username.getText().toString();
				String password = et_password.getText().toString();
				
				if(username.equals("") || password.equals("")) {
					Toast.makeText(MainActivity.this, "weikong", Toast.LENGTH_SHORT).show();
				}
				else {
					AsyncHttpClient client = new AsyncHttpClient();
                    RequestParams params = new RequestParams();
                    params.add("username", username);
                    params.add("password", password);
                    client.post("http://" + ip + ":82" + "/ar_info/reg.php", params, new AsyncHttpResponseHandler() {
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String response = new String(bytes);
                            Log.e("debug", response);
                            JSONObject object = null;
                            try {
                                object = new JSONObject(response);
                                String status = object.getString("status");
                                if (status.equals("exists")) {
                                    Toast.makeText(MainActivity.this, "用户名已存在，请更换", Toast.LENGTH_LONG).show();
                                } else if(status.equals("error")) {
                                    Toast.makeText(MainActivity.this, "出现错误，请稍后重试", Toast.LENGTH_LONG).show();

                                }
                                else if(status.equals("success")){
									Toast.makeText(MainActivity.this, "success", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                            Toast.makeText(MainActivity.this, "网络错误，请稍后重试", Toast.LENGTH_LONG).show();
                        }
                    });
				}
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
