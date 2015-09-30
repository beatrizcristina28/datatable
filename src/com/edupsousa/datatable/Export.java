package com.edupsousa.datatable;

import java.util.LinkedHashMap;

public interface Export {
	public String export(DataTable dataTable, LinkedHashMap<String, Integer> columnsTypes);
}
