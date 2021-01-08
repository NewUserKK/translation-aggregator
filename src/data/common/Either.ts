export abstract class Either<A, B> {
  matcher<R>(): EitherMatcher<A, B, R> {
    return new EitherMatcher(this);
  }
}

export class Left<A> extends Either<A, never> {
  constructor(readonly value: A) {
    super();
  }
}
export class Right<B> extends Either<never, B> {
  constructor(readonly value: B) {
    super();
  }
}

export function rightOf<B>(value: B): Either<never, B> {
  return new Right(value);
}
export function leftOf<A>(value: A): Either<A, never> {
  return new Left(value);
}

class EitherMatcher<A, B, R> {
  constructor(
    private readonly either: Either<A, B>,
    private result: R | null = null
  ) {}

  onLeft(block: (value: A) => R): EitherMatcher<A, B, R> {
    if (this.either instanceof Left) {
      this.result = block(this.either.value);
    }

    return this;
  }

  throwOnLeft(): EitherMatcher<A, B, R> {
    if (this.either instanceof Left) {
      throw this.either.value;
    }

    return this;
  }

  onRight(block: (value: B) => R): EitherMatcher<A, B, R> {
    if (this.either instanceof Right) {
      this.result = block(this.either.value);
    }

    return this;
  }

  selfOnRight(): EitherMatcher<A, B, R> {
    if (this.either instanceof Right) {
      this.result = this.either.value;
    }

    return this;
  }

  match(): R {
    if (this.result != null) {
      return this.result;
    }

    throw "Unmatched result";
  }
}

export function matchEither<A, B, R>(
  either: Either<A, B>
): EitherMatcher<A, B, R> {
  return new EitherMatcher(either);
}
