<div align="center">
    <img src="./images/ATU_Logo-removebg-preview.png" width="60%" alt="ATU Logo">
</div>

# Advanced Software Design Repository

#### Rodrigo Almeida - G00377123

## Introduction

The ATU Examination Paper Management System is a Java-based application designed for managing examination processes at ATU's Department of Computer Science & Applied Physics. This document outlines the key software design features and DOP principles implemented in the system.

## Software Design Features and DOP Principles

### Modular Architecture
- **Description**: The system is structured into distinct packages and classes, each responsible for specific functionalities.
- **DOP Alignment**: While not a DOP principle per se, modular architecture complements DOP by facilitating clearer data flow and management.

### Data Serialization for Persistence
- **Description**: Serialization is used for storing and retrieving objects like `ExaminationPaper`, `ModuleInfo`, and `Examiner`.
- **DOP Alignment**: This approach aligns with DOP's emphasis on treating data as a central asset, focusing on its natural form and persistence.

### Interface-based Design
- **Description**: Services are defined through interfaces, decoupling functionality from implementation.
- **DOP Alignment**: Interfaces can facilitate DOP by clearly defining how data is accessed and manipulated, though true DOP would further separate data and logic.

### Use of Java Records for Data Structuring
- **Description**: Java records are used for concise and immutable data carriers.
- **DOP Alignment**: Immutable data structures are a core DOP principle, enhancing data integrity and predictability.

### Scanner for Command-line User Interaction
- **Description**: A console-based interface is employed for its simplicity.
- **DOP Alignment**: While not directly related to DOP, this choice supports a straightforward data input and output mechanism.

### Immutable Data Structures
- **Description**: The system makes use of immutable data structures, as seen in the usage of Java records.
- **DOP Alignment**: Adheres to the DOP principle of immutability, ensuring data state is not changed unexpectedly.

### Separation of Data from Logic
- **Description**: The system partially separates data from logic, with potential for further decoupling.
- **DOP Alignment**: This separation aligns with DOP's focus on treating data and logic as distinct entities.

### Data Schema
- **Description**: Clear data schemas are defined for various entities like examination papers and modules.
- **DOP Alignment**: DOP emphasizes the importance of well-defined data schemas, which is evident in the system's design.

## Conclusion

The ATU Examination Paper Management System incorporates several DOP principles within its object-oriented architecture, focusing on data integrity, clear schema definition, and immutability. While it shows alignment with DOP in certain aspects, there is room for further integration of DOP principles, particularly in achieving a greater separation of data and logic.