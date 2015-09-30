package com.edupsousa.datatable;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.JOptionPane;

public class DataTable {

	public static final int TYPE_INT = 0;
	public static final int TYPE_STRING = 1;

	public static final int FORMAT_CSV = 0;
	public static final int FORMAT_HTML = 1;

	private LinkedHashMap<String, Integer> columnsTypes = new LinkedHashMap<String, Integer>();
	private ArrayList<DataTableRow> rows = new ArrayList<DataTableRow>();

	public int columnsCount() {
		return columnsTypes.size();
	}

	public int rowsCount() {
		return rows.size();
	}

	public void addCollumn(String name, int type) {
		columnsTypes.put(name, type);
	}

	public boolean hasCollumn(String name) {
		return columnsTypes.containsKey(name);
	}

	public DataTableRow createRow() {
		return new DataTableRow(this);
	}

	public void insertRow(DataTableRow row) {
		checkRowCompatibilityAndThrows(row);
		rows.add(row);
	}

	public DataTableRow lastRow() {
		return rows.get(rows.size()-1);
	}

	public int getCollumnType(String collumn) {
		return columnsTypes.get(collumn);
	}

	private void checkRowCompatibilityAndThrows(DataTableRow row) {
		for (String collumnName : columnsTypes.keySet()) {
			if (row.hasValueFor(collumnName) && 
					!(isValueCompatible(columnsTypes.get(collumnName), row.getValue(collumnName)))) {
				throw new ClassCastException("Wrong type for collumn " + collumnName + ".");
			}
		}
	}

	private boolean isValueCompatible(int type, Object value) {
		if (type == this.TYPE_INT && !(value instanceof Integer)) {
			return false;
		} else if (type == this.TYPE_STRING && !(value instanceof String)) {
			return false;
		}
		return true;
	}

	public DataTableRow getRow(int i) {
		return rows.get(i);
	}

	public String export(int format) {
		if (format==FORMAT_HTML) {
			ExportHTML html = new ExportHTML();
			return html.export(this, columnsTypes);
		}else {
			ExportCSV csv= new ExportCSV();
			return csv.export(this, columnsTypes);
		}
	}

	public void insertRowAt(DataTableRow row, int index) {
		rows.add(index, row);
	}

	public DataTable sortAscending(String collumn) {
		if (columnsTypes.get(collumn) != TYPE_INT) {
			throw new ClassCastException("Only Integer columns can be sorted.");
		}
		for (int i = 0; i < rows.size(); i++) {
			for (int j = 0; j < rows.size()-1; j++) {
				if ((int)rows.get(j).getValue(collumn)>(int)rows.get(j+1).getValue(collumn)) {
					changePositions(j);
				}
			}
		}
		return this;
	}

	public DataTable sortDescending(String collumn) {
		if (columnsTypes.get(collumn) != TYPE_INT) {
			throw new ClassCastException("Only Integer columns can be sorted.");
		}
		for (int i = 0; i < rows.size(); i++) {
			for (int j = 0; j < rows.size()-1; j++) {
				if ((int)rows.get(j).getValue(collumn)<(int)rows.get(j+1).getValue(collumn)) {
					changePositions(j);
				}
			}
		}
		return this;
	}
	
	//Troca de posição os elementos de rows para realizar a ordenação
	public void changePositions(int j){
		DataTableRow maior = rows.get(j);
		DataTableRow menor = rows.get(j+1);
		rows.remove(j);
		rows.remove(j);
		rows.add(j,maior);
		rows.add(j,menor);
	}

	public DataTable filterEqual(String collumn, Object value) {
		for (int i = 0; i < rows.size(); i++) {
			if(columnsTypes.get(collumn) == TYPE_INT){
				if (rows.get(i).getValue(collumn) != value) {
					rows.remove(i);
				}
			}else if (columnsTypes.get(collumn) == TYPE_STRING){
				if (!rows.get(i).getValue(collumn).equals(value)) {
					rows.remove(i);
				}
			}		
		}		
		return this; 
	}

	public DataTable filterNotEqual(String collumn, Object value) {
		for (int i = 0; i < rows.size(); i++) {
			if(columnsTypes.get(collumn) == TYPE_INT){
				if (rows.get(i).getValue(collumn) == value) {
					rows.remove(i);
				}
			}else if (columnsTypes.get(collumn) == TYPE_STRING){
				if (rows.get(i).getValue(collumn).equals(value)) {
					rows.remove(i);
				}
			}		
		}
		return this;
	}
}
