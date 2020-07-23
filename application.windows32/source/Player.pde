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
  void update() {
    if (left) {
      accelerationX = -0.2;
      friction = 1;
      facingRight = false;
    }
    if (right) {
      accelerationX = 0.2;
      friction = 1;
      facingRight = true;
    }
    if (!left&&!right) {
      accelerationX = 0;
      friction = 0.96;
      gravity = 0.3;
    } else if (left&&right) {
      accelerationX = 0;
      friction = 0.96;
      gravity = 0.3;
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
  
 void update2() {
    if (a) {
      accelerationX2 = -0.2;
      friction2 = 1;
      facingRight2 = false;
    }
    if (d) {
      accelerationX2 = 0.2;
      friction2 = 1;
      facingRight2 = true;
    }
    if (!a&&!d) {
      accelerationX2 = 0;
      friction2 = 0.96;
      gravity2 = 0.3;
    } else if (a&&d) {
      accelerationX2 = 0;
      friction2 = 0.96;
      gravity2 = 0.3;
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



  void display1() {
    image(p1, x, y);
  }
  void display2(){
    image(p2,x2,y2);
    }



  void checkBoundaries() {

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


  void checkBoundaries2() {

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

  void checkPlatforms() {
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
  
    void checkPlatforms2() {
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
  
    void resetPlayer1(){
      x = 850;
      y = 15;
      vx = 0;
      vy = 0;
      accelerationX = 0;
      accelerationY = 0;
      speedLimit = 5;
      friction = 0.96;
      bounce = -0.7;
      gravity = 3;
      isOnGround = false;
      jumpForce = -13;


      currentFrame = 0;
      collisionSide = "";
      frameSequence = 6;  
  }
  
  void resetPlayer2(){
      x2 = 10;
      y2 = 10;
      vx2 = 0;
      vy2 = 0;
      accelerationX2 = 0;
      accelerationY2 = 0;
      speedLimit2 = 5;
      friction2 = 0.96;
      bounce2 = -0.7;
      gravity2 = 3;
      isOnGround2 = false;
      jumpForce2 = -13;


      currentFrame2 = 0;
      collisionSide2 = "";
      frameSequence2 = 6;
  }

  
}
