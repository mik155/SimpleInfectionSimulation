package com.vector;

public interface VectorI
{
    double getX();
    double getY();
    double getPhi();
    double length();
    void setVector(double phi, double length);
    Object clone();
}
