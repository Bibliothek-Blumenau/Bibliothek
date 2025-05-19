CREATE TABLE users (
    registration VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    roles VARCHAR(255) NOT NULL,
    profile_pic VARCHAR(255)
);

CREATE TABLE book (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    stock INTEGER NOT NULL,
    available_stock INTEGER NOT NULL,
    cover_image VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    featured BOOLEAN NOT NULL
);

CREATE TABLE loan (
    id UUID PRIMARY KEY,
    book_id UUID NOT NULL REFERENCES book(id),
    user_registration VARCHAR(255) NOT NULL REFERENCES users(registration),
    status VARCHAR(255) NOT NULL,
    loan_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP,
    fine NUMERIC(10, 2) NOT NULL
);

-- Loan indexes
CREATE INDEX idx_loan_book_id ON loan(book_id);
CREATE INDEX idx_loan_user_registration ON loan(user_registration);
CREATE INDEX idx_loan_status ON loan(status);
CREATE INDEX idx_loan_loan_date ON loan(loan_date);
CREATE INDEX idx_loan_return_date ON loan(return_date);

-- Book indexes
CREATE INDEX idx_book_genre ON book(genre);
CREATE INDEX idx_book_author ON book(author);
CREATE INDEX idx_book_featured ON book(featured);
