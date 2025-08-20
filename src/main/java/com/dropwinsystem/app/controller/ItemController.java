package com.dropwinsystem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dropwinsystem.app.domain.Item;
import com.dropwinsystem.app.domain.Member;
import com.dropwinsystem.app.service.ItemService;
import com.dropwinsystem.app.service.FavoriteService;

import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("/register")
    public String showItemForm() {
        return "itemForm";
    }

    @GetMapping("/detail/{id}")
    public String getItemDetail(@PathVariable("id") int id, Model model, HttpSession session) {
        Item item = itemService.getItemById(id);

        if (item == null) {
            return "error/404";
        }

        model.addAttribute("item", item);

        String mainImageUrl = "/img/no_image_available.png";
        if (item.getImagePaths() != null && !item.getImagePaths().isEmpty()) {
            List<String> imageNames = Arrays.asList(item.getImagePaths().split(","));
            if (!imageNames.isEmpty()) {
                mainImageUrl = "/auction_uploads/" + imageNames.get(0);
            }
        }
        model.addAttribute("mainImageUrl", mainImageUrl);

        Member member = (Member) session.getAttribute("loggedInUser");
        boolean isFavorited = false;
        if (member != null) {
            isFavorited = favoriteService.isItemFavorited(member.getId(), id);
        }
        model.addAttribute("isFavorited", isFavorited);
        System.out.println("ItemController: 물품 상세 페이지 로드 - ID: " + id);
        return "views/itemDetail";
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerItem(
            Item item,
            @RequestParam(value = "imageFile", required = false) List<MultipartFile> imageFiles,
            HttpSession session) {

        Map<String, String> response = new HashMap<>();

        Member member = (Member) session.getAttribute("loginUser");
        String sellerId = null;

        if (member != null) {
            sellerId = member.getId();
            System.out.println("ItemController: 물품 등록 시도 - 로그인된 sellerId: " + sellerId);
        } else {
            response.put("status", "error");
            response.put("message", "물품 등록을 위해 로그인이 필요합니다.");
            System.out.println("ItemController: 물품 등록 시도 - 로그인되지 않음.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }

        if (imageFiles != null && imageFiles.size() > 5) {
            response.put("status", "error");
            response.put("message", "이미지는 최대 5장까지만 업로드할 수 있습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<String> uploadedImagePaths = new ArrayList<>();
        String uploadDir = System.getProperty("user.home") + File.separator + "auction_uploads";

        File uploadPath = new File(uploadDir);
        if (!uploadPath.exists()) {
            boolean created = uploadPath.mkdirs();
            if (!created) {
                System.err.println("업로드 디렉토리 생성 실패: " + uploadPath.getAbsolutePath());
                response.put("status", "error");
                response.put("message", "서버 업로드 디렉토리 생성에 실패했습니다.");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        if (imageFiles != null && !imageFiles.isEmpty()) {
            for (MultipartFile file : imageFiles) {
                if (!file.isEmpty()) {
                    try {
                        String originalFileName = file.getOriginalFilename();
                        String fileExtension = "";
                        if (originalFileName != null && originalFileName.contains(".")) {
                            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                        }
                        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
                        File dest = new File(uploadPath, uniqueFileName);
                        file.transferTo(dest);

                        uploadedImagePaths.add(uniqueFileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        response.put("status", "error");
                        response.put("message", "이미지 업로드 중 오류가 발생했습니다: " + e.getMessage());
                        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                }
            }
        }

        item.setStatus("pending");
        item.setSellerId(sellerId);
        item.setImagePaths(String.join(",", uploadedImagePaths));

        try {
            itemService.registerItem(item);
            response.put("status", "success");
            response.put("message", "물품 등록 요청이 완료되었습니다!");
            System.out.println("ItemController: 물품 등록 성공!");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", "물품 등록 중 오류가 발생했습니다: " + e.getMessage());
            System.err.println("ItemController: 물품 등록 실패 - " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/bid")
    public ResponseEntity<Map<String, String>> placeBid(@RequestBody Map<String, Integer> payload) {
        Map<String, String> response = new HashMap<>();
        
        Integer itemId = payload.get("itemId");
        Integer bidPrice = payload.get("bidPrice");

        if (itemId == null || bidPrice == null) {
            response.put("status", "error");
            response.put("message", "입찰 정보가 누락되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
        try {
            boolean success = itemService.placeBid(itemId, bidPrice);
            if (success) {
                response.put("status", "success");
                response.put("message", "입찰이 성공적으로 완료되었습니다!");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("status", "error");
                response.put("message", "입찰에 실패했습니다. 현재 가격을 확인해주세요.");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "입찰 처리 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
