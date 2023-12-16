package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CustomerDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.entity.Customer;
import com.example.projectbase.domain.entity.Product;
import com.example.projectbase.domain.mapper.CustomerMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CustomerRepository;
import com.example.projectbase.repository.ProductRepository;
import com.example.projectbase.service.CustomerService;
import com.example.projectbase.util.PaginationUtil;
import com.example.projectbase.util.UploadFileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UploadFileUtil uploadFileUtil;
    private final ProductRepository productRepository;

    @Override
    public Customer createCustomer(CustomerDto customerDto) {

        Customer customer = customerMapper.toCustomer(customerDto);

        return customerRepository.save(customer);
    }

    @Override
    public CommonResponseDto updateCustomer(int id, CustomerDto customerDto) {
        Optional<Customer> customer = customerRepository.findById((id));
        if (customer.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(id)});
        }
        uploadFileUtil.destroyFileWithUrl(customer.get().getAvatar());
        customerRepository.updateCustomer(id, customerDto.getName(), customerDto.getPhonenumber(), customerDto.getAddress(), uploadFileUtil.uploadFile(customerDto.getAvatar()));
        return new CommonResponseDto(true, SuccessMessage.UPDATE);
    }

    @Override
    public PaginationResponseDto<Customer> getCustomers(PaginationFullRequestDto requestDto) {

        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.CUSTOMER);
        Page<Customer> page = customerRepository.getCustomers(pageable);

        PaginationResponseDto<Customer> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), requestDto.getSortBy(), requestDto.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public CommonResponseDto deleteCustomer(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        uploadFileUtil.destroyFileWithUrl(customer.getAvatar());
        customerRepository.delete(customer);
        return new CommonResponseDto(true, SuccessMessage.DELETE);
    }

    @Override
    public Customer getCustomerByUser(String userId) {
        Optional<Customer> customer = customerRepository.getCustomerByUserId(userId);
        if (customer.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{userId});
        }
        return customer.get();
    }

    @Override
    public Customer getById(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        return customer;
    }

    @Override
    public PaginationResponseDto<GetProductsResponseDto> getFavoriteProducts(int customerId, PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request);
        Page<GetProductsResponseDto> page = customerRepository.getFavoriteProducts(customerId, pageable);

        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public boolean checkFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        return customer.getFavoriteProducts().contains(product);
    }

    @Override
    public CommonResponseDto addFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        customer.getFavoriteProducts().add(product);
        customerRepository.save(customer);
        return new CommonResponseDto(SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto removeFavoriteProduct(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        Product product = productRepository.findByProductId(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
        customer.getFavoriteProducts().remove(product);
        customerRepository.save(customer);
        return new CommonResponseDto(SuccessMessage.DELETE);
    }

    @Override
    public int countCustomer() {
        return customerRepository.countCustomer();
    }

    @Override
    public String uploadImage(MultipartFile multipartFile) {
        return uploadFileUtil.uploadFile(multipartFile);
    }
}
