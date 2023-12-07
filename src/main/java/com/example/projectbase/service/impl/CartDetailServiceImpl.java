package com.example.projectbase.service.impl;


import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.domain.dto.response.CartTotalResponseDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.Cart;
import com.example.projectbase.domain.entity.CartDetail;
import com.example.projectbase.domain.entity.Customer;
import com.example.projectbase.domain.entity.Product;
import com.example.projectbase.exception.InsufficientStockException;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.repository.CartDetailRepository;
import com.example.projectbase.repository.CartRepository;
import com.example.projectbase.repository.CustomerRepository;
import com.example.projectbase.repository.ProductRepository;
import com.example.projectbase.service.CartDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {


    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public CommonResponseDto addProductToCart(int customerId, CartDetailDto cartDetailDto) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        Optional<Cart> cart = cartRepository.findById(customer.getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customer.getCart().getId())});
        }

        Product product = productRepository.findById(cartDetailDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(cartDetailDto.getProductId())}));

        List<ProductFromCartResponseDto> cartInfo = cartDetailRepository.getCartInfor(cart.get().getId());

        for (ProductFromCartResponseDto tmp : cartInfo) {
            if (tmp.getProductId() == cartDetailDto.getProductId()) {
                if (tmp.getQuantity() + cartDetailDto.getQuantity() > product.getQuantity()) {
                    throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
                }
                cartDetailRepository.updateCartInfor(cart.get().getId(), product.getProductId(), cartDetailDto.getQuantity() + tmp.getQuantity());
                return new CommonResponseDto(true, SuccessMessage.ADD_PRODUCT_TO_CART);
            }
        }
        if (cartDetailDto.getQuantity() > product.getQuantity()) {
            throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }
        CartDetail cartDetail = new CartDetail(product, cartDetailDto.getQuantity());
        cartDetail.setCart(cart.get());
        cartDetailRepository.save(cartDetail);

        return new CommonResponseDto(true, SuccessMessage.ADD_PRODUCT_TO_CART);
    }

    @Override
    public List<ProductFromCartResponseDto> getCartInfor(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        Optional<Cart> cart = cartRepository.findById(customer.getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customer.getCart().getId())});
        }
        return cartDetailRepository.getCartInfor(cart.get().getId());
    }

    @Override
    public CartTotalResponseDto getCartTotal(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));
        int total = cartDetailRepository.getTotalProductsInCart(customer.getCart().getId());
        return new CartTotalResponseDto(total);
    }

    @Override
    public CommonResponseDto updateCartInfor(int customerId, CartDetailDto cartDetailDto) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        Optional<Cart> cart = cartRepository.findById(customer.getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customer.getCart().getId())});
        }
        Product product = productRepository.findById(cartDetailDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(cartDetailDto.getProductId())}));


        if (cartDetailDto.getQuantity() > product.getQuantity()) {
            throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        cartDetailRepository.updateCartInfor(cart.get().getId(), product.getProductId(), cartDetailDto.getQuantity());

        return new CommonResponseDto(true, SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteProductFromCart(int customerId, int productId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Customer.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customerId)}));

        Optional<Cart> cart = cartRepository.findById(customer.getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(customer.getCart().getId())});
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));

        cartDetailRepository.deleteCartDetail(cart.get().getId(), productId);

        return new CommonResponseDto(true, SuccessMessage.DELETE);
    }


}
