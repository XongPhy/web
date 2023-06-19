package com.example.web.Services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.web.Model.Size;
import com.example.web.Repositories.SizeRepository;


@Service
@Transactional
public class SizeService {
	@Autowired
	private SizeRepository sizeRepository;

	public List<Size> listAll() {
		return sizeRepository.findAll();
	}

	public void save(Size size) {
		sizeRepository.save(size);
	}

	public Size get(long id) {
		return sizeRepository.findById(id).orElse(null);
	}

	public void delete(long id) {
		sizeRepository.deleteById(id);
	}
}
