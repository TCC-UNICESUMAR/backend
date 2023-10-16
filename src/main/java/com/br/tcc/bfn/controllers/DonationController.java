package com.br.tcc.bfn.controllers;

import com.br.tcc.bfn.dtos.*;
import com.br.tcc.bfn.exceptions.DonationException;
import com.br.tcc.bfn.facades.DonationFacade;
import com.br.tcc.bfn.services.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @PostMapping()
    public ResponseEntity<Response<DonationDto>> register(@RequestBody RegisterDonationDto request) {
        Response<DonationDto> dtoResponse = new Response<>();

        try{
            dtoResponse.setData(donationFacade.save(request));
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
            dtoResponse.setData(donationFacade.findById(id));
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.NOT_FOUND.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.internalServerError().body(dtoResponse);
        }

    }


    @Operation(summary = "Find All Donations By Zipcode on Application")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Find All Donations on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DonationException.class)) }),
            @ApiResponse(responseCode = "500", description = "Error Find All Donations on Application",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Response.class)) })})
    @GetMapping("/feed/{zipCode}")
    public Response<Page<DonationDto>> findAllByZipCode(Pageable pageable, @PathVariable String zipCode){
        Response<Page<DonationDto>> dtoResponse = new Response<>();
        try{
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            dtoResponse.setData(donationFacade.findAllByZipCode(pageable, zipCode));
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
            dtoResponse.setData(donationFacade.findDonationsByUserId(userId,pageable));
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
            dtoResponse.setData(donationFacade.update(id,request));
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
            dtoResponse.setData(donationFacade.findByCategory(category, pageable));
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

    @PutMapping("/approveDonation/{donationId}")
    public ResponseEntity<Response<Void>> approveDonation(@PathVariable Long donationId){
        Response<Void> dtoResponse = new Response<>();
        try{
            donationFacade.approvedDonationOder(donationId);
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
            donationFacade.saveDeliveredByDonor(donationId);
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<Response<Map<String,Long>>> findAll(@RequestParam(value = "status", defaultValue = "") String status,
                                                              @RequestParam(value = "year", defaultValue = "") Integer year){
        Response<Map<String,Long>> dtoResponse = new Response<>();
        try{
            dtoResponse.setData(donationFacade.findByAllByQuery(status, year));
            dtoResponse.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(dtoResponse);
        }catch (Exception e){
            dtoResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
            dtoResponse.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dtoResponse);
        }
    }

}
