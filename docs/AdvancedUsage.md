# Advanced Usage

This guide covers more advanced topics, such as implementing and using your own custom membership functions and T-norms, and provides a deeper dive into the learning algorithms.

## Implementing a Custom Membership Function

To create your own membership function, you need to implement the `IMembershipFunction` interface. This interface is `sealed` and currently only permits `SigmoidMF`. To add a new membership function, you will need to unseal the interface or add your new class to the `permits` clause.

The `IMembershipFunction` interface has the following methods:

*   `double compute(double x)`: Computes the membership value for a given input `x`.
*   `double[] getParameters()`: Returns an array of the membership function's parameters.
*   `IMembershipFunction withParameters(double[] params)`: Creates a new membership function with updated parameters.
*   `int getNumberOfParameters()`: Returns the number of parameters.
*   `double computeDerivative(double x, int paramIndex)`: Computes the derivative of the membership function with respect to a parameter.

Here's an example of a simple triangular membership function implemented as a record:

```java
public record TriangularMF(double a, double b, double c) implements IMembershipFunction {

    @Override
    public double compute(double x) {
        return Math.max(0, Math.min((x - a) / (b - a), (c - x) / (c - b)));
    }

    @Override
    public double[] getParameters() {
        return new double[] { a, b, c };
    }

    @Override
    public IMembershipFunction withParameters(double[] params) {
        return new TriangularMF(params[0], params[1], params[2]);
    }

    @Override
    public int getNumberOfParameters() {
        return 3;
    }

    @Override
    public double computeDerivative(double x, int paramIndex) {
        // Implementation of the derivative calculation
        // This can be complex and depends on the specific function
        return 0; // Placeholder
    }
}
```

**Note:** The `computeDerivative` method is crucial for the learning algorithm. You need to provide a correct implementation for the gradient descent algorithm to work properly.

## Implementing a Custom T-norm

To create your own T-norm, you need to implement the `ITNorm` interface. This interface has two methods:

*   `double computeNorm(double a, double b)`: Computes the T-norm of two values `a` and `b`.
*   `double computeDerivative(double a, double b, int varIndex)`: Computes the derivative of the T-norm with respect to one of its inputs.

Here's an example of the minimum T-norm:

```java
public class MinTNorm implements ITNorm {

    @Override
    public double computeNorm(double a, double b) {
        return Math.min(a, b);
    }

    @Override
    public double computeDerivative(double a, double b, int varIndex) {
        if (varIndex == 0) { // Derivative with respect to a
            return a <= b ? 1 : 0;
        } else { // Derivative with respect to b
            return b <= a ? 1 : 0;
        }
    }
}
```

## Learning Algorithms

The library provides two gradient descent learning algorithms:

*   **`OfflineGradientDescent` (Batch Gradient Descent):** In this algorithm, the gradients are accumulated for all samples in the dataset, and the parameters are updated only once at the end of each epoch. This can lead to a more stable convergence, but it can be slower for large datasets.

*   **`OnlineGradientDescent` (Stochastic Gradient Descent):** In this algorithm, the parameters are updated after each individual sample. This can be faster for large datasets and can help to escape local minima, but the learning process can be more noisy.

The choice of the learning algorithm and its parameters (like the learning rate) can have a significant impact on the performance of the ANFIS model. You may need to experiment with different algorithms and parameters to find the best configuration for your specific problem.
