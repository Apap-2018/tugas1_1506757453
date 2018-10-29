package com.apap.tugas1.service;


import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

/*
 * Pegawai Service
 */
public interface PegawaiService {
	PegawaiDb getPegawaiDb();
	
//	PegawaiModel getDataPegawaiByNIP(String nip);
	
	PegawaiModel getPegawaiById(Long id);
	
	Optional<PegawaiModel> getPegawaiDetailByNIP(String nip);
	
	void addPegawai(PegawaiModel pegawai);
	
	void updatePegawai(PegawaiModel pegawai);
	
	void updateJabatanPegawai(Long id_pegawai, Long id_jabatan);
	
	PegawaiModel getPegawaiTertuaDiInstansi(Long idInstansi);
	
	PegawaiModel getPegawaiTermudaDiInstansi(Long idInstansi);
	
	double calculateGaji(PegawaiModel pegawai);

}
