package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.service.ProvinsiService;

public class ProvinsiController {
	@Autowired
	private ProvinsiService provinsiService;
	
	@RequestMapping(value = "/provinsi/{id}")
	public String getListInstansiByProvinsi(@PathVariable("id") Long id) {
	    return provinsiService.getProvinsiDb().getOne(id).getListInstansi().toString();
	}
	
}	
