import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

class Map extends JPanel{
	private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        test t = new test();
        ArrayList<Shape> region = null;
        ArrayList<Shape> lion = null;
        ArrayList<Shape> pond = null;
		try {
			region = t.getregion();
			lion = t.getlion();
			pond = t.getpond();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		g2d.setPaint(Color.WHITE);
		g2d.fillRect(0, 0, 500, 500);
        for(Shape s:region){
        	g2d.setPaint(Color.BLACK);
        	int[] x = new int[4];
        	int[] y = new int[4];
        	for(int j=0;j<4;j++){
        		x[j] = s.points[j][0];
        		y[j] = s.points[j][1];
        	}
        	g2d.drawPolygon(x, y, 4);
        	g2d.setPaint(Color.WHITE);
        	//g2d.fillPolygon(x, y, 4);
        }
        for(Shape s:pond){
        	g2d.setPaint(Color.BLACK);
        	int x = s.x;
        	int y = s.y;
        	int d = 2*s.radius;
        	g2d.drawOval(x, y, d, d);
        	g2d.setPaint(Color.BLUE);
        	g2d.fillOval(x, y, d, d);
        }
        g2d.setPaint(Color.GREEN);
        for(Shape s:lion){
        	g2d.fillOval(s.x, s.y, 5,5);
        }
    }
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }
}
public class UI extends JFrame implements MouseListener{
	Map panel;
	JCheckBox box;
	boolean canClick;
	public UI(){
		super("Assignment 5 Bonus");
		canClick = false;
		this.setLayout(new BorderLayout());
		this.panel = new Map();
		this.box = new JCheckBox("show lions and ponds in the selected region");
		box.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JCheckBox cb = (JCheckBox)e.getSource();
				if(cb.isSelected()){
					canClick = true;
				}else{
					if(canClick){
						Graphics g = panel.getGraphics();
						panel.paintComponent(g);
					}
					canClick = false;
				}
			}
			
		});
		panel.addMouseListener(this);
		this.setSize(600, 800);
		this.add(panel,BorderLayout.CENTER);
		this.add(box,BorderLayout.SOUTH);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(!canClick){
			return;
		}
		int x = e.getX();
		int y = e.getY();
		//System.out.println(x+" "+y);
		String region_id;
		ArrayList<Shape> lion = null;
		ArrayList<Shape> pond = null;
		try {
			region_id = test.getRegionID(x,y);
			//System.out.println(region_id);
			lion = test.getInsideLion(region_id);
			pond = test.getInsidePond(region_id);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Graphics g = panel.getGraphics();
		panel.paintComponent(g);
		revisePaint(g,lion,pond);
	}
	public void mousePressed(MouseEvent e){
	}
	public void mouseReleased(MouseEvent e){
	}
	public void mouseEntered(MouseEvent e){
	}
	public void mouseExited(MouseEvent e){
	}
	public void revisePaint(Graphics g,ArrayList<Shape> lion,ArrayList<Shape> pond){
		Graphics2D g2d = (Graphics2D) g;
		for(Shape s:pond){
        	g2d.setPaint(Color.BLACK);
        	int x = s.x;
        	int y = s.y;
        	int d = 2*s.radius;
        	g2d.drawOval(x, y, d, d);
        	g2d.setPaint(Color.RED);
        	g2d.fillOval(x, y, d, d);
        }
        g2d.setPaint(Color.RED);
        for(Shape s:lion){
        	g2d.fillOval(s.x, s.y, 5,5);
        }
	}
	public static void main(String[] args){
		UI a = new UI();
		a.setDefaultCloseOperation(EXIT_ON_CLOSE);
		a.setVisible(true);
	}
}
