/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> set;
    public PointSET()// construct an empty set of points
    {
        set = new SET<Point2D>();
    }
    public boolean isEmpty() // is the set empty?
    {
        return set.size() == 0;
    }
    public int size()                         // number of points in the set
    {
        return set.size();
    }
    public void insert(Point2D p)             // add the point to the set (if it is not already in the set)
    {
        if(p == null)
        {
            throw new IllegalArgumentException();
        }
        if(!contains(p)) {
            set.add(p);
        }
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if(p == null)
        {
            throw new IllegalArgumentException();
        }
        return set.contains(p);
    }
    public void draw()                         // draw all points to standard draw
    {
        for (Point2D p : set) {
            StdDraw.point(p.x(),p.y());
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if(rect == null)
        {
            throw new IllegalArgumentException();
        }
        ArrayList<Point2D> listOfPoint = new ArrayList<Point2D>();
        for (Point2D p : set) {

            if (rect.contains(p))
            {
                listOfPoint.add(p);
            }
        }
        return listOfPoint;

    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if(p == null)
        {
            throw new IllegalArgumentException();
        }
        if (set.size() == 0)
        {
            return null;
        }
        double dist = set.iterator().next().distanceTo(p);
        Point2D res = set.iterator().next();
        for (Point2D point : set)
        {
            if (point.distanceTo(p) < dist)
            {
               dist = point.distanceTo(p);
               res = point;
            }
        }
        return res;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {

        Point2D p1 = new Point2D(0.372,0.497);
        PointSET ps = new PointSET();
        ps.insert(p1);
        ps.draw();
        System.out.println(ps.size());

    }
}