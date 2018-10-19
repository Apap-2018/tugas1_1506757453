package com.apap.tugas1.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

/*
 * Pegawai DB
 */
@Repository
public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findBynip(String nip);
	
	List<PegawaiModel> findByInstansiAndTahunMasukAndTanggalLahir(InstansiModel instansi, String tahunMasuk, Date tanggalLahir);
	
	@Query(value = "SELECT p.* FROM pegawai p, provinsi pr, instansi i WHERE p.id_instansi = i.id AND i.id_provinsi = pr.id AND pr.id = :idProvinsi"
			, nativeQuery = true)
	List<PegawaiModel> findByProvinsi(@Param("idProvinsi") Long idProvinsi);
	
	List<PegawaiModel> findByInstansi(InstansiModel instansi);
	
	@Query(value = "SELECT p.* FROM pegawai p, jabatan j, jabatan_pegawai jp WHERE p.id = jp.id_pegawai AND j.id = jp.id_jabatan AND j.id = :id_Jabatan"
			, nativeQuery = true)
	List<PegawaiModel> findByJabatan(@Param("id_Jabatan") Long id_Jabatan);
	
	@Query(value = "SELECT p.* FROM pegawai p, provinsi pr, instansi i WHERE p.id_instansi = i.id AND i.id_provinsi = pr.id AND pr.id = :idProvinsi AND i.id = :id_Instansi"
			, nativeQuery = true)
	List<PegawaiModel> findByProvinsiAndInstansi(@Param("idProvinsi") Long idProvinsi,
												 @Param("id_Instansi") Long id_Instansi);
	
	@Query(value = "SELECT p.* FROM pegawai p, provinsi pr, instansi i, jabatan j, jabatan_pegawai jp WHERE p.id_instansi = i.id AND i.id_provinsi = pr.id AND j.id = jp.id_jabatan AND p.id = jp.id_pegawai AND pr.id = :idProvinsi AND j.id = :id_Jabatan"
			, nativeQuery = true)
	List<PegawaiModel> findByProvinsiAndJabatan(@Param("idProvinsi") Long idProvinsi,
												 @Param("id_Jabatan") Long id_Jabatan);
	
	@Query(value = "SELECT p.* FROM pegawai p, instansi i, jabatan j, jabatan_pegawai jp WHERE p.id_instansi = i.id AND j.id = jp.id_jabatan AND p.id = jp.id_pegawai AND i.id = :id_Instansi AND j.id = :id_Jabatan"
			, nativeQuery = true)
	List<PegawaiModel> findByInstansiAndJabatan(@Param("id_Instansi") Long id_Instansi,
												 @Param("id_Jabatan") Long id_Jabatan);
	
	@Query(value = "SELECT p.* FROM pegawai p, instansi i, provinsi pr, jabatan j, jabatan_pegawai jp WHERE p.id_instansi = i.id AND i.id_provinsi = pr.id AND j.id = jp.id_jabatan AND p.id = jp.id_pegawai AND pr.id = :idProvinsi AND i.id = :id_Instansi AND j.id = :id_Jabatan"
			, nativeQuery = true)
	List<PegawaiModel> findByProvinsiAndInstansiAndJabatan(@Param("idProvinsi") Long idProvinsi,
														   @Param("id_Instansi") Long id_Instansi,
														   @Param("id_Jabatan") Long id_Jabatan);
	
	
	
	

}
