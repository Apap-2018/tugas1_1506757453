package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	@Autowired
	private JabatanService jabatanService;
	
	private Long pass_id_jabatan;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		
		model.addAttribute("jabatan", jabatan);
		return "success-tambah-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/view")
	private String viewPegawai(@RequestParam("idJabatan") Long idJabatan, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		pass_id_jabatan = idJabatan;		
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "jabatan/ubah", method = RequestMethod.GET)
	 private String updateJabatan(@RequestParam("idJabatan") Long idJabatan, Model model) {
		 JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
		 model.addAttribute("jabatan", jabatan);
		 return "update-jabatan";
	 }

	 @RequestMapping(value = "jabatan/ubah", method = RequestMethod.POST)
	 private String updateJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		 jabatan.setId(pass_id_jabatan);
		 jabatanService.updateJabatan(jabatan);
		 model.addAttribute("jabatan", jabatan);
		 
	     return "update-success";
	 }

	 @RequestMapping(value = "jabatan/hapus", method = RequestMethod.POST) 
	 private String deleteJabatan(){
		 jabatanService.deleteJabatanById(pass_id_jabatan);
		 
		 return "delete-success";
	 }
	 
	 @RequestMapping(value = "jabatan/viewall", method = RequestMethod.GET)
	 private String viewAllJabatan(Model model) {
		 List<JabatanModel> allJabatan = jabatanService.getJabatanDb().findAll(); 
		 	
		 model.addAttribute("allJabatan", allJabatan);
		 return "view-all-jabatan";
	 }
	
}
