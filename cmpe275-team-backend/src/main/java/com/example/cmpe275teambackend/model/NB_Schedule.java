package com.example.cmpe275teambackend.model;

import org.hibernate.validator.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "nb_schedule")
public class NB_Schedule implements Serializable{
	
	@Id
    private String train_name;        // PK
	
	private HashMap<String, String> time_table;
    
    private String A;
    
    private String B;
    
    private String C;
    
    private String D;
   
    private String E;
    
    private String F;
    
    private String G;
    
    private String H;
    
    private String I;
    
    private String J;
    
    private String K;
    
    private String L;
    
    private String M;
    
    private String N;
    
    private String O;
    
    private String P;
    
    private String Q;
    
    private String R;
    
    private String S;
    
    private String T;
    
    private String U;
    
    private String V;
    
    private String W;
    
    private String X;
    
    private String Y;
    
    private String Z;
    
    // constructors, setters, getters, etc.
    
    public void setTrain_name(String train_name){
    	this.train_name = train_name;
    }
    
    public String getTrain_name(){
    	return train_name;
    }
    
    public void setA(String A){
    	this.A = A;
    }
    
    public String getA(){
    	return A;
    }

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}

	public String getE() {
		return E;
	}

	public void setE(String e) {
		E = e;
	}

	public String getF() {
		return F;
	}

	public void setF(String f) {
		F = f;
	}

	public String getG() {
		return G;
	}

	public void setG(String g) {
		G = g;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getI() {
		return I;
	}

	public void setI(String i) {
		I = i;
	}

	public String getJ() {
		return J;
	}

	public void setJ(String j) {
		J = j;
	}

	public String getK() {
		return K;
	}

	public void setK(String k) {
		K = k;
	}

	public String getL() {
		return L;
	}

	public void setL(String l) {
		L = l;
	}

	public String getM() {
		return M;
	}

	public void setM(String m) {
		M = m;
	}

	public String getN() {
		return N;
	}

	public void setN(String n) {
		N = n;
	}

	public String getO() {
		return O;
	}

	public void setO(String o) {
		O = o;
	}

	public String getP() {
		return P;
	}

	public void setP(String p) {
		P = p;
	}

	public String getQ() {
		return Q;
	}

	public void setQ(String q) {
		Q = q;
	}

	public String getR() {
		return R;
	}

	public void setR(String r) {
		R = r;
	}

	public String getS() {
		return S;
	}

	public void setS(String s) {
		S = s;
	}

	public String getT() {
		return T;
	}

	public void setT(String t) {
		T = t;
	}

	public String getU() {
		return U;
	}

	public void setU(String u) {
		U = u;
	}

	public String getV() {
		return V;
	}

	public void setV(String v) {
		V = v;
	}

	public String getW() {
		return W;
	}

	public void setW(String w) {
		W = w;
	}

	public String getX() {
		return X;
	}

	public void setX(String x) {
		X = x;
	}

	public String getY() {
		return Y;
	}

	public void setY(String y) {
		Y = y;
	}

	public String getZ() {
		return Z;
	}

	public void setZ(String z) {
		Z = z;
	}

	public HashMap<String, String> getTime_table() {
		return time_table;
	}

	public void setTime_table(HashMap<String, String> time_table) {
		this.time_table = time_table;
	}
    
}