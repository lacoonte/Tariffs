CREATE TABLE Tariffs (
                        BRAND_ID BIGINT,
                        START_DATE TIMESTAMP,
                        END_DATE TIMESTAMP,
                        TARIFF_LIST BIGINT,
                        PRODUCT_ID BIGINT,
                        PRIORITY INT,
                        PRICE DECIMAL(10, 2),
                        CURR CHAR(3),

                        PRIMARY KEY (TARIFF_LIST),

    -- If we had brands in same DB
    -- FOREIGN KEY (BRAND_ID) REFERENCES Brands(ID)
    -- If we had products in same DB
    -- FOREIGN KEY (BRAND_ID) REFERENCES Products(ID)

    -- Additional constraint to ensure that START_DATE is before END_DATE
                        CHECK (START_DATE < END_DATE)
);