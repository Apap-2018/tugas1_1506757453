package com.apap.tugas1.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.repository.ProvinsiDb;

/*
 * Pegawai Service Impl
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService{
	@Autowired
	private PegawaiDb pegawaiDb;
	@Autowired
	private InstansiDb instansiDb;
	@Autowired
	private ProvinsiDb provinsiDb;
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public PegawaiModel getDataPegawaiByNIP(String nip) {
		return pegawaiDb.findBynip(nip);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		String res="";
		
		//kode instansi
		String kodeInstansi = Long.toString(pegawai.getInstansi().getId());
		
		//tanggal lahir pegawai
		String kodeTglLahir = pegawai.getTanggalLahir().toString();
		String tgl = kodeTglLahir.substring(8);
		String bln = kodeTglLahir.substring(5, 7);
		String thn = kodeTglLahir.substring(2, 4);
		kodeTglLahir = tgl + bln + thn;
		
		//kode tahun masuk pegawai
		String kodeThnMasuk = pegawai.getTahunMasuk();
		
		//kode urutan tanggal lahir sama pegawai
		String kodeUrutanLahirMasuk = "";
		List<PegawaiModel> pegawais = pegawaiDb.findByTahunMasukAndTanggalLahir(pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
		pegawais.add(pegawai);
		kodeUrutanLahirMasuk = Integer.toString(pegawais.size());
		if (Integer.parseInt(kodeUrutanLahirMasuk) < 10) {
			kodeUrutanLahirMasuk = "0" + kodeUrutanLahirMasuk;
		}
		
		res = kodeInstansi + kodeTglLahir + kodeThnMasuk + kodeUrutanLahirMasuk;
		
		pegawai.setNip(res);
		
		pegawaiDb.save(pegawai);
	}

	@Override
	public PegawaiModel getPegawaiTertuaDiInstansi(Long idInstansi) {
		List<PegawaiModel> pegawaiInInstansi = instansiDb.getOne(idInstansi).getListPegawai();
		PegawaiModel oldest = pegawaiInInstansi.get(0);
		
		for (int i=1; i<pegawaiInInstansi.size(); i++) {
			if (pegawaiInInstansi.get(i).getTanggalLahir().before(oldest.getTanggalLahir())) {
				oldest = pegawaiInInstansi.get(i);
			}
		}
		return oldest;
	}

	@Override
	public PegawaiModel getPegawaiTermudaDiInstansi(Long idInstansi) {
		List<PegawaiModel> pegawaiInInstansi = instansiDb.getOne(idInstansi).getListPegawai();
		PegawaiModel youngest = pegawaiInInstansi.get(0);
		
		for (int i=1; i<pegawaiInInstansi.size(); i++) {
			if (pegawaiInInstansi.get(i).getTanggalLahir().after(youngest.getTanggalLahir())) {
				youngest = pegawaiInInstansi.get(i);
			}
		}
		return youngest;
	}

	@Override
	public PegawaiDb getPegawaiDb() {
		return pegawaiDb;
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
		PegawaiModel pegawaiToUpdate = pegawaiDb.getOne(pegawai.getId());
		pegawaiToUpdate.setNama(pegawai.getNama());
		pegawaiToUpdate.setTempatLahir(pegawai.getTempatLahir());
		pegawaiToUpdate.setTanggalLahir(pegawai.getTanggalLahir());
		pegawaiToUpdate.setTahunMasuk(pegawai.getTahunMasuk());
		pegawaiToUpdate.setInstansi(pegawai.getInstansi());
		pegawaiToUpdate.setListJabatan(pegawai.getListJabatan());
		
		String res="";
		
		//kode instansi
		String kodeInstansi = Long.toString(pegawai.getInstansi().getId());
		
		//tanggal lahir pegawai
		String kodeTglLahir = pegawai.getTanggalLahir().toString();
		String tgl = kodeTglLahir.substring(8);
		String bln = kodeTglLahir.substring(5, 7);
		String thn = kodeTglLahir.substring(2, 4);
		kodeTglLahir = tgl + bln + thn;
		
		//kode tahun masuk pegawai
		String kodeThnMasuk = pegawai.getTahunMasuk();
		
		//kode urutan tanggal lahir sama pegawai
		String kodeUrutanLahirMasuk = "";
		List<PegawaiModel> pegawais = pegawaiDb.findByTahunMasukAndTanggalLahir(pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
		pegawais.add(pegawai);
		kodeUrutanLahirMasuk = Integer.toString(pegawais.size());
		if (Integer.parseInt(kodeUrutanLahirMasuk) < 10) {
			kodeUrutanLahirMasuk = "0" + kodeUrutanLahirMasuk;
		}
		
		res = kodeInstansi + kodeTglLahir + kodeThnMasuk + kodeUrutanLahirMasuk;
		
		pegawaiToUpdate.setNip(res);

        
        pegawaiDb.save(pegawaiToUpdate);
	}
	
}
