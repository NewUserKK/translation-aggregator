import { Either } from "@/data/common/Either";

type Result<T> = Either<Error, T>;

export default Result;
