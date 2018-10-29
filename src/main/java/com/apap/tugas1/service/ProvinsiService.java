package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

public interface ProvinsiService {
	ProvinsiDb getProvinsiDb();

	Optional<ProvinsiModel> getProvinsiDetailById(long id);
	
	
}
