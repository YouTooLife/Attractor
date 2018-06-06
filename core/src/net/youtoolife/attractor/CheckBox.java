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

public class CheckBox {
	
	Rectangle barRect = new Rectangle(0, 0, 25, 25);
	//Rectangle pick = new Rectangle(0, 0, 15, 25);
	
	public String caption = "";
	public boolean checked = false;
	
	public Vector2 position;
	
	private BitmapFont font;// = new BitmapFont(Gdx.files.internal("assets/"+fontName));
	
	public CheckBox(Vector2 position, String caption) {
		this.position = new Vector2(position);
		this.caption = caption;
		init();
	}
	
	
	private void init() {
		String fontName = "HelveticaNeue.fnt";
		FileHandle f = Gdx.files.internal("assets/"+fontName);
		font = new BitmapFont(f);
		barRect.x = position.x;
		barRect.y = position.y;
	}
	
	public void update() {
			if (Gdx.input.justTouched()) {
				float x = -Gdx.graphics.getWidth()/2.f + Gdx.input.getX();
				float y = -Gdx.graphics.getHeight()/2.f +Gdx.graphics.getHeight() - Gdx.input.getY();
				
				Vector2 point = new Vector2(x, y);
				if (this.barRect.contains(point))
					checked = !checked;
			}
	}
	
	public void render(ShapeRenderer render, SpriteBatch batch) {
		
		update();
		
		render.setColor(Color.ORANGE);
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
		
		if (checked) {
			/*
		render.setColor(Color.ORANGE);
		render.begin(ShapeType.Filled);
		render.rect(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		//render.ellipse(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		render.end();
		*/
		//
		render.setColor(Color.NAVY);
		render.begin(ShapeType.Line);
		//render.rect(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		render.line(position.x+2.f, position.y+2.f, position.x+barRect.width-2.f, position.y+barRect.height-2.f);
		render.line(position.x+2.f, position.y+barRect.height-2.f, position.x+barRect.width-2.f, position.y+2.f);
		//render.ellipse(pickPos.x, pickPos.y, this.pick.width, this.pick.height);
		render.end();
		}
		
		batch.begin();
		font.draw(batch, caption, barRect.x+barRect.width+10, barRect.y+font.getLineHeight()-barRect.height/4.f);
		batch.end();
	}
	

}
