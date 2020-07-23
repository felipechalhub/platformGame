import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class sketch_200508a extends PApplet {

int scn;
final int W=64, H=64;
PImage bk, bk2, bk3, platform1, platform2, platform3, platform4, laser, laser2,  robotHealthBar;

PImage zombieHealthImage, robotHealthImage ;
float healthX, healthY;
  boolean hitZombie = false;
  boolean hitRobot = false;
 
ArrayList <health> zombieHealth;
ArrayList <health> robotHealth;
  int robotWin = 0;
  int zombieWin = 0;

  


//PImage player;
float x, x2 = 0;
float y, y2 = 9*H;
boolean left, right, up, down, l;
boolean a, d, w, s, space;

int zombieLife =50;
int robotLife = 50;
boolean shotByZombie = false;
boolean shotByRobot = false;




Player p1;
Player p2;
Platform [] platforms;
static final int MENU = 0;
static final int GAME = 1;
static final int GAME2 = 2;
static final int GAME3 = 3;
static final int GAMEOVER =9;

Bullet [] bullets;
int nextBullet;
int frames;


Timer firingTimer;








public void setup() {
  
  scn = MENU;
  bk = loadImage("backgroundCastles.png");
  bk2 = loadImage("backgroundColorFall.png");
  bk3 = loadImage("backgroundColorForest.png");
   zombieHealthImage = loadImage("zombieHeart.png");
   robotHealthImage = loadImage("screwdriver.png");


  laser = loadImage("laser.png");
  laser2 = loadImage("laser2.png");
  


  
  p1= new Player();
  p2 = new Player();

  bullets = new Bullet [50];
  firingTimer = new Timer(1000);
  firingTimer.start();


  for (int i = 0; i < bullets.length; ++i) {
    bullets[i] = new Bullet();
  }
  

 
 
  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
  
  robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));


}

public void draw() {
  //print(mouseY);
  background(0);

  switch(scn) {
  case MENU: 
    scene0(); 
    break;

  case GAME: 
    scene1(); 
    break;
    
  case GAME2: 
    scene2(); 
    break;
    
   case GAME3: 
    scene3(); 
    break;

  case GAMEOVER : 
    scene9();
    break;
  }
}
public void keyPressed() {
  switch (keyCode) {
  case 37://left
    left = true;
    break;
  case 39://right
    right = true;
    break;
  case 38://up
    up = true;
    break;
  case 40://down
    down = true;
    break;
  case 76:
    l = true;
    break;



  case 65:
    a = true;
    break;
  case 68:
    d = true;
    break;
  case 87:
    w = true;
    break;
  case 83:
    s = true;
    break;
  case 32: //space
    space = true;
    break;
  }
}
public void keyReleased() {
  switch (keyCode) {
  case 37://left
    left = false;
    break;
  case 39://right
    right = false;
    break;
  case 38://up
    up = false;
    break;
  case 40://down
    down = false;
    break;
  case 76: 
    l = false;
    break;



  case 65:
    a = false;
    break;
  case 68:
    d = false;
    break;
  case 87:
    w = false;
    break;
  case 83:
    s = false;
    break;
  case 32:
    space = false;
    break;
  }
}

