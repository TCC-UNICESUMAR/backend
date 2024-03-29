package com.br.tcc.bfn.services.impl;

import com.br.tcc.bfn.builder.AddressBuilder;
import com.br.tcc.bfn.builder.DonationBuilder;
import com.br.tcc.bfn.dtos.DonationOrderRegisterRequest;
import com.br.tcc.bfn.dtos.RegisterDonationDto;
import com.br.tcc.bfn.dtos.RequestApproveDonationOrder;
import com.br.tcc.bfn.dtos.ResponseDashBoard;
import com.br.tcc.bfn.enums.DonationOrderStatusEnum;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.exceptions.UserException;
import com.br.tcc.bfn.models.Address;
import com.br.tcc.bfn.models.Donation;
import com.br.tcc.bfn.models.DonationOrder;
import com.br.tcc.bfn.models.DonationStatus;
import com.br.tcc.bfn.repositories.*;
import com.br.tcc.bfn.services.*;
import com.br.tcc.bfn.utils.BfnConstants;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private S3Service s3Service;
    @Autowired
    private Environment environment;

    private final static Logger LOGGER = LoggerFactory.getLogger(DonationServiceImpl.class);

    @Override
    public Donation save(RegisterDonationDto request, MultipartFile[] files) throws Exception {

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
                    .product(productService.register(request, files))
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
            Donation donation = donationRepository.findById(id).orElseThrow(() -> new DonationException(BfnConstants.DONATION_NOT_FOUND));
            return donation;
        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public List<ResponseDashBoard> findAllDonationsOrderByQuery(String status, Integer year) throws DonationException {
        try {

            DonationOrderStatusEnum statusEnum = status.equals(StringUtils.EMPTY) ? DonationOrderStatusEnum.SUCCESS : DonationOrderStatusEnum.valueOf(status);
            year = year == null ? new LocalDateTime().getYear() : year;
            List<DonationOrder> donations = donationOrderRepository.findAllDonationOrderByQuery(statusEnum, year);
            List<ResponseDashBoard> listResp = new ArrayList<>();
            listResp.add(new ResponseDashBoard(Month.JANUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 1).count()));
            listResp.add(new ResponseDashBoard(Month.FEBRUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 2).count()));
            listResp.add(new ResponseDashBoard(Month.MARCH.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 3).count()));
            listResp.add(new ResponseDashBoard(Month.APRIL.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 4).count()));
            listResp.add(new ResponseDashBoard(Month.MAY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 5).count()));
            listResp.add(new ResponseDashBoard(Month.JUNE.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 6).count()));
            listResp.add(new ResponseDashBoard(Month.JULY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 7).count()));
            listResp.add(new ResponseDashBoard(Month.AUGUST.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 8).count()));
            listResp.add(new ResponseDashBoard(Month.SEPTEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 9).count()));
            listResp.add(new ResponseDashBoard(Month.OCTOBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 10).count()));
            listResp.add(new ResponseDashBoard(Month.NOVEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 11).count()));
            listResp.add(new ResponseDashBoard(Month.DECEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 12).count()));
            return listResp;

        } catch (Exception exc) {
            LOGGER.error(exc.getMessage());
            throw new DonationException(exc.getMessage());
        }
    }

    @Override
    public List<ResponseDashBoard> findAllDonationsByQuery(Integer year) throws DonationException {
        try {

            year = year == null ? new LocalDateTime().getYear() : year;
            List<Donation> donations = donationRepository.findAllDonationByQuery(year);
            List<ResponseDashBoard> listResp = new ArrayList<>();

            listResp.add(new ResponseDashBoard(Month.JANUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 1).count()));
            listResp.add(new ResponseDashBoard(Month.FEBRUARY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 2).count()));
            listResp.add(new ResponseDashBoard(Month.MARCH.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 3).count()));
            listResp.add(new ResponseDashBoard(Month.APRIL.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 4).count()));
            listResp.add(new ResponseDashBoard(Month.MAY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 5).count()));
            listResp.add(new ResponseDashBoard(Month.JUNE.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 6).count()));
            listResp.add(new ResponseDashBoard(Month.JULY.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 7).count()));
            listResp.add(new ResponseDashBoard(Month.AUGUST.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 8).count()));
            listResp.add(new ResponseDashBoard(Month.SEPTEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 9).count()));
            listResp.add(new ResponseDashBoard(Month.OCTOBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 10).count()));
            listResp.add(new ResponseDashBoard(Month.NOVEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 11).count()));
            listResp.add(new ResponseDashBoard(Month.DECEMBER.name(), donations.stream().filter(x -> x.getCreatedAt().getMonth() == 12).count()));
            return listResp;

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
    public List<DonationOrder> findAllDonationsToApprove() throws DonationException {
        try {
            List<DonationOrder> donationOrders = donationOrderRepository.findAllDonationOrdersToApproveByDonor(DonationOrderStatusEnum.WAITING_DONOR_APPROVED, userService.findAuth().getId());
            return donationOrders;
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DonationOrder> findAllDonationsToOngApprove() throws DonationException {
        try {
            List<DonationOrder> donationOrders = donationOrderRepository.findAllDonationOrdersToOngApprove(DonationOrderStatusEnum.WAITING_ONG_APPROVED, userService.findAuth().getId());
            return donationOrders;
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Donation> findAllByCities(List<String> cities) {
        List<Donation> donations = donationRepository.findAllByCities(cities);
        return donations;
    }

    @Override
    public DonationOrder findDonationOrderById(Long id) throws Exception {
        return donationOrderRepository.findById(id).orElseThrow(() -> new Exception("Not Found"));
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
    public Page<Donation> findAllByZipCode(Pageable pageable, String city) {
        Page<Donation> donations = donationRepository.searchAllByZipCode(pageable, city);
        return donations;
    }

    @Override
    public Page<DonationOrder> findAllDonationOrdersByUser(Pageable pageable) throws UserException {
        Page<DonationOrder> donations = donationOrderRepository.findAllDonationOrdersByUser(pageable, userService.findAuth().getId());
        return donations;
    }
    @Override
    public Page<DonationOrder> findAllDonationOrdersByIntermediary(Pageable pageable) throws UserException {
        Page<DonationOrder> donations = donationOrderRepository.findAllDonationOrdersByIntermediary(pageable, userService.findAuth().getId());
        return donations;
    }

    @Override
    public Page<DonationOrder> findAllDonationOrdersByDonor(Pageable pageable) throws UserException {
        Page<DonationOrder> donations = donationOrderRepository.findAllDonationOrdersByDonor(pageable, userService.findAuth().getId());
        return donations;
    }

    @Override
    public Page<Donation> findAllByUF(Pageable pageable, String uf) {
        Page<Donation> donations = donationRepository.searchAllByUf(pageable, uf);
        return donations;
    }

    @Override
    public void finishDonationOrder(Long donationId) throws DonationException {
        final DonationOrder donation = donationOrderRepository.findById(donationId).orElseThrow(() -> new DonationException("Donation Order Not Found"));
        DonationStatus donationStatus = donation.getDonationStatus();
        donationStatus.setStatus(DonationOrderStatusEnum.SUCCESS);
        donationStatusRepository.save(donationStatus);

//        sendNotification.send(CODE_BRAZIL.concat(donation.getDonor().getPhone()),
//                templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR_WITH_CONGRATULATION_BY_ACTION), donation.getId());
    }

    @Override
    public void createDonationOrder(Long id, DonationOrderRegisterRequest request) throws Exception {
        try {
            final Donation donation = findById(id);

            if (donation.getReserved() == Boolean.TRUE) {
                throw new DonationException("Donation is reserved by other user, try other donate!");
            }

            DonationStatus donationStatus = new DonationStatus();
            donationStatus.setAvailableForPickup(false);
            donationStatus.setCreatedAt(new Date());
            donationStatus.setUpdatedAt(new Date());
            donationStatus.setWaitingOngApprove(true);
            donationStatus.setStatus(DonationOrderStatusEnum.WAITING_DONOR_APPROVED);

            DonationOrder donationOrder = new DonationOrder();
            donationOrder.setDonation(donation);
            donationOrder.setDonationStatus(donationStatus);
            donationOrder.setDonor(donation.getUserBy());
            donationOrder.setReceived(userService.findAuth());
            donationOrder.setCreatedAt(new Date());
            donationOrder.setUpdatedAt(new Date());
            donationOrder.setReason(request.getReason());


            if (donationOrder.getDonor() == donationOrder.getReceived()) {
                throw new DonationException("Donor and Received are equals!");
            }

            if (userService.findAuth().getRoles().stream().findFirst().get().getRoleName().equals(BfnConstants.ROLE_DEFAULT_USER)) {
                donationOrder.setNeedAnIntermediary(Boolean.TRUE);
            } else {
                donationOrder.setNeedAnIntermediary(Boolean.FALSE);
            }

            donationStatusRepository.save(donationStatus);
            donationOrderRepository.save(donationOrder);
            reserveDonation(id);
            userService.saveDonationOrderToDonorApprove(donationOrder);

//            sendNotification.send(CODE_BRAZIL.concat(userService.findAuth().getPhone()), templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_CREATE_ORDER_DONATION), donationOrder.getId());
//            sendNotification.send(CODE_BRAZIL.concat(donation.getUserBy().getPhone()), templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR_TO_APPROVE_ORDER_DONATION), donationOrder.getId());

        } catch (Exception exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }
    }

    @Override
    public void approvedDonationOder(Long id, Boolean status) throws DonationException, UserException {
        try {
            final DonationOrder donationOrder = donationOrderRepository.findById(id).orElseThrow(() -> new DonationException("Donation Not Found"));
            DonationStatus donationStatus = donationOrder.getDonationStatus();
            donationStatus.setUpdatedAt(new Date());
            if(status) {
                donationStatus.setAvailableForPickup(false);
                donationStatus.setWaitingOngApprove(false);
                donationStatus.setApprovedBy(userService.findAuth());
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_DONOR_SEND);
                donationStatus.setApproved(true);
            }else{
                donationStatus.setAvailableForPickup(false);
                donationStatus.setWaitingOngApprove(false);
                donationStatus.setApprovedBy(userService.findAuth());
                donationStatus.setStatus(DonationOrderStatusEnum.CANCELED);
                donationStatus.setApproved(false);
                donationOrder.getDonation().setReserved(false);
                donationRepository.save(donationOrder.getDonation());
                donationStatusRepository.save(donationStatus);
//                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()),
//                        templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_WITH_STATUS_NOT_APPROVED_BY_ONG), donationOrder.getId());
                return;
            }

            donationStatusRepository.save(donationStatus);

//            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonation().getUserBy().getPhone()),
//                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR), donationOrder.getId());
//
//            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonation().getUserBy().getPhone()),
//                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_DONOR), donationOrder.getId());

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

            if (donationOrder.getIntermediary() != null) {
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_RECEIVED_PICKUP);
//                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()),
//                        templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_TO_GET_DONATION), donationOrder.getId());
            } else {
                donationStatus.setStatus(DonationOrderStatusEnum.SUCCESS);
//                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonor().getPhone()),
//                        templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR_WITH_CONGRATULATION_BY_ACTION), donationOrder.getId());
            }

            donationStatusRepository.save(donationStatus);

        } catch (DonationException exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }
    }

    @Override
    public void saveApproveByDonor(RequestApproveDonationOrder request) throws DonationException, UserException {
        try {
            final DonationOrder donationOrder = donationOrderRepository.findById(request.getDonationOrderId()).orElseThrow(() -> new DonationException("Donation Not Found"));

            DonationStatus donationStatus = donationOrder.getDonationStatus();
            donationStatus.setUpdatedAt(new Date());

            if (BooleanUtils.isFalse(request.getApproved())) {
                donationStatus.setStatus(DonationOrderStatusEnum.CANCELED);
                donationOrder.getDonation().setReserved(false);
                donationRepository.save(donationOrder.getDonation());
                donationOrderRepository.save(donationOrder);
                donationStatusRepository.save(donationStatus);
//                sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()),
//                        templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_WITH_STATUS_NOT_APPROVED_BY_DONOR), donationOrder.getId());
                return;
            }


            if (BfnConstants.ROLE_ONG.equals(donationOrder.getReceived().getRoles().stream().findFirst().get().getRoleName())) {
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_DONOR_SEND);
            } else {
                donationOrder.setIntermediary(userService.findById(request.getIdIntermediary()));
                donationStatus.setStatus(DonationOrderStatusEnum.WAITING_ONG_APPROVED);
            }

            donationOrderRepository.save(donationOrder);
            donationStatusRepository.save(donationStatus);

//            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getReceived().getPhone()),
//                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_RECEIVED_WITH_STATUS_WAITING_ONG_APPROVED), donationOrder.getId());
//
//            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getIntermediary().getPhone()),
//                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_INTERMEDIARY), donationOrder.getId());

        } catch (DonationException exc) {
            throw new DonationException("Error to update order donation -> " + exc.getMessage());
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

//            sendNotification.send(CODE_BRAZIL.concat(donationOrder.getDonor().getPhone()),
//                    templateSmsRepository.findByMessageTemplate(BfnConstants.TEMPLATE_NOTIFICATE_DONOR_WITH_CONGRATULATION_BY_ACTION), donationOrder.getId());
        } catch (DonationException exc) {
            throw new DonationException("Error to create order donation -> " + exc.getMessage());
        }

    }
}
