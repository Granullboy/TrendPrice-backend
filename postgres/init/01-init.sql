SELECT 'CREATE DATABASE price_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'price_service')\gexec

SELECT 'CREATE DATABASE product_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'product_service')\gexec

SELECT 'CREATE DATABASE store_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'store_service')\gexec

SELECT 'CREATE DATABASE parser_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'parser_service')\gexec

SELECT 'CREATE DATABASE user_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'user_service')\gexec

SELECT 'CREATE DATABASE receipt_service'
    WHERE NOT EXISTS (SELECT FROM pg_database WHERE datname = 'receipt_service')\gexec