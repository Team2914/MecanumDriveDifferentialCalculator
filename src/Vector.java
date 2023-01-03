import java.awt.*;

public class Vector {
    public double i;
    public double j;
    public double x;
    public double y;

    public Vector(double x, double y, double i, double j) {
        this.i = i;
        this.j = j;
        this.x = x;
        this.y = y;
    }

    public Vector(Vector v) {
        this.i = v.i;
        this.j = v.j;
        this.x = v.x;
        this.y = v.y;
    }

    /*public void draw(Draw canvas, Color color, double radius) {
        canvas.setPenRadius(radius);
        canvas.setPenColor(color);
        canvas.line(x, y, x+i, y+j);
    }*/

    public double dot(Vector v2) {
        return this.i*v2.i + this.j*v2.j;
    }

    public Vector parallelProjection(Vector v, double x, double y){
        double uDotv = this.dot(v);
        double uDotu = this.dot(this);
        double scale = uDotv/uDotu;
        Vector result = new Vector(x, y, this.i*scale, this.j*scale);
        return result;
    }

    public Vector perpendicularProjection(Vector v2, double x, double y) {
        Vector parallel = this.parallelProjection(v2, x, y);
        Vector result = new Vector(x, y, v2.i-parallel.i, v2.j-parallel.j);
        return result;
    }

    public double length() {
        return Math.sqrt(Math.pow(i,2)+Math.pow(j,2));
    }

    public void scale(double factor){
        this.i = this.i*factor;
        this.j = this.j*factor;
    }
}
