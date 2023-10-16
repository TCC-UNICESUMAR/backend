package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.DonationBuilder;
import com.br.tcc.bfn.dtos.DonationOrderRegisterRequest;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.enums.DonationOrderStatusEnum;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.DonationOrder;
import com.br.tcc.bfn.models.DonationStatus;
import com.br.tcc.bfn.repositories.*;
import com.br.tcc.bfn.services.IDonationService;
import com.br.tcc.bfn.services.IProductService;
import com.br.tcc.bfn.services.IUserService;
import com.br.tcc.bfn.services.SendNotification;
import com.br.tcc.bfn.utils.BfnConstants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.*;

@Service
public class DonationServiceImpl implements IDonationService {

    public static final String CODE_BRAZIL = "+55";
    @Autowired
    private DonationRepository donationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private IUserService userService;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private DonationOrderRepository donationOrderRepository;
    @Autowired
    private DonationStatusRepository donationStatusRepository;
    @Autowired
    private SendNotification sendNotification;
    @Autowired
    private TemplateSmsRepository templateSmsRepository;


    private final static Logger LOGGER = LoggerFactory.getLogger(DonationServiceImpl.class);

    @Override
    public Donation save(RegisterDonationDto request) throws Exception {

        try {

            final Address address = AddressBuilder.builder()
                    .city(cityRepository.findByCityName(request.getAddress().getCity()))
                    .complement(request.getAddress().getComplement())
                    .streetNumber(request.getAddress().getStreetNumber())
                    .zipCode(request.getAddress().getZipCode())
                    .streetName(request.getAddress().getStreetName())
                    .state(stateRepository.findStateByUf(request.getAddress().getUf()))
                    .create()
                    .update()
                    .build();

            Donation donation = DonationBuilder.builder()
                    .address(addressRepository.save(address))
                    .created()
                    .update()
                    .reserved()
                    .product(productService.register(request))
                    .userBy(userService.findAuth())
                    .build();

            donationRepository.save(donation);

            return donation;
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException("ERROR ON SAVE DONATION -> " + exc.getMessage());
        }
    }

