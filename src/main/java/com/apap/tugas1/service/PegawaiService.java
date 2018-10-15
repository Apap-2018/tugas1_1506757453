package com.apap.tugas1.service;

import com.apap.tugas1.model.PegawaiModel;

/*
 * Pegawai Service
 */
public interface PegawaiService {
	PegawaiModel getDataPegawaiByNIP(String nip);
	
	void addPegawai(PegawaiModel pegawai);
	
	PegawaiModel getPegawaiTertuaDiInstansi(Long idInstansi);
	
	PegawaiModel getPegawaiTermudaDiInstansi(Long idInstansi);

}
