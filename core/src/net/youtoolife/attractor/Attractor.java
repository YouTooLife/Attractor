package net.youtoolife.attractor;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import static net.youtoolife.attractor.MV.*;


public class Attractor extends ApplicationAdapter implements InputProcessor {
	
	
	//--------3D---------//
    private PerspectiveCamera camera;
    private OrthographicCamera guiCam;
    private ModelBatch modelBatch;
    
    private Environment environment;
    private CameraInputController camController;

    //-----Main model-------//
    private Model model;
    private Model model2;
    private ModelInstance modelInstance;
    private ModelInstance modelInstance2;
    
    //-----Trajectory------//
    private Array<ModelInstance> tModels = new Array<ModelInstance>(); 
    private Array<ModelInstance> tModels2 = new Array<ModelInstance>(); 
        
    
    
    
    //----------GUI-------------//
    private SpriteBatch batch;
    private ShapeRenderer shape;
    
    private TrackBar trackBar1;
    private TrackBar trackBar2;
    private CheckBox checkBox;
    
    private BitmapFont font;
    
   

    
@Override
   public void create () {
	   
	
		//---------3D---------//
	
        camera = new PerspectiveCamera(67,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(5.f, 5.f, 5.f);
        camera.lookAt(0f,0f,0f);
        camera.near = 0.1f;
        camera.far = 300f;
        
        //-------GUI----------//
        guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiCam.lookAt(0.f, 0.f, 0.f);
        
        batch = new SpriteBatch();
	    trackBar1 = new TrackBar(new Vector2(-Gdx.graphics.getWidth()/2f+75.f,
	    		-Gdx.graphics.getHeight()/2f+25.f), 100);
	    trackBar2 = new TrackBar(new Vector2(-Gdx.graphics.getWidth()/2f+75.f,
	    		-Gdx.graphics.getHeight()/2f+25*2.5f+25.f), 100);
        checkBox = new CheckBox(new Vector2(-Gdx.graphics.getWidth()/2f+75.f, 
        		-Gdx.graphics.getHeight()/2f+25*2f), "Дублировать");
        
        
        ///----Debug-----//
        String fontName = "HelveticaNeue.fnt";
		FileHandle f = Gdx.files.internal("assets/"+fontName);
		font = new BitmapFont(f);
        
        shape = new ShapeRenderer();
        
        //------------------//
        
        
        camController = new CameraInputController(camera);
        camController.autoUpdate = true;
        
        Gdx.input.setInputProcessor(camController);

        
        //-------Load models--------//
        loadModelFromFile("model02.obj");

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.4f,0.9f,0.2f,1.f));
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new DirectionalLight().set(Color.GREEN, -5.f, 5.f, 0.0f));
        environment.add(new DirectionalLight().set(Color.RED, 0.5f, -5.f, 0.5f));
        //environment.set(new ColorAttribute(ColorAttribute.Fog, Color.BLACK));
        //environment.add(new PointLight().set(Color.RED, -3.0f, 0, 0, 5));
   }


	private void loadModelFromFile(String fileName) {
		 ObjLoader loader = new ObjLoader();
	        model = loader.loadModel(Gdx.files.external(fileName));
	        model2 = loader.loadModel(Gdx.files.external(fileName));
	        /*model = modelBuilder.createSphere(2f, 2f, 2f, 20, 20,
	                new Material(),
	                Usage.Position | Usage.Normal | Usage.TextureCoordinates);
	                */
	        for (Node n:model.nodes) {
	        	System.out.println(n.id);
	        	for (Node c: n.getChildren())
	        		System.out.println(c.id);
	        	System.out.println("------");
	        } 
	        
	        for (Node n:model2.nodes) {
	        	System.out.println(n.id);
	        	for (Node c: n.getChildren())
	        		System.out.println(c.id);
	        	System.out.println("------");
	        } 
	        
	        
	         
	        modelInstance = new ModelInstance(model, "model");
	        modelInstance.transform.scl(0.05f);
	        
	        modelInstance2 = new ModelInstance(model2, "model");
	        modelInstance2.transform.scl(0.05f);
	        
	        modelBatch = new ModelBatch();
	     
	        
	        Array<Vector3> attracor1 = new Array<Vector3>();
	        Array<Vector3> attracor2 = new Array<Vector3>();
	        
	        //x
	        FileHandle file = Gdx.files.external("x1.txt");
	        String[] arr = file.readString().split("\n");
	        for (String s:arr)
	        	if (!s.isEmpty())
	        		attracor1.add(new Vector3(Float.valueOf(s).floatValue(), 0.f, 0.f));
	        
	        //y
	        file = Gdx.files.external("x2.txt");
	        arr = file.readString().split("\n");
	        for (int i = 0; i < arr.length; i++) {
	        	String s  = arr[i];
	        	if (!s.isEmpty())
	        		attracor1.get(i).y = Float.valueOf(s).floatValue();
	        }
	        
	        //z
	        file = Gdx.files.external("x3.txt");
	        arr = file.readString().split("\n");
	        for (int i = 0; i < arr.length; i++) {
	        	String s  = arr[i];
	        	if (!s.isEmpty())
	        		attracor1.get(i).z = Float.valueOf(s).floatValue();
	        }
	        
	        Mesh mesh = model.getNode("normal").parts.get(0).meshPart.mesh;  
	        Array<Vector3> vect = getPrepVOrthMesh(mesh, false);
	        
	        for (int i = 0; i < attracor1.size-1; i+=2) {

	        ModelBuilder modelBuilder = new ModelBuilder();
	        modelBuilder.begin();

	        MeshPartBuilder meshBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, 
	        		Usage.Position | Usage.Normal, new Material());
	        Mesh mesh1 = MV.mergePoly(mesh, vect, attracor1.get(i), attracor1.get(i+1), i == 0? true:false);
	        meshBuilder.addMesh(mesh1);
	        
	        Model model2 = modelBuilder.end();
	        
	        Color cl = Color.GOLD;
	        if (i == 0)
	        	cl = Color.RED;
	        if (i == (attracor2.size-1))
	        	cl = Color.CYAN;
	        
	        Attribute attr = new ColorAttribute(ColorAttribute.Diffuse,  cl);
	        model2.materials.get(0).set(IntAttribute.createCullFace(0), attr);
	        //model2.materials.get(0).set(IntAttribute.createCullFace(0));
	        tModels.add(new ModelInstance(model2));
	        }
	        
	        trackBar1.max = tModels.size;
	        
	        
	        
	        
	        file = Gdx.files.external("x21.txt");
	        arr = file.readString().split("\n");
	        for (String s:arr)
	        	if (!s.isEmpty())
	        		attracor2.add(new Vector3(Float.valueOf(s).floatValue(), 0.f, 0.f));
	        
	        //y
	        file = Gdx.files.external("x22.txt");
	        arr = file.readString().split("\n");
	        for (int i = 0; i < arr.length; i++) {
	        	String s  = arr[i];
	        	if (!s.isEmpty())
	        		attracor2.get(i).y = Float.valueOf(s).floatValue();
	        }
	        
	        //z
	        file = Gdx.files.external("x23.txt");
	        arr = file.readString().split("\n");
	        for (int i = 0; i < arr.length; i++) {
	        	String s  = arr[i];
	        	if (!s.isEmpty())
	        		attracor2.get(i).z = Float.valueOf(s).floatValue();
	        }
	        
	        Mesh mesh2 = model2.getNode("normal").parts.get(0).meshPart.mesh;  
	        Array<Vector3> vect2 = getPrepVOrthMesh(mesh2, false);
	        
	        for (int i = 0; i < attracor2.size-1; i++) {

	        ModelBuilder modelBuilder2 = new ModelBuilder();
	        modelBuilder2.begin();

	        MeshPartBuilder meshBuilder = modelBuilder2.part("part2", GL20.GL_TRIANGLES, 
	        		Usage.Position | Usage.Normal, new Material());
	        Mesh mesh21 = MV.mergePoly(mesh2, vect2, attracor2.get(i), attracor2.get(i+1), i == 0? true:false);
	        meshBuilder.addMesh(mesh21);
	        
	        Model model2 = modelBuilder2.end();
	        
	        Color cl = Color.GOLDENROD;
	        if (i == 0)
	        	cl = Color.RED;
	        if (i == (attracor2.size-1))
	        	cl = Color.CYAN;
	        
	        Attribute attr = new ColorAttribute(ColorAttribute.Diffuse,  cl);
	        model2.materials.get(0).set(IntAttribute.createCullFace(0), attr);
	        tModels2.add(new ModelInstance(model2));
	        }
	        
	        trackBar2.max = tModels2.size;
	        
	        /*
	        MeshPartBuilder meshBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, 
	        		Usage.Position | Usage.Normal, new Material());
	        		*/
	        //Mesh mesh3 = mergePoly(mesh, vect, new Vector3(-1.f, 0.f, 0.f), new Vector3(0.0f, 1.f, 0.f), true);
	}
	
	
	

   @Override
   public void render () {
	   
	  
	  Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
      
      camController.update();
      guiCam.update();
      //camera.update();
      
      handlerInput(Gdx.graphics.getDeltaTime());
        
        //----Draw 3D----//
        modelBatch.begin(camera);
        modelBatch.render(modelInstance,environment);
        ///
        for (int i = 0; i < trackBar1.pos; i++) {
        	Color cl = Color.GOLDENROD;
	        if (i == 0)
	        	cl = Color.RED;
	        if (i == (trackBar1.pos-1))
	        	cl = Color.BLUE;
	        if (i == (trackBar1.max-1))
	        	cl = Color.LIME;
        	Attribute attr = new ColorAttribute(ColorAttribute.Diffuse,  cl);
        	tModels.get(i).materials.get(0).set(IntAttribute.createCullFace(0), attr);
        	modelBatch.render(tModels.get(i), environment);
        }
        
        for (int i = 0; i < trackBar2.pos; i++) {
        	Color cl = Color.GOLDENROD;
	        if (i == 0)
	        	cl = Color.RED;
	        if (i == (trackBar2.pos-1))
	        	cl = Color.BLUE;
	        if (i == (trackBar2.max-1))
	        	cl = Color.LIME;
        	Attribute attr = new ColorAttribute(ColorAttribute.Diffuse,  cl);
        	tModels2.get(i).materials.get(0).set(IntAttribute.createCullFace(0), attr);
            modelBatch.render(tModels2.get(i), environment);
        }
        ////
        modelBatch.end();

        
        //------Draw GUI--------//
        shape.setProjectionMatrix(guiCam.combined);
        batch.setProjectionMatrix(guiCam.combined);

        trackBar1.render(shape, batch);
        trackBar2.render(shape, batch);
        checkBox.render(shape, batch);
        
        batch.begin();
        font.draw(batch, "FPS: "+String.valueOf(Gdx.graphics.getFramesPerSecond()), 
        		-Gdx.graphics.getWidth()/2.f, Gdx.graphics.getHeight()/2.f);
        batch.end();
   }
   
   private void handlerInput(float delta) {
	   
	   if (checkBox.checked) {
		   if (trackBar1.changed) {
			   trackBar2.pos = trackBar1.pos*2;
			   trackBar1.changed = false;
			   trackBar2.changed = false;
		   }
		   if (trackBar2.changed) {
			   trackBar1.pos = trackBar2.pos/2;
			   trackBar2.changed = false;
			   trackBar1.changed = false;
		   }
	   }
	   
	   float speed = 100.f;
	   delta *= speed;
	   if (Gdx.input.isKeyPressed(Keys.UP))
	    	  //modelInstance.transform.translate(new Vector3(0.f, Gdx.graphics.getDeltaTime(), 0.f));
	    	  //modelInstance.transform.rotate(new Vector3(0f,0f,1f), 1f);
	    	  //camera.rotateAround(Vector3.X, new Vector3(1f, 0f, 0f), 1f);
	   		//camera.rotate(Vector3.X, delta);
	   		camera.rotateAround(Vector3.Zero, Vector3.X, delta);
	      if (Gdx.input.isKeyPressed(Keys.DOWN))
	    	  //camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(1f, 0f, 0f), -1f);
	    	  //modelInstance.transform.translate(new Vector3(0.f, -Gdx.graphics.getDeltaTime(), 0.f));
	    	  //modelInstance.transform.rotate(new Vector3(0f,0f,1f), -1f);
	    	  //camera.rotate(Vector3.X, -delta);
	    	  camera.rotateAround(Vector3.Zero, Vector3.X, -delta);
	      if(Gdx.input.isKeyPressed(Keys.RIGHT))
	          //camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 1f);
	    	  //modelInstance.transform.translate(new Vector3(Gdx.graphics.getDeltaTime(), 0.f,  0.f));
	    	  //modelInstance.transform.rotate(new Vector3(0f,1f,0f), -1f);
	    	  //camera.rotate(Vector3.Y, delta);
	    	  camera.rotateAround(Vector3.Zero, Vector3.Y, delta);
	      if(Gdx.input.isKeyPressed(Keys.LEFT))
	          //camera.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), -1f);
	    	  //modelInstance.transform.translate(new Vector3(-Gdx.graphics.getDeltaTime(), 0.f, 0.f));
	      	//modelInstance.transform.rotate(new Vector3(0f,1f,0f), 1f);
	    	  //camera.rotate(Vector3.Y, -delta);
	    	  camera.rotateAround(Vector3.Zero, Vector3.Y, -delta);
   }
   

    @Override
    public boolean keyDown(int keycode) {

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
    	if (keycode == Keys.Y) {
    		modelInstance.transform.rotateRad(Vector3.Y, (float)Math.PI/9.f);
    		//modelInstance.transform.
    	}
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    } 
    
    
}
