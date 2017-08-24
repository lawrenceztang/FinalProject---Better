package com.example.idstudent.finalproject;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

import static android.R.attr.type;

public class Ground {

    public class Hole {

    }

    public class Shape {
        public int x;
        int grassX;
        public int grassNum;
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
               hi.setColor(Color.rgb(1, 166, 17));
               int width = grassBitmap.getWidth();
               grassNum = shapeWidth / Ground.grassBitmap.getWidth();
               grassX = x;
               for (int i = 0; i < grassNum; i++) {
                   canvas.drawBitmap(Ground.grassBitmap, grassX - player.mapX, shapeHeight - grassBitmap.getHeight() / 2, null);
                   grassX = grassX + Ground.grassBitmap.getWidth();
               }
               canvas.drawBitmap(Bitmap.createBitmap(Ground.grassBitmap, 0, 0, shapeWidth - grassNum * Ground.grassBitmap.getWidth(), Ground.grassBitmap.getHeight()), grassX - player.mapX, shapeHeight - grassBitmap.getHeight() / 2, null);
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
    int biome = 1;
    public boolean fall;
    static Bitmap grassBitmap;
    static int lastX;
    ArrayList<Shape> shapes;
    ArrayList<OceanChunk> oceanArray;
    static int generatedDistance;
    Random rand = new Random();
    int oceanTrue;
    public Ground() {
        shapes =  new ArrayList<Shape>();
        oceanArray = new ArrayList<OceanChunk>();
    }

    public void draw(Canvas canvas, Player player){

        for (Shape shape : shapes) {
            shape.draw(canvas, player);
        }
        for (OceanChunk ocean : oceanArray) {
            ocean.draw(canvas, player.mapX);
        }
        Paint paint = new Paint();
        paint.setColor(Color.RED);
    }


    public void initializeDraw(int width, int height) {
        int n = 10;
        for (int i = 0; i < n; i++) {
            Shape shape = new Shape();
            shape.x = lastX;
            int random = rand.nextInt();
            if (random == 0) {

                shape.shapeHeight = 99999;
                shape.shapeWidth = rand.nextInt(200) + 200;
            }
            else {
                shape.shapeHeight = height - rand.nextInt(height / 2) - 100;
                shape.shapeWidth = rand.nextInt(500) + 200;
            }
            shapes.add(shape);
            lastX = shape.x+shape.shapeWidth;
        }
    }
    public void generateTerrain(int width, int height, Player player, Canvas canvas) {
        if (biome == 1) {
            int n = 10;
            Shape shape = new Shape();
            shape.x = lastX;
            int random = rand.nextInt(5);
            if (random == 0 && shapes.get(shapes.size() - 1).shapeHeight < 99998) {

                shape.shapeHeight = 99999;
                shape.shapeWidth = rand.nextInt(200) + 200;
            } else {
                shape.shapeHeight = height - rand.nextInt(height / 2) - 100;
                shape.shapeWidth = rand.nextInt(500) + 200;
            }
            shapes.add(shape);
            lastX = shape.x + shape.shapeWidth;
            generatedDistance = shape.x;
            if (rand.nextInt(10) == 1) {
                biome = 2;
            }
        }
        else if (biome == 2) {
            OceanChunk ocean = new OceanChunk(lastX, rand.nextInt(5000) + 2000, canvas);
            oceanArray.add(ocean);
            biome = 1;
            lastX = ocean.width + lastX;
        }
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
         oceanTrue = 0;
        int result = 0;

        for(int d = 0; d < oceanArray.size(); d++) {
            if(player.x > oceanArray.get(d).lastX - player.mapX && player.x < oceanArray.get(d).lastX + oceanArray.get(d).width - player.mapX) {
                oceanTrue = 1;
            }
        }

if(oceanTrue == 0) {
    if (player.xSpeed >= 0) {
        for (int i = shapes.size() - 1; i >= 0; i--) {
            result = shapes.get(i).checkCollisionY(player, canvas);
            if (result > 0) {
                player.canJump = 1;
                return result;

            } else {

            }
        }
    } else {
        for (int i = 0; i < shapes.size(); i++) {
            result = shapes.get(i).checkCollisionY(player, canvas);
            if (result != 0) {

                return result;

            }
        }
    }
    return result;
}
else {
    result = player.y + 1;
}
return  result;
    }

    public class OceanChunk {
    int lastX;
        int width;
        int wavePreviousHeight;
        boolean goUp = true;
        int[] wavePoints;
        int seaLevel;
        int maximumRise;

        public OceanChunk(int lastX, int width, Canvas canvas) {

        this.lastX = lastX;
            this.width = width;
            wavePoints = new int[width];
            maximumRise = canvas.getHeight() / 100;
            seaLevel = canvas.getHeight() / 4;
            int slope = -1;
            int waveLength = rand.nextInt(canvas.getWidth() / 100 + 50);
            int counter = 0;
            int waveLengthCounter = 0;
            for(int i = 0; i < width; i++) {
                try {
                    if (goUp == true && wavePoints[i - 1] > seaLevel - maximumRise) {
                    if(waveLength / 3 == waveLengthCounter) {
                        slope--;
                    }
                    if(waveLength / 3 * 2 == waveLengthCounter) {
                        slope++;
                    }
                    }


                    if (goUp == false && wavePoints[i - 1] < seaLevel + maximumRise) {
                        if(waveLength / 3 == waveLengthCounter) {
                            slope++;
                        }
                        if(waveLength / 3 * 2 == waveLengthCounter) {
                            slope--;
                        }
                    }
                    wavePoints[i] = slope+wavePoints[i - 1];
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    wavePoints[0] = seaLevel;
                }

                if(waveLengthCounter >= waveLength  || wavePoints[i] <= seaLevel - maximumRise || wavePoints[i] >= seaLevel + maximumRise) {
                    waveLengthCounter = 0;
                    waveLength = rand.nextInt(canvas.getWidth() / 50);
                    goUp = !goUp;
                    if(goUp == true) { slope = -1; }
                    else { slope = 1; }

                }
               waveLengthCounter++;
                counter++;
            }
    }

    public void draw(Canvas canvas, int playerX) {
       Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        Paint paint2 = new Paint();
        paint2.setColor(Color.rgb(179, 230, 255));
        for(int i = 0; i < width; i++) {
            if(i + lastX - playerX < canvas.getWidth() && i + lastX - playerX > 0) {
                canvas.drawCircle(i + lastX - playerX, wavePoints[i], 1, paint);
                canvas.drawLine(i + lastX - playerX, wavePoints[i] + 1, i + lastX - playerX, canvas.getHeight(), paint2);
            }
        }
    }


    }
}



