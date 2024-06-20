package vn.iostar.springbootbackend.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.iostar.springbootbackend.embeddedId.IdBicycleProduct;
import vn.iostar.springbootbackend.embeddedId.IdBicyclesOfCategory;
import vn.iostar.springbootbackend.entity.*;
import vn.iostar.springbootbackend.model.*;
import vn.iostar.springbootbackend.model.response.BaseResponse;
import vn.iostar.springbootbackend.service.impl.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class BicycleController {
    @Autowired
    private BicycleImageService bicycleImageService;

    @Autowired
    private BicycleService bicycleService;

    @Autowired
    private BicycleSizeService bicycleSizeService;

    @Autowired
    private BicycleColorService bicycleColorService;

    @Autowired
    private BicyclesOfCategoryService bicyclesOfCategoryService;

    @Autowired
    private BicycleCategoryService bicycleCategoryService;

    @Autowired
    private BicycleProductService bicycleProductService;

    @GetMapping("/bicycle/{idBicycle}")
    public ResponseEntity<?> getBicycleById(@PathVariable Long idBicycle) {
        Optional<Bicycle> optBicycle = bicycleService.getBicycleById(idBicycle);
        if (optBicycle.isPresent()) {
            Bicycle bicycle = optBicycle.get();
            BicycleModel bicycleModel = new BicycleModel();
            BeanUtils.copyProperties(bicycle, bicycleModel);
            bicycleModel.setThumbnails(new ArrayList<>());
            for(BicycleThumbnail bicycleThumbnail : bicycle.getBicycleThumbnails()) {
                bicycleModel.getThumbnails().add(bicycleThumbnail.getSource());
            }
            return ResponseEntity.ok(BaseResponse.builder().code(200).status("success").message("Get bicycle successfully!").data(bicycleModel).build());
        }
        return ResponseEntity.ok(BaseResponse.builder().message("Bicycle not found!").status("error").code(404).build());
    }

    @GetMapping("/bicycle/sizes")
    public ResponseEntity<?> getAllSizes() {
        List<BicycleSize> sizes = bicycleSizeService.getAllSizes();
        List<BicycleSizeModel> listSizes = new ArrayList<>();
        for(BicycleSize size : sizes) {
            BicycleSizeModel model = new BicycleSizeModel();
            BeanUtils.copyProperties(size, model);
            listSizes.add(model);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listSizes).message("Get all sizes successfully!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycle/colors")
    public ResponseEntity<?> getAllColors() {
        List<BicycleColor> colors = bicycleColorService.getAllColors();
        List<BicycleColorModel> listColors = new ArrayList<>();
        for(BicycleColor color : colors) {
            BicycleColorModel model = new BicycleColorModel();
            BeanUtils.copyProperties(color, model);
            listColors.add(model);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listColors).message("Get all colors successfully!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycles")
    public ResponseEntity<?> getAllBicycles() {
        List<Bicycle> bicycles = bicycleService.getAllBicycles();
        List<BicycleModel> listBicycles = new ArrayList<>();
        for(Bicycle bicycle : bicycles) {
            BicycleModel model = new BicycleModel();
            BeanUtils.copyProperties(bicycle, model);
            listBicycles.add(model);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listBicycles).message("Get all bicycles successfully!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycles/pagination/{offset}/{pageSize}")
    public ResponseEntity<?> getBicyclesWithPagination(@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize) {
        Page<Bicycle> bicycles = bicycleService.getBicyclesWithPagination(offset, pageSize);
        List<BicycleModel> listBicycles = new ArrayList<>();
        for(Bicycle bicycle : bicycles) {
            BicycleModel model = new BicycleModel();
            BeanUtils.copyProperties(bicycle, model);
            listBicycles.add(model);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listBicycles).message("Get all bicycles successfully!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycles/paginationAndSort/{type}/{offset}/{pageSize}/{field}")
    public ResponseEntity<?> getBicyclesWithPaginationAndSorting(@PathVariable("type") String type,@PathVariable("offset") int offset, @PathVariable("pageSize") int pageSize, @PathVariable("field") String field) {
        Page<Bicycle> bicycles = bicycleService.getBicyclesWithPaginationAndSorting(offset, pageSize, field, type);
        List<BicycleModel> listBicycles = new ArrayList<>();
        for (Bicycle bicycle : bicycles) {
            BicycleModel bicycleModel = new BicycleModel();
            BeanUtils.copyProperties(bicycle, bicycleModel);
            listBicycles.add(bicycleModel);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(listBicycles).message("Get all bicycles successfully!").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bicycles/filter")
    public ResponseEntity<?> filterBicycles(@RequestBody BicycleFilterModel model, @RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort) {
//        if(model.getBicycleCategoriesId().size() == 0 && model.getBicycleSizesId().size() == 0 && model.getBicycleColorsId().size() == 0) {
//            Page<Bicycle> bicycles = bicycleService.getBicyclesWithPagination(page, size);
//            List<BicycleModel> listBicycles = new ArrayList<>();
//            for(Bicycle bicycle : bicycles) {
//                BicycleModel bicycleModel = new BicycleModel();
//                BeanUtils.copyProperties(bicycle, bicycleModel);
//                listBicycles.add(bicycleModel);
//            }
//            List<Bicycle> lstBicycle = bicycleService.getAllBicycles();
//            ProductsResponse productsResponse = ProductsResponse.builder().totalProducts(lstBicycle.size()).bicycles(listBicycles).build();
//            BaseResponse response = BaseResponse.builder().status("success").code(200).data(productsResponse).message("Get Bicycles With Filter Successfully!").build();
//            return ResponseEntity.ok(response);
//        }
        List<Bicycle> bicyclesRes = new ArrayList<>();
        List<Bicycle> bicyclesPrice = bicycleService.getBicyclesLessOrEqualThan(model.getMaxPrice());
        if(sort.equals("asc")) {
            bicyclesPrice = bicyclesPrice.stream()
                    .sorted(Comparator.comparingDouble(Bicycle::getPrice))
                    .collect(Collectors.toList());
        } else if(sort.equals("desc")) {
            bicyclesPrice = bicyclesPrice.stream()
                    .sorted(Comparator.comparingDouble(Bicycle::getPrice).reversed())
                    .collect(Collectors.toList());
        }
        List<Long> bicyclesId = new ArrayList<>();
        List<Long> categoriesId = model.getBicycleCategoriesId();
        if(categoriesId.size() != 0) {
            for(Bicycle bicycle : bicyclesPrice) {
                for(Long idCategory : categoriesId) {
                    if(bicyclesOfCategoryService.checkBicyclesOfCategoryExist(new IdBicyclesOfCategory(bicycle.getIdBicycle(), idCategory))) {
                        if(!bicyclesId.contains(bicycle.getIdBicycle())) {
                            bicyclesId.add(bicycle.getIdBicycle());
                        }
                    }
                }
            }
        } else {
            for(Bicycle bicycle : bicyclesPrice) {
                bicyclesId.add(bicycle.getIdBicycle());
            }
        }

        List<Long> colorsId = model.getBicycleColorsId();
        List<Long> sizesId = model.getBicycleSizesId();
        List<Long> bicyclesIdFinal;

        boolean checkColor = !colorsId.isEmpty();
        boolean checkSize = !sizesId.isEmpty();

        if(!checkColor && !checkSize) {
            bicyclesIdFinal = new ArrayList<>(bicyclesId);
        } else {
            bicyclesIdFinal = new ArrayList<>();
            if(checkColor && checkSize) {
                for(Long idBicycle : bicyclesId) {
                    for(Long idColor : colorsId) {
                        for(Long idSize : sizesId) {
                            IdBicycleProduct idBicycleProduct = new IdBicycleProduct(idBicycle, idSize, idColor);
                            if(bicycleProductService.checkExistIdBicycleProduct(idBicycleProduct)) {
                                if(!bicyclesIdFinal.contains(idBicycle)) {
                                    bicyclesIdFinal.add(idBicycle);
                                }
                            }
                        }
                    }
                }
            } else if (!checkColor && checkSize) {
                for(Long idBicycle : bicyclesId) {
                    for(Long idSize : sizesId) {
                        if(bicycleProductService.checkExistByBicycleAndSize(idBicycle, idSize)) {
                            if(!bicyclesIdFinal.contains(idBicycle)) {
                                bicyclesIdFinal.add(idBicycle);
                            }
                        }
                    }
                }
            } else if (checkColor && !checkSize) {
                for(Long idBicycle : bicyclesId) {
                    for(Long idColor : colorsId) {
                        if(bicycleProductService.checkExistByBicycleAndColor(idBicycle, idColor)) {
                            if(!bicyclesIdFinal.contains(idBicycle)) {
                                bicyclesIdFinal.add(idBicycle);
                            }
                        }
                    }
                }
            }
        }

        for(Long id : bicyclesIdFinal) {
            Optional<Bicycle> opt = bicycleService.getBicycleById(id);
            opt.ifPresent(bicyclesRes::add);
        }

        int fromIdx = page * size;
        int totalBicycles = bicyclesRes.size();
        int toIdx = Math.min(fromIdx + size, bicyclesRes.size());
        List<Bicycle> newBicycleRes = bicyclesRes.subList(fromIdx, toIdx);

        List<BicycleModel> listBicycleModels = new ArrayList<>();
        for(Bicycle bicycle : newBicycleRes) {
            BicycleModel bicycleModel = new BicycleModel();
            BeanUtils.copyProperties(bicycle, bicycleModel);
            listBicycleModels.add(bicycleModel);
        }
        ProductsResponse productsResponse = ProductsResponse.builder().totalProducts(totalBicycles).bicycles(listBicycleModels).build();
        BaseResponse response = BaseResponse.builder().status("success").code(200).data(productsResponse).message("Get bicycles with filter successfully!").build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycle/{idBicycle}/categories")
    public ResponseEntity<?> getAllCategoriesOfBicycle(@PathVariable("idBicycle") Long idBicycle) {
        List<BicyclesOfCategory> bicyclesOfCategories = bicyclesOfCategoryService.getAllCategoriesOfBicycle(idBicycle);
        List<BicycleOfCategoryModel> bicycleOfCategoryModels = new ArrayList<BicycleOfCategoryModel>();
        for(BicyclesOfCategory bicyclesOfCategory : bicyclesOfCategories) {
            Optional<BicycleCategory> optBicycleCategory = bicycleCategoryService.getCategoryById(bicyclesOfCategory.getBicycleCategory().getIdBicycleCategory());
            if(optBicycleCategory.isPresent()) {
                BicycleCategory bicycleCategory = optBicycleCategory.get();
                BicycleOfCategoryModel bicycleOfCategoryModel = new BicycleOfCategoryModel();
                bicycleOfCategoryModel.setIdCategory(bicycleCategory.getIdBicycleCategory());
                bicycleOfCategoryModel.setName(bicycleCategory.getName());
                bicycleOfCategoryModels.add(bicycleOfCategoryModel);
            }
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).message("Get all categories of bicycle successfully!").data(bicycleOfCategoryModels).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bicycle/{idBicycle}/relevant")
    public ResponseEntity<?> getFourBicycleRelevant(@PathVariable("idBicycle") Long idBicycle) {
        List<BicyclesOfCategory> bicyclesOfCategories = bicyclesOfCategoryService.getAllCategoriesOfBicycle(idBicycle);
        List<BicyclesOfCategory> bicyclesOfCategoriesRelevant = bicyclesOfCategoryService.findByIdBicycleNotAndIdBicycleCategory(idBicycle, bicyclesOfCategories.get(0).getIdBicyclesOfCategory().getIdBicycleCategory());
        if(bicyclesOfCategoriesRelevant.size() > 4) {
            bicyclesOfCategoriesRelevant = bicyclesOfCategoriesRelevant.subList(0, 4);
        }
        List<BicycleModel> bicycleModels = new ArrayList<>();
        for(BicyclesOfCategory bicyclesOfCategory : bicyclesOfCategoriesRelevant) {
            Bicycle bicycle = bicyclesOfCategory.getBicycle();
            BicycleModel bicycleModel = new BicycleModel();
            BeanUtils.copyProperties(bicycle, bicycleModel);
            bicycleModels.add(bicycleModel);
        }
        BaseResponse response = BaseResponse.builder().status("success").code(200).message("Get all bicycles relevant successfully!").data(bicycleModels).build();
        return ResponseEntity.ok(response);
    }
}
