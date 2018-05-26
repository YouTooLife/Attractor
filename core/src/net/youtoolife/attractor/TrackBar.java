package net.youtoolife.attractor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TrackBar {
	
	
	Rectangle barRect = new Rectangle(0, 0, 175, 15);
	Rectangle pick = new Rectangle(0, 0, 15, 25);
	
	public int pos = 50;
	public int max = 0;
	
	public Vector2 position;
	
	private BitmapFont font;// = new BitmapFont(Gdx.files.internal("assets/"+fontName));
	
	public TrackBar(Vector2 position) {
		this.position.set(position);
		init();
	}
	
	public TrackBar(Vector2 position, int max) {
		this.position = new Vector2((position));
		this.max = max;
		init();
	}
	
	private void init() {
		String fontName = "HelveticaNeue.fnt";
		FileHandle f = Gdx.files.internal("assets/"+fontName);
		System.out.println(f.exists());
		font = new BitmapFont(f);
		barRect.x = position.x;
		barRect.y = position.y;
		
		float pick = barRect.width / (float)max;
		Vector2 pickPos = new Vector2(position.x+pick*pos-this.pick.width/2.f, 
				position.y+this.barRect.height/2.f -this.pick.height/2.f);
		
		this.pick.x = pickPos.x;
		this.pick.y = pickPos.y;
		
	}
	
	private Vector2 pointXY = null;
	private boolean pickTouched = false;
	
	public void update() {
		if (Gdx.input.isTouched())
		{
			float x = -Gdx.graphics.getWidth()/2.f + Gdx.input.getX();
			float y = -Gdx.graphics.getHeight()/2.f +Gdx.graphics.getHeight() - Gdx.input.getY();
			
			//System.out.println(x+":"+y);
			//System.out.println(this.pick.x+" : "+this.pick.y);
			if (pickTouched) {
				float pick0 = barRect.width / (float)max;
				float px = x;
				if (px < barRect.x)
					px = barRect.x;
				if (px > (barRect.x+barRect.width))
					px = barRect.x+barRect.width;
				float ppx = px - barRect.x;
				pos = (int) (ppx/pick0); 
			}
			
			Vector2 point = new Vector2(x, y);
			if (this.pick.contains(point)) {
				pointXY = point; 
				//System.out.println(x+":"+y);
				pickTouched = true;
			}
			else {
				if (pickTouched && !this.barRect.contains(point))
					pickTouched = false;
			}
		}
		else {
			pickTouched = false;
		}
	}
	
	public void render(ShapeRenderer render, SpriteBatch batch) {
		
		float pick = barRect.width / (float)max;
		Vector2 pickPos = new Vector2(position.x+pick*pos-this.pick.width/2.f, 
				position.y+this.barRect.height/2.f -this.pick.height/2.f);
		
		this.pick.x = pickPos.x;
		this.pick.y = pickPos.y;
		
		update();
		
		render.setColor(Color.NAVY);
		render.begin(ShapeType.Filled);
		render.rect(position.x, position.y, barRect.width, barRect.height);
		//render.ellipse(position.x, position.y, barRect.width, barRect.height);
		render.end();
		//
		render.setColor(Color.WHITE);
		render.begin(ShapeType.Line);
		render.rect(position.x, position.y, barRect.width, barRect.height);
		//render.ellipse(position.x, position.y, barRect.width, barRect.height);
		render.end();
		
		render.setColor(Color.ORANGE);
		render.begin(ShapeType.Filled);
		render.rect(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		//render.ellipse(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		render.end();
		//
		render.setColor(Color.WHITE);
		render.begin(ShapeType.Line);
		render.rect(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		//render.ellipse(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		render.end();
		
		batch.begin();
		font.draw(batch, String.valueOf(pos), barRect.x+barRect.width+10, barRect.y+font.getLineHeight()/2.f);
		batch.end();
	}
	

}
