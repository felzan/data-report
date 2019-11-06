package com.felzan.model;

public class Salesman {

	private String CPF;
	private String name;
	private double salary;

	public Salesman(String cPF, String name, double salary) {
		CPF = cPF;
		this.name = name;
		this.salary = salary;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Salesman [CPF=" + CPF + ", name=" + name + ", salary=" + salary + "]";
	}

}
