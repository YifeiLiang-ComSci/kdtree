// /* *****************************************************************************
//  *  Name:
//  *  Date:
//  *  Description:
//  **************************************************************************** */
//
// import edu.princeton.cs.algs4.Point2D;
// import edu.princeton.cs.algs4.Queue;
// import edu.princeton.cs.algs4.RectHV;
// import edu.princeton.cs.algs4.StdDraw;
// class Node
// {
//     Point2D point;
//     int level;
//     Node left;
//     Node right;
//     RectHV inRect;
//
//     public Node(Point2D p, int l)
//     {
//         point = p;
//         level = l;
//     }
//     public Node(Point2D p,int l,double xmin,double xmax, double ymin, double ymax)
//     {
//         point = p;
//         level = l;
//         left = null;
//         right = null;
//         inRect = new RectHV(xmin,ymin,xmax,ymax);
//     }
// }
//
// public class KdTree {
//     Node root;
//     int size;
//     public KdTree()// construct an empty set of points
//     {
//         root = null;
//         size = 0;
//     }
//     public boolean isEmpty() // is the set empty?
//     {
//         return size == 0;
//     }
//     public int size()                         // number of points in the set
//     {
//         return size;
//     }
//     public boolean right(Node p1, Node p2)
//     {
//         if (p1.point.x() < p2.point.x())
//         {
//            return true;
//         } else if (p1.point.x() > p2.point.x())
//         {
//             return false;
//         } else if (p1.point.y() < p2.point.y())
//         {
//             return true;
//         } else
//         {
//             return false;
//         }
//     }
//     public boolean above(Node p1, Node p2)
//     {
//         if (p1.point.y() < p2.point.y())
//         {
//             return true;
//         } else if (p1.point.y() > p2.point.y())
//         {
//             return false;
//         } else if (p1.point.x() < p2.point.x())
//         {
//             return true;
//         } else {
//             return false;
//         }
//     }
//     public void insert(Point2D p)             // add the point to the set (if it is not already in the set)
//     {
//         if (contains(p))
//         {
//             return;
//         }
//         if (root == null)
//         {
//            root = new Node(p,0,0,1,0,1);
//            return;
//         }
//         Node currentNode = root;
//         double xmin = 0;
//         double ymin = 0;
//         double xmax = 1;
//         double ymax = 1;
//         Node newNode =  new Node(p,0,0,1,0,1);
//         while (true)
//         {
//             if (currentNode.level % 2 == 0 && right(currentNode, newNode) && currentNode.right == null)
//             {
//                 newNode.level = currentNode.level + 1;
//                 newNode.inRect = new RectHV(xmin,ymin,xmax,ymax);
//                 currentNode.right = newNode;
//
//
//                 break;
//             } else if (currentNode.level % 2 == 0 && right(currentNode, newNode) && currentNode.right != null)
//             {
//
//                 xmin = currentNode.point.x();
//                 currentNode = currentNode.right;
//
//             } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode) && currentNode.left == null)
//             {
//                 newNode.level = currentNode.level + 1;
//                 newNode.inRect = new RectHV(xmin,ymin,xmax,ymax);
//                 currentNode.left = newNode;
//
//                 break;
//             } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode) && currentNode.left != null)
//             {
//
//                 xmax =  currentNode.point.x();
//                 currentNode = currentNode.left;
//
//             } if (currentNode.level % 2 == 1 && above(currentNode, newNode) && currentNode.right == null)
//             {
//                 newNode.level = currentNode.level + 1;
//                 newNode.inRect = new RectHV(xmin,ymin,xmax,ymax);
//                 currentNode.right = newNode;
//
//                 break;
//             } else if (currentNode.level % 2 == 1 && above(currentNode, newNode) && currentNode.right != null)
//             {
//                 ymin = currentNode.point.y();
//                 currentNode = currentNode.right;
//
//             } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode) && currentNode.left == null)
//             {
//                 newNode.level = currentNode.level + 1;
//                 newNode.inRect = new RectHV(xmin,ymin,xmax,ymax);
//                 currentNode.left = newNode;
//
//                 break;
//             } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode) && currentNode.left != null)
//             {
//                 ymax = currentNode.point.y();
//                 currentNode = currentNode.left;
//             }
//
//         }
//     }
//     public boolean contains(Point2D p)            // does the set contain point p?
//     {
//         if(root == null) return false;
//         Node currentNode = root;
//         Node newNode = new Node(p,0);
//         while (true)
//         {
//             if(currentNode ==null)
//             {
//                 return false;
//             }
//             if (currentNode.point.equals(p))
//             {
//                 return true;
//             }
//             if (currentNode.level % 2 == 0 && right(currentNode, newNode) )
//             {
//                 currentNode = currentNode.right;
//             } else if (currentNode.level % 2 == 0 && !right(currentNode, newNode) )
//             {
//                 currentNode = currentNode.left;
//             } else if (currentNode.level % 2 == 1 && above(currentNode, newNode))
//             {
//                 currentNode = currentNode.right;
//             } else if (currentNode.level % 2 == 1 && !above(currentNode, newNode) )
//             {
//                 currentNode = currentNode.left;
//             }
//
//         }
//     }
//     private void drawRecursive(Node node)
//     {
//         System.out.println("drawRecursive");
//         if(node == null)
//         {
//             return;
//         }
//         drawRecursive(node.left);
//         StdDraw.point(node.point.x(), node.point.y());
//         drawRecursive(node.right);
//     }
//     public void draw()                         // draw all points to standard draw
//     {
//         drawRecursive(root);
//     }
//     public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
//     {
//
//         Queue<Point2D> queue = new Queue<Point2D>();
//         rangeAdd(root,rect,queue);
//         return queue;
//
//     }
//     public void rangeAdd(Node currentNode, RectHV rect, Queue<Point2D> queue)
//     {
//         if(currentNode!= null && currentNode.inRect.intersects(rect)){
//             if(rect.contains(currentNode.point)){
//                 queue.enqueue(currentNode.point);
//             }
//             rangeAdd(currentNode.left,rect,queue);
//             rangeAdd(currentNode.right,rect,queue);
//         }
//     }
//     public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
//     {
//         return nearest(root,null,new Node(p,-1)).point;
//     }
//
//     private Node nearest(Node currentNode,Node min,Node targetNode)
//     {
//         if (currentNode == null) return min;
//         if (min == null) min = currentNode;
//         if (targetNode.point.distanceTo(min.point) >= currentNode.inRect.distanceTo(targetNode.point))
//         {
//             if (currentNode.point.distanceTo(targetNode.point) < min.point.distanceTo(targetNode.point))
//             {
//                 min = currentNode;
//             }
//             if (currentNode.level % 2 == 0)
//             {
//                 if (right(currentNode, targetNode))
//                 {
//                     min = nearest(currentNode.right, min, targetNode);
//                     min = nearest(currentNode.left, min, targetNode);
//                 } else
//                 {
//                     min = nearest(currentNode.left, min, targetNode);
//                     min = nearest(currentNode.right, min, targetNode);
//                 }
//             } else
//             {
//                 if (above(currentNode, targetNode))
//                 {
//                     min = nearest(currentNode.right, min, targetNode);
//                     min = nearest(currentNode.left, min, targetNode);
//                 } else
//                 {
//                     min = nearest(currentNode.left, min, targetNode);
//                     min = nearest(currentNode.right, min, targetNode);
//                 }
//             }
//         }
//         return min;
//
//
//     }
//
//     public static void main(String[] args)                  // unit testing of the methods (optional)
//     {
//         Point2D p1 = new Point2D(1.0,2.0);
//         PointSET ps = new PointSET();
//         ps.insert(p1);
//         ps.draw();
//
//     }
// }
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;
import java.util.List;

