package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;

public interface InstansiService {
	InstansiDb getInstansiDb();
	
	List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);
}
