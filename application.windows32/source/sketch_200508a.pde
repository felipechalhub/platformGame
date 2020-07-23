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








void setup() {
  size(1024, 1024);
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

void draw() {
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
void keyPressed() {
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
void keyReleased() {
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

void rectangleCollisions(Player r1, Platform r2) {
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

void rectangleCollisions2(Player r1, Platform r2) {
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

  void start() {
    startTime=millis();
  }


  boolean isFinished() {
    int elapsedTime = millis() - startTime;
    if (elapsedTime>interval) {
      return true;
    } else {
      return false;
    }
  }
}
