package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Register new Product on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register new Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Register new Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PostMapping()
    public ResponseEntity<Response<ProductDto>> register(@RequestBody RegisterProductDto request) {
        Response<ProductDto> dtoResponse = new Response<>();

        try{
            dtoResponse.setData(productService.register(request));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Disable Product on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disable Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Disable Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> disableProduct(@PathVariable Long id){
        Response<Void> dtoResponse = new Response<>();
        try{
            productService.disableProduct(id);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @Operation(summary = "Find Product By Id on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Product By Id on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Product By Id on Applicationn",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> findById(@PathVariable Long id){
        Response<ProductDto> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.findById(id));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }


    @Operation(summary = "Find All Products on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find All Products on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Products on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping
    public Response<Page<ProductDto>> findAll(Pageable pageable){
        Response<Page<ProductDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.findAll(pageable));
            return dtoResponse;
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return dtoResponse;
        }

    }

    @Operation(summary = "Find Product By UF on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Product By UF on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Product UF Id on Applicationn",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/region/{uf}")
    public ResponseEntity<Response<Page<ProductDto>>> findByRegion(@PathVariable String uf, Pageable pageable){
        Response<Page<ProductDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.findByUf(uf,pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Update Product on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Update Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PutMapping("/{id}")
    public ResponseEntity<Response<ProductDto>> updateProduct(@PathVariable Long id, @RequestBody RegisterProductDto request){
        Response<ProductDto> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.update(id,request));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Find Product By Category on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Product By Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Product Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/category/{category}")
    public ResponseEntity<Response<Page<ProductDto>>> getByCategory(@PathVariable String category, Pageable pageable){
        Response<Page<ProductDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.findByCategory(category, pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Find All Category on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find All Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/findAllCategories")
    public ResponseEntity<Response<List<CategoryDto>>> getAllCategories(){
        Response<List<CategoryDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.getAllCategories());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
