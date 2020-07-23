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

  void display(){

    
    
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
    void display2(){

    
    
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
  void display3(){
    
    
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