public void rectangleCollisions(Player r1, Platform r2) {
  ////r1 is the player
  ////r2 is the platform rectangle
  float dx = (r1.x+r1.width1/2) - (r2.x+r2.w/2);
  float dy = (r1.y+r1.h/2) - (r2.y+r2.h/2);

  float combinedHalfWidths = r1.halfWidth + r2.halfWidth;
  float combinedHalfHeights = r1.halfHeight + r2.halfHeight;

  if (abs(dx) < combinedHalfWidths) {
    ////a collision may be happening
    ////now check on the y axis
    if (abs(dy) < combinedHalfHeights) {
      ////excellent. they are overlapping
      //determine the overlap on each axis
      float overlapX = combinedHalfWidths - abs(dx);
      float overlapY = combinedHalfHeights - abs(dy);
      ////the collision is on the axis with the 
      ////SMALLEST about of overlap
      if (overlapX >= overlapY) {
        if (dy > 0) {
          r1.collisionSide = "top";
          ////move the rectangle back to eliminate overlap
          ////before calling its display to prevent
          ////drawing object inside each other
          r1.y += overlapY;
          // println("collisionSide: "+r1.collisionSide);
        } else {
          r1.collisionSide = "bottom";
          r1.y -= overlapY;
          // println("collisionSide: "+r1.collisionSide);
        }
      } else {
        if (dx > 0) {
          r1.collisionSide = "left";
          r1.x += overlapX;
          // println("collisionSide: "+r1.collisionSide);
        } else {
          r1.collisionSide = "right";
          r1.x -= overlapX;
          // println("collisionSide: "+r1.collisionSide);
        }
      }
    } else {
      r1.collisionSide = "none";
    }
  } else {
    r1.collisionSide = "none";
  }
  // return collisionSide;
}

public void rectangleCollisions2(Player r1, Platform r2) {
  ////r1 is the player
  ////r2 is the platform rectangle
  float dx = (r1.x2+r1.w2/2) - (r2.x+r2.w/2);
  float dy = (r1.y2+r1.h2/2) - (r2.y+r2.h/2);

  float combinedHalfWidths = r1.halfWidth2 + r2.halfWidth;
  float combinedHalfHeights = r1.halfHeight2 + r2.halfHeight;

  if (abs(dx) < combinedHalfWidths) {
    ////a collision may be happening
    ////now check on the y axis
    if (abs(dy) < combinedHalfHeights) {
      ////excellent. they are overlapping
      //determine the overlap on each axis
      float overlapX = combinedHalfWidths - abs(dx);
      float overlapY = combinedHalfHeights - abs(dy);
      ////the collision is on the axis with the 
      ////SMALLEST about of overlap
      if (overlapX >= overlapY) {
        if (dy > 0) {
          r1.collisionSide2 = "top";
          ////move the rectangle back to eliminate overlap
          ////before calling its display to prevent
          ////drawing object inside each other
          r1.y2 += overlapY;
          // println("collisionSide: "+r1.collisionSide);
        } else {
          r1.collisionSide2 = "bottom";
          r1.y2 -= overlapY;
          // println("collisionSide: "+r1.collisionSide);
        }
      } else {
        if (dx > 0) {
          r1.collisionSide2 = "left";
          r1.x2 += overlapX;
          // println("collisionSide: "+r1.collisionSide);
        } else {
          r1.collisionSide2 = "right";
          r1.x2 -= overlapX;
          // println("collisionSide: "+r1.collisionSide);
        }
      }
    } else {
      r1.collisionSide2 = "none";
    }
  } else {
    r1.collisionSide2 = "none";
  }
  // return collisionSide;
}



class Timer {
  int startTime;
  int interval;
  

  Timer(int tempTime) {
    interval=tempTime;
  }

  public void start() {
    startTime=millis();
  }