/**
 * 2d-tree implementation. A mutable data type that uses a 2d-tree to implement
 * the same API (but replace {@code PointSET} with {@code KdTree}). A 2d-tree is
 * a generalization of a BST to two-dimensional keys. The idea is to build a BST
 * with points in the nodes, using the x- and y-coordinates of the points as
 * keys in strictly alternating sequence.
 *
 * @author Mincong Huang
 */
public class KdTree {

    private enum Separator { VERTICAL, HORIZONTAL }
    private Node root;
    private int size;

    private static class Node {

        private final Separator sepr;
        private final RectHV rect;
        private final Point2D p;
        private Node leftBottom;
        private Node rightTop;

        Node(Point2D p, Separator sepr, RectHV rect) {
            this.p = p;
            this.sepr = sepr;
            this.rect = rect;
        }

        public Separator nextSepr() {
            return sepr == Separator.VERTICAL ?
                   Separator.HORIZONTAL : Separator.VERTICAL;
        }

        public RectHV rectLB() {
            return sepr == Separator.VERTICAL
                   ? new RectHV(rect.xmin(), rect.ymin(), p.x(), rect.ymax())
                   : new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), p.y());
        }

        public RectHV rectRT() {
            return sepr == Separator.VERTICAL
                   ? new RectHV(p.x(), rect.ymin(), rect.xmax(), rect.ymax())
                   : new RectHV(rect.xmin(), p.y(), rect.xmax(), rect.ymax());
        }

        public boolean isRightOrTopOf(Point2D q) {
            return (sepr == Separator.HORIZONTAL && p.y() > q.y())
                    || (sepr == Separator.VERTICAL && p.x() > q.x());
        }
    }

    /**
     * Construct an empty set of points
     */
    public KdTree() {
        root = null;
        size = 0;
    }

    /**
     * Is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * Add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        checkNull(p);
        if (root == null) {
            root = new Node(p, Separator.VERTICAL, new RectHV(0, 0, 1, 1));
            size++;
            return;
        }

        // find node position for insertion
        Node prev = null;
        Node curr = root;
        do {
            if (curr.p.equals(p)) {
                return;
            }
            prev = curr;
            curr = curr.isRightOrTopOf(p) ? curr.leftBottom : curr.rightTop;
        } while (curr != null);

        // prepare new node and insert
        if (prev.isRightOrTopOf(p)) {
            prev.leftBottom = new Node(p, prev.nextSepr(), prev.rectLB());
        } else {
            prev.rightTop = new Node(p, prev.nextSepr(), prev.rectRT());
        }
        size++;
    }

    /**
     * Does the set contain point p?
     */
    public boolean contains(Point2D p) {
        checkNull(p);
        Node node = root;
        while (node != null) {
            if (node.p.equals(p)) {
                return true;
            }
            node = node.isRightOrTopOf(p) ? node.leftBottom : node.rightTop;
        }
        return false;
    }

    /**
     * Draw all points to standard draw
     */
    public void draw() {
        // TODO
    }

    /**
     * All points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        checkNull(rect);
        List<Point2D> results = new LinkedList<>();
        addAll(root, rect, results);
        return results;
    }

    /**
     * Add all points under target node using DFS.
     */
    private void addAll(Node node, RectHV rect, List<Point2D> results) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.p)) {
            results.add(node.p);
            addAll(node.leftBottom, rect, results);
            addAll(node.rightTop, rect, results);
            return;
        }
        if (node.isRightOrTopOf(new Point2D(rect.xmin(), rect.ymin()))) {
            addAll(node.leftBottom, rect, results);
        }
        if (!node.isRightOrTopOf(new Point2D(rect.xmax(), rect.ymax()))) {
            addAll(node.rightTop, rect, results);
        }
    }

    /**
     * A nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        checkNull(p);
        return isEmpty() ? null : nearest(p, root.p, root);
    }

    private Point2D nearest(Point2D target, Point2D closest, Node node) {
        if (node == null) {
            return closest;
        }
        // Recursively search left/bottom or right/top
        // if it could contain a closer point
        double closestDist = closest.distanceTo(target);
        if (node.rect.distanceTo(target) < closestDist) {
            double nodeDist = node.p.distanceTo(target);
            if (nodeDist < closestDist) {
                closest = node.p;
            }
            if (node.isRightOrTopOf(target)) {
                closest = nearest(target, closest, node.leftBottom);
                closest = nearest(target, closest, node.rightTop);
            } else {
                closest = nearest(target, closest, node.rightTop);
                closest = nearest(target, closest, node.leftBottom);
            }
        }
        return closest;
    }

    private void checkNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
    }

    /**
     * Unit testing of the methods (optional)
     */
    public static void main(String[] args) {
    }
}