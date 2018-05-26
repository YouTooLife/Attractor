package net.youtoolife.attractor;

import java.util.AbstractList;

import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;

public class MV {
	
	
	public static Vector3 rotate0 = new Vector3(0.f, -(float)Math.PI/4.f, 0.f); 
	//private static Array<Vector3> bbuf = new Array<Vector3>();

	

	   public static Mesh mergePoly(Mesh mesh1, Array<Vector3> verts, Vector3 position, Vector3 newPosition, boolean first)
	   {
	      int vertexArrayTotalSize = 0;
	      
	      VertexAttributes va = mesh1.getVertexAttributes();
	      int vaA[] = new int [va.size()];
	      for(int i=0; i<va.size(); i++)
	      {
	         vaA[i] = va.get(i).usage;
	      }
	      
	      //VertexAttribute posAttr = mesh1.getVertexAttribute(Usage.Position);  
	      Vector3 p = new Vector3(newPosition.x-position.x, newPosition.y-position.y, newPosition.z-position.z);
	      
	      System.out.println("==P: "+p.x + "; "+p.y+"; "+p.z);
	      
	      Vector3 rotate = new Vector3(0.f, 0.f, 0.f); 
	      
	      
	      double ay = 0;//-rotate.y;
	      if (p.x != 0) {
	    	  if (p.x > 0) {
	    		  if (p.z >= 0 )
	    			  ay -= Math.atan(p.z/p.x);
	    		  else
	    			  ay -= Math.atan(p.z/p.x);
	    	  }
	    	  else {
	    		  if (p.z >= 0 )
	    			  ay += Math.PI - Math.atan(p.z/p.x);
	    		  else
	    			  ay += Math.PI -Math.atan(p.z/p.x); 
	    	  }
	      }
	      else {
	    	  if (p.z > 0) 
	    		  ay -= Math.PI/2.f ;
	    	  else 
	    		  if (p.z != 0)
	    			  ay += Math.PI/2.f;
	    		  else
	    			  ay = 0;
	      }
	      
	      double az = 0;//-rotate.z;
	      if (p.x != 0) {
	    	  if (p.x > 0) {
	    		  if (p.y >= 0 )
	    			  az += Math.atan(p.y/p.x);
	    		  else
	    			  az += Math.atan(p.y/p.x);
	    	  }
	    	  else {
	    		  if (p.y >= 0 )
	    			  az += Math.PI + Math.atan(p.y/p.x);
	    		  else
	    			  az += Math.PI +Math.atan(p.y/p.x); 
	    	  }
	      }
	      else {
	    	  if (p.y > 0) 
	    		  az += Math.PI/2.f;
	    	  else 
	    		  if (p.y != 0)
	    			  az -= Math.PI/2.f;
	    		  else
	    			  az = 0;
	    	  
	      }
	      
	      double ax = 0;//-rotate.x;
	      if (p.z != 0) {
	    	  if (p.z > 0) {
	    		  if (p.y >= 0 )
	    			  ax += Math.atan(p.y/p.z);
	    		  else
	    			  ax += Math.atan(p.y/p.z);
	    	  }
	    	  else {
	    		  if (p.y >= 0 )
	    			  ax += Math.PI + Math.atan(p.y/p.z);
	    		  else
	    			  ax += Math.PI +Math.atan(p.y/p.z); 
	    	  }
	      }
	      else {
	    	  if (p.y > 0) 
	    		  ax += Math.PI/2.f;
	    	  else 
	    		  if (p.y != 0)
	    			  ax -= Math.PI/2.f;
	    		  else
	    			  ax = 0;
	      }
	      //double az = Math.atan(p.y/p.x);
	      //newPosition.rotateRad(Vector3.Z, az)
	      
	      //rotate.x = (float) ax;
	      rotate.y = (float) ay;
	      rotate.z = (float) az;
	      
	      System.out.println("==r: "+rotate.x + "; "+rotate.y +"; "+rotate.z);
	      
	      /*
	      p.rotateRad(Vector3.X, rotate.x);
	      p.rotateRad(Vector3.Y, rotate.y);
	      p.rotateRad(Vector3.Z, rotate.z);
	      */
	      
	      System.out.println("==P: "+p.x + "; "+p.y+"; "+p.z);
	      
	      Array<Float> vert = new Array<Float>();
	      
	      float[] overts = new float[mesh1.getNumVertices()*6];
	      mesh1.setVertices(overts);
	      
	      
	      for (float f:overts)
	    	  vert.add(f);
	      
	      for (int i = 0; i < verts.size; i++) {
	    	  
	    	//2-3
	    	  if (i > 0 && i % 2 == 1) {
	    		  
	        		  Vector3 np = new Vector3(verts.get(i).x, 
	        				  verts.get(i).y, 
	        				  verts.get(i).z);
	        		  
	        		  if (first) {
	        		  np.rotateRad(Vector3.X, rotate.x);
	        		  np.rotateRad(Vector3.Y, rotate.y);
	        		  np.rotateRad(Vector3.Z, rotate.z);
	        		  }
	        		  else {
	        			  
	        			  System.out.println("prePre rot: "+rotate0.x+"; "+ 
		    					  rotate0.y+"; "+ 
		    					  rotate0.z);
		    			  System.out.println("prePre first: "+np.x+"; "+ 
			    				  np.y+"; "+ 
			    				  np.z);
		    			  
	        			  np.rotateRad(Vector3.X, rotate0.x);
		        		  np.rotateRad(Vector3.Y, rotate0.y);
		        		  np.rotateRad(Vector3.Z, rotate0.z);
		        		  
		    			  
		    			  System.out.println("pop(0)"+np.x+" :: "+np.y+" :: "+np.z);
		    			  
		    			  
		        		  
	        		  }
	        		  
	        		  np.x += position.x;
	        		  np.y += position.y;//p.y;
	        		  np.z += position.z;//p.y;
	        		  vert.add(np.x);
	        		  vert.add(np.y);
	        		  vert.add(np.z);
	        		  
	    		  System.out.println("2-3: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  //
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  //----//
	    		  
	    		  
	    		  
	    		  
	    		  
	    		  
	    		  //3-1
	    		  Vector3 np2 = new Vector3(verts.get(i).x, 
	    				  verts.get(i).y, 
	    				  verts.get(i).z);
	    		  
	    		  System.out.println("3-1: "+np2.x
	    		  +" : "+np2.y+" : "+np2.z);
	    		  
	    		  /*
	    		  if (np.x < 0)
	    			  np.x *=-1;
	    		  if (np.y < 0)
	    			  np.y *=-1;
	    		  if (np.z < 0)
	    			  np.z *=-1;
	    			  */
	    		  np2.rotateRad(Vector3.X, rotate.x);
	    		  np2.rotateRad(Vector3.Y, rotate.y);
	    		  np2.rotateRad(Vector3.Z, rotate.z);
	    		  
	    		  System.out.println("rot: 3-1: "+np2.x
	    	    		  +" : "+np2.y+" : "+np2.z);
	    		  
	    		  
	    		  np2.x += newPosition.x;
	    		  np2.y += newPosition.y;//p.y;
	    		  np2.z += newPosition.z;//p.y;
	    		  
	    		  
	    		  
	    		  System.out.println("push(0)"+np2.x+" :: "+np2.y+" :: "+np2.z);
	    		  
	    		  vert.add(np2.x);
	    		  vert.add(np2.y);
	    		  vert.add(np2.z);
	    		  
	    		  
	    		  
	    		  
	    		  
	    		  System.out.println("3-1: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  //
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  //----------//
	    		  
	    		  
	    		  
	    		  
	    		  System.out.println("******");
	    		  //-1-2
	        		  Vector3 np3 = new Vector3(verts.get(i-1).x, 
	        				  verts.get(i-1).y, 
	        				  verts.get(i-1).z);
	        		  
	        		  if (first) {
	        		  np3.rotateRad(Vector3.X, rotate.x);
	        		  np3.rotateRad(Vector3.Y, rotate.y);
	        		  np3.rotateRad(Vector3.Z, rotate.z);
	        		  }
	        		  else {
	        			  
	        			  System.out.println("pre rot: "+rotate0.x+"; "+ 
		    					  rotate0.y+"; "+ 
		    					  rotate0.z);
		    			  System.out.println("prefirst: "+np.x+"; "+ 
			    				  np.y+"; "+ 
			    				  np.z);
		    			
	        			  np3.rotateRad(Vector3.X, rotate0.x);
		        		  np3.rotateRad(Vector3.Y, rotate0.y);
		        		  np3.rotateRad(Vector3.Z, rotate0.z); 
		        		  
	        		  }
	        		  
	        		  np3.x += position.x;
	        		  np3.y += position.y;//p.y;
	        		  np3.z += position.z;//p.y;
	        		  vert.add(np3.x);
	        		  vert.add(np3.y);
	        		  vert.add(np3.z);
	        		  
	    		  System.out.println("-1-2: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  //
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  //---------------//
	    		  
	    		  
	    		  
	    		  
	    		  //-2-3
	    		  //vert.add(verts.get(i).x+p.x);
	    		  //vert.add(verts.get(i).y+p.y);
	    		  //vert.add(verts.get(i).z+p.z);
	    		  vert.add(np2.x);
	    		  vert.add(np2.y);
	    		  vert.add(np2.z);
	    		  System.out.println("-2-3: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  //
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  //----------------//
	    		  
	    		  
	    		  //-3-1
	    		  Vector3 np4 = new Vector3(verts.get(i-1).x, 
	    				  verts.get(i-1).y, 
	    				  verts.get(i-1).z);
	    		  
	    		  
	    		  System.out.println(":-3-1: "+np4.x
	    	    		  +" : "+np4.y+" : "+np4.z);
	    		   
	    		  np4.rotateRad(Vector3.X, rotate.x);
	    		  np4.rotateRad(Vector3.Y, rotate.y);
	    		  np4.rotateRad(Vector3.Z, rotate.z);
	    		  
	    		  
	    		  
	    		  np4.x += newPosition.x;
	    		  np4.y += newPosition.y;//p.y;
	    		  np4.z += newPosition.z;//p.y;
	    		  
	    		  System.out.println("push(1)"+np4.x+" :: "+np4.y+" :: "+np4.z);
	    		  System.out.println(":-3-1: "+np4.x
	    	    		  +" : "+np4.y+" : "+np4.z);
	    		  
	    		  vert.add(np4.x);
	    		  vert.add(np4.y);
	    		  vert.add(np4.z);
	    		  
	    		  //vert.add(verts.get(i-1).x+p.x);
	    		  //vert.add(verts.get(i-1).y+p.y);
	    		  //vert.add(verts.get(i-1).z+p.z);
	    		  
	    		  System.out.println("-3-1: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  //
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  System.out.println("===========");

	    		  //--------------//
	    	  }
	    	  else {
	    		  //1-2
	    		  System.out.println("===========");
	    		  
	    			 

	    			  System.out.println("pos0: "+position.x+"; "+ 
	    					  position.y+"; "+ 
	    					  position.z);	  
	    			  
	    			  System.out.println("first: "+verts.get(i).x+"; "+ 
	    				  verts.get(i).y+"; "+ 
	    				  verts.get(i).z);
	    			  
	    		  Vector3 np = new Vector3(verts.get(i).x, 
	    				  verts.get(i).y, 
	    				  verts.get(i).z);
	    		  
	    		  System.out.println("first: "+np.x+"; "+ 
	    				  np.y+"; "+ 
	    				  np.z);
	    		  
	    		  if (first) {
	    		  np.rotateRad(Vector3.X, rotate.x);
	    		  np.rotateRad(Vector3.Y, rotate.y);
	    		  np.rotateRad(Vector3.Z, rotate.z);
	    		  }
	    		  else {
	    			  
	    			  
	    			  System.out.println("YA rot: "+rotate0.x+"; "+ 
	    					  rotate0.y+"; "+ 
	    					  rotate0.z);
	    			  System.out.println("YA first: "+np.x+"; "+ 
		    				  np.y+"; "+ 
		    				  np.z);
	    			  
        			  np.rotateRad(Vector3.X, rotate0.x);
	        		  np.rotateRad(Vector3.Y, rotate0.y);
	        		  np.rotateRad(Vector3.Z, rotate0.z);  
	        		  
	    			
	        		  
	        		  System.out.println("YA first: "+np.x+"; "+ 
		    				  np.y+"; "+ 
		    				  np.z);
	        		 
        		  }
	    		  
	    		  System.out.println("fir: "+np.x+"; "+ 
	    				  np.y+"; "+ 
	    				  np.z);
	    		  
	    		  
	    		  np.x += position.x;
	    		  np.y += position.y;//p.y;
	    		  np.z += position.z;//p.y;
	    		  
	    		  
	    		  vert.add(np.x);
	    		  vert.add(np.y);
	    		  vert.add(np.z);
	    		 
	    		  System.out.println("1-2: "+vert.get(vert.size-3)
	    		  +" : "+vert.get(vert.size-2)+" : "+vert.get(vert.size-1));
	    		  
	    		  vert.add(1.f);
	    		  vert.add(0.f);
	    		  vert.add(0.f);
	    		  
	    		  
	    	/*
	    	  vertices[i*6] = verts.get(i).x;
	    	  vertices[i*6+1] = verts.get(i).y;
	    	  vertices[i*6+2] = verts.get(i).z;
	    	  vertices[i*6+3] = 1.f;
	    	  vertices[i*6+4] = 0.f;
	    	  vertices[i*6+5] = 0.f;*/
	    	  }
	      }
	      //int numComponents = posAttr.numComponents;
	      //Mesh.transform(transform1, vertices, 6, 0, numComponents, 0, verts.size * 2);
	      vertexArrayTotalSize = vert.size;//verts.size * 6* 2;
	      final float vertices[] = new float[vertexArrayTotalSize];
	      
	      for (int i = 0; i < vertices.length; i++)
	      {
	    	  Float f = vert.get(i);
	    	  vertices[i] = f.floatValue();
	    	  //System.out.println(f.floatValue());
	    	  //if ((i % 6) == 3)
	    		  //System.out.println(vertices[i-2]+" : "+vertices[i-1]+" : "+vertices[i]);
	      }
	      
	      short[] indices = new short[vertices.length / 6];
	      
	      for (int i = 0; i < indices.length; i++)
	    	  indices[i] = (short)i;
	      
	      /*
	      for (int i = indices.length/2; i < indices.length; i++)
	    	  indices[i] = (short)i;*/
	      
	      rotate0.set(rotate);
	      
	      Mesh result = new Mesh(false,  vertices.length / 6, vertices.length / 6,  mesh1.getVertexAttributes());
	      System.out.println(vertices.length);
	      result.setVertices(vertices);
	      result.setIndices(indices);
	      return result;
	   }
	
	

	   public static Array<Vector3> getPrepVOrthMesh(Mesh meshToCopy, boolean removeDuplicates) {
		   
		      final int vertexSize = meshToCopy.getVertexSize() / 4;
		      int numVertices = meshToCopy.getNumVertices();
		      float[] vertices = new float[numVertices * vertexSize];
		      meshToCopy.getVertices(0, vertices.length, vertices);
		      short[] checks = null;
		      VertexAttribute[] attrs = null;
		      int newVertexSize = 0;
		      
		      VertexAttributes va2 = meshToCopy.getVertexAttributes();
		        int vaA2[] = new int [va2.size()];
		        for(int i=0; i<va2.size(); i++)
		        {
		           vaA2[i] = va2.get(i).usage;
		        }
		      
		      if (vaA2 != null) {
		         int size = 0;
		         int as = 0;
		         for (int i = 0; i < vaA2.length; i++)
		            if (meshToCopy.getVertexAttribute(vaA2[i]) != null) {
		               size += meshToCopy.getVertexAttribute(vaA2[i]).numComponents;
		               as++;
		            }
		         if (size > 0) {
		            attrs = new VertexAttribute[as];
		            checks = new short[size];
		            int idx = -1;
		            int ai = -1;
		            for (int i = 0; i < vaA2.length; i++) {
		               VertexAttribute a = meshToCopy.getVertexAttribute(vaA2[i]);
		               if (a == null)
		                  continue;
		               //System.out.println("i = "+i+": "+vaA2[i]);
		               for (int j = 0; j < a.numComponents; j++)
		                  checks[++idx] = (short)(a.offset/4 + j);
		               attrs[++ai] = new VertexAttribute(a.usage, a.numComponents, a.alias);
		               //System.out.println("usage: "+a.usage+"; comp: "+a.numComponents+"; alias: "+a.alias);
		               newVertexSize += a.numComponents;
		            }
		         }
		      }
		      if (checks == null) {
		         checks = new short[vertexSize];
		         for (short i = 0; i < vertexSize; i++)
		            checks[i] = i;
		         newVertexSize = vertexSize;
		      }
		      
		      int numIndices = meshToCopy.getNumIndices();
		      short[] indices = null;   
		      if (numIndices > 0) {
		         indices = new short[numIndices];
		         meshToCopy.getIndices(indices);
		         if (removeDuplicates || newVertexSize != vertexSize) {
		            float[] tmp = new float[vertices.length];
		            int size = 0;
		            for (int i = 0; i < numIndices; i++) {
		               final int idx1 = indices[i] * vertexSize;
		               short newIndex = -1;
		               if (removeDuplicates) {
		                  for (short j = 0; j < size && newIndex < 0; j++) {
		                     final int idx2 = j*newVertexSize;
		                     boolean found = true;
		                     for (int k = 0; k < checks.length && found; k++) {
		                        if (tmp[idx2+k] != vertices[idx1+checks[k]])
		                           found = false;
		                     }
		                     if (found)
		                        newIndex = j;
		                  }
		               }
		               if (newIndex > 0)
		                  indices[i] = newIndex;
		               else {
		                  final int idx = size * newVertexSize;
		                  for (int j = 0; j < checks.length; j++)
		                     tmp[idx+j] = vertices[idx1+checks[j]];
		                  indices[i] = (short)size;
		                  size++;
		               }
		            }
		            vertices = tmp;
		            numVertices = size;
		         }
		      }

		    Array<Vector3> v1 = new Array<Vector3>();
		      for (int i = 0; i < vertices.length; i++)
		      {
		    	 
		    	  if ((i % 6) == 2) {
		    		  
		    		  float f1 = vertices[i-2];
					  float f2 = vertices[i-1];
					  float f3 = vertices[i];
					  
		    			  if (i < 6 || ((i / 6) % 3 != 0))
		    				  v1.add(new Vector3(f1, f2, f3));
		    			  
		    			  if (i == 8)
		    	    		  v1.add(new Vector3(f1, f2, f3));
		    	  }
		      }
		      v1.add(v1.peek());
		      v1.add(v1.first());
		      
		      return v1;
		   }
	   
	   public static Mesh mergeMeshes(AbstractList<Mesh> meshes, AbstractList<Matrix4> transformations)
	    {
	       if(meshes.size() == 0) return null;
	          
	       int vertexArrayTotalSize = 0;
	       int indexArrayTotalSize = 0;
	       
	       VertexAttributes va = meshes.get(0).getVertexAttributes();
	       int vaA[] = new int [va.size()];
	       for(int i=0; i<va.size(); i++)
	       {
	          vaA[i] = va.get(i).usage;
	       }
	       
	       for(int i=0; i<meshes.size(); i++)
	       {
	          Mesh mesh = meshes.get(i);
	          if(mesh.getVertexAttributes().size() != va.size()) 
	          {
	             meshes.set(i, copyMesh(mesh, true, false, vaA));
	          }
	          
	          vertexArrayTotalSize += mesh.getNumVertices() * mesh.getVertexSize() / 4;
	          indexArrayTotalSize += mesh.getNumIndices();
	       }
	       
	       final float vertices[] = new float[vertexArrayTotalSize];
	       final short indices[] = new short[indexArrayTotalSize];
	       
	       int indexOffset = 0;
	       int vertexOffset = 0;
	       int vertexSizeOffset = 0;
	       int vertexSize = 0;
	       
	       for(int i=0; i<meshes.size(); i++)
	       {
	          Mesh mesh = meshes.get(i);
	          
	          int numIndices = mesh.getNumIndices();
	          int numVertices = mesh.getNumVertices();
	          vertexSize = mesh.getVertexSize() / 4;
	          int baseSize = numVertices * vertexSize;
	          VertexAttribute posAttr = mesh.getVertexAttribute(Usage.Position);
	          int offset = posAttr.offset / 4;
	          int numComponents = posAttr.numComponents;
	          
	          if(mesh.getVertexSize() == 48)
	          {
	             System.out.println("Break");
	          }
	          
	          { //uzupelnianie tablicy indeksow
	             mesh.getIndices(indices, indexOffset);
	             for(int c = indexOffset; c < (indexOffset + numIndices); c++)
	             {
	                indices[c] += vertexOffset;
	             }
	             indexOffset += numIndices;
	          }
	          
	          mesh.getVertices(0, baseSize, vertices, vertexSizeOffset);
	          Mesh.transform(transformations.get(i), vertices, vertexSize, offset, numComponents, vertexOffset, numVertices);
	          vertexOffset += numVertices;
	          vertexSizeOffset += baseSize;
	       }
	       
	       Mesh result = new Mesh(true, vertexOffset, indices.length, meshes.get(0).getVertexAttributes());
	       result.setVertices(vertices);
	       result.setIndices(indices);
	       
	       if (indices != null)
	          	for (short i:indices)
	        	  System.out.println("ind: "+i);
	       
	       return result;
	    }
	    
	   
	    public static Mesh copyMesh(Mesh meshToCopy, boolean isStatic, boolean removeDuplicates, final int[] usage) {
	       // TODO move this to a copy constructor?
	       // TODO duplicate the buffers without double copying the data if possible.
	       // TODO perhaps move this code to JNI if it turns out being too slow.
	       final int vertexSize = meshToCopy.getVertexSize() / 4;
	       int numVertices = meshToCopy.getNumVertices();
	       float[] vertices = new float[numVertices * vertexSize];
	       meshToCopy.getVertices(0, vertices.length, vertices);
	       short[] checks = null;
	       VertexAttribute[] attrs = null;
	       int newVertexSize = 0;
	       if (usage != null) {
	          int size = 0;
	          int as = 0;
	          for (int i = 0; i < usage.length; i++)
	             if (meshToCopy.getVertexAttribute(usage[i]) != null) {
	                size += meshToCopy.getVertexAttribute(usage[i]).numComponents;
	                as++;
	             }
	          if (size > 0) {
	             attrs = new VertexAttribute[as];
	             checks = new short[size];
	             int idx = -1;
	             int ai = -1;
	             for (int i = 0; i < usage.length; i++) {
	                VertexAttribute a = meshToCopy.getVertexAttribute(usage[i]);
	                if (a == null)
	                   continue;
	                System.out.println("i = "+i+": "+usage[i]);
	                for (int j = 0; j < a.numComponents; j++)
	                   checks[++idx] = (short)(a.offset/4 + j);
	                attrs[++ai] = new VertexAttribute(a.usage, a.numComponents, a.alias);
	                System.out.println("usage: "+a.usage+"; comp: "+a.numComponents+"; alias: "+a.alias);
	                newVertexSize += a.numComponents;
	             }
	          }
	       }
	       if (checks == null) {
	          checks = new short[vertexSize];
	          for (short i = 0; i < vertexSize; i++)
	             checks[i] = i;
	          newVertexSize = vertexSize;
	       }
	       
	       int numIndices = meshToCopy.getNumIndices();
	       short[] indices = null;   
	       if (numIndices > 0) {
	          indices = new short[numIndices];
	          meshToCopy.getIndices(indices);
	          if (removeDuplicates || newVertexSize != vertexSize) {
	             float[] tmp = new float[vertices.length];
	             int size = 0;
	             for (int i = 0; i < numIndices; i++) {
	                final int idx1 = indices[i] * vertexSize;
	                short newIndex = -1;
	                if (removeDuplicates) {
	                   for (short j = 0; j < size && newIndex < 0; j++) {
	                      final int idx2 = j*newVertexSize;
	                      boolean found = true;
	                      for (int k = 0; k < checks.length && found; k++) {
	                         if (tmp[idx2+k] != vertices[idx1+checks[k]])
	                            found = false;
	                      }
	                      if (found)
	                         newIndex = j;
	                   }
	                }
	                if (newIndex > 0)
	                   indices[i] = newIndex;
	                else {
	                   final int idx = size * newVertexSize;
	                   for (int j = 0; j < checks.length; j++)
	                      tmp[idx+j] = vertices[idx1+checks[j]];
	                   indices[i] = (short)size;
	                   size++;
	                }
	             }
	             vertices = tmp;
	             numVertices = size;
	          }
	       }
	       
	       System.out.println("vert size: "+newVertexSize+"; num vert: "+numVertices);
	       System.out.println("vert len: "+vertices.length);  
	      
	       /*
	        if (indices != null)
	       	for (short i:indices)
	     	  System.out.println("ind: "+i);
	       for (int i = 0; i < vertices.length; i++)
	       {
	     	  float f = vertices[i];
	     	  
	     	  //System.out.println(((i % 6 == 2)?"===":" ")+"["+i+"] vert: "+f);
	     	  if ((i % 6) == 5) {
	     		  vertices[i-2] = 1.f;
	     		  vertices[i-1] = 0.f;
	     		  vertices[i] = 0.f;
	     		  System.out.println("rgb + "+vertices[i-2]+"; "+vertices[i-1]+"; "+vertices[i]);
	     	  }
	     	  if ((i % 6) == 2) {
	     		  float r = (float) Math.sqrt( vertices[i]*vertices[i] 
	     				  +vertices[i-1]*vertices[i-1] +vertices[i-2]*vertices[i-2]);
	     		  
	     		  System.out.println(vertices[i-2]+"; "+vertices[i-1]+"; "+vertices[i]);
	     		  if (fl) {
	     			  
	     			  float f1 = vertices[i-1];
	     			  float f2 = vertices[i];
	     			  if (cpla == 0 || cpla % 3 != 0) {
	     				  pl[cpl] = f1*100.f;
	     				  pl[cpl+1] = f2*100.f;
	     				  cpl+=2;
	     			  }
	     			cpla++;
	     		  }
	     	  }
	     	  
	       }*/
	       
	       Mesh result;
	       if (attrs == null)
	          result = new Mesh(isStatic, numVertices, indices == null ? 0 : indices.length, meshToCopy.getVertexAttributes());
	       else
	          result = new Mesh(isStatic, numVertices, indices == null ? 0 : indices.length, attrs);
	       result.setVertices(vertices, 0, numVertices * newVertexSize);
	       if (indices != null)
	       result.setIndices(indices);
	       
	       return result;
	    }

}
