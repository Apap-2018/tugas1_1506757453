package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;


/*
 * Jabatan Service Impl
 */
@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDb jabatanDb;

	@Override
	public JabatanDb getJabatanDb() {
		return jabatanDb;
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}

	@Override
	public JabatanModel getJabatanById(Long id) {
		return jabatanDb.getOne(id);
	}

	@Override
	public void updateJabatan(JabatanModel jabatan) {
		JabatanModel jabatanToUpdate = jabatanDb.getOne(jabatan.getId());
		jabatanToUpdate.setNama(jabatan.getNama());
		jabatanToUpdate.setDeskripsi(jabatan.getDeskripsi());
		jabatanToUpdate.setGajiPokok(jabatan.getGajiPokok());
		
        jabatanDb.save(jabatan);
		
	}

	@Override
	public void deleteJabatanById(Long id) {
		jabatanDb.deleteById(id);	
	}
}
