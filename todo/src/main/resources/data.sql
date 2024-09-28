-- Insert Companies
INSERT INTO company (id, name, address) VALUES
(1, 'Tech Innovations Inc.', '123 Tech St, Silicon Valley, CA'),
(2, 'Green Solutions LLC', '456 Greenway Blvd, San Francisco, CA'),
(3, 'Global Marketing Co.', '789 Market St, New York, NY');

-- Insert Users
INSERT INTO user_profile (matricule, first_name, last_name, email, company_id, category) VALUES
('001', 'Alice', 'Johnson', 'alice.johnson@example.com', 1, 'STANDARD'),
('002', 'Bob', 'Smith', 'bob.smith@example.com', 2, 'COMPANY_ADMIN'),
('003', 'Charlie', 'Brown', 'charlie.brown@example.com', 3, 'SUPER_USER'),
('004', 'Diana', 'Prince', 'diana.prince@example.com', 2, 'STANDARD'),
('005', 'Ethan', 'Hunt', 'ethan.hunt@example.com', 1, 'COMPANY_ADMIN');

-- Insert Tasks
INSERT INTO task (title, description, due_date, status, assignee_id) VALUES 
('Task 1', 'Complete project report', '2024-01-01', 'IN_PROGRESS', '001'), 
('Task 2', 'Prepare presentation for meeting', '2024-01-07', 'COMPLETED', '002'), 
('Task 3', 'Research new marketing strategies', '2022-01-15', 'ARCHIVED', '003'), 
('Task 4', 'Review quarterly budget', '2024-03-04', 'IN_PROGRESS', '004'), 
('Task 5', 'Plan team-building event', '2024-05-05', 'COMPLETED', '005');
