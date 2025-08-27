# Modern Java Fuzzy Logic System

This project is a modern Java implementation of a fuzzy logic system, built with Java 21 features and a professional, modular architecture.

## Features

*   **Modern Java**: Uses the latest Java syntax and features like records, sealed classes, and switch expressions.
*   **Modular and Extensible**: Designed to be highly modular and easy to extend with new fuzzy operators, membership functions, and more.
*   **Comprehensive Testing**: Includes a suite of unit tests using JUnit 5 and Mockito.
*   **Visualization**: Provides tools to visualize fuzzy rules and error rates, exporting them to PNG files.
*   **Clear Examples**: Comes with multiple examples to demonstrate basic and advanced usage.

## Getting Started

### Prerequisites

*   Java 21 or higher
*   Apache Maven

### Building the Project

To build the project, run the following command from the `fuzzy-logic-system` directory:

```bash
mvn clean install
```

### Running the Examples

You can run the examples using the `exec:java` goal.

**Simple Tipper Example:**

```bash
mvn exec:java -Dexec.mainClass="com.fuzzy.examples.SimpleTipper"
```

**Advanced Fan Controller Example:**

```bash
mvn exec:java -Dexec.mainClass="com.fuzzy.examples.AdvancedExample"
```

## Project Structure

The project follows a standard Maven layout with a professional package structure based on features:

*   `com.fuzzy.config`: Configuration loading.
*   `com.fuzzy.data`: Data loading and representation.
*   `com.fuzzy.system`: Core fuzzy logic system components.
    *   `evaluator`: Fitness evaluation.
    *   `membership`: Membership function implementations.
    *   `model`: Core fuzzy system model classes (rules, sets, variables).
    *   `operators`: Fuzzy operators (T-Norm, S-Norm, Defuzzifier).
*   `com.fuzzy.visualization`: Visualization tools.
*   `com.fuzzy.examples`: Usage examples.
