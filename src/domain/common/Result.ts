import { Either } from "@/domain/common/Either";

type Result<T> = Either<Error, T>;

export default Result;
