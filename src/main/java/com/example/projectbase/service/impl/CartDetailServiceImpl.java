package com.example.projectbase.service.impl;


import com.example.projectbase.constant.ErrorMessage;
import com.example.projectbase.constant.SuccessMessage;
import com.example.projectbase.domain.dto.CartDetailDto;
import com.example.projectbase.domain.dto.response.CommonResponseDto;

import com.example.projectbase.domain.dto.response.GetProductsResponseDto;
import com.example.projectbase.domain.dto.response.ProductFromCartResponseDto;
import com.example.projectbase.domain.entity.*;
import com.example.projectbase.domain.mapper.CartDetailMapper;
import com.example.projectbase.exception.NotFoundException;
import com.example.projectbase.exception.InsufficientStockException;
import com.example.projectbase.repository.*;
import com.example.projectbase.service.CartDetailService;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class CartDetailServiceImpl implements CartDetailService {


    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ProductRepository productRepository;

    @Override
    public CommonResponseDto addProductToCart(String userId, CartDetailDto cartDetailDto) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
        }

        Optional<Cart> cart = cartRepository.findById(user.get().getCustomer().getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(user.get().getCustomer().getCart().getId())});
        }

        Product product = productRepository.findById(cartDetailDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(cartDetailDto.getProductId())}));

        List<ProductFromCartResponseDto> cartInfo=cartDetailRepository.getCartInfor(cart.get().getId());

        for(ProductFromCartResponseDto tmp: cartInfo){
            if(tmp.getProductId()==cartDetailDto.getProductId()){
                if(tmp.getQuantity()+cartDetailDto.getQuantity()>product.getQuantity()){
                    throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
                }
                cartDetailRepository.updateCartInfor(cart.get().getId(),product.getProductID(),cartDetailDto.getQuantity()+tmp.getQuantity());
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
    public List<ProductFromCartResponseDto> getCartInfor(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
        }

        Optional<Cart> cart = cartRepository.findById(user.get().getCustomer().getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(user.get().getCustomer().getCart().getId())});
        }
        return cartDetailRepository.getCartInfor(cart.get().getId());
    }

    @Override
    public CommonResponseDto updateCartInfor(String userId, CartDetailDto cartDetailDto) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
        }
        Optional<Cart> cart = cartRepository.findById(user.get().getCustomer().getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(user.get().getCustomer().getCart().getId())});
        }
        Product product = productRepository.findById(cartDetailDto.getProductId())
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(cartDetailDto.getProductId())}));


        if (cartDetailDto.getQuantity() > product.getQuantity()) {
            throw new InsufficientStockException(ErrorMessage.Product.ERR_INSUFFICIENT_STOCK);
        }

        cartDetailRepository.updateCartInfor(cart.get().getId(), product.getProductID(), cartDetailDto.getQuantity());

        return new CommonResponseDto(true, SuccessMessage.UPDATE);
    }

    @Override
    public CommonResponseDto deleteProductFromCart(String userId, int productId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new NotFoundException(ErrorMessage.User.ERR_NOT_FOUND_ID, new String[]{userId});
        }
        Optional<Cart> cart = cartRepository.findById(user.get().getCustomer().getCart().getId());
        if (cart.isEmpty()) {
            throw new NotFoundException(ErrorMessage.Cart.ERR_NOT_FOUND_ID, new String[]{String.valueOf(user.get().getCustomer().getCart().getId())});
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException(ErrorMessage.Product.ERR_NOT_FOUND_ID, new String[]{String.valueOf(productId)}));

        cartDetailRepository.deleteCartDetail(cart.get().getId(),productId);

        return new CommonResponseDto(true,SuccessMessage.DELETE);
    }


}
