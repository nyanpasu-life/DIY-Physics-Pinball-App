package pinball.Engine;

import com.example.pinball.Engine.Vector2D;

interface LinearLine{
    com.example.pinball.Engine.LinearLine getPerpLineFromDot(Vector2D dot);
    Vector2D getCrossingPoint(com.example.pinball.Engine.LinearLine other);
    double getDistanceFromDot(Vector2D dot);
}

class LinearEquation implements com.example.pinball.Engine.LinearLine { //y = Ax +b
    double A;
    double B;
    LinearEquation(double a, double b){
        A = a;
        B = b;
    }

    @Override
    public com.example.pinball.Engine.LinearLine getPerpLineFromDot(Vector2D dot) {
        if(A ==0){
            return new com.example.pinball.Engine.LinearNoYLine(dot.X);
        }
        else{
            double newA = -1/A;
            double newB = dot.X/A +dot.Y;
            return new com.example.pinball.Engine.LinearEquation(newA, newB);
        }
    }

    @Override
    public Vector2D getCrossingPoint(com.example.pinball.Engine.LinearLine other) {//ax+by =p , cx+dy =q
        double a = -A; double b =1; double p = B;
        double c; double d; double q;
        if(other instanceof com.example.pinball.Engine.LinearEquation){
            c = -((com.example.pinball.Engine.LinearEquation) other).A; d =1; q = ((com.example.pinball.Engine.LinearEquation)other).B;;
        }
        else {
            c = 1; d= 0; q = ((com.example.pinball.Engine.LinearNoYLine) other).A;
        }
        Vector2D resultV = CrossLines(a,b,p,c,d,q);
        return resultV;

    }



    @Override
    public double getDistanceFromDot(Vector2D dot) {
        double distance;
        double x = dot.X;  double y = dot.Y;  //직선 y-Ax-B=0 와 dot 의 x,y 의 교점.

        double numerator = Math.abs((-A*x)+y-B);
        double denominator = Math.sqrt(A*A+1);
        distance = numerator / denominator;
        return distance;
    }

    static Vector2D CrossLines(double a, double b, double p, double c, double d, double q) {

        double denominator = (a*d)-(b*c);
        if(denominator==0){
            return new Vector2D(99999999,999999999); //본래는 평행한 선이므로 교차점이 없지만 nullExpetion을 일으키지 않기 위해 비정상적인 값을 리턴.
        }
        else {
            double xNumerator = (a*q)-(c*p);
            double yNumerator = (d*p)-(b*q);
            return new Vector2D(xNumerator/denominator, yNumerator/denominator);
        }
    }
}


class LinearNoYLine implements com.example.pinball.Engine.LinearLine { //x = A;
    double A;
    LinearNoYLine(double a){
        A = a;
    }

    @Override
    public com.example.pinball.Engine.LinearLine getPerpLineFromDot(Vector2D dot) {
        return new com.example.pinball.Engine.LinearEquation(0,dot.Y);
    }

    public Vector2D getCrossingPoint(com.example.pinball.Engine.LinearLine other) {//ax+by =p , cx+dy =q
        double a = 1; double b =0; double p = A;
        double c; double d; double q;
        if(other instanceof com.example.pinball.Engine.LinearEquation){
            c = -((com.example.pinball.Engine.LinearEquation) other).A; d =1; q = ((com.example.pinball.Engine.LinearEquation)other).B;;
        }
        else {
            c = 1; d= 0; q = ((com.example.pinball.Engine.LinearNoYLine) other).A;
        }
        Vector2D resultV = com.example.pinball.Engine.LinearEquation.CrossLines(a,b,p,c,d,q);
        return resultV;
    }

    @Override
    public double getDistanceFromDot(Vector2D dot) {
        return Math.abs(dot.X-A);
    }
}


