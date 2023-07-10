package com.vector.adapter;

import com.vector.Vector;
import com.vector.VectorI;

public class VectorAdapter implements VectorI
{
    private Vector vector;
    private double phi;

    public VectorAdapter(Vector v)
    {
        vector = v;
    };


    public Vector getSource()
    {
        return vector;
    }

    @Override
    public double getX()
    {
        return vector.getX();
    }

    @Override
    public double getY()
    {
        return vector.getY();
    }

    @Override
    public double getPhi()
    {
        return phi;
    }

    @Override
    public double length()
    {
        double x = vector.getX();
        double y = vector.getY();;
        return Math.sqrt(x * x + y * y);
    }

    @Override
    public void setVector(double phi, double length)
    {
        this.phi = phi;
        vector.setX(Math.cos((phi * 180) / Math.PI) * length);
        vector.setY(Math.sin((phi * 180) / Math.PI) * length);
    }

    @Override
    public Object clone()
    {
        return new VectorAdapter((Vector) vector.clone());
    }
}
