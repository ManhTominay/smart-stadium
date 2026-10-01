package com.example.smartstadium.dto;

public record AvailableFieldResponse(
        Long fieldId,
        String fieldName,
        String sportType,
        Double pricePerHour,

        Long stadiumId,
        String stadiumName,
        String stadiumAddress,
        String stadiumDescription
) {
}