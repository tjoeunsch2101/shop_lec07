package com.tjoeun.shop.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.shop.dto.ItemAndImageDto;
import com.tjoeun.shop.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


   
@Controller
@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class ItemController {
	
	private final ItemService itemService;	
	
	@GetMapping("/register")
	public String registerForm(ItemAndImageDto itemAndImageDto) {
		return "item/register";
	}

	@PostMapping("/register")
	public String registerItem(@Valid ItemAndImageDto itemAndImageDto, BindingResult result,
			                       @RequestParam("itemImageFile") List<MultipartFile> itemImageFileList,
			                       Model model)  {
		
		if(result.hasErrors()) {
			return "item/register";
		}
		
		if(itemImageFileList.get(0).isEmpty() && itemAndImageDto.getId() == null) {
			model.addAttribute("errorMsg", "첫 번째 상품 이미지는 꼭 올려주세요");
			return "item/register";
		}
		
		try {
			itemService.saveItem(itemAndImageDto, itemImageFileList);
		} catch (IOException e) {
			model.addAttribute("errorMsg", "상품 등록 중 오류가 발생함 !!!");
			return "item/register";
		}
		
		return "redirect:/";
	}
	
	
}
