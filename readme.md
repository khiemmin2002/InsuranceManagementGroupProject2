
# COSC2440 Group Assignment 2
----
## Overview:
Welcome to the Insurance Claims Management System. This platform is designed to facilitate the management, tracking, and processing of insurance claims with a focus on ease of use and efficiency. The system accommodates different user roles, each with distinct responsibilities, to ensure seamless operation and comprehensive support for all users.

## Technical
This project use Java and JavaFX library to build, Junit 5 for unit test and requrie JavaSDK 22.
## Team 5 group members:
- [Giang Trong Duong](https://github.com/GiangTrongDuong)
- [Min Chi Gia Khiem](https://github.com/khiemmin2002)
- [Nguyen A Luy](https://github.com/NguyenALuy)
- [Nguyen Huynh Quang](https://github.com/nguyenhuynhquang2909)

## Team contribution
- Giang Trong Duong: 3 points
- Min Chi Gia Khiem: 3 points
- Nguyen A Luy: 3 points
- Nguyen Huynh Quang: 3 points

---

## Test account for database:
### System Admin
`username`: admin

`password`: admin123

### Policy Owner
`username`:

`password`:

### Policy Holder
`username`:

`password`:

### Dependent
`username`:

`password`:

### Insurance Manager
`username`: whyme

`password`: whyme123

### Insurance Surveyor
`username`: adamguy

`password`: adam123

---
# System Overview
## User Roles and Responsibilities:

### Customer:

- Policy Holder: File/update/retrieve own and dependent claims, update personal information (phone, address, email, password).

- Dependent: Retrieve personal claims and information.

- Policy Owner: File/update/delete/retrieve claims of beneficiaries, add/update/remove/get beneficiaries. Calculate yearly insurance cost.

### Provider:

- Insurance Surveyor: Request more information, propose claims to managers, retrieve claims and customers with filtering options.

- Insurance Manager: Approve/reject proposed claims, retrieve claims and customers with filtering options, get information of surveyors.

### System Admin:

- CRUD operations on all entities except claims (retrieve only).

- Calculate total successfully claimed amount with filtering options.

## System Functionalities:

- Login System with role-based authorization.

## Claim Management:

- File, update, retrieve, and manage claims based on user role.

- Upload claim documents (images) with specified format renaming.

## User Management:

- Update user information (limited based on role).

- Policy Owner specific functionalities (beneficiary management, yearly cost calculation). 

