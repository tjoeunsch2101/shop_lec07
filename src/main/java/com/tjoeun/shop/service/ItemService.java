package com.tjoeun.shop.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tjoeun.shop.dto.ItemAndImageDto;
import com.tjoeun.shop.entity.Item;
import com.tjoeun.shop.entity.ItemImage;
import com.tjoeun.shop.repository.ItemImageRepository;
import com.tjoeun.shop.repository.ItemRepository;

import lombok.RequiredArgsConstructor;

// 상품 등록하기
@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
	
	private final ItemRepository itemRepository;
	private final ItemImageRepository itemImageRepository;
	private final ItemImageService itemImageService;
	
	// Item 저장하기
	public Long saveItem(ItemAndImageDto itemAndImageDto, List<MultipartFile> itemImageFileList) throws IOException {
		
		// 상품 등록하기 : DB 에도 저장함
		Item item = itemAndImageDto.createItem();
		itemRepository.save(item);
		
		// 이미지 등록하기 : DB 에도 저장함
		for(int i = 0; i < itemImageFileList.size(); i++) {
			ItemImage itemImage = new ItemImage();
			itemImage.setItem(item);
			
			if(i == 0) {
				itemImage.setRepresentativeImage("Y");
			}else {
				itemImage.setRepresentativeImage("N");
			}
			
			itemImageService.saveItemImage(itemImage, itemImageFileList.get(i));
			
		}
		return item.getId();
		
	} // saveItem

} // class



