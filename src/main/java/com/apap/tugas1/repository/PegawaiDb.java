package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.PegawaiModel;

/*
 * Pegawai DB
 */
@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findBynip(String nip);
	
	List<PegawaiModel> findByTahunMasukAndTanggalLahir(String tahunMasuk, Date tanggalLahir);

}
