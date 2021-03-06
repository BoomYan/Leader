package game;

enum StationaryPattern{
	circle,
	square,
	hexagon,
	spiral,
	random
}

public class StationaryObstacle extends Obstacle {
	//indicates if this obstacle contains the entrance to next world
	Key key = null;
	
	//the world that this obstacle is in
	//World world = null;
	
	//integer indicates the stationary obstacle around the entrance
	
	static float stationaryObstacleMaxRadius = 60;
	static float stationaryObstacleMinRadius = 50;
	//static float stationaryObstaclePatternRadius = 30;
	

	//float obstacleLife = 1f;
	
	StationaryObstacle(Sketch s){
		sketch = s;
		color = sketch.color(0,0,50);

		radius = sketch.montecarlo((StationaryObstacle.stationaryObstacleMaxRadius - StationaryObstacle.stationaryObstacleMinRadius) / 2, 
				(StationaryObstacle.stationaryObstacleMaxRadius + StationaryObstacle.stationaryObstacleMinRadius) / 2);

		obstacleLife = radius * radius;

		avoidRadius = Sketch.min(radius*3f/4f,Swarmling.attackRadius-Swarmling.swarmlingRadius);
	}
	
	StationaryObstacle(Sketch s, float r){
		sketch = s;
		color = sketch.color(0,0,50);

		radius = r;
		obstacleLife = radius * radius;

		avoidRadius = Sketch.min(radius*3f/4f,Swarmling.attackRadius-Swarmling.swarmlingRadius);
	}
	
	public boolean update(){
		radius = Sketch.max(Sketch.sqrt(obstacleLife),0);
		
		if(obstacleLife <= 1f) {
			if(key!=null){
				key.obstaclesRemaining-=1;
			}
			Burst ob = new Burst(sketch, x, y, color);
			sketch.world.contents.add(ob);
			return false;
		}

		return true;
	}

//	public void draw(WorldView view){
//		super.draw(view);
//		if(key!=null){
//		Sketch.println(sketch.frameCount);
//		}
//	}
}
