
public class Shape {
	String id;
	int[][] points = new int[4][2];
	int radius;
	int x;
	int y;
}
class region extends Shape{	
	public region(String id){
		this.id = id;
	}
	public region(){}
}
class lion extends Shape{
	public lion(String id){
		this.id = id;
	}
	public lion(String id, int x, int y){
		this.id = id;
		this.x = x;
		this.y = y;
	}
	public lion(){}
}
class pond extends Shape{
	public pond(String id){
		this.id = id;
	}
	public pond(String id, int x, int y, int radius){
		this.id = id;
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	public pond(){}
}

