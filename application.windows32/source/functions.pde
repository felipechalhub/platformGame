void drawButton(float x, float y, float w, float h, color filling, String caption){
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

void drawButton2(float x, float y, float w, float h, color filling, String caption){
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