    @Override
    public Donation findById(Long id) throws DonationException {
        try {
            return donationRepository.findById(id).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
        } catch (DonationException exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    public Map<String, Long> findByAllByQuery(String status, Integer year) throws DonationException {
        try {

            DonationOrderStatusEnum statusEnum = status.equals(StringUtils.EMPTY) ? DonationOrderStatusEnum.SUCCESS : DonationOrderStatusEnum.valueOf(status);
            year = year == null ? new LocalDateTime().getYear() : year;
            Map<String, Long> donationsCount = new HashMap<>();
            List<Donation> donations = donationOrderRepository.findAllDonationByQuery(statusEnum,year);
            donationsCount.put(Month.JANUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 1).count());
            donationsCount.put(Month.FEBRUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 2).count());
            donationsCount.put(Month.MARCH.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 3).count());
            donationsCount.put(Month.APRIL.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 4).count());
            donationsCount.put(Month.MAY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 5).count());
            donationsCount.put(Month.JUNE.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 6).count());
            donationsCount.put(Month.JULY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 7).count());
            donationsCount.put(Month.AUGUST.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 8).count());
            donationsCount.put(Month.SEPTEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 9).count());
            donationsCount.put(Month.OCTOBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 10).count());
            donationsCount.put(Month.NOVEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 11).count());
            donationsCount.put(Month.DECEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 12).count());
            return donationsCount;

        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Donation update(Long donationId, RegisterDonationDto registerDonationDto) throws Exception {
        try {
            Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));

            donation.setUpdatedAt(new Date());
            donation.getAddress().setUpdatedAt(new Date());
            donation.getAddress().setComplement(registerDonationDto.getAddress().getComplement());
            donation.getAddress().setStreetNumber(registerDonationDto.getAddress().getStreetNumber());
            donation.getAddress().setStreetName(registerDonationDto.getAddress().getStreetName());
            donation.getAddress().setZipCode(registerDonationDto.getAddress().getZipCode());
            donation.getAddress().setCity(cityRepository.findByCityName(registerDonationDto.getAddress().getCity()));
            donation.getAddress().setState(stateRepository.findStateByUf(registerDonationDto.getAddress().getUf()));

            addressRepository.save(donation.getAddress());

            productService.update(donation.getProduct().getId(), registerDonationDto);

            donationRepository.save(donation);

            return donation;
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }

    }

    @Override
    public void disable(Long donationId) throws DonationException {
        try {
            Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
            donation.setDeletedAt(new Date());
            donationRepository.save(donation);
        } catch (DonationException exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public void reserveDonation(Long donationId) throws DonationException {
        try {
            Donation donation = donationRepository.findById(donationId).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
            donation.setReserved(true);
            donationRepository.save(donation);
        } catch (DonationException exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findDonationsByCategory(String category, Pageable pageable) throws DonationException {
        try {

            if (category == null) {
                throw new DonationException("CATEGORY CANNOT BE NULL!");
            }

            Page<Donation> donations = donationRepository.searchAllByCategory(category, pageable);

            if (donations == null) {
                return null;
            }

            return donations;

        } catch (DonationException exc) {
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findDonationsByUserId(Long userId, Pageable pageable) throws DonationException {
        try {

            if (userId == null) {
                throw new DonationException("USERID CANNOT BE NULL!");
            }

            Page<Donation> donations = donationRepository.searchAllByUserId(userId, pageable);

            if (donations == null) {
                throw new DonationException(BfnConstants.DONATION_NOT_FOUND);
            }

            return donations;

        } catch (DonationException exc) {
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public Page<Donation> findAllByZipCode(Pageable pageable, String zipCode) {
        Page<Donation> donations = donationRepository.searchAllByZipCode(pageable, zipCode);
        return donations;
    }

    @Override
    public void createDonationOrder(Long id, DonationOrderRegisterRequest request) throws Exception {
        try {
            final Donation donation = findById(id);

            reserveDonation(id);
            if (donation.getReserved() == Boolean.TRUE) {
                throw new DonationException("Donation is reserved by other user, try other donate!");
            }

            DonationStatus donationStatus = new DonationStatus();
            donationStatus.setAvailableForPickup(false);
            donationStatus.setCreatedAt(new Date());
            donationStatus.setUpdatedAt(new Date());
            donationStatus.setWaitingOngApprove(true);

            DonationOrder donationOrder = new DonationOrder();
            donationOrder.setDonation(donation);
            donationOrder.setDonationStatus(donationStatus);
            donationOrder.setDonor(donation.getUserBy());
            donationOrder.setReceived(userService.findAuth());
            donationOrder.setCreatedAt(new Date());
            donationOrder.setUpdatedAt(new Date());


            if (donationOrder.getDonor() == donationOrder.getReceived()) {
                throw new DonationException("Donor and Received are equals!");
            }

            final Optional<Boolean> roleUser = userService.findAuth().getRoles().stream().map(x -> BfnConstants.ROLE_DEFAULT_USER.equalsIgnoreCase(x.getRoleName())).findFirst();

            if (roleUser.isPresent() && roleUser.get()) {
                if (request.getIntermediary() == null) {
                    throw new DonationException("Intermediary can not be null!");
                }
                donationOrder.setIntermediary(userService.findById(request.getIntermediary()));
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_ONG_APPROVED);
            }else{
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_DONOR_SEND);
                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()), templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR_ONG), donationOrder.getId());
            }

            donationStatusRepository.save(donationStatus);
            donationOrderRepository.save(donationOrder);


            sendNotification.send(CODE_BRAZIL.concat(userService.findAuth().getPhone()), templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_CREATE_ORDER_DONATION), donationOrder.getId());

            if (Objects.nonNull(donationOrder.getIntermediary())) {
                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getIntermediary().getPhone()), templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_INTERMEDIARY), donationOrder.getId());
            }
        } catch (Exception exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }

    }

    @Override
    public void approvedDonationOder(Long id) throws DonationException, UserException {
        try {
            final DonationOrder donationOrder = donationOrderRepository.findById(id).orElseThrow(() -> new DonationException("DonationOrder Not Found!"));

            DonationStatus donationStatus = donationOrder.getDonationStatus();
            donationStatus.setAvailableForPickup(true);
            donationStatus.setUpdatedAt(new Date());
            donationStatus.setWaitingOngApprove(false);
            donationStatus.setApprovedBy(userService.findAuth());
            donationStatus.setStatus(DonationOrderStatusEnum.WAITING_DONOR_SEND);

            donationStatusRepository.save(donationStatus);

            donationOrderRepository.save(donationOrder);

            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonation().getUserBy().getPhone()),
                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR), donationOrder.getId());

            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonation().getUserBy().getPhone()),
                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_DONOR), donationOrder.getId());

        } catch (DonationException exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }

    }

    @Override
    public void saveDeliveredByDonor(Long id) throws DonationException {
        try {
            final DonationOrder donationOrder = donationOrderRepository.findById(id).orElseThrow(() -> new DonationException("DonationOrder Not Found!"));

            DonationStatus donationStatus = donationOrder.getDonationStatus();
            donationStatus.setUpdatedAt(new Date());
            if(donationOrder.getIntermediary() != null){
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_RECEIVED_PICKUP);
            }else{
                donationStatus.setStatus(DonationOrderStatusEnum.SUCCESS);
            }

            donationStatusRepository.save(donationStatus);

        } catch (DonationException exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }

    }

    @Override
    public void finishedDonation(Long id) throws DonationException {
        try {
            final DonationOrder donationOrder = donationOrderRepository.findById(id).orElseThrow(() -> new DonationException("DonationOrder Not Found!"));

            DonationStatus donationStatus = donationOrder.getDonationStatus();
            donationStatus.setAvailableForPickup(false);
            donationStatus.setUpdatedAt(new Date());
            donationStatus.setWaitingOngApprove(false);
            donationStatus.setStatus(DonationOrderStatusEnum.SUCCESS);

            donationStatusRepository.save(donationStatus);

            donationOrderRepository.save(donationOrder);

            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()),
                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_TO_GET_DONATION), donationOrder.getId());

        } catch (DonationException exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }

    }
}
