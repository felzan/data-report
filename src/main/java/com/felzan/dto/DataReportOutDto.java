package com.felzan.dto;

public class DataReportOutDto {

	private Integer clientQuantity;
	private Integer salesmanQuantity;
	private String highestSale;
	private Double highestSaleAmount;
	private String worstSalesman;

	public Integer getClientQuantity() {
		return clientQuantity;
	}

	public void setClientQuantity(Integer clientQuantity) {
		this.clientQuantity = clientQuantity;
	}

	public Integer getSalesmanQuantity() {
		return salesmanQuantity;
	}

	public void setSalesmanQuantity(Integer salesmanQuantity) {
		this.salesmanQuantity = salesmanQuantity;
	}

	public String getHighestSale() {
		return highestSale;
	}

	public void setHighestSale(String highestSale) {
		this.highestSale = highestSale;
	}

	public Double getHighestSaleAmount() {
		return highestSaleAmount;
	}

	public void setHighestSaleAmount(Double highestSaleAmount) {
		this.highestSaleAmount = highestSaleAmount;
	}

	public String getWorstSalesman() {
		return worstSalesman;
	}

	public void setWorstSalesman(String worstSalesman) {
		this.worstSalesman = worstSalesman;
	}

	public String getOutput() {
		return clientQuantity + "ç" + salesmanQuantity + "ç" + highestSale + "ç" + worstSalesman;
	}

}
