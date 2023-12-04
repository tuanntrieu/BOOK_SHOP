package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.constant.StatusConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.BillDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.*;
import com.example.projectbase.domain.mapper.BillMapper;
import com.example.projectbase.exception.InsufficientStockException;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.*;
import com.example.projectbase.service.BillService;
import com.example.projectbase.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;


    private final ProductRepository productRepository;

    private final BillRepository billRepository;

    private final BillDetailRepository billDetailRepository;

    private final CustomerRepository customerRepository;

    @Override
    public CommonResponseDto placeOrderFromCart(int customerId, PlaceOrderRequestDto requestDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        Optional<Cart> cart = cartRepository.findById(customer.getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customer.getCart().getId())});
        }

        customer.setName(requestDto.getNameCustomer());
        customer.setAddress(requestDto.getAddress());
        customer.setPhonenumber(requestDto.getPhonenumber());
        customerRepository.save(customer);

        Bill bill = new Bill();
        bill.setFeeShip(30000);
        bill.setStatus(StatusConstant.ORDERED);
        bill.setCustomer(customer);
        billRepository.save(bill);
        for (int productId : requestDto.getListProductId()) {
            Optional<ProductFromCartResponseDto> product = cartDetailRepository.getProductFromCart(cart.get().getId(), productId);
            if (product.isEmpty()) {
                throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_PRODUCT, new String[]{String.valueOf(productId)});
            }
            BillDetail billDetail = new BillDetail(productRepository.findById(productId).get(), bill, product.get().getQuantity());
            Product product1 = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
            productRepository.updateQuantity(productId, product1.getQuantity() - product.get().getQuantity());
            billDetailRepository.save(billDetail);
            cartDetailRepository.deleteCartDetail(cart.get().getId(), productId);
        }


        return new CommonResponseDto(true, SuccessMessage.ORDER);
    }

    @Override
    public CommonResponseDto buyNow(int customerId, BuyNowRequestDto requestDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        customer.setName(requestDto.getNameCustomer());
        customer.setAddress(requestDto.getAddress());
        customer.setPhonenumber(requestDto.getPhonenumber());
        customerRepository.save(customer);

        Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(requestDto.getProductId())}));

        if (requestDto.getQuantity() > product.getQuantity()) {
            throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }
        Bill bill = new Bill();
        bill.setFeeShip(30000);
        bill.setStatus(StatusConstant.ORDERED);
        bill.setCustomer(customer);
        billRepository.save(bill);

        productRepository.updateQuantity(product.getProductID(), product.getQuantity() - requestDto.getQuantity());
        BillDetail billDetail = new BillDetail(product, bill, product.getQuantity());
        billDetailRepository.save(billDetail);

        return new CommonResponseDto(true, SuccessMessage.ORDER);
    }

    @Override
    public CommonResponseDto cancelOrder(int billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        billRepository.updateStatus(billId, StatusConstant.CANCELLED);
        return new CommonResponseDto(true, SuccessMessage.CANCEL);
    }

    @Override
    public CommonResponseDto buyAgain(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        billRepository.updateStatus(billId, StatusConstant.ORDERED);
        return new CommonResponseDto(true, SuccessMessage.ORDER);

    }

    @Override
    public PaginationResponseDto<Bill> getBills(int customerId, PaginationFullRequestDto request) {
        Pageable pageable = PaginationUtil.buildPageable(request, SortByDataConstant.BILL);

        Page<Bill> page = billRepository.getBills(customerId, pageable);

        PaginationResponseDto<Bill> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());

        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), request.getSortBy(), request.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;

    }

    @Override
    public Bill getBillInfor(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        return bill;
    }


}
