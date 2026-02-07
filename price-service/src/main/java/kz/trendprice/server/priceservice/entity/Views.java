package kz.trendprice.server.priceservice.entity;

public class Views {
    public interface Public {}             // Basic fields
    public interface Internal extends Public {} // Basic + sensitive fields
}
