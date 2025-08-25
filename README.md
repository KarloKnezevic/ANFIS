# Generic ANFIS Implementation in Java

This project provides a generic and extensible Java implementation of an Adaptive Neuro-Fuzzy Inference System (ANFIS). The implementation is designed to be easily configurable with different membership functions and T-norms, making it a flexible tool for building and training ANFIS models.

## What is ANFIS?

An Adaptive Neuro-Fuzzy Inference System (ANFIS) is a type of artificial neural network that is based on a Takagi-Sugeno fuzzy inference system. It integrates both neural networks and fuzzy logic principles, which allows it to capture the benefits of both in a single framework.

The basic idea behind ANFIS is to use a neural network's learning capabilities to tune the parameters of a fuzzy inference system. This results in a system that can learn from data like a neural network, but is also interpretable like a fuzzy logic system.

An ANFIS network typically has five layers:

1.  **Fuzzification Layer:** This layer takes the crisp input values and determines the degree to which they belong to each of the fuzzy sets. This is done using membership functions.
2.  **Rule Layer:** This layer calculates the firing strength (or weight) of each fuzzy rule. The firing strength is typically calculated by applying a T-norm operator (like the product or minimum) to the membership values from the fuzzification layer.
3.  **Normalization Layer:** This layer normalizes the firing strengths of the rules by dividing each firing strength by the sum of all firing strengths.
4.  **Defuzzification Layer:** This layer calculates the output of each rule based on the conclusion part of the rule (which is a linear function of the inputs in a Takagi-Sugeno system) and the normalized firing strength.
5.  **Output Layer:** This layer sums the outputs of all rules to produce the final output of the ANFIS network.

## Project Overview

This project provides a Java implementation of an ANFIS that is designed to be:

*   **Generic:** The implementation is not tied to any specific membership function or T-norm. You can easily plug in your own implementations by implementing the `IMembershipFunction` and `ITNorm` interfaces.
*   **Robust:** The code is well-structured and follows object-oriented principles, making it easier to understand, maintain, and extend.
*   **Configurable:** You can easily configure the ANFIS network by specifying the number of rules, the membership functions, the T-norm, and the learning algorithm.
*   **Well-Documented:** The code is documented with Javadoc comments to explain the purpose of each class and method.


## Documentation

For more detailed documentation, please see the following guides:

*   [Getting Started](./docs/GettingStarted.md)
*   [Advanced Usage](./docs/AdvancedUsage.md)

## Implementation Details

The core components of the implementation are:

*   **`IMembershipFunction` interface:** Defines the contract for membership functions. It has a `compute(x)` method to calculate the membership value and a `computeDerivative(x, paramIndex)` method to calculate the derivative with respect to a parameter, which is needed for the learning algorithm.
*   **`SigmoidMF` class:** An implementation of the `IMembershipFunction` interface that represents a sigmoid membership function.
*   **`ITNorm` interface:** Defines the contract for T-norms. It has a `computeNorm(a, b)` method to calculate the T-norm and a `computeDerivative(a, b, varIndex)` method to calculate the derivative.
*   **`HamacherProduct` class:** An implementation of the `ITNorm` interface that represents the Hamacher product T-norm.
*   **`ANFIS` class:** The main class that represents the ANFIS network. It is configured with membership functions and a T-norm.
*   **`Rule` and `Conclusion` classes:** Represent the fuzzy rules in the ANFIS network.
*   **`OfflineGradientDescent` and `OnlineGradientDescent` classes:** Two implementations of the gradient descent learning algorithm. The offline version updates the parameters after each epoch, while the online version updates them after each sample.

## How to Run

The project includes a `Main` class with an example of how to use the ANFIS implementation. To run the example, you first need to compile the code and then run the `Main` class.

### Compile the code

You can compile the code using the following command from the root of the project:

```bash
javac -d bin $(find src -name "*.java")
```

This will compile all the Java files and put the compiled classes into the `bin` directory.

### Run the example

You can run the example using the following command:

```bash
java -cp bin hr.fer.zemris.nenr.lab2.main.Main
```

This will run the `main` method in the `Main` class, which will create an ANFIS network, train it on a sample dataset, and then test its performance.
