package com.example.projectbase.service.impl;

import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SortByDataConstant;
import com.example.projectbase.constant.StatusConstant;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.common.DataMailDto;
import com.example.projectbase.domain.dto.pagination.PaginationFullRequestDto;
import com.example.projectbase.domain.dto.pagination.PaginationResponseDto;
import com.example.projectbase.domain.dto.pagination.PagingMeta;
import com.example.projectbase.domain.dto.request.BuyNowRequestDto;
import com.example.projectbase.domain.dto.request.PlaceOrderRequestDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.*;
import com.example.projectbase.exception.InsufficientStockException;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.*;
import com.example.projectbase.service.BillService;
import com.example.projectbase.util.ExcelExportUtil;
import com.example.projectbase.util.PaginationUtil;
import com.example.projectbase.util.SendMailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.*;


@Service
@RequiredArgsConstructor
public class BillServiceImpl implements BillService {
    private final UserRepository userRepository;

    private final CartRepository cartRepository;

    private final CartDetailRepository cartDetailRepository;

    private final SendMailUtil sendMailUtil;

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
        bill.setStatus(StatusConstant.TO_PAY);
        bill.setCustomer(customer);
        billRepository.save(bill);
        int total = bill.getFeeShip();
        for (int productId : requestDto.getListProductId()) {
            Optional<ProductFromCartResponseDto> product = cartDetailRepository.getProductFromCart(cart.get().getId(), productId);
            if (product.isEmpty()) {
                throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_PRODUCT, new String[]{String.valueOf(productId)});
            }
            BillDetail billDetail = new BillDetail(productRepository.findById(productId).get(), bill, product.get().getQuantity());
            Product product1 = productRepository.findById(productId)
                    .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));
            total += (product.get().getPrice() - product.get().getDiscount() * product.get().getPrice() / 100) * billDetail.getQuantity();
            productRepository.updateQuantity(productId, product1.getQuantity() - product.get().getQuantity(), product1.getSelled() + product.get().getQuantity());
            billDetailRepository.save(billDetail);
            cartDetailRepository.deleteCartDetail(cart.get().getId(), productId);
        }
        bill.setTotal(total);
        billRepository.save(bill);


        return new CommonResponseDto(true, SuccessMessage.TO_PAY);
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
        bill.setStatus(StatusConstant.TO_PAY);
        bill.setCustomer(customer);
        billRepository.save(bill);
        int total = bill.getFeeShip();

        productRepository.updateQuantity(product.getProductId(), product.getQuantity() - requestDto.getQuantity(), product.getSelled() + requestDto.getQuantity());
        BillDetail billDetail = new BillDetail(product, bill, requestDto.getQuantity());
        billDetailRepository.save(billDetail);
        total += (product.getPrice() - product.getDiscount() * product.getPrice() / 100) * requestDto.getQuantity();

        bill.setTotal(total);
        billRepository.save(bill);
        return new CommonResponseDto(true, SuccessMessage.TO_PAY);
    }

    @Override
    public CommonResponseDto cancelOrder(int customerId, int billId) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        if (bill.getStatus().equals(StatusConstant.TO_PAY)) {
            billRepository.updateStatus(customerId, billId, StatusConstant.CANCELLED);
            for (BillDetail bt : bill.getBillDetail()) {
                productRepository.updateQuantity(bt.getProduct().getProductId(), bt.getProduct().getQuantity() + bt.getQuantity(), bt.getProduct().getSelled() - bt.getQuantity());
            }
            return new CommonResponseDto(true, SuccessMessage.CANCEL);
        } else {
            return new CommonResponseDto(false, ErrorMessage.Bill.NOT_ALLOW_TO_CANCEL);
        }
    }

    @Override
    public CommonResponseDto buyAgain(int customerId, int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        billRepository.updateStatus(customerId, billId, StatusConstant.TO_PAY);
        for (BillDetail bt : bill.getBillDetail()) {
            productRepository.updateQuantity(bt.getProduct().getProductId(), bt.getProduct().getQuantity() - bt.getQuantity(), bt.getProduct().getSelled() + bt.getQuantity());
        }
        return new CommonResponseDto(true, SuccessMessage.TO_PAY);

    }

    @Override
    public CommonResponseDto comfirmOrder(int billId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        bill.setStatus(StatusConstant.ORDERED);
        billRepository.save(bill);
        DataMailDto mailDto = new DataMailDto();
        mailDto.setTo(bill.getCustomer().getUser().getEmail());
        mailDto.setSubject("Đăng ký thành công");
        Map<String, Object> properties = new HashMap<>();
        properties.put("billId", bill.getCustomer().getUser().getUsername());

        mailDto.setProperties(properties);

        try {
            sendMailUtil.sendEmailWithHTML(mailDto, "OrderSuccessfully.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public int countBill() {
        return billRepository.countBill();
    }

    @Override
    public long getRevenue() {
        return billRepository.getRevenue();
    }

    @Override
    public List<Integer> getCoutBillByStatus() {
        List<Integer> count = new ArrayList<>();
        count.add(billRepository.getCountBillByStatus("Chờ xử lý"));
        count.add(billRepository.getCountBillByStatus("Đang giao hàng"));
        count.add(billRepository.getCountBillByStatus("Đặt hàng thành công"));
        count.add(billRepository.getCountBillByStatus("Đã hủy"));
        count.add(billRepository.getCountBillByStatus("Đã giao"));
        return count;
    }

    @Override
    public List<Bill> getBillsToPay() {
        return billRepository.getBillsToPay();
    }

    @Override
    public PaginationResponseDto getAllBills(PaginationFullRequestDto requestDto, String status) {
        Pageable pageable = PaginationUtil.buildPageable(requestDto, SortByDataConstant.BILL);
        Page<GetProductsResponseDto> page;
        String statusConstant = getStatusConstant(status);
        if (status.equals("")) {
            page = billRepository.getAllBill(pageable);
        } else {
            page = billRepository.getBillsByStatus(statusConstant, pageable);
        }
        PaginationResponseDto<GetProductsResponseDto> responseDto = new PaginationResponseDto<>();
        responseDto.setItems(page.getContent());
        PagingMeta pagingMeta = new PagingMeta(page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize(), requestDto.getSortBy(), requestDto.getIsAscending().toString());
        responseDto.setMeta(pagingMeta);
        return responseDto;
    }

    @Override
    public CommonResponseDto getsBillSatistics(HttpServletResponse response, Date timeStart, Date timeEnd) throws IOException {

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment;filename=BillStatistc.xlsx";
        response.setHeader(headerKey, headerValue);

        Timestamp timestamp1 = new Timestamp(timeStart.getTime());
        Timestamp timestamp2 = new Timestamp(timeEnd.getTime());

        List<Bill> bills = billRepository.getBillStatistics(timestamp1.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), timestamp2.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
        ExcelExportUtil excelExportUtil = new ExcelExportUtil(bills);
        excelExportUtil.exportDataToExcel(response);
        return new CommonResponseDto(true, SuccessMessage.CREATE);
    }

    @Override
    public CommonResponseDto updateOrderStatus(int billId, String status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Bill.ERR_NOT_FOUND_ID, new String[]{String.valueOf(billId)}));
        String newStatus = getStatusConstant(status);
        bill.setStatus(newStatus);
        billRepository.save(bill);
        return new CommonResponseDto(SuccessMessage.UPDATE);
    }

    private String getStatusConstant(String status) {
        switch (status) {
            case "to_pay": {
                return StatusConstant.TO_PAY;
            }
            case "to_receive": {
                return StatusConstant.TO_RECEIVE;
            }
            case "ordered": {
                return StatusConstant.ORDERED;
            }
            case "completed": {
                return StatusConstant.COMPLETED;
            }
            case "canceled": {
                return StatusConstant.CANCELLED;
            }
            default: {
                return StatusConstant.ORDERED;
            }
        }
    }
}
