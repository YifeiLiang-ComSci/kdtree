/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
class Node
{
    Point2D point;
    int level;
    Node left;
    Node right;
    RectHV inRect;

    public Node(Point2D p, int l)
    {
        point = p;
        level = l;
    }
    public Node(Point2D p,int l,double xmin,double xmax, double ymin, double ymax)
    {
        point = p;
        level = l;
        left = null;
        right = null;
        inRect = new RectHV(xmin,ymin,xmax,ymax);
    }
}

public class KdTree {
    private Node root;
    private int size;
    public KdTree()// construct an empty set of points
    {
        root = null;
        size = 0;
    }
    public boolean isEmpty() // is the set empty?
    {
        return size == 0;
    }
    public int size()                         // number of points in the set
    {
        return size;
    }
    private boolean right(Node p1, Node p2)
    {
        if (p1.point.x() < p2.point.x())
        {
           return true;
        } else if (p1.point.x() > p2.point.x())
        {
            return false;
        } else if (p1.point.y() < p2.point.y())
        {
            return true;
        } else
        {
            return false;
        }
    }
    private boolean above(Node p1, Node p2)
    {
        if (p1.point.y() < p2.point.y())
        {
            return true;
        } else if (p1.point.y() > p2.point.y())
        {
            return false;
        } else if (p1.point.x() < p2.point.x())
        {
            return true;
        } else {
            return false;
        }
    }
    public void insert(Point2D p)             // add the point to the set (if it is not already in the set)
    {
        if(p == null)
        {
            throw new IllegalArgumentException();
        }
        if (contains(p))
        {
            return;
        }
        if (root == null)
        {
           root = new Node(p,0,0,1,0,1);
           size += 1;
           return;
        }
        size += 1;
        Node currentNode = root;
        double xmin = 0;
        double ymin = 0;
        double xmax = 1;
        double ymax = 1;
        Node newNode =  new Node(p,0,0,1,0,1);
        while (true)
        {
            if (currentNode.level % 2 == 0 && right(currentNode, newNode) && currentNode.right == null)
            {
                newNode.level = currentNode.level + 1;
                newNode.inRect = new RectHV(xmin, ymin, xmax, ymax);
                currentNode.right = newNode;


                break;
            } else if (currentNode.level % 2 == 0 && right(currentNode, newNode) && currentNode.right != null)
            {

                xmin = currentNode.point.x();
                currentNode = currentNode.right;

            } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode) && currentNode.left == null)
            {
                newNode.level = currentNode.level + 1;
                newNode.inRect = new RectHV(xmin, ymin, xmax, ymax);
                currentNode.left = newNode;

                break;
            } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode) && currentNode.left != null)
            {

                xmax =  currentNode.point.x();
                currentNode = currentNode.left;

            } if (currentNode.level % 2 == 1 && above(currentNode, newNode) && currentNode.right == null)
            {
                newNode.level = currentNode.level + 1;
                newNode.inRect = new RectHV(xmin, ymin, xmax, ymax);
                currentNode.right = newNode;

                break;
            } else if (currentNode.level % 2 == 1 && above(currentNode, newNode) && currentNode.right != null)
            {
                ymin = currentNode.point.y();
                currentNode = currentNode.right;

            } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode) && currentNode.left == null)
            {
                newNode.level = currentNode.level + 1;
                newNode.inRect = new RectHV(xmin, ymin, xmax, ymax);
                currentNode.left = newNode;

                break;
            } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode) && currentNode.left != null)
            {
                ymax = currentNode.point.y();
                currentNode = currentNode.left;
            }

        }
    }
    public boolean contains(Point2D p)            // does the set contain point p?
    {
        if(p == null)
        {
            throw new IllegalArgumentException();
        }
        if (root == null) return false;
        Node currentNode = root;
        Node newNode = new Node(p,0);
        while (true)
        {
            if (currentNode == null)
            {
                return false;
            }
            if (currentNode.point.equals(p))
            {
                return true;
            }
            if (currentNode.level % 2 == 0 && right(currentNode, newNode))
            {
                currentNode = currentNode.right;
            } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode))
            {
                currentNode = currentNode.left;
            } else if (currentNode.level % 2 == 1 && above(currentNode, newNode))
            {
                currentNode = currentNode.right;
            } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode))
            {
                currentNode = currentNode.left;
            }

        }
    }
    private void drawRecursive(Node node)
    {
        if (node == null)
        {
            return;
        }
        drawRecursive(node.left);
        StdDraw.point(node.point.x(), node.point.y());
        drawRecursive(node.right);
    }
    public void draw()                         // draw all points to standard draw
    {
        drawRecursive(root);
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if (rect == null)
        {
            throw new IllegalArgumentException();
        }

        Queue<Point2D> queue = new Queue<Point2D>();
        rangeAdd(root, rect, queue);
        return queue;

    }
    private void rangeAdd(Node currentNode, RectHV rect, Queue<Point2D> queue)
    {
        if (currentNode != null && currentNode.inRect.intersects(rect))
        {
            if (rect.contains(currentNode.point))
            {
                queue.enqueue(currentNode.point);
            }
            rangeAdd(currentNode.left, rect, queue);
            rangeAdd(currentNode.right, rect, queue);
        }
    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null)
        {
            throw new IllegalArgumentException();
        }
        return nearest(root, null, new Node(p, -1)).point;
    }

    private Node nearest(Node currentNode, Node min, Node targetNode)
    {
        if (currentNode == null) return min;
        if (min == null) min = currentNode;
        if (targetNode.point.distanceTo(min.point) >= currentNode.inRect.distanceTo(targetNode.point))
        {
            if (currentNode.point.distanceTo(targetNode.point) < min.point.distanceTo(targetNode.point))
            {
                min = currentNode;
            }
            if (currentNode.level % 2 == 0)
            {
                if (right(currentNode, targetNode))
                {
                    min = nearest(currentNode.right, min, targetNode);
                    min = nearest(currentNode.left, min, targetNode);
                } else
                {
                    min = nearest(currentNode.left, min, targetNode);
                    min = nearest(currentNode.right, min, targetNode);
                }
            } else
            {
                if (above(currentNode, targetNode))
                {
                    min = nearest(currentNode.right, min, targetNode);
                    min = nearest(currentNode.left, min, targetNode);
                } else
                {
                    min = nearest(currentNode.left, min, targetNode);
                    min = nearest(currentNode.right, min, targetNode);
                }
            }
        }
        return min;


    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        Point2D p1 = new Point2D(1.0,2.0);
        KdTree ps = new KdTree();
        ps.insert(p1);
        ps.draw();

    }
}
