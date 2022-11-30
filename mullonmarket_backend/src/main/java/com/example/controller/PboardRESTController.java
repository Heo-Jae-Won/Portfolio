package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.dao.PboardDAO;
import com.example.domain.PboardVO;
import com.example.service.PboardService;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
@RequestMapping("/api/pboard")
public class PboardRESTController {
	@Autowired
	PboardDAO pdao;

	@Autowired
	PboardService service;

	@RequestMapping("/list")
	public HashMap<String, Object> list(@Param("page") int page, @Param("num") int num,
			@Param("searchType") String searchType, @Param("keyword") String keyword) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", pdao.list(page, num, searchType, keyword));
		map.put("total", pdao.getTotal(searchType, keyword));
		return map;
	};

	@RequestMapping("/readpwriterid/{pcode}")
	public String readPwriterId(@PathVariable String pcode) {
		if (pdao.read(pcode) != null) {
			return pdao.readPwriterId(pcode);
		} else {
			return null;
		}
	};

	@RequestMapping("/readpcondition/{pcode}")
	public int readPcondition(@PathVariable String pcode) {
		return pdao.readPcondition(pcode);
	};

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void update(PboardVO updateVO, MultipartHttpServletRequest multi) throws Exception {
		if (multi.getFile("file") != null) {

			String path = "c:/upload/project/";
			MultipartFile file = multi.getFile("file");

			// 파일의 확장자가 jpg,png인지 확인
			String contentType = file.getContentType();
			if (contentType.contains("image/png") || contentType.contains("image/jpeg")) {
				System.out.println("성공");
			} else {
				throw new Exception("이미지 파일은 jpg,png만 가능합니다. ");
			}

			File newFile = new File(path + file.getOriginalFilename());
			if (!newFile.exists()) {
				FileOutputStream thumnail = new FileOutputStream(newFile);
				Thumbnailator.createThumbnail(file.getInputStream(), thumnail, 300, 300);
				thumnail.close();
			}
			updateVO.setPcode(updateVO.getPcode());
			updateVO.setPimage("/upload/project/" + file.getOriginalFilename());
		}
		pdao.update(updateVO);
	};

	@RequestMapping("/read/{pcode}")
	public PboardVO readviewcnt(@PathVariable String pcode) {
		if (pdao.read(pcode) != null) {
			return service.read(pcode);
		} else {
			return null;
		}
	};

	@RequestMapping("/onlyread/{pcode}")
	public PboardVO read(@PathVariable String pcode) {
		if (pdao.read(pcode) != null) {
			return pdao.read(pcode);
		} else {
			return null;
		}
	};

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	public void insert(@Valid PboardVO insertVO, MultipartHttpServletRequest multi) throws Exception {
		System.out.println(multi.getFile("file"));
		if (multi.getFile("file") != null) {

			String path = "c:/upload/project/";
			MultipartFile file = multi.getFile("file");

			String contentType = file.getContentType();
			if (contentType.contains("image/png") || contentType.contains("image/jpeg")) {
				System.out.println("성공");
			} else {
				throw new Exception("이미지 파일은 jpg,png만 가능합니다. ");
			}

			File newFile = new File(path + file.getOriginalFilename());

			if (!newFile.exists())
				file.transferTo(newFile);
			insertVO.setPimage("/upload/project/" + file.getOriginalFilename());
			UUID code = UUID.randomUUID();
			insertVO.setPcode(code.toString());
		}
		pdao.insert(insertVO);
	};

	@RequestMapping("/best")
	public List<PboardVO> best() {
		return pdao.best();
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public void delete(String pcode) {
		pdao.delete(pcode);
	}

}
