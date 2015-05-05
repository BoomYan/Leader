package game;

import processing.core.PApplet;
import supercollider.*;
import oscP5.*;

public class Music extends PApplet {
	
	Sketch sketch;
	
	int[] section = {0, 0, 1, 1, 0, 0, 1, 1, 0, 2, 3, 3, 4, 4}; 
	
	float[][][][] score =
		//treble
	   {{{{64, 62, 60, 64, 62, 60, 64, 62, 60, 64, 62, 60,
		   64, 63, 61, 64, 63, 61, 64, 63, 61, 64, 63, 61},
		  {1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8,
		   1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8, 1/8}},
		   
		 {{69, 62, 60, 57, 69, 62, 60, 57, 64, 60, 57, 64, 62, 60, 57,
		   67, 62, 61, 59, 67, 62, 61, 59, 64, 61, 59, 64, 62, 61, 59},
		  {3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16,
		   3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16}},
		   
		 {{64, 62, 60, 59, 64, 62, 60, 59, 64, 62, 60, 59, 64, 62, 60, 59,
		   64, 63, 61, 56, 68, 64, 63, 61, 56, 57, 64, 63, 61, 56, 68, 64, 63, 61, 56, 57},
		  {3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16,
		   3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20, 3/20}},
		   
		 {{68, 63, 61, 59, 68, 63, 61, 59, 66, 61, 59, 66, 63, 61, 59,
		   71, 63, 61, 59, 71, 63, 61, 59, 70, 61, 59, 70, 66, 61, 59},
	      {3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16,
		   3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16}},
		   
		 {{70, 66, 65, 63, 70, 66, 65, 63, 68, 66, 65, 63, 68, 66, 65, 63,
		   68, 65, 63, 59, 68, 65, 63, 59, 68, 65, 63, 59, 68, 65, 63, 59},
		  {3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16,
		   3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 3/16, 1/8, 1/8, 1/8, 3/16, 3/16, 3/16, 3/16}}},
		   
		//tenor
		{{{53, 60, 53, 60, 53, 60, 65, 53, 60, 52, 60, 52, 60, 64, 52, 57, 52,
		   56, 52, 56, 49, 51, 56, 51, 56, 52, 56, 49, 52, 56, 52, 57, 52, 59},
		  {1/16, 1/16, 1/16, 1/16, 1/16, 1/16, 1/8, 1/12, 1/12, 1/12, 1/12, 1/12, 1/12, 1/8, 1/8, 1/8, 1/8,
		   1/8, 1/8, 1/8, 1/8, 1/12, 1/12, 1/12, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/16, 1/16, 1/16, 1/16}},
		   
		 {{48, 53, 48, 53, 57, 60, 48, 53, 48, 53, 57, 62, 48, 53, 48, 53, 57, 64,
		   47, 47, 47, 50, 57, 62, 0},
		  {1/16, 1/16, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/12, 1/12, 1/12, 1/8,
		   1/8, 1/12, 1/12, 1/12, 1/16, 7/16, 3/4}},
		   
		 {{53, 60, 53, 60, 53, 60, 65, 53, 60, 52, 60, 52, 60, 64, 52, 57, 52,
		   56, 52, 56, 49, 51, 56, 51, 56, 52, 56, 49, 52, 56, 52, 57, 52, 59},
		  {1/16, 1/16, 1/16, 1/16, 1/16, 1/16, 1/8, 1/12, 1/12, 1/12, 1/12, 1/12, 1/12, 1/8, 1/8, 1/8, 1/8,
		   1/8, 1/8, 1/8, 1/8, 1/12, 1/12, 1/12, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/16, 1/16, 1/16, 1/16}},
		   
		 {{47, 51, 47, 51, 56, 59, 47, 51, 47, 51, 56, 61, 47, 51, 47, 51, 56, 61,
		   47, 47, 47, 51, 59, 59, 59, 59, 54, 0},
		  {1/16, 1/16, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/12, 1/12, 1/12, 1/8, 1/16, 1/16, 1/12, 1/12, 1/12, 1/8,
		   1/8, 1/12, 1/12, 1/12, 1/16, 7/16, 3/4}},
		   
		 {{51, 58, 56, 58, 51, 58, 61, 51, 58, 56, 54, 56, 58, 61, 51, 54, 58,
		   56, 51, 54, 58, 56, 51, 54, 51, 54, 56, 58, 51, 54, 56, 58},
		  {1/16, 1/16, 1/16, 1/16, 1/16, 1/16, 1/8, 5/32, 5/32, 5/32, 5/32, 5/32, 5/32, 5/32, 5/32, 5/32, 5/32,
		   1/8, 1/8, 1/8, 1/8, 1/12, 1/12, 1/12, 1/12, 1/12, 1/12, 1/8, 1/12, 1/12, 1/12, 1/8}}},
		   
		//bass
	    {{{29, 31, 33, 32, 33, 35, 37},
		  {1/2, 1/4, 3/4, 1/2, 1/4, 1/2, 1/4}},
		  
		 {{41, 40, 38, 36, 35, 33, 41, 42, 44, 45},
		  {1/2, 1/4, 5/12, 1/6, 1/6, 1/2, 1/4, 5/12, 1/6, 1/6}},
		  
		 {{29, 31, 33, 32, 33, 35, 37},
		  {1/2, 1/4, 3/4, 1/2, 1/4, 1/2, 1/4}},
		  
		 {{44, 42, 40, 39, 37, 35, 30, 32, 32, 32, 35, 37},
		  {1/2, 1/4, 5/12, 1/6, 1/6, 1/2, 1/4, 1/8, 1/8, 1/4, 1/6, 1/6}},
		  
		 {{39, 41, 42, 30, 35, 34, 32},
		  {1/2, 1/4, 3/8, 3/8, 1/2, 1/4, 3/4}}}};
	
	
	Music(){

		Treble treble = new Treble();
		Tenor tenor = new Tenor();
		Bass bass = new Bass();
			
		new Thread(treble).start();
		new Thread(tenor).start();
		new Thread(bass).start();
		
	}
	
