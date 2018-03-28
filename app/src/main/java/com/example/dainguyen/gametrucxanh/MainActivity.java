package com.example.dainguyen.gametrucxanh;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int bitmap_width;
    int bitmap_height;
    int checkrandom[] = new int[topic_num];
    public static final int topic_num=19;
    public static final int row_no = 4;
    public static final int col_no = 4;


    public static TextView thongbao,thoigian;
    List<topic> arraytopic;
    ImageView img;
    Bitmap bitmap;
    Canvas canvas;
    Paint paint;
    int img1,img2;
    ChessBoard chessboard;
    Button reset;
    EditText edttraloi;
    String cautraloi,stranswer;
    TextView txtcautldung,goiy;
    int image;
    int cautldung=0;
    CountDownTimer time;
    int giay=100;
    int timecheck=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tapnham();

        time = new CountDownTimer(giay*1000, 1000) {
            public void onTick(long millisUntilFinished) {
                thoigian.setText( millisUntilFinished / 1000+"s");
            }
            public void onFinish() {

                edttraloi.clearAnimation();

                img.setEnabled(false);
                edttraloi.setEnabled(false);
            }
        };
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reset();
            }
        });
        img.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                timecheck++;
                if(timecheck==1)time.start();
                if(chessboard.onTouch(v, event)==1){
                    //mở hết ô
                  ;
                }
                return true;
            }
        });
        edttraloi.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String x = edttraloi.getText().toString();
                    if(x.equals(cautraloi)) {
                        time.cancel();

                        edttraloi.clearAnimation();

                        cautldung++;


                        goiy.setText("[Đáp án] "+stranswer);
                        Bitmap subbitmap = null;
                        subbitmap = BitmapFactory.decodeResource(getResources(), img2);
                        if (subbitmap != null) {
                            canvas.drawBitmap(subbitmap, new Rect(0,0,subbitmap.getWidth(),subbitmap.getHeight()),
                                    new Rect(0,0,bitmap_width, bitmap_height),paint);
                        }
                        img.setEnabled(false);
                        edttraloi.setEnabled(false);
                    }
                }catch (Exception e){
                }
            }
        });
    }
    public void reset(){

        reset.clearAnimation();
        time.cancel();
        createTopic();
        randomTopic();
        img.setEnabled(true);
        edttraloi.setEnabled(true);
        bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        paint.setStrokeWidth(2);
        chessboard = new ChessBoard(MainActivity.this, canvas, paint, bitmap_width, bitmap_height, col_no, row_no, img1, img2);
        chessboard.Init();
        chessboard.drawChessBoard();
        img.setImageBitmap(bitmap);

        edttraloi.setText("");
        thongbao.setText("");
        image=R.drawable.bird;
        thoigian.setText(giay-1+"s");
        timecheck=0;
    }





    public void randomTopic(){
        Random r = new Random();
        int random = r.nextInt(topic_num - 0) + 0;
        while(checkrandom[random]==1){
            random = r.nextInt(topic_num - 0) + 0;
            int dem=0;
            for (int i = 0; i < topic_num; i++) {
                if(checkrandom[i]==1) dem++;
            }
            if(dem==topic_num) for (int i = 0; i < topic_num; i++) {
                checkrandom[i]=0;
            }
            if (checkrandom[random]!=1) break;
        }
        checkrandom[random]++;
        topic t = arraytopic.get(random);
        img1=t.getImage();
        img2=t.getBackground();
        cautraloi=t.getAnswer();
        goiy.setText(t.getInfo()+"");
        stranswer=t.getStranswer();
    }

    public void createTopic(){
        arraytopic = new ArrayList<>();
        arraytopic.add(new topic(0,"[Gợi ý] Có 7 chữ cái",image,R.drawable.bienhieu,"bienhieu","Biển hiệu"));
        arraytopic.add(new topic(1,"[Gợi ý] Có 5 chữ cái",image,R.drawable.casio,"casio","Casio"));
        arraytopic.add(new topic(2,"[Gợi ý] Có 6 chữ cái",image,R.drawable.congty,"congty","Công ty"));
        arraytopic.add(new topic(3,"[Gợi ý] Có 11 chữ cái",image,R.drawable.hoclienthong,"hoclienthong","Học liên thông"));
        arraytopic.add(new topic(4,"[Gợi ý] Có 5 chữ cái",image,R.drawable.lolieu,"lolieu","Lộ liễu"));
        arraytopic.add(new topic(5,"[Gợi ý] Có 4 chữ cái",image,R.drawable.voco,"voco","Vô cơ"));
        arraytopic.add(new topic(6,"[Gợi ý] Có 10 chữ cái",image,R.drawable.bananhkhoa,"bananhkhoa","Bạn Anh Khoa"));
        arraytopic.add(new topic(7,"[Gợi ý] Có 5 chữ cái",image,R.drawable.baotu,"baotu","Bao tử"));
        arraytopic.add(new topic(8,"[Gợi ý] Có 5 chữ cái",image,R.drawable.aomua,"aomua","Áo mưa"));
        arraytopic.add(new topic(9,"[Gợi ý] Có 6 chữ cái",image,R.drawable.bongda,"bongda","Bóng đá"));
        arraytopic.add(new topic(10,"[Gợi ý] Có 6 chữ cái",image,R.drawable.batron,"batron","Ba trợn"));
        arraytopic.add(new topic(11,"[Gợi ý] Có 9 chữ cái",image,R.drawable.xuongrong,"xuongrong","Xương rồng"));
        arraytopic.add(new topic(12,"[Gợi ý] Có 6 chữ cái",image,R.drawable.detien,"detien","Đê tiện"));
        arraytopic.add(new topic(13,"[Gợi ý] Có 6 chữ cái",image,R.drawable.congbo,"congbo","Công bố"));
        arraytopic.add(new topic(14,"[Gợi ý] Có 7 chữ cái",image,R.drawable.matkhau,"matkhau","Mật khẩu"));
        arraytopic.add(new topic(15,"[Gợi ý] Có 6 chữ cái",image,R.drawable.haclao,"haclao","Hắc lào"));
        arraytopic.add(new topic(16,"[Gợi ý] Có 9 chữ cái",image,R.drawable.nambancau,"nambancau","Nam bán cầu"));
        arraytopic.add(new topic(17,"[Gợi ý] Có 6 chữ cái",image,R.drawable.yamaha,"yamaha","Yamaha"));
        arraytopic.add(new topic(18,"[Gợi ý] Có 10 chữ cái",image,R.drawable.sontungmtp,"sontungmtp","Sơn Tùng MTP"));
    }

    public void tapnham(){
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        bitmap_width=width;
        bitmap_height=width;


        img = (ImageView) findViewById(R.id.imageView);
        txtcautldung = (TextView)findViewById(R.id.txtcautldung);
        thongbao = (TextView)findViewById(R.id.thongbao);
        thoigian = (TextView)findViewById(R.id.thoigian);
        goiy=(TextView)findViewById(R.id.goiy);
        reset = (Button)findViewById(R.id.reset);
        edttraloi = (EditText) findViewById(R.id.traloi);
        image=R.drawable.bird;

        createTopic();
        randomTopic();

        bitmap = Bitmap.createBitmap(bitmap_width, bitmap_height, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setARGB(255, 0, 0, 0);
        paint.setStrokeWidth(2);
        chessboard = new ChessBoard(MainActivity.this, canvas, paint, bitmap_width, bitmap_height, col_no, row_no, img1, img2);
        chessboard.Init();
        chessboard.drawChessBoard();
        img.setImageBitmap(bitmap);


    }
}