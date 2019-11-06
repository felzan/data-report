package com.felzan;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.felzan.dto.DataReportOutDto;
import com.felzan.model.Client;
import com.felzan.model.Item;
import com.felzan.model.Sale;
import com.felzan.model.Salesman;

public class Main {

	public static void main(String[] args) throws Exception {
		String path = System.getenv("HOMEPATH");
		String pathIn = path + "/data/in";
		String pathOut = path + "/data/out/";
		
		if (path == null) {
			throw new Exception("HOMEPATH not found!");
		}
		List<String> tmp = new ArrayList<>();
		while (true) {
			File folder = new File(pathIn);
			for (int i = 0; i < folder.listFiles().length; i++) {
				if (folder.list()[i].endsWith(".dat") && !tmp.contains(folder.list()[i])) {
					tmp.add(folder.list()[i]);
					DataReportOutDto report = processDataFile(Files.readAllLines(Paths.get(pathIn + "/" + folder.list()[i])));
					outputReport(report, pathOut, folder.list()[i]);
					System.out.println("Processing file: " + folder.list()[i]);	
				}
			}
		}
	}

	public static void outputReport(DataReportOutDto report, String pathOut, String fileName) throws IOException {
		fileName = fileName.replace(".dat", ".done.dat");
		File newReport = new File(pathOut + fileName);
		newReport.createNewFile();
		System.out.println(pathOut + fileName);
		FileWriter fw = new FileWriter(pathOut + fileName);
		fw.write(report.getOutput());
		fw.close();
	}

	public static DataReportOutDto processDataFile(List<String> data) {
		List<Salesman> salesmanList = new ArrayList<>();
		Map<String, Double> salesmanXSales = new HashMap<>();
		List<Client> clientList = new ArrayList<>();
		List<Sale> salesList = new ArrayList<>();
		DataReportOutDto report = new DataReportOutDto();
		report.setHighestSaleAmount(0.0);

		readInfos(data, salesmanList, salesmanXSales, clientList, salesList, report);
		String worstSalesman = getWorstSalesman(salesmanXSales);

		report.setClientQuantity(clientList.size());
		report.setSalesmanQuantity(salesmanList.size());
		report.setWorstSalesman(worstSalesman);
		return report;
	}

	public static String getWorstSalesman(Map<String, Double> salesmanXSales) {
		Entry<String, Double> min = null;
		for (Entry<String, Double> entry : salesmanXSales.entrySet()) {
		    if (min == null || min.getValue() > entry.getValue()) {
		        min = entry;
		    }
		}
		return min.getKey();
	}

	public static void readInfos(List<String> data, List<Salesman> salesmanList, Map<String, Double> salesmanXSales,
			List<Client> clientList, List<Sale> salesList, DataReportOutDto report) {
		data.forEach(line -> {
			String[] columns = line.split("ç");
			if (columns[0].equals("001")) {
				Salesman salesman = new Salesman(columns[1], columns[2], Double.valueOf(columns[3]));
				salesmanList.add(salesman);
			}
			if (columns[0].equals("002")) {
				Client client = new Client(columns[1], columns[2], columns[3]);
				clientList.add(client);
			}
			if (columns[0].equals("003")) {
				List<Item> itemList = new ArrayList<>();
				String[] items = columns[2].replaceAll("\\[|\\]|\\(|\\)", "").split(",");
				for (int i = 0; i < items.length; i++) {
					String[] itemInfo = items[i].split("-");
					Item newItem = new Item(Integer.valueOf(itemInfo[0]), Integer.valueOf(itemInfo[1]), Double.valueOf(itemInfo[2]));
					itemList.add(newItem);
				}
				Double totalSale = itemList.stream()
				.mapToDouble(item -> item.getTotal())
				.sum();
				if (totalSale > report.getHighestSaleAmount()) {
					report.setHighestSaleAmount(totalSale);
					report.setHighestSale(columns[1]);
				}
				Sale sale = new Sale(columns[1], itemList, columns[3]);
				if (salesmanXSales.get(columns[3]) != null) {
					salesmanXSales.put(columns[3], salesmanXSales.get(columns[3]) + totalSale);
				} else {
					salesmanXSales.put(columns[3], totalSale);
				}
				salesList.add(sale);
			}
		});
	}

}