package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestPopulator implements Populator<Product, RegisterProductDto>{
    @Override
    public void populate(Product product, RegisterProductDto registerProductDto) {
        product.setActive(Boolean.TRUE);
        product.setName(registerProductDto.getName());
        product.setDescription(registerProductDto.getDescription());
        product.setQuantity(registerProductDto.getQuantity());
        product.setImageProductList(registerProductDto.getImageProductList());
    }
}
