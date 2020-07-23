void mousePressed(){
   if(mousePressed){
     if(scn==0 && mouseX>(width/2)-40 && mouseX<(width/2)+40 && mouseY> 390 && mouseY<430)
     scn = 1;
     //if( scn==1 && mouseX>460 && mouseX<440 && mouseY>230 && mouseY<270)
     //scn = 2;
     if( scn==9 && mouseX>360 && mouseX<440 && mouseY>230 && mouseY<270)
     scn = 0;
   }
  
  
}
