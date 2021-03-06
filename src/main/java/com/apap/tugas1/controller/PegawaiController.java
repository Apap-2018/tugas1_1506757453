package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;
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
	
	//Variabel untuk dipass antar controller
	private PegawaiModel pegawaiArch;
	private PegawaiModel pegawaiArch2;
	private Long id_to_pass;
	
	@RequestMapping(value = "/pegawai")
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNIP(nip).get();
		double gajiFinal = pegawaiService.calculateGaji(pegawai);

		String gaji = String.format ("%.0f", gajiFinal);
			
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
	private String addMoreJabatan(@ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.setListJabatan(pegawaiArch.getListJabatan());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params={"submit"}, method = RequestMethod.POST)
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "success-tambah-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String update(@RequestParam("nip") String nip, Model model) {
		id_to_pass = pegawaiService.getPegawaiDb().findBynip(nip).get().getId();
		pegawaiArch2 = pegawaiService.getPegawaiById(id_to_pass);
		
		model.addAttribute("pegawai", pegawaiArch2);
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		return "update-pegawai";
	}

	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"addMoreJabatanUpdate"})
	private String addMoreJabatanUpdate(@RequestParam("nip") String nip, @ModelAttribute PegawaiModel pegawai, BindingResult bindingResult, Model model) {
		pegawai.setListJabatan(pegawaiArch2.getListJabatan());
		pegawai.getListJabatan().add(new JabatanModel());
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		
		return "update-pegawai";
	}

	@RequestMapping(value = "/pegawai/ubah", params={"submit"}, method = RequestMethod.POST)
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		pegawai.setId(id_to_pass);
		pegawaiService.updatePegawai(pegawai);

		model.addAttribute("pegawai", pegawaiService.getPegawaiDb().getOne(id_to_pass).getNip());
		return "success-update-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari")
	private String cari(Model model) {
		model.addAttribute("text", null);
		model.addAttribute("listPegawai", ((PegawaiService) pegawaiService).getPegawaiDb().findAll());
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET)
	private String cariFilter(@RequestParam(value = "idProvinsi", required = false) Long idProvinsi,
							  @RequestParam(value = "id_Instansi", required = false) Long id_Instansi,
							  @RequestParam(value = "id_Jabatan", required = false) Long id_Jabatan, Model model) {
		
		List<PegawaiModel> listPegawai = new ArrayList<PegawaiModel>();
		
		if(idProvinsi != null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndInstansiAndJabatan(idProvinsi, id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi, instansi, dan jabatan");
		}
		else if (idProvinsi != null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndInstansi(idProvinsi, id_Instansi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan instansi");
		}
		else if (idProvinsi != null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsiAndJabatan(idProvinsi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi dan jabatan");
		}
		else if (idProvinsi == null && id_Instansi != null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByInstansiAndJabatan(id_Instansi, id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi dan jabatan");		
		}
		else if (idProvinsi != null && id_Instansi == null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByProvinsi(idProvinsi);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan provinsi");
		}
		else if (idProvinsi == null && id_Instansi != null && id_Jabatan == null) {
			listPegawai = pegawaiService.getPegawaiDb().findByInstansi(instansiService.getInstansiDb().getOne(id_Instansi));
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan instansi");
		}
		else if (idProvinsi == null && id_Instansi == null && id_Jabatan != null) {
			listPegawai = pegawaiService.getPegawaiDb().findByJabatan(id_Jabatan);
			model.addAttribute("text", "Hasil pencarian pegawai berdasarkan jabatan");
		}
		
		model.addAttribute("listPegawai", listPegawai);
		
		model.addAttribute("allProvinsi", ((ProvinsiService) provinsiService).getProvinsiDb().findAll());
		model.addAttribute("allInstansi", ((InstansiService) instansiService).getInstansiDb().findAll());
		model.addAttribute("allJabatan", ((JabatanService) jabatanService).getJabatanDb().findAll());
		
		model.addAttribute("idProvinsi", idProvinsi);
		model.addAttribute("id_Instansi", id_Instansi);
		model.addAttribute("id_Jabatan", id_Jabatan);
		
		return "cari-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String getPilotTertuaTermuda(@RequestParam("idInstansi") Long idInstansi, Model model) {
		PegawaiModel tertua = pegawaiService.getPegawaiTertuaDiInstansi(idInstansi);
		PegawaiModel termuda = pegawaiService.getPegawaiTermudaDiInstansi(idInstansi);
		
		double gajiTertuaFinal = pegawaiService.calculateGaji(tertua);
		double gajiTermudaFinal = pegawaiService.calculateGaji(termuda);
		
		String gajiTertua = String.format ("%.0f", gajiTertuaFinal);
		String gajiTermuda = String.format ("%.0f", gajiTermudaFinal);
		
		model.addAttribute("tertua", tertua);
		model.addAttribute("gajiTertua", gajiTertua);
		model.addAttribute("termuda", termuda);
		model.addAttribute("gajiTermuda", gajiTermuda);
		return "view-pegawai-tertua-termuda";
	}
	
	
}
