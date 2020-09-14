# Assignment 3

## Task 3

### Design decisions

We chose to use global parameters in favor of parameter passing for two reasons:

1. The global parameters never had to change (i.e. they are `final`), and they were each
used in very few places in the code, so it didn't add much complexity or potential bugs.

2. Fewer changes to the code were needed this way. With our implementation of the chat
program, the alternative would have been to pass the parameters to the constructors of
the classes that use them, which would save it as a local variable accessible within
that instance of the class (necessary for other threads to access it). For the current
features, this would be just as 'global' as our current approach, because all of them
are rarely used.
Moreover, when choosing which features to enable, you would probably want them to be in
one place, so a group of global constants was needed either way. Passing those constants
to classes is extra effort and clutter that was not worth the payoff in this case in
our opinion.

### Feature selection

The user should adapt the `Conf.java` file to specify their requirements. It has four
major features (`COLOR` for colored texts, `CRYPTO` for encrypted messages, `AUTH` for
password authentication and `LOG` for message logging), along with feature-specific
options (such as default color, which crypto to use, what password and where to place
log files).

There is no risk of invalid feature selection, because all features are completely
independent of each other. You can choose to use colors or not regardless of
messages being encrypted or logged.

### Left-out variability

We implemented variability for each of the features that we had to implement for the
previous assignment, because all of them were entirely optional for a working chat
program, so it makes sense to disable them if they are not needed.

## Task 4

We are in a group of two, so this task is optional, but we had already implemented the
strategy pattern last week. Since we had to use two different layers of encryption, it
made sense to create an interface for encryption, and two implementations as
strategies: one for ROT-13 and one for reversing the input. However, since both of
these strategies are stateless, the methods for encrypting and decrypting could
essentially be static. But Java doesn't support static interfaces (for a good reason),
so instead we had to force the programmer to only ever create one instance of the
strategy and save that globally. Since the singleton pattern is ugly, we created an
enumeration with abstract functions for encrypt and decrypt instead. While this doesn't
look like the traditional Java strategy pattern, it's still some abstract type
(`Cipher`) with concrete implementations (`ROT13`, `REVERSE`).
