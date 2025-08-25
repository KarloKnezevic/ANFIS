# Getting Started with the ANFIS Library

This guide will walk you through the process of setting up a project, creating an ANFIS model, training it, and using it for predictions.

## 1. Setting up your project

To use this ANFIS library, you need to have a Java development environment set up. You can then include the source files from this project in your own project.

The main components of the library are located in the `hr.fer.zemris.nenr.lab2` package.

## 2. Creating a Dataset

The first step is to create a dataset to train your ANFIS model. The dataset should be a list of `Pair` objects, where each `Pair` represents a sample with two inputs (`x` and `y`) and one output (`value`).

Here's an example of how to create a dataset for the function `f(x, y) = ((x - 1)^2 + (y + 2)^2 - 5xy + 3) * cos(x/5)`:

```java
import hr.fer.zemris.nenr.lab2.util.Pair;
import java.util.ArrayList;
import java.util.List;

// ...

List<Pair> dataset = new ArrayList<>();
for (int i = -4; i <= 4; i++) {
    for (int j = -4; j <= 4; j++) {
        dataset.add(new Pair(i, j, f(i, j)));
    }
}

private static double f(double x, double y) {
    return ((x - 1)*(x - 1) + (y + 2)*(y + 2) - 5*x*y + 3) * Math.cos(x/5.0);
}
```

## 3. Creating an ANFIS Model

Next, you need to create an instance of the `ANFIS` class. The `ANFIS` constructor takes the following arguments:

*   `rulesCount`: The number of fuzzy rules in the network.
*   `mfA`: The membership function for the first input (`x`).
*   `mfB`: The membership function for the second input (`y`).
*   `tNorm`: The T-norm to use for the fuzzy AND operation.
*   `rand`: A `Random` object for initializing the parameters.

Here's an example of how to create an `ANFIS` model with 10 rules, sigmoid membership functions, and the Hamacher product T-norm:

```java
import hr.fer.zemris.nenr.lab2.membershipFunction.IMembershipFunction;
import hr.fer.zemris.nenr.lab2.membershipFunction.SigmoidMF;
import hr.fer.zemris.nenr.lab2.network.ANFIS;
import hr.fer.zemris.nenr.lab2.tNorm.HamacherProduct;
import hr.fer.zemris.nenr.lab2.tNorm.ITNorm;
import java.util.Random;

// ...

int numRules = 10;
Random rand = new Random();
IMembershipFunction mfA = new SigmoidMF(rand);
IMembershipFunction mfB = new SigmoidMF(rand);
ITNorm tNorm = new HamacherProduct();
ANFIS anfis = new ANFIS(numRules, mfA, mfB, tNorm, rand);
```

## 4. Training the Model

To train the ANFIS model, you need to create an instance of a learning algorithm. The library provides two options:

*   `OfflineGradientDescent`: Batch learning, where the parameters are updated after each epoch (i.e., after processing the entire dataset).
*   `OnlineGradientDescent`: Online (or stochastic) learning, where the parameters are updated after each sample.

Here's an example of how to use the `OfflineGradientDescent` algorithm to train the model:

```java
import hr.fer.zemris.nenr.lab2.network.OfflineGradientDescent;

// ...

double learningRate = 0.001;
OfflineGradientDescent trainer = new OfflineGradientDescent(anfis, learningRate);

int epochs = 10000;
for (int i = 0; i < epochs; i++) {
    double error = trainer.learn(dataset);
    if ((i + 1) % 1000 == 0) {
        System.out.println("Epoch " + (i + 1) + ", error: " + error);
    }
}
```

## 5. Using the Trained Model for Predictions

Once the model is trained, you can use the `anfis.compute(x, y)` method to make predictions for new inputs.

Here's an example of how to test the trained network and compare the predicted values with the actual values:

```java
System.out.println("\nTesting the trained network:");
for (int i = -4; i <= 4; i++) {
    for (int j = -4; j <= 4; j++) {
        double expected = f(i, j);
        double actual = anfis.compute(i, j);
        System.out.println("Input: (" + i + ", " + j + "), Expected: " + expected + ", Actual: " + actual + ", Error: " + Math.abs(expected - actual));
    }
}
```
