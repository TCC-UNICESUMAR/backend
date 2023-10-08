package com.br.tcc.bfn.populators;

import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.models.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductRequestPopulator implements Populator<Product, RegisterDonationDto>{
    @Override
    public void populate(Product product, RegisterDonationDto registerDonationDto) {
        product.setActive(Boolean.TRUE);
        product.setName(registerDonationDto.getName());
        product.setDescription(registerDonationDto.getDescription());
        product.setQuantity(registerDonationDto.getQuantity());
    }
}
