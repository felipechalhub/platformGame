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

  void fire(float _x, float _y, float _w, boolean _facingRight) {

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
  void reset() {
    x = 0;
    y = -h;
    vx = 0;
    vy = 0;
    firing = false;
  }


  void update() {
    if (firing) {
      x += vx;
      y += vy;
    }
    ////check boundaries
    if (x < 0 || x > width || y < 0 || y > height) {
      reset();
    }
  }

  void display() {
    // fill(255,0,0);
    // rect(x,y,w,h);
    image(laser, x, y);
  }

  void checkBullet() {
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
