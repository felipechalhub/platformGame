class health{

  float healthX, healthY;
 
health(float _healthX, float _healthY){
  healthX = _healthX;
  healthY = _healthY;

  
}
  
  void displayZombieHealth(){
 image(zombieHealthImage,healthX, healthY);
 zombieHealthImage.resize(64,64);

  }
  void displayRobotHealth(){
    image(robotHealthImage, healthX,healthY);
    robotHealthImage.resize(48,48);
  }
  
}
