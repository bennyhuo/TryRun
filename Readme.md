# TryRun

## Motivation

This is mainly designed for Kotlin Coroutines. When we launch a coroutine with a `try ... catch` in it, the `CancellationException` will be caught when you are trying to catch its super classes like `Exception` or `Throwable`, thus making it implicitly breaking the cancellation mechanism of Kotlin Coroutines. So we need another try catch without catching the `CancellationException` to respond to the cancel state.

## Set up

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("com.bennyhuo.kotlin:tryrun:1.0")
}
```

## Usage

### tryRun

If the block of code you wanna put into `try` block returns Unit, you can use the `tryRun` function:

```Kotlin
tryRun {
    ...
}
```

It returns an instance of `TryRunResult`. You can use `catch` to catch any exception you want:

```Kotlin
tryRun {
    ...
} catch { e: Exception ->
    ...
}
```

The type of the parameter `e` will be caught.

You can catch different exception types with `catching` function which also returns an instance of `TryRunResult`:

```Kotlin
tryRun {
    ...
} catching { e: NullPointerException ->
    ...
} catching { e: IllegalAccessException ->
    ...
} catching { e: UnsupportedOperationException ->
    ...
}
```

We also provide a simple function `catchAll` to catch any `Throwable`s:

```Kotlin
tryRun {
    ...
} catchAll {
    it.printStackTrace() // it: Throwable
}
```

`finally` is also the key feature:

```Kotlin
tryRun {
    ...
} finally {
    ...
}

tryRun {
    ...
} catching {
    ...
} finally {
    ...
}
```

### tryEval

`try ... catch` can be expressions in Kotlin. We also provide `tryEval` if you want to evaluate an expression in the try block.finally

For example:

```Kotlin
val a = tryEval {
    1
} catching { e: Exception ->
    2
} finally {
    3
}

// a should be '1'
```

All of the features are the same with `tryRun` except the return values.

# License

[MIT License](https://github.com/bennyhuo/TryRun/blob/master/LICENSE)

    Copyright (c) 2021 Bennyhuo

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
