package com.apap.tugas1.service;


import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

/*
 * Pegawai Service
 */
public interface PegawaiService {
	PegawaiDb getPegawaiDb();
	
	PegawaiModel getDataPegawaiByNIP(String nip);
	
	void addPegawai(PegawaiModel pegawai);
	
	void updatePegawai(PegawaiModel pegawai, Long id);
	
	PegawaiModel getPegawaiTertuaDiInstansi(Long idInstansi);
	
	PegawaiModel getPegawaiTermudaDiInstansi(Long idInstansi);
	
	double calculateGaji(PegawaiModel pegawai);

}
