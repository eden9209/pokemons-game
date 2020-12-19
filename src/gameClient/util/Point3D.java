/**
 * This class represents a 3D point in space.
 */
package gameClient.util;

import api.geo_location;

import java.io.Serializable;

public class Point3D implements geo_location, Serializable{
	private static final long serialVersionUID = 1L;
	/**
     * Simple set of constants - should be defined in a different class (say class Constants).*/
    public static final double EPS1 = 0.001, EPS2 = Math.pow(EPS1,2), EPS=EPS2;
    /**
     * This field represents the origin point:[0,0,0]
     */
    public static final Point3D ORIGIN = new Point3D(0,0,0);
    private double _x,_y,_z;
    public Point3D(double x, double y, double z) {
        _x=x;
        _y=y;
        _z=z;
    }

    public Point3D(geo_location p) {
       this(p.x(), p.y(), p.z());
    }
    public Point3D(double x, double y) {this(x,y,0);}
    public Point3D(String s) { try {
            String[] a = s.split(",");
            _x = Double.parseDouble(a[0]);
            _y = Double.parseDouble(a[1]);
            _z = Double.parseDouble(a[2]);
        }
        catch(IllegalArgumentException e) {
            System.err.println("ERR: got wrong format string for POint3D init, got:"+s+"  should be of format: x,y,x");
            throw(e);
        }
    }
    @Override
    public double x() {return _x;}
    @Override
    public double y() {return _y;}
    @Override
    public double z() {return _z;}


    public String toString() { return _x+","+_y+","+_z; }
    @Override
    public double distance(geo_location p2) {
        double dx = this.x() - p2.x();
        double dy = this.y() - p2.y();
        double dz = this.z() - p2.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }

    public boolean equals(Object p) {
        if(p==null || !(p instanceof geo_location)) {return false;}
        Point3D p2 = (Point3D)p;
        return ( (_x==p2._x) && (_y==p2._y) && (_z==p2._z) );
    }
    public boolean close2equals(geo_location p2) {
        return ( this.distance(p2) < EPS ); }
    public boolean equalsXY (Point3D p)
    {return p._x == _x && p._y == _y;}

     public String toString(boolean all) {
        if(all) return "[" + _x + "," +_y+","+_z+"]";
        else return "[" + (int)_x + "," + (int)_y+","+(int)_z+"]";
    }
     
     public double distance3D(Point3D p2)
     {
         double dx = this.x() - p2.x();
         double dy = this.y() - p2.y();
         double dz = this.z() - p2.z();
         double t = (dx*dx+dy*dy+dz*dz);
         return Math.sqrt(t);
     }
     public double distance3D()
     {
         return this.distance3D(ORIGIN);
     }
     public double distance2D(geo_location p2)
     {
         double dx = this.x() - p2.x();
         double dy = this.y() - p2.y();
         double t = (dx*dx+dy*dy);
         return Math.sqrt(t);
     }
     
     
     ///////////////////////////// NEW METHODS ///////////////////////////////////
     public void rescale(Point3D center, Point3D vec) {
         if(center!=null && vec != null)
             rescale(center,vec.x(),vec.y(),vec.z());
     }

     public void rescale(Point3D center, double size) {
         if(center!=null && size>0)
             rescale(center,size,size,size);
     }
     private void rescale(Point3D center, double sizeX,double sizeY, double sizeZ) {
         _x = center._x + ((_x - center._x) * sizeX);
         _y = center._y + ((_y - center._y) * sizeY);
         _z = center._z + ((_z - center._z) * sizeZ);
     }

     public void rotate2D(Point3D center, double angle) {
         // angle -1/2PI .. 1/2Piregular degrees.
         _x = _x - center.x();
         _y = _y - center.y();
         double a = Math.atan2(_y,_x);
         //	System.out.println("Angle: "+a);
         double radius = Math.sqrt((_x*_x) + (_y*_y));
         _x = (center.x() +  radius * Math.cos(a+angle));
         _y = (center.y() +  radius * Math.sin(a+angle));
     } 
     
     
     
     
     
     
     
}