  public boolean isFinished() {
    int elapsedTime = millis() - startTime;
    if (elapsedTime>interval) {
      return true;
    } else {
      return false;
    }
  }
}
  class Platform{
  float w,h,x,y;
  String typeof;
  float halfWidth, halfHeight;

  Platform(float _x, float _y, float _w, float _h, String _typeof){
    w = _w;
    h = _h;
    x = _x;
    y = _y;
    typeof = _typeof;

    halfWidth = w/2;
    halfHeight = h/2;
  }

  public void display(){

    
    
    image(platform1, 0, 11*H);
    image(platform1, W, 11*H);
    image(platform1, 2*W, 11*H);
    image(platform1, 3*W, 11*H);
    image(platform1, 4*W, 11*H);
    image(platform2, 5*W, 11*H);
    
    image(platform1, 9*W, 13*H);
    image(platform1, 10*W, 13*H);
    image(platform1, 11*W, 13*H);
    
    image(platform1, 13*W, 11*H);
    image(platform1, 14*W, 11*H);
    image(platform1, 15*W, 11*H);




  }
    public void display2(){

    
    
    image(platform3, 0, 9*H);
    image(platform3, W, 9*H);
    image(platform3, 2*W, 9*H);
    image(platform3, 3*W, 9*H);

    
    image(platform3, 9*W, 13*H);
    image(platform3, 10*W, 13*H);
    image(platform3, 11*W, 13*H);
   
    image(platform3, 13*W, 11*H);
    image(platform3, 14*W, 11*H);
    image(platform3, 15*W, 11*H);
    
    image(platform3, 4*W, 12*H);
    image(platform3, 5*W, 12*H);
    
    image(platform3, 7*W, 6*H);
    image(platform3, 8*W, 6*H);
    image(platform3, 9*W, 6*H);
    image(platform3, 10*W, 6*H);
    
    //image(platform3, 5*W, 8*H);
    image(platform3, 11*W, 9*H);




  }
  public void display3(){
    
    
    image(platform4, 0, 9*H);
    image(platform4, W, 9*H);
    image(platform4, 2*W, 9*H);
    image(platform4, 3*W, 9*H);
       
    image(platform4, 13*W, 11*H);
    image(platform4, 14*W, 11*H);
    image(platform4, 15*W, 11*H);
    
    
    image(platform4, 6*W, 13*H);
    image(platform4, 7*W, 13*H);
    image(platform4, 8*W, 13*H);
    image(platform4, 9*W, 13*H);
    image(platform4, 10*W, 13*H);

   
    
    image(platform4, 6*W, 7*H);
    image(platform4, 7*W, 7*H);
    image(platform4, 8*W, 7*H);
    image(platform4, 9*W, 7*H);
    

  }
}
 class Player {

  //PLAYER1
  PImage p1 = loadImage("zombie.png");
  float width1, h, x, y, vx, vy, 
    accelerationX, accelerationY, 
    speedLimit, friction, bounce, gravity;
  boolean isOnGround;
  float jumpForce;
  float halfWidth, halfHeight;
  int currentFrame;
  String collisionSide;
  boolean facingRight;
  int frameSequence;
  
    //PLAYER2
  PImage p2 = loadImage("robot.png");
  float w2, h2, x2, y2, vx2, vy2, 
    accelerationX2, accelerationY2, 
    speedLimit2, friction2, bounce2, gravity2;
  boolean isOnGround2;
  float jumpForce2;
  float halfWidth2, halfHeight2;
  int currentFrame2;
  String collisionSide2;
  boolean facingRight2;
  int frameSequence2;






  Player() {
    resetPlayer1();
    
  
    resetPlayer2();




  }
  public void update() {
    if (left) {
      accelerationX = -0.2f;
      friction = 1;
      facingRight = false;
    }
    if (right) {
      accelerationX = 0.2f;
      friction = 1;
      facingRight = true;
    }
    if (!left&&!right) {
      accelerationX = 0;
      friction = 0.96f;
      gravity = 0.3f;
    } else if (left&&right) {
      accelerationX = 0;
      friction = 0.96f;
      gravity = 0.3f;
    }
    if (up && isOnGround) {
      vy += jumpForce;
      isOnGround = false;
      friction = 1;
    }
    vx += accelerationX;
    vy += accelerationY;

    ////apply the forces of the universe
    if (isOnGround) {
      vx *= friction;
    }
    vy += gravity;

    ////correct for maximum speeds
    if (vx > speedLimit) {
      vx = speedLimit;
    }
    if (vx < -speedLimit) {
      vx = -speedLimit;
    }
    if (vy > speedLimit * 2) {
      vy = speedLimit * 2;
    }
    ////move the player
    x+=vx;
    y+=vy;
  }
  
 public void update2() {
    if (a) {
      accelerationX2 = -0.2f;
      friction2 = 1;
      facingRight2 = false;
    }
    if (d) {
      accelerationX2 = 0.2f;
      friction2 = 1;
      facingRight2 = true;
    }
    if (!a&&!d) {
      accelerationX2 = 0;
      friction2 = 0.96f;
      gravity2 = 0.3f;
    } else if (a&&d) {
      accelerationX2 = 0;
      friction2 = 0.96f;
      gravity2 = 0.3f;
    }
    if (w && isOnGround2) {
      vy2 += jumpForce2;
      isOnGround2 = false;
      friction2 = 1;
    }
    vx2 += accelerationX2;
    vy2 += accelerationY2;

    ////apply the forces of the universe
    if (isOnGround2) {
      vx2 *= friction2;
    }
    vy2 += gravity2;

    ////correct for maximum speeds
    if (vx2 > speedLimit2) {
      vx2 = speedLimit2;
    }
    if (vx2 < -speedLimit2) {
      vx2 = -speedLimit2;
    }
    if (vy2 > speedLimit2 * 2) {
      vy2 = speedLimit2 * 2;
    }
    ////move the player
    x2+=vx2;
    y2+=vy2;
  }



  public void display1() {
    image(p1, x, y);
  }
  public void display2(){
    image(p2,x2,y2);
    }



  public void checkBoundaries() {

    if (x < -32) {

      x = -32;
    }
    if (x  > width-32) {

      x = width-32;
    }
    if (y < 0) {
    }
    //if (y > height-100) {
    //  scn+=1;
    //  resetPlayer1();
    //  resetPlayer2();

    //}
  }


  public void checkBoundaries2() {

    if (x2 < -32) {

      x2 = -32;
    }
    if (x2  > width-32) {

      x2 = width-32;
    }
    if (y2 < 0) {
    }
    //if (y2 > height-100) {
    //  scn+=1;
    //  resetPlayer2();
    //  resetPlayer1();
     
    //}
  }

  public void checkPlatforms() {
    ////update for platform collisions
    if (collisionSide == "bottom" && vy >= 0) {
      isOnGround = true;
      ////flip gravity to neutralize gravity's effect
      vy = -gravity;
    } else if (collisionSide == "top" && vy <= 0) {
      vy = 0;
    } else if (collisionSide == "right" && vx >= 0) {
      vx = 0;
    } else if (collisionSide == "left" && vx <= 0) {
      vx = 0;
    }
    if (collisionSide != "bottom" && vy > 0) {
      isOnGround = false;
    }
  }
  
    public void checkPlatforms2() {
    ////update for platform collisions
    if (collisionSide2 == "bottom" && vy2 >= 0) {
      isOnGround2 = true;
      ////flip gravity to neutralize gravity's effect
      vy2 = -gravity2;
    } else if (collisionSide2 == "top" && vy2 <= 0) {
      vy2 = 0;
    } else if (collisionSide2 == "right" && vx2 >= 0) {
      vx2 = 0;
    } else if (collisionSide2 == "left" && vx2 <= 0) {
      vx2 = 0;
    }
    if (collisionSide2 != "bottom" && vy2 > 0) {
      isOnGround2 = false;
    }
  }
  
    public void resetPlayer1(){
      x = 850;
      y = 15;
      vx = 0;
      vy = 0;
      accelerationX = 0;
      accelerationY = 0;
      speedLimit = 5;
      friction = 0.96f;
      bounce = -0.7f;
      gravity = 3;
      isOnGround = false;
      jumpForce = -13;


      currentFrame = 0;
      collisionSide = "";
      frameSequence = 6;  
  }
  
  public void resetPlayer2(){
      x2 = 10;
      y2 = 10;
      vx2 = 0;
      vy2 = 0;
      accelerationX2 = 0;
      accelerationY2 = 0;
      speedLimit2 = 5;
      friction2 = 0.96f;
      bounce2 = -0.7f;
      gravity2 = 3;
      isOnGround2 = false;
      jumpForce2 = -13;


      currentFrame2 = 0;
      collisionSide2 = "";
      frameSequence2 = 6;
  }

  
}
public void drawButton(float x, float y, float w, float h, int filling, String caption){
  fill(filling);
  stroke(255);
  strokeWeight(1);
  rectMode(CENTER);
  textAlign(CENTER,CENTER);
  textSize(h/2);  //height/2
  rect(x,y,w,h);
  fill(255);
  text(caption,x,y);
}

