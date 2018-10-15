package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;

@Controller
public class PageController {
	@Autowired
	private JabatanService jabatanService;
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<JabatanModel> allJabatan = jabatanService.getJabatanDb().findAll();
		List<InstansiModel> allInstansi = instansiService.getInstansiDb().findAll();
		
		model.addAttribute("allJabatan", allJabatan);
		model.addAttribute("allInstansi", allInstansi);
		return "home";
	}
}
