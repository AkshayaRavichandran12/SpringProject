package com.petconnect.dto;

public class BookingRequest {
    private Long userId;
    private Long petId;
    private String contact;
    private String bookingDate;
    private String petName;

    // Getters and setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getPetId() { return petId; }
    public void setPetId(Long petId) { this.petId = petId; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getBookingDate() { return bookingDate; }
    public void setBookingDate(String bookingDate) { this.bookingDate = bookingDate; }

    public String getPetName() { return petName; }
    public void setPetName(String petName) { this.petName = petName; }
    
  

}
