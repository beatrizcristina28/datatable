package com.edupsousa.datatable;

import java.util.LinkedHashMap;

public class ExportHTML implements Export{

	@Override
	public String export(DataTable dataTable, LinkedHashMap<String, Integer> columnsTypes) {
		
		DataTableRow row;
		String output = "";

		output = "<table>\n";
		output += "<tr>";
		for(String collumnName : columnsTypes.keySet()){
			output+="<td>"+collumnName+"</td>";
		}
		output+="</tr>\n";
		for (int i = 0; i < dataTable.rowsCount(); i++) {
			row = dataTable.getRow(i);
			output += "<tr>";
			for (String collumnName : columnsTypes.keySet()) {
				output += "<td>" + row.getValue(collumnName) + "</td>";
			}
			output += "</tr>\n";
		}
		output += "</table>\n";

		return output;

	}

}
