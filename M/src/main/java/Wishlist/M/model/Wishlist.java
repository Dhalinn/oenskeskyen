package Wishlist.M.model;

public class Wishlist {
    private Long wishlistId;
    private String wishlistName;
    private Long userId;

    // Constructor
    public Wishlist() {}

    public Wishlist(Long wishlistId, String wishlistName, Long userId) {
        this.wishlistId = wishlistId;
        this.wishlistName = wishlistName;
        this.userId = userId;
    }

    // Getter og setter
    public Long getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Long wishlistId) {
        this.wishlistId = wishlistId;
    }

    public String getWishlistName() {
        return wishlistName;
    }

    public void setWishlistName(String wishlistName) {
        this.wishlistName = wishlistName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
