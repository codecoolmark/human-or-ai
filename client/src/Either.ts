export class Either<A, B> {
    a?: A;
    b?: B;

    constructor(value: {a: A} | {b: B}) {
        if ("a" in value) {
            this.a = value.a;
        } else if ("b" in value) {
            this.b = value.b;
        }
    }

    useA(action: (a: A) => void): Either<A, B> {
        if (this.a) {
            action(this.a)
        }
        return this;
    }

    useB(action: (a: B) => void): Either<A, B> {
        if (this.b) {
            action(this.b)
        }
        return this;
    }
}