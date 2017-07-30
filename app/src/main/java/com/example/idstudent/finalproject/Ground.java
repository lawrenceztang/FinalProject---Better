package com.example.idstudent.finalproject;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class Ground {

    public class Shape {
        public int x;
        public int shapeHeight;
        public int shapeWidth;
        Random rand = new Random();
        Paint paint;
        int shapeType = rand.nextInt(3);
        Shape() {
            paint = new Paint();
            paint.setColor(Color.rgb(125, 68, 30));
        }

        public void draw(Canvas canvas, Player player) {
           if(x - player.mapX + shapeWidth > 0 && x - player.mapX < canvas.getWidth()) {
               canvas.drawRect(
                       (float) (x - player.mapX),
                       (float) (shapeHeight),
                       (float) (x + shapeWidth - player.mapX),
                       (float) (canvas.getHeight()),
                       paint);
               Paint hi = new Paint();
               hi.setColor(Color.rgb(1,166,17));
               canvas.drawRect((float) (x - player.mapX), (float) (shapeHeight), (float) (x + shapeWidth - player.mapX), (float) (shapeHeight + 50), hi);
           }
        }
        public int checkCollisionX(Player player, Canvas canvas) {
            if (
                    (shapeHeight < player.y + player.getHeight() - 11) &&
                            (player.x + player.getWidth() > x - player.mapX) &&
                            (player.x < x - player.mapX)

                    ) {
                return 10;
            }
            if ((shapeHeight < player.y + player.getHeight() - 11) && (player.x + player.getWidth()  > x - player.mapX + shapeWidth) && (player.x < x - player.mapX + shapeWidth)) {
               return -10;
            }
            return 0;
        }
        public int checkCollisionY(Player player, Canvas canvas) {

          if(
                    (x - player.mapX              < player.x + player.getWidth()  || (x - player.mapX              < player.x )) &&
                    (x - player.mapX + shapeWidth > player.x + player.getWidth() || (x - player.mapX + shapeWidth > player.x ))
                    ) {

                if(shapeHeight < player.y + player.getHeight() && shapeHeight > player.y + player.getWidth()/2) {
                    fall = false;

                    return shapeHeight- player.getHeight() + 10;

                }
                fall = true;
                return 0;
            }

            return 0;
        }
    }
    public boolean fall;
    static int lastX;
    ArrayList<Shape> shapes;
    static int generatedDistance;
    Random rand = new Random();
    public Ground() {
        shapes =  new ArrayList<Shape>();
    }

    public void draw(Canvas canvas, Player player){

        for (Shape shape : shapes) {
            shape.draw(canvas, player);
        }

    }


    public void initializeDraw(int width, int height) {
        int n = 10;
        for (int i = 0; i < n; i++) {
            Shape shape = new Shape();
            shape.x = lastX;
            shape.shapeHeight = height - rand.nextInt(height / 2) - 100;
            shape.shapeWidth =  rand.nextInt(500) + 200;
            shapes.add(shape);
            lastX = shape.x+shape.shapeWidth;
        }
    }
    public void generateTerrain(int width, int height, Player player) {
        int n = 10;
        Shape shape = new Shape();
            shape.x = lastX;


            shape.shapeHeight =  height - rand.nextInt(height / 2) - 100;
            shape.shapeWidth =  rand.nextInt(500) + 200;
            shapes.add(shape);
            lastX = shape.x+shape.shapeWidth;

    }


    public int checkCollisionX(Player player, Canvas canvas) {
        int result = 0;
        for (int i = shapes.size() - 1; i >= 0; i--) {
            result = shapes.get(i).checkCollisionX(player, canvas);
            if (result != 0) {

                return result;

            }
        }
        return result;
    }
    public int checkCollisionY(Player player, Canvas canvas) {

        // y check
        int result = 0;
        //for(Shape shape : shapes) {
        if(player.xSpeed>= 0) {
            for (int i = shapes.size() - 1; i >= 0; i--) {
                result = shapes.get(i).checkCollisionY(player, canvas);
                if (result > 0) {
                    player.canJump = 1;
                    return result;

                }
                else {

                }
            }
        }
        else {
            for (int i = 0; i < shapes.size(); i++) {
                result = shapes.get(i).checkCollisionY(player, canvas);
                if (result != 0) {

                    return result;

                }
            }
        }
        return result;
    }
}



