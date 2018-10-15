package com.apap.tugas1.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

/*
 * Pegawai Controller
 */
@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private ProvinsiService provinsiService;
	@Autowired
	private InstansiService instansiService;
	
	private PegawaiModel pegawaiArch;
	
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getDataPegawaiByNIP(nip);
			
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
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		pegawaiArch = new PegawaiModel();
		pegawaiArch.setListJabatan(new ArrayList<JabatanModel>());
		pegawaiArch.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawaiArch);
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params= {"addMoreJabatan"})
	private String addMoreJabatan(Model model) {
		pegawaiArch.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawaiArch);
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params={"submit"}, method = RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute PegawaiModel pegawai) {
		pegawaiService.addPegawai(pegawai);
		return "success-tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String getPilotTertuaTermuda(@RequestParam("idInstansi") Long idInstansi, Model model) {
		PegawaiModel tertua = pegawaiService.getPegawaiTertuaDiInstansi(idInstansi);
		PegawaiModel termuda = pegawaiService.getPegawaiTermudaDiInstansi(idInstansi);
		
		model.addAttribute("tertua", tertua);
		model.addAttribute("termuda", termuda);
		return "view-pegawai-tertua-termuda";
	}
	
	
}
