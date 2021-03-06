package game;

public class WanderingEnemy extends GameObject {
	

	static float maxSpeed = 2.8f;
	static float minSpeed = 0.6f;
	public static float predateRadius = 150f;
	boolean isAttacking = false;
	int attackCooldownCount = 200;	
	int attackCooldown = (int)Math.random()*attackCooldownCount;
	int attackPeriodCount = 160;
	int attackPeriod = attackPeriodCount;
	int alpha = 100;
	int predateeThreshold = 4;
	
	float averageSwarmlingsX =0;
	float averageSwarmlingsY =0;
	
	boolean isOrbiting = false;
	//GameObject center = null;
	float centerX,centerY;
	float centerR;
	float distP = 5f; //the wandering enemy would orbit at distP * center.radius //May change depending on difficulty

	float angle = 0;
	float wSpeed = 0.01f;
	
	static final int puffPeriod = 1;
	int puffPhase;
	
	WanderingEnemy(Sketch s){
		sketch = s;
		color=sketch.color(0,99,99);
		avoidRadius = predateRadius;
	}
	
	
	WanderingEnemy(Sketch s, GameObject c){
		sketch = s;
		centerX = c.x;
		centerY = c.y;
		centerR = c.radius + this.radius;
		color=sketch.color(0,99,99);
		avoidRadius = predateRadius;
		isOrbiting = true;
//		center = c;
	}

	
	public void initInWorld(World world){

		radius = 30f;
		puffPhase = (int) sketch.random(puffPeriod);


		float speed = sketch.montecarlo((maxSpeed - minSpeed)/2, (maxSpeed + minSpeed)/2);
		float radians = sketch.random(2) * Sketch.PI;
		x = Sketch.sin(radians) * (radius + world.radius);		
		y = Sketch.cos(radians) * (radius + world.radius);
		
		if(isOrbiting){
			x = centerX + Sketch.sin(radians) * (distP*centerR);		
			y = centerY + Sketch.cos(radians) * (distP*centerR);
			world.contents.add(this);
			return;
		}
		
		

		int count = 0;
		boolean hitNest = true;
		while(hitNest && world.nest !=null && count<50000){
			float randomRadians = radians - Sketch.PI/4 + sketch.random(1) * Sketch.PI/2;
			dx = Sketch.sin(randomRadians) * speed * -1;
			dy = Sketch.cos(randomRadians) * speed * -1;

			float k = dy/dx;
			float distance = Sketch.abs(k*world.nest.x-world.nest.y-k*x+y)/Sketch.sqrt(k*k+1);
			if(distance >= (world.nest.radius + predateRadius)) hitNest = false;
			count++;
		}
		if(count<50000) 
			world.contents.add(this);
		else Sketch.println("a warndering enemy doesn't init");


	}
	
	public boolean update(){
		
		if(Sketch.dist(0, 0, x, y) > sketch.world.radius + radius * 5){
			sketch.world.wanderingEnemyNumber-=1;
			return false;
		}

//		hit nest test not use anymore
//		if(Sketch.dist(0, 0, x, y) > sketch.world.radius + radius){
//			float radians = sketch.random(2) * Sketch.PI;
//			float speed = sketch.montecarlo((maxSpeed - minSpeed)/2, (maxSpeed + minSpeed)/2);
//			int count = 0;
//			boolean hitNest = true;
//			while(hitNest && sketch.world.nest !=null && count<500){
//				dx = Sketch.sin(radians) * speed * -1;
//				dy = Sketch.cos(radians) * speed * -1;
//
//				float k = dy/dx;
//				float distance = Sketch.abs(k*sketch.world.nest.x-sketch.world.nest.y-k*x+y)/Sketch.sqrt(k*k+1);
//				if(distance >= (sketch.world.nest.radius+radius))hitNest = false;
//				count++;
//			}
//			if(count > 500) {
//				Sketch.println("a warndering enemy doesn't go back");
//				sketch.world.wanderingEnemyNumber-=1;
//				return false;
//			}
//		}
		
		int predateeCount = 0;
//		float sumX = 0;
//		float sumY = 0;
//		int scount = 1;
		for (int i = 0; i < sketch.world.contents.size(); ++i) {
			GameObject other = sketch.world.contents.get(i);
			//float centerDist = Sketch.dist(other.x, other.y, sketch.world.nest.x, sketch.world.nest.y);
			if (other instanceof Swarmling) {
				if(distTo(other)<predateRadius){
					predateeCount++;
				}
			}
		}
//		
//		averageSwarmlingsX = sumX / scount;
//		averageSwarmlingsY = sumY / scount;
		
		//update isAttacking
		attackCooldown = Sketch.max(0, attackCooldown-1);
		if(attackCooldown <= 0 && attackPeriod >0 && predateeCount>predateeThreshold){
			isAttacking = true;
			attackPeriod--;
		}
		else isAttacking = false;
		
		
		//update the direction
//		if(sketch.world.count % 300 ==0){
//			
//			dx =  (averageSwarmlingsX - x) / 20;
//			dy =  (averageSwarmlingsY - y) / 20;			
//
//		}
		//reset attackCooldown, attackPeriod
		if(attackCooldown <= 0 && attackPeriod <=0){
			attackCooldown = attackCooldownCount;
			attackPeriod = attackPeriodCount;
		}
		
		
		

		 
		//check the place and change to the behavior of obiting in the world
		
		//set movement
//		
//		float centerDist = Sketch.dist(x, y, sketch.world.nest.x, sketch.world.nest.y);
//		dx +=  (-(sketch.world.nest.x - x) / centerDist) * (1 - (centerDist / 400));
//		dy +=  (-(sketch.world.nest.y - y) / centerDist) * (1 - (centerDist / 400));
		

		//Sketch.println("towards: " + dx + " " + dy);
		x += dx;
		y += dy;
		
		if(isOrbiting)
		{
			orbit();
			isAttacking = true;

		}
		
		//set Alpha
		if (isAttacking == false)
			alpha = 60 - (int)(40 * attackCooldown/attackCooldownCount);
		else alpha = 100;
		color=sketch.color(0,99,99,alpha);

		if(isAttacking){		
			
			float radians = sketch.random(2) * Sketch.PI;
			float x0 = Sketch.sin(radians) * (predateRadius);		
			float y0 = Sketch.cos(radians) * (predateRadius);
			if ((puffPhase + sketch.frameCount) % puffPeriod == 0) {
			sketch.world.contents.add(new Puff(sketch, x+x0, y+y0, color, radius/5f, 10f, 20,this));
			}
		}
		return true;
	}


	public void orbit(){
		
		float dist = Sketch.dist(x, y, centerX, centerY);		
		x = centerX + Sketch.sin(angle) * dist;
		y = centerY + Sketch.cos(angle) * dist;
		angle = angle + wSpeed;

	}

	public void draw(WorldView view){
		super.draw(view);  
		if(isAttacking){
			sketch.noFill();
			sketch.stroke(0,99,99,alpha);
			sketch.strokeWeight(1);

			sketch.ellipse(sketch.camera.screenX(this.x), sketch.camera.screenY(this.y), view.scale*predateRadius*2, view.scale*predateRadius*2);
		}
	}
}
