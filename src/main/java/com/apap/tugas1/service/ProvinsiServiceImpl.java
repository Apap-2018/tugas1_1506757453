package com.apap.tugas1.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService {
	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public ProvinsiDb getProvinsiDb() {
		return provinsiDb;
	}
	
	@Override
	public Optional<ProvinsiModel> getProvinsiDetailById(long id) {
		return provinsiDb.findById(id);
	}
}
