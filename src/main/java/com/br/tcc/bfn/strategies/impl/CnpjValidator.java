package com.br.tcc.bfn.strategies.impl;

import com.br.tcc.bfn.strategies.ValidatorDocumentStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class CnpjValidator implements ValidatorDocumentStrategy {

    private final static Logger LOGGER = LoggerFactory.getLogger(CnpjValidator.class);
    @Override
    public Boolean validateDocument(String document) {
        document.replaceAll("[^0-9]", "");
        if (document == null){
            LOGGER.error("CNPJ IS NULL");
            return false;
        }

        if (document.equals("00000000000000") || document.equals("11111111111111") ||
                document.equals("22222222222222") || document.equals("33333333333333") ||
                document.equals("44444444444444") || document.equals("55555555555555") ||
                document.equals("66666666666666") || document.equals("77777777777777") ||
                document.equals("88888888888888") || document.equals("99999999999999") ||
                (document.length() != 14))
            return (false);

        char dig13, dig14;
        int sm, i, r, num, peso;

        try {
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                num = (int) (document.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig13 = '0';
            else dig13 = (char) ((11 - r) + 48);

            sm = 0;
            peso = 2;
            for (i = 12; i >= 0; i--) {
                num = (int) (document.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10)
                    peso = 2;
            }

            r = sm % 11;
            if ((r == 0) || (r == 1))
                dig14 = '0';
            else dig14 = (char) ((11 - r) + 48);

            if ((dig13 == document.charAt(12)) && (dig14 == document.charAt(13)))
                return (true);
            else return (false);
        } catch (InputMismatchException erro) {
            return (false);
        }
    }
}
