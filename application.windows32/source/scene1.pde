void scene1() {
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
