package com.example.demo.user.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

/**
 * Phone represents a phone number entity associated with a user.
 *
 * This class is mapped to the "phones" table in the database.
 * It contains fields for the phone number and its related dialing codes.
 *
 * Fields:
 * - id: the unique identifier for the phone entity, generated as a UUID
 * - number: the phone number string
 * - cityCode: the city dialing code
 * - countryCode: the country dialing code
 */
@Entity
@Table(name = "phones")
@Data
public class Phone {

    /**
     * The unique identifier of the phone entry.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * The phone number.
     */
    private String number;

    /**
     * The city dialing code.
     */
    private String cityCode;

    /**
     * The country dialing code.
     */
    private String countryCode;
}
