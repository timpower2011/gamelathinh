package com.example.dainguyen.gametrucxanh;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChessBoard {
    Context context;
    Canvas canvas;
    Paint paint;
    int bitmap_width, bitmap_height, col_no, row_no;
    int img1,img2;
    List<Line> listLine;
    int[][] X;
    int k=0,dem=0;
    int centerRect ;
    int player;
    int check1=0,check2=0;
    int [] o1 ={0,0};
    int [] o2 ={0,0};

    public static int white = R.drawable.white;
    public static final int delaytime=250;

    public ChessBoard(Context context, Canvas canvas, Paint paint, int bitmap_width, int bitmap_height, int col_no, int row_no, int img1, int img2) {
        this.context = context;
        this.canvas = canvas;
        this.paint = paint;
        this.bitmap_width = bitmap_width;
        this.bitmap_height = bitmap_height;
        this.col_no = col_no;
        this.row_no = row_no;
        this.img1 = img1;
        this.img2 = img2;
    }

    public void Init(){
        listLine = new ArrayList<Line>();
        int du = (int)paint.getStrokeWidth()/2;
        Bitmap subbitmap = null;
        subbitmap = BitmapFactory.decodeResource(context.getResources(), white);
        if (subbitmap != null) {
            canvas.drawBitmap(subbitmap, new Rect(0,0,subbitmap.getWidth(),subbitmap.getHeight()),
                    new Rect(0,0,bitmap_width, bitmap_height),paint);
        }
        // VẼ HÀNG NGANG
        for (int i = 0; i <= row_no; i++){
            if(i==0){
              /*  listLine.add(new Line(new Point(0,i*bitmap_height/row_no+du),
                        new Point(bitmap_width,i*bitmap_height/row_no+du)));*/
            } else if(i==row_no){
                listLine.add(new Line(new Point(0,i*bitmap_height/row_no-du),
                        new Point(bitmap_width,i*bitmap_height/row_no-du)));
            } else listLine.add(new Line(new Point(0,i*bitmap_height/row_no),
                    new Point(bitmap_width,i*bitmap_height/row_no)));
        }

        // VẼ HÀNG DỌC
        for (int i = 0; i <= col_no; i++){
            if(i==0){
               /* listLine.add(new Line(new Point(i*bitmap_width/col_no+du,0),
                    new Point(i*bitmap_width/col_no+du, bitmap_height)));*/
            } else  if (i==col_no){
              /* listLine.add(new Line(new Point(i*bitmap_width/col_no-du,0),
                    new Point(i*bitmap_width/col_no-du, bitmap_height)));*/
            } else listLine.add(new Line(new Point(i*bitmap_width/col_no,0),
                    new Point(i*bitmap_width/col_no, bitmap_height)));
        }
        X = new int[row_no][col_no];
        int [] tam = {0,0,0,0,0,0,0,0};
        for (int i = 0; i < row_no; i++) {
            for (int j = 0; j < col_no; j++) {
                Random r = new Random();
                int random = r.nextInt(8 - 0) + 0;
                while(tam[random]==2){
                    random = r.nextInt(8 - 0) + 0;
                    if (tam[random]!=2) break;
                }
                tam[random]++;
                X[i][j]=random;
            }
        }
        player=1;
    }

    public void drawChessBoard (){
        Line line ;
        for ( int i = 0; i<listLine.size(); i++){
            line = listLine.get(i);
            canvas.drawLine(line.getStartPoint().getX(), line.getStartPoint().getY(), line.getEndPoint().getX(),
                    line.getEndPoint().getY(), paint);
        }

    }

    public int onTouch(final View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int width = v.getWidth();
            int height = v.getHeight();
            int cchang = width / col_no;
            int cccot = height / row_no;
            int a = y / cccot;//chi so dong
            int b = x / cchang;//chi so cot
            centerRect = bitmap_height / row_no;

            if (X[a][b] != -1) {
                k = X[a][b];
                //lượt 1
                if (player == 1) {
                    o1[0] = a;
                    o1[1] = b;
                    check1 = k;
                    drawbird(o1[0], o1[1], v);
                    player = (player + 1) % 2;
                    return 0;
                } else { //lượt 2
                    o2[0] = a;
                    o2[1] = b;
                    if (o2[0] == o1[0] && o2[1] == o1[1]) {
                        player=1;
                        drawwhite(o2[0], o2[1], v);
                        return -1;
                    }
                    check2 = k;
                    drawbird(o2[0], o2[1], v);
                    player = (player + 1) % 2;
                    dem = 1;
                    v.setEnabled(false);
                }

                if (check1 == check2 & dem == 1) {



                    X[o1[0]][o1[1]] = -1;
                    X[o2[0]][o2[1]] = -1;
                    player=1;
                    if(checkwin()==1) {
                        drawBackground(o1[0], o1[1], v);
                        drawBackground(o2[0], o2[1], v);

                        v.setEnabled(true);
                        return 1;
                    }

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            drawBackground(o1[0], o1[1], v);
                            drawBackground(o2[0], o2[1], v);
                            MainActivity.thongbao.setText("");
                            v.setEnabled(true);
                            return;
                        }
                    }, delaytime);
                } else {

                    dem = 0;
                    player=1;
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            drawwhite(o1[0], o1[1], v);
                            drawwhite(o2[0], o2[1], v);
                            MainActivity.thongbao.setText("");
                            v.setEnabled(true);
                            return;
                        }
                    }, delaytime);
                }
            }
        }
        return 0;
    }

    public int drawbird(int a,int b,View v){
        //vi trí tâm
        Point center = new Point((int) ((b + 0.5) * bitmap_height / row_no), (int) ((a + 0.5) * bitmap_width / col_no));
        Point leftTop = new Point(center.getX() - centerRect / 2, center.getY() - centerRect / 2);
        Point rightBot = new Point(center.getX() + centerRect / 2, center.getY() + centerRect / 2);
        Bitmap subbitmap = null;
        subbitmap = BitmapFactory.decodeResource(context.getResources(), img1);
        if (subbitmap != null) {
            canvas.drawBitmap(subbitmap, new Rect(k*subbitmap.getWidth()/8,0,subbitmap.getWidth()/8 *(k + 1),subbitmap.getWidth()/8),
                    new Rect(leftTop.getX() + 5, leftTop.getY() + 5, rightBot.getX() - 5, rightBot.getY() - 5), paint);
            v.invalidate();
        }
        return 0;
    }
    public int drawwhite(int a,int b,View v){
        //vi trí tâm
        Point center = new Point((int) ((b + 0.5) * bitmap_height / row_no), (int) ((a + 0.5) * bitmap_width / col_no));
        Point leftTop = new Point(center.getX() - centerRect / 2, center.getY() - centerRect / 2);
        Point rightBot = new Point(center.getX() + centerRect / 2, center.getY() + centerRect / 2);
        Bitmap subbitmap = null;
        subbitmap = BitmapFactory.decodeResource(context.getResources(), white);
        if (subbitmap != null) {
            canvas.drawBitmap(subbitmap, new Rect(b*subbitmap.getWidth()/4, a*subbitmap.getHeight()/4,subbitmap.getWidth()/4 *(b + 1),subbitmap.getHeight()/4 *(a + 1)),
                    new Rect(leftTop.getX() + 5, leftTop.getY() + 5, rightBot.getX() - 5, rightBot.getY() - 5), paint);
            v.invalidate();
        }
        return 0;
    }
    public int drawBackground(int a,int b,View v){
        //vi trí tâm
        Point center = new Point((int) ((b + 0.5) * bitmap_height / row_no), (int) ((a + 0.5) * bitmap_width / col_no));
        Point leftTop = new Point(center.getX() - centerRect / 2, center.getY() - centerRect / 2);
        Point rightBot = new Point(center.getX() + centerRect / 2, center.getY() + centerRect / 2);
        Bitmap subbitmap = null;
        subbitmap = BitmapFactory.decodeResource(context.getResources(), img2);
        if (subbitmap != null) {
            canvas.drawBitmap(subbitmap, new Rect(b*subbitmap.getWidth()/4, a*subbitmap.getHeight()/4,subbitmap.getWidth()/4 *(b + 1),subbitmap.getHeight()/4 *(a + 1)),
                    new Rect(leftTop.getX() + 5, leftTop.getY() + 5, rightBot.getX() - 5, rightBot.getY() - 5), paint);
            v.invalidate();
        }
        return 0;
    }

    public int checkwin(){
        for (int i = 0; i < row_no; i++) {
            for (int j = 0; j < col_no; j++) {
                if(X[i][j]!=-1) return 0;
            }
        }
        return 1;
    }

}
