package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.RegisterProductDto;
import com.br.tcc.bfn.models.Product;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductRequestPopulator implements Populator<Product, RegisterProductDto>{
    @Override
    public void populate(Product product, RegisterProductDto registerProductDto) {
        product.setActive(Boolean.TRUE);
        product.setName(registerProductDto.getName());
        product.setDescription(registerProductDto.getDescription());
        product.setQuantity(registerProductDto.getQuantity());
        product.getAddress().setCity(registerProductDto.getAddressDto().getCity());
        product.getAddress().setComplement(registerProductDto.getAddressDto().getComplement());
        product.getAddress().setUf(registerProductDto.getAddressDto().getUf());
        product.getAddress().setZipCode(registerProductDto.getAddressDto().getZipCode());
        product.getAddress().setStreetNumber(registerProductDto.getAddressDto().getStreetNumber());
        product.getAddress().setStreetName(registerProductDto.getAddressDto().getStreetName());
        product.getAddress().setUpdateAddressAt(new Date());
    }
}
