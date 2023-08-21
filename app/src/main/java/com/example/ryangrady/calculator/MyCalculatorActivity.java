package com.example.ryangrady.calculator;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.ryangrady.R;
import com.example.ryangrady.features.MainiFeaturesActivity;
import com.example.ryangrady.securitylocks.SecurityLocksCommon;
import com.example.ryangrady.securitylocks.SecurityLocksCommon.LoginOptions;
import com.example.ryangrady.securitylocks.SecurityLocksSharedPreferences;
import com.example.ryangrady.utilities.Common;
import com.example.ryangrady.utilities.Utilities;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class MyCalculatorActivity extends AppCompatActivity {
    String LoginOption;
    StringBuilder builder = new StringBuilder();
    public Calculator calculator;
    StringBuilder compringString = new StringBuilder();
    int counter = 0;
    public TextView displayPrimary;
    private TextView displaySecondary;
    public HorizontalScrollView hsv;

    LinearLayout visiable, history_visiable;
    ImageView up_down;
    String myDecoyPass = "";
    String mypass = "";
    String mydefaultpass = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SecurityLocksSharedPreferences securityCredentialsSharedPreferences;

    HashMap<String, String> hashMap;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ListView history_lits;

    ImageButton history;

    private class MyAsyncTask extends AsyncTask<String, Integer, Double> {

        public void onProgressUpdate(Integer... numArr) {
        }

        private MyAsyncTask() {
        }


        public Double doInBackground(String... strArr) {
            postData(strArr[0], strArr[1], strArr[2]);
            return null;
        }


        public void onPostExecute(Double d) {
            if (LoginOptions.Password.toString().equals(MyCalculatorActivity.this.LoginOption)) {
                Toast.makeText(MyCalculatorActivity.this, R.string.toast_forgot_recovery_Success_Password_sent, Toast.LENGTH_LONG).show();
            } else if (LoginOptions.Pin.toString().equals(MyCalculatorActivity.this.LoginOption) || LoginOptions.Calculator.toString().equals(MyCalculatorActivity.this.LoginOption)) {
                Toast.makeText(MyCalculatorActivity.this, R.string.toast_forgot_recovery_Success_Pin, Toast.LENGTH_LONG).show();
            } else if (LoginOptions.Pattern.toString().equals(MyCalculatorActivity.this.LoginOption)) {
                Toast.makeText(MyCalculatorActivity.this, R.string.toast_forgot_recovery_Success_Pattern, Toast.LENGTH_LONG).show();
            }
        }

        public void postData(String str, String str2, String str3) {

            Log.e("AppType", "ACGV - Android");
            Log.e("Email", "" + str2);
            Log.e("Pass", "" + str);
            Log.e("PassType", "" + str3);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(SecurityLocksCommon.ServerAddress);
            try {
                ArrayList arrayList = new ArrayList(3);
                arrayList.add(new BasicNameValuePair("AppType", "ACGV - Android"));
                arrayList.add(new BasicNameValuePair("Email", str2));
                arrayList.add(new BasicNameValuePair("Pass", str));
                arrayList.add(new BasicNameValuePair("PassType", str3));
                httpPost.setEntity(new UrlEncodedFormEntity(arrayList));
                inputStreamToString(defaultHttpClient.execute(httpPost).getEntity().getContent()).toString().toString().equalsIgnoreCase("Successfully");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

        private StringBuilder inputStreamToString(InputStream inputStream) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    sb.append(readLine);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb;
        }
    }

    public void onCreate(Bundle bundle) {
        char c;
        char c2;
        Bundle bundle2 = bundle;
        super.onCreate(bundle);
        SecurityLocksCommon.IsAppDeactive = true;
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        this.securityCredentialsSharedPreferences = SecurityLocksSharedPreferences.GetObject(this);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        StringBuilder sb = new StringBuilder();
        sb.append(this.securityCredentialsSharedPreferences.GetEmail());
        sb.append("");
        Log.e("emailll", sb.toString());
        if (!defaultSharedPreferences.getBoolean("pref_dark", false)) {
            String string = defaultSharedPreferences.getString("pref_theme", "0");
            switch (string.hashCode()) {
                case 48:
                    if (string.equals("0")) {
                        c = 0;
                        break;
                    }
                case 49:
                    if (string.equals("1")) {
                        c = 1;
                        break;
                    }
                case 50:
                    if (string.equals("2")) {
                        c = 2;
                        break;
                    }
                case 51:
                    if (string.equals("3")) {
                        c = 3;
                        break;
                    }
                case 52:
                    if (string.equals("4")) {
                        c = 4;
                        break;
                    }
                case 53:
                    if (string.equals("5")) {
                        c = 5;
                        break;
                    }
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    setTheme(R.style.AppTheme_Light_Blue);
                    break;
                case 1:
                    setTheme(R.style.AppTheme_Light_Cyan);
                    break;
                case 2:
                    setTheme(R.style.AppTheme_Light_Gray);
                    break;
                case 3:
                    setTheme(R.style.AppTheme_Light_Green);
                    break;
                case 4:
                    setTheme(R.style.AppTheme_Light_Purple);
                    break;
                case 5:
                    setTheme(R.style.AppTheme_Light_Red);
                    break;
            }
        } else {
            String string2 = defaultSharedPreferences.getString("pref_theme", "0");
            switch (string2.hashCode()) {
                case 48:
                    if (string2.equals("0")) {
                        c2 = 0;
                        break;
                    }
                case 49:
                    if (string2.equals("1")) {
                        c2 = 1;
                        break;
                    }
                case 50:
                    if (string2.equals("2")) {
                        c2 = 2;
                        break;
                    }
                case 51:
                    if (string2.equals("3")) {
                        c2 = 3;
                        break;
                    }
                case 52:
                    if (string2.equals("4")) {
                        c2 = 4;
                        break;
                    }
                case 53:
                    if (string2.equals("5")) {
                        c2 = 5;
                        break;
                    }
                default:
                    c2 = 65535;
                    break;
            }
            switch (c2) {
                case 0:
                    setTheme(R.style.AppTheme_Dark_Blue);
                    break;
                case 1:
                    setTheme(R.style.AppTheme_Dark_Cyan);
                    break;
                case 2:
                    setTheme(R.style.AppTheme_Dark_Gray);
                    break;
                case 3:
                    setTheme(R.style.AppTheme_Dark_Green);
                    break;
                case 4:
                    setTheme(R.style.AppTheme_Dark_Purple);
                    break;
                case 5:
                    setTheme(R.style.AppTheme_Dark_Red);
                    break;
            }
        }
        setContentView((int) R.layout.activity_my_calculator);
        this.mypass = this.securityCredentialsSharedPreferences.GetSecurityCredential();

        this.mydefaultpass = this.securityCredentialsSharedPreferences.GetDefaultSecurityCredential();

        Log.e("password123", this.mypass);
        this.myDecoyPass = this.securityCredentialsSharedPreferences.GetDecoySecurityCredential();
        this.LoginOption = this.securityCredentialsSharedPreferences.GetLoginType();
        Log.e("mypass", this.mypass);
        this.displayPrimary = (TextView) findViewById(R.id.display_primary);
        this.displaySecondary = (TextView) findViewById(R.id.display_secondary);
        this.hsv = (HorizontalScrollView) findViewById(R.id.display_hsv);
        final SecurityLocksSharedPreferences GetObject = SecurityLocksSharedPreferences.GetObject(this);
        TextView[] textViewArr = {(TextView) findViewById(R.id.button_0), (TextView) findViewById(R.id.button_1), (TextView) findViewById(R.id.button_2), (TextView) findViewById(R.id.button_3), (TextView) findViewById(R.id.button_4), (TextView) findViewById(R.id.button_5), (TextView) findViewById(R.id.button_6), (TextView) findViewById(R.id.button_7), (TextView) findViewById(R.id.button_8), (TextView) findViewById(R.id.button_9)};
        for (int i = 0; i < textViewArr.length; i++) {
            final String str = (String) textViewArr[i].getText();
            textViewArr[i].setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    MyCalculatorActivity.this.calculator.digit(str.charAt(0));
                    MyCalculatorActivity myCalculatorActivity = MyCalculatorActivity.this;
                    StringBuilder sb = myCalculatorActivity.builder;
                    sb.append(str.charAt(0));
                    myCalculatorActivity.compringString = sb;
                    MyCalculatorActivity myCalculatorActivity2 = MyCalculatorActivity.this;
                    myCalculatorActivity2.counter = 0;

                    if (myCalculatorActivity2.mydefaultpass.trim().equals(MyCalculatorActivity.this.getText().trim())) {
                        SecurityLocksCommon.IsAppDeactive = false;
                        MyCalculatorActivity.this.builder.setLength(0);
                        Intent intent = new Intent(MyCalculatorActivity.this, MainiFeaturesActivity.class);
                        Common.loginCount++;
                        GetObject.SetRateCount(Common.loginCount);
                        MyCalculatorActivity.this.startActivity(intent);
                        MyCalculatorActivity.this.calculator.setText("");
                        MyCalculatorActivity.this.finish();
                    }

                    if (myCalculatorActivity2.mypass.trim().equals(MyCalculatorActivity.this.getText().trim())) {
                        SecurityLocksCommon.IsAppDeactive = false;
                        MyCalculatorActivity.this.builder.setLength(0);
                        Intent intent = new Intent(MyCalculatorActivity.this, MainiFeaturesActivity.class);
                        Common.loginCount++;
                        GetObject.SetRateCount(Common.loginCount);
                        MyCalculatorActivity.this.startActivity(intent);
                        MyCalculatorActivity.this.calculator.setText("");
                        MyCalculatorActivity.this.finish();
                    }
                }
            });
        }
        TextView[] textViewArr2 = {(TextView) findViewById(R.id.button_sin), (TextView) findViewById(R.id.button_cos), (TextView) findViewById(R.id.button_tan), (TextView) findViewById(R.id.button_ln), (TextView) findViewById(R.id.button_log), (TextView) findViewById(R.id.button_factorial), (TextView) findViewById(R.id.button_pi), (TextView) findViewById(R.id.button_e), (TextView) findViewById(R.id.button_exponent), (TextView) findViewById(R.id.button_start_parenthesis), (TextView) findViewById(R.id.button_end_parenthesis), (TextView) findViewById(R.id.button_square_root), (TextView) findViewById(R.id.button_add), (TextView) findViewById(R.id.button_subtract), (TextView) findViewById(R.id.button_multiply), (TextView) findViewById(R.id.button_divide), (TextView) findViewById(R.id.button_decimal), (TextView) findViewById(R.id.button_equals)};
        for (int i2 = 0; i2 < textViewArr2.length; i2++) {
            final String str2 = (String) textViewArr2[i2].getText();
            textViewArr2[i2].setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    if (str2.equals("sin")) {
                        MyCalculatorActivity.this.calculator.opNum('s');
                    }
                    if (str2.equals("cos")) {
                        MyCalculatorActivity.this.calculator.opNum('c');
                    }
                    if (str2.equals("tan")) {
                        MyCalculatorActivity.this.calculator.opNum('t');
                    }
                    if (str2.equals("ln")) {
                        MyCalculatorActivity.this.calculator.opNum('n');
                    }
                    if (str2.equals("log")) {
                        MyCalculatorActivity.this.calculator.opNum('l');
                    }
                    if (str2.equals("!")) {
                        MyCalculatorActivity myCalculatorActivity = MyCalculatorActivity.this;
                        myCalculatorActivity.counter = 0;
                        myCalculatorActivity.calculator.numOp('!');
                    }
                    if (str2.equals("π")) {
                        MyCalculatorActivity.this.calculator.num((char) 960);
                    }
                    if (str2.equals("e")) {
                        MyCalculatorActivity.this.calculator.num('e');
                    }
                    if (str2.equals("%")) {
                        MyCalculatorActivity.this.calculator.numOp('%');
                    }
                    if (str2.equals("(")) {
                        MyCalculatorActivity.this.calculator.parenthesisLeft();
                    }
                    if (str2.equals(")")) {
                        MyCalculatorActivity.this.calculator.parenthesisRight();
                    }
                    if (str2.equals("√")) {
                        MyCalculatorActivity.this.calculator.opNum((char) 8730);
                        MyCalculatorActivity.this.counter = 0;
                    }
                    if (str2.equals("÷")) {
                        MyCalculatorActivity.this.calculator.numOpNum(IOUtils.DIR_SEPARATOR_UNIX);
                        MyCalculatorActivity.this.counter = 0;
                    }
                    if (str2.equals("×")) {
                        MyCalculatorActivity myCalculatorActivity2 = MyCalculatorActivity.this;
                        myCalculatorActivity2.counter = 0;
                        myCalculatorActivity2.calculator.numOpNum('*');
                    }
                    if (str2.equals("−")) {
                        MyCalculatorActivity myCalculatorActivity3 = MyCalculatorActivity.this;
                        myCalculatorActivity3.counter = 0;
                        myCalculatorActivity3.calculator.numOpNum('-');
                    }
                    if (str2.equals("+")) {
                        MyCalculatorActivity.this.calculator.numOpNum('+');
                        MyCalculatorActivity.this.counter++;
                        if (MyCalculatorActivity.this.counter == 5) {
                            if (!Utilities.isNetworkOnline(MyCalculatorActivity.this)) {
                                Toast.makeText(MyCalculatorActivity.this, R.string.toast_connection_error, Toast.LENGTH_SHORT).show();
                            } else if (MyCalculatorActivity.this.securityCredentialsSharedPreferences.GetSecurityCredential().length() <= 0 || MyCalculatorActivity.this.securityCredentialsSharedPreferences.GetEmail().length() <= 0) {
                                Toast.makeText(MyCalculatorActivity.this, R.string.toast_forgot_recovery_fail_Pattern, Toast.LENGTH_SHORT).show();
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append(MyCalculatorActivity.this.securityCredentialsSharedPreferences.GetEmail());
                                sb.append("");
                                Log.e("emailll", sb.toString());
                                new MyAsyncTask().execute(new String[]{MyCalculatorActivity.this.mypass, MyCalculatorActivity.this.securityCredentialsSharedPreferences.GetEmail(), "Pin"});
                                Toast.makeText(MyCalculatorActivity.this, R.string.for_calculator_forgot_pin, Toast.LENGTH_SHORT).show();
                            }
                            MyCalculatorActivity.this.counter = 0;
                        }
                    }
                    if (str2.equals(".")) {
                        MyCalculatorActivity myCalculatorActivity4 = MyCalculatorActivity.this;
                        myCalculatorActivity4.counter = 0;
                        myCalculatorActivity4.calculator.decimal();
                    }
                    if (str2.equals("=") && !MyCalculatorActivity.this.getText().equals("")) {
                        MyCalculatorActivity.this.calculator.equal();
                        MyCalculatorActivity myCalculatorActivity5 = MyCalculatorActivity.this;
                        myCalculatorActivity5.counter = 0;
                        myCalculatorActivity5.builder.setLength(0);
                        MyCalculatorActivity.this.compringString.setLength(0);
                    }
                }
            });
        }

        //========================================
        visiable = findViewById(R.id.visible);
        up_down = findViewById(R.id.up_down);
        history_lits = findViewById(R.id.history_list);
        history_visiable = findViewById(R.id.history_visiable);
        history = findViewById(R.id.history);

        sharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
        editor= sharedPreferences.edit();
        //findViewById(R.id.radios).setStud
        //===============================
        final int[] a = {0};
        history.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String display = ((TextView) findViewById(R.id.display_primary)).getText().toString();
                String ans = ((TextView) findViewById(R.id.display_secondary)).getText().toString();




                if(a[0] ==0) {
                    history_visiable.setVisibility(View.VISIBLE);
                    findViewById(R.id.key_visiable).setVisibility(View.GONE);
                    a[0] = 1;
                }
                else if(a[0] ==1){
                    history_visiable.setVisibility(View.GONE);
                   findViewById(R.id.key_visiable).setVisibility(View.VISIBLE);
                    a[0] = 0;
                }

                if(display.equals("")){
                    findViewById(R.id.no_data).setVisibility(View.VISIBLE);

                }else{
                    findViewById(R.id.no_data).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.button_equals).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String display = ((TextView) findViewById(R.id.display_primary)).getText().toString();
                String ans = ((TextView) findViewById(R.id.display_secondary)).getText().toString();

                hashMap = new HashMap<>();
                hashMap.put("display", display);
                hashMap.put("ans", ans);
                arrayList.add(hashMap);

                MyCalculatorActivity.MyAdapter myadapter = new MyCalculatorActivity.MyAdapter();
                history_lits.setAdapter(myadapter);

            }
        });

        //==================

        sharedPreferences = getSharedPreferences("theme", Context.MODE_PRIVATE);
        String theme = sharedPreferences.getString("light","Nothing");

        if(theme.equals("light")){

            findViewById(R.id.display_color).setBackgroundColor(Color.parseColor("#ffffff"));
            findViewById(R.id.radios).setBackgroundResource(R.drawable.whiteback_style_1);
            findViewById(R.id.key_visiable).setBackgroundResource(R.drawable.whiteback_style_1);

        }else {
            findViewById(R.id.radios).setBackgroundResource(R.drawable.style_own);
            findViewById(R.id.key_visiable).setBackgroundResource(R.drawable.style_own);
        }

        //============
        findViewById(R.id.settingsButton).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MyCalculatorActivity.this, findViewById(R.id.settingsButton));
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.three_dot_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                      if(item.getTitle().equals("Light")){

                          editor.putString("light","light");
                          editor.commit();

                          startActivity(new Intent(MyCalculatorActivity.this, MyCalculatorActivity.class));


                      }
                        if(item.getTitle().equals("Dark")){

                            editor.putString("light","dark");
                            editor.commit();
                            startActivity(new Intent(MyCalculatorActivity.this, MyCalculatorActivity.class));

                        }
                        return true;
                    }
                });

                popup.show(); //showing popup menu
            }
        });

        //=========


        final int[] i = {0};
        up_down.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i[0] == 0) {
                    visiable.setVisibility(View.GONE);
                    i[0] = 1;
                    up_down.setImageResource(R.drawable.baseline_keyboard_double_arrow_up_24);
                    if(theme.equals("dark")) {
                        findViewById(R.id.radios).setBackgroundResource(R.drawable.style_own);
                        findViewById(R.id.key_visiable).setBackgroundResource(R.drawable.style_own);
                    }
                } else {
                    visiable.setVisibility(View.VISIBLE);
                    if(theme.equals("dark")) {
                        findViewById(R.id.radios).setBackgroundResource(R.drawable.nothig_style);
                        findViewById(R.id.key_visiable).setBackgroundResource(R.drawable.nothig_style);
                    }else{
                        findViewById(R.id.visible).setBackgroundResource(R.drawable.whiteback_style_1);
                        ((TextView) findViewById(R.id.button_sin)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_cos)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_tan)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_ln)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_log)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_factorial)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_pi)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_e)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_square_root1)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_start_parenthesis)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_end_parenthesis)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_exponent111)).setTextColor(Color.BLACK);
                        ((TextView) findViewById(R.id.button_factorial1)).setTextColor(Color.BLACK);
                    }
                    i[0] = 0;
                    up_down.setImageResource(R.drawable.baseline_keyboard_double_arrow_down_24);
                }

            }
        });
        //
        findViewById(R.id.button_delete).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                MyCalculatorActivity.this.calculator.delete();
                MyCalculatorActivity.this.builder.setLength(0);
                MyCalculatorActivity.this.compringString.setLength(0);
            }
        });
        findViewById(R.id.button_delete).setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View view) {
                if (!MyCalculatorActivity.this.displayPrimary.getText().toString().trim().equals("")) {
                    View findViewById = MyCalculatorActivity.this.findViewById(R.id.display_overlay);
                    if (VERSION.SDK_INT >= 21) {
                        Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(findViewById, findViewById.getMeasuredWidth() / 2, findViewById.getMeasuredHeight(), 0.0f, (float) ((int) Math.hypot((double) findViewById.getWidth(), (double) findViewById.getHeight())));
                        createCircularReveal.setDuration(300);
                        createCircularReveal.addListener(new AnimatorListener() {
                            public void onAnimationRepeat(Animator animator) {
                            }

                            public void onAnimationStart(Animator animator) {
                            }

                            public void onAnimationEnd(Animator animator) {
                                MyCalculatorActivity.this.calculator.setText("");
                            }

                            public void onAnimationCancel(Animator animator) {
                                MyCalculatorActivity.this.calculator.setText("");
                            }
                        });
                        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(findViewById, "alpha", new float[]{0.0f});
                        ofFloat.setInterpolator(new DecelerateInterpolator());
                        ofFloat.setDuration(200);
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playSequentially(new Animator[]{createCircularReveal, ofFloat});
                        findViewById.setAlpha(1.0f);
                        animatorSet.start();
                    } else {
                        MyCalculatorActivity.this.calculator.setText("");
                    }
                }
                return false;
            }
        });
        this.calculator = new Calculator(this);
        if (bundle2 != null) {
            setText(bundle2.getString("text"));
        }
        if (defaultSharedPreferences.getInt("launch_count", 5) == 0) {
            RateDialog.show(this);
            Editor edit = defaultSharedPreferences.edit();
            edit.putInt("launch_count", -1);
            edit.apply();
        }
    }


    public void onResume() {
        super.onResume();
        setText(getText());
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("text", getText());
    }

    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        setText(bundle.getString("text"));
    }

    public String getText() {
        return this.calculator.getText();
    }

    public void setText(String str) {
        this.calculator.setText(str);
    }

    public void displayPrimaryScrollLeft(String str) {
        this.displayPrimary.setText(formatToDisplayMode(str));
        this.hsv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MyCalculatorActivity.this.hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MyCalculatorActivity.this.hsv.fullScroll(17);
            }
        });
    }

    public void displayPrimaryScrollRight(String str) {
        this.displayPrimary.setText(formatToDisplayMode(str));
        this.hsv.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MyCalculatorActivity.this.hsv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MyCalculatorActivity.this.hsv.fullScroll(66);
            }
        });
    }

    public void displaySecondary(String str) {
        this.displaySecondary.setText(formatToDisplayMode(str));
    }

    private String formatToDisplayMode(String str) {
        return str.replace("/", "÷").replace("*", "×").replace("-", "−").replace("n ", "ln(").replace("l ", "log(").replace("√ ", "√(").replace("s ", "sin(").replace("c ", "cos(").replace("t ", "tan(").replace(" ", "").replace("∞", "Infinity").replace("NaN", "Undefined");
    }


    public void onPause() {
        super.onPause();
    }

    // Base Adapter
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            //Grid-View Logo


            LayoutInflater layoutInflater = getLayoutInflater();
            View Myview = layoutInflater.inflate(R.layout.item_hisrory_list, null);
            TextView display = Myview.findViewById(R.id.display);
            TextView ans = Myview.findViewById(R.id.ans);

            hashMap = arrayList.get(i);

            display.setText(hashMap.get("display"));
            ans.setText(hashMap.get("ans"));


            return Myview;
        }
    }

    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.three_dot_menu, popup.getMenu());


        popup.show();

    }
}
