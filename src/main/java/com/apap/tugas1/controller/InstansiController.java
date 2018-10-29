package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class InstansiController {
	@Autowired 
	private ProvinsiService provinsiService;
	
//	@RequestMapping(value = "/instansi/cari",method = RequestMethod.GET)
//	private @ResponseBody List<InstansiModel> getInstansiByProvinsi(@RequestParam(value="idProvinsi") Long idProvinsi) {
//		ProvinsiModel provinsi = provinsiService.getProvinsiDb().getOne(idProvinsi);
//		return instansiService.getInstansiDb().findByProvinsi(provinsi);
//	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi", method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinsiModel provinsi = provinsiService.getProvinsiDetailById(provinsiId).get();
		
		return provinsi.getListInstansi();
	}
}
