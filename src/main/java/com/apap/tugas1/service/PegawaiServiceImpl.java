package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
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
	
//	@Override
//	public PegawaiModel getDataPegawaiByNIP(String nip) {
//		return pegawaiDb.findBynip(nip);
//	}
	
	@Override
    public Optional<PegawaiModel> getPegawaiDetailByNIP(String nip) {
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
		List<PegawaiModel> pegawais = pegawaiDb.findByInstansiAndTahunMasukAndTanggalLahir(pegawai.getInstansi(), pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
		pegawais.add(pegawai);
		kodeUrutanLahirMasuk = Integer.toString(pegawais.size());
		if (Integer.parseInt(kodeUrutanLahirMasuk) < 10) {
			kodeUrutanLahirMasuk = "0" + kodeUrutanLahirMasuk;
		}
		
		res = kodeInstansi + kodeTglLahir + kodeThnMasuk + kodeUrutanLahirMasuk;
		
		pegawai.setNip(res);
		System.out.print(pegawai.getListJabatan() != null);
		
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
//		PegawaiModel pegawaiToUpdate = pegawaiDb.getOne(pegawai.getId());
		// TODO Auto-generated method stub
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
		List<PegawaiModel> pegawais = pegawaiDb.findByInstansiAndTahunMasukAndTanggalLahir(pegawai.getInstansi(), pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
		for (int i = 0; i < pegawais.size(); i++) {
			if(pegawais.get(i).getId() == pegawai.getId()) {
				pegawais.remove(i);
			}
		}
		pegawais.add(pegawai);
		
		kodeUrutanLahirMasuk = Integer.toString(pegawais.size());
		if (Integer.parseInt(kodeUrutanLahirMasuk) < 10) {
			kodeUrutanLahirMasuk = "0" + kodeUrutanLahirMasuk;
		}
		
		res = kodeInstansi + kodeTglLahir + kodeThnMasuk + kodeUrutanLahirMasuk;
//		
//		pegawaiToUpdate.setNip(res);
//		pegawaiToUpdate.setNama(pegawai.getNama());
//		pegawaiToUpdate.setTempatLahir(pegawai.getTempatLahir());
//		pegawaiToUpdate.setTanggalLahir(pegawai.getTanggalLahir());
//		pegawaiToUpdate.setTahunMasuk(pegawai.getTahunMasuk());
//		pegawaiToUpdate.setInstansi(pegawai.getInstansi());
//		pegawaiToUpdate.setListJabatan(pegawai.getListJabatan());
//		
//		for (JabatanModel jabatan : pegawaiToUpdate.getListJabatan()) {
//			pegawaiToUpdate.removeJabatan(jabatan);
//		}
//		for (JabatanModel jabatan : pegawai.getListJabatan()) {
//			pegawaiToUpdate.addJabatan(jabatan);
//		}
////		for (JabatanModel jabatan : pegawaiToUpdate.getListJabatan()) {
//			pegawaiToUpdate.getListJabatan().remove(jabatan);
//		}
//		for (int i=0; i < pegawai.getListJabatan().size(); i++) {
//			pegawaiToUpdate.getListJabatan().add(pegawai.getListJabatan().get(i));
//		}
//		pegawaiToUpdate.setListJabatan(pegawai.getListJabatan());
		
//		for (JabatanModel jabatan : pegawaiToUpdate.getListJabatan()) {
//			pegawaiToUpdate.addJabatan(jabatan);
//		}
//		System.out.print(pegawai.getListJabatan().toString());
//		for (JabatanModel jabatan : pegawai.getListJabatan()) {
//			jabatanDb.save(jabatan);
////		}
//		pegawai.setId(id);
		
		pegawai.setNip(res);
		pegawaiDb.saveAndFlush(pegawai);
	}

	@Override
	public double calculateGaji(PegawaiModel pegawai) {
		// TODO Auto-generated method stub
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
		
		return gajiPegawai;
	}

	@Override
	public PegawaiModel getPegawaiById(Long id) {
		// TODO Auto-generated method stub
		return pegawaiDb.getOne(id);
	}

	@Override
	public void updateJabatanPegawai(Long id_pegawai, Long id_jabatan) {
		pegawaiDb.updateJabatanPegawai(id_pegawai, id_jabatan);
	}

//	@Override
//	public PegawaiModel getDataPegawaiByNIP(String nip) {
//		// TODO Auto-generated method stub
//		return pegawaiDb.findBynip(nip);
//	}
	
}
