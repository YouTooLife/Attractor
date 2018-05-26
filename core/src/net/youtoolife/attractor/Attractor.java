package net.youtoolife.attractor;

import java.util.AbstractList;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.IntAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.graphics.g3d.model.Node;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.DepthShaderProvider;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.utils.Array;
import static net.youtoolife.attractor.MV.*;

public class Attractor extends ApplicationAdapter implements InputProcessor {
    private PerspectiveCamera camera;
    private OrthographicCamera guiCam;
    private ModelBatch modelBatch;
    private ModelBuilder modelBuilder;
    private Model model;
    private ModelInstance modelInstance;
    private Environment environment;
    
    private ModelBatch shadowBatch;
    
    
    public CameraInputController camController;
    
    float[] pl = null;
    int cpl;
    ShapeRenderer shape;
    
    ArrayList<Mesh> meshesToMerge = new ArrayList<Mesh>();
    ArrayList<Matrix4> transforms = new ArrayList<Matrix4>();
    
    Vector3 pos = new Vector3(0.f, 0.f, 0.f);
    
    
    Vector3 next = new Vector3(1.5f, 0.f, 0.f);
    
    DirectionalShadowLight shadowLight;
    
    SpriteBatch batch;// = new SpriteBatch();
    
    TrackBar trackBar1;
    TrackBar trackBar2;
    CheckBox checkBox;
    
   
   @SuppressWarnings("deprecation")
@Override
   public void create () {
	   
	   	
        camera = new PerspectiveCamera(110,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        camera.position.set(0f, 0f, 3f);
        camera.lookAt(0f,0f,0f);
        camera.near = 1.f;
        camera.far = 300f;
        
        guiCam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        guiCam.lookAt(0.f, 0.f, 0.f);
        
        batch = new SpriteBatch();
	    trackBar1 = new TrackBar(new Vector2(-Gdx.graphics.getWidth()/2f+75.f,
	    		-Gdx.graphics.getHeight()/2f+25.f), 100);
	    trackBar2 = new TrackBar(new Vector2(-Gdx.graphics.getWidth()/2f+75.f,
	    		-Gdx.graphics.getHeight()/2f+25*2.5f+25.f), 100);
        checkBox = new CheckBox(new Vector2(-Gdx.graphics.getWidth()/2f+75.f, 
        		-Gdx.graphics.getHeight()/2f+25*2f), "Дублировать");

	    
	    //guiCam.near = 1.f;
        //guiCam.far = 300;;
        
        shape = new ShapeRenderer();
        
        camController = new CameraInputController(camera);
        Gdx.input.setInputProcessor(camController);

        modelBatch = new ModelBatch();
        modelBuilder = new ModelBuilder();
        /*
        box = modelBuilder.createBox(2f,2f,2f,
                new Material(ColorAttribute.createDiffuse(new Color(0.7f, 0.7f, 0.7f, 1.3f))),
                VertexAttributes.Usage.Position|VertexAttributes.Usage.Normal);*/
        
        ObjLoader loader = new ObjLoader();
        model = loader.loadModel(Gdx.files.external("model02.obj"));
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
        
        
        
        Mesh mesh = model.getNode("normal") .parts.get(0).meshPart.mesh;
        
        Mesh mesh0 = model.getNode("model") .parts.get(0).meshPart.mesh;
        float[] vert = new float[mesh.getNumVertices()*6];
        mesh0.getVertices(vert);
        Array<Vector3> vvert = new Array<Vector3>();
        Vector3 buf = new Vector3();
        for (int i = 0; i < vert.length; i++) {
        	if (i % 6 == 2) {
        		if (vert[i-2] > -0.0001 &&  vert[i-2] < 0.0001)
        		{
        			vvert.add(new Vector3(vert[i-2], vert[i-2], vert[i]));
        			System.out.println("v: "+vert[i-2]+"  :  "+vert[i-1]+"  :  "+vert[i]);
        		}
        		else 
        		{
        			//if (buf.x > 0 && vert[i-2] < 0 || buf.x < 0 )
        		}
        	}
        }
        
        
        //model.getNode("traec").parts.get(0).enabled = false;
        model.getNode("normal").parts.get(0).enabled = false;
        
        Array<Vector3> vect = getPrepVOrthMesh(mesh, false);
        for (Vector3 v:vect)
        	System.out.println(v.x+" : "+v.y+" : "+v.z);
        System.out.println("|--------||");
        
        //Mesh mesh2 = mergePoly(mesh, vect, pos, new Vector3(0.5f, 0.f, 0.f));
        
        Mesh mesh3 = mergePoly(mesh, vect, new Vector3(-1.f, 0.f, 0.f), new Vector3(0.0f, 1.f, 0.f), true);
        
        //Mesh mesh5 = mergePoly(mesh, vect, new Vector3(-1f, 0.f, -2.f), new Vector3(0.f, 0.f, -1.f), false);
        //Mesh mesh6 = mergePoly(mesh, vect, new Vector3(-1f, 0.f, 0.f), new Vector3(0.f, 0.f, -1.f), false);
        //MV.rotate0 = new Vector3(0.f, (float)Math.PI/4.f, 0.f);
        Mesh mesh5 = mergePoly(mesh, vect, new Vector3(0f, 1.f, 0.f), new Vector3(1.f, 1.f, 0.f), false);
        //Mesh mesh6 = mergePoly(mesh, vect, new Vector3(1f, 1.f, 0.f), new Vector3(2.f, 1.f, 0.f), false);
        //model.nodes.add( );
        
        
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder meshBuilder;
        meshBuilder = modelBuilder.part("part1", GL20.GL_TRIANGLES, 
        		Usage.Position | Usage.Normal, new Material());
        
        
        //meshesToMerge.add(mesh2);
        meshesToMerge.add(mesh3);
        meshesToMerge.add(mesh5);
        //meshesToMerge.add(mesh6);
        transforms.add(model.getNode("normal").globalTransform);
        transforms.add(model.getNode("normal").globalTransform);
        transforms.add(model.getNode("normal").globalTransform);
        meshBuilder.addMesh(mergeMeshes(meshesToMerge, transforms));
        model = modelBuilder.end();
        //meshBuilder.cone(5, 5, 5, 10);
        
        
        
        /*VertexAttributes va = mesh.getVertexAttributes();
        int vaA[] = new int [va.size()];
        for(int i=0; i<va.size(); i++)
        {
           vaA[i] = va.get(i).usage;
        }
        Mesh mesh01 = copyMesh(mesh, true, false, vaA);
        
        meshesToMerge.add(mesh01);
        transforms.add(model.getNode("normal").localTransform);*/
        
        
        /*
        System.out.println("Normal mesh:");
        System.out.println("max vert: "+mesh.getMaxIndices());
        System.out.println("vert size: "+mesh.getVertexSize());
        System.out.println("vert num: "+mesh.getNumVertices());
        float[] verts = new float[mesh.getMaxVertices()];
        System.out.println("float size: "+mesh.getVertices(verts).length);
        for (float f:verts)
        	System.out.println(f);
        
        System.out.println("max indic: "+mesh.getMaxIndices());
        System.out.println("indic num: "+mesh.getNumIndices());
        short[] indic = new short[mesh.getMaxIndices()];
        mesh.getIndices(indic);
        System.out.println("float size: "+indic.length);
        for (float f:indic)
        	System.out.println(f);
        */
       
        /*
        meshBuilder.addMesh(mesh2);
        
        meshBuilder = modelBuilder.part("part2", GL20.GL_TRIANGLES, 
        		Usage.Position | Usage.Normal, new Material());
        */
        
        model.materials.get(0).set(IntAttribute.createCullFace(0));
        modelInstance = new ModelInstance(model,0,0,0);
        
        //modelInstance.get
        
        
        
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight,0.8f,0.8f,0.8f,0.8f));
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        //environment.add(new DirectionalLight().set(Color.GREEN, -5.f, 5.f, 0.0f));
        environment.add(new DirectionalLight().set(Color.RED, 0f, 0f, -0.0f));
        //environment.set(new ColorAttribute(ColorAttribute.Fog, Color.BLACK));
        environment.add(new PointLight().set(Color.RED, -3.0f, 0, 0, 5));
        //environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 1.0f, 1f, .6f, 1f));
        /*
        environment.add((shadowLight = new DirectionalShadowLight(1024, 1024, 60f, 60f, .1f, 50f))                  
                    .set(1f, 1f, 1f, 40.0f, -35f, -35f));   
        environment.shadowMap = shadowLight; 
        
        */

