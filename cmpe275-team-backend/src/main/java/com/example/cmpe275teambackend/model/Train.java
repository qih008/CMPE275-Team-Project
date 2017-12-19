package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "train")
public class Train implements Serializable{
	
	@Id
    private String name;        // PK
    
    private String direction;    
    
    private String start_time;
 
    private int capacity;
    
    private boolean express;
    
    private int A;
    
    private int B;
    
    private int C;
    
    private int D;
   
    private int E;
    
    private int F;
    
    private int G;
    
    private int H;
    
    private int I;
    
    private int J;
    
    private int K;
    
    private int L;
    
    private int M;
    
    private int N;
    
    private int O;
    
    private int P;
    
    private int Q;
    
    private int R;
    
    private int S;
    
    private int T;
    
    private int U;
    
    private int V;
    
    private int W;
    
    private int X;
    
    private int Y;
    
    private int Z;
 
    @OneToMany(mappedBy = "train")
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();    // one transaction can map to many tickets
  
    
    // constructors, setters, getters, etc.
    
    public void setName(String name){
    	this.name = name;
    }
    
    public String getName(){
    	return name;
    }
    
    public void setDirection(String direction){
    	this.direction = direction;
    }
    
    public String getDirection(){
    	return direction;
    }
    
    public void setStart_time(String start_time){
    	this.start_time = start_time;
    }
    
    public String getStart_time(){
    	return start_time;
    }
    
    public void setCapacity(int capacity){
    	this.capacity = capacity;
    }
    
    public int getCapacity(){
    	return capacity;
    }
    
    public void setExpress(boolean express){
    	this.express = express;
    }
    
    public boolean getExpress(){
    	return express;
    }
    
    public void setTickets(List<Ticket> tickets){
    	this.tickets = tickets;
    }
    
    public List<Ticket> getTickets(){
    	return tickets;
    }
    
    public void setA(int A){
    	this.A = A;
    }
    
    public int getA(){
    	return A;
    }

	public int getB() {
		return B;
	}

	public void setB(int b) {
		B = b;
	}

	public int getC() {
		return C;
	}

	public void setC(int c) {
		C = c;
	}

	public int getD() {
		return D;
	}

	public void setD(int d) {
		D = d;
	}

	public int getE() {
		return E;
	}

	public void setE(int e) {
		E = e;
	}

	public int getF() {
		return F;
	}

	public void setF(int f) {
		F = f;
	}

	public int getG() {
		return G;
	}

	public void setG(int g) {
		G = g;
	}

	public int getH() {
		return H;
	}

	public void setH(int h) {
		H = h;
	}

	public int getI() {
		return I;
	}

	public void setI(int i) {
		I = i;
	}

	public int getJ() {
		return J;
	}

	public void setJ(int j) {
		J = j;
	}

	public int getK() {
		return K;
	}

	public void setK(int k) {
		K = k;
	}

	public int getL() {
		return L;
	}

	public void setL(int l) {
		L = l;
	}

	public int getM() {
		return M;
	}

	public void setM(int m) {
		M = m;
	}

	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
	}

	public int getO() {
		return O;
	}

	public void setO(int o) {
		O = o;
	}

	public int getP() {
		return P;
	}

	public void setP(int p) {
		P = p;
	}

	public int getQ() {
		return Q;
	}

	public void setQ(int q) {
		Q = q;
	}

	public int getR() {
		return R;
	}

	public void setR(int r) {
		R = r;
	}

	public int getS() {
		return S;
	}

	public void setS(int s) {
		S = s;
	}

	public int getT() {
		return T;
	}

	public void setT(int t) {
		T = t;
	}

	public int getU() {
		return U;
	}

	public void setU(int u) {
		U = u;
	}

	public int getV() {
		return V;
	}

	public void setV(int v) {
		V = v;
	}

	public int getW() {
		return W;
	}

	public void setW(int w) {
		W = w;
	}

	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}

	public int getZ() {
		return Z;
	}

	public void setZ(int z) {
		Z = z;
	}
   
    
}