public void drawButton2(float x, float y, float w, float h, int filling, String caption){
  fill(filling);
  stroke(255);
  strokeWeight(1);
  rectMode(CENTER);
  textAlign(CENTER,CENTER);
  textSize(h/2);  //height/2
  rect(x,y,w,h);
  fill(255);
  text(caption,x,y);
}


class health{

  float healthX, healthY;
 
health(float _healthX, float _healthY){
  healthX = _healthX;
  healthY = _healthY;

  
}
  
  public void displayZombieHealth(){
 image(zombieHealthImage,healthX, healthY);
 zombieHealthImage.resize(64,64);

  }
  public void displayRobotHealth(){
    image(robotHealthImage, healthX,healthY);
    robotHealthImage.resize(48,48);
  }
  
}
  class Bullet {
  float w, h, x, y;
  float halfWidth, halfHeight;
  float vx, vy;
  boolean firing;
  
  Bullet() {
    w = 35;
    h = 10;
    x = 0;
    y = -h;
    halfWidth = w/2;
    halfHeight = h/2;
    vx = 0;
    vy = 0;
    boolean firing = false;
    

  }

  public void fire(float _x, float _y, float _w, boolean _facingRight) {

    if (!firing) { 
      y = _y+60;

      firing = true;
      if (_facingRight == true) {
        vx = 18;
        x = _x + _w +35;
      } else {
        vx = -15;
        x = _x;
      }
    }
  }
  public void reset() {
    x = 0;
    y = -h;
    vx = 0;
    vy = 0;
    firing = false;
  }


  public void update() {
    if (firing) {
      x += vx;
      y += vy;
    }
    ////check boundaries
    if (x < 0 || x > width || y < 0 || y > height) {
      reset();
    }
  }

  public void display() {
    // fill(255,0,0);
    // rect(x,y,w,h);
    image(laser, x, y);
  }

  public void checkBullet() {
    float d = dist(p1.x, p1.y+50, x, y);
    float d2 = dist(p2.x2, p2.y2+50, x, y);
    
    
   if (d<50 && shotByZombie == false){
     hitZombie = true;
     zombieLife -=1; 
      print(zombieLife + "    ");      
    }
      if(d2<50 && shotByRobot == false){
        hitRobot = true;
      robotLife -=1;
      print(robotLife + "      ");
  }


  }

}
public void mousePressed(){
   if(mousePressed){
     if(scn==0 && mouseX>(width/2)-40 && mouseX<(width/2)+40 && mouseY> 390 && mouseY<430)
     scn = 1;
     //if( scn==1 && mouseX>460 && mouseX<440 && mouseY>230 && mouseY<270)
     //scn = 2;
     if( scn==9 && mouseX>360 && mouseX<440 && mouseY>230 && mouseY<270)
     scn = 0;
   }
  
  
}
int fill1 = color(148,0,211);
public void scene0(){
  drawButton((width/2),(height/2)-100,80,40,fill1,"PLAY");
  text( "ROBOT X ZOMBIE", width/2,height/2);
  text("By Felipe Portela Chalhub", (width/2)+300, (height/2)+300);
}
public void scene1() {
  background(bk);


  platform1 = loadImage("platform_13.png");
  platform2 = loadImage("platform_14.png");
  




  p1.update();
  p1.checkBoundaries();
  p1.display1();

  p2.display2();
  p2.checkBoundaries2();
  p2.update2();


  if (space) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p2.x2, p2.y2, p2.w2, p2.facingRight2);
      nextBullet = (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByRobot = true;
      shotByZombie=false;
    }
  }

  if (l) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p1.x, p1.y, p1.width1, p1.facingRight);
      nextBullet= (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByRobot=false;
      shotByZombie=true;
    }
  }


  for (int i = 0; i < bullets.length; i++) {
    bullets[i].update();
    bullets[i].display();
    bullets[i].checkBullet();
  }





  for (int i=0; i< zombieHealth.size(); i++) {
    zombieHealth.get(i).displayZombieHealth(); 
  
    if(hitZombie){
     zombieHealth.remove(0); 
     hitZombie = false;
     shotByZombie = true;
    }
    if(zombieHealth.size() == 0 || p1.y > height-100 ){

     robotWin +=1 ;
     
   
     p1.resetPlayer1();
     p2.resetPlayer2();
     
  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
  
    robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
  
     
     
     

     print(robotWin);
             scn=2;
  
  }
  }
    
   
    
  
    for (int i=0; i< robotHealth.size(); i++) {
    robotHealth.get(i).displayRobotHealth(); 
    
    if(hitRobot){
     robotHealth.remove(0); 
     hitRobot = false;
     shotByRobot = true;
    }
    
    if(robotHealth.size() == 0  || p2.y2 > height -100){

     zombieWin +=1 ;


   
  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
  
  robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
  
  //drawButton3(400,250,80,40,fill1,"PLAY");

     
     p1.resetPlayer1();
     p2.resetPlayer2();
     print(zombieWin);
             scn=2;
  
  }
    }

    

  


  platforms = new Platform[13];  
  platforms[0] = new Platform (-W, 9*H, W+10, 64, "safe");
  platforms[1] = new Platform (0, 9*H, W+10, 64, "safe");
  platforms[2] = new Platform ((W)-1, 9*H, W+10, 64, "safe");
  platforms[3] = new Platform ((2*W)-1, 9*H, W+10, 64, "safe");
  platforms[4] = new Platform ((3*W)-1, 9*H, W+10, 64, "safe");
  platforms[5] = new Platform ((4*W)-1, 9*H, W+32, 64, "safe");


  platforms[6] = new Platform ((8*W)-1, 11*H, W+10, 64, "safe");
  platforms[7] = new Platform ((9*W)-1, 11*H, W+10, 64, "safe");
  platforms[8] = new Platform ((10*W)-1, 11*H, W+32, 64, "safe");


  platforms[9] = new Platform ((12*W)-1, 9*H, W+10, 64, "safe");
  platforms[10] = new Platform ((13*W)-1, 9*H, W+10, 64, "safe");
  platforms[11] = new Platform ((14*W)-1, 9*H, W+10, 64, "safe");
  platforms[12] = new Platform ((15*W)-1, 9*H, W+108, 64, "safe");




  for (int i = 0; i < platforms.length; i++) {
    platforms[i].display();
  }
  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions(p1, platforms[i]);
    p1.checkPlatforms();
  }


  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions2(p2, platforms[i]);
    p2.checkPlatforms2();
  }
}
public void scene2(){
  background(bk2);
  


  platform3 = loadImage("platform_15.png");



  p1.update();
  p1.checkBoundaries();
  p1.display1();

  p2.display2();
  p2.checkBoundaries2();
  p2.update2();


  if (space) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p2.x2, p2.y2, p2.w2, p2.facingRight2);
      nextBullet = (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByZombie=false;
      shotByRobot = true;
    }
  }

  if (l) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p1.x, p1.y, p1.width1, p1.facingRight);
      nextBullet= (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByZombie=true;
      shotByRobot=false;
    }
  }


  for (int i = 0; i < bullets.length; i++) {
    bullets[i].update();
    bullets[i].display();
    bullets[i].checkBullet();
  }
  





  for (int i=0; i< zombieHealth.size(); i++) {
    zombieHealth.get(i).displayZombieHealth(); 
    
    if(hitZombie){
     zombieHealth.remove(0); 
     hitZombie = false;
     shotByZombie = true;
    }
    if(zombieHealth.size() == 0|| p1.y > height-100 ){
      scn=3;
     robotWin +=1 ;
     
    

  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
    
   robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
  
     p1.resetPlayer1();
     p2.resetPlayer2();
     
     print(robotWin);
    }
   
    
  }
    for (int i=0; i< robotHealth.size(); i++) {
    robotHealth.get(i).displayRobotHealth(); 
    
    if(hitRobot){
     robotHealth.remove(0); 
     hitRobot = false;
     shotByRobot = true;
    }
    
    if(robotHealth.size() == 0 || p2.y2 > height -100){
      scn=3;
     zombieWin +=1 ;


  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
      
     
  robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
     

     p1.resetPlayer1();
     p2.resetPlayer2();

       

    //    drawButton3(400,250,80,40,fill1,"PLAY");

     
     
     print(zombieWin);
    }

    

  }


  platforms = new Platform[18];  
  platforms[0] = new Platform (-W, 7*H, W+10, 128, "safe");
  platforms[1] = new Platform (0, 7*H, W+10, 128, "safe");
  platforms[2] = new Platform (W-1, 7*H, W+10, 128, "safe");
  platforms[3] = new Platform ((2*W)-1, 7*H, W+32, 128, "safe");




  platforms[4] = new Platform ((8*W)-1, 11*H, W+10, 128, "safe");
  platforms[5] = new Platform ((9*W)-1, 11*H, W+10, 128, "safe");
  platforms[6] = new Platform ((10*W)-1, 11*H, W+32, 128, "safe");

  platforms[7] = new Platform ((12*W)-1, 9*H, W+10, 128, "safe");
  platforms[8] = new Platform ((13*W)-1, 9*H, W+10, 128, "safe");
  platforms[9] = new Platform ((14*W)-1, 9*H, W+10, 128, "safe");
  platforms[10] = new Platform ((15*W)-1, 9*H, W+100, 128, "safe");
  
  platforms[11] = new Platform ((3*W)-1, 10*H, W+10, 128, "safe");
  platforms[12] = new Platform ((4*W)-1, 10*H, W+32, 128, "safe");
  
  platforms[13] = new Platform ((6*W)-1, 4*H, W+10, 128, "safe");
  platforms[14] = new Platform ((7*W)-1, 4*H, W+10, 128, "safe"); 
  platforms[15] = new Platform ((8*W)-1, 4*H, W+10, 128, "safe");
  platforms[16] = new Platform ((9*W)-1, 4*H, W+32, 128, "safe");

   platforms[17] = new Platform ((10*W)-1, 7*H, W+32, 128, "safe");

  
  




  for (int i = 0; i < platforms.length; i++) {
    platforms[i].display2();
  }
  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions(p1, platforms[i]);
    p1.checkPlatforms();
  }


  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions2(p2, platforms[i]);
    p2.checkPlatforms2();
  }

  
}
public void scene3(){
  background(bk3);
  platform4 = loadImage("platform_16.png");
  
  
   p1.update();
  p1.checkBoundaries();
  p1.display1();

  p2.display2();
  p2.checkBoundaries2();
  p2.update2();


  if (space) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p2.x2, p2.y2, p2.w2, p2.facingRight2);
      nextBullet = (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByZombie=false;
      shotByRobot = true;
    }
  }

  if (l) {
    if (firingTimer.isFinished()) {
      bullets[nextBullet].fire(p1.x, p1.y, p1.width1, p1.facingRight);
      nextBullet= (nextBullet+1)%bullets.length;
      firingTimer.start();
      shotByZombie=true;
      shotByRobot=false;
    }
  }


  for (int i = 0; i < bullets.length; i++) {
    bullets[i].update();
    bullets[i].display();
    bullets[i].checkBullet();
  }
  





  for (int i=0; i< zombieHealth.size(); i++) {
    zombieHealth.get(i).displayZombieHealth(); 
    
    if(hitZombie){
     zombieHealth.remove(0); 
     hitZombie = false;
     shotByZombie = true;
    }
    if(zombieHealth.size() == 0 || p1.y > height-100 ){
      scn=9;
     robotWin +=1 ;
     
  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
    
   robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
  
     print(robotWin);
     p1.resetPlayer1();
     p2.resetPlayer2();
    }
   
    
  }
    for (int i=0; i< robotHealth.size(); i++) {
    robotHealth.get(i).displayRobotHealth(); 
    
    if(hitRobot){
     robotHealth.remove(0); 
     hitRobot = false;
     shotByRobot = true;
    }
    
    if(robotHealth.size() == 0 ||  p2.y2 > height-100 ){
        scn=9;
        zombieWin +=1 ;
        
  zombieHealth = new ArrayList<health>();
  zombieHealth.add(new health(700, 100));
  zombieHealth.add(new health(750, 100));
  zombieHealth.add(new health(800, 100));
  zombieHealth.add(new health(850, 100));
  zombieHealth.add(new health(900, 100));
  zombieHealth.add(new health(950, 100));
    
   robotHealth = new ArrayList<health>();
  robotHealth.add(new health(100, 100));
  robotHealth.add(new health(150, 100));
  robotHealth.add(new health(200, 100));
  robotHealth.add(new health(250, 100));
  robotHealth.add(new health(300, 100));
  robotHealth.add(new health(350, 100));
  
     p1.resetPlayer1();
     p2.resetPlayer2();


       

    //    drawButton3(400,250,80,40,fill1,"PLAY");

     
     
     print(zombieWin);
    }

    

  }


  platforms = new Platform[17];  
  platforms[0] = new Platform (-W, 7*H, W+10, 128, "safe");
  platforms[1] = new Platform (0, 7*H, W+10, 128, "safe");
  platforms[2] = new Platform (W-1, 7*H, W+10, 128, "safe");
  platforms[3] = new Platform ((2*W)-1, 7*H, W+32, 128, "safe");


  platforms[4] = new Platform ((12*W)-1, 9*H, W+10, 128, "safe");
  platforms[5] = new Platform ((13*W)-1, 9*H, W+10, 128, "safe");
  platforms[6] = new Platform ((14*W)-1, 9*H, W+10, 128, "safe");
  platforms[7] = new Platform ((15*W)-1, 9*H, W+100, 128, "safe");
  

  platforms[8] = new Platform ((5*W)-1, 11*H, W+10, 128, "safe");
  platforms[9] = new Platform ((6*W)-1, 11*H, W+10, 128, "safe");
  platforms[10] = new Platform ((7*W)-1, 11*H, W+10, 128, "safe");
  platforms[11] = new Platform ((8*W)-1, 11*H, W+10, 128, "safe");
  platforms[12] = new Platform ((9*W)-1, 11*H, W+32, 128, "safe");


  platforms[13] = new Platform ((5*W)-1, 5*H, W+10, 128, "safe");
  platforms[14] = new Platform ((6*W)-1, 5*H, W+10, 128, "safe");
  platforms[15] = new Platform ((7*W)-1, 5*H, W+10, 128, "safe");
  platforms[16] = new Platform ((8*W)-1, 5*H, W+32, 128, "safe"); 


  
  




  for (int i = 0; i < platforms.length; i++) {
    platforms[i].display3();
  }
  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions(p1, platforms[i]);
    p1.checkPlatforms();
  }


  for (int i = 0; i < platforms.length; i++) {
    rectangleCollisions2(p2, platforms[i]);
    p2.checkPlatforms2();
  }

}
  public void scene9(){
    background(0);
    drawButton2(400,250,80,40,fill1,"Menu");
    text( "GAME OVER", width/2,height/2);
   
  }
  public void settings() {  size(1024, 1024); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "sketch_200508a" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
