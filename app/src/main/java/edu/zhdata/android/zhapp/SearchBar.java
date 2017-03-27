package edu.zhdata.android.zhapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import edu.zhdata.android.zhapp.entity.SQuestion;
import edu.zhdata.android.zhapp.entity.STopic;
import edu.zhdata.android.zhapp.entity.SUser;
import edu.zhdata.android.zhapp.tools.BaseNetwork;
import edu.zhdata.android.zhapp.voice.speech.setting.IatSettings;
import edu.zhdata.android.zhapp.voice.speech.util.ApkInstaller;
import edu.zhdata.android.zhapp.voice.speech.util.JsonParser;

public class SearchBar extends FragmentActivity {

    private static String TAG = MainActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

    private Toast mToast;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语记安装助手类
    ApkInstaller mInstaller;

    private ImageButton button;

    private ImageView imageView;
    private EditText editText;
    List<SQuestion> qlist;
    List<STopic> tlist;
    List<SUser> ulist;
    Handler handler;
    List<Integer> getdatalist;
    List<String> getdatalist1;
    ArrayList datalist=new ArrayList<String>();
    ArrayList datalist1=new ArrayList<String>();
    ArrayList datalist2=new ArrayList<String>();
    ArrayAdapter arrayAdapter,arrayAdapter1,arrayAdapter2;
    private SharedPreferences preferences;
    public String Sid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);
        editText=(EditText)findViewById(R.id.search_edit1) ;

        preferences=getSharedPreferences("first_pref",MODE_PRIVATE);
        Sid=preferences.getString("sid","");

        // Initialize the ViewPager and set an adapter
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(pager);
        imageView=(ImageView)findViewById(R.id.search_img1);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        button=(ImageButton)findViewById(R.id.button2);

        initLayout();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(SearchBar.this, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new RecognizerDialog(SearchBar.this, mInitListener);

        mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);


        arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, datalist);
        arrayAdapter1 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, datalist1);
        arrayAdapter2 = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, datalist2);
        editText=(EditText)findViewById(R.id.search_edit1);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(editText.getText().toString().trim()) == false) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        BaseNetwork bn = new BaseNetwork();
                                        qlist = bn.searchQuestion(editText.getText().toString(), Sid);
                                        Message ms = new Message();
                                        ms.what = 1;
                                        datalist.clear();
                                        getdatalist1 = new ArrayList<String>();
                                        for (int i = 0; i < qlist.size(); i++) {//从放回的数据中提取自己所需要的数据，并放到datalist
                                            datalist.add(qlist.get(i).getQname());
                                            getdatalist1.add(qlist.get(i).getQid() + "");
                                        }
                                        handler.sendMessage(ms);
                                    }catch (NullPointerException e){
                                        Message ms=new Message();
                                        ms.what=4;
                                        handler.sendMessage(ms);
                                    }
                                }
                            }).start();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        BaseNetwork bn = new BaseNetwork();
                                        tlist = bn.searchTopic(editText.getText().toString(), Sid);
                                        Message ms = new Message();
                                        ms.what = 2;
                                        datalist1.clear();
                                        getdatalist = new ArrayList<Integer>();
                                        for (int i = 0; i < tlist.size(); i++) {//从放回的数据中提取自己所需要的数据，并放到datalist
                                            datalist1.add(tlist.get(i).getTname());
                                            getdatalist.add(tlist.get(i).getTid());
                                        }
                                        handler.sendMessage(ms);
                                    }catch (NullPointerException e){
                                        Message ms=new Message();
                                        ms.what=4;
                                        handler.sendMessage(ms);
                                    }
                                }
                            }).start();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        BaseNetwork bn = new BaseNetwork();
                                        ulist = bn.searchUser(editText.getText().toString(), Sid);
                                        Message ms = new Message();
                                        ms.what = 3;
                                        datalist2.clear();
                                        getdatalist1 = new ArrayList<String>();
                                        for (int i = 0; i < ulist.size(); i++) {//从放回的数据中提取自己所需要的数据，并放到datalist
                                            datalist2.add(ulist.get(i).getPname());
                                            getdatalist1.add(ulist.get(i).getP_id());
                                        }
                                        handler.sendMessage(ms);
                                    }catch (NullPointerException e){
                                        Message ms=new Message();
                                        ms.what=4;
                                        handler.sendMessage(ms);
                                    }
                                }
                            }).start();
                }else

                {
                    datalist.clear();
                    datalist1.clear();
                    datalist2.clear();
                    Message ms = new Message();
                    ms.what = 1;
                    handler.sendMessage(ms);
                    Message ms1 = new Message();
                    ms1.what = 2;
                    handler.sendMessage(ms1);
                    Message ms2 = new Message();
                    ms2.what = 3;
                    handler.sendMessage(ms2);
                }
            }

        });
        handler=new Handler(){
            public void handleMessage(Message msg){
                if(msg.what==1){
                    arrayAdapter.notifyDataSetChanged();
                }
                else if(msg.what==2){
                    arrayAdapter1.notifyDataSetChanged();
                }
                else if(msg.what==3){
                    arrayAdapter2.notifyDataSetChanged();
                }else if (msg.what==4){
                    Toast.makeText(SearchBar.this,"请检查网络连接",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void initLayout() {

        mEngineType = SpeechConstant.TYPE_CLOUD;

    }

    int ret = 0; // 函数调用返回值


    public void onClick(View view) {
        if( null == mIat ){
            // 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
            this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，且有调用 createUtility 进行初始化" );
            return;
        }

        switch (view.getId()) {
            // 开始听写
            // 如何判断一次听写结束：OnResult isLast=true 或者 onError
            case R.id.button2:
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(SearchBar.this, "iat_recognize");

                editText.setText(null);// 清空显示内容
                mIatResults.clear();
                // 设置参数
                setParam();
                boolean isShowDialog = mSharedPreferences.getBoolean(
                        getString(R.string.pref_key_iat_show), true);
                if (isShowDialog) {
                    // 显示听写对话框
                    mIatDialog.setListener(mRecognizerDialogListener);
                    mIatDialog.show();
                    showTip(getString(R.string.text_begin));
                } else {
                    // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                        showTip("听写失败,错误码：" + ret);
                    } else {
                        showTip(getString(R.string.text_begin));
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败，错误码：" + code);
            }
        }
    };

    /**
     * 上传联系人/词表监听器。
     */
    private LexiconListener mLexiconListener = new LexiconListener() {

        @Override
        public void onLexiconUpdated(String lexiconId, SpeechError error) {
            if (error != null) {
                showTip(error.toString());
            } else {
                showTip(getString(R.string.text_upload_success));
            }
        }
    };

    /**
            * 听写监听器。
            */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {
                // TODO 最后的结果
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        editText.setText(resultBuffer.toString().substring(0,resultBuffer.toString().length()-1));
        editText.setSelection(editText.length());
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    /**
     * 获取联系人监听器。
     */
    private ContactManager.ContactListener mContactListener = new ContactManager.ContactListener() {

        @Override
        public void onContactQueryFinish(final String contactInfos, boolean changeFlag) {
            // 注：实际应用中除第一次上传之外，之后应该通过changeFlag判断是否需要上传，否则会造成不必要的流量.
            // 每当联系人发生变化，该接口都将会被回调，可通过ContactManager.destroy()销毁对象，解除回调。
            // if(changeFlag) {
            // 指定引擎类型
            runOnUiThread(new Runnable() {
                public void run() {
                    editText.setText(contactInfos);
                }
            });

            mIat.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
            mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
            ret = mIat.updateLexicon("contact", contactInfos, mLexiconListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip("上传联系人失败：" + ret);
            }
        }
    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    /**
     * 参数设置
     *
     * @param
     * @return
     */
    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }
        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if( null != mIat ){
            // 退出时释放连接
            mIat.cancel();
            mIat.destroy();
        }
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(SearchBar.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(SearchBar.this);
        super.onPause();
    }

    public String getKeyword(){
        return editText.getText().toString();
    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        private final String[] TITLES = {"问题","话题","用户"};

        MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            return SuperAwesomeCardFragment.newInstance(position);
        }
    }


}