	float cpsmidi (float freq){
		
	  return (log(freq / 440f) / log(2f)) * 12 + 69;
	  
	}
	
	public class Bass implements Runnable {
		
		public void run(){
			
			Synth synth = new Synth("Bass");
			synth.create();
			
			try{
				
				while(true){
					
					for(int i = 0; i < 12; i++){
						
						for(int s = 0; s < section.length; s++){

							for(int n = 0; n < score[2][section[s]][0].length; n++){
								
								if(score[2][section[s]][0][n] != 0){
									
									synth.set("frequency", cpsmidi(score[2][section[s]][0][n] - i));
									synth.set("duration", score[2][section[s]][1][n] * 60/23);
									synth.set("iteration", i);
									
									
								}
								
								Thread.sleep((long)(score[2][section[s]][1][n] * 60000/23));
								
							}
							
						}
						
					}	
					
				}
				
			} catch (InterruptedException e){
				
				System.err.println(e);
				
			}	
			
		}
		
	}
	
	public class Tenor implements Runnable {
		
		public void run(){
			Synth synth = new Synth("Tenor");
			synth.create();
			try{
				
				while(true){
					
					for(int i = 0; i < 12; i++){
						
						for(int s = 0; s < section.length; s++){
							
							for(int n = 0; n < score[2][section[s]][0].length; n++){
								
								if(score[1][section[s]][0][n] != 0){
									
									
									synth.set("frequency", cpsmidi(score[2][section[s]][0][n] - i));
									synth.set("duration", score[2][section[s]][1][n] * 60/23);
									synth.set("iteration", i);
									
									
								}
								
								Thread.sleep((long)(score[1][section[s]][1][n] * 60000/23));
								
							}
							
						}
						
					}	
					
				}
				
			} catch (InterruptedException e){
				
				System.err.println(e);
				
			}	
			
		}
		
	}
	
	public class Treble implements Runnable {
		
		public void run(){
			Synth synth = new Synth("Treble");
			synth.create();
			try{
				
				while(true){
					
					for(int i = 0; i < 12; i++){
						
						for(int s = 0; s < section.length; s++){
							
							for(int n = 0; n < score[2][section[s]][0].length; n++){
								
								if(score[0][section[s]][0][n] != 0){
									
									
									synth.set("frequency", cpsmidi(score[2][section[s]][0][n] - i));
									synth.set("duration", score[2][section[s]][1][n] * 60/23);
									synth.set("iteration", i);
									
									
								}
								
								Thread.sleep((long)(score[0][section[s]][1][n] * 60000/23));
								
							}
							
						}
						
					}	
					
				}
				
			} catch (InterruptedException e){
				
				System.err.println(e);
				
			}	
			
		}
		
	}
	
}
