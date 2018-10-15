package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

public interface JabatanService {
	JabatanDb getJabatanDb();
	
	void addJabatan(JabatanModel jabatan);
	
	void updateJabatan(JabatanModel jabatan);
	
	void deleteJabatanById(Long id);
	
	JabatanModel getJabatanById(Long id);
}
