package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.ProductDto;
import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.dtos.Response;
import com.br.tcc.bfn.models.Product;
import com.br.tcc.bfn.services.IProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @PostMapping()
    public ResponseEntity<Response<Product>> register(@RequestBody RegisterProductDto request) {
        Response<Product> dtoResponse = new Response<>();

        try{
            Product product =  productService.register(request);
            dtoResponse.setData(product);
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Product>> disableProduct(@PathVariable Long id){
        Response<Product> dtoResponse = new Response<>();
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

    @GetMapping("/{id}")
    public ResponseEntity<Response<Product>> findById(@PathVariable Long id){
        Response<Product> dtoResponse = new Response<>();
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

    @GetMapping
    public ResponseEntity<Response<Page<ProductDto>>> findAll(Pageable pageable){
        Response<Page<ProductDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(productService.findAll(pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Product>> updateProduct(@PathVariable Long id, @RequestBody RegisterProductDto request){
        Response<Product> dtoResponse = new Response<>();
        try{
            Product product = productService.update(id,request);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(product);
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