        //shadowBatch = new ModelBatch(new DepthShaderProvider());
        
        //ProgressBar
        //com.badlogic.gdx.scenes.scene2d.ui.

        Gdx.input.setInputProcessor(this);
   }

   @Override
   public void render () {
	   
	   /*
	   shadowLight.begin(Vector3.Zero, camera.direction);
       shadowBatch.begin(shadowLight.getCamera());

       shadowBatch.render(modelInstance);

       shadowBatch.end();
       shadowLight.end();
       */
	  
	  Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      Gdx.gl.glClearColor(0, 0, 0, 1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
      
      camController.update();
      
      
      guiCam.update();

      
      if (Gdx.input.isKeyPressed(Keys.UP))
    	  //modelInstance.transform.translate(new Vector3(0.f, Gdx.graphics.getDeltaTime(), 0.f));
    	  modelInstance.transform.rotate(new Vector3(0f,0f,1f), 1f);
    	  //camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(1f, 0f, 0f), 1f);
      if (Gdx.input.isKeyPressed(Keys.DOWN))
    	  //camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(1f, 0f, 0f), -1f);
    	  //modelInstance.transform.translate(new Vector3(0.f, -Gdx.graphics.getDeltaTime(), 0.f));
    	  modelInstance.transform.rotate(new Vector3(0f,0f,1f), -1f);
      
      if(Gdx.input.isKeyPressed(Keys.RIGHT))
          //camera.rotateAround(new Vector3(0f, 0f, 0f), new Vector3(0f, 1f, 0f), 1f);
    	  //modelInstance.transform.translate(new Vector3(Gdx.graphics.getDeltaTime(), 0.f,  0.f));
    	  modelInstance.transform.rotate(new Vector3(0f,1f,0f), -1f);
      if(Gdx.input.isKeyPressed(Keys.LEFT))
          //camera.rotateAround(new Vector3(0f,0f,0f),new Vector3(0f,1f,0f), -1f);
    	  //modelInstance.transform.translate(new Vector3(-Gdx.graphics.getDeltaTime(), 0.f, 0.f));
      		modelInstance.transform.rotate(new Vector3(0f,1f,0f), 1f);
        camera.update();
        modelBatch.begin(camera);
        modelBatch.render(modelInstance,environment);
        modelBatch.end();
        
       
        
        
        
        shape.setProjectionMatrix(guiCam.combined);
        batch.setProjectionMatrix(guiCam.combined);
        float x = 0, y = 0;
        boolean flag = false;
        if (pl != null)
        {
        	for (int i = 0; i < cpl; i++)
        	if (i % 2 == 1) {
        		if (flag) {
        	shape.setColor(x,y,1.f,1.f);
        	//shape.begin(ShapeType.Point);
        	shape.begin(ShapeType.Point);
        	//shape.polyline(pl);
        	//shape.line(x, y, pl[i-1], pl[i]);
        	shape.point(pl[i-1], pl[i], 0.f);
        	shape.end();
        		}
        	x = pl[i-1];
        	y = pl[i];
        	flag = true;
        	}
        }
        trackBar1.render(shape, batch);
        trackBar2.render(shape, batch);
        checkBox.render(shape, batch);
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
