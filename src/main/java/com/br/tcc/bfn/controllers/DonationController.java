package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.facades.DonationFacade;
import com.br.tcc.bfn.services.IProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.GsonBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/donation")
public class DonationController {

    @Autowired
    private IProductService productService;

    @Autowired
    private DonationFacade donationFacade;

    @Operation(summary = "Register new Donation on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Register new Donation on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Register new Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Response<DonationDto>> register(@ModelAttribute RegisterDonationRequest request) throws JsonProcessingException {
        Response<DonationDto> dtoResponse = new Response<>();

        try{
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            RegisterDonationDto donationDto = gson.fromJson(request.getBody(), RegisterDonationDto.class);
            dtoResponse.setBody(donationFacade.save(donationDto, request.getFiles()));
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Disable Donation on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Disable Donation on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductDto.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Disable Product on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> disableDonation(@PathVariable Long id){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.disable(id);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Find Donation By Id on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Donation By Id on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Donation By Id on Applicationn",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/{id}")
    public ResponseEntity<Response<DonationDto>> findById(@PathVariable Long id){
        Response<DonationDto> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findById(id));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }


    @Operation(summary = "Find All Donations By City on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find All Donations on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Donations on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/region/{city}")
    public Response<Page<DonationDto>> findAllByCity(Pageable pageable, @PathVariable String city){
        Response<Page<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findAllByZipCode(pageable, city));
            return dtoResponse;
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return dtoResponse;
        }
    }

    @Operation(summary = "Find Donations By UF on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Donations By UF on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Donations UF Id on Applicationn",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/region/uf/{uf}")
    public ResponseEntity<Response<Page<DonationDto>>> findByRegion(@PathVariable String uf, Pageable pageable){
        Response<Page<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findAllByUF(pageable, uf));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Find Donations By User on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Donations By User on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Donations User Id on Applicationn",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<Response<Page<DonationDto>>> findDonationsByUserId(@PathVariable Long userId, Pageable pageable){
        Response<Page<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findDonationsByUserId(userId,pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }
    }

    @Operation(summary = "Update Donation on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Update Donation on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Update Donation on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @PutMapping("/{id}")
    public ResponseEntity<Response<DonationDto>> updateDonation(@PathVariable Long id, @RequestBody RegisterDonationDto request){
        Response<DonationDto> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.update(id,request));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @Operation(summary = "Find Donations By Category on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find Product By Donations on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find Product Category on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/category/{category}")
    public ResponseEntity<Response<Page<DonationDto>>> getByCategory(@PathVariable String category, Pageable pageable){
        Response<Page<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findByCategory(category, pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PostMapping("/createOrderDonation/{donationId}")
    public ResponseEntity<Response<Void>> createOrderDonation(@PathVariable Long donationId, @RequestBody DonationOrderRegisterRequest request){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.createDonationOrder(donationId,request);
            dtoResponse.setStatusCode(HttpStatus.CREATED.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PostMapping("/ongApproveDonation")
    public ResponseEntity<Response<Void>> approveDonation(@RequestBody RequestOngApprovedDonation request){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.approvedDonationOder(request.getDonationOrderId(), request.getApproved());
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PutMapping("/deliveryByDonor/{donationId}")
    public ResponseEntity<Response<Void>> saveDelivered(@PathVariable Long donationId){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.saveDeliveredByDonor(donationId);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PutMapping("/finishedDonation/{donationId}")
    public ResponseEntity<Response<Void>> finishedDonation(@PathVariable Long donationId){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.finishDonationOrder(donationId);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @PostMapping("/sendDonorApproveDonation")
    public ResponseEntity<Response<Void>> sendDonorApproveDonation(@RequestBody RequestApproveDonationOrder request){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.sendDonorApprove(request);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonationsOrder")
    public ResponseEntity<Response<List<ResponseDashBoard>>> findAll(@RequestParam(value = "status", defaultValue = "") String status,
                                                              @RequestParam(value = "year", defaultValue = "") Integer year){
        Response<List<ResponseDashBoard>> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findAllDonationsOrderByQuery(status, year));
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonations")
    public ResponseEntity<Response<List<ResponseDashBoard>>> findAll(@RequestParam(value = "year", defaultValue = "") Integer year){
        Response<List<ResponseDashBoard>> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findAllDonationsByQuery(year));
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/city")
    public ResponseEntity<Response<List<DonationDto>>> findAllByCities(@RequestParam(value = "cities", defaultValue = "") List<String> cities){
        Response<List<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findAllByCities(cities));
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonationsToApprove")
    public ResponseEntity<Response<List<DonationOrderDto>>> findAllDonationsToApprove(){
        Response<List<DonationOrderDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findAllDonationsToApprove());
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findDonationOrderById/{donationOrderId}")
    public ResponseEntity<Response<DonationOrderDto>> findDonationOrderById(@PathVariable Long donationOrderId){
        Response<DonationOrderDto> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findDonationOrderById(donationOrderId));
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonationsToOngApprove")
    public ResponseEntity<Response<List<DonationOrderDto>>> findAllDonationsToOngApprove(){
        Response<List<DonationOrderDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setBody(donationFacade.findAllDonationsToOngApprove());
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonationsOrdersByUser")
    public ResponseEntity<Response<Page<DonationOrderDto>>> findAllDonationsOrdersByUser(Pageable pageable){
        Response<Page<DonationOrderDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findAllDonationOrdersByUser(pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAllDonationOrdersByIntermediary")
    public ResponseEntity<Response<Page<DonationOrderDto>>> findAllDonationOrdersByIntermediary(Pageable pageable){
        Response<Page<DonationOrderDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setBody(donationFacade.findAllDonationOrdersByIntermediary(pageable));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
