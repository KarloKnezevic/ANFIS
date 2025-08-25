# Getting Started with the ANFIS Library

This guide will walk you through the process of setting up a project, creating an ANFIS model, training it, and using it for predictions.

## 1. Setting up your project

<<<<<<< HEAD
This project is built with Maven. To use it in your own project, you can add it as a dependency in your `pom.xml` file.

First, you need to install the library to your local Maven repository. You can do this by running the following command from the root of this project:

```bash
mvn clean install
```

Then, you can add the following dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>hr.fer.zemris.nenr.lab2</groupId>
    <artifactId>anfis</artifactId>
    <version>1.0.0</version>
</dependency>
```
=======
To use this ANFIS library, you need to have a Java development environment set up. You can then include the source files from this project in your own project.

The main components of the library are located in the `hr.fer.zemris.nenr.lab2` package.
>>>>>>> master

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

<<<<<<< HEAD
## 3. Configuring the ANFIS Model

The ANFIS model is configured through a `config.properties` file located in the `src/main/resources` directory. This file allows you to set the parameters of the network without changing the code.

Here's an example of a `config.properties` file:

```properties
# ANFIS Configuration
anfis.rules=10
anfis.learningRate=0.00001
anfis.epochs=10000
```

The `Main` class in the project shows how to load these properties and use them to create and train an ANFIS model.

## 4. Creating and Training the ANFIS Model

The `ANFIS.create` static factory method is used to create a new ANFIS model. It takes the number of rules, factories for the membership functions, a T-norm, and a `Random` object as arguments.

The learning algorithms (`OfflineGradientDescent` and `OnlineGradientDescent`) are designed to work with the immutable `ANFIS` record. The `learn` method takes an `ANFIS` instance and a dataset, and returns a new `ANFIS` instance with the updated parameters.

Here's an example of how to create and train an ANFIS model:

```java
// Load configuration from properties file
// ...

// Create ANFIS
Random rand = new Random();
ITNorm tNorm = new HamacherProduct();
ANFIS anfis = ANFIS.create(numRules, SigmoidMF.factory(), SigmoidMF.factory(), tNorm, rand);

// Create learning algorithm
OfflineGradientDescent trainer = new OfflineGradientDescent(learningRate);

// Train the network
for (int i = 0; i < epochs; i++) {
    anfis = trainer.learn(anfis, dataset);
    // ...
}
```

## 5. Monitoring and Testing

The `Main` class provides a simple CLI that shows the learning progress in real-time. After training, it tests the network and prints the predicted values against the actual values.

You can run the `Main` class to see the whole process in action.
=======
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
>>>>>>> master
