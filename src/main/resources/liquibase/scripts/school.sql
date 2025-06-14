--liquibase formatted sql

--changeset author:MorozovVladislavSergeevich
CREATE INDEX idx_student_name ON student (name);

CREATE INDEX idx_faculty_name_color ON faculty (name, color);