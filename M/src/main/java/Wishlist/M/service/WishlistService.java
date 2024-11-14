package Wishlist.M.service;

import Wishlist.M.model.Wishlist;
import Wishlist.M.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    @Autowired
    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }


    public void createWishlist(String name, Long userId) {
        Wishlist wishlist = new Wishlist();
        wishlist.setWishlistName(name);
        wishlist.setUserId(userId); // Set Wishlit til specifik bruger
        wishlistRepository.save(wishlist);


    }


    public Wishlist getWishlistById(Long id) {
        return wishlistRepository.findById(id).orElse(null);
    }
    public List<Wishlist> getWishlistsByUserId(Long userId) {
        return wishlistRepository.findByUserId(userId);
    }


}
