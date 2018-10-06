package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

/*
 * Pegawai Controller
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	private String home() {
		return "home";
	}
	
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDataPegawaiByNIP(nip);
		if (pegawai != null) {
			double gajiPegawai = pegawai.getListJabatan().get(0).getGajiPokok();
			
			if(pegawai.getListJabatan().size() > 1) {
				for (int i=1; i<pegawai.getListJabatan().size(); i++) {
					 if (pegawai.getListJabatan().get(i).getGajiPokok() > gajiPegawai) {
						 gajiPegawai = pegawai.getListJabatan().get(i).getGajiPokok();
					 }
				}
			}
			
			double tunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan()/100;
			gajiPegawai = gajiPegawai + (tunjangan * gajiPegawai);
			String gaji = String.format ("%.0f", gajiPegawai);
			
			model.addAttribute("gaji", gaji);
			model.addAttribute("pegawai", pegawai);
			return "view-pegawai";
		}
		else {
			return "error";
		}
	}
	
}
