package com.br.tcc.bfn.populators;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface Populator<SOURCE, TARGET>{

    void populate(SOURCE source, TARGET target);

}